package com.stock.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.stock.dataobject.StockInfo;
import com.stock.dataobject.StockRiseAndFall;
import com.stock.service.StockService;
import com.stock.utils.StockUtil;

@RestController
@RequestMapping("/stock")
public class StockController {

	private final Logger log = LoggerFactory.getLogger(StockController.class);
	
    private String url = "http://hq.sinajs.cn/list=";
    
    @Autowired
    private StockService stockService;

    RestTemplate restTemplate = new RestTemplate();
    
    List<StockInfo> hsStocks = null;
    
    List<StockInfo> hkStocks = null;

    @GetMapping("/index")
    public ModelAndView index(Map<String, Object> map){

        List<StockInfo> canBuyList = new ArrayList<StockInfo>();

        List<StockInfo> viewList = new ArrayList<StockInfo>();
        
        List<StockInfo> hkviewList = new ArrayList<StockInfo>();

        List<StockInfo> hugeFallList = new ArrayList<StockInfo>();

        List<StockInfo> hugeFallList2 = new ArrayList<StockInfo>();
        
        List<StockInfo> hongkongList = new ArrayList<StockInfo>();
        
        

        try {
            //查询所有关注股票
        	List<StockInfo> stockInfoList = stockService.getStockList();
            if(stockInfoList!=null && stockInfoList.size()>0){
            	
            	//A股拼接查询字符串
            	log.info("开始组装A股数据。");
            	String codes = this.getCodes(stockInfoList);
                //String codes = "sh600300,sh600811,sz000698";  //for test
                if(codes != null){
                    //获取实时数据
                    long start = System.currentTimeMillis();
                    String response = restTemplate.getForObject(url+codes, String.class);
                    log.info("获取A股实时数据时间："+(System.currentTimeMillis()-start)/1000 + "s.");
                    //数据组装
                    viewList = this.assembleDatas(response, hsStocks, "hs");
                }
                log.info("成功组装A股数据。");
                
              //港股拼接查询字符串
                log.info("开始组装港股数据。");
            	String hkCodes = this.getHKCodes(stockInfoList);
                if(hkCodes != null){
                    long start = System.currentTimeMillis();
                    String hkresponse = restTemplate.getForObject(url+hkCodes, String.class);
                    log.info("获取港股实时数据时间："+(System.currentTimeMillis()-start)/1000 + "s.");
                    //数据组装
                    hkviewList = this.assembleDatas(hkresponse, hkStocks, "hk");
                }
                log.info("成功组装A股数据。");
                
                
                if(viewList.size()>0) {
                    Iterator<StockInfo> iterable = viewList.iterator();
                    while(iterable.hasNext()){
                        StockInfo stockInfo = iterable.next();
                        
                        //获取可以买入的股票列表
                        if(stockInfo.getBuyRate().startsWith("-")){
                            canBuyList.add(stockInfo);
                        }
                        
                        //获取最大跌幅在90%以上、85%以上的股票
                        String maxRate = stockInfo.getMaxRate();
                        if (maxRate!=null) {
                            if(maxRate.startsWith("9")){
                                hugeFallList.add(stockInfo);
                            }
                            if(maxRate.startsWith("85") || maxRate.startsWith("86") || maxRate.startsWith("87") || maxRate.startsWith("88") || maxRate.startsWith("89")){
                                hugeFallList2.add(stockInfo);
                            }
                        }
                        
                    }
                }
                map.put("viewList", viewList);
                map.put("hkviewList", hkviewList);
                map.put("canBuyList", canBuyList);
                map.put("hugeFallList", hugeFallList);
                map.put("hugeFallList2", hugeFallList2);
                
                log.info("viewList:" + viewList.size());
                log.info("canBuyList:" + canBuyList.size());
                log.info("hugeFallList:" + hugeFallList.size());
            }
        }catch (Exception e){
            log.error("Exec index() exception: "+e.getMessage());
        }
        return new ModelAndView("/stock/index", "map", map);
    }

    //拼接查询字符串
    public String getCodes(List<StockInfo> stockInfoList) {
    	hsStocks = new ArrayList<StockInfo>();
        StringBuffer sb = new StringBuffer();
        if(stockInfoList != null && stockInfoList.size() > 0){
            for(StockInfo stockInfo : stockInfoList){
                String code = stockInfo.getCode();
                if(code.length() == 6){
                	sb.append(code.startsWith("6") ? "sh" : "sz");
                    sb.append(code);
                    sb.append(",");
                    hsStocks.add(stockInfo);
                }
            }
            return sb.toString().substring(0, sb.length()-1);
        }
        return null;
    }
    
    //港股拼接查询字符串
    public String getHKCodes(List<StockInfo> stockInfoList) {
    	hkStocks = new ArrayList<StockInfo>();
        StringBuffer sb = new StringBuffer();
        if(stockInfoList != null && stockInfoList.size() > 0){
            for(StockInfo stockInfo : stockInfoList){
                String code = stockInfo.getCode();
                if(code.length() == 5){
                	sb.append("hk");
                    sb.append(code);
                    sb.append(",");
                    hkStocks.add(stockInfo);
                }
            }
            return sb.toString().substring(0, sb.length()-1);
        }
        return null;
    }

    //组装数据
    public List<StockInfo> assembleDatas(String response, List<StockInfo> stockInfoList, String flag) {
        DecimalFormat df = new DecimalFormat("0.00%");
        List<StockInfo> viewList = new ArrayList<StockInfo>();
        String[] result = response.split("\n");
        if(result != null && result.length > 0){
            for(int i=0; i<result.length; i++) {
            	StockInfo repostoryStock = stockInfoList.get(i);
                String[] stockData = result[i].split(",");
                if (stockData != null && stockData.length > 0) {
                    StockInfo stockInfo = new StockInfo();

                    //股票代码和名称
                    if("hs".equals(flag)){
                    	String nameStr = stockData[0];
                        stockInfo.setCode(nameStr.substring(13, 19));
                        stockInfo.setName(nameStr.substring(21, nameStr.length()));
                    }
                    if("hk".equals(flag)){
                    	stockInfo.setCode(repostoryStock.getCode());
                        stockInfo.setName(stockData[1]);
                    }

                    //实时价格
                    Double realTimePrice = 0.0;
                    if (stockData[3].startsWith("0.0")) {
                        realTimePrice = 200.00;
                    } else {
                    	if("hs".equals(flag)){
                    		realTimePrice = Double.parseDouble(stockData[3]);
                    	} else if("hk".equals(flag)){
                    		realTimePrice = Double.parseDouble(stockData[6]);
                    	}
                        
                    }
                    stockInfo.setRealTimePrice(realTimePrice);

                    //昨日收盘价格
                    Double yesterDayPrice = 0.0;
                    if("hs".equals(flag)){
	                    if(stockData[2].startsWith("0.0")){
	                        yesterDayPrice = realTimePrice;
	                    } else {
	                        yesterDayPrice = Double.parseDouble(stockData[2]);
	                    }
                    } else if("hk".equals(flag)){
                    	if(stockData[3].startsWith("0.0")){
	                        yesterDayPrice = realTimePrice;
	                    } else {
	                        yesterDayPrice = Double.parseDouble(stockData[3]);
	                    }
                    }
                    //今日涨跌幅
                    String ratePercent = "";
                    if(yesterDayPrice >=0 ){
                        ratePercent = df.format((realTimePrice - yesterDayPrice) / yesterDayPrice);
                    }
                    stockInfo.setRatePercent(ratePercent);

                    //买入价格
                    Double buyPrice = repostoryStock.getBuyPrice();
                    if (buyPrice == null || "".equals(buyPrice)) {
                        buyPrice = 1.0;
                    }
                    stockInfo.setBuyPrice(buyPrice);

                    //买入还差百分之几
                    String buyRate = "";
                    if(realTimePrice>=0){
                        buyRate = df.format((realTimePrice - buyPrice) / realTimePrice);
                        if(buyRate.length()<6){
                            buyRate = "0" + buyRate;
                        }
                    }
                    stockInfo.setBuyRate(buyRate);

                    //最高点已跌百分比
                    Double maxValue = repostoryStock.getMaxValue();
                    String maxRate = "";
                    if(maxValue!=null && maxValue>=0){
                        maxRate = df.format((maxValue - realTimePrice) / maxValue);
                    }
                    stockInfo.setMaxRate(maxRate);

                    //stockInfo.setRealtimeDate(stockData[30]+" "+stockData[31]);
                    stockInfo.setDescription(stockInfoList.get(i).getDescription() == null ? "" : stockInfoList.get(i).getDescription());
                    viewList.add(stockInfo);
                    
                }
            }
        }
        return viewList;
    }

    /**
     * 获取股票涨跌数量
     * @param map
     * @return
     */
    @GetMapping("/findRiseAndFallData")
    public ModelAndView findRiseAndFallData(Map<String, Object> map){
        String response = restTemplate.getForObject("https://www.legulegu.com/stockdata/market-activity", String.class);
        Map<String, Integer> riseAndFallDataMap = StockUtil.findRiseAndFallData(response);
        log.info("size:"+riseAndFallDataMap.size());
        StockRiseAndFall stockRiseAndFall = new StockRiseAndFall();
        StockUtil.mapToBean(riseAndFallDataMap, stockRiseAndFall);
        stockRiseAndFall.setUp0To3Percentage(StockUtil.calPercentage(Float.valueOf(stockRiseAndFall.getUp0To3()), Float.valueOf(stockRiseAndFall.getTotal())));
        stockRiseAndFall.setUp3To5Percentage(StockUtil.calPercentage(Float.valueOf(stockRiseAndFall.getUp3To5()), Float.valueOf(stockRiseAndFall.getTotal())));
        stockRiseAndFall.setUp5To7Percentage(StockUtil.calPercentage(Float.valueOf(stockRiseAndFall.getUp5To7()), Float.valueOf(stockRiseAndFall.getTotal())));
        stockRiseAndFall.setUp7To10Percentage(StockUtil.calPercentage(Float.valueOf(stockRiseAndFall.getUp7To10()), Float.valueOf(stockRiseAndFall.getTotal())));
        stockRiseAndFall.setDown0To3Percentage(StockUtil.calPercentage(Float.valueOf(stockRiseAndFall.getDown0To3()), Float.valueOf(stockRiseAndFall.getTotal())));
        stockRiseAndFall.setDown3To5Percentage(StockUtil.calPercentage(Float.valueOf(stockRiseAndFall.getDown3To5()), Float.valueOf(stockRiseAndFall.getTotal())));
        stockRiseAndFall.setDown5To7Percentage(StockUtil.calPercentage(Float.valueOf(stockRiseAndFall.getDown5To7()), Float.valueOf(stockRiseAndFall.getTotal())));
        stockRiseAndFall.setDown7To10Percentage(StockUtil.calPercentage(Float.valueOf(stockRiseAndFall.getDown7To10()), Float.valueOf(stockRiseAndFall.getTotal())));
        map.put("stockRiseAndFall", stockRiseAndFall);
        return new ModelAndView("/stock/riseAndFallData", "map", map);
    }

    @GetMapping("/update")
    public void update(@RequestParam("tdIndex") String tdIndex,
                        @RequestParam("code") String code,
                        @RequestParam("value") String value) {
        log.info("Update info : code:" + code + ", tdIndex:" + tdIndex + ", value:" + value);
        stockService.update(tdIndex, code, value);
        log.info("update sucess.");
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("code") String code){
        try {
            log.info("Delete info : code:" + code);
            stockService.delete(code);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        log.info("Delete sucess.");
        return "删除成功";
    }

    @GetMapping("/add")
    public String add(@RequestParam("code") String code, @RequestParam("name") String name, 
    		@RequestParam("maxPrice") String maxPrice, @RequestParam("buyPrice") String buyPrice){
        try {
            StockInfo stockInfo = new StockInfo();
            stockInfo.setCode(code);
            stockInfo.setName(name);
            stockInfo.setBuyPrice(Double.parseDouble(buyPrice));
            stockInfo.setMaxValue(Double.parseDouble(maxPrice));;
            stockService.add(stockInfo);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        log.info(code + " add sucess.");
        return "添加成功";
    }

}

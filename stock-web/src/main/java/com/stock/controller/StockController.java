package com.stock.controller;

import com.stock.dataobject.StockInfo;
import com.stock.dataobject.StockRiseAndFall;
import com.stock.service.StockService;
import com.stock.utils.StockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
@Slf4j
public class StockController {

    private String url = "http://hq.sinajs.cn/list=";

    @Autowired
    private StockService stockService;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/index")
    public ModelAndView index(Map<String, Object> map){

        List<StockInfo> canBuyList = new ArrayList<StockInfo>();

        List<StockInfo> viewList = new ArrayList<StockInfo>();

        List<StockInfo> deletedList = new ArrayList<StockInfo>();

        try {
            //查询所有关注股票
            List<StockInfo> stockInfoList = stockService.findAll();

            //拼接查询字符串
            String codes = this.getCodes(stockInfoList);
            //String codes = "sh601006,sh600825,sz300291";  //for test
            if(codes != null){
                //获取实时数据
                long start = System.currentTimeMillis();
                String response = restTemplate.getForObject(url+codes, String.class);
                log.info("获取实时数据时间："+(System.currentTimeMillis()-start)/1000 + "s.");

                //数据组装
                viewList = this.assembleDatas(response, stockInfoList);
            }
            log.info("成功组装数据。");
            if(viewList.size()>0) {
                Iterator<StockInfo> iterable = viewList.iterator();
                while(iterable.hasNext()){
                    StockInfo stockInfo = iterable.next();
                    if(stockInfo.getBuyRate().startsWith("-")){
                        canBuyList.add(stockInfo);
                    }

                    if(stockInfo.getFlag() == 2){  //已删除
                        deletedList.add(stockInfo);
                        iterable.remove();
                    }
                }
                /*for (StockInfo stockInfo : viewList) {
                    if(stockInfo.getBuyRate().startsWith("-")){
                        canBuyList.add(stockInfo);
                    }
                    if(stockInfo.getFlag0() == 2){  //已删除
                        deletedList.add(stockInfo);
                    }
                }*/
            }
            map.put("viewList", viewList);
            map.put("canBuyList", canBuyList);
            map.put("deletedList", deletedList);
            log.info("viewList:" + viewList.size());
            log.info("canBuyList:" + canBuyList.size());
            log.info("deletedList:" + deletedList.size());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ModelAndView("/stock/index", "map", map);
    }

    //拼接查询字符串
    public String getCodes(List<StockInfo> stockInfoList) {
        StringBuffer sb = new StringBuffer();
        if(stockInfoList != null && stockInfoList.size() > 0){
            for(StockInfo stockInfo : stockInfoList){
                String code = stockInfo.getCode();
                sb.append(code.startsWith("6") ? "sh" : "sz");
                sb.append(code);
                sb.append(",");
            }
            return sb.toString().substring(0, sb.length()-1);
        }
        return null;
    }

    //组装数据
    public List<StockInfo> assembleDatas(String response, List<StockInfo> stockInfoList) {
        List<StockInfo> viewList = new ArrayList<StockInfo>();
        String[] result = response.split("\n");
        if(result != null && result.length > 0){
            for(int i=0; i<result.length; i++) {
                String[] stockData = result[i].split(",");
                if (stockData != null && stockData.length > 0) {
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    StockInfo stockInfo = new StockInfo();

                    //股票代码和名称
                    String nameStr = stockData[0];
                    stockInfo.setCode(nameStr.substring(13, 19));
                    stockInfo.setName(nameStr.substring(21, nameStr.length()));

                    //实时价格
                    Double realTimePrice = 0.0;
                    if (stockData[3].startsWith("0.0")) {
                        realTimePrice = 200.00;
                    } else {
                        realTimePrice = Double.parseDouble(stockData[3]);
                    }
                    stockInfo.setRealTimePrice(realTimePrice);

                    //买入价格
                    Double buyPrice = stockInfoList.get(i).getBuyPrice();
                    if (buyPrice == null || "".equals(buyPrice)) {
                        buyPrice = 1.0;
                    }
                    stockInfo.setBuyPrice(buyPrice);

                    //买入还差百分之几
                    DecimalFormat df = new DecimalFormat("0.00%");
                    String buyRate = "";
                    if(realTimePrice>=0){
                        buyRate = df.format((realTimePrice - buyPrice) / realTimePrice);
                        if(buyRate.length()<6){
                            buyRate = "0" + buyRate;
                        }
                    }
                    stockInfo.setBuyRate(buyRate);

                    //最高点已跌百分比
                    Double maxValue = stockInfoList.get(i).getMaxValue();
                    String maxRate = "";
                    if(maxValue>=0){
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
    public void delete(@RequestParam("code") String code){
        log.info("Delete info : code:" + code);
        stockService.delete(code);
        log.info("Dpdate sucess.");
    }


}

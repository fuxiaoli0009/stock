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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
@Slf4j
public class StockController {

    String url = "http://hq.sinajs.cn/list=";

    @Autowired
    private StockService stockService;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/index")
    public ModelAndView index(Map<String, Object> map){
        List<StockInfo> stockInfoList = stockService.findAll();
        List<StockInfo> viewList = new ArrayList<StockInfo>();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<stockInfoList.size(); i++){
            String code = stockInfoList.get(i).getCode();
            if(code.startsWith("6")){
                sb.append("sh");
            }else{
                sb.append("sz");
            }
            sb.append(code);
            sb.append(",");
        }
        String codes = sb.toString().substring(0, sb.length()-1);//"sh601006,sh600825,sz300291";
        //String codes = "sh601006,sh600825,sz300291";
        long start = System.currentTimeMillis();
        //获取实时数据
        String response = restTemplate.getForObject(url+codes, String.class);
        log.info("获取实时数据时间："+(System.currentTimeMillis()-start)/1000);
        //String response = restTemplate.getForObject("http://hq.sinajs.cn/list=sh601163,sz399102,sz300290,sz002170", String.class);
        //数据组装
        String[] result = response.split("\n");
        for(int i=0; i<result.length; i++){
            String[] stockData = result[i].split(",");
            if(stockData != null && stockData.length>0){
                NumberFormat numberFormat = NumberFormat.getInstance();
                StockInfo stockInfo = new StockInfo();
                String nameStr = stockData[0];
                stockInfo.setCode(nameStr.substring(13, 19));
                stockInfo.setName(nameStr.substring(21, nameStr.length()));
                String realtimePrice = stockData[3];
                String buyPrice = stockInfoList.get(i).getBuyPrice();
                Float realtimePriceDouble = Float.parseFloat(realtimePrice);
                if(buyPrice==null || "".equals(buyPrice)){
                    buyPrice = "1000";
                }
                Float buyPriceDouble = Float.parseFloat(buyPrice);
                stockInfo.setRealtimePrice(numberFormat.format(realtimePriceDouble));
                stockInfo.setBuyPrice(numberFormat.format(buyPriceDouble));
                numberFormat.setMaximumFractionDigits(2);
                numberFormat.setMinimumFractionDigits(2);
                if(realtimePriceDouble!=0){
                    String buyRate = numberFormat.format((realtimePriceDouble - buyPriceDouble) /  realtimePriceDouble * 100) + "%";
                    if(buyRate.length()<6){
                        buyRate = "0" + buyRate;
                    }
                    stockInfo.setBuyRate(buyRate);
                }else{
                    stockInfo.setBuyRate("0");
                }
                stockInfo.setRealtimeDate(stockData[30]+" "+stockData[31]);
                stockInfo.setDescription(stockInfoList.get(i).getDescription()==null?"":stockInfoList.get(i).getDescription());
                viewList.add(stockInfo);
            }
        }
        log.info("成功获取实时数据。");
        map.put("viewList", viewList);
        return new ModelAndView("/stock/index", "map", map);
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
        stockService.update(tdIndex, code, value);
        log.info("code:{}, tdIndex:{}, value:{}", code, tdIndex, value);
    }
}

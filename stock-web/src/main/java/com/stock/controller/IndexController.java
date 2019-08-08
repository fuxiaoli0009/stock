package com.stock.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.enums.StockTypeEnum;
import com.stock.service.impl.RemoteDataServiceImpl;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api")
public class IndexController {
	
	private final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private Map<String, List<StockInfo>> map = new HashMap<String, List<StockInfo>>();
	
	public static String source = "0";
	
	@Autowired
	private RemoteDataServiceImpl remoteDataService;
	
	@ApiOperation(value = "切换", httpMethod = "GET")
    @RequestMapping(value = "/switch", method = RequestMethod.GET)
    public String switchSource(){
		try {
			if("0".equals(IndexController.source)) {
				IndexController.source = "1";
				RemoteDataServiceImpl.source = "1";
			}else {
				IndexController.source = "0";
				RemoteDataServiceImpl.source = "0";
			}
		} catch (Exception e) {
			logger.error("切换渠道异常", e);
			return "切换渠道异常";
		}
		logger.info("切换渠道source:{}", IndexController.source);
		return "切换渠道成功";
		
	}
	
	@ApiOperation(value = "方式二", httpMethod = "POST")
    @RequestMapping(value = "/indexNew", method = RequestMethod.POST)
    public String indexNew(){
		
		//上证指数 start
    	List<String> codes = new ArrayList<String>();
    	codes.add("000001");
    	codes.add("399006");
    	Map<String, RemoteDataInfo> remoteMap = remoteDataService.findSHZSRemoteDataInfoMap(StockTypeEnum.STOCK_STATUS_HS.getCode(), source, codes);
    	RemoteDataInfo remote = remoteMap.get("000001");
    	String szRatePercent = remote.getRatePercent();
    	remote = remoteMap.get("399006");
    	String czRatePercent = remote.getRatePercent();
    	//上证指数 end
    	
    	List<StockInfo> hsViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_HS.getCode(), source);
    	List<StockInfo> hkViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_HK.getCode(), source);
    	List<StockInfo> starViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STAR.getCode(), source);
    	List<StockInfo> chosenViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_CHOSEN.getCode(), source);
    	Collections.sort(hsViewStocks);
    	Collections.sort(hkViewStocks);
    	Collections.sort(starViewStocks);
    	
    	//key值可以优化为userId等
    	map.put("hsViewStocks", hsViewStocks);
    	
    	String hsAverageRatePercent = remoteDataService.calculateAverageRatePercent(hsViewStocks);
    	String chosenAverageRatePercent = remoteDataService.calculateAverageRatePercent(chosenViewStocks);
    	
    	//科创指数
    	Map<String, Object> maps = new HashMap<String, Object>();
    	List<Integer> starCloseIndexs = remoteDataService.getCloseIndexsByCode("688000");
    	if(remoteDataService.isTradingDayByStar()) {
    		String todayClosePrice = remoteDataService.getTodayIndex(StockTypeEnum.STOCK_STAR.getCode(), "688000");
    		if(todayClosePrice!=null) {
    			starCloseIndexs.add(Double.valueOf(todayClosePrice).intValue());
        		String starAverageRatePercent = remoteDataService.getTodayIndexRatePercent("688000", new BigDecimal(todayClosePrice));
        		maps.put("starAverageRatePercent", starAverageRatePercent);
    		}else {
    			maps.put("starAverageRatePercent", "0.00%");
    		}
    	}
    	
    	maps.put("hsStocks", hsViewStocks);
    	maps.put("hkStocks", hkViewStocks);
        maps.put("starStocks", starViewStocks);
        maps.put("hsAverageRatePercent", hsAverageRatePercent);
        maps.put("chosenAverageRatePercent", chosenAverageRatePercent);
        maps.put("szRatePercent", szRatePercent);
        maps.put("czRatePercent", czRatePercent);
        maps.put("starCloseIndexs", starCloseIndexs);
        maps.put("source", IndexController.source);
    	return JSON.toJSONString(maps);
    }
	
	
	@RequestMapping(value = "/getCodes", method = RequestMethod.POST)
	public String getCodes() {
		List<StockInfo> hsViewStocks = map.get("hsViewStocks");
		StringBuilder sb = new StringBuilder();
		for(StockInfo stockInfo : hsViewStocks) {
			sb.append(stockInfo.getCode());
			sb.append("<br/>");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String a = "2948.149";
		System.out.println(Double.valueOf(a).intValue());
	}

}

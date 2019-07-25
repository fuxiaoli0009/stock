package com.stock.controller;

import java.math.BigDecimal;
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
import org.springframework.web.servlet.ModelAndView;

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

	@Autowired
	private RemoteDataServiceImpl remoteDataService;
	
    @ApiOperation(value = "查询", httpMethod = "GET")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Map<String, Object> map, String source){
    	
    	//上证指数 start
    	String code = "000001";
    	Map<String, RemoteDataInfo> remoteMap = remoteDataService.findSpecialDataInfoMap(source, code);
    	RemoteDataInfo remote = remoteMap.get(code);
    	String szRatePercent = remote.getRatePercent();
    	//上证指数 end
    	
    	List<StockInfo> hsViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_HS.getCode(), source);
    	List<StockInfo> hkViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_HK.getCode(), source);
    	List<StockInfo> starViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STAR.getCode(), source);
    	Collections.sort(hsViewStocks);
    	Collections.sort(hkViewStocks);
    	Collections.sort(starViewStocks);
    	
    	String hsAverageRatePercent = remoteDataService.calculateAverageRatePercent(hsViewStocks);
    	String starAverageRatePercent = remoteDataService.calculateAverageRatePercent(starViewStocks);
    	map.put("hsStocks", hsViewStocks);
    	map.put("hkStocks", hkViewStocks);
        map.put("starStocks", starViewStocks);
        map.put("hsAverageRatePercent", hsAverageRatePercent);
        map.put("starAverageRatePercent", starAverageRatePercent);
        map.put("szRatePercent", szRatePercent);
    	return new ModelAndView("/index", "map", map);
    }

	@ApiOperation(value = "方式二", httpMethod = "POST")
    @RequestMapping(value = "/indexNew", method = RequestMethod.POST)
    public String indexNew(String source){
		
		//上证指数 start
    	String code = "000001";
    	Map<String, RemoteDataInfo> remoteMap = remoteDataService.findSpecialDataInfoMap(source, code);
    	RemoteDataInfo remote = remoteMap.get(code);
    	String szRatePercent = remote.getRatePercent();
    	//上证指数 end
    	
    	List<StockInfo> hsViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_HS.getCode(), source);
    	List<StockInfo> hkViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_HK.getCode(), source);
    	List<StockInfo> starViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STAR.getCode(), source);
    	Collections.sort(hsViewStocks);
    	Collections.sort(hkViewStocks);
    	Collections.sort(starViewStocks);
    	
    	String hsAverageRatePercent = remoteDataService.calculateAverageRatePercent(hsViewStocks);
    	String starAverageRatePercent = remoteDataService.calculateAverageRatePercent(starViewStocks);
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("hsStocks", hsViewStocks);
    	map.put("hkStocks", hkViewStocks);
        map.put("starStocks", starViewStocks);
        map.put("hsAverageRatePercent", hsAverageRatePercent);
        map.put("starAverageRatePercent", starAverageRatePercent);
        map.put("szRatePercent", szRatePercent);
    	return JSON.toJSONString(map);
    }
    
}

package com.stock.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
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
    	
    	List<StockInfo> hsViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_HS.getCode(), source);
    	List<StockInfo> hkViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STATUS_HK.getCode(), source);
    	List<StockInfo> starViewStocks = remoteDataService.findStocksByType(StockTypeEnum.STOCK_STAR.getCode(), source);
    	Collections.sort(hsViewStocks);
    	Collections.sort(hkViewStocks);
    	Collections.sort(starViewStocks);
    	map.put("hsStocks", hsViewStocks);
    	map.put("hkStocks", hkViewStocks);
        map.put("starStocks", starViewStocks);
    	return new ModelAndView("/index", "map", map);
    }
    
}

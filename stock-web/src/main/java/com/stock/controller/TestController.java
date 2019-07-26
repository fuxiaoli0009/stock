package com.stock.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbStock;
import com.stock.service.SinaApiService;
import com.stock.service.StockService;
import com.stock.service.TencentApiService;
import com.stock.service.impl.RemoteDataServiceImpl;

@RestController
@RequestMapping(value="api")
public class TestController {
	
	@Autowired
	private SinaApiService sinaApiService;
	
	@Autowired
	private TencentApiService tencentApiService;
	
	@Autowired
	private RemoteDataServiceImpl remoteDataService;
	
	@Autowired
	private StockService stockService;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public void test() {
		List<TbStock> hsStocks = stockService.getStocksByType(StockTypeEnum.STOCK_STATUS_HS.getCode());
		String hsCodes = sinaApiService.getCodesFromStocks(hsStocks, StockTypeEnum.STOCK_STATUS_HS.getCode());
		Map<String, RemoteDataInfo> remoteDataInfoMap = sinaApiService.getRealTimeInfoFromRemote(hsCodes);
		List<StockInfo> list = remoteDataService.assembleDatas(remoteDataInfoMap, hsStocks, StockTypeEnum.STOCK_STATUS_HS.getCode());
		for(int i=0; i<list.size(); i++) {
			StockInfo stock = list.get(i);
			System.out.println(stock.getCode()+" " + stock.getName()+" "+stock.getRealTimePrice()+" "+stock.getRatePercent()+" "+stock.getBuyPrice()+" "+stock.getBuyRate()+" "+stock.getDescription());
		}
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Boolean isTradingDay() {
		List<String> codes = new ArrayList<String>();
    	codes.add("000001");
    	codes.add("000300");
    	codes.add("000016");
    	codes.add("000905");
    	codes.add("000009");
    	codes.add("000903");
    	codes.add("000906");
    	Map<String, RemoteDataInfo> remoteMap = remoteDataService.findSHZSRemoteDataInfoMap(null, "1", codes);
    	int i=0;
    	for(RemoteDataInfo remoteDataInfo : remoteMap.values()) {
    		System.out.println(remoteDataInfo.getRatePercent());
    		if("0.00%".equals(remoteDataInfo.getRatePercent())) {
    			i++;
    		}
    	}
    	if(i>=3) {
    		return false;
    	}
		return true;
	}
}

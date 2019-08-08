package com.stock.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.stock.model.TbHistoryData;
import com.stock.model.TbHistoryDataExample;
import com.stock.model.TbStock;
import com.stock.repository.TbHistoryDataMapper;
import com.stock.service.HistoryDataService;
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
	private RemoteDataServiceImpl remoteDataService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private HistoryDataService historyDataService;
	
	@Autowired
	private TbHistoryDataMapper tbHistoryDataMapper;

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
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Boolean isTradingDayByStar() {
		List<String> codes = new ArrayList<String>();
    	codes.add("688001");
    	codes.add("688002");
    	codes.add("688003");
    	codes.add("688005");
    	codes.add("688006");
    	codes.add("688007");
    	codes.add("688008");
    	Map<String, RemoteDataInfo> remoteMap = remoteDataService.findSHZSRemoteDataInfoMap(null, "1", codes);
    	int i=0;
    	for(RemoteDataInfo remoteDataInfo : remoteMap.values()) {
    		TbHistoryData tbHistoryData = historyDataService.selectByCode(remoteDataInfo.getCode());
    		if(tbHistoryData!=null) {
    			if(tbHistoryData.getCloseRatePercent().equals(remoteDataInfo.getRatePercent())) {
    				i++;
    			}
    		}
    	}
    	if(i>=3) {
    		return false;
    	}
		return true;
	}
	
	@RequestMapping(value = "/updateHistoryData", method = RequestMethod.POST)
	public void updateHistoryData() {
		
		List<String> codes = new ArrayList<String>();
		codes.add("688388");
		for(String code : codes) {
			TbHistoryDataExample example = new TbHistoryDataExample();
			example.or().andCodeEqualTo(code).andClosePriceNotEqualTo(new BigDecimal(0));
			List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
			if(tbHistoryDataList!=null && tbHistoryDataList.size()>0) {//更新
				TbHistoryData tbHistoryData = tbHistoryDataList.get(0);
				String closeRatePercent = tbHistoryData.getCloseRatePercent();
				BigDecimal closeRateDecimal = new BigDecimal(closeRatePercent.replace("%", ""));
				BigDecimal cal = new BigDecimal(100);
				BigDecimal lastCloseValue = new BigDecimal(1000);
				BigDecimal closeValue = cal.add(closeRateDecimal).multiply(lastCloseValue).divide(cal, 3, BigDecimal.ROUND_HALF_DOWN);
				tbHistoryData.setUpdateTime(new Date());
				tbHistoryData.setDescription(closeValue.toString());
				tbHistoryDataMapper.updateByPrimaryKeySelective(tbHistoryData);
				for(int i=1; i<tbHistoryDataList.size(); i++) {
					TbHistoryData historyData = tbHistoryDataList.get(i);
					closeRateDecimal = new BigDecimal(historyData.getCloseRatePercent().replace("%", ""));
					lastCloseValue = new BigDecimal(tbHistoryDataList.get(i-1).getDescription());
					closeValue = cal.add(closeRateDecimal).multiply(lastCloseValue).divide(cal, 3, BigDecimal.ROUND_HALF_DOWN);
					historyData.setUpdateTime(new Date());
					historyData.setDescription(closeValue.toString());
					tbHistoryDataMapper.updateByPrimaryKeySelective(historyData);
				}
			}
		}
		
	}
	
	@RequestMapping(value = "/updateHistoryIndex", method = RequestMethod.POST)
	public void updateHistoryIndex() {
		
		List<String> closeDates = new ArrayList<String>();
		closeDates.add("2019-07-22");
		closeDates.add("2019-07-23");
		closeDates.add("2019-07-24");
		closeDates.add("2019-07-25");
		closeDates.add("2019-07-26");
		
		closeDates.add("2019-07-29");
		closeDates.add("2019-07-30");
		closeDates.add("2019-07-31");
		closeDates.add("2019-08-01");
		closeDates.add("2019-08-02");
		
		closeDates.add("2019-08-05");
		closeDates.add("2019-08-06");
		closeDates.add("2019-08-07");
		
		for(int m=0; m<closeDates.size(); m++) {
			String closeDate = closeDates.get(m);
			TbHistoryDataExample example = new TbHistoryDataExample();
			example.or().andCodeNotEqualTo("688000").andCodeNotEqualTo("900001").andClosePriceNotEqualTo(new BigDecimal(0)).andCloseRatePercentNotEqualTo("0.00%").andCloseDateEqualTo(closeDate);
			List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
			if(tbHistoryDataList!=null && tbHistoryDataList.size()>0) {//更新
				BigDecimal result = new BigDecimal(0);
				for(int i=0; i<tbHistoryDataList.size(); i++) {
					TbHistoryData tbHistoryData = tbHistoryDataList.get(i);
					result = result.add(new BigDecimal(tbHistoryData.getDescription())); 
				}
				BigDecimal closeIndex = result.divide(new BigDecimal(tbHistoryDataList.size()), 3, BigDecimal.ROUND_HALF_DOWN);
				System.out.println(closeDate + " " +closeIndex + " " + tbHistoryDataList.size());
			}
		}
	}
		
}

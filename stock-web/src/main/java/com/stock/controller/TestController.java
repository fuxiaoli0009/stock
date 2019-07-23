package com.stock.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="test")
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
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		list.add("2.73%");
		list.add("3.74%");
		list.add("4.03%");
		list.add("1.73%");
		
		BigDecimal sum = new BigDecimal(0);
		int staticNums = 0;
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).contains("%")) {
				sum = sum.add(new BigDecimal(list.get(i).replace("%", "")));
				staticNums++;
			}
		}	
		if(staticNums > 0) {
			BigDecimal result = sum.divide(new BigDecimal(staticNums), 3,BigDecimal.ROUND_HALF_DOWN);
			System.out.println(result.toString()+"");
		}	
	}
}

package com.stock.controller;

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

import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbStock;
import com.stock.service.SinaApiService;
import com.stock.service.StockService;
import com.stock.service.TencentApiService;
import com.stock.service.impl.RemoteDataServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api")
public class IndexController {
	
	private final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private TencentApiService tencentApiService;
	
	@Autowired
	private SinaApiService sinaApiService;
	
	@Autowired
	private RemoteDataServiceImpl remoteDataService;
	
	@Autowired
	private StockService stockService;

    @ApiOperation(value = "查询", httpMethod = "GET")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Map<String, Object> map, String source){
    	
    	List<StockInfo> hsViewStocks = findStocksByType(StockTypeEnum.STOCK_STATUS_HS.getCode(), source);
    	List<StockInfo> hkViewStocks = findStocksByType(StockTypeEnum.STOCK_STATUS_HK.getCode(), source);
    	List<StockInfo> starViewStocks = findStocksByType(StockTypeEnum.STOCK_STAR.getCode(), source);
    	Collections.sort(hsViewStocks);
    	Collections.sort(hkViewStocks);
    	Collections.sort(starViewStocks);
    	map.put("hsStocks", hsViewStocks);
    	map.put("hkStocks", hkViewStocks);
        map.put("starStocks", starViewStocks);
    	return new ModelAndView("/index", "map", map);
    }
    
    private List<StockInfo> findStocksByType(String type, String source) {
    	Map<String, RemoteDataInfo> remoteDataInfoMap = new HashMap<String, RemoteDataInfo>();
    	List<TbStock> tbStocks = stockService.getStocksByType(type);
    	//获取不通渠道数据
    	if("1".equals(source)) {
    		remoteDataInfoMap = this.tencentSource(tbStocks, type);
    	} else {
    		remoteDataInfoMap = this.sinaSource(tbStocks, type);
    	}
    	return remoteDataService.assembleDatas(remoteDataInfoMap, tbStocks, type);
	}

    /**
     * 获取Tencent模板数据
     * @param tbStocks
     * @param type
     * @return
     */
	public Map<String, RemoteDataInfo> tencentSource(List<TbStock> tbStocks, String type) {
		String codes = tencentApiService.getCodesFromStocks(tbStocks, type);
		return tencentApiService.getRealTimeInfoFromRemote(codes);
    }
	
	/**
     * 获取Sina模板数据
     * @param tbStocks
     * @param type
     * @return
     */
	public Map<String, RemoteDataInfo> sinaSource(List<TbStock> tbStocks, String type) {
		String codes = sinaApiService.getCodesFromStocks(tbStocks, type);
		return sinaApiService.getRealTimeInfoFromRemote(codes);
    }
    
}

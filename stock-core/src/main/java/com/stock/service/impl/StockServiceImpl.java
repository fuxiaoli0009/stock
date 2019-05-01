package com.stock.service.impl;

import com.stock.dataobject.StockInfo;
import com.stock.enums.StockStatusEnum;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbStock;
import com.stock.model.TbStockExample;
import com.stock.repository.TbStockMapper;
import com.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class StockServiceImpl implements StockService, InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
	
	private List<TbStock> hsStocks = new ArrayList<TbStock>();
	
	private List<TbStock> hkStocks = new ArrayList<TbStock>();
	
    @Autowired
    private TbStockMapper tbStockMapper;
    
    @Override
	public void afterPropertiesSet() throws Exception {
    	
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    	executor.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				hsStocks = updateStockCache(StockTypeEnum.STOCK_STATUS_HS.getCode());
				hkStocks = updateStockCache(StockTypeEnum.STOCK_STATUS_HK.getCode());
			}
    		
    	}, 0, 5, TimeUnit.MINUTES);
	}
    
    protected List<TbStock> updateStockCache(String typeCode) {
    	try {
    		logger.info("Start to get newest stocks info.");
    		TbStockExample example = new TbStockExample();
    		example.createCriteria().andStatusEqualTo(StockStatusEnum.STOCK_STATUS_INIT.getCode()).andTypeEqualTo(typeCode);
    		return tbStockMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("Exec updateStockCache error.");
			e.printStackTrace();
		}
    	return null;
    }

	@Override
	public List<TbStock> getStocksByType(String typeCode) {
		return StockTypeEnum.STOCK_STATUS_HS.getCode().equals(typeCode) ? hsStocks : hkStocks;
	}
	
	public List<TbStock> getHsStocks() {
		return hsStocks;
	}

	public void setHsStocks(List<TbStock> hsStocks) {
		this.hsStocks = hsStocks;
	}

	public List<TbStock> getHkStocks() {
		return hkStocks;
	}

	public void setHkStocks(List<TbStock> hkStocks) {
		this.hkStocks = hkStocks;
	}


	/*
	 * public void update(String tdIndex, String code, String value){ StockInfo
	 * stock = stockRepository.findByCode(code); if("5".equals(tdIndex)){
	 * stock.setBuyPrice(Double.parseDouble(value)); } if("9".equals(tdIndex)){
	 * stock.setDescription(value); } stockRepository.save(stock); }
	 * 
	 * public void delete(String code){ StockInfo stockInfo =
	 * stockRepository.findByCode(code); if(stockInfo!=null){
	 * stockRepository.delete(stockInfo); } }
	 * 
	 * public String add(StockInfo stockInfo){ StockInfo stock =
	 * stockRepository.findByCode(stockInfo.getCode()); if(stock==null){
	 * stockInfo.setSellPrice(0.00); stockInfo.setMinValue(0.00);
	 * stockRepository.save(stockInfo); } return null; }
	 */

}

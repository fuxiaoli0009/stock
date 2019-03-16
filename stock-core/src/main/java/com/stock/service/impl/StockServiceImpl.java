package com.stock.service.impl;

import com.stock.dataobject.StockInfo;
import com.stock.repository.StockRepository;
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

/**
 * @Author: fuxiaoli
 * @Date: 2018/4/3
 **/
@Service
@Slf4j
public class StockServiceImpl implements StockService, InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
	
	private List<StockInfo> stockInfoList = new ArrayList<StockInfo>();
	
    @Autowired
    private StockRepository stockRepository;
    
    @Override
	public void afterPropertiesSet() throws Exception {
    	
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    	executor.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				updateStockCache();
			}
    		
    	}, 0, 5, TimeUnit.MINUTES);
	}
    
    protected void updateStockCache() {
    	try {
    		logger.info("Start to get newest stocks info.");
			stockInfoList = findAll();
		} catch (Exception e) {
			logger.error("Exec updateStockCache error.");
			e.printStackTrace();
		}
    } 

	@Override
	public List<StockInfo> getStockList() {
		return stockInfoList;
	}

    public List<StockInfo> findAll(){
        return stockRepository.findAll();
    }

    public void update(String tdIndex, String code, String value){
        StockInfo stock = stockRepository.findByCode(code);
        if("5".equals(tdIndex)){
            stock.setBuyPrice(Double.parseDouble(value));
        }
        if("9".equals(tdIndex)){
            stock.setDescription(value);
        }
        stockRepository.save(stock);
    }

    public void delete(String code){
        StockInfo stockInfo = stockRepository.findByCode(code);
        if(stockInfo!=null){
            stockRepository.delete(stockInfo);
        }
    }

    public String add(StockInfo stockInfo){
        StockInfo stock = stockRepository.findByCode(stockInfo.getCode());
        if(stock==null){
            stockInfo.setSellPrice(0.00);
            stockInfo.setMinValue(0.00);
            stockRepository.save(stockInfo);
        }
        return null;
    }

}

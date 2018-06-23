package com.stock.service.impl;

import com.stock.dataobject.StockInfo;
import com.stock.repository.StockRepository;
import com.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: fuxiaoli
 * @Date: 2018/4/3
 **/
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

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
            stockInfo.setBuyPrice(1.00);
            stockInfo.setSellPrice(0.00);
            stockRepository.save(stockInfo);
        }
        return null;
    }
}

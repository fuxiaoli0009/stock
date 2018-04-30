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

    public void update(String code, String buyPrice){
        StockInfo stock = stockRepository.findByCode(code);
        stock.setBuyPrice(buyPrice);
        stockRepository.save(stock);
    }
}

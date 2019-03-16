package com.stock.service;

import com.stock.dataobject.StockInfo;

import java.util.List;

/**
 * @Author: fuxiaoli
 * @Date: 2018/4/3
 **/
public interface StockService {

    /**
     * 查询自选股
     * @return
     */
    public List<StockInfo> findAll();

    public void update(String tdIndex, String code, String value);

    public void delete(String code);

    public String add(StockInfo stockInfo);

	public List<StockInfo> getStockList();
	
}

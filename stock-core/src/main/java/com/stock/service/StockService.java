package com.stock.service;

import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.model.TbStock;
import com.stock.model.TbStockExample;

import java.util.List;

public interface StockService {
	
	public List<TbStock> getStocksByType(String typeCode);
	
	public void update(String tdIndex, String code, String value);
	
	public void delete(String code);
	
	public void addStock(TbStock tbStock);
	
	List<TbStock> selectByExample(TbStockExample example);
	
	public int updateByExampleSelective(TbStock record, TbStockExample example);
	
	int insertSelective(TbStock record);
	
}

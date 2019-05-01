package com.stock.service;

import com.stock.model.TbStock;
import java.util.List;

public interface StockService {
	
	public List<TbStock> getStocksByType(String typeCode);
	
	public void update(String tdIndex, String code, String value);
	
	public void delete(String code);
	
	public void addStock(TbStock tbStock);
	
}

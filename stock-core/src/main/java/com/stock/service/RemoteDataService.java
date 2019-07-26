package com.stock.service;

import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.model.TbStock;

import java.util.List;
import java.util.Map;

public interface RemoteDataService {
	
	public Map<String, RemoteDataInfo> findRemoteDataInfoMap(String type, String source, List<TbStock> tbStocks);
	
	public List<StockInfo> assembleDatas(Map<String, RemoteDataInfo> remoteDataInfoMap, List<TbStock> tbStocks, String typeCode);
	
	public Map<String, RemoteDataInfo> findSpecialDataInfoMap(String source, String code);
	
	public String calculateAverageRatePercent(List<StockInfo> stocks);
	
	/**
	 * 获取上海指数数据
	 * @param type
	 * @param source
	 * @param codes
	 * @return
	 */
	public Map<String, RemoteDataInfo> findSHZSRemoteDataInfoMap(String type, String source, List<String> codes);
	
	/**
	 * 判断是否为交易日
	 * @return
	 */
	public Boolean isTradingDay();
	
	/**
	 * 获取指数收盘信息
	 * @param string
	 * @return
	 */
	public List<Integer> getCloseIndexsByCode(String code);
}

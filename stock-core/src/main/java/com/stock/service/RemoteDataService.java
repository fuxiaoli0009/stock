package com.stock.service;

import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.model.TbStock;

import java.util.List;
import java.util.Map;

public interface RemoteDataService {
	
	public Map<String, RemoteDataInfo> findRemoteDataInfoMap(String type, String source, List<TbStock> tbStocks);
	
	public List<StockInfo> assembleDatas(Map<String, RemoteDataInfo> remoteDataInfoMap, List<TbStock> tbStocks, String typeCode);
	
}

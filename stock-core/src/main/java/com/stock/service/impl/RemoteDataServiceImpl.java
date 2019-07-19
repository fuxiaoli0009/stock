package com.stock.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.model.TbStock;
import com.stock.service.RemoteDataService;

@Service
public class RemoteDataServiceImpl implements RemoteDataService {

	private static final Logger logger = LoggerFactory.getLogger(RemoteDataServiceImpl.class);
	
	private final static String PBPEUrl = "https://www.jisilu.cn/data/stock/";
	
	/**
	 * 通用方法：将模板数据封装为前端展示数据
	 * tbStocks:表数据; remoteDataInfos:根据表数据查询到远程数据
	 * viewList:返回前端封装
	 */
	@Override
	public List<StockInfo> assembleDatas(Map<String, RemoteDataInfo> remoteDataInfoMap, List<TbStock> tbStocks, String typeCode) {
		
		List<StockInfo> viewList = new ArrayList<StockInfo>();
		for(int i=0; i<tbStocks.size(); i++) {
			try {
				TbStock tbStock = tbStocks.get(i);//表对象
				String code = tbStock.getCode();
				RemoteDataInfo remote = remoteDataInfoMap.get(code);//远程对象
				StockInfo stockInfo = new StockInfo();//组装对象
				stockInfo.setCode(code);
				stockInfo.setName(tbStock.getName());
				stockInfo.setPBPEUrl(RemoteDataServiceImpl.PBPEUrl + code);
				stockInfo.setRealTimePrice(-1D);
				Double realTimePrice = 100D;
				if(remote!=null) {
					stockInfo.setName(remote.getName());
					//实时价格
					realTimePrice = remote.getRealTimePrice()==0D?100D:remote.getRealTimePrice();
					stockInfo.setRealTimePrice(realTimePrice);
					stockInfo.setRatePercent(remote.getRatePercent());
				}else {
					logger.info("code:{}, 远程数据对象为空", code);
				}
				Double buyPrice = tbStock.getBuyPrice()==null?0.5D:tbStock.getBuyPrice().doubleValue();
				stockInfo.setBuyPrice(buyPrice);
				//买入还差百分之几
				NumberFormat nf = NumberFormat.getPercentInstance();
				nf.setMinimumFractionDigits(2);
				BigDecimal b1 = new BigDecimal(Double.toString(realTimePrice - buyPrice));
				BigDecimal b2 = new BigDecimal(Double.toString(realTimePrice));
				Double buyRate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
				stockInfo.setBuyRateDouble(buyRate);
				stockInfo.setBuyRate(nf.format(buyRate));
				//最高点已跌百分比
				Double maxValue = tbStock.getMaxValue()==null?200D:tbStock.getMaxValue().doubleValue();
				b1 = new BigDecimal(Double.toString(maxValue - realTimePrice));
				b2 = new BigDecimal(Double.toString(maxValue));
				Double maxRate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
				stockInfo.setMaxRate(nf.format(maxRate));
				stockInfo.setDescription(tbStock.getDescription()==null?"":tbStock.getDescription());
				
				viewList.add(stockInfo);
			} catch (Exception e) {
				logger.error("根据远程数据组装展示数据方法异常:", e);
			}
		}
		return viewList;
	}
	
}

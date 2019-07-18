package com.stock.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbStock;
import com.stock.service.RemoteDataService;

@Service
public class RemoteDataServiceImpl implements RemoteDataService {

	private static final Logger logger = LoggerFactory.getLogger(RemoteDataServiceImpl.class);
	
	private final static String PBPEUrl = "https://www.jisilu.cn/data/stock/";
	
	/**
	 * 通用方法
	 * tbStocks:表数据; remoteDataInfos:根据表数据查询到远程数据
	 */
	@Override
	public List<StockInfo> assembleDatas(Map<String, RemoteDataInfo> remoteDataInfoMap, List<TbStock> tbStocks, String typeCode) {
		
		List<StockInfo> viewList = new ArrayList<StockInfo>();
		for(int i=0; i<tbStocks.size(); i++) {
			TbStock tbStock = tbStocks.get(i);//表对象
			String code = tbStock.getCode();
			RemoteDataInfo remote = remoteDataInfoMap.get(code);//远程对象
			StockInfo stockInfo = new StockInfo();//组装对象
			stockInfo.setCode(code);
			stockInfo.setName(tbStock.getName());
			stockInfo.setPBPEUrl(RemoteDataServiceImpl.PBPEUrl + code);
			stockInfo.setRealTimePrice(-1D);
			if(remote!=null) {
				stockInfo.setName(remote.getName());
				//实时价格
				if(remote!=null) {
					stockInfo.setRealTimePrice(remote.getRealTimePrice());
				}
			}else {
				
			}
			
			
		}
		
					    
	    Double yesterdayPrice = 0.0;
	    Double realTimePrice = 0.0;
	    if(StockTypeEnum.STOCK_STATUS_HS.getCode().equals(typeCode) || StockTypeEnum.STOCK_STAR.getCode().equals(typeCode)) {
	    	String nameStr = stockData[0];
	        stockInfo.setName(nameStr.substring(21, nameStr.length()));//名称
	        realTimePrice = stockData[3].startsWith("0.0")?200.00:Double.parseDouble(stockData[3]);
	        stockInfo.setRealTimePrice(realTimePrice);//实时价格
	        yesterdayPrice = stockData[2].startsWith("0.0")?realTimePrice:Double.parseDouble(stockData[2]);//昨天收盘价
	    }else if(StockTypeEnum.STOCK_STATUS_HK.getCode().equals(typeCode)) {
	    	stockInfo.setName(stockData[1]);
	    	realTimePrice = stockData[3].startsWith("0.00")?200.00:Double.parseDouble(stockData[6]);
	    	stockInfo.setRealTimePrice(realTimePrice);
	    	yesterdayPrice = stockData[3].startsWith("0.00")?realTimePrice:Double.parseDouble(stockData[3]);
	    }
	    
	    //今日涨跌及百分比
	    BigDecimal b1 = new BigDecimal(Double.toString(realTimePrice - yesterdayPrice));  
		BigDecimal b2 = new BigDecimal(Double.toString(yesterdayPrice));
		Double rate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(2); //小数点前保留几位
		nf.setMinimumFractionDigits(2);//小数点后保留几位
		stockInfo.setRatePercent(nf.format(rate));
	    
	    //买入价格
		Double buyPrice = stock.getBuyPrice()==null?1.0:stock.getBuyPrice().doubleValue();
	    stockInfo.setBuyPrice(buyPrice);
	    //买入还差百分之几
	    b1 = new BigDecimal(Double.toString(realTimePrice - buyPrice));
	    b2 = new BigDecimal(Double.toString(realTimePrice));
	    Double buyRate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
	    stockInfo.setBuyRateDouble(buyRate);
	    stockInfo.setBuyRate(nf.format(buyRate));
	    
	    //最高点已跌百分比
	    Double maxValue = stock.getMaxValue()==null?200.0:stock.getMaxValue().doubleValue();
	    b1 = new BigDecimal(Double.toString(maxValue - realTimePrice));
	    b2 = new BigDecimal(Double.toString(maxValue));
	    Double maxRate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
	    stockInfo.setMaxRate(nf.format(maxRate));
	    
	    stockInfo.setDescription(stock.getDescription()==null?"":stock.getDescription());
	    
	    viewList.add(stockInfo);
					}
				} catch (Exception e) {
					logger.error("数据组装异常, 股票信息:{}, 异常信息:", JSON.toJSONString(stock), e);
				}
            }
        }
		return null;
	}

}

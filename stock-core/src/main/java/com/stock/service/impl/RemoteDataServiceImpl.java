package com.stock.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stock.dataobject.RemoteDataInfo;
import com.stock.dataobject.StockInfo;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbHistoryData;
import com.stock.model.TbHistoryDataExample;
import com.stock.model.TbStock;
import com.stock.repository.TbHistoryDataMapper;
import com.stock.service.HistoryDataService;
import com.stock.service.RemoteDataService;
import com.stock.service.SinaApiService;
import com.stock.service.StockService;
import com.stock.service.TencentApiService;
import com.stock.service.WarningInfoService;

@Service
public class RemoteDataServiceImpl implements RemoteDataService {

	private static final Logger logger = LoggerFactory.getLogger(RemoteDataServiceImpl.class);
	
	private final static String PBPEUrl = "https://www.jisilu.cn/data/stock/";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String source = "0";
	
	@Autowired
	private TencentApiService tencentApiService;
	
	@Autowired
	private SinaApiService sinaApiService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private TbHistoryDataMapper tbHistoryDataMapper;
	
	@Autowired
	private HistoryDataService historyDataService;
	
	@Autowired
	private WarningInfoService warningInfoService;
	
	public List<StockInfo> findStocksByType(String type, String source) {
    	List<TbStock> tbStocks = stockService.getStocksByType(type);
    	if(tbStocks!=null&&tbStocks.size()>0) {
    		Map<String, RemoteDataInfo> remoteDataInfoMap = this.findRemoteDataInfoMap(type, tbStocks);
        	return this.assembleDatas(remoteDataInfoMap, tbStocks, type);
    	}
    	return null;
	}
	
	public Map<String, RemoteDataInfo> findRemoteDataInfoMap(String type, List<TbStock> tbStocks) {
    	//获取不同渠道数据
    	if("1".equals(source)) {
    		return this.tencentSource(tbStocks, type);
    	} else {
    		return this.sinaSource(tbStocks, type);
    	}
	}
	
	//type字段区分各地区的，赞没用到，只计算上海
	public Map<String, RemoteDataInfo> findSHZSRemoteDataInfoMap(String type, String source, List<String> codes) {
    	//获取不同渠道数据
    	if("1".equals(source)) {
    		return this.tencentSHZSSource(codes, type);
    	} else {
    		return this.sinaSHZSSource(codes, type);
    	}
	}
	
    /**
     * 获取Tencent模板数据
     * @param tbStocks
     * @param type
     * @return
     */
	public Map<String, RemoteDataInfo> tencentSource(List<TbStock> tbStocks, String type) {
		String codes = tencentApiService.getCodesFromStocks(tbStocks, type);
		return tencentApiService.getRealTimeInfoFromRemote(codes);
    }
	
	/**
     * 获取Sina模板数据
     * @param tbStocks
     * @param type
     * @return
     */
	public Map<String, RemoteDataInfo> sinaSource(List<TbStock> tbStocks, String type) {
		String codes = sinaApiService.getCodesFromStocks(tbStocks, type);
		return sinaApiService.getRealTimeInfoFromRemote(codes);
    }
	
	/**
     * 获取Tencent上海指数数据
     * @param tbStocks
     * @param type
     * @return
     */
	public Map<String, RemoteDataInfo> tencentSHZSSource(List<String> tbStocks, String type) {
		String codes = tencentApiService.getCodesFromSHZSCodes(tbStocks, type);
		return tencentApiService.getRealTimeInfoFromRemote(codes.toString());
    }
	
	/**
     * 获取Sina上海指数数据
     * @param tbStocks
     * @param type
     * @return
     */
	public Map<String, RemoteDataInfo> sinaSHZSSource(List<String> tbStocks, String type) {
		String codes = sinaApiService.getCodesFromSHZSCodes(tbStocks, type);
		return sinaApiService.getRealTimeInfoFromRemote(codes);
    }
	
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
				}else {
					logger.info("code:{}, 远程数据对象为空", code);
					warningInfoService.saveWarningInfo("RemoteDataService", "code:"+code+", 远程数据对象为空");
				}
			} catch (Exception e) {
				logger.error("根据远程数据组装展示数据方法异常:", e);
				warningInfoService.saveWarningInfo("RemoteDataService", "根据远程数据组装展示数据方法异常,code:"+tbStocks.get(i).getCode()+"."+e);
			}
		}
		return viewList;
	}
	
	//000001有两个：上证指数和平安银行
	public Map<String, RemoteDataInfo> findSpecialDataInfoMap(String source, String code){
		if("1".equals(source)) {
    		return tencentApiService.getRealTimeInfoFromRemote("s_sh" + code);
    	} else {
    		return sinaApiService.getRealTimeInfoFromRemote("sh" + code);
    	}
	}

	public String calculateAverageRatePercent(List<StockInfo> stocks) {
		try {
			if(stocks!=null && stocks.size()>0) {
				BigDecimal sum = new BigDecimal(0);
				int staticNums = 0;
				for(int i=0; i<stocks.size(); i++) {
					if(stocks.get(i).getRatePercent().contains("%") && stocks.get(i).getRealTimePrice()>0) {
						sum = sum.add(new BigDecimal(stocks.get(i).getRatePercent().replace("%", "")));
						staticNums++;
					}
				}	
				if(staticNums > 0) {
					BigDecimal result = sum.divide(new BigDecimal(staticNums),3,BigDecimal.ROUND_HALF_DOWN);
					logger.info("sum:{}, staticNums:{}, result:{}", sum, staticNums, result.toString()+"%");
					return result.toString()+"%";
				}
			}
		} catch (Exception e) {
			logger.error("计算平均涨跌幅异常", e);
			warningInfoService.saveWarningInfo("RemoteDataService", "计算平均涨跌幅异常,stocks:"+stocks.toString()+"."+e);
		}
		return "0.00%";
	}
	
	public Boolean isTradingDay() {
		List<String> codes = new ArrayList<String>();
    	codes.add("000001");
    	codes.add("000300");
    	codes.add("000016");
    	codes.add("000905");
    	codes.add("000009");
    	codes.add("000903");
    	codes.add("000906");
    	Map<String, RemoteDataInfo> remoteMap = this.findSHZSRemoteDataInfoMap(null, source, codes);
    	int i=0;
    	for(RemoteDataInfo remoteDataInfo : remoteMap.values()) {
    		System.out.println(remoteDataInfo.getRatePercent());
    		if("0.00%".equals(remoteDataInfo.getRatePercent())) {
    			i++;
    		}
    	}
    	if(i>=3) {
    		return false;
    	}
		return true;
	}
	
	public Boolean isTradingDayByStar() {
		try {
			List<TbStock> tbStocks = new ArrayList<TbStock>();
			TbStock tbStock = new TbStock();
			tbStock.setCode("688001");
			tbStocks.add(tbStock);
			tbStock = new TbStock();
			tbStock.setCode("688002");
			tbStocks.add(tbStock);
			tbStock = new TbStock();
			tbStock.setCode("688003");
			tbStocks.add(tbStock);
			tbStock = new TbStock();
			tbStock.setCode("688005");
			tbStocks.add(tbStock);
			tbStock = new TbStock();
			tbStock.setCode("688006");
			tbStocks.add(tbStock);
			tbStock = new TbStock();
			tbStock.setCode("688007");
			tbStocks.add(tbStock);
			tbStock = new TbStock();
			tbStock.setCode("688008");
			tbStocks.add(tbStock);
			Map<String, RemoteDataInfo> remoteMap = this.findRemoteDataInfoMap(StockTypeEnum.STOCK_STAR.getCode(), tbStocks);
			int i=0;
			for(RemoteDataInfo remoteDataInfo : remoteMap.values()) {
				TbHistoryData tbHistoryData = historyDataService.selectByCode(remoteDataInfo.getCode());
				if(tbHistoryData!=null) {
					if(tbHistoryData.getCloseRatePercent().equals(remoteDataInfo.getRatePercent())) {
						i++;
					}
				}
			}
			if(i>=4) {
				logger.info("{}:判断为非交易日.", new Date());
				return false;
			}
			logger.info("{}:判断为交易日.", new Date());
		} catch (Exception e) {
			logger.error("判断是否为交易日异常", e);
			warningInfoService.saveWarningInfo("RemoteDataService", "判断是否为交易日异常,"+e);
		}
		return true;
	}

	public List<Integer> getCloseIndexsByCode(String code) {
		return tbHistoryDataMapper.getCloseIndexsByCode(code);
	}
	
	public String calCloseValue(String code, String closeRatePercent) {
		
		BigDecimal closeValue = new BigDecimal(0);
		try {
			BigDecimal closeRateDecimal = new BigDecimal(closeRatePercent.replace("%", ""));
			BigDecimal cal = new BigDecimal(100);
			TbHistoryDataExample example = new TbHistoryDataExample();
			example.or().andCodeEqualTo(code).andCloseDateNotEqualTo(sdf.format(new Date()));
			List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
			if(tbHistoryDataList!=null&&tbHistoryDataList.size()>0) {
				TbHistoryData lastHistoryData = tbHistoryDataList.get(tbHistoryDataList.size()-1);
				BigDecimal lastCloseValue = new BigDecimal(lastHistoryData.getDescription());
				closeValue = cal.add(closeRateDecimal).multiply(lastCloseValue).divide(cal, 3, BigDecimal.ROUND_HALF_DOWN);
			} else {
				closeValue = cal.add(closeRateDecimal).multiply(new BigDecimal(1000)).divide(cal, 3, BigDecimal.ROUND_HALF_DOWN);
			}
		} catch (Exception e) {
			logger.error("code:{}, closeRatePercent:{}, 计算收盘值异常异常", code, closeRatePercent, e);
			warningInfoService.saveWarningInfo("RemoteDataService", "code:"+code+", closeRatePercent:"+closeRatePercent+", 计算收盘值异常异常,"+e);
		}
		return closeValue.toString();
	}
	
	public String getTodayIndex(String type, String calCode) {
		try {
			List<TbStock> tbStocks = stockService.getStocksByType(type);
			Map<String, RemoteDataInfo> remoteDataInfoMap = this.findRemoteDataInfoMap(type, tbStocks);
			BigDecimal sum = new BigDecimal(0);
			int staticNums = 0;
			for(RemoteDataInfo remoteDataInfo : remoteDataInfoMap.values()) {
				String code = remoteDataInfo.getCode();
				try {
					if(remoteDataInfo.getRealTimePrice()>0) {
						String closeRatePercent = remoteDataInfo.getRatePercent();
						String closeValue = this.calCloseValue(code, closeRatePercent);
						sum = sum.add(new BigDecimal(closeValue));
						staticNums++;
					}
				} catch (Exception e) {
					logger.error("code:{}, 查询数据失败.", code);
				}
			}
			logger.info("sum:{}, staticNums:{}.", sum, staticNums);
			if(staticNums > 0) {
				BigDecimal todayClosePrice = sum.divide(new BigDecimal(staticNums),3,BigDecimal.ROUND_HALF_DOWN);
				return todayClosePrice.toString();
			}
		} catch (Exception e) {
			logger.error("calCode:{}, 计算今日指数值异常.", calCode, e);
			warningInfoService.saveWarningInfo("RemoteDataService", "calCode:"+calCode+", 计算今日指数值异常."+e);
		}
		return null;
	}
	
	public String getTodayIndexRatePercent(String calCode, BigDecimal todayClosePrice) {
		try {
			TbHistoryDataExample example = new TbHistoryDataExample();
			example.or().andCodeEqualTo(calCode).andCloseDateNotEqualTo(sdf.format(new Date()));
			example.setOrderByClause("close_date desc");
			List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
			TbHistoryData tbHistoryData = tbHistoryDataList.get(0);
			BigDecimal lastClosePrice = tbHistoryData.getClosePrice();
			logger.info("{}获取今日价格, lastClosePrice:{}, todayClosePrice:{}", calCode, lastClosePrice, todayClosePrice);
			BigDecimal result = todayClosePrice.subtract(lastClosePrice).multiply(new BigDecimal(100)).divide(lastClosePrice, 3,BigDecimal.ROUND_HALF_DOWN);
			return result.toString()+"%";
		} catch (Exception e) {
			logger.error("{}获取今日价格异常", calCode, e);
			warningInfoService.saveWarningInfo("RemoteDataService", "calCode:"+calCode+", 获取今日价格异常."+e);
			return "0.00%";
		}
	}
	
}

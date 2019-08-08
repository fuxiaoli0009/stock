package com.stock.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stock.dataobject.RemoteDataInfo;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbHistoryData;
import com.stock.model.TbHistoryDataExample;
import com.stock.model.TbStock;
import com.stock.repository.TbHistoryDataMapper;
import com.stock.service.RemoteDataService;
import com.stock.service.StockService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JobHandler(value = "HistoryDataJobHandler")
@Component
public class HistoryDataJobHandler extends IJobHandler {
	
	private final Logger logger = LoggerFactory.getLogger(HistoryDataJobHandler.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private RemoteDataService remoteDataService;
	
	@Autowired
	private TbHistoryDataMapper tbHistoryDataMapper;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		String type = StockTypeEnum.STOCK_STAR.getCode();
		try {
			if(remoteDataService.isTradingDayByStar()) {
				String calCode = "688000";
				this.calAndSaveCloseDataInfo(type, calCode);
				type = StockTypeEnum.STOCK_STATUS_CHOSEN.getCode();
				calCode = "900001";
				this.calAndSaveCloseDataInfo(type, calCode);
			}
		} catch (Exception e) {
			logger.info("【记录收盘数据-类型{}】异常", type, e);
			return FAIL;
		}
		return SUCCESS;
	}
	
	public void calAndSaveCloseDataInfo(String type, String calCode) {
		//该方法不做事务处理
		List<TbStock> tbStocks = stockService.getStocksByType(type);
		Map<String, RemoteDataInfo> remoteDataInfoMap = remoteDataService.findRemoteDataInfoMap(type, tbStocks);
		logger.info("【记录收盘数据-类型{}】准备记录收盘信息,数据条数{},未剔除即将上市股.", type, remoteDataInfoMap.size());
		BigDecimal sum = new BigDecimal(0);//用于计算板块平均涨幅中间参数
		int staticNums = 0;
		String closeDate = sdf.format(new Date());
		for(RemoteDataInfo remoteDataInfo : remoteDataInfoMap.values()) {
			String code = remoteDataInfo.getCode();
			try {
				if(remoteDataInfo.getRealTimePrice()>0) {
					String closeRatePercent = remoteDataInfo.getRatePercent();
					if(StockTypeEnum.STOCK_STAR.getCode().equals(type)) {  
						//科创//每个code每天插入一条数据，若已有数据就更新
						TbHistoryDataExample example = new TbHistoryDataExample();
						example.or().andCodeEqualTo(code).andCloseDateEqualTo(closeDate);
						List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
						String closeValue = remoteDataService.calCloseValue(code, closeRatePercent);
						if(tbHistoryDataList!=null && tbHistoryDataList.size()>0) {//更新
							TbHistoryData record = tbHistoryDataList.get(0);
							record.setClosePrice(new BigDecimal(remoteDataInfo.getRealTimePrice()));
							record.setCloseRatePercent(closeRatePercent);
							record.setUpdateTime(new Date());
							record.setDescription(closeValue);
							tbHistoryDataMapper.updateByPrimaryKeySelective(record);
						}else {
							TbHistoryData record = new TbHistoryData();
							record.setCode(code);
							record.setName(remoteDataInfo.getName());
							record.setCloseDate(closeDate);
							record.setClosePrice(new BigDecimal(remoteDataInfo.getRealTimePrice()));
							record.setCloseRatePercent(closeRatePercent);
							record.setDescription(closeValue);
							tbHistoryDataMapper.insertSelective(record);
						}
						logger.info("code:{}, 保存收盘数据成功.", code);
						sum = sum.add(new BigDecimal(closeValue));
						staticNums++;
					}else {
						//非科创只需保存收盘指数
						sum = sum.add(new BigDecimal(closeRatePercent.replace("%", "")));
						staticNums++;
					}
				}
			} catch (Exception e) {
				logger.error("code:{}, 保存收盘数据失败, 转手动处理.", code);
			}
		}
		logger.info("sum:{}, staticNums:{}.", sum, staticNums);
		logger.info("【记录收盘数据-类型{}】{}收盘数据记录成功.", type, closeDate);
		//计算并保存当日指数收盘数据
		//在当日之前，相关指数至少有一条历史数据存在
		if(staticNums > 0) {
			TbHistoryDataExample example = new TbHistoryDataExample();
			example.or().andCodeEqualTo(calCode).andCloseDateNotEqualTo(closeDate);
			List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
			TbHistoryData tbHistoryData = tbHistoryDataList.get(tbHistoryDataList.size()-1);
			BigDecimal lastClosePrice = tbHistoryData.getClosePrice();
			
			BigDecimal closePrice = new BigDecimal(0);
			String closeRatePercent = "";
			if(StockTypeEnum.STOCK_STAR.getCode().equals(type)) {  
				closePrice = sum.divide(new BigDecimal(staticNums),3,BigDecimal.ROUND_HALF_DOWN);
			} else {
				BigDecimal avgRate = sum.divide(new BigDecimal(staticNums),3,BigDecimal.ROUND_HALF_DOWN);//平均涨幅（无%）
				BigDecimal cal = new BigDecimal(100);
				closeRatePercent = avgRate.toString()+"%";
				closePrice = cal.add(avgRate).multiply(lastClosePrice).divide(new BigDecimal(100), 3, BigDecimal.ROUND_HALF_DOWN);
			}
			logger.info("【记录收盘数据-类型{}】staticNums:{}, sum:{}, closePrice:{}, lastClosePrice:{}", type, staticNums, sum, closePrice, lastClosePrice);
			TbHistoryDataExample exampleToday = new TbHistoryDataExample();
			exampleToday.or().andCodeEqualTo(calCode).andCloseDateEqualTo(closeDate);
			List<TbHistoryData> tbHistoryDataListToday = tbHistoryDataMapper.selectByExample(exampleToday);
			if(tbHistoryDataListToday!=null&&tbHistoryDataListToday.size()>0) {
				TbHistoryData record = tbHistoryDataListToday.get(0);
				record.setClosePrice(closePrice);
				record.setCloseRatePercent(closeRatePercent);
				record.setUpdateTime(new Date());
				tbHistoryDataMapper.updateByPrimaryKeySelective(record);
			} else {
				TbHistoryData record = new TbHistoryData();
				record.setCode(calCode);
				record.setName("");
				record.setCloseDate(closeDate);
				record.setClosePrice(closePrice);
				record.setCloseRatePercent(closeRatePercent);
				tbHistoryDataMapper.insertSelective(record);
			}
			logger.info("【记录收盘数据-类型{}】今日{}指数数据记录成功[上条数据时间:{}]", type, closeDate, tbHistoryData.getCloseDate());
		}
	}
	
}

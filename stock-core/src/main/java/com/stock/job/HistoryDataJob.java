package com.stock.job;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stock.dataobject.RemoteDataInfo;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbHistoryData;
import com.stock.model.TbHistoryDataExample;
import com.stock.model.TbStock;
import com.stock.repository.TbHistoryDataMapper;
import com.stock.service.RemoteDataService;
import com.stock.service.StockService;

@Component
public class HistoryDataJob {
	
	private final Logger logger = LoggerFactory.getLogger(HistoryDataJob.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private RemoteDataService remoteDataService;
	
	@Autowired
	private TbHistoryDataMapper tbHistoryDataMapper;

	@Scheduled(cron = "0 0 17 * * ?")
	public void saveCloseDataInfo() {
		
		if(remoteDataService.isTradingDay()) {
			//该方法不做事务处理
			String type = StockTypeEnum.STOCK_STAR.getCode();
			List<TbStock> tbStocks = stockService.getStocksByType(type);
			Map<String, RemoteDataInfo> remoteDataInfoMap = remoteDataService.findRemoteDataInfoMap(type, null, tbStocks);
			logger.info("【记录STAR收盘数据】准备记录收盘信息,数据条数{},未剔除即将上市股.", remoteDataInfoMap.size());
			BigDecimal sum = new BigDecimal(0);//用于计算板块平均涨幅中间参数
			int staticNums = 0;
			String closeDate = sdf.format(new Date());
			for(RemoteDataInfo remoteDataInfo : remoteDataInfoMap.values()) {
				String code = remoteDataInfo.getCode();
				try {
					//每个code每天插入一条数据，若已有数据就更新
					if(remoteDataInfo.getRealTimePrice()>0) {
						String closeRatePercent = remoteDataInfo.getRatePercent();
						TbHistoryDataExample example = new TbHistoryDataExample();
						example.or().andCodeEqualTo(code).andCloseDateEqualTo(closeDate);
						List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
						if(tbHistoryDataList!=null && tbHistoryDataList.size()>0) {//更新
							TbHistoryData record = tbHistoryDataList.get(0);
							record.setClosePrice(new BigDecimal(remoteDataInfo.getRealTimePrice()));
							record.setCloseRatePercent(closeRatePercent);
							record.setUpdateTime(new Date());
							tbHistoryDataMapper.updateByPrimaryKeySelective(record);
						}else {
							TbHistoryData record = new TbHistoryData();
							record.setCode(code);
							record.setName(remoteDataInfo.getName());
							record.setCloseDate(closeDate);
							record.setClosePrice(new BigDecimal(remoteDataInfo.getRealTimePrice()));
							record.setCloseRatePercent(closeRatePercent);
							tbHistoryDataMapper.insertSelective(record);
						}
						sum = sum.add(new BigDecimal(closeRatePercent.replace("%", "")));
						staticNums++;
					}
					logger.info("code:{}, 保存收盘数据成功.", code);
				} catch (Exception e) {
					logger.info("code:{}, 保存收盘数据失败, 转手动处理.", code);
				}
			}
			logger.info("【记录STAR收盘数据】{}收盘数据记录成功.", closeDate);
			//计算并保存当日科创指数收盘数据
			if(staticNums > 0) {
				TbHistoryDataExample example = new TbHistoryDataExample();
				example.or().andCodeEqualTo("688000");
				List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
				TbHistoryData tbHistoryData = tbHistoryDataList.get(tbHistoryDataList.size()-1);
				BigDecimal lastClosePrice = tbHistoryData.getClosePrice();
				BigDecimal avgRate = sum.divide(new BigDecimal(staticNums),3,BigDecimal.ROUND_HALF_DOWN);//平均涨幅（无%）
				BigDecimal cal = new BigDecimal(100);
				BigDecimal closePrice = cal.add(avgRate).multiply(lastClosePrice).divide(new BigDecimal(100), 3, BigDecimal.ROUND_HALF_DOWN);
				TbHistoryDataExample exampleToday = new TbHistoryDataExample();
				exampleToday.or().andCodeEqualTo("688000").andCloseDateEqualTo(closeDate);
				List<TbHistoryData> tbHistoryDataListToday = tbHistoryDataMapper.selectByExample(exampleToday);
				if(tbHistoryDataListToday!=null&&tbHistoryDataListToday.size()>0) {
					TbHistoryData record = tbHistoryDataListToday.get(0);
					record.setClosePrice(closePrice);
					record.setCloseRatePercent(avgRate.toString()+"%");
					record.setUpdateTime(new Date());
					tbHistoryDataMapper.updateByPrimaryKeySelective(record);
				} else {
					TbHistoryData record = new TbHistoryData();
					record.setCode("688000");
					record.setName("科创指数");
					record.setCloseDate(closeDate);
					record.setClosePrice(closePrice);
					record.setCloseRatePercent(avgRate.toString()+"%");
					tbHistoryDataMapper.insertSelective(record);
				}
				logger.info("【记录STAR收盘数据】今日{}指数数据记录成功[上条数据时间:{}]", closeDate, tbHistoryData.getCloseDate());
			}
		} else {
			logger.info("【记录STAR收盘数据】 "+new Date()+" 判断为非交易日.");
		}
		
	}
	
}

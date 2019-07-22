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

	@Scheduled(cron = "* * 17 * * ?")
	public void saveCloseDataInfo() {
		
		String type = StockTypeEnum.STOCK_STAR.getCode();
		List<TbStock> tbStocks = stockService.getStocksByType(type);
		Map<String, RemoteDataInfo> remoteDataInfoMap = remoteDataService.findRemoteDataInfoMap(type, null, tbStocks);
		logger.info("准备保存收盘信息，数据条数{}", remoteDataInfoMap.size());
		for(RemoteDataInfo remoteDataInfo : remoteDataInfoMap.values()) {
			String code = remoteDataInfo.getCode();
			try {
				String closeDate = sdf.format(new Date());
				TbHistoryDataExample example = new TbHistoryDataExample();
				example.or().andCodeEqualTo(code).andCloseDateEqualTo(closeDate);
				List<TbHistoryData> tbHistoryDataList = tbHistoryDataMapper.selectByExample(example);
				if(tbHistoryDataList!=null && tbHistoryDataList.size()>0) {//更新
					TbHistoryData record = tbHistoryDataList.get(0);
					record.setClosePrice(new BigDecimal(remoteDataInfo.getRealTimePrice()));
					record.setCloseRatePercent(remoteDataInfo.getRatePercent());
					record.setUpdateTime(new Date());
					tbHistoryDataMapper.updateByPrimaryKeySelective(record);
				}else {
					TbHistoryData record = new TbHistoryData();
					record.setCode(code);
					record.setName(remoteDataInfo.getName());
					record.setCloseDate(closeDate);
					record.setClosePrice(new BigDecimal(remoteDataInfo.getRealTimePrice()));
					record.setCloseRatePercent(remoteDataInfo.getRatePercent());
					tbHistoryDataMapper.insertSelective(record);
				}
				logger.info("code:{}, 保存收盘数据成功.", code);
			} catch (Exception e) {
				logger.info("code:{}, 保存收盘数据失败, 转手动处理.", code);
			}
		}
	}
}

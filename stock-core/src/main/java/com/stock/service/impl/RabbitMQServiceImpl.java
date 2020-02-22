package com.stock.service.impl;


import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.command.Command;
import com.common.command.CommandManager;
import com.stock.enums.FlagEnum;
import com.stock.model.TbStock;
import com.stock.model.TbStockExample;
import com.stock.service.RabbitMQService;
import com.stock.service.StockService;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQServiceImpl.class);

    @Autowired
    private StockService stockService;
    
    @PostConstruct
    public void init() {
    	logger.info("初始化CommandManager");
        CommandManager.register(FlagEnum.INIT_CONCERNED.name(), this::saveInitConcernedData);
    }

    @Override
    public String saveInitConcernedData(Command command) {
        
    	try {
			String json = command.json;
			TbStock tbStock = JSONObject.parseObject(json, TbStock.class);
			TbStockExample example = new TbStockExample();
			example.or().andCodeEqualTo(tbStock.getCode());
			List<TbStock> tbStockList = stockService.selectByExample(example);
			if(tbStockList!=null&&tbStockList.size()>0) { //更新最大值
				TbStock record = tbStockList.get(0);
				TbStockExample stockExample = new TbStockExample();
				if(tbStock.getMaxValue()!=null) {
					record.setMaxValue(tbStock.getMaxValue());
					stockExample.or().andCodeEqualTo(tbStock.getCode());
				}
				stockService.updateByExampleSelective(record, stockExample);
				logger.info("code:{},更新成功.", tbStock.getCode());
			}else {//新增
				stockService.insertSelective(tbStock);
				logger.info("code:{},新增成功.", tbStock.getCode());
			}
		} catch (Exception e) {
			logger.error("初始化关注的数据异常", e);
		}
        return "初始化关注的数据成功.";
    }

}

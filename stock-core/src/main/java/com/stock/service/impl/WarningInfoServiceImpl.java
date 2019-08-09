package com.stock.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.model.TbWarningInfo;
import com.stock.repository.TbWarningInfoMapper;
import com.stock.service.WarningInfoService;
import com.stock.utils.DateUtils;

@Service
public class WarningInfoServiceImpl implements WarningInfoService {

	@Autowired
	private TbWarningInfoMapper tbWarningInfoMapper;
	
	@Override
	public void saveWarningInfo(String warningType, String description) {
		TbWarningInfo record = new TbWarningInfo();
		record.setWarningType(warningType);
		record.setDescription(description);
		String closeDate = DateUtils.getDate(new Date());
		record.setCloseDate(closeDate);
		tbWarningInfoMapper.insertSelective(record);
	}
	
	
}

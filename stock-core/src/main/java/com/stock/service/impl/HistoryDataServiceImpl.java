package com.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.model.TbHistoryData;
import com.stock.model.TbHistoryDataExample;
import com.stock.repository.TbHistoryDataMapper;
import com.stock.service.HistoryDataService;

@Service
public class HistoryDataServiceImpl implements HistoryDataService {

	@Autowired
	private TbHistoryDataMapper tbHistoryDataMapper;

	@Override
	public TbHistoryData selectByCode(String code) {
		TbHistoryDataExample example = new TbHistoryDataExample();
		example.or().andCodeEqualTo(code);
		List<TbHistoryData> tbHistoryDataList =  tbHistoryDataMapper.selectByExample(example);
		if(tbHistoryDataList!=null&&tbHistoryDataList.size()>0) {
			return tbHistoryDataList.get(tbHistoryDataList.size()-1);
		}
		return null;
	}
	
	
	
}

package com.stock.service;

import java.util.List;

import com.stock.model.TbWarningInfo;

public interface WarningInfoService {

	void saveWarningInfo(String warningType, String description);
	
	List<TbWarningInfo> selectByStatus(String status);

}

package com.stock.service;

import com.stock.model.TbHistoryData;

public interface HistoryDataService {

	public TbHistoryData selectByCode(String code);
}

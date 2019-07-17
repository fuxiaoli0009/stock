package com.stock.service;

import com.common.command.Command;

public interface RabbitMQService {

	/**
	 * 初始化关注的数据
	 * @param command
	 * @return
	 */
	String saveInitConcernedData(Command command);
}

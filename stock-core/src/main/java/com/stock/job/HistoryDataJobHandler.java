package com.stock.job;

import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

@JobHandler(value = "HistoryDataJobHandler")
@Component
public class HistoryDataJobHandler extends IJobHandler {

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		System.out.println("################success");
		return null;
	}

}

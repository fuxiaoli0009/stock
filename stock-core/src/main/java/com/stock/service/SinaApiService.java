package com.stock.service;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 新浪接口服务
 */
@Service
public class SinaApiService {

	private final Logger logger = LoggerFactory.getLogger(SinaApiService.class);
	
	private final static String SINA_REMOTE_URL = "http://hq.sinajs.cn/list=";
	
	@Autowired
	RestTemplate restTemplate;
	
	public String getRealTimeStockInfoFromRemote(String codes) {
		logger.info("调用新浪接口批量查询股票实时信息, url:{}, 入参:{}", codes);
		StopWatch watch = new StopWatch();
		watch.start();
		String response = restTemplate.getForObject(SinaApiService.SINA_REMOTE_URL + codes, String.class);
		watch.stop();
		logger.info("调用新浪接口批量查询股票实时信息, 耗时:{}毫秒, 返回结果:{}", watch.getTime(), response);
		return response;
	}
}

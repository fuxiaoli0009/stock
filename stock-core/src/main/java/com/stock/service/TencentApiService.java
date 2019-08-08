package com.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stock.dataobject.RemoteDataInfo;
import com.stock.enums.RemoteDataPrefixEnum;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbStock;

@Service
public class TencentApiService {

private final Logger logger = LoggerFactory.getLogger(SinaApiService.class);
	
	private final static String TENCENT_REMOTE_URL = "http://qt.gtimg.cn/r=0q=";
	
	@Autowired
	RestTemplate restTemplate;
	
	/**
	 * 获取并返回Tencent接口数据
	 * @param codes
	 * @return
	 */
	public Map<String, RemoteDataInfo> getRealTimeInfoFromRemote(String codes) {
		logger.info("调用Tencent接口批量查询实时信息, url:{}", TencentApiService.TENCENT_REMOTE_URL+codes);
		StopWatch watch = new StopWatch();
		watch.start();
		String response = restTemplate.getForObject(TencentApiService.TENCENT_REMOTE_URL + codes, String.class);
		watch.stop();
		logger.info("调用Tencent接口批量查询实时信息, 耗时:{}毫秒, 返回结果:{}", watch.getTime(), response);
		return remoteDataInfoMap(response);
	}
	
	/**
	 * 封装Tencent数据为通用模板RemoteDataInfo数据
	 * @param response
	 * @return
	 */
	public Map<String, RemoteDataInfo> remoteDataInfoMap(String response){
		if(response!=null && response.contains(";")) {
			String[] responseArray = response.split(";");
			Map<String, RemoteDataInfo> remoteDataInfoMap = new HashMap<String, RemoteDataInfo>();
			for(int i=0; i< responseArray.length-1; i++) {
				try {
					RemoteDataInfo remote = new RemoteDataInfo();
					String[] strs = responseArray[i].split("~");
					if(strs!=null&&strs.length>0) {
						if((strs[0].contains(RemoteDataPrefixEnum.TENCENT_SH.getCode())||strs[0].contains(RemoteDataPrefixEnum.TENCENT_SZ.getCode()))&&!(Long.valueOf(strs[7])>0)) {
							logger.error("数据:{},成交量为0,剔除统计.", responseArray[i]);
						}else {
							remote.setCode(strs[2]);
							remote.setName(strs[1]);
							remote.setRealTimePrice(Double.parseDouble(strs[3]));
							if(strs[0].contains(RemoteDataPrefixEnum.TENCENT_HK.getCode())) {
								remote.setRatePercent(strs[32]+"%");
							}else {
								remote.setRatePercent(strs[5]+"%");
							}
							if(strs[0].contains(RemoteDataPrefixEnum.TENCENT_SH.getCode())||strs[0].contains(RemoteDataPrefixEnum.TENCENT_SZ.getCode())){
								remote.setTurnOver(Long.valueOf(strs[7])*10000);
							}
							remoteDataInfoMap.put(remote.getCode(), remote);
						}
					}
				} catch (Exception e) {
					logger.error("数据:{},封装Tencent数据为通用模板异常", responseArray[i], e);
				}
			}
			return remoteDataInfoMap;
		}
		return null;
	}

	/**
	 * 封装Tencent接口代码前缀
	 * @param tbStocks
	 * @param typeCode
	 * @return
	 */
	public String getCodesFromStocks(List<TbStock> tbStocks, String typeCode) {
		if(tbStocks!=null && tbStocks.size()>0) {
    		StringBuffer sb = new StringBuffer();
            for(TbStock tbStock : tbStocks){
            	String code = tbStock.getCode();
                try {
                	if(StockTypeEnum.STOCK_STATUS_HS.getCode().equals(typeCode)||StockTypeEnum.STOCK_STATUS_CHOSEN.getCode().equals(typeCode)) {
                		sb.append(code.length() == 6 && code.startsWith("6") ? RemoteDataPrefixEnum.TENCENT_SH.getCode() : RemoteDataPrefixEnum.TENCENT_SZ.getCode());
                	}
                	if(StockTypeEnum.STOCK_STATUS_HK.getCode().equals(typeCode)) {
                		sb.append(RemoteDataPrefixEnum.TENCENT_HK.getCode());
                	}
                	if(StockTypeEnum.STOCK_STAR.getCode().equals(typeCode)) {
                		sb.append(RemoteDataPrefixEnum.TENCENT_SH.getCode());
                	}
    				sb.append(code);
    				sb.append(",");
    			} catch (Exception e) {
    				logger.error("code:{}, Tencent拼接字符串异常, 请修改数据库相关字段, 异常:{}", code, ExceptionUtils.getStackTrace(e));
    			}
            }
            return sb.toString().substring(0, sb.length()-1);
    	}
        return null;
	}

	/**
	 * 组装上海指数前缀
	 * @param codes
	 * @param type
	 * @return
	 */
	public String getCodesFromSHZSCodes(List<String> codes, String type) {
		StringBuffer sb = new StringBuffer();
		for(String code : codes) {
			sb.append(code.startsWith("0")?"s_sh":"s_sz");
			sb.append(code);
			sb.append(",");
		}
		return sb.toString().substring(0, sb.length()-1);
	}
	
}

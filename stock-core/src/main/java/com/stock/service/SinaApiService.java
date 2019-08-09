package com.stock.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.stock.dataobject.RemoteDataInfo;
import com.stock.enums.RemoteDataPrefixEnum;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbStock;

/**
 * Sina接口服务
 */
@Service
public class SinaApiService {

	private final Logger logger = LoggerFactory.getLogger(SinaApiService.class);
	
	private final static String SINA_REMOTE_URL = "http://hq.sinajs.cn/list=";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private WarningInfoService warningInfoService;
	
	public Map<String, RemoteDataInfo> getRealTimeInfoFromRemote(String codes) {
		String response = null;
		try {
			logger.info("调用Sina接口批量查询股票实时信息, url:{}", SinaApiService.SINA_REMOTE_URL+codes);
			StopWatch watch = new StopWatch();
			watch.start();
			response = restTemplate.getForObject(SinaApiService.SINA_REMOTE_URL + codes, String.class);
			watch.stop();
			logger.info("调用Sina接口批量查询股票实时信息, 耗时:{}毫秒, 返回结果:{}", watch.getTime(), response);
		} catch (RestClientException e) {
			logger.error("调用Sina接口批量查询股票实时信息异常", e);
			warningInfoService.saveWarningInfo("SINA", "调用Sina接口批量查询股票实时信息异常, codes="+codes);
		}
		return remoteDataInfoMap(response);
	}
	
	/**
	 * 封装Sina数据为通用模板RemoteDataInfo数据
	 * 沪深股票、沪深指数、港股，远程接口返回数据格式都不相同，要分别解析
	 * @param response
	 * @return
	 */
	public Map<String, RemoteDataInfo> remoteDataInfoMap(String response){
		if(response!=null && response.contains(";")) {
			String[] responseArray = response.split("\n");
			Map<String, RemoteDataInfo> remoteDataInfoMap = new HashMap<String, RemoteDataInfo>();
			for(int i=0; i< responseArray.length; i++) {
				try {
					RemoteDataInfo remote = new RemoteDataInfo();
					String[] datas = responseArray[i].split(",");  //每条数据
					String[] market = datas[0].split("=\"");
					String codeStr = market[0];
					String code = "";
					String name = "";
					Double realTimePrice = 0D;
					Double yesterdayPrice = 0D;//今日涨跌及百分比
					if(datas!=null&&datas.length>3) {
						if(codeStr.contains(RemoteDataPrefixEnum.SINA_S_SH.getCode()) || codeStr.contains(RemoteDataPrefixEnum.SINA_S_SZ.getCode())) {
							//沪深指数解析
							code = codeStr.substring(codeStr.length()-6, codeStr.length());
							name = market[1];
							realTimePrice = Double.parseDouble(datas[1]);
							remote.setRatePercent(datas[3]+"%");
						}else if(codeStr.contains(RemoteDataPrefixEnum.SINA_HK.getCode())) {
							//港股解析
							code = codeStr.substring(codeStr.length()-5, codeStr.length());
							name = datas[1];
							realTimePrice = Double.parseDouble(datas[6]);
							remote.setRatePercent(datas[8]+"%");
						}else{
							//沪深股票解析
							code = codeStr.substring(codeStr.length()-6, codeStr.length());
							name = market[1];
							realTimePrice = Double.parseDouble(datas[3]);
							yesterdayPrice = datas[2].startsWith("0.0")?realTimePrice:Double.parseDouble(datas[2]);//昨天收盘价
							if(realTimePrice!=0 && yesterdayPrice!=0) {
								BigDecimal b1 = new BigDecimal(realTimePrice).subtract(new BigDecimal(Double.toString(yesterdayPrice)));  
								BigDecimal b2 = new BigDecimal(Double.toString(yesterdayPrice));
								Double rate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
								NumberFormat nf = NumberFormat.getPercentInstance();
								nf.setMaximumIntegerDigits(4); //小数点前保留几位
								nf.setMinimumFractionDigits(2);//小数点后保留几位
								remote.setRatePercent(nf.format(rate));
							} else {
								remote.setRatePercent("0.00%");
							}
							remote.setTurnOver(Long.parseLong(datas[9].substring(0, datas[9].indexOf("."))));
						}
						remote.setCode(code);
						remote.setName(name);
						remote.setRealTimePrice(realTimePrice);
						remoteDataInfoMap.put(remote.getCode(), remote);
					}else {
						logger.error("封装Sina数据为通用模板RemoteDataInfo数据异常, 长度不够", responseArray[i]);
						warningInfoService.saveWarningInfo("SINA", "封装Sina数据为通用模板RemoteDataInfo数据异常, 长度不够, responseArray[i]="+responseArray[i]);
					}
					
				} catch (Exception e) {
					logger.error("封装Sina数据为通用模板RemoteDataInfo数据异常,{}", responseArray[i], e);
					warningInfoService.saveWarningInfo("SINA", "封装Sina数据为通用模板RemoteDataInfo数据异常, responseArray[i]="+responseArray[i]);
				}
			}
			return remoteDataInfoMap;
		}
		return null;
	}
	
	/**
	 * 封装Sina接口代码前缀
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
                		sb.append(code.length() == 6 && code.startsWith("6") ? RemoteDataPrefixEnum.SINA_SH.getCode() : RemoteDataPrefixEnum.SINA_SZ.getCode());
                	}
                	if(StockTypeEnum.STOCK_STATUS_HK.getCode().equals(typeCode)) {
                		sb.append(RemoteDataPrefixEnum.SINA_HK.getCode());
                	}
                	if(StockTypeEnum.STOCK_STAR.getCode().equals(typeCode)) {
                		sb.append(RemoteDataPrefixEnum.SINA_SH.getCode());
                	}
    				sb.append(code);
    				sb.append(",");
    			} catch (Exception e) {
    				logger.error("code:{}, Sina接字符串异常, 请修改数据库相关字段, 异常:{}", code, ExceptionUtils.getStackTrace(e));
    				warningInfoService.saveWarningInfo("SINA", "code:"+code+", Sina接字符串异常.");
    			}
            }
            return sb.toString().substring(0, sb.length()-1);
    	}
        return null;
	}

	/**
	 * 沪深指数前缀
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

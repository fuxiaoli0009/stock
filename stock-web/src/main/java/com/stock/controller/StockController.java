package com.stock.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.stock.dataobject.StockInfo;
import com.stock.dataobject.StockRiseAndFall;
import com.stock.enums.StockTypeEnum;
import com.stock.model.TbStock;
import com.stock.service.SinaApiService;
import com.stock.service.StockService;
import com.stock.utils.StockUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "stock")
public class StockController {

	private final Logger log = LoggerFactory.getLogger(StockController.class);
	
	private final static String PBPEUrl = "https://www.jisilu.cn/data/stock/";
	
    @Autowired
    private StockService stockService;
    
    @Autowired
    private SinaApiService sinaApiService;

    @Autowired
    private RestTemplate restTemplate;
    
    @ApiOperation(value = "查询", httpMethod = "GET")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Map<String, Object> map){
    	
        try {
        	//沪深
        	List<TbStock> hsStocks = stockService.getStocksByType(StockTypeEnum.STOCK_STATUS_HS.getCode());
        	//String hsCodes = "sh600300,sh600811,sz000698";  //for test
        	String hsCodes = this.getCodesFromStocks(hsStocks, StockTypeEnum.STOCK_STATUS_HS.getCode());
        	String sinaHsResponse = sinaApiService.getRealTimeStockInfoFromRemote(hsCodes);
        	List<StockInfo> hsViewStocks = this.assembleDatas(sinaHsResponse, hsStocks, StockTypeEnum.STOCK_STATUS_HS.getCode());
        	
        	//香港
        	List<TbStock> hkStocks = stockService.getStocksByType(StockTypeEnum.STOCK_STATUS_HK.getCode());
        	//String hkCodes = "hk01282";
        	String hkCodes = this.getCodesFromStocks(hkStocks, StockTypeEnum.STOCK_STATUS_HK.getCode());
        	String sinaHkResponse = sinaApiService.getRealTimeStockInfoFromRemote(hkCodes);
        	List<StockInfo> hkViewStocks = this.assembleDatas(sinaHkResponse, hkStocks, StockTypeEnum.STOCK_STATUS_HK.getCode());
        	
        	Collections.sort(hsViewStocks);
        	Collections.sort(hkViewStocks);
        	map.put("hsStocks", hsViewStocks);
            map.put("hkStocks", hkViewStocks);
            log.info("沪深数量:{}, 港股数量:{}", hsViewStocks.size(), hkViewStocks.size());
        }catch (Exception e){
        	log.error("获取股票数据异常, 错误信息:{}", ExceptionUtils.getStackTrace(e));
        }
        return new ModelAndView("/stock/index", "map", map);
    }
    
    //根据股票信息拼接股票代码字符串
    public String getCodesFromStocks(List<TbStock> tbStocks, String typeCode) {
    	if(tbStocks!=null&&tbStocks.size()>0) {
    		StringBuffer sb = new StringBuffer();
            for(TbStock tbStock : tbStocks){
            	String code = tbStock.getCode();
                try {
                	if(StockTypeEnum.STOCK_STATUS_HS.getCode().equals(typeCode)) {
                		sb.append(code.length() == 6 && code.startsWith("6") ? "sh" : "sz");
                	}else if(StockTypeEnum.STOCK_STATUS_HK.getCode().equals(typeCode)) {
                		if(code.length() == 5) {
                			sb.append("hk");
                		}
                	}
    				sb.append(code);
    				sb.append(",");
    			} catch (Exception e) {
    				log.error("code:{}, 拼接字符串异常, 请修改数据库相关字段, 异常:{}", code, ExceptionUtils.getStackTrace(e));
    			}
            }
            return sb.toString().substring(0, sb.length()-1);
    	}
        return null;
    }
    
    //组装数据
    public List<StockInfo> assembleDatas(String response, List<TbStock> tbStocks, String typeCode) {
        
    	List<StockInfo> viewList = new ArrayList<StockInfo>();
    	String[] result = response.split("\n");
    	if(result != null && result.length > 0){
            for(int i=0; i<result.length; i++) {
            	TbStock stock = tbStocks.get(i);
                try {
					String[] stockData = result[i].split(",");
					if (stockData != null && stockData.length > 0) {
					    StockInfo stockInfo = new StockInfo();
					    
					    stockInfo.setCode(stock.getCode());
					    stockInfo.setPBPEUrl(StockController.PBPEUrl+stockInfo.getCode());
					    Double yesterdayPrice = 0.0;
					    Double realTimePrice = 0.0;
					    if(StockTypeEnum.STOCK_STATUS_HS.getCode().equals(typeCode)) {
					    	String nameStr = stockData[0];
					        stockInfo.setName(nameStr.substring(21, nameStr.length()));//名称
					        realTimePrice = stockData[3].startsWith("0.0")?200.00:Double.parseDouble(stockData[3]);
					        stockInfo.setRealTimePrice(realTimePrice);//实时价格
					        yesterdayPrice = stockData[2].startsWith("0.0")?realTimePrice:Double.parseDouble(stockData[2]);//昨天收盘价
					    }else if(StockTypeEnum.STOCK_STATUS_HK.getCode().equals(typeCode)) {
					    	stockInfo.setName(stockData[1]);
					    	realTimePrice = stockData[3].startsWith("0.0")?200.00:Double.parseDouble(stockData[6]);
					    	stockInfo.setRealTimePrice(realTimePrice);
					    	yesterdayPrice = stockData[3].startsWith("0.0")?realTimePrice:Double.parseDouble(stockData[3]);
					    }
					    
					    //今日涨跌及百分比
					    BigDecimal b1 = new BigDecimal(Double.toString(realTimePrice - yesterdayPrice));  
						BigDecimal b2 = new BigDecimal(Double.toString(yesterdayPrice));
						Double rate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
						NumberFormat nf = NumberFormat.getPercentInstance();
						nf.setMaximumIntegerDigits(2); //小数点前保留几位
						nf.setMinimumFractionDigits(2);//小数点后保留几位
						stockInfo.setRatePercent(nf.format(rate));
					    
					    //买入价格
						Double buyPrice = stock.getBuyPrice()==null?1.0:stock.getBuyPrice().doubleValue();
					    stockInfo.setBuyPrice(buyPrice);
					    //买入还差百分之几
					    b1 = new BigDecimal(Double.toString(realTimePrice - buyPrice));
					    b2 = new BigDecimal(Double.toString(realTimePrice));
					    Double buyRate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
					    stockInfo.setBuyRateDouble(buyRate);
					    stockInfo.setBuyRate(nf.format(buyRate));
					    
					    //最高点已跌百分比
					    Double maxValue = stock.getMaxValue()==null?200.0:stock.getMaxValue().doubleValue();
					    b1 = new BigDecimal(Double.toString(maxValue - realTimePrice));
					    b2 = new BigDecimal(Double.toString(maxValue));
					    Double maxRate = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
					    stockInfo.setMaxRate(nf.format(maxRate));
					    
					    stockInfo.setDescription(stock.getDescription()==null?"":stock.getDescription());
					    
					    viewList.add(stockInfo);
					}
				} catch (Exception e) {
					log.error("数据组装异常, 股票信息:{}", JSON.toJSONString(stock));
				}
            }
        }
        return viewList;
    }
    
    @ApiOperation(value = "更新", httpMethod = "GET")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void update(@RequestParam("tdIndex") String tdIndex, @RequestParam("code") String code, 
    		@RequestParam("value") String value){
    	stockService.update(tdIndex, code, value);
    }
    
    @ApiOperation(value = "删除", httpMethod = "GET")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void delete(@RequestParam("code") String code) {
    	stockService.delete(code);
    }
    
    @RequestMapping(value = "/addStock", method = RequestMethod.GET)
    public void addStock(@RequestParam("code") String code, @RequestParam("name") String name, 
    		@RequestParam("maxPrice") String maxPrice, @RequestParam("buyPrice") String buyPrice) {
    	TbStock tbStock = new TbStock();
    	tbStock.setCode(code);
    	tbStock.setName(name);
    	tbStock.setBuyPrice(new BigDecimal(buyPrice));
    	tbStock.setMaxValue(new BigDecimal(maxPrice));
    	stockService.addStock(tbStock);
    }
	  
}

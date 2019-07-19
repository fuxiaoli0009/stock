package com.stock.dataobject;

/**
 * 远程数据封装模板类
 */
public class RemoteDataInfo {

	public String code;
	
	public String name;
	
	public Double realTimePrice;
	
	public String ratePercent;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRealTimePrice() {
		return realTimePrice;
	}

	public void setRealTimePrice(Double realTimePrice) {
		this.realTimePrice = realTimePrice;
	}

	public String getRatePercent() {
		return ratePercent;
	}

	public void setRatePercent(String ratePercent) {
		this.ratePercent = ratePercent;
	}
	
}

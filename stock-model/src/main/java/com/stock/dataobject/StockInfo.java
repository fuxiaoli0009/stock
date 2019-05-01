package com.stock.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tb_stock")
public class StockInfo implements Comparable<StockInfo>{

    @Id
    public int id;

    /**
     * 股票代码
     */
    public String code;

    /**
     * 股票价格
     */
    public String name;

    /**
     * 实时价格
     */
    @Transient
    public Double realTimePrice;

    /**
     * 买入价格
     */
    public Double buyPrice;

    /**
     * 卖出价格
     */
    public Double sellPrice;

    /**
     * 5178前复权最高价
     */
    public Double maxValue;

    /**
     * 5178前复权最低价
     */
    public Double minValue;
    
    public Double buyRateDouble;

    /**
     * 买入还差
     */
    @Transient
    public String buyRate;

    /**
     * 最高点已跌百分比
     */
    @Transient
    public String maxRate;

    /**
     * 今日涨跌幅
     */
    @Transient
    public String ratePercent;

    public String description;

    public String PBPEUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public String getBuyRate() {
		return buyRate;
	}

	public void setBuyRate(String buyRate) {
		this.buyRate = buyRate;
	}

	public String getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}

	public String getRatePercent() {
		return ratePercent;
	}

	public void setRatePercent(String ratePercent) {
		this.ratePercent = ratePercent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPBPEUrl() {
		return PBPEUrl;
	}

	public void setPBPEUrl(String pBPEUrl) {
		PBPEUrl = pBPEUrl;
	}

	public Double getBuyRateDouble() {
		return buyRateDouble;
	}

	public void setBuyRateDouble(Double buyRateDouble) {
		this.buyRateDouble = buyRateDouble;
	}

	@Override
	public int compareTo(StockInfo stockInfo) {
		if(this.buyRateDouble > stockInfo.getBuyRateDouble()) {
			return 1;
		}else {
			return -1;
		}
	}
    
}

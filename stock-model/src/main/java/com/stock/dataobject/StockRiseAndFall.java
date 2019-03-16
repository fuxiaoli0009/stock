package com.stock.dataobject;

import lombok.Data;

/**
 * @Author: fuxiaoli
 * @Date: 2018/4/6
 **/
public class StockRiseAndFall {

    /**
     * 股票总数
     */
    public Integer total;

    /**
     * 上涨股票数
     */
    public Integer totalUp;

    /**
     * 下跌股票数
     */
    public Integer totalDown;

    /**
     * 涨停数
     */
    public Integer limitUp;

    /**
     * 有效涨停数
     */
    private Integer realLimitUp;

    /**
     * 跌停数
     */
    private Integer limitDown;

    /**
     * 有效跌停数
     */
    private Integer realLimitDown;

    /**
     * 涨幅在3%以内的股票数量
     */
    private Integer up0To3;

    private String  up0To3Percentage;

    /**
     * 涨幅在3%至5%的股票数量
     */
    private Integer up3To5;

    private String up3To5Percentage;

    /**
     * 涨幅在5%至7%的股票数量
     */
    private Integer up5To7;

    private String up5To7Percentage;

    /**
     * 涨幅在7%至10%的股票数量
     */
    private Integer up7To10;

    private String up7To10Percentage;

    /**
     * 跌幅在3%以内的股票数量
     */
    private Integer down0To3;

    private String down0To3Percentage;

    /**
     * 跌幅在3%至5%的股票数量
     */
    private Integer down3To5;

    private String down3To5Percentage;

    /**
     * 跌幅在5%至7%的股票数量
     */
    private Integer down5To7;

    private String down5To7Percentage;

    /**
     * 跌幅在7%至10%的股票数量
     */
    private Integer down7To10;

    private String down7To10Percentage;

    /**
     * 平盘股票数量
     */
    private Integer priceStop;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTotalUp() {
		return totalUp;
	}

	public void setTotalUp(Integer totalUp) {
		this.totalUp = totalUp;
	}

	public Integer getTotalDown() {
		return totalDown;
	}

	public void setTotalDown(Integer totalDown) {
		this.totalDown = totalDown;
	}

	public Integer getLimitUp() {
		return limitUp;
	}

	public void setLimitUp(Integer limitUp) {
		this.limitUp = limitUp;
	}

	public Integer getRealLimitUp() {
		return realLimitUp;
	}

	public void setRealLimitUp(Integer realLimitUp) {
		this.realLimitUp = realLimitUp;
	}

	public Integer getLimitDown() {
		return limitDown;
	}

	public void setLimitDown(Integer limitDown) {
		this.limitDown = limitDown;
	}

	public Integer getRealLimitDown() {
		return realLimitDown;
	}

	public void setRealLimitDown(Integer realLimitDown) {
		this.realLimitDown = realLimitDown;
	}

	public Integer getUp0To3() {
		return up0To3;
	}

	public void setUp0To3(Integer up0To3) {
		this.up0To3 = up0To3;
	}

	public String getUp0To3Percentage() {
		return up0To3Percentage;
	}

	public void setUp0To3Percentage(String up0To3Percentage) {
		this.up0To3Percentage = up0To3Percentage;
	}

	public Integer getUp3To5() {
		return up3To5;
	}

	public void setUp3To5(Integer up3To5) {
		this.up3To5 = up3To5;
	}

	public String getUp3To5Percentage() {
		return up3To5Percentage;
	}

	public void setUp3To5Percentage(String up3To5Percentage) {
		this.up3To5Percentage = up3To5Percentage;
	}

	public Integer getUp5To7() {
		return up5To7;
	}

	public void setUp5To7(Integer up5To7) {
		this.up5To7 = up5To7;
	}

	public String getUp5To7Percentage() {
		return up5To7Percentage;
	}

	public void setUp5To7Percentage(String up5To7Percentage) {
		this.up5To7Percentage = up5To7Percentage;
	}

	public Integer getUp7To10() {
		return up7To10;
	}

	public void setUp7To10(Integer up7To10) {
		this.up7To10 = up7To10;
	}

	public String getUp7To10Percentage() {
		return up7To10Percentage;
	}

	public void setUp7To10Percentage(String up7To10Percentage) {
		this.up7To10Percentage = up7To10Percentage;
	}

	public Integer getDown0To3() {
		return down0To3;
	}

	public void setDown0To3(Integer down0To3) {
		this.down0To3 = down0To3;
	}

	public String getDown0To3Percentage() {
		return down0To3Percentage;
	}

	public void setDown0To3Percentage(String down0To3Percentage) {
		this.down0To3Percentage = down0To3Percentage;
	}

	public Integer getDown3To5() {
		return down3To5;
	}

	public void setDown3To5(Integer down3To5) {
		this.down3To5 = down3To5;
	}

	public String getDown3To5Percentage() {
		return down3To5Percentage;
	}

	public void setDown3To5Percentage(String down3To5Percentage) {
		this.down3To5Percentage = down3To5Percentage;
	}

	public Integer getDown5To7() {
		return down5To7;
	}

	public void setDown5To7(Integer down5To7) {
		this.down5To7 = down5To7;
	}

	public String getDown5To7Percentage() {
		return down5To7Percentage;
	}

	public void setDown5To7Percentage(String down5To7Percentage) {
		this.down5To7Percentage = down5To7Percentage;
	}

	public Integer getDown7To10() {
		return down7To10;
	}

	public void setDown7To10(Integer down7To10) {
		this.down7To10 = down7To10;
	}

	public String getDown7To10Percentage() {
		return down7To10Percentage;
	}

	public void setDown7To10Percentage(String down7To10Percentage) {
		this.down7To10Percentage = down7To10Percentage;
	}

	public Integer getPriceStop() {
		return priceStop;
	}

	public void setPriceStop(Integer priceStop) {
		this.priceStop = priceStop;
	}
    
}

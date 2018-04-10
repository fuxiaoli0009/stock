package com.stock.dataobject;

import lombok.Data;

/**
 * @Author: fuxiaoli
 * @Date: 2018/4/6
 **/
@Data
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

}

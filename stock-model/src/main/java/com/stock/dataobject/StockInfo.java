package com.stock.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Data
@Table(name="tb_stock")
public class StockInfo {

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

    public String description;

    public int flag;

}

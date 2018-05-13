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
    public String realtimePrice;

    /**
     * 实时时间
     */
    public String realtimeDate;

    /**
     * 买入价格
     */
    public String buyPrice;

    /**
     * 卖出价格
     */
    public String sellPrice;

    @Transient
    public String buyRate;

    public String description;

}

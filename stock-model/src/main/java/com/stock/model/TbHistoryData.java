package com.stock.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbHistoryData {
    private Integer id;

    private String code;

    private String name;

    private String closeDate;

    private BigDecimal closePrice;

    private String closeRatePercent;

    private BigDecimal closePb;

    private BigDecimal closePe;

    private BigDecimal closePs;

    private BigDecimal closeTotalValue;

    private Date createTime;

    private Date updateTime;

    private String type;

    private String flag;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public String getCloseRatePercent() {
        return closeRatePercent;
    }

    public void setCloseRatePercent(String closeRatePercent) {
        this.closeRatePercent = closeRatePercent;
    }

    public BigDecimal getClosePb() {
        return closePb;
    }

    public void setClosePb(BigDecimal closePb) {
        this.closePb = closePb;
    }

    public BigDecimal getClosePe() {
        return closePe;
    }

    public void setClosePe(BigDecimal closePe) {
        this.closePe = closePe;
    }

    public BigDecimal getClosePs() {
        return closePs;
    }

    public void setClosePs(BigDecimal closePs) {
        this.closePs = closePs;
    }

    public BigDecimal getCloseTotalValue() {
        return closeTotalValue;
    }

    public void setCloseTotalValue(BigDecimal closeTotalValue) {
        this.closeTotalValue = closeTotalValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
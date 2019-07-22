package com.stock.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbHistoryDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbHistoryDataExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCloseDateIsNull() {
            addCriterion("close_date is null");
            return (Criteria) this;
        }

        public Criteria andCloseDateIsNotNull() {
            addCriterion("close_date is not null");
            return (Criteria) this;
        }

        public Criteria andCloseDateEqualTo(String value) {
            addCriterion("close_date =", value, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateNotEqualTo(String value) {
            addCriterion("close_date <>", value, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateGreaterThan(String value) {
            addCriterion("close_date >", value, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateGreaterThanOrEqualTo(String value) {
            addCriterion("close_date >=", value, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateLessThan(String value) {
            addCriterion("close_date <", value, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateLessThanOrEqualTo(String value) {
            addCriterion("close_date <=", value, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateLike(String value) {
            addCriterion("close_date like", value, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateNotLike(String value) {
            addCriterion("close_date not like", value, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateIn(List<String> values) {
            addCriterion("close_date in", values, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateNotIn(List<String> values) {
            addCriterion("close_date not in", values, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateBetween(String value1, String value2) {
            addCriterion("close_date between", value1, value2, "closeDate");
            return (Criteria) this;
        }

        public Criteria andCloseDateNotBetween(String value1, String value2) {
            addCriterion("close_date not between", value1, value2, "closeDate");
            return (Criteria) this;
        }

        public Criteria andClosePriceIsNull() {
            addCriterion("close_price is null");
            return (Criteria) this;
        }

        public Criteria andClosePriceIsNotNull() {
            addCriterion("close_price is not null");
            return (Criteria) this;
        }

        public Criteria andClosePriceEqualTo(BigDecimal value) {
            addCriterion("close_price =", value, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceNotEqualTo(BigDecimal value) {
            addCriterion("close_price <>", value, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceGreaterThan(BigDecimal value) {
            addCriterion("close_price >", value, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("close_price >=", value, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceLessThan(BigDecimal value) {
            addCriterion("close_price <", value, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("close_price <=", value, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceIn(List<BigDecimal> values) {
            addCriterion("close_price in", values, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceNotIn(List<BigDecimal> values) {
            addCriterion("close_price not in", values, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_price between", value1, value2, "closePrice");
            return (Criteria) this;
        }

        public Criteria andClosePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_price not between", value1, value2, "closePrice");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentIsNull() {
            addCriterion("close_rate_Percent is null");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentIsNotNull() {
            addCriterion("close_rate_Percent is not null");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentEqualTo(String value) {
            addCriterion("close_rate_Percent =", value, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentNotEqualTo(String value) {
            addCriterion("close_rate_Percent <>", value, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentGreaterThan(String value) {
            addCriterion("close_rate_Percent >", value, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentGreaterThanOrEqualTo(String value) {
            addCriterion("close_rate_Percent >=", value, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentLessThan(String value) {
            addCriterion("close_rate_Percent <", value, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentLessThanOrEqualTo(String value) {
            addCriterion("close_rate_Percent <=", value, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentLike(String value) {
            addCriterion("close_rate_Percent like", value, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentNotLike(String value) {
            addCriterion("close_rate_Percent not like", value, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentIn(List<String> values) {
            addCriterion("close_rate_Percent in", values, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentNotIn(List<String> values) {
            addCriterion("close_rate_Percent not in", values, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentBetween(String value1, String value2) {
            addCriterion("close_rate_Percent between", value1, value2, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andCloseRatePercentNotBetween(String value1, String value2) {
            addCriterion("close_rate_Percent not between", value1, value2, "closeRatePercent");
            return (Criteria) this;
        }

        public Criteria andClosePbIsNull() {
            addCriterion("close_pb is null");
            return (Criteria) this;
        }

        public Criteria andClosePbIsNotNull() {
            addCriterion("close_pb is not null");
            return (Criteria) this;
        }

        public Criteria andClosePbEqualTo(BigDecimal value) {
            addCriterion("close_pb =", value, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbNotEqualTo(BigDecimal value) {
            addCriterion("close_pb <>", value, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbGreaterThan(BigDecimal value) {
            addCriterion("close_pb >", value, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("close_pb >=", value, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbLessThan(BigDecimal value) {
            addCriterion("close_pb <", value, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("close_pb <=", value, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbIn(List<BigDecimal> values) {
            addCriterion("close_pb in", values, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbNotIn(List<BigDecimal> values) {
            addCriterion("close_pb not in", values, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_pb between", value1, value2, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_pb not between", value1, value2, "closePb");
            return (Criteria) this;
        }

        public Criteria andClosePeIsNull() {
            addCriterion("close_pe is null");
            return (Criteria) this;
        }

        public Criteria andClosePeIsNotNull() {
            addCriterion("close_pe is not null");
            return (Criteria) this;
        }

        public Criteria andClosePeEqualTo(BigDecimal value) {
            addCriterion("close_pe =", value, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeNotEqualTo(BigDecimal value) {
            addCriterion("close_pe <>", value, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeGreaterThan(BigDecimal value) {
            addCriterion("close_pe >", value, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("close_pe >=", value, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeLessThan(BigDecimal value) {
            addCriterion("close_pe <", value, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("close_pe <=", value, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeIn(List<BigDecimal> values) {
            addCriterion("close_pe in", values, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeNotIn(List<BigDecimal> values) {
            addCriterion("close_pe not in", values, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_pe between", value1, value2, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_pe not between", value1, value2, "closePe");
            return (Criteria) this;
        }

        public Criteria andClosePsIsNull() {
            addCriterion("close_ps is null");
            return (Criteria) this;
        }

        public Criteria andClosePsIsNotNull() {
            addCriterion("close_ps is not null");
            return (Criteria) this;
        }

        public Criteria andClosePsEqualTo(BigDecimal value) {
            addCriterion("close_ps =", value, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsNotEqualTo(BigDecimal value) {
            addCriterion("close_ps <>", value, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsGreaterThan(BigDecimal value) {
            addCriterion("close_ps >", value, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("close_ps >=", value, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsLessThan(BigDecimal value) {
            addCriterion("close_ps <", value, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("close_ps <=", value, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsIn(List<BigDecimal> values) {
            addCriterion("close_ps in", values, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsNotIn(List<BigDecimal> values) {
            addCriterion("close_ps not in", values, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_ps between", value1, value2, "closePs");
            return (Criteria) this;
        }

        public Criteria andClosePsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_ps not between", value1, value2, "closePs");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueIsNull() {
            addCriterion("close_total_value is null");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueIsNotNull() {
            addCriterion("close_total_value is not null");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueEqualTo(BigDecimal value) {
            addCriterion("close_total_value =", value, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueNotEqualTo(BigDecimal value) {
            addCriterion("close_total_value <>", value, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueGreaterThan(BigDecimal value) {
            addCriterion("close_total_value >", value, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("close_total_value >=", value, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueLessThan(BigDecimal value) {
            addCriterion("close_total_value <", value, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("close_total_value <=", value, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueIn(List<BigDecimal> values) {
            addCriterion("close_total_value in", values, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueNotIn(List<BigDecimal> values) {
            addCriterion("close_total_value not in", values, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_total_value between", value1, value2, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCloseTotalValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("close_total_value not between", value1, value2, "closeTotalValue");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(String value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(String value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(String value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(String value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(String value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(String value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLike(String value) {
            addCriterion("flag like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotLike(String value) {
            addCriterion("flag not like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<String> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<String> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(String value1, String value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(String value1, String value2) {
            addCriterion("flag not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
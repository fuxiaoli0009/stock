package com.stock.utils;

import net.sf.cglib.beans.BeanMap;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: fuxiaoli
 * @Date: 2018/4/6
 **/
public class StockUtil {

    /**
     * 数据抽取自：https://www.legulegu.com/stockdata/market-activity
     */
    private static String REGEX = "id=\"\\w+\"\n\\s+data-chart='\\d+'";

    /**
     * 获取股票涨跌数据
     * @param response
     * @return
     */
    public static Map<String, Integer> findRiseAndFallData(String response){
        Map<String, Integer> riseAndFallData = new HashMap<String, Integer>();
        Pattern pattern = Pattern.compile(StockUtil.REGEX);
        Matcher matcher = pattern.matcher(response);
        while(matcher.find())
            riseAndFallData.put(matcher.group().split("\"")[1], Integer.parseInt(matcher.group().split("\'")[1]));
        return riseAndFallData;
    }

    /**
     * BeanMap实现map转对象
     * @param map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Integer> map,T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 计算两个数的百分比,保留小数后两位
     * @param obj1
     * @param obj2
     * @return
     */
    public static String calPercentage(Float obj1, Float obj2){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format((obj1) /  obj2 * 100) + "%";
    }

}

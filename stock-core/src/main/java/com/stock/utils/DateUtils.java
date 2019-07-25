package com.stock.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 返回昨天
	 * 
	 * @param today
	 * @return
	 */
	public static Date yesterday(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		return calendar.getTime();
	}
}

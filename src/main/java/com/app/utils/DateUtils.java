package com.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static final String PATTERN_DATE_FORMAT = "dd/MM/yyyy";
	public static final String PATTERN_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static Date convertStringToDate(String dateString, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String convertDateToString(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date addDays(Date date, Integer numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
		return calendar.getTime();
	}

}

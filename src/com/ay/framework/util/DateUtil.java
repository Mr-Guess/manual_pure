package com.ay.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作类
 * 
 * @author zxy
 *
 */
public class DateUtil {
	private DateUtil() {
	}

	public static final String ISO_DATETIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	public static final String ISO_DATETIME_WITH_MILLISECOND_FORMAT = "dd-MMM-yyyy HH:mm:ss.SSS";
	public static final String ISO_DATE_FORMAT = "dd-MMM-yyyy";
	public static final String ISO_SHORT_DATE_FORMAT = "dd-MMM-yy";
	public static final String ISO_TIME_FORMAT = "HH:mm:ss";
	public static final String ISO_TIME_WITH_MILLISECOND_FORMAT = "HH:mm:ss.SSS";
	public static final String UNIX_LONG_DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
	public static final String US_DATE_FORMAT = "MM/dd/yyyy";
	public static final String US_SHORT_DATE_FORMAT = "MM/dd/yy";

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将日期格式化为特定的字符串(Date to String)
	 * 
	 * @param date
	 *            java.util.Date
	 * @param format
	 *            可以为null,默认格式为"yyyy-MM-dd HH:mm:ss"
	 * @return 返回"yyyy-MM-dd"格式的字符串,如果为空或null则的返回空
	 * @throws Exception
	 */
	public static String format(Date date, String format) throws Exception {
		if (date == null)
			return "";
		try {
			if (format == null) {
				format = DATETIME_FORMAT;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception ex) {
			Exception ue = new Exception("日期格式化产生异常", ex);
			throw ue;
		} catch (Throwable t) {
			throw new Exception("日期格式化产生异常", t);
		}
	}

	/**
	 * 将日期格式化为特定的字符串(Date to String)
	 * 
	 * @param date
	 *            java.util.Date
	 * @return 返回"yyyy-MM-dd"格式的字符串
	 * @throws Exception
	 */
	public static String format(Date date) throws Exception {

		return format(date, null);
	}

	/**
	 * 将日期字符串解析为日期类型(String to Date)
	 * 
	 * @param dateString
	 *            不可心为null或空
	 * @param format
	 *            可以为null,默认格式化"yyyy-MM-dd"
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String dateString, String format) throws Exception {

		try {
			if (format == null) {
				format = DATETIME_FORMAT;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateString);
		} catch (Exception ex) {
			Exception ue = new Exception("格式化日期产生异常", ex);
			throw ue;
		} catch (Throwable t) {
			throw new Exception("格式化日期产生异常", t);
		}
	}

	/**
	 * 将日期格式化为特定的字符串(Date to String)
	 * 
	 * @param dateString
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String dateString) throws Exception {

		return parse(dateString, null);
	}

	/**
	 * 比较两个日期间隔，返回天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getPeriodDate(Date d1, Date d2) {
		return (d1.getTime() - d2.getTime()) / 1000 / 60 / 60 / 24;
	}

	/***
	 * 得到两个时间相隔的时间差
	 * 
	 * @param start
	 *            开始时间字符串 yyyy-MM-dd HH:mm:ss
	 * @param end
	 *            结束时间字符串 yyyy-MM-dd HH:mm:ss
	 * @return XX天XX小时XX分XX秒
	 */
	public static String getTimelag(String start, String end) throws Exception {
		Date now;
		Date date;
		try {
			now = parse(start);
			date = parse(end);
			return getTimelag(now, date);
		} catch (Exception ex) {
			Exception ue = new Exception("获取间隔日期时产生异常", ex);
			throw ue;
		}
	}

	/***
	 * 得到两个时间相隔的时间差
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return XX天XX小时XX分XX秒
	 */
	public static String getTimelag(Date start, Date end) throws Exception {
		try {
			long l = end.getTime() - start.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			return "" + day + "天" + hour + "小时" + min + "分" + s + "秒";

		} catch (Exception ex) {
			Exception ue = new Exception("获取间隔日期时产生异常", ex);
			throw ue;
		}
	}

	/**
	 * 得到当天时间的区域 如：2011-11-04 00：00：00 ~ 2011-11-04 23：59：59
	 * @return
	 */
	public static String[] getCurrentTimeRange() {
		Calendar start = Calendar.getInstance();

		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);

		Calendar end = Calendar.getInstance();

		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);

		String[] timeRange = new String[2];
		try {
			timeRange[0] = DateUtil.format(start.getTime());
			timeRange[1] = DateUtil.format(end.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeRange;
	}

	/**
	 * 得到两个日期的时间区域
	 * 
	 * @param day
	 * @return
	 */
	public static String[] getTimeRange(String day) {
		if (StringUtil.isNullOrEmpty(day)){
			day = getSomeDay(0);
		}
		
		Calendar start = Calendar.getInstance();
		try {
			start.setTime(DateUtil.parse(day, DATE_FORMAT));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);

		Calendar end = Calendar.getInstance();
		try {
			end.setTime(DateUtil.parse(day, DATE_FORMAT));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);

		String[] timeRange = new String[2];
		try {
			timeRange[0] = DateUtil.format(start.getTime());
			timeRange[1] = DateUtil.format(end.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeRange;
	}

	/**
	 * 得到某天
	 * 
	 * @param interval
	 * @return
	 */
	public static String getSomeDay(int interval) {
		String day = null;
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH)
				+ interval);

		try {
			day = DateUtil.format(now.getTime(), DateUtil.DATE_FORMAT);
		} catch (Exception e) {
		}
		return day;
	}
	
	/**
	 * 得到某天
	 * 
	 * @param interval
	 * @return
	 */
	public static Date getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(new Date());
        Date nowDate = new Date();
		try {
			nowDate = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nowDate;
	}
}

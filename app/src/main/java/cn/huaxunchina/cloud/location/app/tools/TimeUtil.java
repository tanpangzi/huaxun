package cn.huaxunchina.cloud.location.app.tools;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;

/**
 * 时间差工具类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-19 上午9:39:39
 */
public class TimeUtil {
	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return long[] 返回值为：{天, 时, 分, 秒}
	 */
	@SuppressLint("SimpleDateFormat")
	public static long[] getDistanceTimes(String str1, String str2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			diff = time2 - time1;
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			// sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min };
		return times;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static  long getDistanceMin(String str1, String str2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date one;
		Date two;
		long hour = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			diff = time2 - time1;
			hour = diff / (60* 1000);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hour;
	}

	@SuppressLint("SimpleDateFormat")
	public static int getDistanceValue(String str1, String str2) {
		java.text.DateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(str1));
			c2.setTime(df.parse(str2));
		} catch (ParseException e) {
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);
		return result;
	}

	/**
	 * 获取系统当前时间时分值
	 */

	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		// c.setTimeInMillis(new Date().getTime());
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return dateFormat.format(c.getTime());
		//
		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		// 设置日期的显示
		Calendar calendar = c;
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		return decimal.format(hour) + ":" + decimal.format(min);

	}

	/**
	 * 获取当前年月
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurYMothTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String date = sdf.format(new Date());
		return date.replace("-", "");

	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getCurYMothTime1() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String date = sdf.format(curDate);
		return date;
	}
	@SuppressLint("SimpleDateFormat")
	public static String getCurYMothTime2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String date = sdf.format(curDate);
		return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String formatYmd(String date) {
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat("yyyyMMddHHmm").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(date2);

	}
	
	@SuppressLint("SimpleDateFormat")
	public static String formatHm(String date) {
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat("yyyyMMddHHmm").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("HH:mm").format(date2);

	}
	

}

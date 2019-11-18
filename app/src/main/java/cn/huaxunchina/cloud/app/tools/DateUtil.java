package cn.huaxunchina.cloud.app.tools;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.huaxunchina.cloud.app.common.Constant;

import android.annotation.SuppressLint;

public class DateUtil {
	@SuppressLint("SimpleDateFormat")
	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatStringDate(String date) {
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2);

	}

	@SuppressLint("SimpleDateFormat")
	public static String formatDateAndTime(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatDateAndTime(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 将日期信息转换成今天、明天、后天、星期
	 * 
	 * @param date
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateDetail(String date) {
		Calendar today = Calendar.getInstance();
		Calendar old = Calendar.getInstance();
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			old.setTime(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 此处好像是去除0
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		old.set(Calendar.HOUR, 0);
		old.set(Calendar.MINUTE, 0);
		old.set(Calendar.SECOND, 0);
		// 老的时间减去今天的时间
		long intervalMilli = old.getTimeInMillis() - today.getTimeInMillis(); // -43200616
																				// 86400000
		int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
		// -2:前天 -1：昨天 0：今天 1：明天 2：后天， out：显示日期
		// if (xcts >= -2 && xcts <= 2) {
		// return String.valueOf(xcts);
		// } else {
		// return "out";
		// }

		return showDateDetail(xcts, old, date2);

	}

	/**
	 * 获取时间差
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */

	@SuppressLint("SimpleDateFormat")
	public static String getDateInterval(String endDate, String startDate) {
		long hour = 0;
		long day = 0;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long diff;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endDate).getTime() - sd.parse(startDate).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh;// 计算差多少小时
			double min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			if (day == 0 && hour > 0) {
				return String.valueOf(hour) + "小时";
			} else if (day > 0 && hour == 0 && min == 0) {
				return day + "天";
			}
			if (hour == 0 && min > 0) {
				return day + "天"
						+ String.valueOf(((min / 60) >= 0.5 ? 1 : 0.5)) + "小时";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day + "天" + String.valueOf(hour) + "小时";
	}

	/**
	 * 将日期转换成long类型值后进行相减值
	 */
	@SuppressLint("SimpleDateFormat")
	public static long dateDiff(String startDate, String endDate) {
		Date firstDate = null;
		Date secondDate = null;
		long time_date = 0;
		try {
			firstDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(startDate);
			secondDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(endDate);
			// 得到两个日期对象的总毫秒数
			long firstDateMilliSeconds = firstDate.getTime();
			long secondDateMilliSeconds = secondDate.getTime();
			// 得到两者之差
			time_date = secondDateMilliSeconds - firstDateMilliSeconds;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time_date;
	}

	// 日期差值后获取小时值
	@SuppressLint("SimpleDateFormat")
	public static double dateDiffHour(String fromDate, String toDate) {
		long hour = 0;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long diff;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(toDate).getTime() - sd.parse(fromDate).getTime();
			long day = diff / nd;// 计算差多少天
			hour = diff % nd / nh;// 计算差多少小时
			double min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			if (hour == 0 && min > 0) {
				return ((min / 60) >= 0.5 ? 1 : 0.5);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hour;

	}

	/**
	 * 将日期差显示为日期或者星期
	 * 
	 * @param xcts
	 * @param target
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private static String showDateDetail(int xcts, Calendar target, Date date) {
		switch (xcts) {
		case 0:
			return Constant.TODAY;
		case 1:
			return Constant.TOMORROW;// 明天
		case 2:
			return Constant.AFTER_TOMORROW;
		case -1:
			return Constant.YESTERDAY;
		case -2:
			return Constant.BEFORE_YESTERDAY;
		default:
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
			// int dayForWeek = 0;
			// dayForWeek = target.get(Calendar.DAY_OF_WEEK);
			// switch(dayForWeek){
			// case 1: return Constant.SUNDAY;
			// case 2: return Constant.MONDAY;
			// case 3: return Constant.TUESDAY;
			// case 4: return Constant.WEDNESDAY;
			// case 5: return Constant.THURSDAY;
			// case 6: return Constant.FRIDAY;
			// case 7: return Constant.SATURDAY;
			// default:return null;
			// }

		}
	}

	public static String converTime(long timestamp) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > 24 * 60 * 60) {// 1天以上
			timeStr = timeGap / (24 * 60 * 60) + "天前";
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "小时前";
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getStandardTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentMonth() {
		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return decimal.format(month + 1) + "月" + decimal.format(day) + "日";
	}

}

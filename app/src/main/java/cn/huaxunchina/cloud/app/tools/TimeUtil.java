package cn.huaxunchina.cloud.app.tools;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import cn.huaxunchina.cloud.app.common.Constant;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {

	public static String TimetoString(String time) {
		String strTime = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = null;
		try {
			today = dateFormat.parse(time);
			DateFormat chtDateFormat = new SimpleDateFormat("MM-dd HH:mm");
			strTime = chtDateFormat.format(today);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strTime;
	}

	public static String formatStringDate(String date) {
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2);

	}

	public static long formatLongDate(String date) {
		Date date2 = null;
		long lTime = 0;
		try {
			date2 = new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
			lTime = date2.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lTime;
	}

	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public static String getCurrentDate2() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 获取指定日后 后 dayAddNum 天的 日期
	 * 
	 * @param day
	 *            日期，格式为String："2013-9-3";
	 * @param dayAddNum
	 *            增加天数 格式为int;
	 * @return
	 */
	public static String getDateStr(String day, int dayAddNum) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = null;
		try {
			nowDate = df.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date newDate2 = new Date(nowDate.getTime() - dayAddNum * 24 * 60 * 60
				* 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateOk = simpleDateFormat.format(newDate2);
		return dateOk;
	}

	/**
	 * 计算传入字符串的时间差
	 */

	public static long getDiffTime(String nowTime, String relTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d1, d2;
		long days = 0;
		try {
			d1 = formatter.parse(nowTime);
			d2 = formatter.parse(relTime);
			long diff = d1.getTime() - d2.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 计算传入字符串的与当前时间的时间差
	 */

	public static long getDiffTime(String relTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d1, d2;
		long days = 0;
		try {
			d1 = formatter.parse(TimeUtil.getCurrentDate2());
			d2 = formatter.parse(relTime);
			long diff = d1.getTime() - d2.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	public static String formatDateT(String time) {
		time = time.replace("T", " ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d1;
		String new_time = null;
		try {
			d1 = sdf.parse(time);
			new_time = sdf.format(d1);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return new_time;
	}

	public static String formatDateT2(String time) {
		time = time.replace("T", " ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1;
		String new_time = null;
		try {
			d1 = sdf.parse(time);
			new_time = sdf.format(d1);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return new_time;
	}

	public static String formatDateT3(String time) {
		time = time.replace("T", " ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d1;
		String new_time = null;
		try {
			d1 = sdf.parse(time);
			new_time = sdf.format(d1);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return new_time.substring(11, new_time.length());
	}
	
	/**
	 * 将日期信息转换成今天、明天、后天、星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateDetail(String date,String toady) {
		Date data1 = null;
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date.replace("T"," "));
			if(toady==null){
				data1 =new Date(System.currentTimeMillis()); //获取当前时间
			}else{
				data1 = new SimpleDateFormat("yyyy-MM-dd").parse(toady.replace("T", " "));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long diff = date2.getTime()-data1.getTime() ;
		int days = (int) diff / (1000 * 60 * 60 * 24);
		return showDateDetail(days, date);

	}

	/**
	 * 将日期差显示为日期或者星期
	 * 
	 * @param xcts
	 * @param target
	 * @return
	 */
	private static String showDateDetail(int days, String date) {
		switch (days) {
		case 0:
			return formatDateT3(date);
		case -1:
			return Constant.YESTERDAY;
		default:
			return formatDateT2(date);
		}
	}
}

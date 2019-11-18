package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

/**
 * 闹钟设置数据指令
 * @author zoupeng@huaxunchina.cn
 *
 * 2015-1-12 下午4:53:02
 */
@SuppressWarnings("serial")
public class AlarmSetModel implements Serializable {
 
	private String c="alarmsetting";
	private String imei; //imei号
	private String Alarm1="Bool"; //AlarmX：闹钟开关
	private int AlarmType1; //AlarmtypeX：闹钟类型，1-一次性闹钟 2-工作日闹钟 3-每日闹钟
	private String AlarmTime1; //AlarmTimeX：闹钟时间，若闹钟类型为一次性闹钟，其格式为YYYYMMDDHHMM，若闹钟类型为工作日闹钟或者每日闹钟，则其格式为HH:MM，只支持24小时制 
	private String Alarm2="Bool";
	private int AlarmType2;
	private String AlarmTime2;
	private String Alarm3="Bool";
	private int AlarmType3;
	private String AlarmTime3;
	
	
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getAlarm1() {
		return Alarm1;
	}
	public void setAlarm1(String alarm1) {
		Alarm1 = alarm1;
	}
	public int getAlarmType1() {
		return AlarmType1;
	}
	public void setAlarmType1(int alarmType1) {
		AlarmType1 = alarmType1;
	}
	public String getAlarmTime1() {
		return AlarmTime1;
	}
	public void setAlarmTime1(String alarmTime1) {
		AlarmTime1 = alarmTime1;
	}
	public String getAlarm2() {
		return Alarm2;
	}
	public void setAlarm2(String alarm2) {
		Alarm2 = alarm2;
	}
	public int getAlarmType2() {
		return AlarmType2;
	}
	public void setAlarmType2(int alarmType2) {
		AlarmType2 = alarmType2;
	}
	public String getAlarmTime2() {
		return AlarmTime2;
	}
	public void setAlarmTime2(String alarmTime2) {
		AlarmTime2 = alarmTime2;
	}
	public String getAlarm3() {
		return Alarm3;
	}
	public void setAlarm3(String alarm3) {
		Alarm3 = alarm3;
	}
	public int getAlarmType3() {
		return AlarmType3;
	}
	public void setAlarmType3(int alarmType3) {
		AlarmType3 = alarmType3;
	}
	public String getAlarmTime3() {
		return AlarmTime3;
	}
	public void setAlarmTime3(String alarmTime3) {
		AlarmTime3 = alarmTime3;
	}
	
}

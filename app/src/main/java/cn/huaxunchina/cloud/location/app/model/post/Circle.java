package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;
/**
 * 围栏基本信息
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月4日 下午3:23:42 
 *
 */
@SuppressWarnings("serial")
public class Circle implements Serializable {
	
	 
	private String startTime;//开始时间
	private double positionLat;//位置报警中心点纬度
	private int Circle_id;//围栏ID
	private boolean onoff;//是否开启
	private String pointAddress;//电子围栏地址
	private int repeatDay;//
	private boolean positionAlarm;//位置报警是否开启
	private int positionRadius;//半径
	private String endTime;//结束时间
	private String positionName;//位置报警名称
	private String noticeNumber;//通知号码
	private double positionLng;//位置报警中心点经度
	private int noticeType;//通知类型
	
   /* private int type;//1.添加  2.修改 3.删除
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}*/
	public String getPointAddress() {
		if(this.pointAddress==null){
			this.pointAddress="";
		}
		return this.pointAddress;
	}
	public void setPointAddress(String pointAddress) {
		this.pointAddress = pointAddress;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public boolean isOnoff() {
		return onoff;
	}
	public void setOnoff(boolean onoff) {
		this.onoff = onoff;
	}
	public int getRepeatDay() {
		return repeatDay;
	}
	public void setRepeatDay(int repeatDay) {
		this.repeatDay = repeatDay;
	}
	public int getPositionRadius() {
		return positionRadius;
	}
	public void setPositionRadius(int positionRadius) {
		this.positionRadius = positionRadius;
	}
	public String getNoticeNumber() {
		return noticeNumber;
	}
	public void setNoticeNumber(String noticeNumber) {
		this.noticeNumber = noticeNumber;
	}
	public int getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}
	public int getCircle_id() {
		return Circle_id;
	}
	public void setCircle_id(int circle_id) {
		Circle_id = circle_id;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public boolean isPositionAlarm() {
		return positionAlarm;
	}
	public void setPositionAlarm(boolean positionAlarm) {
		this.positionAlarm = positionAlarm;
	}
	public double getPositionLat() {
		return positionLat;
	}
	public void setPositionLat(double positionLat) {
		this.positionLat = positionLat;
	}
	public double getPositionLng() {
		return positionLng;
	}
	public void setPositionLng(double positionLng) {
		this.positionLng = positionLng;
	}
	
	
	
}

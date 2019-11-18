package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class School implements Serializable {

	private String schoolId;
	private String schoolName;
	private int weekTotal;
	private int currentWeek;
	
	public int getCurrentWeek() {
		return currentWeek;
	}
	public void setCurrentWeek(int currentWeek) {
		this.currentWeek = currentWeek;
	}
	public int getWeekTotal() {
		return weekTotal;
	}
	public void setWeekTotal(int weekTotal) {
		this.weekTotal = weekTotal;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
}

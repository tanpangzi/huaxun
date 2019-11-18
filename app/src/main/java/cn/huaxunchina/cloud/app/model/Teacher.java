package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

public class Teacher implements Serializable {

	private String sex;
	private String teacherID;
	private String teacherName;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
}

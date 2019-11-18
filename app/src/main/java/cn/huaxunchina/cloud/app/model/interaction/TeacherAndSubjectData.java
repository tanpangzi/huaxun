package cn.huaxunchina.cloud.app.model.interaction;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TeacherAndSubjectData implements Serializable {
	private String subject; // 科目
	private String teacherName; // 老师名
	private String teacherId;//老师id

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

}

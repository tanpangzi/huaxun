package cn.huaxunchina.cloud.app.model.homewrok;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HomeWrokCourse implements Serializable {
	private String courseName; // 课程名称
	private int courseId; // 课程Id（唯一）
	private int classId;// 班级Id

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

}

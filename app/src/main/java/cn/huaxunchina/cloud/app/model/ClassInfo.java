package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClassInfo implements Serializable {

	public String classId; // 班级id
	public String className; // 班级名称
	public String classNum;
	public EduGrade eduGrade;//年级对象列表

	public EduGrade getEduGrade() {
		return eduGrade;
	}

	public void setEduGrade(EduGrade eduGrade) {
		this.eduGrade = eduGrade;
	}

	public String getClassId() {
		if(classId==null)
		return "";
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

}

package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * 
 * 课程表
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月15日 上午9:10:00 
 *
 */
public class Syllabus1 implements Serializable {
	
	private String classesName;//班级名称
	private String courseName;//课程表科目
	private String name;//第几节的课
	private String tearcherName;//老师名称
	
	public String getClassesName() {
		return classesName;
	}
	public void setClassesName(String classesName) {
		this.classesName = classesName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTearcherName() {
		return tearcherName;
	}
	public void setTearcherName(String tearcherName) {
		this.tearcherName = tearcherName;
	}
	

}

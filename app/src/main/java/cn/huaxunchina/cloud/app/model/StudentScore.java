package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * 学生考试成绩
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月14日 下午3:51:57 
 *
 */
public class StudentScore implements Serializable{
	
	private String examId;//考试id
	private String examTypeName;//考试类型
	private int courseId;
	private String courseName;//科目
	private String score;//成绩
	private String studentName;//学生名字
	private String studentId;//学生id
	private String commentt;//评语
	private String examTime;
	
	
	
	public String getExamTime() {
		if(examTime==null){
			return "";
		}
		int length = examTime.length();
		if(length>10){
			examTime=examTime.substring(0, 10);
		}
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public String getCommentt() {
		return commentt;
	}
	public void setCommentt(String commentt) {
		this.commentt = commentt;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getExamTypeName() {
		return examTypeName;
	}
	public void setExamTypeName(String examTypeName) {
		this.examTypeName = examTypeName;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	
	

}

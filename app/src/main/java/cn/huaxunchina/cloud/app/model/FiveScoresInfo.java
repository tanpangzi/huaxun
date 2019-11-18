package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * 五次考试详情
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月5日 上午9:45:37 
 *
 */
public class FiveScoresInfo implements Serializable {

	private String examId;//考试id
	private int courseId;//科目id
	private String courseName;//科目名称
	private int score;//成绩
	private String scoreId;//考试id
	private String scoreNo;//考试编号
	private String studentName;//学生名字
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	 
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getScoreId() {
		return scoreId;
	}
	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}
	public String getScoreNo() {
		return scoreNo;
	}
	public void setScoreNo(String scoreNo) {
		this.scoreNo = scoreNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	
}

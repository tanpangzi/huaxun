package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月13日 下午3:08:24 
 *
 */
@SuppressWarnings("serial")
public class Score implements Serializable {
	
	private String className;//班级名称
	private String examId;//考试id
	private String examNo;//考试标题
	private String examTime;//考试时间
	
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getExamNo() {
		return examNo;
	}
	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}
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
	
	
	
}

package cn.huaxunchina.cloud.app.model.homewrok;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HomeWrokProperty implements Serializable {
	private String homeworkId;// 家庭作业id
	private String content; // 作业内容
	private String releaseTime;// 发布时间
	private String courseName;// 科目
	private String tips;// 家庭作业提示答案
	private String classId;//班级id
    private String  nowTime;//当前时间
    private String teacherid; //发布人id
    private boolean isCheck=false; //是否选择
    private boolean isDel=false; //是否删除
    private boolean isClick=false;//是否可点击

	public boolean isDel() {
		return isDel;
	}

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(String homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

}

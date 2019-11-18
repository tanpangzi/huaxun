package cn.huaxunchina.cloud.app.model.homewrok;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HomeWorkDetailProperty implements Serializable {

	private String releaseTime;
	private String content;
	private String tips;
    private String courseName;//课程名字 
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EduGrade implements Serializable {

	private String gradeId;
	private String gradeName;

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

}

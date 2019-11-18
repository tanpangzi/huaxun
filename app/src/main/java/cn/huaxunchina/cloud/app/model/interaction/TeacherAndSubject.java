package cn.huaxunchina.cloud.app.model.interaction;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TeacherAndSubject implements Serializable {
	private List<TeacherAndSubjectData> data;

	public List<TeacherAndSubjectData> getData() {
		return data;
	}

	public void setData(List<TeacherAndSubjectData> data) {
		this.data = data;
	}

}

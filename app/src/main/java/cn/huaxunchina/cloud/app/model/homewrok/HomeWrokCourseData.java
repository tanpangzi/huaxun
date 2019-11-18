package cn.huaxunchina.cloud.app.model.homewrok;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class HomeWrokCourseData implements Serializable {
	private List<HomeWrokCourse> data;

	public List<HomeWrokCourse> getData() {
		return data;
	}

	public void setData(List<HomeWrokCourse> data) {
		this.data = data;
	}

}

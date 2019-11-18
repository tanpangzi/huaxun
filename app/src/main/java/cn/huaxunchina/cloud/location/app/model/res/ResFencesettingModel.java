package cn.huaxunchina.cloud.location.app.model.res;

import java.io.Serializable;
import java.util.List;

import cn.huaxunchina.cloud.location.app.model.post.BaseModel;
import cn.huaxunchina.cloud.location.app.model.post.Circle;

@SuppressWarnings("serial")
public class ResFencesettingModel extends BaseModel implements Serializable {
	
	private List<Circle> data;

	public List<Circle> getData() {
		return data;
	}

	public void setData(List<Circle> data) {
		this.data = data;
	}
	
	

}

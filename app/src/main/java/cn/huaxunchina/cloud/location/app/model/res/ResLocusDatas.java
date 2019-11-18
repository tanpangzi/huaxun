package cn.huaxunchina.cloud.location.app.model.res;

import java.io.Serializable;
import java.util.List;

public class ResLocusDatas implements Serializable {
	
	
	private List<ResLocusModel> data;

	public List<ResLocusModel> getData() {
		return data;
	}

	public void setData(List<ResLocusModel> data) {
		this.data = data;
	}
	

}

package cn.huaxunchina.cloud.app.model.todayread;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TodayDetailDataArray implements Serializable {
	private List<TodayReadData> items;

	public List<TodayReadData> getItems() {
		return items;
	}

	public void setItems(List<TodayReadData> items) {
		this.items = items;
	}

}

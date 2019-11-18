package cn.huaxunchina.cloud.app.model.homewrok;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class HomeWrokArray implements Serializable {
	private List<HomeWrokProperty> items;
	private int totalCount; // 总条数
	private int currentPageNo;// 当前页面

	public int getTotalCount() {
		return totalCount;
	}

	public List<HomeWrokProperty> getItems() {
		return items;
	}

	public void setItems(List<HomeWrokProperty> items) {
		this.items = items;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

}

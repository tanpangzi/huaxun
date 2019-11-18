package cn.huaxunchina.cloud.app.model.todayread;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TodayReadDataArray implements Serializable {
	private List<TodayReadData> items;
	private int totalCount;
	private int currentPageNo;

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public List<TodayReadData> getItems() {
		return items;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setItems(List<TodayReadData> items) {
		this.items = items;
	}

}

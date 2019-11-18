package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ScoreData implements Serializable {
	
	private int totalCount;
	private int currentPageNo;
	private List<Score> items;
	public int getTotalCount() {
		return totalCount;
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
	public List<Score> getItems() {
		return items;
	}
	public void setItems(List<Score> items) {
		this.items = items;
	}

	

}

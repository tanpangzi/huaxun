package cn.huaxunchina.cloud.app.model.leave;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class LeaveArray implements Serializable {

	private List<LeaveProperty> items;
	private int totalCount; // 总条数
	private int currentPageNo;//当前页面

	public int getTotalCount() {
		return totalCount;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<LeaveProperty> getItems() {
		return items;
	}

	public void setItems(List<LeaveProperty> items) {
		this.items = items;
	}

	 

}

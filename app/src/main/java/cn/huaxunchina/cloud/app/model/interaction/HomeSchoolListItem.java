package cn.huaxunchina.cloud.app.model.interaction;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class HomeSchoolListItem implements Serializable {
	private List<HomeSchoolListData> items;
	private int totalCount; // 总条数
	private int currentPageNo;

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public List<HomeSchoolListData> getItems() {
		return items;
	}

	public void setItems(List<HomeSchoolListData> items) {
		this.items = items;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}

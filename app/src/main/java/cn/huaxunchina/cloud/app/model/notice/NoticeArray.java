package cn.huaxunchina.cloud.app.model.notice;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class NoticeArray implements Serializable {

	private List<NoticeData> items;
	private int totalCount;// 总条数
	private int currentPageNo;

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public List<NoticeData> getItems() {
		return items;
	}

	public void setItems(List<NoticeData> items) {
		this.items = items;
	}


}

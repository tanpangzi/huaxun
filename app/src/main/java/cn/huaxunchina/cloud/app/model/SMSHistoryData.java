package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * 历史短信列表接口返回数据模型
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-3-12 上午10:34:29
 */
@SuppressWarnings("serial")
public class SMSHistoryData implements Serializable {
	private List<SMSHistory> items; // 短信列表
	private int totalCount; // 总条数
	private int currentPageNo;// 当前页面

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

	public List<SMSHistory> getItems() {
		return items;
	}

	public void setItems(List<SMSHistory> items) {
		this.items = items;
	}

}

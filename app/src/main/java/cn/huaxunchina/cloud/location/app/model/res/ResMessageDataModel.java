package cn.huaxunchina.cloud.location.app.model.res;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-21 下午12:17:28
 */
@SuppressWarnings("serial")
public class ResMessageDataModel implements Serializable {

	private List<ResMessageModel> items;
	private int totalCount; // 总条数
	private int currentPageNo;// 当前页面
	
	/*public boolean isLastPage(){
		
	}*/
	
	public List<ResMessageModel> getItems() {
		return items;
	}

	public void setItems(List<ResMessageModel> items) {
		this.items = items;
	}

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

	

}

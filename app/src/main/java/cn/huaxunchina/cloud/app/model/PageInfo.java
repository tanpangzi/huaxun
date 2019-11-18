package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PageInfo implements Serializable {

	private int numCount;// 总条数
	private int curPage = 1;// 当前页
	private int pageSize = 10;// 每页多条
	private int pageCount;// 总页数
	private int indexId = 0;// 索引id
	private boolean lastPage = true;// 是否还有下一页面

	public PageInfo() {

	}

	public PageInfo(int pageSize, int numCount, int curPage) {
		this.pageSize = pageSize;
		this.numCount = numCount;
		this.curPage = curPage;

	}

	public int getNumCount() {
		return numCount;
	}

	public void setNumCount(int numCount) {
		this.numCount = numCount;
	}

	public int getCurPage() {

		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		pageCount = numCount / pageSize;
		if ((numCount % pageSize) > 0) {
			pageCount++;
		}
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getIndexId() {
		if (curPage == 1) {
			indexId = 0;
		} else if (curPage > 1) {
			indexId = curPage * pageSize - 10;
		}
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public boolean isLastPage() {
		pageCount = getPageCount();
		if (curPage == pageCount) {
			lastPage = true;
		} else {
			lastPage = false;
		}
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

}

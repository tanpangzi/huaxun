package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

/**
 * 消息列表请求模型
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-22 下午2:57:37
 */
@SuppressWarnings("serial")
public class LocMessageModel extends BaseModel implements Serializable {
	private String c="alarmListApp";
	private int type; //请求类型
	private int start; //起始行
	private int limit = 10;//页大小
	private String startDate;//查询时间

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}

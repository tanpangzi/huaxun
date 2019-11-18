package cn.huaxunchina.cloud.app.model.todayread;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TodayDetailData implements Serializable {
	private String content;// 详情内容
	private String title;// 详情主题
	private String origin; // 文章来源
	private String readId; // 文章唯一id
	private String createTime;//创建时间
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String context) {
		this.content = context;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getReadId() {
		return readId;
	}

	public void setReadId(String readId) {
		this.readId = readId;
	}

}

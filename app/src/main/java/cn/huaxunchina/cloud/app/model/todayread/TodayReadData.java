package cn.huaxunchina.cloud.app.model.todayread;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TodayReadData implements Serializable {

	private String title; // 主题
	private String origin; // 文章来源
	private String content;//文章内容
	private String context;
	private String img; // 图片路径
	private String readId; // 阅读资料Id（唯一）
	private boolean isCollect;// 是否已经有收藏
	private String createTime;//发布时间
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	 
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getReadId() {
		return readId;
	}
	public void setReadId(String readId) {
		this.readId = readId;
	}
	public boolean isCollect() {
		return isCollect;
	}
	public void setCollect(boolean isCollect) {
		this.isCollect = isCollect;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrigin() {
		return "来源:"+origin;
	}
 

}

package cn.huaxunchina.cloud.app.model.notice;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoticeData implements Serializable {
	private String content;
	private String publishTime;
	private String title;
	private String imgList[];
	private String publisher;
	private String noticeId;//公告id

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String[] getImgList() {
		return imgList;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public void setImgList(String[] imgList) {
		this.imgList = imgList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

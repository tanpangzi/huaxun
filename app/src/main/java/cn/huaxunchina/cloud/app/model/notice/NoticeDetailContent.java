package cn.huaxunchina.cloud.app.model.notice;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoticeDetailContent implements Serializable {
	private String content; // 通知公告详情内容
	private String noticeId; // 公告ID
	private String publishTime; // 公告发布时间
	private String publisher; // 公告发布人
	private String title; // 公告发布主题

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

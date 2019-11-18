package cn.huaxunchina.cloud.app.model.interaction;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class HomeSchoolDetailData implements Serializable {

	private String content; // 家校互动详情内容
	private List<CommentContent> replyList; // 家校互动评论内容
	private String title;//主题
	private String publishTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getContent() {
		return content;
	}

	public List<CommentContent> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<CommentContent> replyList) {
		this.replyList = replyList;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

package cn.huaxunchina.cloud.app.model.interaction;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommentContent implements Serializable {
	private String content;
	private String replyTime;
	private String type;
	private String replyerName;

	public String getReplyerName() {
		return replyerName;
	}

	public void setReplyerName(String replyerName) {
		this.replyerName = replyerName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

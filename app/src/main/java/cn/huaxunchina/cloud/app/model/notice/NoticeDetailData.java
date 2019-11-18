package cn.huaxunchina.cloud.app.model.notice;

import java.io.Serializable;

/**
 * 公告详情数据
 */

@SuppressWarnings("serial")
public class NoticeDetailData implements Serializable {
	private NoticeDetailContent data;

	public NoticeDetailContent getData() {
		return data;
	}

	public void setData(NoticeDetailContent data) {
		this.data = data;
	}

}

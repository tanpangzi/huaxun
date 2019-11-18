package cn.huaxunchina.cloud.location.app.model.post.mes;

import java.io.Serializable;

/**
 * 消息列表信息序列化类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-29 下午3:08:44
 */
@SuppressWarnings("serial")
public class LocMessage implements Serializable {

	private String titleTime; // 主题时间
	private String noticeType;// 提醒类型
	private String locAddress; // 定位地址

	public String getTitleTime() {
		return titleTime;
	}

	public void setTitleTime(String titleTime) {
		this.titleTime = titleTime;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getLocAddress() {
		return locAddress;
	}

	public void setLocAddress(String locAddress) {
		this.locAddress = locAddress;
	}

}

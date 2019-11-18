package cn.huaxunchina.cloud.location.app.model.res;

import java.io.Serializable;

/**
 * 定位模块消息列表接口返回数据模型
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-21 上午9:53:28
 */
@SuppressWarnings("serial")
public class ResMessageModel implements Serializable {
	private String username;// 用户名
	private String name;// 终端卡姓名
	private String cardId;// 卡号
	private String imei;// IMEI号
	private String sim;// 终端SIM卡号
	private String receiveNum;// 通知号码
	private String createTime;// 短信时间
	private String categoryId;// 短信类型
	private String content;// 短信内容
	private String url; // 告警定位路径
	private String address;// 地址

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	private String terminalType; // 卡号

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(String receiveNum) {
		this.receiveNum = receiveNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

/**
 * 历史短信列表字段模型
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-3-12 上午10:29:38
 */
@SuppressWarnings("serial")
public class SMSHistory implements Serializable {
	private String content; // 短信内容
	private String sendSerial; // 短信批次号
	private String createTime; // 短时发送时间
	private int num; // 总人数
	private String names;// 短信接收人

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getSendSerial() {
		return sendSerial;
	}

	public void setSendSerial(String sendSerial) {
		this.sendSerial = sendSerial;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}

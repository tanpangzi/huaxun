package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;

import m.framework.utils.Data;

/**
 * 历史短信详情字段模型
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-3-12 上午10:29:38
 */
@SuppressWarnings("serial")
public class SMSHistoryDetail implements Serializable {
	private String sendSerial; // 短信批次号
	private String content; // 短信内容
	private Data createTime; // 短时发送时间
	private List<String> smsServer; // 短信接收人
	private int num; // 总人数

	public String getSendSerial() {
		return sendSerial;
	}

	public void setSendSerial(String sendSerial) {
		this.sendSerial = sendSerial;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Data getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Data createTime) {
		this.createTime = createTime;
	}

	public List<String> getSmsServer() {
		return smsServer;
	}

	public void setSmsServer(List<String> smsServer) {
		this.smsServer = smsServer;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}

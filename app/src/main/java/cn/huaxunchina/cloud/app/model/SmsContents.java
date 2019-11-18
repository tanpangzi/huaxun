package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * 发短信
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月27日 上午10:18:16
 * 
 */
public class SmsContents implements Serializable {

	private String content;
	private List<SmsUser> users;
	private int groupSms;
	private String classId;
	private String direct;
	private int category;
	private int smsType;
	private String names;
	
	

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public List<SmsUser> getUsers() {
		return users;
	}

	public void setUsers(List<SmsUser> users) {
		this.users = users;
	}

	public int getGroupSms() {
		return groupSms;
	}

	public void setGroupSms(int groupSms) {
		this.groupSms = groupSms;
	}

	public int getSmsType() {
		return smsType;
	}

	public void setSmsType(int smsType) {
		this.smsType = smsType;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

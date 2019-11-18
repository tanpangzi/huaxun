package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

/**
 * 联系人信息
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月20日 下午2:16:43
 * 
 */
@SuppressWarnings("serial")
public class ContactsInfo implements Serializable {

	private String userId;// 用户id
	private String userName;// 用户名称
	private String linkPhone;// 联系电话
	private String sortLetters;
	private boolean isCheck = false;
	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

}

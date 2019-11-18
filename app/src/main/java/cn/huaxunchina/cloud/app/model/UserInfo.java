package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * 
 * 登陆用户信息
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月11日 上午10:06:48 
 *
 */
@SuppressWarnings("serial")
public class UserInfo implements Serializable {

	private String userId;
	private String sex;
	private String userName;
	private School school;
	private Role[] roles;
	private Role role;
	private String clientAppId;

	
	public String getClientAppId() {
		return clientAppId;
	}

	public void setClientAppId(String clientAppId) {
		this.clientAppId = clientAppId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role[] getRoles() {
		return roles;
	}

	public void setRoles(Role[] roles) {
		this.roles = roles;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

}

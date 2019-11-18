package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.model.LoginManager;

public class ClientAppId  implements Serializable{
	
	private String c = "setClientAppId";
	private String clientAppId;
	private String username = Constant.LBS_USERNAME; // 测试账号
	private String password = Constant.LBS_PASSWORD; // 测试密码
	private int loginType = Constant.LBS_LOGINTYPE; // 客户端登录标示
	private String imei = LoginManager.getInstance().getImei(); //测试imei号
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getClientAppId() {
		return clientAppId;
	}

	public void setClientAppId(String clientAppId) {
		this.clientAppId = clientAppId;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}
	
	

}

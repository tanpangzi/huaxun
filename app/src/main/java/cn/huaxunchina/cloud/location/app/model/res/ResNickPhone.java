package cn.huaxunchina.cloud.location.app.model.res;

import java.io.Serializable;

import cn.huaxunchina.cloud.location.app.model.post.BaseModel;

/**
 * 查询亲情号接口返回数据模型
 */
@SuppressWarnings("serial")
public class ResNickPhone extends BaseModel implements Serializable {
	private String mobile1;// 亲情号
	private String mobile2;
	private String mobile3;
	private String mobile4;
	private String mobile5;
	private String nick1; // 昵称
	private String nick2;
	private String nick3;
	private String nick4;
	private String nick5;

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getMobile3() {
		return mobile3;
	}

	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}

	public String getMobile4() {
		return mobile4;
	}

	public void setMobile4(String mobile4) {
		this.mobile4 = mobile4;
	}

	public String getMobile5() {
		return mobile5;
	}

	public void setMobile5(String mobile5) {
		this.mobile5 = mobile5;
	}

	public String getNick1() {
		return nick1;
	}

	public void setNick1(String nick1) {
		this.nick1 = nick1;
	}

	public String getNick2() {
		return nick2;
	}

	public void setNick2(String nick2) {
		this.nick2 = nick2;
	}

	public String getNick3() {
		return nick3;
	}

	public void setNick3(String nick3) {
		this.nick3 = nick3;
	}

	public String getNick4() {
		return nick4;
	}

	public void setNick4(String nick4) {
		this.nick4 = nick4;
	}

	public String getNick5() {
		return nick5;
	}

	public void setNick5(String nick5) {
		this.nick5 = nick5;
	}

}

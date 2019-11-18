package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

/**
 * 添加或者修改或者删除白名单指令
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-12 下午2:07:48
 */
@SuppressWarnings("serial")
public class ChanWhitePostModel extends BaseModel implements Serializable {
	private String c = "uWhitelist";
	private String type;// 1—查询；2-添加 3-修改 4-删除
	private String mobile1;// 电话号码，若为空，表示删除
	private String mobile2;
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	private String mobile3;
	private String mobile4;
	private String mobile5;
	private String nick1; // 昵称
	private String nick2;
	private String nick3;
	private String nick4;
	private String nick5;
}

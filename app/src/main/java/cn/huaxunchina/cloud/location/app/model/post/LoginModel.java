package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

/**
 * 测试定位平台登录javaBean数据
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-14 下午6:02:36
 */
@SuppressWarnings("serial")
public class LoginModel extends BaseModel implements Serializable {
	
	public LoginModel(){
		super();
	}
	private String c = "login";

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}
}

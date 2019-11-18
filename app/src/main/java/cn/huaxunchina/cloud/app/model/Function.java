package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

public class Function implements Serializable {

	private String funId;
	private String funKey;
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
	public String getFunKey() {
		return funKey;
	}
	public void setFunKey(String funKey) {
		this.funKey = funKey;
	}
	
}

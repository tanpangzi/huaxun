package cn.huaxunchina.cloud.location.app.model.res;

import java.io.Serializable;

public class ResLoginModel implements Serializable {
	
	private String id;
	private int status;
	private String name;
	private String imei;
	private String sim;
	private int bat;//电量
	private String password;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public int getBat() {
		return bat;
	}
	public void setBat(int bat) {
		this.bat = bat;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	

}

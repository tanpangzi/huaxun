package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

public class SmsUser implements Serializable{
	
	private String id;
	private String name;//手机号码
	private boolean active;
	 
	 
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	}

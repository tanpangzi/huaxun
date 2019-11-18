package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class More implements Serializable {

	private String title;
	private int img;
	private String funkey;
	private String funid;
	private int id;
	
	public String getFunid() {
		return funid;
	}
	public void setFunid(String funid) {
		this.funid = funid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImg() {
		return img;
	}
	public void setImg(int img) {
		this.img = img;
	}
	public String getFunkey() {
		return funkey;
	}
	public void setFunkey(String funkey) {
		this.funkey = funkey;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}

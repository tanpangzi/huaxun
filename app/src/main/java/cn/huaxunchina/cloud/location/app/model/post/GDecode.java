package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;
/**
 * 经纬度解析指令
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月4日 下午2:03:21 
 *
 */
public class GDecode implements Serializable {
	
	private String c = "gDecode";//指令名称
	private String id;//终端ID号
	private String psw;//终端密码
	private double lat;//纬度
	private double lng;//经度
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	

}

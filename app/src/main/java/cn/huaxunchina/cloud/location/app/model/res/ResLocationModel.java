package cn.huaxunchina.cloud.location.app.model.res;

import java.io.Serializable;

import cn.huaxunchina.cloud.app.tools.TimeUtil;
/**
 * 定位数据模型
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月16日 下午12:25:58 
 *
 */
@SuppressWarnings("serial")
public class ResLocationModel implements Serializable {
	
	private double lng;
	private double lat;
	private boolean online;//是否在线
	private String created;//时间
	private int loctype;//定位类型
	private int bat;//电量
	private String wir;//
	private String addr;//地址
	
	public String getAddr() {
		return "地址:"+addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public String getCreated() {
		return created;
	}
	
	public String getCreatedStr() {
		return "时间:"+TimeUtil.formatStringDate(getCreated());
	}
	
	public long getCreatedLong() {
		return TimeUtil.formatLongDate(getCreated());
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	public int getLoctype() {
		return loctype;
	}
	
	public String getLoctypeStr(){
		String str = "";
		if (loctype==1) 
			return "定位类型:GPS定位";
		if (loctype==2) 
			return "定位类型:基站定位";
		return str;
	}
	
	public void setLoctype(int loctype) {
		this.loctype = loctype;
	}
	public int getBat() {
		return bat;
	}
	public void setBat(int bat) {
		this.bat = bat;
	}
	public String getWir() {
		return wir;
	}
	public void setWir(String wir) {
		this.wir = wir;
	}
	
	

}

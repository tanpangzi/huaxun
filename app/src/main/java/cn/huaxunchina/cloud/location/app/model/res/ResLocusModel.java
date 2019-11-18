package cn.huaxunchina.cloud.location.app.model.res;

import java.io.Serializable;

import cn.huaxunchina.cloud.app.tools.TimeUtil;
/**
 * 轨迹返回数据
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月17日 上午10:08:36 
 *
 */
public class ResLocusModel implements Serializable {
	
	private String addr;
	private double lng;
	private double lat;
	private int loctype;
	private String created;
	
	
	public String getAddr() {
		return addr;
	}
	public String getAddrStr() {
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
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getCreatedStr() {
		return "时间:"+TimeUtil.formatStringDate(getCreated());
	}
	
	public long getCreatedLong(){
		return TimeUtil.formatLongDate(getCreated());
	}
	
	
	

}

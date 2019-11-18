package cn.huaxunchina.cloud.location.app.model;

import com.baidu.mapapi.map.Marker;

public class LocusItem{
	public Marker marker;
	public String address;//地址
	public String time;//时间
	public int loctype;//1.基站 2.gps
	public String loctypeStr; 
	public double lat;
	public double lng;
	public int index;//1.起点 2.终点
	
}

package cn.huaxunchina.cloud.location.app.lbs;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

public class MarkerInfo {
	
	public int type;//1 .gps  2.基站
	public LatLng latlng;
	public int tag = -1;
	public Marker marker;

}

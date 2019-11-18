package cn.huaxunchina.cloud.location.app.model;

import cn.huaxunchina.cloud.location.app.model.post.Circle;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;

public class MarkerInfo{
	public Marker marker;
	public MarkerOptions markerOptions;//标记
	public int radius;//半经
	public String name;
	public String address;//地址
	public boolean state;//状态
	public int datatype;//0<.已添加好的围栏 0=.新添加的围栏
	public boolean isCk = false;
	public Circle circle;
	public double lat;
	public double lng;

	
}
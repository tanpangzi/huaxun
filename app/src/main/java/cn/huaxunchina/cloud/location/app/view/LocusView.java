package cn.huaxunchina.cloud.location.app.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

public class LocusView{
	
	 
	private BaiduMap baiduMap;
	private List<LatLng> points;
	private int linColor = Color.parseColor("#05abff");
	private int width = 5;
	private List<Overlay> overlays = new ArrayList<Overlay>();
	private int pointscount = 0;
	private int index = 1;
	private boolean isRun = false;
	
	
	public LocusView(BaiduMap baiduMap){
	this.baiduMap=baiduMap;
	}
	
	public void setIndex(){
		this.index=1;
	}
	
	public void setRun(boolean b){
		this.isRun=b;
	}
	public boolean isRun(){
		return isRun;
	}
	
	public void setPoints(List<LatLng> points){
	this.points=points;
	pointscount=points.size();
	}
	//点添加后不会直接画线，要调用画线方法
	public void addLinePoint(){
	PolylineOptions ooPolyline= new PolylineOptions().width(width)
	.color(linColor).points(points);
	Overlay  overlay = baiduMap.addOverlay(ooPolyline);
	overlays.add(overlay);
	}
	
	public void clear(){
	 int size = overlays.size();
	 for(int i=0;i<size;i++){
	 Overlay  overlay = overlays.get(i);
	 overlay.remove();
	 }
	 overlays.clear();
	 baiduMap.clear();
	}
	 
	 
	
	//增量画
	public void addDrawLine(int startIndex,int endIndex){
	LatLng startPoint = points.get(startIndex);
	LatLng ednPoint = points.get(endIndex);
	List<LatLng> list = new ArrayList<LatLng>();
	list.add(startPoint);
	list.add(ednPoint);
	OverlayOptions ooPolyline = new PolylineOptions().width(5)
	.color(linColor).points(list);
	Overlay  overlay = baiduMap.addOverlay(ooPolyline);
	overlays.add(overlay);
	index++;
	if(pointscount==index){//如果画到了最后一段，重置数据
	index=1;
	isRun=false;
	}
	}
	
	public int getIndex(){
		return index;
	}
	
}


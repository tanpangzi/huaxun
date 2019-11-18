package cn.huaxunchina.cloud.location.app.lbs;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import cn.huaxunchina.cloud.app.R;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
/**
 * 轨迹
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年2月2日 下午4:56:20 
 *
 */
public class LocusManager extends MarkerManager {
	
	
	private int linColor = Color.parseColor("#05abff");
	private int width = 5;
	//构建Marker图标  
	private BitmapDescriptor jz_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_jz_icon);
	private BitmapDescriptor gps_bitmap  = BitmapDescriptorFactory.fromResource(R.drawable.loc_gps_icon);
	public BitmapDescriptor jz_ck_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_jz_ck_icon);
	public BitmapDescriptor gps_ck_bitmap  = BitmapDescriptorFactory.fromResource(R.drawable.loc_gps_ck_icon);
	private BitmapDescriptor startmarker_bitmap = BitmapDescriptorFactory.fromResource(R.drawable. loc_start_marker);
	private BitmapDescriptor endmarke_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_end_marke);
	private Marker startMarker,endMarker;
	private MarkerInfo lastMarker;
	
	private List<Overlay> lins = new ArrayList<Overlay>();
	private List<MarkerInfo> markerInfo = new 	ArrayList<MarkerInfo>();
	private int indexs = 1;
	
	

	public int getIndexs() {
		return indexs;
	}

	public void setIndex(int indexs) {
		this.indexs = indexs;
	}

	public LocusManager(MapView mMapView) {
		super(mMapView);
		// TODO Auto-generated constructor stub
	}
	
	public MarkerInfo getLastMarker(){
		return lastMarker;
	}
	
	public void setLastMarker(MarkerInfo lastMarker){
		this.lastMarker=lastMarker;
	}
	
	public void setMarkers(List<MarkerInfo> markers){
		this.markerInfo=markers;
	}
	
	//画所有的
	public void drawAllLine(){
	int size = markerInfo.size();
	if(size>1){//多少个点的情况
	for(int i=1;i<size;i++){
	draw(i);
	}
	}else if(size==1)
	{//一个点的情况
	drawMarker();
	}
	}
	
	//==一个点的情况
	public void drawMarker(){
	MarkerInfo info = markerInfo.get(0);
	if(info.type==1)
	{
    this.setMarkIcon(gps_bitmap);
	this.drawMarker(info);
	}else
	{
	this.setMarkIcon(jz_bitmap);
	this.drawMarker(info);
	}
    //画起点	     
	startMarker = this.drawMarker(info.latlng,startmarker_bitmap);
	startMarker.setAnchor(0.5f, -1.0f);
	}
	
	
	public void draw(int index){
    //如果是第一个线，多画一个点
    if(index==1){
	MarkerInfo info1 = markerInfo.get((index-1));
	locusDrawMarker(info1);
    if(index==1){//判断是不是起点
    startMarker = this.drawMarker(info1.latlng,startmarker_bitmap);
    startMarker.setAnchor(0.5f, -0.9f);
    }
    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(info1.latlng);
	this.getBaiduMap().animateMapStatus(u);
    }
    //其他线的点只画一个就好
    MarkerInfo info = markerInfo.get(index);
    locusDrawMarker(info);
	if(index==(markerInfo.size()-1)){//判断是不是终点
	endMarker = this.drawMarker(info.latlng,endmarke_bitmap);
	endMarker.setAnchor(0.5f, -1.0f);
	}
	
	//画线
	drawLine((index-1),index);
	}
	
	
	//增量画
	public void drawLine(int startIndex,int endIndex){
	LatLng startPoint = markerInfo.get(startIndex).latlng;
	LatLng ednPoint = markerInfo.get(endIndex).latlng;
	List<LatLng> list = new ArrayList<LatLng>();
	list.add(startPoint);
	list.add(ednPoint);
	OverlayOptions ooPolyline = new PolylineOptions().width(width)
	.color(linColor).points(list);
	Overlay  overlay = this.getBaiduMap().addOverlay(ooPolyline);
	lins.add(overlay);
	//==
	MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ednPoint);
	this.getBaiduMap().animateMapStatus(u);
	indexs ++;
	}
	
	public boolean isEnd(){
		boolean b = false;
		if(indexs<markerInfo.size()){
			b=true;
		}
		return b;
	}
	
	
	public void locusDrawMarker(MarkerInfo info){
    if(info.type==1){
    this.setMarkIcon(gps_bitmap);
	this.drawMarker(info);
	}else{
    this.setMarkIcon(jz_bitmap);
	this.drawMarker(info);
	}
	}
	
	public void setIcon(MarkerInfo info,boolean isCk){
    if(info.type==1){
	 if(isCk){info.marker.setIcon(gps_ck_bitmap);}else{info.marker.setIcon(gps_bitmap);}
	}else{
	 if(isCk){info.marker.setIcon(jz_ck_bitmap);}else{info.marker.setIcon(jz_bitmap);}
	}
	}
	
	
	public void clearLocus(){
	this.clearMarkers();
	if(startMarker!=null){
	startMarker.remove();
	startMarker=null;
	}
	if(endMarker!=null){
	endMarker.remove();
	endMarker=null;
	}
	if(lastMarker!=null){
	lastMarker.marker.remove();
	lastMarker=null;
	}
	this.getBaiduMap().hideInfoWindow();
	int size = lins.size();
	for(int i=0;i<size;i++){
		lins.get(i).remove();
	}
	lins = new ArrayList<Overlay>();
    }
	
 
}

 

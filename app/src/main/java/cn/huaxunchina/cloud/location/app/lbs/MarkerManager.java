package cn.huaxunchina.cloud.location.app.lbs;

import java.util.ArrayList;
import java.util.List;

import cn.huaxunchina.cloud.app.R;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class MarkerManager extends MapManager {
	
	
	private List<MarkerInfo> markers = new ArrayList<MarkerInfo>();
	//构建Marker图标  
	private	BitmapDescriptor markIcon = BitmapDescriptorFactory.fromResource(R.drawable.loc_zx_mark);

	public MarkerManager(MapView mMapView) {
	super(mMapView);
		// TODO Auto-generated constructor stub
	}
	
	public void addLatLng(MarkerInfo latLng){
	this.markers.add(latLng);
	}
	
	public MarkerInfo getMarker(int index){
		return markers.get(index);
	}
	
	public MarkerInfo getMarkerInfo(int index){
		return markers.get(index);
	}
	
	public void setLatLngs(List<MarkerInfo> latLngs){
	this.markers=latLngs;
	}
	
	protected List<MarkerInfo> getMarkerInfo(){
	return this.markers;
	}
	
	
	public void drawMarker(MarkerInfo info){
	//构建MarkerOption，用于在地图上添加Marker  
	OverlayOptions option = new MarkerOptions().position(info.latlng).icon(markIcon);  
    //在地图上添加Marker，并显示  
	Marker	m =(Marker)getBaiduMap().addOverlay(option);
	m.setAnchor(0.5f, 0.5f);
	info.marker=m;
	markers.add(info);
	 
	}
	
	 
	
	
	public Marker drawMarker(LatLng point,BitmapDescriptor icon){
	//构建MarkerOption，用于在地图上添加Marker  
	OverlayOptions option = new MarkerOptions().position(point).icon(icon);  
	//在地图上添加Marker，并显示  
	Marker	m =(Marker)getBaiduMap().addOverlay(option);
	return m;
	}
	
	public void drawAddMarker(){
	int size  = markers.size();
	for(int i=0;i<size;i++){
	drawMarker(markers.get(i));
	}
	}
	

	public void setMarkIcon(BitmapDescriptor bitamap){
	this.markIcon = bitamap;
	}
	
	 
	
	 
	
	public void clearMarkers(){
	int size = markers.size();
	for(int i=0;i<size;i++){
		Marker m= markers.get(i).marker;
		if(m!=null){
		 m.remove();
		}
	}
	markers.clear();
	}
	
	/**
	 * TODO(描述) 
	  * @param m 小于负一表示没有找到
	  * @return
	 */
	public int getIndexes(Marker m){
		int indexes = -1;
		int size = markers.size();
		for(int i=0;i<size;i++){
			if(m==markers.get(i).marker){
				indexes=markers.get(i).tag;
			}
		}
		return indexes;
	}
	
	public MarkerInfo getMarkerInfo(Marker m){
		MarkerInfo info = null;
		int size = markers.size();
		for(int i=0;i<size;i++){
			if(m==markers.get(i).marker){
				info=markers.get(i);
			}
		}
		return info;
	}
	
	
	
	
	 
}

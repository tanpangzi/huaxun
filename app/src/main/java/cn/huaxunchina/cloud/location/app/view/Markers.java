package cn.huaxunchina.cloud.location.app.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.model.LocusItem;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;

public class Markers {
		
		//构建Marker图标  
		private BitmapDescriptor gps_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_jz_icon);
		private BitmapDescriptor jz_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_gps_icon);
		private BitmapDescriptor gps_ck_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_jz_ck_icon);
		private BitmapDescriptor jz_ck_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_gps_ck_icon);
		private BitmapDescriptor startmarker_bitmap = BitmapDescriptorFactory.fromResource(R.drawable. loc_start_marker);
		private BitmapDescriptor endmarke_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_end_marke);
		
		private BaiduMap baiduMap;
		private List<LocusItem> points;
		private List<Marker> markers = new ArrayList<Marker>();
		private Marker lastMarker = null;
		private Marker startMarker = null;
		private Marker endMarker = null;
		private LcousPopView popview;
		
		
		public Markers(BaiduMap baiduMap,Context context){
		this.baiduMap=baiduMap;
		popview = new LcousPopView(context);
		}
		public void setLocusInfo(List<LocusItem> points){
		this.points=points;
		}
		
		public void addMarker(){
			int size = points.size();
			for(int i = 0;i<size;i++){
			addDrawMarker(i,false);
			}
			 
		}
		
		public void clear(){
			//baiduMap.clear();
			 int size = markers.size();
			 for(int i=0;i<size;i++){
				 Marker  marker = markers.get(i);
				 marker.remove();
			 }
			 markers.clear();
			 if(lastMarker!=null){
				 lastMarker.remove();
				 lastMarker=null;
			 }
			 if(endMarker!=null){
				 endMarker.remove();
				 endMarker=null;
			 }
			 if(startMarker!=null){
				 startMarker.remove();
				 startMarker=null;
			 }
		}
		
		
		public void addDrawMarker(int index,boolean isloc){
			LocusItem info = points.get(index);
			LatLng latlng = new LatLng(info.lat,info.lng);
			BitmapDescriptor bitmap = null;
			if(info.loctype==1){
			bitmap= jz_bitmap;
			}else{
			bitmap=gps_bitmap;
			}
			OverlayOptions  overlay = new MarkerOptions().position(latlng).icon(bitmap);	
			Marker marker = (Marker)baiduMap.addOverlay(overlay);
			//设置偏移量
			marker.setAnchor(0.5f, 0.5f);
			info.marker=marker;
			markers.add(marker);
			
			//画起点和终点
			if(info.index==1){
			overlay = new MarkerOptions().position(latlng).icon(startmarker_bitmap);
			startMarker = (Marker)baiduMap.addOverlay(overlay);
			startMarker.setAnchor(0.5f, -1.0f);
			}else if(info.index==2){
			overlay = new MarkerOptions().position(latlng).icon(endmarke_bitmap);
			endMarker = (Marker)baiduMap.addOverlay(overlay);	
			endMarker.setAnchor(0.5f, -1.0f);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latlng);
			baiduMap.animateMapStatus(u);
			}
			
			if(isloc&&info.index!=2){
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latlng);
			baiduMap.animateMapStatus(u);
			}
		}
		
		
		 
		
		public void setLastMarker(){
		this.lastMarker = null;
		}
		
		public void setOnMarkerClickListener(){
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
		@SuppressWarnings("unused")
		@Override
		public boolean onMarkerClick(final Marker marker) {
		LocusItem currinfo = getLocusInfo(marker);
		baiduMap.hideInfoWindow();
		if(currinfo!=null){
			//替换状态图片
			if(currinfo.loctype==1){
			currinfo.marker.setIcon(jz_ck_bitmap);
			}else{
			currinfo.marker.setIcon(gps_ck_bitmap);
			}
			//baiduMap.showInfoWindow(popview.getPopView(currinfo));
			//修改之前的图片
			if(lastMarker!=null){
				LocusItem lastinfo = getLocusInfo(lastMarker);
				if(lastinfo.loctype==1){
					lastinfo.marker.setIcon(jz_bitmap);
				}else{
					lastinfo.marker.setIcon(gps_bitmap);
				}
			}
			//记录当前的marker
			lastMarker=currinfo.marker;
		}
		return true;
		}});
		}
		
		
		private LocusItem getLocusInfo(Marker marker){
			int size = points.size();
			LocusItem info = null;
			for (int i = 0; i < size; i++) {
				LocusItem new_info = points.get(i);
				if(new_info.marker==marker){
					new_info.marker=marker;
					info = new_info;
				}
			}
			return info;
		}
		
		
		 
		
	}

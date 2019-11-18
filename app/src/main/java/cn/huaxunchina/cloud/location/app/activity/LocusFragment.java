package cn.huaxunchina.cloud.location.app.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.view.LocusAboutDialog;
import cn.huaxunchina.cloud.location.app.view.ZoomControlView;

public class LocusFragment extends BaseFragment implements OnClickListener{

	private Activity activity;
	private MapView mMapView;
	private ZoomControlView  mZooview;
	private BaiduMap mBaiduMap;
	private List<LocusInfo> locusInfos = new ArrayList<LocusInfo>();
	private List<LatLng> mpoints = new ArrayList<LatLng>();
	//线程池的初始化
	private ScheduledFuture<?> future=null;
	private ScheduledExecutorService execuService= Executors.newScheduledThreadPool(1);
	private final static int TIME_CODE = 1012;
	

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity=activity;
	}
	
	 
	@Override
	public void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		
		mMapView.onResume();
		mBaiduMap.clear();
		super.onResume();
	}
	
	@Override
	public void onPause() {
		mMapView.onPause();
		mBaiduMap.clear();
		super.onPause();
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		mMapView.onResume();

	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.loc_home_locus_layout, container,false);
		view.findViewById(R.id.locus_about).setOnClickListener(this);
		view.findViewById(R.id.locus_playbtn).setOnClickListener(this);
		MyBackView back = (MyBackView)view.findViewById(R.id.back);
		back.setBackText("轨迹");
		mZooview=(ZoomControlView)view.findViewById(R.id.locus_zoomview);
		
		mMapView = (MapView)view.findViewById(R.id.locaticon_mapView);
		System.out.println();
		//隐藏baidu自带放大控件
		mMapView.showZoomControls(false);
		mBaiduMap =  mMapView.getMap();
		mBaiduMap.clear();
		//设置地图不可以旋转
		mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);
	    ////设置地图缩放级别
		MapStatusUpdate zooout = MapStatusUpdateFactory.zoomTo(14f);
		mBaiduMap.animateMapStatus(zooout);
		//设置自定义放大控件
		mZooview.setMapView(mMapView,mBaiduMap);
		mBaiduMap.clear();
		
		test();
		overlay();
		 
		
		 
		 
		return view;
	}
	
	 
	
	private void test(){
		LatLng[] lngs = {
				new LatLng(22.56692, 113.887894),
				new LatLng(22.56652, 113.899464),
				new LatLng(22.574863, 113.896302),
				new LatLng(22.574863, 113.887247),
				new LatLng(22.587344, 113.878767),
				new LatLng(22.595886, 113.886888)};
		
		
		 for (int i = 0; i < lngs.length; i++) {
			 LocusInfo info = new LocusInfo();
			 info.address="宝安西乡";
			 if(i==1){
			 info.datatype=2; 
			 }else{
			 info.datatype=1;
			 }
			 
			 info.lat=lngs[i].latitude;
			 info.lng=lngs[i].longitude;
			 info.time="2015-01-02";
			 if(i==0){
			  info.index = 1; 
			 }
			 if(i==((lngs.length-1))){
			  info.index = 2;  
			 }
			 
			 LatLng ll = lngs[i];
			 mpoints.add(ll);
			 
			 locusInfos.add(info);
		}
		 
		 
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case TIME_CODE:
				int index = lview.getIndex();
				System.out.println("index:"+index);
				lview.addDrawLine(index-1, index);
				if(index==1){
				pview.addDrawMarker(index-1);
				}
				pview.addDrawMarker(index);	
				break;
			}
		}
	};
	
	
	LocusView lview;
	PointsView pview;
	private void overlay(){
		lview = new LocusView(mBaiduMap);
		lview.setPoints(mpoints);
		lview.addLinePoint();
		pview = new PointsView(mBaiduMap);
		pview.setLocusInfo(locusInfos);
		pview.addMarker();
		pview.setOnMarkerClickListener();
	}
	
	
	 

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.locus_about:
			LocusAboutDialog diolog = new LocusAboutDialog(activity);
			//Window window = diolog.getWindow();
			//window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置 //Gravity.BOTTOM
			diolog.setCancelable(true);
			diolog.setCanceledOnTouchOutside(true);
			diolog.show();
			break;
		case R.id.locus_playbtn://播放
			lview.clear();
			lview.setRun(true);
			pview.clear();
			pview.setLastMarker();
			if(null!=future){
    		future.cancel(true);
			future=null;
	    	}
			future=execuService.scheduleAtFixedRate(new Runnable(){
				@Override
				public void run() {
					if(lview.isRun()){
					handler.sendEmptyMessage(TIME_CODE);}
				}
			}, 0, 500, TimeUnit.MILLISECONDS);
			break;
		}
		
	}
	
	
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
	
	
	public class PointsView{
		
		//构建Marker图标  
		private BitmapDescriptor gps_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_gps_icon);
		private BitmapDescriptor jz_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_jz_icon);
		private BitmapDescriptor gps_ck_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_gps_ck_icon);
		private BitmapDescriptor jz_ck_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_jz_ck_icon);
		private BitmapDescriptor startmarker_bitmap = BitmapDescriptorFactory.fromResource(R.drawable. loc_start_marker);
		private BitmapDescriptor endmarke_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_end_marke);
		
		private BaiduMap baiduMap;
		private List<LocusInfo> points;
		private List<Marker> markers = new ArrayList<Marker>();
		
		public PointsView(BaiduMap baiduMap){
		this.baiduMap=baiduMap;
		}
		public void setLocusInfo(List<LocusInfo> points){
		this.points=points;
		}
		
		public void addMarker(){
			int size = points.size();
			for(int i = 0;i<size;i++){
			addDrawMarker(i);
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
		}
		
		
		public void addDrawMarker(int index){
			LocusInfo info = points.get(index);
			LatLng latlng = new LatLng(info.lat,info.lng);
			BitmapDescriptor bitmap = null;
			if(info.datatype==1){
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
			Marker startmarker = (Marker)baiduMap.addOverlay(overlay);
			startmarker.setAnchor(0.5f, -1.0f);
			markers.add(startmarker);
			}else if(info.index==2){
			overlay = new MarkerOptions().position(latlng).icon(endmarke_bitmap);
			Marker endmarke = (Marker)baiduMap.addOverlay(overlay);	
			endmarke.setAnchor(0.5f, -1.0f);
			markers.add(endmarke);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latlng);
			mBaiduMap.animateMapStatus(u);
			 
			}
		}
		
		
		private Marker lastMarker = null;
		
		public void setLastMarker(){
		this.lastMarker = null;
		}
		
		public void setOnMarkerClickListener(){
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
		@SuppressWarnings("unused")
		@Override
		public boolean onMarkerClick(final Marker marker) {
		LocusInfo currinfo = getLocusInfo(marker);
		if(currinfo!=null){
			//替换状态图片
			if(currinfo.datatype==1){
			currinfo.marker.setIcon(jz_ck_bitmap);
			}else{
			currinfo.marker.setIcon(gps_ck_bitmap);
			}
			//修改之前的图片
			if(lastMarker!=null){
				LocusInfo lastinfo = getLocusInfo(lastMarker);
				if(lastinfo.datatype==1){
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
		 
		
		
		
		private LocusInfo getLocusInfo(Marker marker){
			int size = points.size();
			LocusInfo info = null;
			for (int i = 0; i < size; i++) {
				LocusInfo new_info = points.get(i);
				if(new_info.marker==marker){
					new_info.marker=marker;
					info = new_info;
				}
			}
			return info;
		}
		
	}
	
 
	
	public class LocusInfo{
		Marker marker;
		String address;//地址
		String time;//时间
		int datatype;//1.基站 2.gps
		double lat;
		double lng;
		int index;//1.起点 2.终点
		
	}
	
 
	
	
	 

}

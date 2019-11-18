package cn.huaxunchina.cloud.location.app.activity.stt.crawl;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.model.MarkerInfo;
import cn.huaxunchina.cloud.location.app.model.post.Circle;
import cn.huaxunchina.cloud.location.app.view.FencingPopView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;

public class FencingView {
	
	
	BitmapDescriptor zx_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_wz_mark);
	BitmapDescriptor lx_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_xz_mark);
	private int round_rim_color = Color.parseColor("#05abff");
	private int circle_color = Color.argb(127,128,202,240);//Color.parseColor("#80caf0");127表示透明度
	
	public BaiduMap mBaiduMap;
	private Overlay crawl_overlay;
	private FencingPopView popView;
	private LinearLayout crawl_layout;
	private ControlView controlView;
	private FencingActivity fencingActivity;
	
	private List<MarkerInfo> markerInfo = new ArrayList<MarkerInfo>();
	
	
	public FencingView(FencingActivity fencingActivity,BaiduMap mBaiduMap,ControlView controlView,LinearLayout layout){
	this.mBaiduMap=mBaiduMap;
	this.crawl_layout=layout;
	this.controlView=controlView;
	this.fencingActivity=fencingActivity;
	popView = new FencingPopView(fencingActivity);
	onMarkerClickListener();
	}
	
	
	public void initData(List<Circle> data){
		System.out.println(markerInfo);
		if(data==null)return;
		int size = data.size();
		for (int i = 0; i < size; i++) {
			Circle circle = data.get(i);
			initData(circle,false);
		}
		//画
		for(int i = 0; i < size; i++){
			addMarker(markerInfo.get(i));
		}
		
		
	}
	 
    public void initData(Circle circle,boolean isCk){
    	markerInfo.add(initMarkerInfo(circle,isCk));
	}
	
	
	public MarkerInfo initMarkerInfo(Circle circle,boolean isCk){
		MarkerInfo mk = new MarkerInfo();
		mk.datatype=circle.getCircle_id();
		mk.address=circle.getPointAddress();
		mk.lat=circle.getPositionLat();
		mk.lng=circle.getPositionLng();
		MarkerOptions marker = new MarkerOptions().position(new LatLng(circle.getPositionLat(), circle.getPositionLng())).icon(zx_bitmap);
		mk.markerOptions=marker;
		mk.radius=circle.getPositionRadius();
		mk.state=circle.isPositionAlarm();//circle.isOnoff();
		mk.name=circle.getPositionName();
		mk.isCk=isCk;
		mk.circle=circle;
		return mk;
	}
	
	
	public void isExist(MarkerInfo mk){
		boolean b = true;
		int size = markerInfo.size();
		for (int i = 0; i < size; i++) {
		LatLng lng = markerInfo.get(i).marker.getPosition();
		LatLng new_lng = mk.marker.getPosition();
		if(lng.latitude==new_lng.latitude&&lng.longitude==new_lng.longitude){
		System.out.println(markerInfo);
		markerInfo.remove(i);
		markerInfo.add(mk);
		
		b = false;
		System.out.println(markerInfo);
		break;
		}
		}
		if(b){
		markerInfo.add(mk);
		}
		System.out.println(markerInfo);
		 
		
	}
	
	public void addMarker(MarkerInfo mk){
		Marker marker = (Marker)mBaiduMap.addOverlay(mk.markerOptions);
		mk.marker=marker;
		if(mk.isCk){
		marker.setIcon(lx_bitmap);
		}
		//===移动位置 
		LatLng ll = mk.marker.getPosition();
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.animateMapStatus(u);
		
		if(markerInfo.size()>=5){//判断是不是已经不可以添加隐藏添加
		crawl_layout.setVisibility(View.GONE);
		}else{
		crawl_layout.setVisibility(View.VISIBLE);
		}
	}
	
	
	
	private void onMarkerClickListener(){
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener(){
		@Override
		public boolean onMarkerClick(Marker marker) {
			crawl(marker);
			return true;
		}});
		mBaiduMap.setOnMapClickListener(new OnMapClickListener(){
		@Override
		public void onMapClick(LatLng arg0) {
			hide();
		}
		@Override
		public boolean onMapPoiClick(MapPoi arg0) {
			return false;
		}});
	}
	
	
	public void hide(){
		mBaiduMap.hideInfoWindow();
		controlView.hideOperationView();
		if(crawl_overlay!=null){
		crawl_overlay.remove();
		}
		
		for (int i = 0; i < markerInfo.size(); i++) {
		MarkerInfo mk = markerInfo.get(i);
		if(mk.isCk){
		mk.marker.setIcon(zx_bitmap);
		mk.isCk=false;
		break;
		}
		}
	}
	
	public void remove(MarkerInfo minfo){
	    for (int i = 0; i < markerInfo.size(); i++) {
    	if(markerInfo.get(i).circle.getCircle_id()==minfo.circle.getCircle_id()){
    		
    		markerInfo.get(i).marker.remove();
    		markerInfo.remove(i);
    	}}
	    
	    System.out.println(markerInfo);
	    mBaiduMap.clear();
	    for(int i = 0; i < markerInfo.size(); i++){
			addMarker(markerInfo.get(i));
		}
	    
	 
	}
	
	
	
	MarkerInfo cr_markerInfo;
	//画围栏
	public void crawl(Marker marker){
	for (int i = 0; i < markerInfo.size(); i++)
	{
		MarkerInfo info = markerInfo.get(i);
		LatLng lng = marker.getPosition();
		LatLng new_lng = info.marker.getPosition();
		 
		
		if(lng.latitude==new_lng.latitude&&lng.longitude==new_lng.longitude){
			
		 
		//移除原来的圆
		if(crawl_overlay!=null){
		crawl_overlay.remove();	
		}
		
		
		marker.setIcon(lx_bitmap);
		info.isCk=true;
		info.marker=marker;
		
		// 添加圆
		OverlayOptions ooCircle = new CircleOptions()
		.fillColor(circle_color)
		.center(lng).stroke(new Stroke(2, round_rim_color))
		.radius(info.radius);
         //保存为全局对象
		crawl_overlay =  mBaiduMap.addOverlay(ooCircle);	
		//显示pop
		popView.initPopView(info);
		mBaiduMap.showInfoWindow(popView.getPopView());
		controlView.operation(info);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(marker.getPosition());
		mBaiduMap.animateMapStatus(u);
		}else
		{
		upCrawl(info);
		
		}
	 
	}
	}
	
	
	
	//画围栏
	public void upCrawl(MarkerInfo minfo){
		if(minfo.isCk){
		minfo.isCk=false;
		minfo.marker.setIcon(zx_bitmap);
		}
	}
	
	
	public void upView(Circle circle){
	    //==创建自定义信息
		MarkerInfo mk = initMarkerInfo(circle,true);
		//移除上一次新添加的
		int count = markerInfo.size();
		for(int i=0;i<count;i++){
		MarkerInfo info = markerInfo.get(i);
		if(info.isCk){
		info.marker.setIcon(zx_bitmap);
		info.isCk=false;
		}}
		
		//添加view
		addMarker(mk);
		isExist(mk);
		
		
		//判断是不是已经添加好的数据
		if(crawl_overlay!=null){
		crawl_overlay.remove();
		}
		// 添加圆
		OverlayOptions ooCircle = new CircleOptions()
		.fillColor(circle_color)
		.center(new LatLng(mk.lat,mk.lng)).stroke(new Stroke(2, round_rim_color))
		.radius(mk.radius);
         //保存为全局对象
		crawl_overlay =  mBaiduMap.addOverlay(ooCircle);	
		
		FencingPopView pview = new FencingPopView(fencingActivity);
		pview.initPopView(mk);
		mBaiduMap.showInfoWindow(pview.getPopView());
		//==
		controlView.operation(mk);
		
	}
	
	 
	
	

}

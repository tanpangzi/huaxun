package cn.huaxunchina.cloud.location.app.activity.stt.crawl;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.model.response.LoginData;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.view.ContactsDialog;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.activity.stt.TimeStepSelect;
import cn.huaxunchina.cloud.location.app.model.MarkerInfo;
import cn.huaxunchina.cloud.location.app.model.post.Circle;
import cn.huaxunchina.cloud.location.app.model.post.FencesettingModel;
import cn.huaxunchina.cloud.location.app.model.res.ResFencesettingModel;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.view.CityDialog;
import cn.huaxunchina.cloud.location.app.view.FencingPopView;
import cn.huaxunchina.cloud.location.app.view.ZoomControlView;
import cn.huaxunchina.cloud.location.app.xml.CityManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
/**
 * 电子围栏
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月8日 上午10:37:46 
 *
 */
public class FencingActivity2 extends Activity implements OnClickListener,CityDialog.CityDisplay{
	
	
	private MapView mMapView;
	private ZoomControlView  mZooview;
	private BaiduMap mBaiduMap;
	//气泡点
	//private InfoWindow mInfoWindow;
	//构建Marker图标  
	BitmapDescriptor zx_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_wz_mark);
	BitmapDescriptor lx_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc_xz_mark);
	// 定位相关
	private LocationClient locClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位
	//围栏相关
	//圆边
	private int round_rim_color = Color.parseColor("#05abff");
	private int circle_color = Color.argb(127,128,202,240);//Color.parseColor("#80caf0");127表示透明度
	private Button cityBtn;
	private ApplicationController applicationController;
	private LoadingDialog loadingDialog;
	private AlarmsettView alarmsettView;
	private OperationView operationView;
	private List<MarkerInfo> markers = new ArrayList<MarkerInfo>();
	private LinearLayout crawl_layout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_crawl_layout);
		initView();
		initData();
		
		
	}
	
	
	private void circleData(List<Circle> data){
		int size = data.size();
		for (int i = 0; i < size; i++) {
			Circle circle = data.get(i);
			markers.add(initMarkerInfo(circle,false));
			
		}
		 
	}
	
	
	
	private MarkerInfo initMarkerInfo(Circle circle,boolean isCk){
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
	
	
	private void initData(){
		FencesettingModel fen = new FencesettingModel();
		fen.setType(1);
		new HttpHandler(loadingDialog,applicationController.httpUtils_lbs,fen,new HttpHandlerCallBack(){
			@Override
			public void onCallBack(String json) {
				try {
					System.out.println("");
					ResFencesettingModel model = GsonUtils.getSingleBean(json, ResFencesettingModel.class);
					List<Circle> data = model.getData();
					if(data!=null){
					circleData(data); 
					alarmsettView.allMarker(markers);
					alarmsettView.onMarkerClickListener();
					}
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			@Override
			public void onErro() {
				
			}});
	}
	
	
	
	private void initView(){
		applicationController=(ApplicationController)this.getApplication();
		loadingDialog = new LoadingDialog(this);
		MyBackView back = (MyBackView)findViewById(R.id.back);
		back.setBackText("电子围栏");
		back.setAddActivty(this);
		findViewById(R.id.crawl_me_btn).setOnClickListener(this);
		mZooview=(ZoomControlView)findViewById(R.id.crawl_zoomview);
		mMapView = (MapView)findViewById(R.id.crawl_mapView);
		//隐藏baidu自带放大控件
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();
		//设置地图不可以旋转
		mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);
	    ////设置地图缩放级别
		MapStatusUpdate zooout = MapStatusUpdateFactory.zoomTo(14f);
		mBaiduMap.animateMapStatus(zooout);
		//设置自定义放大控件
		mZooview.setMapView(mMapView,mBaiduMap);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		alarmsettView = new AlarmsettView(mBaiduMap);
		
		findViewById(R.id.poi_search).setOnClickListener(this);
		cityBtn=(Button)findViewById(R.id.poi_city);
		cityBtn.setOnClickListener(this);
		cityBtn.setText(CityDialog.getCityName(this));
		Button add = (Button)findViewById(R.id.crawl_add);
		Button det = (Button)findViewById(R.id.crawl_det);
		Button del = (Button)findViewById(R.id.crawl_del);
		operationView = new OperationView(add,det,del);
		crawl_layout = (LinearLayout)findViewById(R.id.crawl_layout1);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.crawl_me_btn://定位自己
			Toast.makeText(this, "开始定位...", Toast.LENGTH_LONG).show();
			locMe();
			break;
		case R.id.poi_search:
			String curr_city = cityBtn.getText().toString().trim();
			Intent intent = new Intent(this, PoiActivity.class);
			intent.putExtra("curr_city", curr_city);
			startActivityForResult(intent, ResultCode.FEN_CODE);
			break;
		case R.id.poi_city:
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			int width = dm.widthPixels;
			int height = dm.heightPixels;
			CityDialog diolog = new CityDialog(FencingActivity2.this, width,height);
			Window window = diolog.getWindow();
			window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
			window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
			diolog.setCancelable(true);
			diolog.setCityDisplay(this);
			diolog.setCanceledOnTouchOutside(true);
			diolog.show();
			break;
		}
		
	}
	

	@Override
	public void onCallBackCity(String city) {
		cityBtn.setText(city);
	}
	
	
	//定位
	private void locMe(){
		isFirstLoc = true;
		// 定位初始化
		locClient = new LocationClient(this);
		locClient.registerLocationListener(myListener);//注册定位监听接口 
		//LocationClientOption 该类用来设置定位SDK的定位方式。
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setAddrType("all");//返回的定位结果包含地址信息  
		option.setCoorType("bd09ll"); // 设置坐标类型 /返回的定位结果是百度经纬度,默认值gcj02  
		//option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先  
		option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms  
		//option.disableCache(false);//禁止启用缓存定位  
		//option.setIsNeedAddress(true);//返回的定位结果
		locClient.setLocOption(option);//设置定位参数 
		locClient.start();
	    mCurrentMode = LocationMode.NORMAL;
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
						mCurrentMode, true, mCurrentMarker));
	    
	}
	
	
	 
	

	/**
	 * 定位SDK监听函数
	 */
	private class MyLocationListenner implements BDLocationListener {

		/** 
         * 接收异步返回的定位结果，参数是BDLocation类型参数 
         */ 
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
			return;
			MyLocationData locData = new MyLocationData.Builder()
			.accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
			.direction(100).latitude(location.getLatitude())
			.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
			Toast.makeText(FencingActivity2.this, "[我的位置]"+location.getAddrStr(), Toast.LENGTH_LONG).show();
			isFirstLoc = false;
			LatLng ll = new LatLng(location.getLatitude(),
			location.getLongitude());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.animateMapStatus(u);
			}
		}
		/** 
         * 接收异步返回的POI查询结果，参数是BDLocation类型参数 
         */ 
		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	
	
 
	
	
	private class AlarmsettView{
		private BaiduMap baiduMap;
		private List<MarkerInfo> mks = new ArrayList<MarkerInfo>();
		private Overlay crawl_overlay;
		private FencingPopView popView;
		
		public AlarmsettView(BaiduMap baiduMap){
		this.baiduMap=baiduMap;
		popView = new FencingPopView(FencingActivity2.this);
		}
		
		public int getMarkerInfoCount(){
			return mks.size();
		}
		
		public List<MarkerInfo> getMarkerInfo(){
			return mks;
		} 
		 
		
		public void allMarker(List<MarkerInfo> markers){
			if(markers!=null){
				int size = markers.size();
				for(int i=0;i<size;i++){
					MarkerInfo mk = markers.get(i);
					addMarker(mk);
				}
			}
		}
		
		public void addMarker(MarkerInfo mk){
			Marker marker = (Marker)baiduMap.addOverlay(mk.markerOptions);
			mk.marker=marker;
			if(mk.isCk){
			marker.setIcon(lx_bitmap);
			}
			mks.add(mk);
			//===移动位置 
			LatLng ll = mk.marker.getPosition();
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.animateMapStatus(u);
			if(mks.size()==5){//判断是不是已经不可以添加隐藏添加
			crawl_layout.setVisibility(View.GONE);
			}
		}
		
		
		public void rmoveMarker(MarkerInfo mk){
			List<MarkerInfo> info = alarmsettView.getMarkerInfo();
			int length = info.size();
			boolean b = false;
			for (int i = 0; i < length; i++) {
				MarkerInfo m = info.get(i);
				
				if(m.circle.getCircle_id()==mk.circle.getCircle_id()){
					b=true;
					
					
					Marker marker = (Marker)baiduMap.addOverlay(mk.markerOptions);
					mk.marker=marker;
					if(mk.isCk){
					marker.setIcon(lx_bitmap);
					}
					mks.add(i,mk);
					//===移动位置 
					LatLng ll = mk.marker.getPosition();
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
					mBaiduMap.animateMapStatus(u);
					if(mks.size()==5){//判断是不是已经不可以添加隐藏添加
					crawl_layout.setVisibility(View.GONE);
					}
					
				}
				
			}
			if(b==false){
				//添加view
				alarmsettView.addMarker(mk);
			}
		}
		
		public void onMarkerClickListener(){
			baiduMap.setOnMarkerClickListener(new FenMarkerClickListener());
			baiduMap.setOnMapClickListener(new OnMapClickListener(){
				@Override
				public void onMapClick(LatLng arg0) {
					baiduMap.hideInfoWindow();
					operationView.hideOperationView();
					if(alarmsettView.crawl_overlay!=null){
					alarmsettView.crawl_overlay.remove();
					}
					for (int i = 0; i < mks.size(); i++) {
						MarkerInfo mk = mks.get(i);
						if(mk.isCk){
							mk.marker.setIcon(zx_bitmap);
							mk.isCk=false;
						}
					}
				}
				@Override
				public boolean onMapPoiClick(MapPoi arg0) {
					return false;
				}});
		}
		
		//画围栏
		public void crawl(Marker marker){
			for (int i = 0; i < mks.size(); i++) {
				MarkerInfo mk = mks.get(i);
				LatLng posit = marker.getPosition();
				if(mk.isCk){
					mk.marker.setIcon(zx_bitmap);
					mk.isCk=false;
				}
				//判断是不是同一个点
				if(marker==mk.marker){
					marker.setIcon(lx_bitmap);
					mk.isCk=true;
					//移除原来的圆
					if(crawl_overlay!=null){
					crawl_overlay.remove();	
					}
					// 添加圆
					OverlayOptions ooCircle = new CircleOptions()
					.fillColor(circle_color)
					.center(posit).stroke(new Stroke(2, round_rim_color))
					.radius(mk.radius);
	                 //保存为全局对象
					crawl_overlay =  mBaiduMap.addOverlay(ooCircle);	
					//显示pop
					popView.initPopView(mk);
					baiduMap.showInfoWindow(popView.getPopView());
					//
					operationView.operation(mk);
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(marker.getPosition());
					baiduMap.animateMapStatus(u);
				}
			}
			 
			
		}
		
		private class FenMarkerClickListener implements OnMarkerClickListener{
			@Override
			public boolean onMarkerClick(Marker marker) {
				crawl(marker);
				return true;
			}
			
		}
		
	}

	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("");
		 if(resultCode  == ResultCode.FEN_CODE||resultCode  == ResultCode.FEN_DEL_CODE&&data!=null){//详情
			   Circle circle = (Circle) data.getSerializableExtra("data");
			    //==创建自定义信息
				MarkerInfo mk = initMarkerInfo(circle,true);
				//移除上一次新添加的
				int count = alarmsettView.getMarkerInfoCount();
				for(int i=0;i<count;i++){
				MarkerInfo info = alarmsettView.getMarkerInfo().get(i);
				if(info.isCk){
				info.marker.setIcon(zx_bitmap);
				info.isCk=false;
				}}
				/*//添加view
				alarmsettView.addMarker(mk);*/
				alarmsettView.rmoveMarker(mk);
				//判断是不是已经添加好的数据
				if(alarmsettView.crawl_overlay!=null){
				alarmsettView.crawl_overlay.remove();
				}
				// 添加圆
				OverlayOptions ooCircle = new CircleOptions()
				.fillColor(circle_color)
				.center(new LatLng(mk.lat,mk.lng)).stroke(new Stroke(2, round_rim_color))
				.radius(mk.radius);
                 //保存为全局对象
				alarmsettView.crawl_overlay =  mBaiduMap.addOverlay(ooCircle);	
				
				FencingPopView pview = new FencingPopView(FencingActivity2.this);
				pview.initPopView(mk);
				mBaiduMap.showInfoWindow(pview.getPopView());
				//==
				operationView.operation(mk);
				 
		} 
	}
	
	
	private class OperationView{
		public Button add_crawl;
		public Button det_crawl;
		public Button del_crawl;
		private MarkerInfo markerInfo;
		
		
		public OperationView(Button add_crawl,Button det_crawl,Button del_crawl){
			this.add_crawl=add_crawl;
			this.det_crawl=det_crawl;
			this.del_crawl=del_crawl;
			add_crawl.setVisibility(View.GONE);
			det_crawl.setVisibility(View.GONE);
			del_crawl.setVisibility(View.GONE);
			 
			add_crawl.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					addCrawl();
				}
			});
			det_crawl.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					detCrawl();
				}
			});
			del_crawl.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					Circle circle = markerInfo.circle;
					ContactsDialog dialog = new ContactsDialog(FencingActivity2.this);
					dialog.show();
					dialog.setTitle("提示!");
					dialog.setMessage("确定要删除"+circle.getPositionName()+"围栏?");
					dialog.setNegativeButton("取消");
					dialog.setPositiveButton("确定");
					dialog.setOkOnClickListener(new ContactsDialog.OnClickListener(){
					@Override
					public void onClick() {
					delCrawl();
					}});
						
				}
			});
		}
		
		
		public void hideOperationView(){
			add_crawl.setVisibility(View.GONE);
			det_crawl.setVisibility(View.GONE);
			del_crawl.setVisibility(View.GONE);
		}
		
		public void operation(MarkerInfo markerInfo){
			if(markerInfo.datatype==0){
				add_crawl.setVisibility(View.VISIBLE);
				det_crawl.setVisibility(View.GONE);
				del_crawl.setVisibility(View.GONE);
			}else{
				add_crawl.setVisibility(View.GONE);
				det_crawl.setVisibility(View.VISIBLE);
				del_crawl.setVisibility(View.VISIBLE);
			} 
			this.markerInfo=markerInfo;
			
		}
		
		private void addCrawl(){
			if(markerInfo!=null){
			Circle circle = markerInfo.circle;
			Intent intent = new Intent(FencingActivity2.this,AddElectronicFence.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("data", circle);
			intent.putExtras(bundle);
			startActivityForResult(intent, ResultCode.FEN_DEL_CODE);
			}
			
		}
		
		private void delCrawl(){
			if(markerInfo!=null){
				FencesettingModel fen = new FencesettingModel();//删除
				fen.setType(4);
				fen.setCircle_id(markerInfo.circle.getCircle_id());
				new HttpHandler(loadingDialog,applicationController.httpUtils_lbs,fen,new HttpHandlerCallBack(){
					@Override
					public void onCallBack(String json) {
						System.out.println(json);
						int count = alarmsettView.getMarkerInfoCount();
						for(int i=0;i<count;i++){
						MarkerInfo info = alarmsettView.getMarkerInfo().get(i);
						if(info.lat==markerInfo.lat&&info.lng==markerInfo.lng){
						info.marker.remove();
						alarmsettView.getMarkerInfo().remove(i);
						if(alarmsettView.crawl_overlay!=null){//移出定义的圆
							alarmsettView.crawl_overlay.remove();	
						}
						break;
						}
						}
						operationView.hideOperationView();
						crawl_layout.setVisibility(View.VISIBLE);
						mBaiduMap.hideInfoWindow();
						
					}
					@Override
					public void onErro() {
						
					}});
			}
		}
		
		
		private void detCrawl(){
			if(markerInfo!=null){
				Circle circle = markerInfo.circle;
				Intent intent = new Intent(FencingActivity2.this,AddElectronicFence.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", circle);
				intent.putExtras(bundle);
				startActivityForResult(intent, ResultCode.FEN_DEL_CODE);
			}
		}
	}
	
	
 
}

 

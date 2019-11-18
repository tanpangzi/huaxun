package cn.huaxunchina.cloud.location.app.activity.stt.crawl;

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
public class FencingActivity extends Activity implements OnClickListener,CityDialog.CityDisplay{
	
	
	private MapView mMapView;
	private ZoomControlView  mZooview;
	private BaiduMap mBaiduMap;
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
	private Button cityBtn;
	public ApplicationController applicationController;
	public LoadingDialog loadingDialog;
	private ControlView controlView;
	private FencingView fencingView;
	private LinearLayout crawl_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_crawl_layout);
		initView();
		initData();
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
		findViewById(R.id.poi_search).setOnClickListener(this);
		cityBtn=(Button)findViewById(R.id.poi_city);
		cityBtn.setOnClickListener(this);
		cityBtn.setText(CityDialog.getCityName(this));
		Button add = (Button)findViewById(R.id.crawl_add);
		Button det = (Button)findViewById(R.id.crawl_det);
		Button del = (Button)findViewById(R.id.crawl_del);
		controlView = new ControlView(this,add,det,del);
		crawl_layout = (LinearLayout)findViewById(R.id.crawl_layout1);
		fencingView = new FencingView(this,mBaiduMap,controlView,crawl_layout);
		controlView.setFencingView(fencingView);
	}
	
	
	private void initData(){
		FencesettingModel fen = new FencesettingModel();
		fen.setType(1);
		new HttpHandler(loadingDialog,applicationController.httpUtils_lbs,fen,new HttpHandlerCallBack(){
		@Override
		public void onCallBack(String json) {
		try {
		ResFencesettingModel model = GsonUtils.getSingleBean(json, ResFencesettingModel.class);
		List<Circle> data = model.getData();
		fencingView.initData(data);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}}
		@Override
		public void onErro() {
		}});
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
			CityDialog diolog = new CityDialog(FencingActivity.this, width,height);
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
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
	    
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
			Toast.makeText(FencingActivity.this, "[我的位置]"+location.getAddrStr(), Toast.LENGTH_LONG).show();
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
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("");
		 if(resultCode  == ResultCode.FEN_CODE||resultCode  == ResultCode.FEN_DEL_CODE&&data!=null){//详情
			   Circle circle = (Circle) data.getSerializableExtra("data");
			    //==创建自定义信息
				//MarkerInfo mk = fencingView.initMarkerInfo(circle,true);
			   fencingView.upView(circle);
		} 
	}
	
		 
	
 
}

 

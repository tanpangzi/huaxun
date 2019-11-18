package cn.huaxunchina.cloud.location.app.activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import androidx.fragment.app.Fragment;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView1;
import cn.huaxunchina.cloud.location.app.lbs.MarkerInfo;
import cn.huaxunchina.cloud.location.app.lbs.MarkerManager;
import cn.huaxunchina.cloud.location.app.model.post.LocationModel;
import cn.huaxunchina.cloud.location.app.model.post.LoginModel;
import cn.huaxunchina.cloud.location.app.model.res.ResLocationModel;
import cn.huaxunchina.cloud.location.app.model.res.ResLoginModel;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.tools.Instruction;
import cn.huaxunchina.cloud.location.app.tools.SmsManagerUtil;
import cn.huaxunchina.cloud.location.app.view.LocProgressBarDialog.SmsCancelOnClickListener;
import cn.huaxunchina.cloud.location.app.view.LocationPopView;
import cn.huaxunchina.cloud.location.app.view.LocationSmsDialog;
import cn.huaxunchina.cloud.location.app.view.LocationSmsDialog.SmsOnClickListener;
import cn.huaxunchina.cloud.location.app.view.LoginDialog;
import cn.huaxunchina.cloud.location.app.view.LoginDialog.LocLogin;
import cn.huaxunchina.cloud.location.app.view.ZoomControlView;

/**
 * 
 * TODO(描述) 定位
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年12月29日 下午3:21:18
 * 
 */
public class LocationFragment extends Fragment implements OnClickListener,
		SmsOnClickListener, SmsCancelOnClickListener, LocLogin {

	private Activity activity;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private MarkerManager markerManager;
	// 构建Marker图标
	// 定位相关
	private LocationClient locClient;
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位
	private LoadingDialog loadingDialog;
	private ApplicationController applicationController;
	private ResLocationModel location;
	private LocationPopView popview;

	// 线程池的初始化
	private ScheduledFuture<?> future = null;
	private ScheduledExecutorService execuService = Executors
			.newScheduledThreadPool(1);
	private LoginDialog loginDialog;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		loadingDialog = new LoadingDialog(activity);
		applicationController = (ApplicationController) activity
				.getApplicationContext();
	}

	public void test() {
	}

	@Override
	public void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		mBaiduMap = mMapView.getMap();
		String pwd = ApplicationController.getPwd();
		if (!TextUtils.isEmpty(pwd) && Utils.isNetworkConn()) {
			runData();
			isRun = true;
			System.out.println("出现了=====isRun==========");
		}
		System.out.println("出现了===============");
		super.onResume();
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		isRun = false;
		cancel();
		System.out.println("不见了===============");
		super.onPause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.loc_home_location_layout,
				container, false);
		MyBackView1 back = (MyBackView1) view.findViewById(R.id.back);
		back.setSearchGone();
		back.setBackText("即时定位");
		back.setAddActivty(activity);
		view.findViewById(R.id.loc_me_btn).setOnClickListener(this);
		view.findViewById(R.id.loc_marker_btn).setOnClickListener(this);
		ZoomControlView mZooview = (ZoomControlView) view
				.findViewById(R.id.locaticon_zoomview);
		mMapView = (MapView) view.findViewById(R.id.locaticon_mapView);
		markerManager = new MarkerManager(mMapView);
		mBaiduMap = markerManager.getBaiduMap();
		markerManager.setZoomControlView(mZooview);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 气泡的操作事件注册
		mBaiduMap.setOnMarkerClickListener(new MyMarkerClickListener());

		// 如果没有登陆过，侧进行登陆
		String pwd = ApplicationController.getPwd();
		if (TextUtils.isEmpty(pwd) || !Utils.isNetworkConn()) {
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int width = dm.widthPixels;
			int height = dm.heightPixels;

			loginDialog = new LoginDialog(activity, width, height);
			loginDialog.setCancelable(false);
			loginDialog.setLocLogin(this);
			loginDialog.show();

			loginCallBack();
		} else {
			runData();
		}
		return view;
	}

	@Override
	public void loginCallBack() {
		loginDialog.startAnimation();
		new HttpHandler(null, applicationController.httpUtils_lbs,
				new LoginModel(), new HttpHandlerCallBack() {
					@Override
					public void onCallBack(String json) {
						try {

							String code = GsonUtils.getCode(json);
							if (code.equals(String.valueOf(HandlerCode.LBS_901))) {// 禁用

								// Toast.makeText(ApplicationController.getContext(),
								// "该终端卡已禁用,请联系客服或者系统管理员", 0).show();
								loginDialog.setMsg("该终端卡已禁用,请联系客服或者系统管理员");

							} else if (code.equals(String
									.valueOf(HandlerCode.LBS_902))) {// 无效

								// Toast.makeText(ApplicationController.getContext(),
								// "无效终端卡号,请联系当地客服人员", 0).show();
								loginDialog.setMsg("无效终端卡号,请联系当地客服人员");

							} else if (code.equals(HttpResultStatus.SUCCESS)) {

								String data = GsonUtils.getData(json);
								ResLoginModel model = GsonUtils.getSingleBean(
										data, ResLoginModel.class);
								ApplicationController.setPwd(model
										.getPassword());
								ApplicationController.setSim(model.getSim());
								loginDialog.dismiss();

								runData();

							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onErro() {
						loginDialog.stopAnimation();
					}
				});
	}

	// 实时定位
	@Override
	public void onCallBack() {
		// 发送定位指令
		String phoneNumber = ApplicationController.getSim();
		String content = Instruction.getLocation();
		SmsManagerUtil.sentSms(phoneNumber, content);

	}

	private boolean isRun = true;

	private void runData() {
		cancel();
		future = execuService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (isRun) {
					handler.sendEmptyMessage(TIME_CODE);
				}
			}
		}, 0, 10, TimeUnit.SECONDS);
	}

	// 取消轮循
	@Override
	public void cancel() {
		if (null != future) {
			future.cancel(true);
			future = null;
		}
	}

	final static int TIME_CODE = 101;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case TIME_CODE:
				System.out.println("MaxiTime____3_____post");
				roundRobinData();
				break;
			}
		}
	};

	private void roundRobinData() {
		System.out.println("MaxiTime____1_____post");
		MapStatusUpdate zooout = MapStatusUpdateFactory.zoomTo(14f);
		mBaiduMap.animateMapStatus(zooout);
		new HttpHandler(null, applicationController.httpUtils_lbs,
				new LocationModel(), new HttpHandlerCallBack() {
					@Override
					public void onCallBack(String json) {
						System.out.println("MaxiTime____2_____post");
						ResLocationModel locModel = null;
						try {
							String data = GsonUtils.getData(json);
							locModel = GsonUtils.getSingleBean(data,
									ResLocationModel.class);
							if (locModel.getCreated() == null)
								return;// 判断是不是取到了数据
							location = locModel;
							overlay(location);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onErro() {
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.loc_me_btn:// 定位自己
			Toast.makeText(activity, "开始定位...", Toast.LENGTH_LONG).show();
			locMe();
			break;
		case R.id.loc_marker_btn:// 定位二代卡
			LocationSmsDialog diolog = new LocationSmsDialog(activity, this);
			diolog.setCancelable(true);
			diolog.show();
			break;
		}

	}

	// 定位
	private void locMe() {
		isFirstLoc = true;
		// 定位初始化
		locClient = new LocationClient(activity);
		locClient.registerLocationListener(new MyLocationListenner() {
			@Override
			public void onReceivePoi(BDLocation arg0) {

			}
		});// 注册定位监听接口
		// LocationClientOption 该类用来设置定位SDK的定位方式。
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll"); // 设置坐标类型 /返回的定位结果是百度经纬度,默认值gcj02
		//option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
		//option.setOpenGps(ture);
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms
		//option.disableCache(false);// 禁止启用缓存定位
		locClient.setLocOption(option);// 设置定位参数
		locClient.start();
		mCurrentMode = LocationMode.NORMAL;
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, true, mCurrentMarker));

	}

	// 定位二代卡
	public void overlay(ResLocationModel location) {
		markerManager.clearMarkers();

		LatLng point = new LatLng(location.getLat(), location.getLng());
		MarkerInfo info = new MarkerInfo();
		info.latlng = point;
		info.tag = 0;
		markerManager.drawMarker(info);
		MapStatusUpdate zooout = MapStatusUpdateFactory.zoomTo(11f);
		mBaiduMap.animateMapStatus(zooout);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
		mBaiduMap.animateMapStatus(u);
		popview = new LocationPopView(activity);
		popview.initPopView(location);
		mBaiduMap.showInfoWindow(popview.getPopView());

	}

	/**
	 * 定位SDK监听函数
	 */
	private abstract class MyLocationListenner implements BDLocationListener {
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
				Toast.makeText(activity, "[我的位置]" + location.getAddrStr(),
						Toast.LENGTH_LONG).show();
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation arg0) {
		}

	}

	// 弹出pop_view的操作
	private class MyMarkerClickListener implements OnMarkerClickListener {
		public boolean onMarkerClick(final Marker marker) {
			int indexs = markerManager.getIndexes(marker);
			if (indexs >= 0 && location != null) {
				mBaiduMap.showInfoWindow(popview.getPopView());
			}
			return true;
		}
	}

	@Override
	public void onDestroy() {
		cancel();
		// 退出时销毁定位
		if (locClient != null) {
			locClient.stop();
		}
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		// mMapView.onDestroy();
		// mMapView = null;
		super.onDestroy();
	}

}

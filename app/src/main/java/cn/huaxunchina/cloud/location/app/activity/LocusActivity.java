package cn.huaxunchina.cloud.location.app.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.TimeUtil;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView1;
import cn.huaxunchina.cloud.location.app.activity.stt.TimeStepSelect;
import cn.huaxunchina.cloud.location.app.lbs.LocusManager;
import cn.huaxunchina.cloud.location.app.lbs.MarkerInfo;
import cn.huaxunchina.cloud.location.app.model.post.LocusModel;
import cn.huaxunchina.cloud.location.app.model.res.LocusComparator;
import cn.huaxunchina.cloud.location.app.model.res.ResLocusDatas;
import cn.huaxunchina.cloud.location.app.model.res.ResLocusModel;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.view.LcousPopView;
import cn.huaxunchina.cloud.location.app.view.LocProgressBar;
import cn.huaxunchina.cloud.location.app.view.LocusAboutDialog;
import cn.huaxunchina.cloud.location.app.view.ZoomControlView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
/**
 * 历史轨迹
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月17日 下午4:02:33 
 *
 */
public class LocusActivity extends BaseActivity  implements OnClickListener{
 
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private LocusManager locusManager;
	private LcousPopView popview;
	private List<ResLocusModel> new_datas = new ArrayList<ResLocusModel>();
	//线程池的初始化
	private ScheduledFuture<?> future=null;
	private ScheduledExecutorService execuService= Executors.newScheduledThreadPool(1);
	private final static int TIME_CODE = 1012;
	private final static int DRAW_OVERLAY = 1013;
	private LoadingDialog loadingDialog;
	private LocProgressBar locProgressBar;
	private Button playbtn;
	private boolean playState = false;//判断当前播放状态
	private String startTime = "";
	private String endTime = "";
	private boolean isRun = false;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_home_locus_layout);
		findViewById(R.id.locus_about).setOnClickListener(this);
		findViewById(R.id.locus_playbtn).setOnClickListener(this);
		findViewById(R.id.search_imagebtn).setOnClickListener(this);
		playbtn=(Button)findViewById(R.id.locus_playbtn);
		MyBackView1 back = (MyBackView1)findViewById(R.id.back);
		back.setBackText("历史轨迹");
		back.setAddActivty(this);
		back.searchOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			startActivityForResult(new Intent(LocusActivity.this, TimeStepSelect.class), ResultCode.LOCUS_CODE);
			}
		});
		ZoomControlView mZooview=(ZoomControlView)findViewById(R.id.locus_zoomview);
		ProgressBar progressBar = (ProgressBar)findViewById(R.id.loc_progress);
		mMapView = (MapView)findViewById(R.id.locus_mapView);
		locusManager = new LocusManager(mMapView);
		popview = new LcousPopView(this);
		mBaiduMap =  locusManager.getBaiduMap();
		locusManager.setZoomControlView(mZooview);
		mBaiduMap.clear();
		loadingDialog = new LoadingDialog(this);
		locProgressBar = new LocProgressBar(progressBar);
		startTime = TimeUtil.getCurrentDate()+"000000";
		endTime = TimeUtil.getCurrentDate()+TimeUtil.getCurrentTime();
		initData();
		//点击事件
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener(){
			@Override
			public boolean onMarkerClick(Marker m) {
				int id = locusManager.getIndexes(m);
				MarkerInfo ckInfo = locusManager.getMarkerInfo(m);
				if(id>0){
					MarkerInfo marker1 = locusManager.getLastMarker();
					if(marker1!=null){
					locusManager.setIcon(marker1, false);
					}
					/*MarkerInfo marker2 = new MarkerInfo();
					marker2.marker=m;*/
					locusManager.setIcon(ckInfo, true);
					locusManager.setLastMarker(ckInfo);
					//显示popview
					mBaiduMap.showInfoWindow(popview.getPopView(new_datas.get(id)));
				}
				return false;
			}});
		
	}
	
	
	private void initData(){
		LocusModel loc = new LocusModel();
		loc.setStartTime(startTime);
		loc.setEndTime(endTime);
		new HttpHandler(loadingDialog,httpUtils,loc,new HttpHandlerCallBack(){
		@Override
		public void onCallBack(String json) {
		try {
			    ResLocusDatas data  = GsonUtils.getSingleBean(json, ResLocusDatas.class);
			    Message msg = handler.obtainMessage();
				msg.what = DRAW_OVERLAY;
				msg.obj = data;
				handler.handleMessage(msg);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		@Override
		public void onErro() {
		}});
	}
	
	 
	
	 
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case TIME_CODE:
				if(locusManager.isEnd()){
				int index = locusManager.getIndexs();
				locProgressBar.setProgress(index);
				locusManager.draw(index);
				}else{
				int index = locusManager.getIndexs();//====
				locProgressBar.setProgress(index);
				isRun=false;
				playState=false;
				}
				 
				break;
			case DRAW_OVERLAY:
				ResLocusDatas data = (ResLocusDatas)msg.obj;
				new_datas = sort(data.getData());
				int size = new_datas.size();
				if(size>0){
				List<MarkerInfo> markers = new ArrayList<MarkerInfo>();
				for (int i = 0; i < size; i++) 
				{
				ResLocusModel locusModel = new_datas.get(i);
				MarkerInfo info = new MarkerInfo();
				info.type=locusModel.getLoctype();
				info.tag = i;
				info.latlng=new LatLng(locusModel.getLat(),locusModel.getLng());
				markers.add(info);
				}
				locusManager.setMarkers(markers);
				locusManager.drawAllLine();
				locProgressBar.setProgressLength(size);
				locProgressBar.setProgress(size);
				}
			 
				break;
			}
		}
	};
	
	
 

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.locus_about://轨迹
			LocusAboutDialog diolog = new LocusAboutDialog(this);
			diolog.setCancelable(true);
			diolog.setCanceledOnTouchOutside(true);
			diolog.show();
			break;
		case R.id.locus_playbtn://播放
			run();
			break;
		}
		
	}
	
	
	
	private void run(){
	int size = new_datas.size();
	if(size==0) return;	
	if(playState){//暂停
	playState=false;
	isRun = false;
	playbtn.setBackgroundResource(R.drawable.loc_playbtn);
	}else{//播放
	playState=true;
	isRun = true;
	locusManager.setIndex(1);
	locusManager.clearLocus();
	playbtn.setBackgroundResource(R.drawable.loc_pause);
	}
	 //重新画轨迹的逻辑
	if(playState){
	locProgressBar.setProgress(0);
	if(null!=future){
	future.cancel(true);
	future=null;
	}
	future=execuService.scheduleAtFixedRate(new Runnable(){
		@Override
		public void run() {
			if(isRun){
			handler.sendEmptyMessage(TIME_CODE);}
		}
	}, 0, 500, TimeUnit.MILLISECONDS);}
	
	}
	


	private List<ResLocusModel> sort(List<ResLocusModel> datas){
		LocusComparator comparator = new LocusComparator();
		Collections.sort(datas, comparator);// 排序
		return datas;
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode  == ResultCode.LOCUS_CODE&&data!=null) {
			startTime = data.getStringExtra("startTime");
			endTime = data.getStringExtra("endTime");
			locusManager.clearLocus();
			initData();
		}
	}

}

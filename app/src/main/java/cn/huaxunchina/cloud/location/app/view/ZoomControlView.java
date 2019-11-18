package cn.huaxunchina.cloud.location.app.view;

import cn.huaxunchina.cloud.app.R;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;

public class ZoomControlView extends RelativeLayout implements OnClickListener{
	private Button mButtonZoomin;
	private Button mButtonZoomout;
	private MapView mapView;
	private BaiduMap baiuMap;
	private float maxZoomLevel;
	private float minZoomLevel;
	
	public ZoomControlView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ZoomControlView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	
	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.loc_zoom_controls_layout, null);
		mButtonZoomin = (Button) view.findViewById(R.id.zoomin);
		mButtonZoomout = (Button) view.findViewById(R.id.zoomout);
		mButtonZoomin.setOnClickListener(this);
		mButtonZoomout.setOnClickListener(this);
		addView(view);
	}

	@Override
	public void onClick(View v) {
		if(mapView == null){
			throw new NullPointerException("you can call setMapView(MapView mapView) at first");
		}
		switch (v.getId()) {
		case R.id.zoomin:{
			MapStatusUpdate zooin = MapStatusUpdateFactory.zoomIn();
			baiuMap.animateMapStatus(zooin);
			break;
		}
		case R.id.zoomout:{
			MapStatusUpdate zooout = MapStatusUpdateFactory.zoomOut();
			baiuMap.animateMapStatus(zooout);
			 
			break;
		}
		}
	}

	/**
	 * 与MapView设置关联
	 * @param mapView
	 */
	public void setMapView(MapView mapView,BaiduMap baiuMap) {
		this.mapView = mapView;
		this.baiuMap=baiuMap;
		// 获取最大的缩放级别
		maxZoomLevel = baiuMap.getMaxZoomLevel();
		// 获取最大的缩放级别
		minZoomLevel = baiuMap.getMinZoomLevel();
	}
	
	
	/**
	 * 根据MapView的缩放级别更新缩放按钮的状态，当达到最大缩放级别，设置mButtonZoomin
	 * 为不能点击，反之设置mButtonZoomout
	 * @param level
	 */
	public void refreshZoomButtonStatus(int level){
		if(mapView == null){
			throw new NullPointerException("you can call setMapView(MapView mapView) at first");
		}
		if(level > minZoomLevel && level < maxZoomLevel){
			if(!mButtonZoomout.isEnabled()){
				mButtonZoomout.setEnabled(true);
			}
			if(!mButtonZoomin.isEnabled()){ 
				mButtonZoomin.setEnabled(true);
			}
		}
		else if(level == minZoomLevel ){
			mButtonZoomout.setEnabled(false);
		}
		else if(level == maxZoomLevel){
			mButtonZoomin.setEnabled(false);
		}
	}

}

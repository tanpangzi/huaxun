package cn.huaxunchina.cloud.location.app.lbs;

import cn.huaxunchina.cloud.location.app.view.ZoomControlView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.model.LatLng;
/**
 * 所有相关地图操作的夫类
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年2月2日 上午10:59:23 
 *
 */
public abstract class MapManager {
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	
	public  MapManager(MapView mMapView){
		this.mMapView=mMapView;
		this.mBaiduMap=mMapView.getMap();
		init();
	}
	
	protected MapView getMapView(){
		return this.mMapView;
	}
	
	public BaiduMap getBaiduMap(){
		return this.mBaiduMap;
	}
	
	protected void init(){
		//隐藏baidu自带放大控件
		mMapView.showZoomControls(false);
		//设置地图不可以旋转
		mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);
	    //设置地图缩放级别
		MapStatusUpdate zooout = MapStatusUpdateFactory.zoomTo(14f);
		mBaiduMap.animateMapStatus(zooout);
		mBaiduMap.setOnMapClickListener(new OnMapClickListener(){
		@Override
		public void onMapClick(LatLng arg0) {
		mBaiduMap.hideInfoWindow();
		}
		@Override
		public boolean onMapPoiClick(MapPoi arg0) {
			return false;
		}});
	}
	
	/**
	 * TODO(描述) 设置自定义放大控件
	 * @param zoomControlView
	 */
	public void setZoomControlView(ZoomControlView zoomControlView){
		zoomControlView.setMapView(mMapView,mBaiduMap);
	}
	
	
	
	

}

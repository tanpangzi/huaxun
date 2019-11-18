package cn.huaxunchina.cloud.location.app.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.tools.DisplayUtil;
import cn.huaxunchina.cloud.location.app.model.MarkerInfo;

import com.baidu.mapapi.map.InfoWindow;

public class FencingPopView {
	
	
	private InfoWindow popView;
	private View view;
	private TextView name;
	private TextView address;
	private TextView radius;
	private TextView state;
	
	public FencingPopView(Activity activity){
		 view = View.inflate(activity, R.layout.loc_fencing_tips, null);
		 name = (TextView)view.findViewById(R.id.fen_name);
		 address = (TextView)view.findViewById(R.id.fen_address);
		 radius = (TextView)view.findViewById(R.id.fen_radius);
		 state = (TextView)view.findViewById(R.id.fen_state);
	}
	 
	 
	
	public void initPopView(MarkerInfo mk){
		name.setVisibility(View.VISIBLE);
		radius.setVisibility(View.VISIBLE);
		state.setVisibility(View.VISIBLE);
		name.setText("围栏名称:"+mk.name);
		address.setText("地址:"+mk.address);
		radius.setText("半径:"+mk.radius+"米");
		if(mk.state){
		state.setText("状态:已开启");	
		}else{
		state.setText("状态:已关闭");
		}
		int arg = -DisplayUtil.dip2px(20);
		popView = new InfoWindow(view,mk.marker.getPosition(), arg);
	}
	
	public InfoWindow getPopView(){
		
		return popView;
	}
	
	 
	
}

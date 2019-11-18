package cn.huaxunchina.cloud.location.app.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.model.res.ResLocationModel;

import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.model.LatLng;

public class LocationPopView {
	
	
	private InfoWindow popView;
	private Drawable drawable_lx;
	private Drawable drawable_zx; 
	private Activity activity;
	private View view;
	private TextView address;
	private TextView time;
	private TextView loctype;
	private TextView online;
	private BatteryView bat;
	private  TextView bat_txt;
	
	public LocationPopView(Activity activity){
		this.activity=activity;
		 drawable_lx= activity.getResources().getDrawable(R.drawable.loc_lx_mark_pop);
		 drawable_zx= activity.getResources().getDrawable(R.drawable.loc_zx_mark_pop); 
		 view = View.inflate(activity, R.layout.loc_location_tips, null);
		 address = (TextView)view.findViewById(R.id.location_address);
		 time = (TextView)view.findViewById(R.id.location_time);
		 loctype = (TextView)view.findViewById(R.id.location_type);
		 online = (TextView)view.findViewById(R.id.location_online);
		 bat_txt=(TextView)view.findViewById(R.id.location_bat_txt);
	}
	 
	 
	
	public void initPopView(ResLocationModel location){
		bat=(BatteryView)view.findViewById(R.id.location_bat);
		address.setText(location.getAddr());
		time.setText(location.getCreatedStr());
		loctype.setText(location.getLoctypeStr());
		if(location.isOnline()){
			drawable_zx.setBounds(0, 0, drawable_zx.getMinimumWidth(), drawable_zx.getMinimumHeight()); 
			online.setCompoundDrawables(drawable_zx,null,null,null);
			online.setText("在线");
		}else{
			online.setText("离线");
			drawable_lx.setBounds(0, 0, drawable_lx.getMinimumWidth(), drawable_lx.getMinimumHeight()); 
			online.setCompoundDrawables(drawable_lx,null,null,null);
		}
		getPower(location.getBat(),bat,bat_txt);
		LatLng point = new LatLng(location.getLat(), location.getLng());
		popView = new InfoWindow(view,point, -16);
	}
	
	public InfoWindow getPopView(){
		
		return popView;
	}
	
	private void getPower(int level,BatteryView bat,TextView bat_txt) {
		int power = level;
		if (power > 20) {
			bat_txt.setTextColor(activity.getResources().getColor(R.color.loc_bule));
		} else {
			bat_txt.setTextColor(activity.getResources().getColor(R.color.loc_red));
		}
		bat_txt.setText(power + "%");
		bat.setPower(power);

	}
	
}

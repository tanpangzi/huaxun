package cn.huaxunchina.cloud.location.app.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.model.LocusItem;
import cn.huaxunchina.cloud.location.app.model.res.ResLocusModel;

import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.model.LatLng;

public class LcousPopView {
	
	private InfoWindow popView;
	private View view = null;
	private TextView address ;
	private TextView time ;
	private TextView loctype;
	
	public LcousPopView(Context context){
	view = View.inflate(context, R.layout.loc_lcous_tips, null);
	address = (TextView)view.findViewById(R.id.location_address);
	time = (TextView)view.findViewById(R.id.location_time);
	loctype = (TextView)view.findViewById(R.id.location_type);
	}
	
	public InfoWindow getPopView(ResLocusModel m){
		popView = null;
		address.setText(m.getAddrStr());
		time.setText(m.getCreatedStr());
		loctype.setText(m.getLoctypeStr());
		LatLng point = new LatLng(m.getLat(), m.getLng());
		popView = new InfoWindow(view,point, -10);
		return popView;
	}
	
}

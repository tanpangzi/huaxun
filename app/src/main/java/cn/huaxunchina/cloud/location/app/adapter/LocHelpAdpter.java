package cn.huaxunchina.cloud.location.app.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.huaxunchina.cloud.app.tools.ViewHolder;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.view.ExpandableTextView;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocHelpAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private SparseBooleanArray mConvertTextCollapsedStatus = new SparseBooleanArray();
	private List<String> title = new ArrayList<String>();
	private List<String> msg = new ArrayList<String>();
	
	public LocHelpAdpter(Context context) {
		this.mContext  = context;
        this.mInflater = LayoutInflater.from(mContext);
        title.add("什么是基站定位？");
        title.add("基站定位与Gps定位的区别？");
        msg.add("基站定位一般应用于手机用户，手机基站定位服务又叫做移动位置服务（LBS——Location Based Service），它是通过电信移动运营商的网络（如GSM网）获取移动终端用户的位置信息（经纬度坐标），在电子地图平台的支持下，为用户提供相应服务的一种增值业务，例如目前中国移动动感地带提供的动感位置查询服务等。");
        msg.add("GPS定位使用卫星，比较费电，精确，但在室内无法定位。基站定位的精度较低，但是可以在室内定位。");
	}

	@Override
	public int getCount() {
		return title.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.loc_help_list_item, null);
		} 
		TextView loc_help_title =ViewHolder.get(convertView, R.id.loc_help_title);
		loc_help_title.setText(title.get(position));
		ExpandableTextView expandableTextView=ViewHolder.get(convertView, R.id.expand_text_view);
		expandableTextView.setConvertText(mConvertTextCollapsedStatus,position, msg.get(position));
		return convertView;
	}
}

package cn.huaxunchina.cloud.location.app.adapter;

import cn.huaxunchina.cloud.app.tools.ViewHolder;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AlarmSetAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private int size = 0;
	private final String[] locAlarmStrings;
	
	public AlarmSetAdpter(Context context) {
		this.mContext  = context;
		locAlarmStrings = mContext.getResources().getStringArray(R.array.loc_alarm_setting);
        this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (locAlarmStrings != null) {
			size = locAlarmStrings.length;
		}
		return size;
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
			convertView = mInflater.inflate(R.layout.loc_alarm_list_item, null);
		} 
		TextView loc_alarm_type =ViewHolder.get(convertView, R.id.loc_alarm_type);
		TextView loc_alarm_time =ViewHolder.get(convertView, R.id.loc_alarm_time);
		TextView loc_alarm_statue =ViewHolder.get(convertView, R.id.loc_alarm_statue);
		TextView loc_alarm =ViewHolder.get(convertView, R.id.loc_alarm);
		CheckBox setting_alarm_ck =ViewHolder.get(convertView, R.id.setting_alarm_ck);
		loc_alarm_type.setText(locAlarmStrings[position]);
		loc_alarm_time.setText("03:22");
		loc_alarm_statue.setText("无");
		loc_alarm.setText("关闭");
		return convertView;
	}
}

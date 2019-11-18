package cn.huaxunchina.cloud.location.app.adapter;

import cn.huaxunchina.cloud.app.tools.ViewHolder;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocMessDetailAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private int size = 0;
	private String[]  detial_key;
	private String[]  detial_val;

	public LocMessDetailAdpter(Context context, String[] detail_key,String[] detail_val) {
		this.mContext = context;
		this.detial_key=detail_key;
		this.detial_val=detail_val;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (detial_val != null) {
			size = detial_val.length;
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
			convertView = mInflater.inflate(R.layout.loc_mess_detail_item, null);
		} 
		TextView loc_detail_title =ViewHolder.get(convertView, R.id.loc_detail_title);
		TextView loc_detail_content =ViewHolder.get(convertView, R.id.loc_detail_content);
		loc_detail_title.setText(detial_key[position]);
		loc_detail_content.setText(detial_val[position]);
		if(position==(detial_val.length-1)){
			loc_detail_content.setAutoLinkMask(Linkify.ALL); 
			loc_detail_content.setMovementMethod(LinkMovementMethod.getInstance()); 
		}
		return convertView;
	}

}

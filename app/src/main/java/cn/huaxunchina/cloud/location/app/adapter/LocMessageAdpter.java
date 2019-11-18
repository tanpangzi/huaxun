package cn.huaxunchina.cloud.location.app.adapter;

import java.util.List;
import cn.huaxunchina.cloud.app.tools.ViewHolder;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.model.res.ResMessageModel;
import cn.huaxunchina.cloud.location.app.tools.StringUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocMessageAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<ResMessageModel> items;
	private int size = 0;

	public LocMessageAdpter(Context context, List<ResMessageModel> items) {
		this.mContext = context;
		this.items = items;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (items != null) {
			size = items.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {

		return position;
	}
	public void addItems(List<ResMessageModel> data, boolean next) {
		if (next) {
			if (data != null) {
				items.addAll(data);
				notifyDataSetChanged();
			}
		} else {
			if (data != null) {
				this.items.clear();
				this.items.addAll(data);
				notifyDataSetChanged();
			}
		}
	}

	public void addLocalItems(ResMessageModel data) {
		items.add(0, data);
		notifyDataSetChanged();
	}

	public List<ResMessageModel> getItemsValue() {
		return items;
	}

	public ResMessageModel getItemLeave(int postion) {
		return items.get(postion);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ResMessageModel locMessage = items.get(position);
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.loc_mess_list_item, null);
		}
		TextView loc_title_time = ViewHolder.get(convertView,R.id.loc_title_time);
		TextView loc_title_notice = ViewHolder.get(convertView,R.id.loc_title_notice);
		TextView loc_mess_conent = ViewHolder.get(convertView,R.id.loc_mess_conent);
		String loc_time=locMessage.getCreateTime();
		loc_title_time.setText(loc_time);
		String messageType=locMessage.getCategoryId();
		if(messageType.equals("2")){
			loc_title_notice.setText("低电量提醒");	
		}else if(messageType.equals("3")){
			loc_title_notice.setText("进入围栏提醒");	
		}else{
			loc_title_notice.setText("已出围栏提醒");	
		}
		loc_mess_conent.setText(StringUtil.replaceNull(locMessage.getAddress()));
		convertView.setBackgroundResource(R.drawable.layout_selector_bg);
		return convertView;
	}

}

package cn.huaxunchina.cloud.app.adapter;

import java.util.List;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.model.SMSHistory;
import cn.huaxunchina.cloud.app.tools.TimeUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HosMessageAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private int size = 0;
	private List<SMSHistory> itmes;
	private String todayDate;

	public HosMessageAdpter(Context context, List<SMSHistory> itmes,String todayDate) {
		this.mContext = context;
		this.itmes = itmes;
		if(todayDate!=null){
			this.todayDate=todayDate;
		}
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (itmes != null) {
			size = itmes.size();
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

	public void addItems(List<SMSHistory> data, boolean next) {
		if (next) {
			if (data != null) {
				itmes.addAll(data);
				notifyDataSetChanged();
			}
		} else {
			if (data != null) {
				this.itmes = data;
				notifyDataSetChanged();
			}
		}
	}
	
	public SMSHistory getItemSMSHistory(int postion) {
		return itmes.get(postion);
	}
	
	public List<SMSHistory> getItemsValue() {
		return itmes;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		SMSHistory data=itmes.get(position);
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.hos_mess_list_item, null);
			holder.hosMessContent=(TextView) convertView.findViewById(R.id.hos_list_content);
			holder.hosMessTime=(TextView) convertView.findViewById(R.id.hos_list_time);
			holder.hosMessTitle=(TextView) convertView.findViewById(R.id.hos_list_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.hosMessContent.setText(data.getContent());
		holder.hosMessTitle.setText(data.getNames());
		holder.hosMessTime.setText(TimeUtil.getDateDetail(data.getCreateTime().toString(),todayDate));
		convertView.setBackgroundResource(R.drawable.layout_selector_bg);
		return convertView;
	}

	public final class ViewHolder {
		public TextView hosMessTitle; // 历史短信主题
		public TextView hosMessTime; // 历史短信时间
		public TextView hosMessContent;// 历史短信内容

	}
}

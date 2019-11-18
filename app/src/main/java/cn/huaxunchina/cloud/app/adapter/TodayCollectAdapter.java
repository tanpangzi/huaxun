package cn.huaxunchina.cloud.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadData;
import cn.huaxunchina.cloud.app.R;

/**
 * 收藏夹列表适配数据
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-21 下午12:04:41
 */

public class TodayCollectAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<TodayReadData> itmes;
	private int size = 0;
	private TodayReadData data;

	public TodayCollectAdapter(Context context,
			List<TodayReadData> toadayCollectArray) {
		this.mContext = context;
		this.itmes = toadayCollectArray;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (itmes != null) {
			size = itmes.size();
		}
		return size;
	}

	public void addItems(List<TodayReadData> data, boolean next) {
		if (next) {
			if (data != null) {
				itmes.addAll(data);
				notifyDataSetInvalidated();
			}
		} else {
			if (data != null) {
				this.itmes=data;
				notifyDataSetInvalidated();
			}
		}
	}

	public void removeLocalItems(TodayReadData data) {
		if (data.getReadId() != null) {
			int id = Integer.valueOf(data.getReadId()) - 1;
			itmes.remove(id);
			notifyDataSetInvalidated();
		}

	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public TodayReadData getItemTodayCollect(int postion) {
		return itmes.get(postion);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.today_collect_item, null);
			holder.collect_title = (TextView) convertView.findViewById(R.id.collect_title);
			holder.collect_source = (TextView) convertView.findViewById(R.id.collect_sorce);
			holder.collect_content = (TextView) convertView.findViewById(R.id.collect_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		data = itmes.get(position);
		holder.collect_title.setText(data.getTitle());
		holder.collect_content.setText(data.getContext());
		holder.collect_source.setText(data.getOrigin());
		return convertView;
	}

	public final class ViewHolder {
		public TextView collect_title; // 每日一读主题
		public TextView collect_source; // 收藏來源
		public TextView collect_content; // 收藏列表内容
	}

}

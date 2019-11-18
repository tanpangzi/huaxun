package cn.huaxunchina.cloud.app.adapter;

import java.util.List;
import java.util.Map;
import cn.huaxunchina.cloud.app.model.leave.LeaveProperty;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LeaveManageAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<LeaveProperty> items;
	private int size = 0;
	private String[] leave_type;
	private LeaveProperty leave_property;
	private Map<String, String> leave_state;
	private int type;

	public LeaveManageAdpter(Context context, List<LeaveProperty> items,String[] leave_type, Map<String, String> leave_state) {
		this.mContext = context;
		this.items = items;
		this.leave_type = leave_type;
		this.leave_state = leave_state;
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

	public void addItems(List<LeaveProperty> data, boolean next) {
		if (next) {
			if (data != null) {
				items.addAll(data);
				notifyDataSetChanged();
			}
		} else {
			if (data != null) {
				this.items = data;
				notifyDataSetChanged();
			}
		}
	}

	public void addLocalItems(LeaveProperty data) {
		items.add(0,data);
		notifyDataSetChanged();
	}

	public List<LeaveProperty> getItemsValue() {
		return items;
	}

	public LeaveProperty getItemLeave(int postion) {
		return items.get(postion);
	}
	
	
	public void updatelItems(LeaveProperty data) {
		if (data.getAskLeaveId()!= null&&data.getApproveStatus()!=null) {
		int id = Integer.valueOf(data.getAskLeaveId())- 1;
		items.get(id).setApproveStatus(data.getApproveStatus());
		notifyDataSetInvalidated();
		}
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.leave_list_item, null);
			holder.leave_name = (TextView) convertView.findViewById(R.id.leave_name);
			holder.leave_time = (TextView) convertView.findViewById(R.id.leave_time);
			holder.leave_sumbit_time = (TextView) convertView.findViewById(R.id.leave_sumbit_time);
			holder.leave_state = (TextView) convertView.findViewById(R.id.leave_state_code);
			holder.leave_type = (TextView) convertView.findViewById(R.id.leave_type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		leave_property = items.get(position);
		type = Integer.valueOf(leave_property.getLeaveType());
		holder.leave_name.setText(leave_property.getStudentName());
		String date = DateUtil.getDateInterval(leave_property.getEndTime(),leave_property.getStartTime()).toString();
		if (date.equals("0天0小时")) {
			holder.leave_time.setText("时间异常");
		} else {
			
			holder.leave_time.setText(date);
		}
		holder.leave_sumbit_time.setText(DateUtil.getDateDetail(leave_property.getAskTime()));
		String leave_state_value = leave_state.get(leave_property.getApproveStatus().toString());
		if (leave_state_value.equals("同意")) {
			holder.leave_state.setTextColor(mContext.getResources().getColor(R.color.leave_type_color2));
		} else if (leave_state_value.equals("未审批")) {
			holder.leave_state.setTextColor(mContext.getResources().getColor(R.color.grey));
		} else if (leave_state_value.equals("退回")) {
			holder.leave_state.setTextColor(mContext.getResources().getColor(R.color.leave_type_color3));
		}
		holder.leave_state.setText(leave_state_value);
		if (leave_type != null) {
			holder.leave_type.setText(leave_type[type]);
		}
		convertView.setBackgroundResource(R.drawable.layout_selector_bg);
		return convertView;

	}

	public final class ViewHolder {
		public TextView leave_name; // 请假主题
		public TextView leave_time; // 请假时间
		public TextView leave_sumbit_time;// 请假提交时间
		public TextView leave_state;// 审核状态
		public TextView leave_type;// 审核类型

	}
}

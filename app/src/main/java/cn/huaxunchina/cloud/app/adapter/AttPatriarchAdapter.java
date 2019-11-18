package cn.huaxunchina.cloud.app.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.model.AttDayComparator;
import cn.huaxunchina.cloud.app.model.AttendanceInfo;
import cn.huaxunchina.cloud.app.model.AttendanceInfo.AttDay;
import cn.huaxunchina.cloud.app.tools.TimeUtil;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 家长考勤列表数据Adapter
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月16日 下午5:11:35
 * 
 */
public class AttPatriarchAdapter extends BaseAdapter {

	private Context context;
	private List<AttDay> day_list = new ArrayList<AttDay>();

	public AttPatriarchAdapter(AttendanceInfo info) {
		this.context = ApplicationController.getContext();
		if (info != null) {
			day_list = info.getCardata();
			AttDayComparator comparator = new AttDayComparator();
			Collections.sort(day_list, comparator);
			// System.out.println(day_list);

		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return day_list.size();
	}

	public void clear() {
		if (day_list != null) {
			day_list.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int itemId, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		if (view == null) {
			view = View.inflate(context, R.layout.att_patriarch_item, null);
			holder = new ViewHolder();
			holder.tvTime = (TextView) view.findViewById(R.id.att_time);
			holder.tvStatus = (TextView) view.findViewById(R.id.att_status);
			holder.tvAtttime = (TextView) view.findViewById(R.id.att_time_icon);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		AttDay info = day_list.get(itemId);
		int type = info.getAttendanceType();
		String result = info.getResult();
		String signTim = info.getSignTime();
		switch (type) {
		case 1:
			holder.tvTime.setText("早上到校时间:");
			break;
		case 2:
			holder.tvTime.setText("中午离校时间:");
			break;
		case 3:
			holder.tvTime.setText("下午到校时间:");
			break;
		case 4:
			holder.tvTime.setText("下午离校时间:");
			break;
		case 5:
			holder.tvTime.setText("晚上到校时间:");
			break;
		case 6:
			holder.tvTime.setText("晚上离校时间:");
			break;
		}

		if (result == null) {
			holder.tvStatus.setText("未打卡");
		} else if (result.equals("1") && signTim != null) {// 离校
			holder.tvStatus.setText(TimeUtil.TimetoString(signTim));
			holder.tvAtttime.setBackgroundResource(R.drawable.att_time_out);
		} else if (result.equals("0") && signTim != null) {// 进校
			holder.tvStatus.setText(TimeUtil.TimetoString(signTim));
			holder.tvAtttime.setBackgroundResource(R.drawable.att_time_in);

		}
		return view;
	}

	private class ViewHolder {
		private TextView tvTime;
		private TextView tvStatus;
		private TextView tvAtttime;
	}

}

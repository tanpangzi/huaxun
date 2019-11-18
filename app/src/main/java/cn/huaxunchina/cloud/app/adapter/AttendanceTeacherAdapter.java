package cn.huaxunchina.cloud.app.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.model.AttendanceInfo;
import cn.huaxunchina.cloud.app.model.AttendanceInfo.AttDay;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 老师考勤Adapter
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月16日 下午5:11:35
 * 
 */
public class AttendanceTeacherAdapter extends BaseAdapter {

	private Context context;
	private List<AttendanceInfo> listdata;
	 private int mseclection=-1;
	 
	public AttendanceTeacherAdapter(List<AttendanceInfo> listdata) {
		this.context = ApplicationController.getContext();
		this.listdata = initData(listdata);
	}

	@Override
	public int getCount() {

		int count = 0;
		if (listdata != null) {
			count = listdata.size();
		}
		return count;
	}

	public AttendanceInfo getAttendanceInfo(int item) {
		return listdata.get(item);
	}

	@Override
	public Object getItem(int arg0) {

		return null;
	}

	public void setSeclection(int position){
		this.mseclection=position;
		notifyDataSetChanged();
	}
	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int id, View view, ViewGroup arg2) {
		ViewHolder holder ;
		if (view == null) {
			view = View.inflate(context, R.layout.att_teacher_item, null);
			holder = new ViewHolder();
			holder.titile = (TextView) view.findViewById(R.id.attendance_studentname);
			holder.type = (ImageView) view.findViewById(R.id.attendance_status);
			holder.attend_image = (ImageView) view.findViewById(R.id.attend_image);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		AttendanceInfo info = listdata.get(id);
		holder.titile.setText(info.getName());
		
		// 1.未打卡 2.漏打卡 3.全勤
		if (info.getAttType() == 1) {
			holder.type.setVisibility(View.VISIBLE);
			holder.type.setImageResource(R.drawable.att_item_status);
			holder.attend_image.setImageResource(R.drawable.att_item_bg);
		} else if (info.getAttType() == 2) {
			holder.type.setVisibility(View.VISIBLE);
			holder.type.setImageResource(R.drawable.loser_card);
			holder.attend_image.setImageResource(R.drawable.att_item_bg);
		} else if (info.getAttType()== 3) {
			holder.type.setVisibility(View.GONE);
			holder.attend_image.setImageResource(R.drawable.att_item_bg_bule);
		}
		
		if(mseclection==id){
			view.setBackgroundResource(R.drawable.classscore_item_bg1);
		}else{
			view.setBackgroundResource(R.drawable.classscore_item_bg);
		}
		return view;
	}

	static class ViewHolder {
		private TextView titile;
		private ImageView type;
		private ImageView attend_image;
	}

	/**
	 * 是否有异常
	 * 
	 * @param att_day
	 * @return
	 */
	private int attStatus(List<AttDay> att_day) {
		int type = 1;// 1.未打卡 2.漏打卡 3.全勤
		int att_count = 0;
		if (att_day != null) {
			int count = att_day.size();
			int size = att_day.size();
			for (int i = 0; i < size; i++) {
				AttDay info = att_day.get(i);
				if (info.getResult() == null) {
					att_count++;
				}
			}
			if (att_count == count) {
				type = 1;
			} else if (att_count == 0) {
				type = 3;
			} else {
				type = 2;
			}

		}
		return type;

	}
	
	/**
	 * TODO(描述) 初始化考勤类型
	  * @param listdata
	  * @return
	 */
	private List<AttendanceInfo> initData(List<AttendanceInfo> listdata){
		List<AttendanceInfo> attlist = new ArrayList<AttendanceInfo>();
		if(listdata==null){
			return  attlist;
		}
		int count = listdata.size();
		for (int i = 0; i < count; i++) {
			AttendanceInfo info = listdata.get(i);
			List<AttDay> att_day = info.getCardata();
			info.setAttType(attStatus(att_day));
			attlist.add(info);
		}
		return attlist;
		
	}

}

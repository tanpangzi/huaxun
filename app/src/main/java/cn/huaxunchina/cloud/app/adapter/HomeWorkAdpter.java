package cn.huaxunchina.cloud.app.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokProperty;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.TimeUtil;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeWorkAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private int size = 0;
	private List<HomeWrokProperty> itmes;
	private boolean visflag = false;
	private String curRoleId;
	private TextView title;
	private int curCount = 0;

	private int[] homeWrok_imagesIds = new int[] { R.drawable.yw,
			R.drawable.sx, R.drawable.yingyu, R.drawable.wl, R.drawable.hx,
			R.drawable.sw, R.drawable.zz, R.drawable.ls, R.drawable.dl,
			R.drawable.yy, R.drawable.ty }; // 图片资源
	private String[] courseName = new String[] { "语文", "数学", "英语", "物理", "化学",
			"生物", "政治", "历史", "地理", "音乐", "体育" }; // 科目

	public HomeWorkAdpter(Context context, final List<HomeWrokProperty> itmes,String curRoleId, TextView title) {
		this.mContext = context;
		this.itmes = itmes;
		this.curRoleId = curRoleId;
		this.title = title;
		size = itmes.size();
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (itmes != null) {
			size = itmes.size();
		}
		return size;
	}

	public int getTotalCount() {
		int totalCount = 0;
		for (int i = 0; i < size; i++) {
			String teacher_id = itmes.get(i).getTeacherid();
			if (teacher_id.equals(curRoleId)) {
				totalCount++;
			}
		}
		return totalCount;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	public void addItems(List<HomeWrokProperty> data, boolean next) {
		if (next) {
			if (data != null) {
				itmes.addAll(data);
				notifyDataSetInvalidated();
			}
		} else {
			if (data != null) {
				this.itmes = data;
				notifyDataSetInvalidated();
				curCount = 0;
			}
		}
		setTitle(curCount);
	}

	public HomeWrokProperty getItemHomeWork(int postion) {
		return itmes.get(postion);
	}

	public void isCheckVisible(boolean visflag) {
		this.visflag = visflag;
		notifyDataSetChanged();
	}

	public void setAllCk(boolean isCk) {
		for (int i = 0; i < itmes.size(); i++) {
			String teacher_id = itmes.get(i).getTeacherid();
			if (teacher_id.equals(curRoleId)) {
				itmes.get(i).setCheck(isCk);
			}
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addLocalItems(HomeWrokProperty data) {
		itmes.add(0, data);
		notifyDataSetChanged();
	}

	public List<HomeWrokProperty> getItemsValue() {
		return itmes;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.home_work_list_item, null);
			holder.course_image = (ImageView) convertView.findViewById(R.id.kc_image);
			holder.course_content = (TextView) convertView
					.findViewById(R.id.kc_title);
			holder.course_time = (TextView) convertView
					.findViewById(R.id.kc_time);
			holder.homeCk = (CheckBox) convertView
					.findViewById(R.id.home_work_check);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		HomeWrokProperty homeWrok_property = itmes.get(position);
		for (int i = 0; i < courseName.length; i++) {
			if (homeWrok_property.getCourseName().equals(courseName[i])) {
				holder.course_image.setImageResource(homeWrok_imagesIds[i]);
			}
		}
		List<String> tempList = Arrays.asList(courseName);
		if (!tempList.contains(homeWrok_property.getCourseName())) {
			holder.course_image.setImageResource(R.drawable.qt);
		}
		holder.course_content.setText(homeWrok_property.getContent());
		holder.course_time.setText(DateUtil.getDateDetail(homeWrok_property
				.getReleaseTime()));
		convertView.setBackgroundResource(R.drawable.layout_selector_bg);

		if (visflag) {
			holder.homeCk.setVisibility(View.VISIBLE);
		} else {
			holder.homeCk.setVisibility(View.GONE);
		}
		homeCkSelect(holder.homeCk, homeWrok_property, convertView);

		return convertView;

	}

	public final class ViewHolder {
		public ImageView course_image; // 科目图标
		public TextView course_content; // 作业内容
		public TextView course_time; // 评论内容
		public CheckBox homeCk;// 家庭作业批量删除ck
	}

	/**
	 * 长按显示check选项条目
	 * 
	 * @param ck
	 * @param position
	 */
	public void homeCkSelect(final CheckBox ck,
			final HomeWrokProperty homeWrok_property, View view) {
		if (homeWrok_property.isCheck()) {
			ck.setChecked(true);
		} else {
			ck.setChecked(false);
		}
		String teacher_id = homeWrok_property.getTeacherid();
		if (teacher_id != null) {
			if (teacher_id.equals(curRoleId)) {
				ck.setClickable(true);
				homeWrok_property.setClick(true);
				ck.setBackgroundResource(R.drawable.setting_homework_check);
			} else {
				ck.setClickable(false);
				homeWrok_property.setClick(false);
				ck.setBackgroundResource(R.drawable.homework_checkbox_nockeck);
			}
		}

		ck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isChecked = homeWrok_property.isCheck();
				if (isChecked) {
					ck.setChecked(false);
					homeWrok_property.setCheck(false);
					curCount--;
				} else {
					ck.setChecked(true);
					homeWrok_property.setCheck(true);
					curCount++;
				}
				notifyDataSetChanged();
				setTitle(curCount);
			}
		});
	}

	public void setTitle(int curCount) {
		this.curCount = curCount;
		if (curCount == getTotalCount()) {
			title.setText("全不选");
		} else {
			title.setText("全选");
		}
	}

	public int getCurCount() {
		return curCount;
	}

	public void setCurCount(int curCount) {
		this.curCount = curCount;
	}

	public List<Boolean> isCurDayData() {
		boolean diaFlag = false;
		String curserTime = "";
		String releaseTime = "";
		List<Boolean> list = new ArrayList<Boolean>();
		for (int i = 0; i < itmes.size(); i++) {
			if (itmes.get(i).isCheck()) {
				curserTime = itmes.get(i).getNowTime();
				releaseTime = itmes.get(i).getReleaseTime();
				int day = (int) TimeUtil.getDiffTime(curserTime, releaseTime);
				if (day < 1) {
					diaFlag = true;
				}
			}
			list.add(diaFlag);
		}
		return list;
	}

	/**
	 * 删除item数据
	 * 
	 * @return
	 */
	public String delItemData() {
		boolean isdel = false;
		String del_homeId = "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < itmes.size(); i++) {
			isdel = itmes.get(i).isCheck();
			del_homeId = itmes.get(i).getHomeworkId();
			if (isdel) {
				itmes.get(i).setDel(true);
				sb.append(del_homeId);
				sb.append(",");
			}
		}
		return sb.toString();
	}

	// public void list_item(View view, final CheckBox ck) {
	// view.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// boolean isChecked = ck.isChecked();
	// if (isChecked) {
	// ck.setChecked(false);
	// // homeWrok_property.setCheck(false);
	// } else {
	// ck.setChecked(true);
	// // homeWrok_property.setCheck(true);
	// }
	//
	// notifyDataSetChanged();
	// }
	// });
	// }

}

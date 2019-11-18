package cn.huaxunchina.cloud.location.app.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.tools.StringUtil;
import com.wheel.LocaWheelAdapter;
import com.wheel.NumericWheelAdapter;
import com.wheel.OnWheelChangedListener;
import com.wheel.WheelView;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 设置-设置闹钟时间对话框
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-31 下午3:29:07
 */
public class LocSetAlarmTimeDialog extends Dialog {
	
	private SetAlarmTimeCallBack attendanceTeacherCallBack;
	@SuppressWarnings("rawtypes")
	private LocaWheelAdapter class_adapter = null;
	private NumericWheelAdapter month_adapter = null;
	private NumericWheelAdapter day_adapter = null;
	private Map<String, String> classdata_map = new HashMap<String, String>();
	private List<String> class_id = new ArrayList<String>();
	private static int theme = R.style.dialog;// 主题
	private boolean scrolling = false;
	private int curYear = 0;
	private int curMonth = 0;
	private int curDay = 0;
	private WheelView classview = null;
	private WheelView monthview = null;
	private WheelView dayview = null;
	private int width, height;// 对话框宽高
	private boolean isClass = false;

	public LocSetAlarmTimeDialog(Context context, List<ClassInfo> classInfo,
			int width, int height, boolean isClass) {
		super(context, theme);
		this.width = width;
		this.height = height;
		this.isClass = isClass;

		// classdata_map

		if (classInfo != null) {
			int size = classInfo.size();
			String[] classdata = new String[size];
			for (int i = 0; i < size; i++) {
				ClassInfo info = classInfo.get(i);
				classdata[i] = StringUtil.substring(info.getClassName(), 5);
				classdata_map.put(info.getClassName(), info.getClassId());
				class_id.add(info.getClassId());
			}
			class_adapter = new LocaWheelAdapter<String>(classdata, size);
		}
	}

	public LocSetAlarmTimeDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.att_teacher_diolog);
		LinearLayout layout = (LinearLayout) findViewById(R.id.attendance_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);
		classview = (WheelView) findViewById(R.id.attendance_class_wh);
		monthview = (WheelView) findViewById(R.id.attendance_month_wh);
		dayview = (WheelView) findViewById(R.id.attendance_day_wh);

		if (isClass) {
			classview.setVisibility(View.GONE);
		}
		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		if (this.curYear == 0 || this.curMonth == 0) {
			curYear = calendar.get(Calendar.YEAR);
			curMonth = calendar.get(Calendar.MONTH) + 1;
			curDay = calendar.get(Calendar.DAY_OF_MONTH);

		}
		// 联动事件
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateDays(curYear, monthview, dayview);
				}
			}
		};
		monthview.addChangingListener(listener);

		// 班级
		if (this.classdata_map != null) {
			classview.setCurrentItem(classdata_map.size());
			classview.setCyclic(false);
			classview.setVisibleItems(5);
			classview.setAdapter(class_adapter);
		}
		// 月份&日期
		month_adapter = new NumericWheelAdapter(1, 12, "%02d");
		monthview.setAdapter(month_adapter);
		monthview.setLabel("月");
		monthview.setCurrentItem(curMonth - 1);
		monthview.setCyclic(false);
		monthview.setVisibleItems(5);
		updateDays(curYear, monthview, dayview);
		dayview.setCyclic(false);
		dayview.setVisibleItems(5);
		dayview.setLabel("日");

		findViewById(R.id.att_inquiry_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (isClass) {
							attendanceTeacherCallBack.onSelectdata(null,
									curYear + "-" + month_adapter.getValues()
											+ "-" + day_adapter.getValues());
						} else {
							// String classId =
							// classdata_map.get(class_adapter.getValues());
							String classId = class_id.get(class_adapter
									.getIndexs());
							attendanceTeacherCallBack.onSelectdata(classId,
									curYear + "-" + month_adapter.getValues()
											+ "-" + day_adapter.getValues());
						}
						dismiss();

					}
				});
	}

	/**
	 * 根据年份和月份来更新日期
	 */
	private void updateDays(int year_num, WheelView month, WheelView day) {
		// 添加大小月月份并将其转换为list,方便之后的判
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);
		// 判断大小月及是否闰年
		if (list_big.contains(String.valueOf(month.getCurrentItem() + 1))) {
			day_adapter = new NumericWheelAdapter(1, 31, "%02d");
		} else if (list_little
				.contains(String.valueOf(month.getCurrentItem() + 1))) {
			day_adapter = new NumericWheelAdapter(1, 30, "%02d");
		} else {
			if ((year_num % 4 == 0 && year_num % 100 != 0)
					|| year_num % 400 == 0)
				day_adapter = new NumericWheelAdapter(1, 29, "%02d");
			else
				day_adapter = new NumericWheelAdapter(1, 28, "%02d");
		}
		dayview.setAdapter(day_adapter);
		dayview.setCurrentItem(curDay - 1);
	}

	public void setAlarmAndTimeCallBack(SetAlarmTimeCallBack callBack) {
		this.attendanceTeacherCallBack = callBack;
	}

	public interface SetAlarmTimeCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(String mclass, String date);
	}
}

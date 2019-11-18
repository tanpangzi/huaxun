package cn.huaxunchina.cloud.app.view;

import com.wheel.LocaWheelAdapter;
import com.wheel.WheelView;
import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class HomeSchoolDialog extends Dialog {
	private HomeSchoolCallBack callBack;
	@SuppressWarnings("rawtypes")
	private LocaWheelAdapter teacherSubject_adapter = null;
	private static int theme = R.style.dialog;// 主题
	private int width, height;// 对话框宽高
	private WheelView teacherSubject_view = null;
	private int select_index;

	public HomeSchoolDialog(Context context, int theme) {
		super(context, theme);
	}

	public HomeSchoolDialog(Context context, int width, int height,
			String[] teacherSubjectName,int select_index) {
		super(context, theme);
		this.width = width;
		this.height = height;
		this.select_index=select_index;
		teacherSubject_adapter = new LocaWheelAdapter<String>(
				teacherSubjectName, teacherSubjectName.length);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_scholol_dialog);
		LinearLayout layout = (LinearLayout) findViewById(R.id.homeschool_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);
		teacherSubject_view = (WheelView) findViewById(R.id.intraction_teacher_subject_index);
		teacherSubject_view.setCyclic(false);
		teacherSubject_view.setVisibleItems(5);
		teacherSubject_view.setAdapter(teacherSubject_adapter);
		teacherSubject_view.setCurrentItem(select_index);
		findViewById(R.id.homeshcool_inquiry_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						callBack.onSelectdata(teacherSubject_adapter
								.getIndexs());
						dismiss();

					}
				});
	}

	public void SetHomeSchoolCallBack(HomeSchoolCallBack callBack) {
		this.callBack = callBack;
	}

	public interface HomeSchoolCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(int teacherSubjectIndex);
	}

}

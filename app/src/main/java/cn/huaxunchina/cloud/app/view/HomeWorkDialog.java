package cn.huaxunchina.cloud.app.view;

import java.util.ArrayList;
import java.util.List;

import com.wheel.LocaWheelAdapter;
import com.wheel.OnWheelChangedListener;
import com.wheel.WheelView;
import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class HomeWorkDialog extends Dialog implements OnWheelChangedListener {
	private HomeWorkCallBack callBack;
	@SuppressWarnings("rawtypes")
	private LocaWheelAdapter mClass_adapter = null;
	@SuppressWarnings("rawtypes")
	private LocaWheelAdapter mCourse_adapter = null;
	private static int theme = R.style.dialog;// 主题
	private int width, height;// 对话框宽高
	private WheelView home_work_class = null;
	private WheelView home_work_course = null;
	private String[] homeWorkClass, homeWorkcourse, classId, homeWorkClassId,newCourseName,courseId;
	private List<String> courseId_list;

	public HomeWorkDialog(Context context, int theme) {
		super(context, theme);
	}

	public HomeWorkDialog(Context context, int width, int height,String[] home_work_class, String[] home_work_course,
			String[] classId, String[] homeWorkClassId,String[]courseId) {
		super(context, theme);
		this.width = width;
		this.height = height;
		this.homeWorkClass = home_work_class;
		this.homeWorkcourse = home_work_course;
		this.homeWorkClassId = homeWorkClassId;
		this.classId = classId;
		this.courseId=courseId;
		mClass_adapter = new LocaWheelAdapter<String>(homeWorkClass,homeWorkClass.length);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sumbit_home_work_dialog);
		LinearLayout layout = (LinearLayout) findViewById(R.id.home_wrok_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);
		home_work_class = (WheelView) findViewById(R.id.home_work_class);
		home_work_course = (WheelView) findViewById(R.id.home_work_course);
		home_work_class.setCyclic(false);
		home_work_course.setCyclic(false);
		home_work_class.setVisibleItems(5);
		home_work_class.setCurrentItem(0);
		home_work_course.setVisibleItems(5);
		home_work_class.setAdapter(mClass_adapter);
		updateHomeWorkCourse(true);
		home_work_class.addChangingListener(this);
		findViewById(R.id.home_inquiry_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						callBack.onSelectdata(mClass_adapter.getIndexs(),mCourse_adapter.getIndexs());
						dismiss();

					}
				});
	}

	public void SetHomeWorkCallBack(HomeWorkCallBack callBack) {
		this.callBack = callBack;
	}

	public interface HomeWorkCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(int classindex, int courseindex);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		updateHomeWorkCourse(false);
	}

	public String[] getNewCourse(){
		return this.newCourseName;
	}
	
	public List<String> getNewCourseId(){
		return this.courseId_list;
	}
	
	/**
	 * 根据班级获取课程科目联动数据
	 */

	private void updateHomeWorkCourse(boolean flag) {
		int homeWorkClassIndex;
		if (flag) {
			homeWorkClassIndex = 0;
		} else {
			homeWorkClassIndex = home_work_class.getCurrentItem();
		}
		
		List<String> course_list = new ArrayList<String>();
		courseId_list= new ArrayList<String>();
		Object[] object = null;
		if (homeWorkClassId != null && homeWorkClassId.length > 0) {
			for (int i = 0; i < homeWorkClassId.length; i++) {
				if (classId[homeWorkClassIndex].equals(homeWorkClassId[i])) {
					course_list.add(homeWorkcourse[i]);
					courseId_list.add(courseId[i]);
				}
			}
		} else {
			course_list.add("无");
		}
		object = course_list.toArray();
		newCourseName = new String[object.length];
		for (int i = 0; i < object.length; i++) {
			newCourseName[i] = object[i].toString();
		}
		home_work_course.setCurrentItem(0);
		mCourse_adapter = new LocaWheelAdapter<String>(newCourseName,newCourseName.length);
		home_work_course.setAdapter(mCourse_adapter);
		flag = true;
	}

}

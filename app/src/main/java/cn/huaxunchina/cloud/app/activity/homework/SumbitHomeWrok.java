package cn.huaxunchina.cloud.app.activity.homework;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.homwork.HomeWrokImpl;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.EduGrade;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokCourse;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokCourseData;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokProperty;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.HomeWorkDialog;
import cn.huaxunchina.cloud.app.view.HomeWorkDialog.HomeWorkCallBack;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 发布家庭作业
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-7-22 下午2:50:48
 */

public class SumbitHomeWrok extends BaseActivity implements HomeWorkCallBack {
	private EditText home_work_edit, home_wrok_tips;
	private ImageView image_jt;
	private TextView sumbit_txt;
	private TextView bottom_course_name;
	private String edit_content, edit_tips;
	private RelativeLayout home_bottom_relative;
	private HomeWorkDialog home_wrokdialog;
	private Window window;
	private String class_id, course_id, grad_id;
	private int width, height;
	private MyBackView back;
	private UserManager manager;// 个人信息对象
	private String[] className, homeWrokCourseName, gradeId, courseId, classId,	homeWorkClassId;
	private boolean visible;
	private String CourseName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sumbit_home_work);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		home_work_edit = (EditText) findViewById(R.id.work_edit);
		home_wrok_tips = (EditText) findViewById(R.id.home_tips);
		home_bottom_relative = (RelativeLayout) findViewById(R.id.home_bottom_relative);
		sumbit_txt = (TextView) findViewById(R.id.sumbit_text);
		bottom_course_name = (TextView) findViewById(R.id.bottom_homework_text);
		image_jt=(ImageView) findViewById(R.id.jt_image);
		super.findView();
	}

	@Override
	public void initView() {
		back.setBackText("编辑作业");
		back.setAddActivty(this);
		loadingDialog = new LoadingDialog(context);
		homeImpl = new HomeWrokImpl();
		manager = LoginManager.getInstance().getUserManager();
		if (manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {
			// studentId = manager.curId;
		} else {
			List<ClassInfo> infos = manager.classInfo;
			className = new String[infos.size()];
			classId = new String[infos.size()];
			gradeId = new String[infos.size()];
			for (int i = 0; i < infos.size(); i++) {
				ClassInfo c_info = infos.get(i);
				EduGrade grade_info = c_info.getEduGrade();
				gradeId[i] = grade_info.getGradeId();
				className[i] = c_info.getClassName();
				classId[i] = c_info.getClassId();
			}

		}
		getTeacherCourse();
		super.initView();
	}

	/**
	 * 获取老师所交的科目
	 */
	private void getTeacherCourse() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog.show();
		homeImpl.getSumbitHomeWrokCourse(SumbitHomeWrok.this,httpUtils, handler);
	}

	@Override
	public void onSelectdata(int classindex,int courseindex) {
		class_id = classId[classindex];
		grad_id = gradeId[classindex];
		String[] newCourseName=home_wrokdialog.getNewCourse();
		CourseName=newCourseName[courseindex];
		course_id = home_wrokdialog.getNewCourseId().get(courseindex);
		bottom_course_name.setText(CourseName+ "   " + className[classindex]);
	}

	/**
	 * 发布家庭作业
	 */
	@SuppressLint("ShowToast")
	private void sumbitHomeWrok(String content, String tips) {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		if (edit_content.equals("")) {
			showToast("请输入提交的作业内容");
			return;
		}
		if (bottom_course_name.getText().toString().equals("选择发布班级与科目")) {
			showToast("选择发布的老师与科目");
			return;
		}
		if (bottom_course_name.getText().toString().equals("暂无班级科目")) {
			showToast("暂无班级或相关科目发布");
			return;
		}
		loadingDialog.show();
		homeImpl.getSumbitHomeWrok(SumbitHomeWrok.this,httpUtils, handler, content, tips, grad_id,course_id,CourseName, class_id);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 获取班级与科目数据
			case HandlerCode.GET_HOMEWROK_CLASS_COURSE_SUCCESS:
				loadingDialog.dismiss();
				HomeWrokCourseData data = (HomeWrokCourseData) msg.obj;
				if (data != null) {
					List<HomeWrokCourse> listdata = data.getData();
					int size = listdata.size();
					homeWrokCourseName = new String[size];
					courseId = new String[size];
					homeWorkClassId = new String[size];
					for (int i = 0; i < size; i++) {
						HomeWrokCourse course = listdata.get(i);
						homeWrokCourseName[i] = course.getCourseName().toString();
						courseId[i] = String.valueOf(course.getCourseId());
						homeWorkClassId[i] = String.valueOf(course.getClassId());
					}
					if(className.length>1||homeWrokCourseName.length>1){
						visible = true;
						image_jt.setVisibility(View.VISIBLE);
					}else{
						//如果只有一个选择，给定默认值
						bottom_course_name.setText(homeWrokCourseName[0]+"  "+className[0]);
						class_id=classId[0];
						CourseName=homeWrokCourseName[0];
						course_id=courseId[0];
						grad_id = gradeId[0];
					}
					
				} else {
					showToast("暂无班级科目数据");
					bottom_course_name.setText("暂无班级科目");
				}
				break;
			case HandlerCode.HANDLER_SUCCESS:
				String homeWorkId=(String) msg.obj;
				loadingDialog.dismiss();
				showToast("发布成功");
				HomeWrokProperty homeWrokData=storeLocalData(homeWorkId);
				Intent intent = new Intent();
				intent.putExtra("storeData", homeWrokData);
				intent.setAction("action.refreshHomeWrokList");
				sendBroadcast(intent); // 发送及时刷新广播
				finish();
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				loadingDialog.dismiss();
				showToast("请求失败");
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				loadingDialog.dismiss();
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				showLoginDialog(SumbitHomeWrok.this);
			    break;
			}

		};

	};
	
	
	/**
	 * 发布家庭作业后保存本地数据
	 */
	public HomeWrokProperty storeLocalData(String homeWrokId) {
		HomeWrokProperty homeWrokData = new HomeWrokProperty();
		homeWrokData.setContent(edit_content);
		homeWrokData.setCourseName(CourseName);
		homeWrokData.setReleaseTime(DateUtil.getCurrentTime());
		homeWrokData.setNowTime(DateUtil.getCurrentTime());
		homeWrokData.setTips(edit_tips);
		homeWrokData.setClassId(class_id);
		homeWrokData.setHomeworkId(homeWrokId);
		homeWrokData.setTeacherid(manager.userId);
		return homeWrokData;
	}

	/**
	 * 获取屏幕适配
	 */
	public void getScreen() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	/**
	 * 获取底部对话框视图
	 */
	public void getDialogView(Dialog dialog) {
		window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置 //Gravity.BOTTOM
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
	}

	@Override
	public void bindView() {
		home_bottom_relative.setOnClickListener(this);
		sumbit_txt.setOnClickListener(this);
		super.bindView();
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_bottom_relative:
			if (visible) {
				getScreen();
				home_wrokdialog = new HomeWorkDialog(context, width, height,className, homeWrokCourseName, classId, homeWorkClassId,courseId);
				getDialogView(home_wrokdialog);
				home_wrokdialog.SetHomeWorkCallBack(this);
				home_wrokdialog.show();
			}
			break;
		case R.id.sumbit_text:
			edit_content = home_work_edit.getText().toString().trim();
			edit_tips = home_wrok_tips.getText().toString().trim();
			sumbitHomeWrok(edit_content, edit_tips);
			break;
		default:
			break;
		}
	}

}

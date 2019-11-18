package cn.huaxunchina.cloud.app.activity.interaction;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.interaction.HomeSchoolImpl;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.interaction.HomeSchoolListData;
import cn.huaxunchina.cloud.app.model.interaction.TeacherAndSubject;
import cn.huaxunchina.cloud.app.model.interaction.TeacherAndSubjectData;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.HomeSchoolDialog;
import cn.huaxunchina.cloud.app.view.HomeSchoolDialog.HomeSchoolCallBack;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.app.view.MyTextWatcher;

/**
 * 家校互动提问界面(家长角色)
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-7 上午11:23:52
 */
public class InteractionQuestions extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener, HomeSchoolCallBack {
	private ImageView jt_image;
	private EditText sumbit_content;
	private EditText sumbit_title;
	private TextView sumbit_ed_txt;
	private TextView sumbit_text_btn;
	private TextView teacher_name;
	private String title, content;
	private CheckBox sumbit_check;
	private int width, height;
	private HomeSchoolDialog dialog;
	private RelativeLayout bottom_layout;
	private Window window;
	private String[] teacher, teacherSubject, teacherId;
	private String techer_id;
	private String isPriavte = "1";
	private String studentId;
	private boolean visible;
	private MyBackView back;
	private ApplicationController applicationController;
	private int select_index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interaction_questions);
		applicationController = (ApplicationController) this.getApplication();
		initView();
		getTeacherAndSubject();
	}

	@Override
	public void initView() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		back = (MyBackView) findViewById(R.id.back);
		back.setBackText("在线提问");
		back.setAddActivty(this);
		sumbit_title = (EditText) findViewById(R.id.homeschool_title);
		sumbit_content = (EditText) findViewById(R.id.homeschool_content);
		sumbit_ed_txt = (TextView) findViewById(R.id.ed_txt);
		sumbit_text_btn = (TextView) findViewById(R.id.sumbit_text);
		sumbit_text_btn.setOnClickListener(this);
		sumbit_check = (CheckBox) findViewById(R.id.setting_msg_ck);
		sumbit_check.setOnCheckedChangeListener(this);
		jt_image = (ImageView) findViewById(R.id.image_jt);
		teacher_name = (TextView) findViewById(R.id.teacher_txt);
		bottom_layout = (RelativeLayout) findViewById(R.id.bottom_layout);
		bottom_layout.setOnClickListener(this);
		// 字数的控制
		MyTextWatcher myTextWatcher = new MyTextWatcher(sumbit_content,sumbit_ed_txt, 150);
		sumbit_content.addTextChangedListener(myTextWatcher);
		sumbit_content.setSelection(sumbit_content.length()); // 将光标移动最后一个字符后面
		myTextWatcher.setLeftCount();
		// 获取当前用户信息
		UserManager manager = LoginManager.getInstance().getUserManager();
		studentId = manager.curId;
		homeSchoolImpl = new HomeSchoolImpl();

		super.initView();
	}

	/**
	 * 获取家校互动的老师与科目
	 */
	private void getTeacherAndSubject() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog = new LoadingDialog(InteractionQuestions.this);
		loadingDialog.show();
		homeSchoolImpl.getTeacherAndSubject(InteractionQuestions.this,applicationController.httpUtils,
				handler, studentId);
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sumbit_text:
			publish();
			break;
		case R.id.bottom_layout:
			if (visible) {
				dialog = new HomeSchoolDialog(context, width, height,teacherSubject, select_index);
				window = dialog.getWindow();
				window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置//
													// //Gravity.BOTTOM
				window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
				dialog.setCancelable(true);
				dialog.setCanceledOnTouchOutside(true);
				dialog.SetHomeSchoolCallBack(this);
				dialog.show();
			}
			break;
		}
		super.onClick(v);
	}

	// 发布互动
	private void publish() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}

		title = sumbit_title.getText().toString().trim();
		content = sumbit_content.getText().toString().trim();
		String teacherName = teacher_name.getText().toString().trim();
		if (title == null || title.equals("")) {
			showToast("请输入主题");
			return;
		}
		if (content == null || content.equals("")) {
			showToast("请输入内容");
			return;
		}
		if (teacherName == null || teacherName.equals("")) {
			showToast("请选择发布对象");
			return;
		}

		// 提交数据
		loadingDialog = new LoadingDialog(InteractionQuestions.this);
		loadingDialog.show();
		homeSchoolImpl.sumbitHomeSchool(InteractionQuestions.this,applicationController.httpUtils,
				handler, techer_id, studentId, title, content, isPriavte);

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				TeacherAndSubject data = (TeacherAndSubject) msg.obj;
				if (data != null) {
					List<TeacherAndSubjectData> list = data.getData();
					if (list != null && list.size() > 0) {
						teacher = new String[list.size()];
						teacherSubject = new String[list.size()];
						teacherId = new String[list.size()];
						for (int i = 0; i < list.size(); i++) {
							teacher[i] = list.get(i).getTeacherName();
							teacherSubject[i] = teacher[i] + "　　"
									+ list.get(i).getSubject();
							teacherId[i] = list.get(i).getTeacherId();
						}

						if (teacherSubject.length > 1) {
							visible = true;
							jt_image.setVisibility(View.VISIBLE);
						} else {
							teacher_name.setText(teacherSubject[0]);
						}
						
						if(teacherId.length==1){
							techer_id=teacherId[0];
						}

					} else {
						showToast("暂无老师及相应科目");

					}
				}
				break;

			case HandlerCode.SUMBIT_HOME_SCHOOL_QUESTIONS_SUCCESS:
				loadingDialog.dismiss();
				showToast("发布成功");
				String themdId = (String) msg.obj;
				if (themdId != null) {
					HomeSchoolListData homeSchoolData = storeLocalData(themdId);
					Intent intent = new Intent();
					intent.putExtra("storeData", homeSchoolData);
					intent.setAction("action.refreshHomeSchoolList");
					sendBroadcast(intent); // 发送及时刷新广播
				}
				finish();
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				showToast("请求失败");
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				showLoginDialog(InteractionQuestions.this);
				break;
			}

		};

	};

	/**
	 * 提交互动主题成功后本地存储数据
	 */
	public HomeSchoolListData storeLocalData(String themdId) {
		HomeSchoolListData localData = new HomeSchoolListData();
		localData.setContent(content);
		localData.setPublishTime(DateUtil.getCurrentTime());
		localData.setThemeId(themdId);
		localData.setTitle(title);
		localData.setIsPrivate(isPriavte);
		return localData;
	}

	// 1：公开 2：私密
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			isPriavte = "2";
		} else {
			isPriavte = "1";
		}

	}

	@Override
	public void onSelectdata(int teacherIndex) {
		select_index = teacherIndex;
		techer_id = teacherId[teacherIndex];
		teacher_name.setText(teacherSubject[teacherIndex]);
	}

}

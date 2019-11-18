package cn.huaxunchina.cloud.app.activity.leave;

import java.util.Calendar;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.leave.LeaveImpl;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.leave.LeaveProperty;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.DataDialog;
import cn.huaxunchina.cloud.app.view.LeaveDialog;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.app.view.MyTextWatcher;
import cn.huaxunchina.cloud.app.view.LeaveDialog.LeaveCallBack;

/**
 * 发布请假申请
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-7 下午7:58:48
 */
public class SumbitLeave extends BaseActivity implements LeaveCallBack {
	private ImageButton sumbit_leave_btn;
	private RelativeLayout leave_type_relative, sta_data_relative,
			end_data_relative;
	private EditText leave_edit_content;
	private TextView leave_person_text;
	private TextView type_text;
	private TextView sta_text;
	private TextView end_text;
	private TextView leave_txt;
	private int width, height;
	private LeaveDialog diolog;
	private DataDialog DataDialog;
	private Window window;
	private String[] leave_type = { "事假", "病假" };
	private String leaveType;// 请假类型
	private int typeindex = 0;
	private String leave_content;
	private UserManager manager;// 个人信息对象
	private String studentName = "";
	private String studentId;
	private String sta_date;
	private String end_date;
	private String sta_date_replace;
	private String end_date_replace;
	private double hour;
	private MyBackView back;
	private LeaveProperty leaveProperty;
	private ApplicationController applicationController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sumbit_leave);
		findView();
		initView();
		bindView();
	}

	@SuppressLint("CutPasteId")
	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		sumbit_leave_btn = (ImageButton) findViewById(R.id.sumbit_leave_btn);
		leave_edit_content = (EditText) findViewById(R.id.leave_edit);
		leave_person_text = (TextView) findViewById(R.id.leave_person_text);
		type_text = (TextView) findViewById(R.id.type_text);
		sta_text = (TextView) findViewById(R.id.sta_time);
		end_text = (TextView) findViewById(R.id.end_time_txt);
		leave_txt = (TextView) findViewById(R.id.leave_txt);
		leave_type_relative = (RelativeLayout) findViewById(R.id.leave_type_relative);
		sta_data_relative = (RelativeLayout) findViewById(R.id.sta_date_relative);
		end_data_relative = (RelativeLayout) findViewById(R.id.end_date_relative);
		super.findView();

	}

	@Override
	public void initView() {
		back.setBackText("请假管理");
		back.setAddActivty(this);
		applicationController = (ApplicationController) getApplication();
		leaveImpl = new LeaveImpl();
		loadingDialog = new LoadingDialog(this);
		manager = LoginManager.getInstance().getUserManager();
		studentId = manager.curId;
		List<Students> list = manager.students;
		if (list != null) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				Students info = list.get(i);
				if (info.getStudentId().equals(studentId)) {
					studentName = info.getName();
				}
			}
		}
		leave_person_text.setText(studentName);
		MyTextWatcher myTextWatcher = new MyTextWatcher(leave_edit_content,leave_txt, 30);
		leave_edit_content.addTextChangedListener(myTextWatcher);
		leave_edit_content.setSelection(leave_edit_content.length()); // 将光标移动最后一个字符后面
		myTextWatcher.setLeftCount();
		super.initView();
	}

	@Override
	public void bindView() {
		leave_type_relative.setOnClickListener(this);
		sumbit_leave_btn.setOnClickListener(this);
		sta_data_relative.setOnClickListener(this);
		end_data_relative.setOnClickListener(this);
		super.bindView();
	}

	@Override
	public void onSelectdata(String typeindex) {
		this.typeindex = Integer.valueOf(typeindex);
		leaveType = leave_type[Integer.valueOf(typeindex)];
		type_text.setText(leaveType);
	}

	/**
	 * 获取底部对话框视图
	 */
	public void getDialogView(Dialog dialog) {
		window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置 //Gravity.BOTTOM
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		diolog.setCancelable(true);
		diolog.setCanceledOnTouchOutside(true);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				showToast("发布成功");
				leaveProperty = storeLocalData();
				Intent intent = new Intent();
				intent.putExtra("storeData", leaveProperty);
				intent.setAction("action.refreshLeaveList");
				sendBroadcast(intent); // 发送及时刷新广播
				finish();
				break;
			case HandlerCode.HANDLER_NET:
				showToast("请求失败");
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
				showLoginDialog(SumbitLeave.this);
				break;
			}

		};

	};

	/**
	 * 提交申请单成功后本地存储数据
	 */
	public LeaveProperty storeLocalData() {
		LeaveProperty leaveProperty = new LeaveProperty();
		leaveProperty.setApproveStatus("W");
		leaveProperty.setAskLeaveId(studentId);
		leaveProperty.setAskTime(DateUtil.getCurrentTime());
		leaveProperty.setStartTime(sta_date_replace);
		leaveProperty.setEndTime(end_date_replace);
		leaveProperty.setLeaveType((typeindex + 1));
		leaveProperty.setReason(leave_content);
		leaveProperty.setStudentName(studentName);
		return leaveProperty;
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

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leave_type_relative:
			getScreen();
			diolog = new LeaveDialog(context, width, height, leave_type);
			getDialogView(diolog);
			diolog.SetLeaveCallBack(this);
			diolog.show();
			break;
		// 提交请假申请
		case R.id.sumbit_leave_btn:
			leave_content = leave_edit_content.getText().toString().trim();
			sta_date = sta_text.getText().toString();
			end_date = end_text.getText().toString();
			if (sta_date.equals("开始时间") || end_date.equals("结束时间")) {
				showToast("请选择请假时间");
				return;
			}
			
			sta_date_replace = sta_date.replace("时", ":").replace("分", "");
			end_date_replace = end_date.replace("时", ":").replace("分", "");
			long date = DateUtil.dateDiff(sta_date_replace, end_date_replace);
			if (date > 0) {
				hour = DateUtil.dateDiffHour(sta_date_replace, end_date_replace);
				getSumbitLeave();
			} else {
				showToast("结束或开始日期格式不正确");
			}
			break;
		// 选择开始日期时间
		case R.id.sta_date_relative:
			getLeaveData(sta_text);
			break;
		// 选择结束日期时间
		case R.id.end_date_relative:
			getLeaveData(end_text);
			break;
		default:
			break;
		}
	}

	/**
	 * 提交请假申请
	 */
	@SuppressLint("ShowToast")
	private void getSumbitLeave() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}

		if (leave_content.equals("")) {	
			showToast("请输入请假内容");
			return;
		}
		loadingDialog.show();
		leaveImpl.SumbitLeave(SumbitLeave.this,applicationController.httpUtils, handler, "1",
				String.valueOf((typeindex + 1)), leave_content,
				sta_date_replace, end_date_replace, studentId,
				String.valueOf(hour));

	}

	/**
	 * 获取请假时间值
	 */
	public void getLeaveData(final TextView data_text) {
		DataDialog = new DataDialog.Builder(context,false,false,false)
				.setPositiveButton("确认", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Calendar c = DataDialog.getSetCalendar();
						data_text.setText(getFormatDate(c));
						DataDialog.dismiss();
					}
				}).create();
		window = DataDialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		DataDialog.setCancelable(true);
		DataDialog.setCanceledOnTouchOutside(true);
		DataDialog.show();
	}

}

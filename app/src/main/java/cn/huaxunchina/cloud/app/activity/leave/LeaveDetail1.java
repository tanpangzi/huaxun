package cn.huaxunchina.cloud.app.activity.leave;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.leave.LeaveImpl;
import cn.huaxunchina.cloud.app.imp.leave.LeaveInterface;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.leave.LeaveProperty;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.TimeUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 请假管理详情界面（老师角色）
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-8 上午11:17:48
 */
public class LeaveDetail1 extends BaseActivity implements OnClickListener {
	private TextView leave_type, leave_reason, sta_leave_time,end_leave_time ,lea_state,
			lea_sumbit, leave_name;
	private TextView agree_txt, back_txt;
	private RelativeLayout re_bottom;
	private UserManager manager;// 个人信息对象
	private String askLeaveId; // 请假人id
	private ApplicationController applicationController;
	private MyBackView back;
	private String sendStatus;
	private String id;
	private LeaveInterface leaveImpl;
	protected LoadingDialog loadingDialog; // 加载进度条对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave_detail);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		loadingDialog = new LoadingDialog(this);
		back = (MyBackView) findViewById(R.id.back);
		leave_type = (TextView) findViewById(R.id.leave_type);
		leave_reason = (TextView) findViewById(R.id.leave_reason);
		sta_leave_time = (TextView) findViewById(R.id.sta_leave_time);
		end_leave_time=(TextView) findViewById(R.id.end_leave_time);
		lea_sumbit = (TextView) findViewById(R.id.leave_sumbit_time);
		lea_state = (TextView) findViewById(R.id.leave_state);
		leave_name = (TextView) findViewById(R.id.leave_name);
		re_bottom = (RelativeLayout) findViewById(R.id.re_bottom);
		agree_txt = (TextView) findViewById(R.id.yesbtn);
		back_txt = (TextView) findViewById(R.id.backbtn);
		super.findView();
	}
	

	@Override
	public void initView() {
		back.setBackText("请假详情");
		back.setAddActivty(this);
		leaveImpl = new LeaveImpl();
		applicationController = (ApplicationController) getApplication();
		intent = getIntent();
		initData();
	}
	
	
	
	public void initData(){
		Handler init_handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case HandlerCode.HANDLER_SUCCESS:
					if(loadingDialog!=null){
						loadingDialog.dismiss();}
					LeaveProperty info = (LeaveProperty)msg.obj;
					leave_type.setText(info.getLeaveTypeStr());
					leave_reason.setText(replaceNull(info.getReason()));
					lea_state.setText(info.getApproveStatusStr());
					sta_leave_time.setText(TimeUtil.formatDateT(info.getStartTime()));
					end_leave_time.setText(TimeUtil.formatDateT(info.getEndTime()));
					leave_name.setText(info.getStudentName());
					lea_sumbit.setText(DateUtil.getDateDetail(info.getAskTime()));
					askLeaveId = info.getAskLeaveId();
					manager = LoginManager.getInstance().getUserManager();
					if (manager.curRoleId.equals(String.valueOf(RolesCode.HEAD_TEACHER))&& info.isStatus()) {
						re_bottom.setVisibility(View.VISIBLE);
					}
					break;
				case HandlerCode.HANDLER_NET:
					if(loadingDialog!=null){
						loadingDialog.dismiss();}
					Utils.netWorkToast(); // 请求网络异常
					break;
				case HandlerCode.HANDLER_ERROR:// 失败
					showToast("请求异常");
					if(loadingDialog!=null){
					loadingDialog.dismiss();}
					break;
	          case HandlerCode.HANDLER_TIME_OUT:// 超时
					Utils.netWorkToast();
					if(loadingDialog!=null){
					loadingDialog.dismiss();}
					break;
				case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
					if(loadingDialog!=null){
					loadingDialog.dismiss();
					}
					break;
				}
			}
		};
		id = intent.getStringExtra("id");
		//id = "1437";
		loadingDialog .show();
		leaveImpl.getgetLeaveInfo(LeaveDetail1.this,httpUtils, init_handler, id);
		
	}
	

	@Override
	public void bindView() {
		agree_txt.setOnClickListener(this);
		back_txt.setOnClickListener(this);
		super.bindView();
	}

	/**
	 * 提交审核状态
	 */

	public void getCheckState() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog = new LoadingDialog(this);
		loadingDialog.show();
		leaveImpl.getCheckState(LeaveDetail1.this,applicationController.httpUtils,handler, askLeaveId, sendStatus);

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				showToast("审核完成");
				finish();
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				showToast("请求异常");
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				break;
          case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				showLoginDialog(LeaveDetail1.this);
			    break;
			}
			
		};

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.yesbtn: // 同意
			sendStatus="Y";
			getCheckState();
			break;
		case R.id.backbtn: // 退回
			sendStatus="N";
			getCheckState();
			break;
		}
	}

}

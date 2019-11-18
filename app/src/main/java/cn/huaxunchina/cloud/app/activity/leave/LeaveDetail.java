package cn.huaxunchina.cloud.app.activity.leave;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.leave.LeaveImpl;
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
public class LeaveDetail extends BaseActivity implements OnClickListener {
	private TextView leave_type, leave_reason, sta_leave_time,end_leave_time ,lea_state,
			lea_sumbit, leave_name;
	private TextView agree_txt, back_txt;
	private LeaveProperty items;
	private RelativeLayout re_bottom;
	private UserManager manager;// 个人信息对象
	private String askLeaveId; // 请假人id
	private String leaveState;
	private ApplicationController applicationController;
	private int position;
	private MyBackView back;
	private String sendStatus;

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
	
	//LEAVEBYID

	@Override
	public void initView() {
		back.setBackText("请假详情");
		back.setAddActivty(this);
		leaveImpl = new LeaveImpl();
		applicationController = (ApplicationController) getApplication();
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (intent != null) {
			items = (LeaveProperty) bundle.getSerializable("LeaveProperty");
			leaveState = bundle.getString("leaveState");
			leave_type.setText(bundle.getString("leaveType"));
			leave_reason.setText(replaceNull(items.getReason()));
			lea_state.setText(leaveState);
			sta_leave_time.setText(TimeUtil.formatDateT(items.getStartTime()));
			end_leave_time.setText(TimeUtil.formatDateT(items.getEndTime()));
			leave_name.setText(items.getStudentName());
			lea_sumbit.setText(DateUtil.getDateDetail(items.getAskTime()));
			askLeaveId = items.getAskLeaveId();
			position=intent.getIntExtra("position", 0);
			
		}
		manager = LoginManager.getInstance().getUserManager();
		if (manager.curRoleId.equals(String.valueOf(RolesCode.HEAD_TEACHER))&& leaveState.equals("未审批")) {
			re_bottom.setVisibility(View.VISIBLE);
		}
		super.initView();
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
		leaveImpl.getCheckState(LeaveDetail.this,applicationController.httpUtils,handler, askLeaveId, sendStatus);

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
				Intent intent = new Intent();
				items.setAskLeaveId(position+"");
				items.setApproveStatus(sendStatus);
				intent.putExtra("storeData", items);
				setResult(ResultCode.REFRESH_CHECK_STATE,intent);	
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
				showLoginDialog(LeaveDetail.this);
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
		super.onClick(v);
	}

}

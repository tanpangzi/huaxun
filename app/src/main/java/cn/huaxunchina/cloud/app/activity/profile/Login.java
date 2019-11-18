package cn.huaxunchina.cloud.app.activity.profile;

import java.util.List;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.activity.Home;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.LoginResponse;
import cn.huaxunchina.cloud.app.imp.LoginModel;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Role;
import cn.huaxunchina.cloud.app.model.ServerData;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.ServerData.ServerInfo;
import cn.huaxunchina.cloud.app.model.response.LoginData;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyLoginEdText;
import cn.huaxunchina.cloud.app.view.ServerDialog;
import cn.huaxunchina.cloud.app.view.ServerDialog.ServerCallBack;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

/**
 * 登陆
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月14日 上午9:31:03
 * 
 */
public class Login extends BaseActivity implements OnClickListener,ServerCallBack {
	private MyLoginEdText edPassword = null;// 密码
	private MyLoginEdText edAccount = null;// 账号
	private LoginResponse response;
	private LoadingDialog loadingDialog;
	private ApplicationController applicationController;
	private int select_index = 0;
	private PreferencesHelper pre;
	private String NAME = "name";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		initView();
		applicationController = (ApplicationController) this.getApplication();
		response = new LoginModel();
	}

	@Override
	public void initView() {
		super.initView();
		pre = new PreferencesHelper(Login.this, "Login");
		edPassword = (MyLoginEdText) findViewById(R.id.login_password_ed);
		edAccount = (MyLoginEdText) findViewById(R.id.login_account_ed);
		findViewById(R.id.login_forget_password_tv).setOnClickListener(this);
		findViewById(R.id.login_server_list).setOnClickListener(this);
		findViewById(R.id.login_btn).setOnClickListener(this);
		// 获取本地保存数据
		UserManager umanager = LoginManager.getInstance().getUserManager();
		String password = umanager.password;
		String loginPhone = pre.getValue(NAME);
		edAccount.setText(loginPhone);
		edPassword.setText(password);
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_forget_password_tv:// 忘记密码
			String url = UserUtil.getRequestUrl();
			if (TextUtils.isEmpty(url)) {
				showToast("请选择地区");
				return;
			}else{
			goActivity(ForgetPassword.class);
			}
			
			goActivity(ForgetPassword.class);
			break;
		case R.id.login_btn:// 登陆
			login();
			// doActivity(Home.class);
			break;
		case R.id.login_server_list:// 选择服务器列表
			doServerList();
			break;
		}

	}

	@SuppressWarnings("static-access")
	private void doServerList() {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog = new LoadingDialog(Login.this);
		loadingDialog.show();
		response.doServerList(applicationController.httpUtils, handler);

	}
	

	@SuppressWarnings("static-access")
	private void login() {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		String loginPhone = edAccount.getText().toString().trim();
		String password = edPassword.getText().toString().trim();
		if (TextUtils.isEmpty(loginPhone)) {
			 //设置晃动  
			edAccount.setShakeAnimation(); 
			showToast("请输入账号");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			edPassword.setShakeAnimation(); 
			showToast("请输入密码");
			return;
		}
		String url = UserUtil.getRequestUrl();
		// showToast(url);
		if (TextUtils.isEmpty(url)) {
			showToast("请选择地区");
			return;
		}
		loadingDialog = new LoadingDialog(Login.this);
		loadingDialog.show();
		response.doLogin(loginPhone, password, applicationController.httpUtils,handler);

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:// 登陆成功
				loadingDialog.dismiss();
				LoginData data = (LoginData) msg.obj;
				// showToast(data.getUserinfo().getUserName());
				// 判断角色 角色&小孩的个数 如果为1 直接登陆不用切换角色
				int roles_count = 0;
				int student_count = 0;

				Role[] roles = data.getUserinfo().getRoles();
				if (roles != null) {
					roles_count = roles.length;
				}

				List<Students> students = data.getStudents();
				if (students != null) {
					student_count = students.size();
				}

				if (roles_count == 1 && student_count == 1 || roles_count == 0
						&& student_count == 1 || roles_count == 1
						&& student_count == 0) {// 跳过角色切换
					// 保持基本信息 当前角色 当前用户名 学校
					PreferencesHelper pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
					LoginManager manager = LoginManager.getInstance();
					cn.huaxunchina.cloud.app.model.UserInfo info = data.getUserinfo();
					manager.setUserName(pre, info.getUserName());
					manager.setCurRoleId(pre, info.getRole().getRoleId() + "");
					manager.setCurRoleName(pre, info.getRole().getRoleName());
					manager.setSchoolName(pre, info.getSchool().getSchoolName());
					manager.setRoleCount(pre, "1");
					// 当前id
					if (info.getRole().getRoleId() == RolesCode.PARENTS) {
						Students sinfo = data.getStudents().get(0);
						manager.setCurStudentId(pre, sinfo.getStudentId());
						manager.setImei(sinfo.getImeiNum());
						manager.setCurClassId(pre, sinfo.getClassId());
						manager.setCurId(pre, sinfo.getStudentId());
					} else if(data.getTeacher()!=null){
						manager.setCurTeacherId(pre, data.getTeacher().getTeacherID());
						manager.setCurId(pre, data.getTeacher().getTeacherID());
						manager.setImei("");
						List<ClassInfo> classlist = data.getClassList();
						if(classlist!=null&&classlist.size()>0){
						manager.setCurClassId(pre, classlist.get(0).getClassId());
						}
					}

					// 登陆状态
					ApplicationController.setIsLogin();
					goActivity(Home.class);
					finish();

				} else if (roles_count > 1 || student_count > 1) {// 切换角色
					Intent intent = new Intent(getBaseContext(),ChangeRole.class);
					intent.putExtra("roles_type", "1");
					Bundle bundle = new Bundle();
					bundle.putSerializable("loginData", data);
					intent.putExtras(bundle);
					startActivityForResult(intent, ResultCode.LOGIN_CODE);
					// startActivity(intent);
					// finish();
					// LOGIN_CODE
				}
				String account = edAccount.getText().toString();
				pre.setValue(NAME, account);
				break;
			case HandlerCode.HANDLER_FAIL:// 登陆失败
				loadingDialog.dismiss();
				String msg_str = (String) msg.obj;
				showToast(msg_str);
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				loadingDialog.dismiss();
				showToast("连接超时");
				break;
			case HandlerCode.HANDLER_ERROR:// 错误
				loadingDialog.dismiss();
				break;
			case HandlerCode.SERVER_SUCCESS:// 服务器请求成功
				loadingDialog.dismiss();
				ServerData serverData = (ServerData) msg.obj;
				if (serverData != null) {
					DisplayMetrics dm = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(dm);
					int width = dm.widthPixels;
					int height = dm.heightPixels;
					if (!UserUtil.getDiologPostion().equals("")) {
						select_index = Integer.valueOf(UserUtil
								.getDiologPostion());
					}
					ServerDialog diolog = new ServerDialog(Login.this,
							serverData.getData(), width, height, select_index);
					Window window = diolog.getWindow();
					window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
					window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
					diolog.setServerCallBack(Login.this);
					diolog.setCancelable(true);
					diolog.setCanceledOnTouchOutside(true);
					diolog.show();
				}
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == ResultCode.LOGIN_CODE) {
			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onServerUrl(ServerInfo info, int index) {
		UserUtil.setRequestUrl(info.getDomain());
		UserUtil.setMsgUrl(info.getMsgServerUrl());
		UserUtil.setRequestId(info.getId());
		select_index = index;
		UserUtil.setDiologPostion(String.valueOf(select_index));

	};

}

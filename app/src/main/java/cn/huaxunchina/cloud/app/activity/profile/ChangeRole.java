package cn.huaxunchina.cloud.app.activity.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.activity.Home;
import cn.huaxunchina.cloud.app.adapter.ChangeRoleAdapter;
import cn.huaxunchina.cloud.app.adapter.ChangeRoleAdapter.ChangeInfo;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.LoginModel;
import cn.huaxunchina.cloud.app.imp.LoginResponse;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.response.LoginData;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.model.ClientAppIdUtil;

/**
 * 角色切换
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月6日 上午9:48:47
 * 
 */
public class ChangeRole extends BaseActivity implements ChangeRoleAdapter.ChangeItemClickListener {
	private ListView lvRolename = null;
	private ChangeRoleAdapter adapter = null;
	private LoadingDialog loadingDialog;
	private String roles_type = "";
	private ApplicationController applicationController;
	private MyBackView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changerole_activity);
		// initBar("选择角色");
		initView();
	}

	@Override
	public void initView() {
		super.initView();
		lvRolename = (ListView) findViewById(R.id.changerole_listview);
		back=(MyBackView) findViewById(R.id.back);
		LoginData loginData = (LoginData) this.getIntent().getSerializableExtra("loginData");
		roles_type = this.getIntent().getStringExtra("roles_type");
		adapter = new ChangeRoleAdapter(loginData,this,roles_type);
		adapter.setChangeItemClickListener(this);
		adapter.setHandler(handler);
		lvRolename.setAdapter(adapter);
		back.setBackText("选择角色");
		back.setAddActivty(this);
		
//		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}
//		});
//		TextView tvTitle = (TextView) findViewById(R.id.title);
//		tvTitle.setText("选择角色");

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				UserUtil.setChangRolePostion(adapter.getSelectItem());
				
				//上次lbsClientAppid
				 
				
				 
				if(LoginManager.getInstance().getUserManager().curRoleId.equals(
						String.valueOf(RolesCode.PARENTS))){
					ClientAppIdUtil.setLbsClientAppId(LoginManager.getInstance().getUserManager().clientAppId);
				}
				
				if (roles_type.equals("2")) {
					Intent intent = new Intent(ChangeRole.this, Home.class);
					setResult(ResultCode.PROFILE_CODE, intent);
				} else {
					Intent intent = new Intent(ChangeRole.this, Login.class);
					setResult(ResultCode.LOGIN_CODE, intent);
					// startActivity(intent);
				}
				finish();
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_TIME_OUT:
				loadingDialog.dismiss();
				break;
			case HandlerCode.HANDLER_ERROR:
				loadingDialog.dismiss();
				break;
			}
		}
	};

	@SuppressWarnings("static-access")
	@Override
	public void onItemClickListener(ChangeInfo role) {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog = new LoadingDialog(this);
		loadingDialog.show();
		LoginResponse response = new LoginModel();
		applicationController = (ApplicationController) getApplication();
		
		response.changeRole(role, applicationController.httpUtils, handler);
		 
	}

}

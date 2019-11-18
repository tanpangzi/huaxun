package cn.huaxunchina.cloud.app.activity.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.LoginModel;
import cn.huaxunchina.cloud.app.imp.LoginResponse;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 修改密码
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月16日 上午10:48:57
 * 
 */
public class ModifyPassword extends BaseActivity implements OnClickListener {

	private EditText edPassword;
	private EditText edNewPassword;
	private EditText edNewtooPassword;
	private LoginResponse response;
	private LoadingDialog loadingDialog;
	private MyBackView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_pwd_activity);
		initBartoTitle("提交");
		initView();
		loadingDialog = new LoadingDialog(this);
		response = new LoginModel();
	}

	@Override
	public void initView() {
		super.initView();
		edPassword = (EditText) findViewById(R.id.modify_password_ed);
		edNewPassword = (EditText) findViewById(R.id.modify_new_password_ed);
		edNewtooPassword = (EditText) findViewById(R.id.modify_newtwo_password_ed);
		findViewById(R.id.submit_txt).setOnClickListener(this);
		back=(MyBackView) findViewById(R.id.back);
		back.setBackText("修改密码");
		back.setAddActivty(this);
		// .matches("[a-zA-Z0-9]*")
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_txt:// 提交
			changPwd();
			break;
		}

	}

	private void changPwd() {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}

		UserManager manager = LoginManager.getInstance().getUserManager();
		String loginPhone = manager.loginPhone;
		String oldpassword = edPassword.getText().toString().trim();
		String newPassword = edNewPassword.getText().toString().trim();
		String newtooPassword = edNewtooPassword.getText().toString().trim();
		if (TextUtils.isEmpty(oldpassword)) {
			showToast("旧密码不能为空!");
			return;
		}
		int oldpassword_size = oldpassword.length();
		if (oldpassword_size < 8) {
			showToast("旧密码长度不能小于8!");
			return;
		}
		if (!oldpassword.matches("[a-zA-Z0-9]*")) {
			showToast("旧密码格式错误!");
			return;
		}
		if (TextUtils.isEmpty(newPassword)) {
			showToast("新密码不能为空!");
			return;
		}
		int newPassword_size = newPassword.length();
		if (newPassword_size < 8) {
			showToast("新密码长度不能小于8!");
			return;
		}
		if (!newPassword.matches("[a-zA-Z0-9]*")) {
			showToast("新密码格式错误!");
			return;
		}
		if (TextUtils.isEmpty(newtooPassword)) {
			showToast("确定密码不能为空!");
			return;
		}
		if (!newPassword.equals(newtooPassword)) {
			showToast("两次密码不相同!");
			return;
		}
		loadingDialog.show();
		response.changPwd(loginPhone, oldpassword, newPassword, httpUtils,
				handler);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:// 修改成功
				loadingDialog.dismiss();
				showToast("修改成功!");
				break;
			case HandlerCode.HANDLER_FAIL:// 修改失败
				loadingDialog.dismiss();
				String fail_msg = (String) msg.obj;
				showToast(fail_msg);
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				loadingDialog.dismiss();
				break;
			case HandlerCode.HANDLER_ERROR://
				loadingDialog.dismiss();
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				showLoginDialog(ModifyPassword.this);
			    break;
			}

		}
	};
}

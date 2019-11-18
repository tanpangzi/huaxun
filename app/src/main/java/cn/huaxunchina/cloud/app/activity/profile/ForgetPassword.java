package cn.huaxunchina.cloud.app.activity.profile;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.LoginModel;
import cn.huaxunchina.cloud.app.imp.LoginResponse;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
/**
 * 忘记密码--短信验证
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月14日 上午10:54:39 
 */
public class ForgetPassword extends BaseActivity implements OnClickListener{
	private MyBackView back;
	private Button btVerifyCode = null;
	private EditText edAccount = null;
	private EditText edVerifyCode = null;
	private EditText edNewpassword = null;
	private EditText edTwopassword = null;
	private String verifyCode  = null;
	private int timeOut=300;
	private LoginResponse response;
	public  static int FORGET_VERIFYCODE = 1;
	public  static int FORGET_SUCCESS = 2;
	private LoadingDialog loadingDialog;
	private boolean isVerifyCode = true;
	private int VERIFY_SUCCESS = 1;
	
	//线程池的初始化
	private ScheduledFuture<?> future=null;
	private ScheduledExecutorService execuService= Executors.newScheduledThreadPool(1);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_pwd_activity);
		initBartoTitle("提交");
		response = new LoginModel();
		initView();
	}
	
	@Override
	public void initView() {
		super.initView();
		edAccount=(EditText)findViewById(R.id.forget_account_ed);
		edVerifyCode=(EditText)findViewById(R.id.forget_verifycode_ed);
		edNewpassword=(EditText)findViewById(R.id.forget_new_password_ed);
		edTwopassword=(EditText)findViewById(R.id.forget_two_password_ed);
		btVerifyCode=(Button)findViewById(R.id.forget_verifycode_btn);
		back=(MyBackView) findViewById(R.id.back);
		back.setBackText("短信验证");
		back.setAddActivty(this);
		btVerifyCode.setOnClickListener(this);
		//verifyCode=VerifyCode.getVerifyCode();//验证码的初始化
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.forget_verifycode_btn://获取验证码
			if (!Utils.isNetworkConn()) {
				Utils.netWorkMessage(handler);
				return;
			}
			 
			String loginPhones =edAccount.getText().toString().trim();
			if(TextUtils.isEmpty(loginPhones)){
			showToast("请输入账号");
			return;
			}
			if(!isVerifyCode)return;
			loadingDialog = new LoadingDialog(ForgetPassword.this);
			loadingDialog .show();
			//String loginPhones = "13128993854";
			response.getSmsCode(loginPhones, httpUtils, handler);
			break;
		case R.id.submit_txt://提交
			//goActivity(ResettingPassword.class);
			forgetPassword();
			break;
		}
		
	}
	
	
	private void forgetPassword(){
		
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		String loginPhone =edAccount.getText().toString().trim();
		if(TextUtils.isEmpty(loginPhone)){
		showToast("请输入账号!");
		return;
		}
		if(VERIFY_SUCCESS==1){
			showToast("请先获取验证码!");
		return;	
		}
		String validCode = edVerifyCode.getText().toString().trim();
		if(TextUtils.isEmpty(validCode)){
		showToast("请输入验证码!");
		return;
		}
		String password = edNewpassword.getText().toString().trim();
		if(TextUtils.isEmpty(password)){
		showToast("请设置密码!");
		return;	
		}
		int password_size = password.length();
		if(password_size<8){
		showToast("密码长度不能小于8!");
		return;	
		}
		if(!password.matches("[a-zA-Z0-9]*")){
		showToast("新密码格式错误!");
		return;
		}
		String two_password = edTwopassword.getText().toString().trim();
		if(TextUtils.isEmpty(two_password)){
		showToast("请确认密码!");
		return;
		}
		if(!two_password.equals(password)){
		showToast("两次密码不一致!");
		return;	
		}
		loadingDialog = new LoadingDialog(ForgetPassword.this);
		loadingDialog .show();
		response.getNewPasswd(loginPhone, validCode, password, httpUtils, handler);
	}
	//验证码的计时器
	private void timer(){
		future=execuService.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				timeOut--;
				handler.sendEmptyMessage(TIME_CODE);
			}
		}, 0, 1000, TimeUnit.MILLISECONDS);
	}
	
	final static int  TIME_CODE = 60;
	
   //停止计时
   private void stopTimer(){
    	if(null!=future){
    		future.cancel(true);
			future=null;
    	}
    }
    //activity在销毁时做一些处理 
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopTimer();
		execuService.shutdown();
	}

	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TIME_CODE://计时器
				if(timeOut>0){
				btVerifyCode.setText("("+timeOut+")");	
				}else{
				isVerifyCode=true;
				stopTimer();
				timeOut=300;
				btVerifyCode.setText("获取验证码");	
				}
				break;
			case HandlerCode.FORGET_VERIFYCODE://获取验证码成功
				isVerifyCode=false;
				VERIFY_SUCCESS=2;
				loadingDialog.dismiss();
				timer();
				break;
			case HandlerCode.FORGET_SUCCESS://密码设置成功
				loadingDialog.dismiss();
				showToast("设置成功");
				finish();
				break;
			case HandlerCode.HANDLER_ERROR://请求错误
				loadingDialog.dismiss();
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				break;
			}
		};
	};
	
}

package cn.huaxunchina.cloud.app.activity;



import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.getui.GetuiUtil;
import cn.huaxunchina.cloud.app.getui.GetuiUtil.GetuidoActivity;
import cn.huaxunchina.cloud.app.imp.LoginModel;
import cn.huaxunchina.cloud.app.imp.LoginResponse;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.upgrade.VersionService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
/**
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月22日 下午6:12:05 
 *
 */
public class Loading extends BaseActivity implements GetuidoActivity{
	private boolean isFirstIn = false;
	private ApplicationController applicationController;
	private String categoryId ="";
	private String id = "";
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_activity);
		applicationController = (ApplicationController) this.getApplication();
		initView();
		
		Intent intent=new Intent(Loading.this, VersionService.class); 
		intent.putExtra("type", "1");
		startService(intent);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
		categoryId = bundle.getString("categoryId");
		id = bundle.getString("id");
		//System.out.println("Loading推送id:===="+id);
		}
	}
     
	
	@Override
	public void initView() {
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
		categoryId = bundle.getString("categoryId");
		id = bundle.getString("id");
		//System.out.println("Loading推送id:===="+id);
		}
		
		// sp = new PreferencesHelper(context, "");
		// 使用SharedPreferences来记录程序的使用次数
		SharedPreferences preferences = getSharedPreferences(Constant.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		//System.out.println(" ===isFirstIn==="+isFirstIn);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			 //接受到通知公告的时候如果没有登陆就自动登陆,如果登陆就就直接跳到对应的模块里面
			String islogin = applicationController.getIsLogin();
			if(islogin==null){
				//System.out.println("还没有登陆======"+id);
			 handler.postDelayed(new LoginRunnable(), Constant.SPLASH_DELAY_MILLIS);	
			}else if(categoryId!=null&&!categoryId.equals("")&&id!=null){
				//个推逻辑
				///System.out.println("Loading 开始跳======"+id);
				GetuiUtil.getInstance().setGetuidoActivity(this);
				GetuiUtil.getInstance().categoryType(categoryId,id, Loading.this);
			}else{
				//System.out.println("登陆去了======"+id);
				UserManager manager = LoginManager.getInstance().getUserManager();
				String password = manager.password;
				String roleId = manager.curRoleId;
				if(password.equals("")&&roleId.equals("")){
				intent = new Intent(Loading.this, Login.class);
				startActivity(intent);
				finish();
				}else{
				finish();
				}
			}
			
			/*else{
				intent = new Intent(Loading.this, Login.class);
				startActivity(intent);
				finish();
			}*/
			
		} else {
			//==本地阅读保存标示的初始化数据
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent guide_intent = new Intent(Loading.this,GuideActivity.class);
					startActivity(guide_intent);
					finish();
				}
			}, Constant.SPLASH_DELAY_MILLIS);
		}
		super.initView();
	}

	
	@Override
	public void doActivity(Intent intent) {
		startActivity(intent);
		finish();
	}
	
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				intent = new Intent(Loading.this, Home.class);
				intent.putExtra("categoryId", categoryId);
				intent.putExtra("id", id);
				startActivity(intent);
				finish();
				break;
			case HandlerCode.HANDLER_TIME_OUT:
				showToast("连接超时!");
				intent = new Intent(Loading.this, Login.class);
				startActivity(intent);
				finish();
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast();
				intent = new Intent(Loading.this, Login.class);
				startActivity(intent);
				finish();
				break;
			case HandlerCode.HANDLER_ERROR:
				String message = (String)msg.obj;
				showToast(message);
				intent = new Intent(Loading.this, Login.class);
				startActivity(intent);
				finish();
				break;
			}
		}

	};
	
	
	 
	

	class LoginRunnable implements Runnable {
		@Override
		public void run() {
			UserManager manager = LoginManager.getInstance().getUserManager();
			String password = manager.password;
			String loginPhone = manager.loginPhone;
			String roleId = manager.curRoleId;
			String url = UserUtil.getRequestUrl();
			// 自动登陆
			if (password != null && !password.equals("") 
					&& loginPhone != null&& !loginPhone.equals("") 
					&& roleId != null&& !roleId.equals("")
					&&url != null&& !url.equals("")) {
				doLogin(roleId, loginPhone, password);
				//System.out.println("doLogin:---------------->");
			} else {
				intent = new Intent(Loading.this, Login.class);
				startActivity(intent);
				finish();
			}

		}
	}

	private void doLogin(String roleId, String loginPhone, String password) {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		LoginResponse loginModel = new LoginModel();
		loginModel.doLogin(roleId, loginPhone, password,applicationController.httpUtils, handler);
	}
	
	
	 

	 

}

package cn.huaxunchina.cloud.app.activity;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.loopj.android.http.AsyncHttpClient;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.imp.homwork.HomeWrokImpl;
import cn.huaxunchina.cloud.app.imp.interaction.HomeSchoolImpl;
import cn.huaxunchina.cloud.app.imp.leave.LeaveImpl;
import cn.huaxunchina.cloud.app.imp.notice.NoticeImpl;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.tools.ActivityForResultUtil;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.R;


public class BaseActivity extends FragmentActivity implements Initialization,
		OnClickListener {
	protected LeaveImpl leaveImpl;
	protected HomeWrokImpl homeImpl;
	protected NoticeImpl noticeImpl;
	protected HomeSchoolImpl homeSchoolImpl;
	protected Context context;
	protected Intent intent;
	protected AsyncHttpClient httpUtils; // http请求工具类
	protected ApplicationController application;
	protected String url;
	protected ProgressDialog progressDialog;
	protected LoadingDialog loadingDialog; // 加载进度条对话框
	protected PreferencesHelper sp;
	protected PageInfo pageInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		application = (ApplicationController) getApplication();
		httpUtils = application.httpUtils;
		overridePendingTransition(R.anim.fragment_enter_in,R.anim.fragment_enter_out);
		// 从左向右推出动画效果
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ApplicationController.addActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		ApplicationController.removeActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ApplicationController.removeActivity(this);
	}

	@Override
	public void initView() {
	}

	@Override
	public void findView() {

	}

	@Override
	public void bindView() {

	}

	
	/**
	 * bar数据初始化
	 * 
	 * @param title
	 */
	protected void initBar(String title) {
//		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}
//		});
		TextView tvTitle = (TextView) findViewById(R.id.title);
		tvTitle.setText(title);
	}

	protected void initBartoTitle(String rstr) {
//		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}
//		});
//		TextView lTitle = (TextView) findViewById(R.id.title);
//		lTitle.setText(lstr);
		TextView rTitle = (TextView) findViewById(R.id.submit_txt);
		rTitle.setOnClickListener(this);
		rTitle.setText(rstr);
	}

	@SuppressLint("ShowToast")
	protected void showToast(String msg) {
		Toast.makeText(getBaseContext(), msg, 0).show();
	}

	protected void goActivity(Class<?> activity) {
		intent = new Intent();
		intent.setClass(this, activity);
		startActivity(intent);
		overridePendingTransition(R.anim.fragment_enter_in,R.anim.fragment_enter_out);
//		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.new_push_left_in,R.anim.new_push_left_out);// 从右向左推出动画效果
		}
		return false;
	}

	/**
	 * finish当前界面（返回）
	 */
	protected void back() {
		finish();
		overridePendingTransition(R.anim.new_push_left_in,
				R.anim.new_push_left_out);// 从右向左推出动画效果
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			progressDialog = new ProgressDialog(context);
			progressDialog.setIndeterminate(true);
			return progressDialog;
		case 1:

			break;
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	protected void setContext(Context context) {
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	public void showLoading(Context context, int id) {
		setContext(context);
		showDialog(id);
		progressDialog.setContentView(R.layout.loading_dialog);
	}

	@Override
	public void onClick(View arg0) {
	}

	/**
	 * 替换返回空字符数据
	 * 
	 * @param text
	 * @return
	 */
	protected String replaceNull(String text) {
		if ("".equals(text) || null == text || "null".equals(text)) {
			return "无";
		} else {
			return text;
		}
	}

	// 请求Gallery程序
	protected void doPickPhotoFromGallery() {
		try {
			final Intent intent = getPhotoPickIntent();
			startActivityForResult(intent,
					ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION);
		} catch (ActivityNotFoundException e) {

		}
	}

	protected static String getFormatDate(Calendar c) {
		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		// 设置日期的显示
		Calendar calendar = c;
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		return year + "-" + decimal.format(month + 1) + "-"
				+ decimal.format(day) + " " + decimal.format(hour) + "时"
				+ decimal.format(min) + "分";
	}

	// 封装请求Gallery的intent
	private Intent getPhotoPickIntent() {
		intent = new Intent(Intent.ACTION_PICK, null);
		intent.setAction(Intent.ACTION_GET_CONTENT);
		// intent.putExtra("crop", "true");
		// intent.putExtra("aspectX", 1);
		// intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", 80);
		// intent.putExtra("outputY", 80);
		// 图片格式
		// intent.putExtra("outputFormat", "JPEG");
		// intent.putExtra("noFaceDetection", true);
		// intent.putExtra("return-data", true);
		return intent;
	}

	public void showLoginDialog(final Context mcontext) {
		
		Toast.makeText(mcontext, "已断开,请重新登录", Toast.LENGTH_SHORT).show();
		 //ApplicationController.exit();
		 //intent = new Intent();
		 //intent.setClass(mcontext, Login.class);
		 //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK); 
		 //mcontext.startActivity(intent); 
		 
		
		 /*SingleDialog dialog = new SingleDialog(mcontext, R.style.dialog);
		 dialog.setTitle("提示"); 
		 dialog.setContent("会话超时,请重新登陆");
		 dialog.setButtonText("重新登陆"); 
		 dialog.setButtonClickListener(new OnClickListener() {
		 @SuppressLint("InlinedApi")
		 @Override public void onClick(View v) { 
		 ApplicationController.exit();
		 intent = new Intent();
		 intent.setClass(mcontext, Login.class);
		 //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK); 
		 mcontext.startActivity(intent); 
		  
		 }
		 }); dialog.show();*/
		 
	}

}

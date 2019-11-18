package cn.huaxunchina.cloud.app.activity.contacts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ContactsModel;
import cn.huaxunchina.cloud.app.imp.ContactsResponse;
import cn.huaxunchina.cloud.app.model.SMSHistory;
import cn.huaxunchina.cloud.app.model.SendSmsData;
import cn.huaxunchina.cloud.app.tools.TimeUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 历史短信详情界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-3-11 上午11:13:32
 */
public class HosMessageDetail extends BaseActivity {
	private MyBackView back;
	private TextView transmit;
	private ApplicationController applicationController;
	private FrameLayout framLayout;
	private TextView tv_desc_short;
	private TextView tv_desc_long;
	private boolean isShowShortText = true;
	private boolean isShowButton = false;
	private boolean isInit = false;
	private ImageButton imageButton;
	private TextView contentTxt;
	private String data;
	private String sendSerial;
	private TextView sendTimeTxt;
	private TextView numTxt;
	private SendSmsData snedData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hos_mess_detail);
		initData();
	}

	/**
	 * 初始化数据
	 */
	@SuppressLint("CutPasteId")
	private void initData() {
		back = (MyBackView) findViewById(R.id.back);
		transmit = (TextView) findViewById(R.id.transmit);
		framLayout = (FrameLayout) findViewById(R.id.fl_desc);
		tv_desc_short = (TextView) findViewById(R.id.tv_desc_short);
		tv_desc_long = (TextView) findViewById(R.id.tv_desc_long);
		imageButton = (ImageButton) findViewById(R.id.group_image);
		contentTxt = (TextView) findViewById(R.id.send_content);
		sendTimeTxt = (TextView) findViewById(R.id.send_time_txt);
		numTxt = (TextView) findViewById(R.id.tv_num);
		back.setBackText("短信详情");
		back.setAddActivty(this);
		transmit.setOnClickListener(this);
		framLayout.setOnClickListener(this);
		tv_desc_short.setOnClickListener(this);
		imageButton.setOnClickListener(this);
		tv_desc_short.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_desc_long.setMovementMethod(ScrollingMovementMethod.getInstance());
		applicationController = (ApplicationController) getApplication();
		intent = this.getIntent();
		if (intent != null) {
			data = intent.getStringExtra("data");
			snedData = (SendSmsData) intent.getSerializableExtra("sendData");
			sendSerial = intent.getStringExtra("sendSerial");
		}
		loadingDialog = new LoadingDialog(this);
		if (data.equals("today")) {
			data = "today";
		} else {
			data = "history";
		}
		getHosMessageDetail();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (loadingDialog != null) {
				loadingDialog.dismiss();
			}
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				SMSHistory data = (SMSHistory) msg.obj;
				if (data != null) {
					String content = "收件人："+ data.getNames().toString().replace("[", "").replace("]", "");
					tv_desc_short.setText(content);
					tv_desc_long.setText(content);
					contentTxt.setText(data.getContent());
					sendTimeTxt.setText(TimeUtil.formatDateT(data.getCreateTime()));
					numTxt.setText(data.getNum() + "人");
				}
				handFramLayout();
				break;

			case HandlerCode.HANDLER_ERROR:
				showToast("请求失败");
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			default:
				break;
			}

		};
	};

	/**
	 * 处理收件人布局监听
	 */
	public void handFramLayout(){
		ViewTreeObserver vto = framLayout.getViewTreeObserver();
		vto.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (isInit) {
					return true;
				}
				if (mesureDescription(tv_desc_short, tv_desc_long)) {
					tv_desc_long.setMaxLines(4);
				}
				isInit = true;
				return false;
			}
		});
		
	}
	
	/**
	 * 获取历史详情数据
	 */
	@SuppressWarnings("static-access")
	private void getHosMessageDetail() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog.show();
		ContactsResponse response = new ContactsModel();
		response.getHosMessageDetail(data, sendSerial,applicationController.httpUtils, handler);
	}

	/**
	 * 计算描述信息是否过长
	 */
	public boolean mesureDescription(TextView shortView, TextView longView) {
		final int shortHeight = shortView.getHeight();
		final int longHeight = longView.getHeight();
		if (longHeight > shortHeight) {
			shortView.setVisibility(View.VISIBLE);
			longView.setVisibility(View.GONE);
			return true;
		}
		shortView.setVisibility(View.GONE);
		longView.setVisibility(View.VISIBLE);
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.group_image:
			tv_desc_short.setVisibility(View.VISIBLE);
			tv_desc_long.setVisibility(View.GONE);
			imageButton.setVisibility(View.GONE);
			numTxt.setVisibility(View.GONE);
			break;
		case R.id.tv_desc_short:
			if (isShowShortText) {
				tv_desc_short.setVisibility(View.GONE);
				tv_desc_long.setVisibility(View.VISIBLE);
				isShowButton = true;
			} else {
				isShowButton = false;
				tv_desc_short.setVisibility(View.VISIBLE);
				tv_desc_long.setVisibility(View.GONE);
			}
			toogleMoreButton(isShowButton);
			isShowShortText = !isShowShortText;
			break;	
		case R.id.fl_desc:
			if (isShowShortText) {
				tv_desc_short.setVisibility(View.GONE);
				tv_desc_long.setVisibility(View.VISIBLE);
				isShowButton = true;
			} else {
				isShowButton = false;
				tv_desc_short.setVisibility(View.VISIBLE);
				tv_desc_long.setVisibility(View.GONE);
			}
			toogleMoreButton(isShowButton);
			isShowShortText = !isShowShortText;
			break;
		case R.id.transmit:
			if (snedData != null) {
				intent = new Intent(context, SmsContent.class);
				String content = contentTxt.getText().toString().trim();
				snedData.setContent(content);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", snedData);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	/**
	 * 切换选择箭头的隐藏与显示
	 */
	private void toogleMoreButton(boolean btn) {
		if (btn) {
			imageButton.setVisibility(View.VISIBLE);
			numTxt.setVisibility(View.VISIBLE);
		} else {
			imageButton.setVisibility(View.GONE);
			numTxt.setVisibility(View.GONE);
		}
	}
}

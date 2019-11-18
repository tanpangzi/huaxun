package cn.huaxunchina.cloud.app.activity.notice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.notice.NoticeImpl;
import cn.huaxunchina.cloud.app.model.notice.NoticeDetailContent;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadData;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.HtmlUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 通知通告详情界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-7-19 下午4:34:42
 */
public class NoticeDetail extends BaseActivity {
	private NoticeDetailContent noticeDetailData;
	private String detailContent;
	private WebView mWebView;
	private String noticeId;
	private MyBackView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_detail);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		mWebView = (WebView) findViewById(R.id.notice_detail_webView);
		super.findView();
	}

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled"}) 
	@Override
	public void initView() {
		back.setBackText("通知详情");
		back.setAddActivty(this);
		intent = getIntent();
		if (intent != null) {
			noticeId = intent.getStringExtra("id");
		}
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
		mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
		mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
		WebSettings webSetting = mWebView.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
		webSetting.setCacheMode(WebSettings.LOAD_DEFAULT| WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSetting.setNeedInitialFocus(false);
		mWebView.setBackgroundColor(Color.TRANSPARENT);// 设置其背景为透明
		//mWebView.setBackgroundColor(R.color.loc_activity_bg);
		//mWebView.setBackgroundColor(R.color.loc_activity_bg);
		
		progressDialog = new ProgressDialog(context);
		loadingDialog = new LoadingDialog(context);
		noticeImpl = new NoticeImpl();
		getNoticeDetail();
		super.initView();
	}

	/**
	 * 获取通知通告详情
	 */
	private void getNoticeDetail() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog.show();
		noticeImpl.getNoticeDetail(NoticeDetail.this,httpUtils, handler, noticeId);

	}

	@Override
	public void bindView() {
		super.bindView();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				noticeDetailData = (NoticeDetailContent) msg.obj;
				if (noticeDetailData != null) {
					detailContent = noticeDetailData.getContent().toString();
					TextUtils.htmlEncode(detailContent);
					Html.fromHtml(detailContent);
					String title = noticeDetailData.getTitle()+"";
					String time = DateUtil.getDateDetail(noticeDetailData.getPublishTime());
					String source ="发布人:"+ noticeDetailData.getPublisher();
					mWebView.loadDataWithBaseURL(null, HtmlUtil.getHtml(title,time,source,detailContent), "text/html", "utf-8", null); 
				}
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				loadingDialog.dismiss();
				showToast("请求失败");
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				loadingDialog.dismiss();
				break;
			default:
				break;
			}

		};

	};

}

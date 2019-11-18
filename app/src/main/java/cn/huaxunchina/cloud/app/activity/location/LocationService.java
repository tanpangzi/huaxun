package cn.huaxunchina.cloud.app.activity.location;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.view.NavigationBar;

/**
 * 定位服务界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-7-22 下午4:46:53
 */
public class LocationService extends BaseActivity {
	private WebView mWebView;
	private boolean mFlag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_service);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		mWebView = (WebView) findViewById(R.id.webview);
//		mNavigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
		super.findView();
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void initView() {
//		mNavigationBar.setLeftButtonText(getResources()
//				.getString(R.string.back));
//		mNavigationBar.setTitle(getResources().getString(
//				R.string.location_service));
		// String url =
		// "http://m.sunegps.com/moblogin.aspx?cp="+application.userInfo.imeiNum+"&l=0";
		// String imei = application.userInfo.imeiNum;
		String imei = "";
		if (null == imei || "".equals(imei) || "null".equals(imei)) {
			// showLongToast("您的账号暂无学生定位服务");
		} else {
			// String url = application.userInfo.gpsUrl + "cp=" + imei + "&l=0";
			// String url = "http://guishu.showji.com/search.htm?m=13900008888";
			// synCookies(context, url);
			mWebView.getSettings().setJavaScriptEnabled(true); // 设置WebView支持JavaScript
			mWebView.getSettings().setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
			mWebView.getSettings().setSupportZoom(true); // 设置支持缩放
			mWebView.getSettings().setBuiltInZoomControls(true);// 显示缩放控件
			mWebView.getSettings().setLoadWithOverviewMode(true);
			mWebView.requestFocus();
			// mWebView.loadUrl(url); // 加载assert目录下的文件
		}
		super.initView();
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	@Override
	public void bindView() {
		// webview加载进度
		mWebView.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {
				// if (mFlag) {
				// showLoading(mContext, 0);
				// mFlag = false;
				// }
				// if (progress == 100) {
				// removeLoading(0);
				// }
			}

		});
		// 设置webview能响应http请求
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();
				super.onReceivedSslError(view, handler, error);
			}
		});
		super.bindView();
	}

}

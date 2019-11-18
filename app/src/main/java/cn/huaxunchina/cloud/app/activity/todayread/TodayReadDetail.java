package cn.huaxunchina.cloud.app.activity.todayread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageButton;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.read.TodayReadImpl;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadData;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.HtmlUtil;
import cn.huaxunchina.cloud.app.tools.PatternUtil;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.onekeyshare.OnekeyShare;
import cn.sharesdk.framework.ShareSDK;

/**
 * 每日一读详情界面
 * 
 * @author zoupeng@huaxunchina.cn 2014-8-7 上午9:23:14
 */
public class TodayReadDetail extends BaseActivity implements OnClickListener {
	private MyBackView back;
	private ImageButton share_btn;
	private ImageButton collect_btn;
	public static String TEST_IMAGE="";
	private String readId;
	private boolean isCollect;
	private int position;
	private String type;
	private WebView today_detail_web;
	private TodayReadData todaydetail;
	private String content, share_content;
	private ApplicationController applicationController;
	private TodayReadImpl cmoImplement;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.today_read_detail);
		applicationController = (ApplicationController) getApplication();
		initView();
		getTodayReadDetail();

	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	public void initView() {
		ShareSDK.initSDK(this);
		back = (MyBackView) findViewById(R.id.back);
		share_btn = (ImageButton) findViewById(R.id.share_btn);
		share_btn.setOnClickListener(this);
		collect_btn = (ImageButton) findViewById(R.id.collect_btn);
		collect_btn.setOnClickListener(this);
		today_detail_web = (WebView) findViewById(R.id.today_detail_web);
		back.setBackText("每日一读详情");
		back.setAddActivty(this);
		cmoImplement = new TodayReadImpl();
		today_detail_web.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		today_detail_web.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
//		today_detail_web.setScrollBarStyle(0);
		today_detail_web.setHorizontalScrollBarEnabled(false);//水平不显示
		today_detail_web.setVerticalScrollBarEnabled(false); //垂直不显示
		WebSettings webSetting = today_detail_web.getSettings();
		webSetting.setJavaScriptEnabled(true);
//		today_detail_web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webSetting.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
		webSetting.setCacheMode(WebSettings.LOAD_DEFAULT| WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSetting.setNeedInitialFocus(false);
		today_detail_web.setBackgroundColor(Color.TRANSPARENT);// 设置其背景为透明
		intent = getIntent();
		if (intent != null) {
			readId = intent.getStringExtra("id");
			position = intent.getIntExtra("position", 0);
			type = intent.getStringExtra("type");
		}

	}

	/**
	 * 获取每日一读详情
	 */
	private void getTodayReadDetail() {
		getNetConn();
		loadingDialog = new LoadingDialog(context);
		loadingDialog.show();
		cmoImplement.getToadyDetail(TodayReadDetail.this,applicationController.httpUtils, handler,
				readId);

	}

	/**
	 * 无网络请求
	 */
	public void getNetConn() {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 开启线程加载本地图片
		File file = new File(Constant.SHARE_IMAGE);
		if (!file.exists()) {
			new Thread() {
				public void run() {
					initImagePath();
				}
			}.start();
		}
	}
	 

	// 初始化图片路径
	private void initImagePath() {
	    try {
	    	File dirFile = new File(Constant.PAXY_PATH);
			 if (!dirFile.exists()) {
			 dirFile.mkdirs();
			 }
			File file = new File(dirFile,"share_image.jpg");
			file.createNewFile();
			Bitmap pic = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
			FileOutputStream fos = new FileOutputStream(file);
			pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String title = "";

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:// 成功
				loadingDialog.dismiss();
				todaydetail = (TodayReadData) msg.obj;
				if (todaydetail != null) {
					share_btn.setVisibility(View.VISIBLE);
					collect_btn.setVisibility(View.VISIBLE);
					today_detail_web.setVisibility(View.VISIBLE);
					content = todaydetail.getContent();
					TextUtils.htmlEncode(content);
					Html.fromHtml(content);
					title = todaydetail.getTitle()+"";
					String time = DateUtil.getDateDetail(todaydetail.getCreateTime());
					String source =todaydetail.getOrigin()+"";
					today_detail_web.loadDataWithBaseURL(null, HtmlUtil.getHtml(title,time,source,content), "text/html", "utf-8", null); 
					isCollect = todaydetail.isCollect();
					if (isCollect) {
						collect_btn.setBackgroundResource(R.drawable.collect_btn);
					} else {
						collect_btn.setBackgroundResource(R.drawable.collect_btn1);
					}
				}
				break;
			case HandlerCode.COLLECT_SUCCESS: // 收藏成功
				if(loadingDialog!=null){
				loadingDialog.dismiss();}
				showToast("收藏成功");
				isCollect = true;
				collect_btn.setBackgroundResource(R.drawable.collect2);
				break;
			case HandlerCode.DEL_COLLECT_SUCCESS: // 取消收藏成功
				if(loadingDialog!=null){
				loadingDialog.dismiss();}
				showToast("取消收藏");
				isCollect = false;
				collect_btn.setBackgroundResource(R.drawable.collect3);
				break;
			case HandlerCode.HANDLER_NET:
				showToast("请求失败");
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				if(loadingDialog!=null){
				loadingDialog.dismiss();}
				showToast("请求失败");
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				if(loadingDialog!=null){
				loadingDialog.dismiss();}
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if(loadingDialog!=null){
				loadingDialog.dismiss();}
				showLoginDialog(TodayReadDetail.this);
			    break;
			}

		};

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share_btn: // 分享
			// String share_url = getFilterData(content);
			share_content = PatternUtil.filterHtml(content.toString());
			showShare(false, null, this, readId, title, PatternUtil.filterStr(share_content));
			break;
		case R.id.collect_btn: // 收藏
			if (isCollect) {
				delCollectRead();
			} else {
				collectDailyRead();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 收藏文章
	 */

	private void collectDailyRead() {
		getNetConn();
		loadingDialog.show();
		cmoImplement.collectTodayRead(TodayReadDetail.this,applicationController.httpUtils, handler,
				Integer.valueOf(readId));

	}

	/**
	 * 取消收藏
	 */

	public void delCollectRead() {
		getNetConn();
		loadingDialog.show();
		cmoImplement.delCollectTodayRead(TodayReadDetail.this,applicationController.httpUtils,
				handler, Integer.valueOf(readId));

	}

	public void showShare(boolean silent, String platform,
			final Context context, String id, String title, String share_content) {
		final OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher,
				context.getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(title);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(share_content);
		// if (share_image_url == null) {
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath(Constant.SHARE_IMAGE);
		// } else {
		// oks.setImageUrl("http://ln.wlyedu.com/images/image/20140527/20140527105717_4465.jpg");
		// }
		// url仅在微信（包括好友和朋友圈）中使用

		oks.setUrl(UserUtil.getRequestUrl()+ "interaction/noPowerBrowse.do?readId=" + id);
		oks.setSilent(silent);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		// 启动分享GUI
		oks.show(context);
	}

	@Override
	protected void onDestroy() {
		ShareSDK.stopSDK(this); // 结束分享至第三方平台的统计并释放资源
		if (type.equals("2")) {
			if (isCollect == false) {
				Intent intent = new Intent();
				todaydetail.setReadId(position + "");
				todaydetail.setCollect(isCollect);
				intent.putExtra("storeData", todaydetail);
				intent.setAction("action.refreshTodayList");
				sendBroadcast(intent); // 发送及时刷新广播
			}
		}
		super.onDestroy();
	}
	
	
	 
	 
}

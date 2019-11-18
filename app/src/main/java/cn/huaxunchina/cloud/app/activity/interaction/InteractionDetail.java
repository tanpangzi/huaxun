package cn.huaxunchina.cloud.app.activity.interaction;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.CommentAdpter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.interaction.HomeSchoolImpl;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.interaction.CommentContent;
import cn.huaxunchina.cloud.app.model.interaction.HomeSchoolDetailData;
import cn.huaxunchina.cloud.app.tools.HtmlUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 家校互动详情界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-7 上午11:23:52
 */
public class InteractionDetail extends BaseActivity implements OnClickListener {
	private MyBackView back;
	private EditText bottom_ed;
	private ImageButton ed_btn;
	private String comment_content, content;
	private HomeSchoolDetailData data;
	private List<CommentContent> replyList;
	private CommentAdpter mAdpter;
	private String themeId;
	private String commentType = "0";
	private int ComNumsize = 0;// 评论数
	private ListView commentList;
	private TextView comment;
	private TextView detail_title;
	private TextView detail_time;
	private WebView detail_web;
	private ApplicationController applicationController;
	private String publishTime;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interaction_detail);
		applicationController = (ApplicationController) this.getApplication();
		initView();
		getHomeSchoolDetail();
	}

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@Override
	public void initView() {
		loadingDialog = new LoadingDialog(context);
		homeSchoolImpl = new HomeSchoolImpl();
		themeId = getIntent().getStringExtra("id");
		String role = LoginManager.getInstance().getUserManager().curRoleId;
		if (role.equals(String.valueOf(RolesCode.PARENTS))) {
			commentType = "0";
		} else {
			commentType = "1";
		}

		back = (MyBackView) findViewById(R.id.back);
		back.setBackText("互动详情");
		back.setAddActivty(this);
		commentList = (ListView) findViewById(R.id.listview);
		bottom_ed = (EditText) findViewById(R.id.detail_bottom_ed);
		ed_btn = (ImageButton) findViewById(R.id.bottom_imabtn);
		ed_btn.setOnClickListener(this);
		super.initView();
	}

	/**
	 * 获取家校互动详情
	 */
	public void getHomeSchoolDetail() {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog.show();
		homeSchoolImpl.getHomeSchoolDetail(InteractionDetail.this,applicationController.httpUtils,handler, themeId);
	}

	/**
	 * 发布评论
	 */
	private void sumbitComment(String content) {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog.show();
		homeSchoolImpl.sumbitCommentList(InteractionDetail.this,applicationController.httpUtils,handler, themeId, content);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("deprecation")
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				data = (HomeSchoolDetailData) msg.obj;
				if (data != null) {
					title = data.getTitle().toString();
					publishTime = data.getPublishTime().toString();
					content = data.getContent();
					replyList = data.getReplyList();
					TextUtils.htmlEncode(content);
					Html.fromHtml(content);
					if (replyList != null) {
						ComNumsize = replyList.size();
					}
					getHallListHeader();
					if (mAdpter == null) {
						mAdpter = new CommentAdpter(context, replyList);
						commentList.setAdapter(mAdpter);
						setListViewHeightBasedOnChildren(commentList);
						if (mAdpter.getCount() == 0) {
							commentList.setBackgroundDrawable(null);
						}
					}
				}
				break;
			case HandlerCode.SUMBIT_HOME_SCHOOL_COMMENT_SUCCESS:// 发布评论成功
				loadingDialog.dismiss();
				showToast("评论发布成功!");
				bottom_ed.setText("");
				CommentContent info = new CommentContent();
				info.setContent(comment_content);
				info.setType(commentType);
				info.setReplyTime("");
				mAdpter.addItems(info);
				ComNumsize = ComNumsize + 1;
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				showToast("请求失败");
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				showLoginDialog(InteractionDetail.this);
				break;
			}

		};

	};
	/**
	 * 获取列表头布局
	 */

	private void getHallListHeader() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.includ_interaction_detail, null);
		commentList.addHeaderView(view);
		comment = (TextView) findViewById(R.id.comment);
		detail_title = (TextView) findViewById(R.id.home_school_detail_title);
		detail_time = (TextView) findViewById(R.id.home_school_detal_time);
		detail_web = (WebView) findViewById(R.id.detail_webview);
		setHeadText(title, publishTime, ("评论： " + String.valueOf(ComNumsize)));
		setWebView(content);

	}

	/**
	 * 设置模块标题
	 * 		
	 * @param text
	 */
	public void setHeadText(String titleText, String timeText,String commentText) {
		comment.setText(commentText);
		detail_title.setText(titleText);
		detail_time.setText(timeText);

	}

	@SuppressLint("SetJavaScriptEnabled")
	public void setWebView(String content) {
		detail_web.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		detail_web.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
		detail_web.addJavascriptInterface(this, "javatojs");
		detail_web.setHorizontalScrollBarEnabled(false);// 水平不显示
		detail_web.setVerticalScrollBarEnabled(false); // 垂直不显示
		// 在内容显示内部显示
		WebSettings webSetting = detail_web.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setNeedInitialFocus(false);
		webSetting.setSupportZoom(true);
		detail_web.setBackgroundColor(Color.TRANSPARENT);// 设置其背景为透明
		webSetting.setCacheMode(WebSettings.LOAD_DEFAULT| WebSettings.LOAD_CACHE_ELSE_NETWORK);
		detail_web.loadDataWithBaseURL(null, HtmlUtil.getHtml(content),"text/html", "utf-8", null);
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bottom_imabtn:
			comment_content = bottom_ed.getText().toString().trim();
			if (comment_content == null || comment_content.equals("")) {
				showToast("请输入评论内容");
				return;
			}
			sumbitComment(comment_content);
			break;
		}
		super.onClick(v);
	}

	/**
	 * 重新计算listView高度
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);

	}

}

package cn.huaxunchina.cloud.app.activity;

import java.util.List;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.loopj.android.http.RequestParams;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.adapter.NewsAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.imp.MyResponseHandler;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.NewsInfo;
import cn.huaxunchina.cloud.app.model.NewsInfoData;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

/**
 * 首页资讯
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月10日 下午6:06:20
 * 
 */
public class NewsFragment extends BaseFragment implements
		PullToRefreshExpandableListView.OnRefreshListener2<ExpandableListView> {
	private Activity activity;
	private PullToRefreshExpandableListView refreshList; // ExpandableListView
	private ExpandableListView elNews;
	private LoadingDialog loadingDialog;
	private ApplicationController applicationController;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 初始化数据请求 initdata
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		loadingDialog = new LoadingDialog(activity);
		applicationController = (ApplicationController) activity.getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_news_layout, container,false);
		TextView title = (TextView) view.findViewById(R.id.bar_title);
		title.setText("首页");
		initView(view);
		return view;
	}

	private void initView(View view) {
		refreshList = (PullToRefreshExpandableListView) view.findViewById(R.id.news_listview);
		refreshList.setShowIndicator(false);
		refreshList.setOnRefreshListener(this);
		elNews = refreshList.getRefreshableView();
		elNews.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
				return true;
			}
		});
		initData(false);
	}
	
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.FORGET_SUCCESS:// 成功
				loadingDialog.dismiss();
				NewsInfoData newsInfoData = (NewsInfoData) msg.obj;
				List<NewsInfo> data = newsInfoData.getData();
				if (data != null) {
					NewsAdapter adapter = new NewsAdapter(activity, data);
					elNews.setAdapter(adapter);
					int groupCount = elNews.getCount();
					for (int i = 0; i < groupCount; i++) {
						elNews.expandGroup(i);
					}
				}
				refreshList.onRefreshComplete();
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				refreshList.onRefreshComplete();
				break;
			case HandlerCode.HANDLER_ERROR:// 错误
				loadingDialog.dismiss();
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				loadingDialog.dismiss();
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if(loadingDialog!=null){
					loadingDialog.dismiss();
					refreshList.onRefreshComplete();
				}
				showLoginDialog(activity);
			    break;
			}
		};
	};

	private void initData(boolean isRefresh) {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		
		if(!isRefresh){
			loadingDialog.show();
		}
		UserManager umanager = LoginManager.getInstance().getUserManager();
		String studentId = null;
		if (umanager.curRoleId.equals(String
				.valueOf(Constant.RolesCode.PARENTS))) {
			studentId = umanager.curStudentId;
		}
		// =================================
		// studentId="1528";

		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("studentId", studentId);
		String url = UserUtil.getRequestUrl() + Constant.RequestCode.NEWS;
		applicationController.httpUtils.post(url, params,new MyResponseHandler(url,params,applicationController.httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {// 判断是否成功返回
						NewsInfoData newsInfoData = GsonUtils.getSingleBean(json,NewsInfoData.class);
						message.obj = newsInfoData;
						message.what = HandlerCode.FORGET_SUCCESS;
						handler.sendMessage(message);
					} else {
						message.what = HandlerCode.HANDLER_ERROR;// 错误
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
					message.what = HandlerCode.HANDLER_ERROR;// 错误
					handler.sendMessage(message);
				}
			}}));
 
	 
		
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
		initData(true);
	}

	@Override
	public void onPullUpToRefresh(
			PullToRefreshBase<ExpandableListView> refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}

}

package cn.huaxunchina.cloud.app.activity.interaction;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.HomeSchoolActivitiesAdpter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.imp.interaction.HomeSchoolImpl;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.interaction.HomeSchoolListData;
import cn.huaxunchina.cloud.app.model.interaction.HomeSchoolListItem;
import cn.huaxunchina.cloud.app.tools.HandSucessAdpter;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 家校互动列表界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-7 上午10:30:48
 */
public class HomeSchoolActivities extends BaseActivity implements
		OnClickListener, OnItemClickListener, OnRefreshListener<ListView> {
	private PullToRefreshListView refreshListView;
	private ImageButton question;
	private HomeSchoolActivitiesAdpter mAdpter;
	private UserManager manager;// 个人信息对象
	private String studentId = null;
	private String teacherId = null;
	private int CurPage;
	private int pull_start = 0;
	private MyBackView back;
	private ApplicationController applicationController;
	private MyListAdpterUtil adpterUtil;
	private HandSucessAdpter handAdpter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_school_actvities);
		applicationController = (ApplicationController) this.getApplication();
		initView();
		
	}

	@Override
	public void initView() {
		back = (MyBackView) findViewById(R.id.back);
		back.setBackText("家校互动");
		back.setAddActivty(this);
		loadingDialog = new LoadingDialog(context);
		refreshListView = (PullToRefreshListView) findViewById(R.id.home_school_list);
		refreshListView.setOnItemClickListener(this);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setMode(Mode.BOTH);
		question = (ImageButton) findViewById(R.id.questions);
		question.setOnClickListener(this);
		homeSchoolImpl = new HomeSchoolImpl();
		pageInfo = new PageInfo();
		manager = LoginManager.getInstance().getUserManager();
		// 判断当前角色
		if (manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {
			question.setVisibility(View.VISIBLE);
			studentId = manager.curId;
		} else {
			teacherId = manager.curId;
		}
		handLeaveManageUtil();
		getHomeSchoolList(false);
		super.initView();
	}

	/**
	 * 获取家校互动列表
	 */
	private void getHomeSchoolList(boolean isRefresh) {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(adpterUtil.getHandler());
			return;
		}
		if (!isRefresh) {
			
			loadingDialog.show();
		}
		String start = String.valueOf(pageInfo.getIndexId());
		String limit = String.valueOf(pageInfo.getPageSize());
		homeSchoolImpl.getHomeSchoolList(HomeSchoolActivities.this,applicationController.httpUtils,
				adpterUtil.getHandler(), studentId, teacherId, start, limit);
	}

	
	/**
	 * 处理封装handler接收数据实例化类方法
	 */
	public void handLeaveManageUtil() {
		adpterUtil = new MyListAdpterUtil(this, refreshListView, loadingDialog,
				new ListDataCallBack() {
					@Override
					public void onCallBack(Message msg) {
						HomeSchoolListItem homeSchoolitems = (HomeSchoolListItem) msg.obj;
						List<HomeSchoolListData> itmes =homeSchoolitems.getItems();
						if (homeSchoolitems != null) {
							
							int total_count = homeSchoolitems.getTotalCount();
							int cur_page = homeSchoolitems.getCurrentPageNo();
							HandSucessAdpter.initPageInfo(refreshListView, pageInfo, cur_page, total_count);
							CurPage = pageInfo.getCurPage();
							
						}

						if (itmes == null||itmes.size() == 0) {
							refreshListView.onRefreshComplete();
							showToast("暂无数据");
							return;
						}
						if (mAdpter == null) {
							mAdpter = new HomeSchoolActivitiesAdpter(itmes);
							refreshListView.setAdapter(mAdpter);
						}
						if (pull_start == 1) {
							refreshListView.onRefreshComplete();
							mAdpter.addItems(itmes, true);// 下一页数据
						} else if (pull_start == 2) {
							refreshListView.onRefreshComplete();
							mAdpter.addItems(itmes, false);// 请求最新数据
						}
					}
				});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.questions:
			// 跳转家校互动提问界面
			goActivity(InteractionQuestions.class);
			break;
		case R.id.back:
			finish();
			break;
		}
	}

	private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction();
				if (action.equals("action.refreshHomeSchoolList")) {
					HomeSchoolListData homeSchoolData = (HomeSchoolListData) intent.getSerializableExtra("storeData");
					if (mAdpter != null && mAdpter.getItemsValue() != null) {
						mAdpter.addLocalItems(homeSchoolData);
					} else {
						List<HomeSchoolListData> new_tiems = new ArrayList<HomeSchoolListData>();
						new_tiems.add(homeSchoolData);
						mAdpter = new HomeSchoolActivitiesAdpter(new_tiems);
						refreshListView.setAdapter(mAdpter);
					}
				}
			}
		}
	};

	@Override
	protected void onStart() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.refreshHomeSchoolList");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mRefreshBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, InteractionDetail.class);
		HomeSchoolListData data = mAdpter.getItemData((position - 1));
		String themeId = data.getThemeId();
		intent.putExtra("id", themeId);
		startActivity(intent);

	}

	@SuppressLint("ShowToast")
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(context,
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_SHOW_DATE| DateUtils.FORMAT_ABBREV_ALL);
		// 设置最后更新时间
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
			// 下拉刷新数据
			pull_start = 2;
			CurPage = 1;
			pageInfo.setCurPage(CurPage);
			getHomeSchoolList(true);
		} else {
			// 上拉加载更多
			if (pageInfo.isLastPage()) {
				Message message = adpterUtil.getHandler().obtainMessage();
				message.what = HandlerCode.HANDLER_LASTPAGE;
				adpterUtil.getHandler().sendMessage(message);
			} else {
				CurPage++;
				pageInfo.setCurPage(CurPage);
				pull_start = 1;
				getHomeSchoolList(true);
			}

		}
	}

}

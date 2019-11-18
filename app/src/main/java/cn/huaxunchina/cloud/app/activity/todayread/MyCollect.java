package cn.huaxunchina.cloud.app.activity.todayread;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.TodayReadAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.imp.read.TodayReadImpl;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadData;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadDataArray;
import cn.huaxunchina.cloud.app.tools.HandSucessAdpter;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 每日一读收藏夹界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-21 上午11:34:25
 */
public class MyCollect extends BaseActivity implements OnItemClickListener, OnRefreshListener<ListView> {
	private PullToRefreshListView today_collect_list;
	private TodayReadAdapter mAdapter;
	private TodayReadDataArray todayCollect;
	private ApplicationController applicationController;
	private int pull_start = 0;
	private int CurPage;
	private MyBackView back;
	private TodayReadImpl cmoImplement;
	private MyListAdpterUtil adpterUtil;
	private HandSucessAdpter handAdpter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_collect);
		applicationController = (ApplicationController) getApplication();
		initView();
		handTodayCollectUtil();
		getTodaySaveList(false);
	}
 
	@Override
	public void initView() {
		back = (MyBackView) findViewById(R.id.back);
		back.setBackText("我的收藏");
		back.setAddActivty(this);
		today_collect_list = (PullToRefreshListView) findViewById(R.id.today_collect_list);
		today_collect_list.setOnItemClickListener(this);
		today_collect_list.setOnRefreshListener(this);
		pageInfo = new PageInfo();
		loadingDialog = new LoadingDialog(context);
		cmoImplement = new TodayReadImpl();
		super.initView();
	}

	/**
	 * 获取收藏夹列表数据
	 */
	private void getTodaySaveList(boolean isRefresh) {
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
		cmoImplement.getToadySaveList(MyCollect.this,applicationController.httpUtils, adpterUtil.getHandler(),start, limit);

	}
	
	/**
	 * 处理封装handler接收数据实例化类方法
	 */

	public void handTodayCollectUtil() {
		adpterUtil = new MyListAdpterUtil(this, today_collect_list, loadingDialog,
				new ListDataCallBack() {
					@Override
					public void onCallBack(Message msg) {
						todayCollect = (TodayReadDataArray) msg.obj;
						List<TodayReadData> itmes = todayCollect.getItems();
						if (todayCollect != null) {
							
							int total_count = todayCollect.getTotalCount();
							int cur_page = todayCollect.getCurrentPageNo();
							HandSucessAdpter.initPageInfo(today_collect_list, pageInfo, cur_page, total_count);
							CurPage = pageInfo.getCurPage();
							
						}

						if (itmes == null || itmes.size() == 0) {
							today_collect_list.onRefreshComplete();
							showToast("暂无数据");
							return;
						}
						if (mAdapter == null) {
							mAdapter=new TodayReadAdapter(context, itmes);
							today_collect_list.setAdapter(mAdapter);
						}
						if (pull_start == 1) {
							today_collect_list.onRefreshComplete();
							mAdapter.addItmes(itmes, true);// 下一页数据
						} else if (pull_start == 2) {
							today_collect_list.onRefreshComplete();
							mAdapter.addItmes(itmes, false);// 请求最新数据
						}
					}
				});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		intent = new Intent(context, TodayReadDetail.class);
		TodayReadData data = mAdapter.getItemTodayRead(position - 1);
		intent.putExtra("type", "2");
		intent.putExtra("id", data.getReadId());
		intent.putExtra("position", position);
		intent.putExtra("isCollect", true);
		startActivity(intent);
	}

	private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction();
				if (action.equals("action.refreshTodayList")) {
					TodayReadData todaydetail = (TodayReadData) intent.getSerializableExtra("storeData");
					mAdapter.removeLocalItems(todaydetail);
				}
			}
		}
	};

	protected void onStart() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.refreshTodayList");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);
		super.onStart();
	};

	@Override
	protected void onDestroy() {
		unregisterReceiver(mRefreshBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(context,
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// 设置最后更新时间
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
			// 下拉刷新数据
			pull_start = 2;
			CurPage = 1;
			pageInfo.setCurPage(CurPage);
			getTodaySaveList(true);

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
				getTodaySaveList(true);
			}

		}
	}
}

package cn.huaxunchina.cloud.app.activity.todayread;

import java.util.List;

import android.content.Intent;
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
import cn.huaxunchina.cloud.app.adapter.TodayReadAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.imp.read.TodayReadImpl;
import cn.huaxunchina.cloud.app.imp.read.TodayReadInterface;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadData;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadDataArray;
import cn.huaxunchina.cloud.app.tools.HandSucessAdpter;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TodayRead extends BaseActivity implements OnItemClickListener,OnClickListener, OnRefreshListener<ListView> {
	private PullToRefreshListView today_list;
	private ImageButton savebtn;
	private MyBackView back;
	private TodayReadAdapter mAdapter;
	private TodayReadDataArray todayReadData;
	private int pull_start = 0;
	private int CurPage;
	private ApplicationController applicationController;
	private MyListAdpterUtil adpterUtil;
	private HandSucessAdpter handAdpter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.today_read);
		applicationController = (ApplicationController) getApplication();
		initView();
		handTodayUtil();
		getDailyReadList(false);
	}

	@Override
	public void initView() {
		back = (MyBackView) findViewById(R.id.back);
		today_list = (PullToRefreshListView) findViewById(R.id.today_list);
		today_list.setOnItemClickListener(this);
		today_list.setOnRefreshListener(this);
		savebtn = (ImageButton) findViewById(R.id.today_savebtn);
		savebtn.setOnClickListener(this);
		back.setBackText("每日一读");
		back.setAddActivty(this);
		pageInfo = new PageInfo();
		loadingDialog = new LoadingDialog(this);
		super.initView();
	}
	
	/**
	 * 处理封装handler接收数据实例化类方法
	 */

	public void handTodayUtil() {
		adpterUtil = new MyListAdpterUtil(this, today_list, loadingDialog,
				new ListDataCallBack() {
					@Override
					public void onCallBack(Message msg) {
						todayReadData = (TodayReadDataArray) msg.obj;
						List<TodayReadData> itmes = todayReadData.getItems();
						if (todayReadData != null) {
							
							
							int total_count = todayReadData.getTotalCount();
							int cur_page = todayReadData.getCurrentPageNo();
							HandSucessAdpter.initPageInfo( today_list, pageInfo, cur_page, total_count);
							CurPage = pageInfo.getCurPage();
							
						}

						if (itmes == null || itmes.size() == 0) {
							today_list.onRefreshComplete();
							showToast("暂无数据");
							return;
						}
						if (mAdapter == null) {
							mAdapter = new TodayReadAdapter(context,itmes);
							today_list.setAdapter(mAdapter);
						}
						if (pull_start == 1) {
							today_list.onRefreshComplete();
							mAdapter.addItmes(itmes, true);// 下一页数据
						} else if (pull_start == 2) {
							today_list.onRefreshComplete();
							mAdapter.addItmes(itmes, false);// 请求最新数据
						}
					}
				});
	}
	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 收藏夹
		case R.id.today_savebtn:
			goActivity(MyCollect.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		intent = new Intent(context, TodayReadDetail.class);
		TodayReadData info = mAdapter.getItemTodayRead(position - 1);
		intent.putExtra("type", "1");
		intent.putExtra("id", info.getReadId());
		intent.putExtra("position", position);
		intent.putExtra("isCollect", info.isCollect());
		startActivity(intent);
	}

	/**
	 * 获取每日一读列表信息
	 */

	@SuppressWarnings("static-access")
	public void getDailyReadList(boolean isRefresh) {
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
		TodayReadInterface todayReadInterface = new TodayReadImpl();
		todayReadInterface.getDailyReadList(TodayRead.this,applicationController.httpUtils,adpterUtil.getHandler(), start, limit); // 获取家长每日一读文章列表
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
			getDailyReadList(true);
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
				getDailyReadList(true);
			}

		}

	}
}

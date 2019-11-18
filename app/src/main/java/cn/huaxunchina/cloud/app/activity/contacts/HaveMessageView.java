package cn.huaxunchina.cloud.app.activity.contacts;

import java.util.ArrayList;
import java.util.List;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.HosMessageAdpter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ContactsModel;
import cn.huaxunchina.cloud.app.imp.ContactsResponse;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.model.SMSHistory;
import cn.huaxunchina.cloud.app.model.SMSHistoryData;
import cn.huaxunchina.cloud.app.model.SendSmsData;
import cn.huaxunchina.cloud.app.tools.HandSucessAdpter;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 通讯录历史短信界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-2-5 上午11:14:32
 */
public class HaveMessageView extends BaseActivity implements OnItemClickListener, OnRefreshListener<ListView> {
	private PullToRefreshListView hos_list;
	private HosMessageAdpter mAdpter;
	private MyListAdpterUtil adpterUtil;
	private int pull_start = 0;
	private int CurPage;
	private MyBackView back;
	private ApplicationController applicationController;
	private List<SMSHistory> itmes = new ArrayList<SMSHistory>();
	private SendSmsData sendData;
	private SMSHistoryData data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hos_message);
		initData();
	}

	private void initData() {
		hos_list = (PullToRefreshListView) findViewById(R.id.hos_mess_list);
		back = (MyBackView) findViewById(R.id.back);
		back.setBackText("短信记录");
		back.setAddActivty(this);
		hos_list.setOnItemClickListener(this);
		hos_list.setOnRefreshListener(this);
		adpterUtil = new MyListAdpterUtil();
		loadingDialog = new LoadingDialog(this);
		intent = this.getIntent();
		if (intent != null) {
			sendData = (SendSmsData) intent.getSerializableExtra("data");
		}
		pageInfo = new PageInfo();
		applicationController = (ApplicationController) getApplication();
		handlistCallBack();
		getHosMessageList(false);
	}

	/**
	 * 获取历史短信列表数据
	 */

	@SuppressWarnings("static-access")
	private void getHosMessageList(boolean isRefresh) {
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
		ContactsResponse response = new ContactsModel();
		response.getHosMessageList(start, limit,applicationController.httpUtils, adpterUtil.getHandler());
	}

	/**
	 * 处理列表返回的后的数据
	 */
	public void handlistCallBack() {
		adpterUtil = new MyListAdpterUtil(this, hos_list, loadingDialog,
				new ListDataCallBack() {
					@Override
					public void onCallBack(Message msg) {
						
						
						
						data = (SMSHistoryData) msg.obj;
						if (data != null) {
							itmes = data.getItems();
						}
						if (itmes.size() == 0) {
							showToast("暂无数据");
							return;
						}
						if(itmes.size()>0){
							int total_count = data.getTotalCount();
							int cur_page = data.getCurrentPageNo();
							HandSucessAdpter.initPageInfo(hos_list, pageInfo, cur_page, total_count);
							CurPage = pageInfo.getCurPage();
						}
						if (mAdpter == null) {
							mAdpter = new HosMessageAdpter(context, itmes,applicationController.getCurDate());
							hos_list.setAdapter(mAdpter);
						}
						if (pull_start == 1) {
							hos_list.onRefreshComplete();
							mAdpter.addItems(itmes, true);// 下一页数据
						} else if (pull_start == 2) {
							hos_list.onRefreshComplete();
							mAdpter.addItems(itmes, false);// 请求最新数据
						}

					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SMSHistory items = mAdpter.getItemSMSHistory(position -1);
		intent = new Intent(this, HosMessageDetail.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("sendData", sendData);
		intent.putExtra("data", "today");
		intent.putExtra("sendSerial", items.getSendSerial());
		intent.putExtras(bundle);
		startActivity(intent);
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
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
			// // 下拉刷新数据
			pull_start = 2;
			CurPage = 1;
			pageInfo.setCurPage(CurPage);
			getHosMessageList(true);
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
				getHosMessageList(true);
			}
		}
	}
}

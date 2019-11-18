package cn.huaxunchina.cloud.location.app.activity.msg;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.location.app.adapter.LocMessageAdpter;
import cn.huaxunchina.cloud.location.app.model.post.LocMessageModel;
import cn.huaxunchina.cloud.location.app.model.res.ResMessageDataModel;
import cn.huaxunchina.cloud.location.app.model.res.ResMessageModel;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.tools.TimeUtil;

/**
 * 消息列表内容界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-29 下午12:09:32
 */
public class LocMessageFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener<ListView> {
	// 标志位，标志已经初始化完成。
	private boolean isPrepared;
	private ApplicationController applicationController;
	private Activity activity;
	private PageInfo pageInfo;
	private LoadingDialog loadingDialog; // 加载进度条对话框
	private PullToRefreshListView message_list;
	private LocMessageAdpter mAdpter;
	private int pull_start = 0;
	private int CurPage = 1;
	private String locMessageType = "";
	private LocMessageModel locPostMessageModel;
	private HttpHandler httpHandler;
	private String date;

	@Override
	protected void loadData() {
		if (!isPrepared || !isVisible) {
			return;
		}
		isPrepared = false;
		Bundle bundle = getArguments();
		locMessageType = bundle.getString("locMessageType");
		date = TimeUtil.getCurYMothTime();
		initObject(date);
		initMessageListData(false, locPostMessageModel);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		applicationController = (ApplicationController) activity.getApplication();
		locPostMessageModel = new LocMessageModel();
		pageInfo = new PageInfo();
		loadingDialog = new LoadingDialog(activity);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.loc_mess_fragment, container,false);
		message_list = (PullToRefreshListView) view.findViewById(R.id.loc_mess_list);
		message_list.setOnItemClickListener(this);
		message_list.setOnRefreshListener(this);
		httpHandler = new HttpHandler(activity, message_list);
		isPrepared = true;
		loadData();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 初始化接口请求数据
	 */
	public void initMessageListData(boolean isRefresh, Object object) {
		if (!isRefresh) {
			loadingDialog.show();
		}
		new HttpHandler(loadingDialog, applicationController.httpUtils_lbs, object,
				new HttpHandlerCallBack() {
					@Override
					public void onCallBack(String json) {
						handBackResult(json);
					}
					@SuppressLint("ShowToast")
					@Override
					public void onErro() {
						if(message_list!=null){
						message_list.onRefreshComplete();
						message_list.setMode(Mode.BOTH);
						}
					}
				});
	}

	/**
	 * 处理接口返回成功数据
	 * 
	 * @param msg
	 */
	@SuppressLint("ShowToast")
	public void handBackResult(String json) {
		try {
			String data = GsonUtils.getData(json);
			ResMessageDataModel model = GsonUtils.getSingleBean(data,ResMessageDataModel.class);
			List<ResMessageModel> itmes = model.getItems();
			if (data != null) {
				
				int totalCount = model.getTotalCount();
				this.CurPage =  model.getCurrentPageNo();
				pageInfo.setCurPage(CurPage);
				pageInfo.setNumCount(totalCount);
				int pageCount = pageInfo.getPageCount();
				if (pageCount > 1) {
					message_list.setMode(Mode.BOTH);
				} else {
					message_list.setMode(Mode.PULL_FROM_START);
				}
				
			}
			
			if (itmes == null || itmes.size() == 0) {
				itmes = new ArrayList<ResMessageModel>();
				Toast.makeText(activity, "暂无数据", 0).show();
				mAdpter = new LocMessageAdpter(activity, itmes);
				message_list.setAdapter(mAdpter);
				return;
			}
			if (mAdpter == null) {
				mAdpter = new LocMessageAdpter(activity, itmes);
				message_list.setAdapter(mAdpter);
			}
			if (pull_start == 1) {
				message_list.onRefreshComplete();
				mAdpter.addItems(itmes, true);// 下一页数据
			} else if (pull_start == 2) {
				message_list.onRefreshComplete();
				mAdpter.addItems(itmes, false);// 请求最新数据
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(activity,
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// 设置最后更新时间
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
			// 下拉刷新数据
			pull_start = 2;
			CurPage = 1;
			pageInfo.setIndexId(0);
			pageInfo.setCurPage(CurPage);
			initObject(date);
			initMessageListData(true, locPostMessageModel);
		} else {
			// 上拉加载更多
			if (pageInfo.isLastPage()) {
				Message message = httpHandler.getHander().obtainMessage();
				message.what = HandlerCode.HANDLER_LASTPAGE;
				httpHandler.getHander().sendMessage(message);
			} else {
				CurPage++;
				pageInfo.setCurPage(CurPage);
				pull_start = 1;
				initObject(date);
				initMessageListData(true, locPostMessageModel);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ResMessageModel items = mAdpter.getItemLeave(position - 1);
		Intent intent = new Intent(getActivity(), LocMessageDetail.class);
		intent.putExtra("items", items);
		startActivity(intent);
	}

	public void initObject(String date) {
		locPostMessageModel.setType(Integer.valueOf(locMessageType));
		locPostMessageModel.setStart(pageInfo.getIndexId());
		locPostMessageModel.setStartDate(date);
		locPostMessageModel.setLimit(pageInfo.getPageSize());
	}
	public void onSelectdata(String time,int pull_start) {
		this.pull_start=pull_start;
		date = time;
		initObject(date);
		initMessageListData(false, locPostMessageModel);
	}

}

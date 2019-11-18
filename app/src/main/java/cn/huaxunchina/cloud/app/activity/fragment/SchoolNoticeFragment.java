package cn.huaxunchina.cloud.app.activity.fragment;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.activity.notice.NoticeDetail;
import cn.huaxunchina.cloud.app.adapter.SchoolNoticeAdpter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.imp.notice.NoticeImpl;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.model.notice.NoticeArray;
import cn.huaxunchina.cloud.app.model.notice.NoticeData;
import cn.huaxunchina.cloud.app.tools.HandSucessAdpter;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;

/**
 * 滑动界面-校园公告界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-7-18 下午2:33:16
 */
public class SchoolNoticeFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener<ListView> {
	private PullToRefreshListView notice_list;
	private SchoolNoticeAdpter schoolnoticeAdpter;
	private LoadingDialog loadingDialog; // 加载进度条对话框
	protected NoticeImpl noticeImplement; // 公告接口实现类
	private AsyncHttpClient httpUtils;
	private Activity activity;
	private String role;
	private String notice_type;
	private String role_id;
	private ApplicationController applicationController;
	private NoticeArray notice_array;
	private PageInfo pageInfo;
	private int pull_start = 0;
	private int CurPage;
	// 标志位，标志已经初始化完成。
	private boolean isPrepared;
	private MyListAdpterUtil adpterUtil;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		applicationController = (ApplicationController) activity.getApplication();
		pageInfo = new PageInfo();
		loadingDialog = new LoadingDialog(activity);
		httpUtils = applicationController.httpUtils;
		noticeImplement = new NoticeImpl();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notice_school,container, false);
		notice_list = (PullToRefreshListView) view.findViewById(R.id.notice_list);
		notice_list.setOnItemClickListener(this);
		notice_list.setOnRefreshListener(this);
		isPrepared = true;  
		loadData();
		return view;
	}

	@Override
	protected void loadData() {
		if (!isPrepared || !isVisible) {
			return;
		}
		isPrepared = false;
		Bundle bundle = getArguments();
		notice_type = bundle.getString("notice_type");
		role = bundle.getString("role");
		role_id = bundle.getString("id");
		handSchoolNoticeUtil();
		getNoticeList(false);
	}

	/**
	 * 获取本地发布后广播接收到的数据
	 * 
	 * @param intent
	 */

	public void getStoreData(Intent intent) {
		NoticeData data = (NoticeData) intent.getSerializableExtra("storeData");
		if (schoolnoticeAdpter != null&& schoolnoticeAdpter.getItemsValue() != null) {
			schoolnoticeAdpter.addLocalItems(data);
		} else {
			List<NoticeData> new_tiems = new ArrayList<NoticeData>();
			new_tiems.add(data);
			schoolnoticeAdpter = new SchoolNoticeAdpter(activity, new_tiems);
			notice_list.setAdapter(schoolnoticeAdpter);
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/*
	 * 获取通知通告列表数据
	 */
	private void getNoticeList(boolean isRefresh) {
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
		noticeImplement.getNoticeList(getActivity(),httpUtils, adpterUtil.getHandler(),role_id, start, limit, notice_type, role);
	}

	/**
	 * 处理封装handler接收数据实例化类方法
	 */

	public void handSchoolNoticeUtil() {
		adpterUtil = new MyListAdpterUtil(activity, notice_list, loadingDialog,
				new ListDataCallBack() {
					@Override
					public void onCallBack(Message msg) {
						notice_array = (NoticeArray) msg.obj;
						List<NoticeData> items = notice_array.getItems();
						if (notice_array != null) {
							int total_count = notice_array.getTotalCount();
							int cur_page = notice_array.getCurrentPageNo();
							HandSucessAdpter.initPageInfo( notice_list, pageInfo, cur_page, total_count);
							CurPage = pageInfo.getCurPage();
							
						}
						if (schoolnoticeAdpter == null) {
							schoolnoticeAdpter = new SchoolNoticeAdpter(activity, items);
							notice_list.setAdapter(schoolnoticeAdpter);

						} else if (pull_start == 1) {
							notice_list.onRefreshComplete();
							schoolnoticeAdpter.addItems(items, true);// 下一页数据
						} else if (pull_start == 2) {
							notice_list.onRefreshComplete();
							schoolnoticeAdpter.addItems(items, false);
						}

					}
				});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Intent intent = new Intent(getActivity(), NoticeDetail.class);
		NoticeData data = schoolnoticeAdpter.getItemNotice((position - 1));
		String noticeId = data.getNoticeId();
		intent.putExtra("id", noticeId);
		startActivity(intent);
	}

	@SuppressLint("ShowToast")
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(activity,System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// 设置最后更新时间
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
			// 下拉刷新数据
			pull_start = 2;
			CurPage = 1;
			pageInfo.setCurPage(CurPage);
			getNoticeList(true);
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
				getNoticeList(true);
			}

		}
	}

}

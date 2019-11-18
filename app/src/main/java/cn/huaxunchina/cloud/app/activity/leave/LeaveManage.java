package cn.huaxunchina.cloud.app.activity.leave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.huaxunchina.cloud.app.adapter.LeaveManageAdpter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.imp.leave.LeaveImpl;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.leave.LeaveArray;
import cn.huaxunchina.cloud.app.model.leave.LeaveProperty;
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
 * 请假管理列表界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-7 下午5:59:57
 */
public class LeaveManage extends BaseActivity implements OnClickListener,
		OnRefreshListener<ListView>, OnItemClickListener {
	private ImageButton send_leave;
	private LeaveManageAdpter mAdpter;
	private final String[] leave_type = { "其他", "事假", "病假" };
	private Map<String, String> leave_state;
	private PullToRefreshListView leave_list;
	private LeaveArray leaveArray; // 请假数据字段数组
	private Bundle bundle;
	private String studentId;
	private String teacherId;
	private int pull_start = 0;
	private int CurPage;
	private ApplicationController applicationController;
	private UserManager manager;// 个人信息对象
	private MyBackView back;
	private MyListAdpterUtil adpterUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave_manage);
		findView();
		initView();
		bindView();

	}

	@Override
	public void findView() {
		send_leave = (ImageButton) findViewById(R.id.edit_leave_imagebtn);
		leave_list = (PullToRefreshListView) findViewById(R.id.leave_list);
		back = (MyBackView) findViewById(R.id.back);
		super.findView();
	}

	@Override
	public void initView() {
		back.setBackText("请假管理");
		back.setAddActivty(this);
		loadingDialog = new LoadingDialog(this);
		applicationController = (ApplicationController) getApplication();
		pageInfo = new PageInfo();
		leave_state = new HashMap<String, String>();
		leave_state.put("W", "未审批");
		leave_state.put("Y", "同意");
		leave_state.put("N", "退回");
		leaveImpl = new LeaveImpl();
		manager = LoginManager.getInstance().getUserManager();
		if (manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {
			send_leave.setVisibility(View.VISIBLE);
			studentId = manager.curId;
		} else {
			send_leave.setVisibility(View.GONE);
			teacherId = manager.curId;
		}
		handLeaveManageUtil();
		getLeaveList(false);
		super.initView();
	}

	@Override
	public void bindView() {
		send_leave.setOnClickListener(this);
		leave_list.setOnItemClickListener(this);
		leave_list.setOnRefreshListener(this);
		super.bindView();
	}

	/**
	 * 处理封装handler接收数据实例化类方法
	 */

	public void handLeaveManageUtil() {
		adpterUtil = new MyListAdpterUtil(this, leave_list, loadingDialog,
				new ListDataCallBack() {
					@Override
					public void onCallBack(Message msg) {
						leaveArray = (LeaveArray) msg.obj;
						if(leaveArray==null){ showToast("暂无数据"); return;}
						List<LeaveProperty> itmes =leaveArray.getItems();
						if (leaveArray != null) {
							int total_count = leaveArray.getTotalCount();
							int cur_page = leaveArray.getCurrentPageNo();
							HandSucessAdpter.initPageInfo( leave_list, pageInfo, cur_page, total_count);
							CurPage = pageInfo.getCurPage();
							System.out.println(CurPage+""+pageInfo);
						}

						if (itmes == null||itmes.size() == 0) {
							leave_list.onRefreshComplete();
							showToast("暂无数据");
							return;
						}
						if (mAdpter == null) {
							mAdpter = new LeaveManageAdpter(context, itmes,leave_type, leave_state);
							leave_list.setAdapter(mAdpter);
						}
						if (pull_start == 1) {
							leave_list.onRefreshComplete();
							mAdpter.addItems(itmes, true);// 下一页数据
						} else if (pull_start == 2) {
							leave_list.onRefreshComplete();
							mAdpter.addItems(itmes, false);// 请求最新数据
						}
					}
				});

	}

	/**
	 * 获取请假列表
	 */

	public void getLeaveList(boolean isRefresh) {
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
		leaveImpl.getLeaveList(LeaveManage.this,applicationController.httpUtils,adpterUtil.getHandler(), studentId, teacherId,manager.curRoleId, start, limit);
	}

	private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction();
				if (action.equals("action.refreshLeaveList")) {
					LeaveProperty leaveProperty = (LeaveProperty) intent.getSerializableExtra("storeData");
					if (mAdpter != null && mAdpter.getItemsValue() != null) {
						mAdpter.addLocalItems(leaveProperty);
					} else {
						List<LeaveProperty> new_tiems = new ArrayList<LeaveProperty>();
						new_tiems.add(leaveProperty);
						mAdpter = new LeaveManageAdpter(context, new_tiems,leave_type, leave_state);
						leave_list.setAdapter(mAdpter);

					}
				}
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == ResultCode.REFRESH_CHECK_STATE) {
			if (data != null) {
				LeaveProperty leaveProperty = (LeaveProperty) data.getSerializableExtra("storeData");
				mAdpter.updatelItems(leaveProperty);
			}

		}
	}

	@Override
	protected void onStart() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.refreshLeaveList");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_leave_imagebtn:
			// 请假申请界面
			goActivity(SumbitLeave.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		intent = new Intent(this, LeaveDetail.class);
		LeaveProperty items = mAdpter.getItemLeave(position -1);
		bundle = new Bundle();
		bundle.putSerializable("LeaveProperty", items);
		bundle.putString("leaveType",leave_type[Integer.valueOf(items.getLeaveType())]);
		bundle.putString("leaveState",	leave_state.get(items.getApproveStatus().toString()));
		intent.putExtras(bundle);
		intent.putExtra("position", position);
		startActivityForResult(intent, 0);
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
	}
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(context,System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// 设置最后更新时间
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
			// 下拉刷新数据
			pull_start = 2;
			CurPage = 1;
			pageInfo.setCurPage(CurPage);
			getLeaveList(true);
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
				getLeaveList(true);
			}

		}
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(mRefreshBroadcastReceiver);
		super.onDestroy();
	}

}

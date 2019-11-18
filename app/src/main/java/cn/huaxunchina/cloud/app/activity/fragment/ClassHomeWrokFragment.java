package cn.huaxunchina.cloud.app.activity.fragment;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.activity.homework.HomeWork;
import cn.huaxunchina.cloud.app.activity.homework.HomeWrokDetail;
import cn.huaxunchina.cloud.app.adapter.HomeWorkAdpter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.imp.homwork.HomeWrokImpl;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokArray;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokProperty;
import cn.huaxunchina.cloud.app.tools.HandSucessAdpter;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.ContactsDialog;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyViewPager;
import com.astuetz.PagerSlidingTabStrip;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;

/**
 * 班级家庭作业Tab标签页面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-7-22 上午11:59:05
 */

@SuppressLint({ "ValidFragment", "HandlerLeak" })
public class ClassHomeWrokFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener<ListView>, OnClickListener {
	private PullToRefreshListView home_wrok_list;
	private HomeWorkAdpter homeWrokAdpter;
	private HomeWrokImpl homeImpl;
	private AsyncHttpClient httpUtils;
	private int CurPage;
	private String classId;
	private HomeWork activity;
	private LoadingDialog loadingDialog;
	private HomeWrokProperty homeWorkModel;
	private ApplicationController applicationController;
	private PageInfo pageInfo;
	private int pull_start = 0;
	// 标志位，标志已经初始化完成。
	private boolean isPrepared;
	private MyListAdpterUtil adpterUtil;
	private View view;
	private UserManager manager;// 个人信息对象
	private LinearLayout del_relative;
	private Button del_btn, cal_btn;
	private TextView all_txt;// 全选txt
	private ImageButton edit_homework_btn;
	private List<HomeWrokProperty> itmes;
	private MyViewPager MyPager;
	PagerSlidingTabStrip tabs;
	private int curCount = 0;

	@SuppressWarnings("static-access")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (HomeWork) activity;
		applicationController = (ApplicationController) activity
				.getApplication();
		pageInfo = new PageInfo();
		manager = LoginManager.getInstance().getUserManager();
		httpUtils = applicationController.httpUtils;
		loadingDialog = new LoadingDialog(activity);
		adpterUtil = new MyListAdpterUtil();
		homeImpl = new HomeWrokImpl();
	}

	@Override
	protected void loadData() {
		if (!isPrepared || !isVisible) {
			return;
		}
		isPrepared = false;
		Bundle bundle = getArguments();
		classId = (String) bundle.getString("classId");
		handHomeWrokUtil();
		getHomeWrokList(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_homework_class, container,
				false);
		home_wrok_list = (PullToRefreshListView) view
				.findViewById(R.id.home_wrok_list);
		del_relative = (LinearLayout) view.findViewById(R.id.del_relative);
		del_btn = (Button) view.findViewById(R.id.delete);
		cal_btn = (Button) view.findViewById(R.id.cancle);
		all_txt = (TextView) activity.findViewById(R.id.all_txt);
		edit_homework_btn = (ImageButton) activity
				.findViewById(R.id.edit_imagebtn);
		MyPager = (MyViewPager) activity.findViewById(R.id.mypager);
		tabs = (PagerSlidingTabStrip) activity.findViewById(R.id.tabs);
		home_wrok_list.setOnItemClickListener(this);
		home_wrok_list.setOnRefreshListener(this);
		del_btn.setOnClickListener(this);
		cal_btn.setOnClickListener(this);
		isPrepared = true;
		loadData();
		return view;
	}

	/**
	 * 本地刷新列表数据
	 * 
	 * @param data
	 */
	public void refresLocalData(HomeWrokProperty data) {
		if (homeWrokAdpter != null && homeWrokAdpter.getItemsValue() != null) {
			homeWrokAdpter.addLocalItems(data);
		} else {
			List<HomeWrokProperty> new_tiems = new ArrayList<HomeWrokProperty>();
			new_tiems.add(data);
			homeWrokAdpter = new HomeWorkAdpter(activity, new_tiems,
					manager.userId, all_txt);
			home_wrok_list.setAdapter(homeWrokAdpter);
			onLongClickListener();
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 处理封装handler接收数据实例化类方法
	 */

	public void handHomeWrokUtil() {
		adpterUtil = new MyListAdpterUtil(activity, home_wrok_list,
				loadingDialog, new ListDataCallBack() {
					@SuppressLint("ShowToast")
					@Override
					public void onCallBack(Message msg) {
						HomeWrokArray homewok_array = (HomeWrokArray) msg.obj;
						itmes = homewok_array.getItems();
						if (itmes != null) {
							int total_count = homewok_array.getTotalCount();
							int cur_page = homewok_array.getCurrentPageNo();
							HandSucessAdpter.initPageInfo(home_wrok_list,
									pageInfo, cur_page, total_count);
							CurPage = pageInfo.getCurPage();
						}
						if (itmes == null || itmes.size() == 0) {
							home_wrok_list.onRefreshComplete();
							Toast.makeText(activity, "暂无数据", 0).show();
						}
						if (homeWrokAdpter == null) {
							homeWrokAdpter = new HomeWorkAdpter(activity,itmes, manager.userId, all_txt);
							home_wrok_list.setAdapter(homeWrokAdpter);
						}
						if (pull_start == 1) {
							home_wrok_list.onRefreshComplete();
							homeWrokAdpter.addItems(itmes, true);// 下一页数据
						} else if (pull_start == 2) {
							home_wrok_list.onRefreshComplete();
							homeWrokAdpter.addItems(itmes, false);// 请求最新数据
						}
						onLongClickListener();
					}
				});
	}

	@SuppressLint({ "HandlerLeak", "ShowToast" })
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (loadingDialog != null) {
				loadingDialog.dismiss();
			}
			switch (msg.what) {
			case HandlerCode.DEL_SUCCESS: // 删除成功
				MyPager.setDisableScroll(false);
				tabs.setCilck(true);
				pullList();
				Toast.makeText(activity, "删除成功", 0).show();
				hidControlVisible();
				initCheck();
				break;
			case HandlerCode.DEL_FAIL: // 删除失败
				Toast.makeText(activity, "删除失败", 0).show();
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				Toast.makeText(activity, "已断开,请重新登录", 0).show();
				break;
			}
		}
	};

	/**
	 * 添加列表删除事件
	 */
	public void onLongClickListener() {
		if (!manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {
			ListView listView = home_wrok_list.getRefreshableView();
			listView.refreshDrawableState();
			listView.setOnItemLongClickListener(listener);
		}
	}

	/**
	 * 获取请假作业列表数据
	 */
	private void getHomeWrokList(boolean isRefresh) {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		if (!isRefresh) {
			loadingDialog.show();
		}
		String start = String.valueOf(pageInfo.getIndexId());
		String limit = String.valueOf(pageInfo.getPageSize());
		homeImpl.getHomeWrokList(getActivity(),httpUtils, adpterUtil.getHandler(), start,
				limit, classId);
	}

	/**
	 * 删除家庭作业数据
	 */
	private void delHomeWrokList(String homeWorkId) {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(adpterUtil.getHandler());
			return;
		}
		loadingDialog.show();
		homeImpl.delHomeWrokList(getActivity(),httpUtils, handler, homeWorkId);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), HomeWrokDetail.class);
		homeWorkModel = homeWrokAdpter.getItemHomeWork(position - 1);
		intent.putExtra("id", homeWorkModel.getHomeworkId());
		intent.putExtra("items", homeWorkModel);
		startActivity(intent);
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
			// // 下拉刷新数据
			pullList();
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
				getHomeWrokList(true);
			}
		}
	}

	/**
	 * 设置全选与全不选状态
	 */
	public void setAllCheck() {
		int totalCount = homeWrokAdpter.getTotalCount();
	    curCount = homeWrokAdpter.getCurCount();
		if (curCount == totalCount) {
			curCount = 0;
			homeWrokAdpter.setAllCk(false);
		} else {
			curCount = totalCount;
			homeWrokAdpter.setAllCk(true);
		}
		homeWrokAdpter.notifyDataSetChanged();
		homeWrokAdpter.setTitle(curCount);
	}

	/**
	 * 刷新列表
	 */
	public void pullList() {
		pull_start = 2;
		CurPage = 1;
		pageInfo.setCurPage(CurPage);
		getHomeWrokList(true);
	}

	/**
	 * 显示底部删除操作栏时，基础控件显示与隐藏及动画
	 */
	public void ControlVisible() {
//		Animation animation = AnimationUtils.loadAnimation(activity,R.anim.dialog_in);
//		del_relative.setAnimation(animation);
		del_relative.setVisibility(View.VISIBLE);
		all_txt.setVisibility(View.VISIBLE);
		edit_homework_btn.setVisibility(View.GONE);
		homeWrokAdpter.isCheckVisible(true);
	}

	/**
	 * 隐藏底部删除操作栏时，基础控件显示与隐藏及动画
	 */
	public void hidControlVisible() {
//		Animation animation = AnimationUtils.loadAnimation(activity,R.anim.dialog_out);
		del_relative.setVisibility(View.GONE);
//		del_relative.setAnimation(animation);
		all_txt.setVisibility(View.GONE);
		edit_homework_btn.setVisibility(View.VISIBLE);
		homeWrokAdpter.isCheckVisible(false);
	}

	private OnItemLongClickListener listener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			MyPager.setDisableScroll(true);
			tabs.setCilck(false);
			ControlVisible();
			return true;
		}
	};

	/**
	 * 创建对话框
	 */
	protected void dialog(final String del_homeId) {
		ContactsDialog dialog = new ContactsDialog(getActivity());
		dialog.show();
		dialog.setTitle("提示");
		dialog.setMessage("确认删除,今天发布的作业？");
		dialog.setNegativeButton("取消");
		dialog.setPositiveButton("确定");
		dialog.setOkOnClickListener(new ContactsDialog.OnClickListener() {
			@Override
			public void onClick() {
				delHomeWrokList(del_homeId);// 删除家庭作业
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delete: // 删除
			delData();
			break;
		case R.id.cancle: // 取消操作
			hidControlVisible();
			MyPager.setDisableScroll(false);
			tabs.setCilck(true);
			initCheck();
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化全选与不全选
	 */
	private void initCheck() {
		List<HomeWrokProperty> itmes = homeWrokAdpter.getItemsValue();
		for (int i = 0; i < itmes.size(); i++) {
			if (itmes.get(i).isClick()) {
				itmes.get(i).setCheck(false);
			}
		}
		all_txt.setText("全选");
		homeWrokAdpter.setCurCount(0);
	}

	/**
	 * 删除数据操作
	 */
	@SuppressLint({ "ShowToast", "ResourceAsColor" })
	public void delData() {
		String delId = homeWrokAdpter.delItemData();
		if (!delId.equals("")) {
			List<Boolean> list = homeWrokAdpter.isCurDayData();
			if (list.contains(true)) {
				dialog(delId.substring(0, (delId.length() - 1)));
			} else {
				delHomeWrokList(delId.substring(0, (delId.length() - 1)));// 删除家庭作业
			}
		} else {
			Toast.makeText(activity, "请选择删除项", 0).show();
		}

	}
}

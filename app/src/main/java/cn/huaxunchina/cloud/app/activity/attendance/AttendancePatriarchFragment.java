package cn.huaxunchina.cloud.app.activity.attendance;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.adapter.AttPatriarchAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.imp.AttendanceModel;
import cn.huaxunchina.cloud.app.imp.AttendanceResponse;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.model.AttendanceInfo;
import cn.huaxunchina.cloud.app.model.AttendanceInfoData;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil.DateCallBack;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 考勤列表(家长角色)
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月16日 下午12:23:36
 */
public class AttendancePatriarchFragment extends BaseFragment implements
		OnClickListener, OnRefreshListener<ListView>,
		DateCallBack {

	private Activity activity;
	private PullToRefreshListView refreshListView;
	private AttendanceResponse response;
	private ApplicationController applicationController;
	private LoadingDialog loadingDialog;
	private String studentId;
	private AttPatriarchAdapter adapter;
	private MyBackView back;
	private MyListAdpterUtil adpterUtil;
	private DataDialogUtil dateUtil;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		applicationController = (ApplicationController) activity.getApplication();
		response = new AttendanceModel();
		loadingDialog = new LoadingDialog(activity);
		studentId = LoginManager.getInstance().getUserManager().curId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.att_patriarch_activity,container, false);
		initView(view);
		handAttendanceUtil();
		initData(null, false);
		return view;
	}

	private void initView(View view) {
		back = (MyBackView) view.findViewById(R.id.back);
		back.setBackText("考勤");
		back.setAddActivty(activity);
		view.findViewById(R.id.att_search).setOnClickListener(this);
		refreshListView = (PullToRefreshListView) view.findViewById(R.id.att_patriarch_listview);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setMode(Mode.PULL_FROM_START);
		dateUtil = new DataDialogUtil(getActivity());
		dateUtil.setDateCallBack(this);
	}

	/**
	 * 获取考勤数据
	 * @param signDate
	 * @param isRefresh
	 */
	private void initData(String signDate, boolean isRefresh) {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(adpterUtil.getHandler());
			return;
		}
		
		if (!isRefresh) {
			loadingDialog.show();
		}
		response.getAttInfoList(studentId, null, signDate,applicationController.httpUtils, adpterUtil.getHandler());
	}

	/**
	 * 处理封装handler接收数据实例化类方法
	 */
	public void handAttendanceUtil() {
		adpterUtil = new MyListAdpterUtil(activity, refreshListView,loadingDialog, new ListDataCallBack() {
					@SuppressLint("ShowToast")
					@Override
					public void onCallBack(Message msg) {
						AttendanceInfoData data = (AttendanceInfoData) msg.obj;
						List<AttendanceInfo> list = data.getData();
						if (list != null && list.size() > 0) {
							adapter = new AttPatriarchAdapter(list.get(0));
							refreshListView.setAdapter(adapter);
						} else {
							Toast.makeText(activity, "暂无数据", 0).show();
							if (adapter != null) {
								adapter.clear();
							}
							
						}
					}

				});
	}

	@Override
	public void onSelectdata(String dates) {
		this.date = dates;
		initData(date, false);
		//Toast.makeText(activity, date, Toast.LENGTH_LONG).show();
	}

	/*@Override
	public void onSelectdata(String str,String dates) {
		this.date = dates;
		//initData(date, false);
		Toast.makeText(activity, date, Toast.LENGTH_LONG).show();
	}*/
	
	String date;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.att_search:
			/*DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int width = dm.widthPixels;
			int height = dm.heightPixels;
			AttendanceTeacherDialog diolog = new AttendanceTeacherDialog(activity, null, width, height, true);
			Window window = diolog.getWindow();
			window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置// //Gravity.BOTTOM
			window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
			diolog.setAttendanceTeacherCallBack(this);
			diolog.setCancelable(true);
			diolog.setCanceledOnTouchOutside(true);
			diolog.show();*/
			dateUtil.getCurYearDateList(null);
			break;
		}

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(activity,System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// 设置最后更新时间
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
			// 下拉刷新数据
			initData(date, true);
		}
	}

	@Override
	protected void loadData() {

	}

}

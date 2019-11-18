package cn.huaxunchina.cloud.app.activity.score;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

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
import cn.huaxunchina.cloud.app.adapter.ScoreAdapter;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.imp.ScoreModel;
import cn.huaxunchina.cloud.app.imp.ScoreResponse;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.PageInfo;
import cn.huaxunchina.cloud.app.model.Score;
import cn.huaxunchina.cloud.app.model.ScoreData;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.HandSucessAdpter;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 成绩列表
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月24日 下午2:26:17
 * 
 */
public class ScoreList extends BaseActivity implements OnItemClickListener,
		OnRefreshListener<ListView> {

	private PullToRefreshListView refreshListView;
	private ScoreAdapter adapter;
	private ScoreResponse response;
	private String curRoleId = null;
	private String classId = null;
	private LoadingDialog loadingDialog;
	private PageInfo pageInfo;
	private int pull_start = 0;
	private int CurPage = 1;
	private MyBackView back;
	private MyListAdpterUtil adpterUtil;
	private HandSucessAdpter handAdpter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoredetail_activity);
		response = new ScoreModel();
		// initBar("成绩管理");
		initView();
		

	}

	@Override
	public void initView() {
		super.initView();
		back = (MyBackView) findViewById(R.id.back);
		back.setBackText("成绩管理");
		back.setAddActivty(this);
		loadingDialog = new LoadingDialog(this);
		pageInfo = new PageInfo();
		refreshListView = (PullToRefreshListView) findViewById(R.id.score_detail_listview);
		refreshListView.setOnItemClickListener(this);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setMode(Mode.BOTH);
		UserManager manager = LoginManager.getInstance().getUserManager();
		curRoleId = manager.curRoleId;
		if (curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {// 家长
			String _studentId = manager.curId;
			List<Students> list = manager.students;
			for (Students info : list) {
				if (info.getStudentId().equals(_studentId)) {
					classId = info.getClassId();
				}
			}
			// studentId="6";
		} else {
			classId = null;
			// teacherId="9";
		}
		
		handScoreUtil();
		getScoreList(false);
	}

	private void getScoreList(boolean isRefresh) {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(adpterUtil.getHandler());
			return;
		}
		if (!isRefresh) {
			loadingDialog.show();
		}
		String start = String.valueOf(pageInfo.getIndexId());
		String limit = String.valueOf(pageInfo.getPageSize());
		response.scoreListData(classId, start, limit, httpUtils, adpterUtil.getHandler());
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int id, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		Score info = adapter.getItemScore((id - 1));
		String examId = info.getExamId();
		String examName = info.getExamNo();
		String examTime = info.getExamTime();
		intent.setClass(this, ScoreDetail.class);
		intent.putExtra("examId", examId);
		intent.putExtra("examName", examName);
		intent.putExtra("examTime", examTime);
		startActivity(intent);

	}
	
	/**
	 * 处理封装handler接收数据实例化类方法
	 */

	public void handScoreUtil() {
		adpterUtil = new MyListAdpterUtil(this, refreshListView, loadingDialog,
				new ListDataCallBack() {
					@Override
					public void onCallBack(Message msg) {
						ScoreData scoredata = (ScoreData) msg.obj;
						List<Score> itmes = scoredata.getItems();
						if (scoredata != null) {
							int total_count = scoredata.getTotalCount();
							int cur_page = scoredata.getCurrentPageNo();
							HandSucessAdpter.initPageInfo( refreshListView, pageInfo, cur_page, total_count);
							CurPage = pageInfo.getCurPage();
						}

						if (itmes == null || itmes.size() == 0) {
							refreshListView.onRefreshComplete();
							showToast("暂无数据");
							return;
						}
						if (adapter == null) {
							adapter = new ScoreAdapter(getBaseContext(), itmes);
							refreshListView.setAdapter(adapter);
						}
						if (pull_start == 1) {
							refreshListView.onRefreshComplete();
							adapter.addItem(itmes, true); // 下一页数据
						} else if (pull_start == 2) {
							refreshListView.onRefreshComplete();
							adapter.addItem(itmes, false);// 请求最新数据
						}
					}
				});
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
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
			getScoreList(true);
		} else {
			// 上拉加载更多
			if (pageInfo.isLastPage()) {
				// 最后一页了
				Message message = adpterUtil.getHandler().obtainMessage();
				message.what = HandlerCode.HANDLER_LASTPAGE;
				adpterUtil.getHandler().sendMessage(message);

			} else {
				CurPage++;
				pageInfo.setCurPage(CurPage);
				pull_start = 1;
				getScoreList(true);

			}
		}

	}

}










package cn.huaxunchina.cloud.app.activity.score;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.adapter.StudentScoreAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.ScoreModel;
import cn.huaxunchina.cloud.app.imp.ScoreResponse;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.StudentScoreData;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 家长考试详情
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月5日 下午3:18:47
 * 
 */
public class ScoreDetailFragment extends BaseFragment implements
		OnClickListener {

	private TextView tvRemark = null;
	private TextView tvType = null;
	private TextView tvTime = null;
	private ListView lvScore = null;
	private ScoreResponse response;
	private String examId = "";
	private String examName = "";
	private String examTime = "";
	private String studentId = null;
	private String teacherId = null;
	private Activity activity;
	private ApplicationController applicationController;
	private LoadingDialog loadingDialog;
    private MyBackView back;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// 初始化数据请求 initdata
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
		loadingDialog = new LoadingDialog(activity);
		applicationController = (ApplicationController) activity
				.getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.score_paterarch_detail_activity,container, false);
		initView(view);
		initData();
		return view;
	}

	public void initView(View view) {
		examId = activity.getIntent().getStringExtra("examId");
		/*examName = activity.getIntent().getStringExtra("examName");
		examTime = activity.getIntent().getStringExtra("examTime");*/
		
		response = new ScoreModel();
		back=(MyBackView) view.findViewById(R.id.back);
		back.setBackText("成绩单");
		back.setAddActivty(activity);
		
		view.findViewById(R.id.submit_txt).setOnClickListener(this);
		TextView rTitle = (TextView) view.findViewById(R.id.submit_txt);
		rTitle.setText("曲线图 ");

		UserManager manager = LoginManager.getInstance().getUserManager();
		if (manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {// 角色
			studentId = manager.curId;
		} else {
			teacherId = manager.curId;
		}
		tvRemark = (TextView) view.findViewById(R.id.score_remark);
		tvType = (TextView) view.findViewById(R.id.score_type);
		tvTime = (TextView) view.findViewById(R.id.score_time);
		lvScore = (ListView) view.findViewById(R.id.score_listview);
	}

	private void initData() {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog.show();
		response.scoreDetail(examId, studentId, teacherId,applicationController.httpUtils, handler);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				StudentScoreData data = (StudentScoreData) msg.obj;
				if (data == null)
					return;
				if(data.getData().size()==0){
					Toast.makeText(activity, "没有找到成绩详细", Toast.LENGTH_SHORT).show();
				}
				StudentScoreAdapter adapter = new StudentScoreAdapter(data.getData());
				lvScore.setAdapter(adapter);
				tvTime.setText(adapter.geteTime());
				tvType.setText(adapter.geteName());
				examName = adapter.geteName();
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				showLoginDialog(activity);
			    break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit_txt:
			Intent intent = new Intent(activity, ScoreGraph.class);
			intent.putExtra("examId", examId);
			intent.putExtra("examName", examName);
			activity.startActivity(intent);
			break;
		}
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

}

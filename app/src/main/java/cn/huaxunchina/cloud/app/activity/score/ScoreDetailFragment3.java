package cn.huaxunchina.cloud.app.activity.score;


import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.adapter.ClassAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ScoreModel;
import cn.huaxunchina.cloud.app.imp.ScoreResponse;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.StudentScore;
import cn.huaxunchina.cloud.app.model.StudentScoreData;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
/**
 * 班主任成员详情
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月24日 下午4:43:04 
 */
public class ScoreDetailFragment3 extends BaseFragment{
	private GridView lvScoredetail;
	private ClassAdapter adapter;
	private ScoreResponse  response;
	private Activity activity; 
	private ApplicationController  applicationController;
	private LoadingDialog loadingDialog;
//	private TextView tvTitle;
	private String examName ="";
	private String examTime = "";
	private MyBackView back;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//初始化数据请求 initdata  
	}
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity=activity;
		loadingDialog = new LoadingDialog(activity);
		applicationController=(ApplicationController)activity.getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.classscore_activity, container,false);
		back=(MyBackView) view.findViewById(R.id.back);
//		tvTitle=(TextView)view.findViewById(R.id.title);
//		view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				activity.finish();
//			}
//		});
		response = new ScoreModel();
		initView(view);
		initData();
		return view;
	}
	
	
	public void initView(View view) {
		lvScoredetail=(GridView)view.findViewById(R.id.score_gridview);
		lvScoredetail.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int id,long arg3) {
				adapter.setSeclection(id);
//				adapter.notifyDataSetChanged();
				List<StudentScore> studentScore_list = adapter.getStudentScore(id);
				StudentScoreData studentScoreData = new StudentScoreData();
				studentScoreData.setData(studentScore_list);
				Intent intent = new Intent();
				intent.setClass(activity, ClassStudentScoreDetail.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("studentScoreData", studentScoreData);
				bundle.putString("examTime", examTime);
				intent.putExtras(bundle);
				activity.startActivity(intent);
			}
		});
	}
	
	
	private void initData(){
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		
		loadingDialog.show();
		String examId = activity.getIntent().getStringExtra("examId");
		examName=activity.getIntent().getStringExtra("examName");
		examTime=activity.getIntent().getStringExtra("examTime");
//		tvTitle.setText(examName+"");
		back.setBackText(examName+"");
		back.setAddActivty(activity);
		String teacherId = LoginManager.getInstance().getUserManager().curId;
		response.scoreDetail(examId, null, teacherId, applicationController.httpUtils, handler);
	}
	
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				StudentScoreData data = (StudentScoreData)msg.obj;
				adapter = new ClassAdapter(data.getData(),activity);
				lvScoredetail.setAdapter(adapter);
				break;
			case HandlerCode.HANDLER_NET://无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_ERROR://失败
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				break;
			case HandlerCode.HANDLER_TIME_OUT://超时
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
		};
	};

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	

}

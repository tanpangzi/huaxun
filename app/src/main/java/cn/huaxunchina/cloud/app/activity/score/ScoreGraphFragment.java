package cn.huaxunchina.cloud.app.activity.score;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.astuetz.PagerSlidingTabStrip;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.adapter.TabFragmentAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ScoreModel;
import cn.huaxunchina.cloud.app.imp.ScoreResponse;
import cn.huaxunchina.cloud.app.model.FiveScoredsDatas;
import cn.huaxunchina.cloud.app.model.FiveScoredsInfos;
import cn.huaxunchina.cloud.app.model.FiveScoresData;
import cn.huaxunchina.cloud.app.model.FiveScoresInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.ScoreUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ScoreGraphFragment extends BaseFragment {
	
	
	 
	private PagerSlidingTabStrip tabs = null;
	private ViewPager pagers = null;
	private TextView title;
	private String examId = "";
	private String examName = "";
	private String studentId = "";
	private LoadingDialog loadingDialog; 
	private ScoreResponse response;
	private ApplicationController applicationController;
	private Activity activity;
	private MyBackView back;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// 初始化数据请求 initdata
		examId = getArguments().getString("examId");
		examName = getArguments().getString("examName");
		UserManager manager = LoginManager.getInstance().getUserManager();
		studentId=manager.curId;
		//=============================================================================测试
		//examId="1011";
		//studentId="9";
		
		initData();
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
		this.applicationController = (ApplicationController) activity.getApplicationContext();
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.score_graph_layout, container,false);
		back=(MyBackView) view.findViewById(R.id.back);
		back.setBackText("曲线图");
		back.setAddActivty(activity);
//		view.findViewById(R.id.back).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				activity.finish();
//			}
//		});
		tabs = (PagerSlidingTabStrip)view.findViewById(R.id.score_graph_tabs);
		pagers= (ViewPager)view.findViewById(R.id.score_graph_pager);
//		title=(TextView)view.findViewById(R.id.title);
//		title.setText("曲线图");
		tabs.setVisibility(View.GONE);
		return view;
	}
	
	public void initData(){
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog = new LoadingDialog(activity);
		loadingDialog.show();
		response = new ScoreModel();
		response.fiveScores(studentId, examId, applicationController.httpUtils, handler);
		
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS://成功
				loadingDialog.dismiss();
				tabs.setVisibility(View.VISIBLE);
				FiveScoredsDatas data = (FiveScoredsDatas)msg.obj;
				List<FiveScoredsInfos> listdata = data.getData();
				if(listdata!=null){
				List<String> tablist = new ArrayList<String>();
				List<Fragment> fragmentlist = new ArrayList<Fragment>();
				Map<String,FiveScoredsInfos>  map = ScoreUtil.getFiveScoresInfo(data);
				tablist=ScoreUtil.getAllkey(map);
				//========================
				int tab_size = tablist.size();
				for(int t=0;t<tab_size;t++){
				ScoreGraphView fm = new ScoreGraphView();
				fragmentlist.add(fm);
				}
				//====================
				TabFragmentAdapter adapter = new TabFragmentAdapter(getFragmentManager(), fragmentlist, tablist);
				pagers.setAdapter(adapter);
				pagers.setOffscreenPageLimit(tab_size);
				tabs.setViewPager(pagers);
				tabs.setMinimumWidth(200);
				tabs.setShouldExpand(true);// 屏幕填充
				if(tab_size<2){//隐藏view
				tabs.setVisibility(View.GONE);	
				}
				
				//=====
				for(int f=0;f<tab_size;f++){
					ScoreGraphView fm = (ScoreGraphView)fragmentlist.get(f);
					if (fm.isAdded()) {
						FiveScoredsInfos newdata = map.get(tablist.get(f));
						fm.initView(newdata);//更新数据	
					}
				}
				}
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_ERROR:
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				Toast.makeText(activity, "无数据", Toast.LENGTH_LONG).show();
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

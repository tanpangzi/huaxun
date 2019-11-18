package cn.huaxunchina.cloud.app.activity.score;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.astuetz.PagerSlidingTabStrip;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.adapter.TabFragmentAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ScoreModel;
import cn.huaxunchina.cloud.app.imp.ScoreResponse;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.StudentScore;
import cn.huaxunchina.cloud.app.model.StudentScoreData;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.StudentScoreUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
/**
 *任课老师 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月19日 下午3:20:52 
 *
 */
public class ScoreDetailFragment2 extends BaseFragment {

	private PagerSlidingTabStrip tabs = null;
	private ViewPager pagers = null;
	private String examId = null;
	private String examName ="";
	private String teacherId = null;
	private ScoreResponse  response;
	private List<Fragment> fragmentlist = new ArrayList<Fragment>();
	private List<String> tablist = new ArrayList<String>();
	private List<List<StudentScore>> dataList = new ArrayList<List<StudentScore>>();
	private FragmentManager fmanager;
	private Activity activity; 
	private ApplicationController  applicationController;
	private LoadingDialog loadingDialog;
//	private TextView tvTitle;
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
		View view = inflater.inflate(R.layout.score_detail_v2_activity, container,false);
//		tvTitle=(TextView)view.findViewById(R.id.title);
		back=(MyBackView) view.findViewById(R.id.back);
//		view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				activity.finish();
//			}
//		});
		initView(view);
		initData();
		return view;
	}
	
	public void initView(View view) {
		// TODO Auto-generated method stub
		
		examId=activity.getIntent().getStringExtra("examId");
		examName=activity.getIntent().getStringExtra("examName");
//		tvTitle.setText(examName+"");
		back.setBackText(examName+"");
		back.setAddActivty(activity);
		UserManager manager = LoginManager.getInstance().getUserManager();
		teacherId=manager.curId;
		response = new ScoreModel();
		fmanager=getFragmentManager();
		tabs=(PagerSlidingTabStrip)view.findViewById(R.id.score_tabs);
		tabs.setVisibility(View.GONE);
		pagers=(ViewPager)view.findViewById(R.id.score_pager);
		
	}
	
	public void initData(){
		if(!Utils.isNetworkConn()){
			Utils.netWorkMessage(handler);
		return;
		}
		loadingDialog.show();
		response.scoreDetail(examId, null, teacherId, applicationController.httpUtils, handler);
	}
	
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				StudentScoreData data = (StudentScoreData)msg.obj;
				if(data==null) return;
				sort(data.getData());
				//ScoreDetailView
				int size = dataList.size();
				if(size<1){
					Toast.makeText(activity, "没有找到成绩详细", Toast.LENGTH_SHORT).show();
				return;
				}
				if(size>1){
				tabs.setVisibility(View.VISIBLE);	
				}
				pagers.setOffscreenPageLimit(tablist.size());//设置view的缓存个数
				for(int i=0;i<size;i++){
					ScoreDetailView sv = new ScoreDetailView();;
					fragmentlist.add(sv);
				}
				TabFragmentAdapter adapter = new TabFragmentAdapter(getFragmentManager(),fragmentlist,tablist);
				pagers.setAdapter(adapter);
				tabs.setViewPager(pagers);
				tabs.setMinimumWidth(200);
				tabs.setShouldExpand(true);//屏幕填充
				//刷新数据
				
				for(int j=0;j<size;j++){
					ScoreDetailView fm = (ScoreDetailView)fragmentlist.get(j);
					if(fm.isAdded()){
						fm.initData(dataList.get(j));
					}
				}
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
	
	//多科目的分类
	private void sort(List<StudentScore> data){
		if(data!=null)
		{
			Map<String,List<StudentScore>> map = StudentScoreUtil.getScoresMap(data);
			tablist=StudentScoreUtil.getAllkey(map);
			//dataList
			int size = tablist.size();
			for(int i=0;i<size;i++){
				dataList.add(map.get(tablist.get(i)));
			}
		 
		}
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
	
}

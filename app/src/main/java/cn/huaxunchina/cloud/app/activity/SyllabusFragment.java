package cn.huaxunchina.cloud.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;

import cn.huaxunchina.cloud.app.activity.syllabus.SyllabusView;
import cn.huaxunchina.cloud.app.adapter.TabFragmentAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.SyllabusModel;
import cn.huaxunchina.cloud.app.imp.SyllabusResponse;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Syllabus1;
import cn.huaxunchina.cloud.app.model.SyllabusData1;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.Authority;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.tools.Authority.Action;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.SyllabusDialog;
import cn.huaxunchina.cloud.app.view.SyllabusDialog.SyllabusCallBack;
import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * 课程表
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 上午8:58:32
 * 
 */
public class SyllabusFragment extends BaseFragment implements SyllabusCallBack,
		OnClickListener {

	private Activity activity;
	private PagerSlidingTabStrip tabs = null;
	private ViewPager pagers = null;
	private SyllabusView view1, view2, view3, view4, view5;
	private List<Fragment> fragmentlist = new ArrayList<Fragment>();
	private List<String> tablist = new ArrayList<String>();
	private SyllabusResponse response;
	private ApplicationController applicationController;
	private String classId = null;
	private String teacherId = null;
	private LoadingDialog loadingDialog;
	private UserManager manager;
	private TextView syllabus_title;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 初始化数据请求 initdata
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
		this.applicationController = (ApplicationController) activity.getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_syllabus_layout, container,false);
		ImageButton iBtn =(ImageButton) view.findViewById(R.id.inquiry_btn);
		iBtn.setOnClickListener(this);
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.syllabus_tabs);
		pagers = (ViewPager) view.findViewById(R.id.syllabus_pager);
		syllabus_title = (TextView) view.findViewById(R.id.syllabus_title);
		syllabus_title.setText("课程表");
		pagers.setOffscreenPageLimit(5);// 设置view的缓存个数
		response = new SyllabusModel();
		view1 = new SyllabusView();
		view2 = new SyllabusView();
		view3 = new SyllabusView();
		view4 = new SyllabusView();
		view5 = new SyllabusView();
		fragmentlist.add(view1);
		fragmentlist.add(view2);
		fragmentlist.add(view3);
		fragmentlist.add(view4);
		fragmentlist.add(view5);
		tablist.add("星期一");
		tablist.add("星期二");
		tablist.add("星期三");
		tablist.add("星期四");
		tablist.add("星期五");
		TabFragmentAdapter adapter = new TabFragmentAdapter(getFragmentManager(), fragmentlist, tablist);
		pagers.setAdapter(adapter);
		tabs.setViewPager(pagers);
		tabs.setMinimumWidth(200);
		tabs.setShouldExpand(true);// 屏幕填充

		// 判断当前角色
		manager = LoginManager.getInstance().getUserManager();
		int rId = Integer.valueOf(manager.curRoleId);
		if(rId== RolesCode.TEACHER){
			 teacherId = manager.curTeacherId;
			 classId = null;
			 iBtn.setVisibility(View.GONE);
		}else if(rId== RolesCode.HEAD_TEACHER){
			teacherId = null;
			List<ClassInfo> classlist = manager.classInfo;
			if(classlist!=null&&classlist.size()>0){
			ClassInfo info = classlist.get(0);
			if(info!=null){
			classId = info.getClassId();
			//classId = manager.curClassId;
			}
			}
			
			iBtn.setVisibility(View.VISIBLE);
		}else if(rId== RolesCode.PARENTS){
			teacherId = null;
			classId = manager.curClassId;
			iBtn.setVisibility(View.GONE);
		}
		initData(classId,teacherId);
		return view;
	}

	/**
	 * 数据初始话
	 * 
	 * @param teacherId
	 * @param clazzIds
	 */
	private void initData(final String clazzIds, final String teacherId) {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		Authority.getInstance().verification(new Action(){
			@Override
			public void doAction() {
				loadingDialog = new LoadingDialog(activity);
				loadingDialog.show();
				response.syllabusList(clazzIds, teacherId, applicationController.httpUtils, handler);
			}});

	}

	 
	
	private void updateFragment(List<Syllabus1> data[]) {
		//int size = data.length;
		if(data==null) return;
		for (int i = 0; i < 5; i++) {
			SyllabusView fm = (SyllabusView) fragmentlist.get(i);
			if (fm.isAdded()) {
				fm.initData(data[i]);
			}
		}

	}

	@SuppressLint("ShowToast")
	@Override
	public void onSelectdata(String classId) {
		initData(classId, teacherId);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.inquiry_btn:
			UserManager manager = LoginManager.getInstance().getUserManager();
			if (manager.curRoleId.equals(String.valueOf(RolesCode.TEACHER))
					|| manager.curRoleId.equals(String.valueOf(RolesCode.HEAD_TEACHER))
					|| manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {
				DisplayMetrics dm = new DisplayMetrics();
				this.getActivity().getWindowManager().getDefaultDisplay()
						.getMetrics(dm);
				int width = dm.widthPixels;
				int height = dm.heightPixels;
				SyllabusDialog diolog = new SyllabusDialog(activity, width,height);
				Window window = diolog.getWindow();
				window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
				window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
				diolog.setSyllabusCallBack(this);
				diolog.setCancelable(true);
				diolog.setCanceledOnTouchOutside(true);
				diolog.show();
			}
			break;
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:// 成功
				if (loadingDialog != null) {
				    loadingDialog.dismiss();
				}
				SyllabusData1 data = (SyllabusData1) msg.obj;
				updateFragment(data.getData());
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if (loadingDialog != null) {
					loadingDialog.dismiss();
				}
				showLoginDialog(activity);
				break;
			}
		};
	};

	@Override
	protected void loadData() {

	}
}

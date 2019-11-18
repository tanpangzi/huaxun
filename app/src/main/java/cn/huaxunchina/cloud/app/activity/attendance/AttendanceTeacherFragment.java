package cn.huaxunchina.cloud.app.activity.attendance;

import java.util.List;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.adapter.AttendanceTeacherAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.imp.AttendanceModel;
import cn.huaxunchina.cloud.app.imp.AttendanceResponse;
import cn.huaxunchina.cloud.app.model.AttendanceInfo;
import cn.huaxunchina.cloud.app.model.AttendanceInfoData;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.AttDetailDialog;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 老师角色考勤
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月14日 上午10:04:38 
 *
 */
public class AttendanceTeacherFragment extends BaseFragment implements OnClickListener{
	
	
	private Activity activity;
	private GridView gvAttendance;
	private TextView tvTitle;
  	private AttendanceTeacherAdapter adapter;
  	private AttendanceResponse  response;
	private ApplicationController applicationController;
	private LoadingDialog loadingDialog;
	private List<ClassInfo> classInfo = null;
	private String classId = null;
	private String className = "";
	private MyBackView back;
	private String select_date="";
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=activity;
		applicationController=(ApplicationController)activity.getApplication();
		response = new AttendanceModel();
		UserManager manager = LoginManager.getInstance().getUserManager();
		classInfo = manager.classInfo;
		if(classInfo!=null&&classInfo.size()>0){
			ClassInfo info = classInfo.get(0);
			classId = 	info.getClassId();
			className = info.getClassName();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = View.inflate(activity, R.layout.att_teacher_activity, null);
		gvAttendance=(GridView)view.findViewById(R.id.attendance_gridview);
		view.findViewById(R.id.att_search).setOnClickListener(this);
		back=(MyBackView) view.findViewById(R.id.back);
		back.setBackText(className);
		back.setAddActivty(activity);
		gvAttendance.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int item,
					long arg3) {
				//detailDialog();
				if(adapter!=null){
					adapter.setSeclection(item);
					AttendanceInfo info= adapter.getAttendanceInfo(item);
					detailDialog(info);
				}
			}
		});
		
		initData(null);
		return view;
	}
	
	
	
	private void detailDialog(AttendanceInfo info){
		AttDetailDialog diolog = new AttDetailDialog(activity,info,select_date);
		Window window = diolog.getWindow();
		window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置 //Gravity.BOTTOM
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		diolog.setCancelable(true);
		diolog.setCanceledOnTouchOutside(true);
		diolog.show();
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			activity.finish();
			break;
		case R.id.att_search:
			/*DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int width = dm.widthPixels;
			int height = dm.heightPixels;
			AttendanceTeacherDialog diolog = new AttendanceTeacherDialog(activity,classInfo,width,height,false);
			Window window = diolog.getWindow();
			window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置 //Gravity.BOTTOM
			window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
			diolog.setAttendanceTeacherCallBack(this);
			diolog.setCancelable(true);
			diolog.setCanceledOnTouchOutside(true);
			diolog.show();*/
			startActivityForResult(new Intent(activity, AttTime.class), ResultCode.ATT_CODE);
			break;

		}
		
	}

	/*@Override
	public void onSelectdata(String classId, String date) {
		//Toast.makeText(activity, classId+"["+date+"]", 1000).show();
		this.select_date=date;
		this.classId=classId;
		int size = classInfo.size();
		for(int i=0;i<size;i++){
			ClassInfo info = classInfo.get(i);
			if(info.getClassId().equals(classId)){
				className=info.getClassName();
			}
		}
		initData(select_date);
	}*/
	

	private void initData(String signDate){
		if(!Utils.isNetworkConn()){
			Utils.netWorkMessage(handler);
		return;
		}
		loadingDialog = new LoadingDialog(this.activity);
		loadingDialog.show();
		//classId="1";
		//signDate="2014-08-19";
		response.getAttInfoList(null, classId, signDate, applicationController.httpUtils, handler);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@SuppressLint("ShowToast")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS://成功
				loadingDialog.dismiss();
				AttendanceInfoData data =(AttendanceInfoData)msg.obj;
				List<AttendanceInfo> list = data.getData();
				if(list!=null){
				adapter = new AttendanceTeacherAdapter(list);
				gvAttendance.setAdapter(adapter);
				}
				if (list == null||list.size() == 0) {
					Toast.makeText(activity, "暂无数据", 0).show();
					return;
				}
				break;
			case HandlerCode.HANDLER_NET://无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_ERROR://错误
				loadingDialog.dismiss();
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				if(loadingDialog!=null){
					loadingDialog.dismiss();	
				}
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if(loadingDialog!=null){
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

	 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode  == ResultCode.ATT_CODE&&data!=null) {
			 /*select_date = data.getStringExtra("date");
			 classId = data.getStringExtra("classId");
			 initData(select_date);*/
			
			String date = data.getStringExtra("date");
			String class_id = data.getStringExtra("classId");
			String class_name = data.getStringExtra("className");
			this.select_date=date;
			this.classId=class_id;
			this.className=class_name;
			back.setBackText(className);
			initData(select_date);
			
			//Toast.makeText(activity, date, 0).show();
		}
	}
	
}

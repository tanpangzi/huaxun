package cn.huaxunchina.cloud.app.activity.attendance;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.view.ClassCallBack;
import cn.huaxunchina.cloud.app.view.ClassDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil.DateCallBack;
/**
 * 
 * TODO(描述)  考勤的时间选择 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年2月27日 下午2:12:10 
 *
 */
public class AttTime extends BaseActivity implements OnClickListener,DateCallBack,ClassCallBack{
	
	private String date ="";
	private DataDialogUtil dateUtil;
	private TextView attTime,attClassName;
	private List<ClassInfo> classInfo;
	private String class_id;
	private String class_name;
 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.att_teacher_time);
		initView();
	}
	
	public void initView(){
		MyBackView back=(MyBackView) findViewById(R.id.back);
		back.setBackText("查找条件");
		back.setAddActivty(this);
		findViewById(R.id.att_time_confirm).setOnClickListener(this);
		findViewById(R.id.att_time_layout).setOnClickListener(this);
		findViewById(R.id.att_classid_layout).setOnClickListener(this);
		attTime = (TextView)findViewById(R.id.att_time);
		attClassName = (TextView)findViewById(R.id.att_classid);
		dateUtil = new DataDialogUtil(this);
		dateUtil.setDateCallBack(this);
		UserManager manager = LoginManager.getInstance().getUserManager();
		classInfo = manager.classInfo;
		class_id = classInfo.get(0).getClassId();
		class_name = classInfo.get(0).getClassName();
		attClassName.setText(class_name+"");
		date = DateUtil.getCurrentTime();
		attTime.setText(date);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.att_time_confirm:
			Intent intent = new Intent(AttTime.this, AttendancePatriarchFragment.class);
			intent.putExtra("date", date);
			intent.putExtra("classId", class_id);
			intent.putExtra("className", class_name);
			setResult(ResultCode.ATT_CODE, intent);
			finish();
			break;
		case R.id.att_time_layout:
			dateUtil.getCurYearDateList(attTime);
			break;
		case R.id.att_classid_layout:
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			int width = dm.widthPixels;
			int height = dm.heightPixels;
			ClassDialog diolog = new ClassDialog(this,classInfo,width,height);
			Window window = diolog.getWindow();
			window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置 //Gravity.BOTTOM
			window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
			diolog.setClassCallBack(this);
			diolog.setCancelable(true);
			diolog.setCanceledOnTouchOutside(true);
			diolog.show();
			break;
		}
	}
	 

	@Override
	public void onSelectdata(String date) {
		this.date=date;
	}

	@Override
	public void onSelectdata(String class_id, String class_name) {
		this.class_id=class_id;
		this.class_name=class_name;
		attClassName.setText(class_name+"");
	}

}





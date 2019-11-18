package cn.huaxunchina.cloud.location.app.activity.stt;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil;
import cn.huaxunchina.cloud.location.app.view.ComplSetAlarmDialog;
import cn.huaxunchina.cloud.location.app.view.ComplSetAlarmDialog.AlarmTypeCallBack;

/**
 * 设置闹钟界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-31 下午1:30:59
 */
public class SetAlarmActivity extends BaseActivity implements AlarmTypeCallBack{
	private MyBackView back;
	private RelativeLayout alarm_relative;
	private RelativeLayout loc_timeRelative;
	private TextView alarmType;
	private DataDialogUtil dateUtil; //底部日期对话框选择工具类
    private TextView setAlarm_txt;
    private String [] Alarmtype;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_set_alarm);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		alarm_relative = (RelativeLayout) findViewById(R.id.alarm_relative);
		loc_timeRelative = (RelativeLayout) findViewById(R.id.loc_timeRelative);
		alarmType = (TextView) findViewById(R.id.loc_alarm_type);
		setAlarm_txt=(TextView) findViewById(R.id.loc_setalarm_time);
		super.findView();
	}

	@Override
	public void initView() {
		back.setAddActivty(this);
		back.setBackText("设置闹钟1");
		dateUtil=new DataDialogUtil(this);
		Alarmtype = getResources().getStringArray(R.array.loc_alarm_type);
		super.initView();
	}

	@Override
	public void bindView() {
		alarm_relative.setOnClickListener(this);
		loc_timeRelative.setOnClickListener(this);
		super.bindView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alarm_relative:
			getSetAlarmDialogContent();
			break;
		case R.id.loc_timeRelative:
			dateUtil.getCurDateList(setAlarm_txt);
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	public void onSelectdata(int setAlarmTypeIndex) {
		alarmType.setText(Alarmtype[setAlarmTypeIndex]);
	}
	
	/**
	 * 获取设置闹钟类型数据
	 */
	public void getSetAlarmDialogContent() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		ComplSetAlarmDialog diolog = new ComplSetAlarmDialog(this, width, height,Alarmtype);
		Window window = diolog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		diolog.setAlarmTypeCallBack(this);
		diolog.setCancelable(true);
		diolog.setCanceledOnTouchOutside(true);
		diolog.show();
	}

}

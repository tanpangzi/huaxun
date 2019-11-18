package cn.huaxunchina.cloud.location.app.activity.stt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.tools.Instruction;
import cn.huaxunchina.cloud.location.app.view.PathReportDialog;
import cn.huaxunchina.cloud.location.app.view.PathReportDialog.PathReportCallBack;

/**
 * 轨迹上报界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-31 下午3:53:10
 */

public class PathReportedActivity extends BaseActivity implements
		PathReportCallBack, OnCheckedChangeListener {
	private MyBackView back;
	private RelativeLayout reportTimeReltive;
	private TextView reportedTime;
	private String[] timedata;
	private TextView pathRpSaveTxt;
	private CheckBox pathReportCk;
	private EditText pathReportEd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_path_reported);
		findView();
		initView();
		bindView();

	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		reportTimeReltive = (RelativeLayout) findViewById(R.id.path_relative);
		reportedTime = (TextView) findViewById(R.id.loc_reported_time);
		pathRpSaveTxt = (TextView) findViewById(R.id.loc_path_report_txt);
		pathReportCk = (CheckBox) findViewById(R.id.path_report_ck);
		pathReportEd = (EditText) findViewById(R.id.loc_report_ed);
		super.findView();
	}

	@Override
	public void initView() {
		back.setAddActivty(this);
		back.setBackText("轨迹上报");
		timedata = getResources().getStringArray(R.array.loc_path_time);
		// pathReportEd.setText("0.5");
		super.initView();
	}

	@Override
	public void bindView() {
		reportTimeReltive.setOnClickListener(this);
		pathRpSaveTxt.setOnClickListener(this);
		pathReportCk.setOnCheckedChangeListener(this);
		pathReportCk.setChecked(true);
		super.bindView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.path_relative:
			getPathDialogContent();
			break;
		case R.id.loc_path_report_txt: // 轨迹上报
			sett();
			break;
		}

	}

	public void sett() {
		if (pathReportCk.isChecked()) {
			String ed = pathReportEd.getText().toString();
			String space_time = reportedTime.getText().toString();
			Double reportTime = 0.0; 
			int spaceTime =0;

			if (space_time.equals("请选择")) {
				showToast("请选择定时上报间隔");
				return;
			}
			if (TextUtils.isEmpty(ed)) {
				pathReportEd.setText("0.5");
				reportTime= Double.valueOf(pathReportEd.getText().toString().trim());
				spaceTime = Integer.valueOf(space_time.replace("分钟", ""));
			} else {
				if (Double.valueOf(ed) > 72 || Double.valueOf(ed) < 0.5) {
					showToast("请输入0.5至72小时之间的上报时长");
					return;
				}
				
				reportTime= Double.valueOf(ed);
				spaceTime = Integer.valueOf(space_time.replace("分钟", ""));
				if (spaceTime > (reportTime * 60)) {
					showToast("选择的上报时长须大于上报时间间隔");
					return;
				}
			}

			getOnPathReport((int) (reportTime * 60), spaceTime * 60);
		} else {
			getOffPathReport();
		}
	}

	@Override
	public void onSelectdata(int reportTimeindex) {
		reportedTime.setText(timedata[reportTimeindex]);
	}

	/**
	 * 获取上报隔间时间
	 */
	public void getPathDialogContent() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		PathReportDialog diolog = new PathReportDialog(this, width, height,
				timedata);
		Window window = diolog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		diolog.setPathReportCallBack(this);
		diolog.setCancelable(true);
		diolog.setCanceledOnTouchOutside(true);
		diolog.show();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			pathReportCk.setChecked(true);
			// showToast("轨迹上报开启");
		} else {
			pathReportCk.setChecked(false);
		}

	}

	/**
	 * 获取开启后上报时间指令
	 */
	@SuppressWarnings("static-access")
	public void getOnPathReport(int reportTime, int sapceTime) {
		Uri uri = Uri.parse("smsto:" + application.getSim());
		intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", Instruction.getLocus(sapceTime, reportTime));
		startActivity(intent);
	}

	/**
	 * 获取开启后上报时间指令
	 */
	@SuppressWarnings("static-access")
	public void getOffPathReport() {
		Uri uri = Uri.parse("smsto:" + application.getSim());
		intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", "Report*off");
		startActivity(intent);
	}

}

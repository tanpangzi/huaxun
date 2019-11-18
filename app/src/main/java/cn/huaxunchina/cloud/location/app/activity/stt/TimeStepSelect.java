package cn.huaxunchina.cloud.location.app.activity.stt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.activity.LocusActivity;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil;
import cn.huaxunchina.cloud.location.app.tools.TimeUtil;

/**
 * 时段选择 2015-1-17 下午4:22:37
 */
public class TimeStepSelect extends BaseActivity {
	private LinearLayout sta_liner;
	private LinearLayout end_liner;
	private MyBackView back;
	private DataDialogUtil dateUtil;
	private TextView sta_year_txt, end_year_txt, sat_time_txt, end_time_txt;
	private TextView sure_txt;
	private String sat_time, end_time, sta_year_time, end_year_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_step_select);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		sure_txt = (TextView) findViewById(R.id.loc_sure_txt);
		sta_liner = (LinearLayout) findViewById(R.id.sta_year_data_liner);
		end_liner = (LinearLayout) findViewById(R.id.end_year_data_liner);
		sta_year_txt = (TextView) findViewById(R.id.sta_year_data_txt);
		end_year_txt = (TextView) findViewById(R.id.end_year_data_txt);
		sat_time_txt = (TextView) findViewById(R.id.sta_time_txt);
		end_time_txt = (TextView) findViewById(R.id.end_time_txt);
		super.findView();
	}

	@Override
	public void initView() {
		dateUtil = new DataDialogUtil(this);
		back.setAddActivty(this);
		back.setBackText("时段选择");
		sta_year_txt.setText(DateUtil.getCurrentTime());
		end_year_txt.setText(DateUtil.getCurrentTime());
		sat_time_txt.setText(TimeUtil.getCurrentTime());
		end_time_txt.setText(TimeUtil.getCurrentTime());
		super.initView();
	}

	@Override
	public void bindView() {
		sure_txt.setOnClickListener(this);
		sta_liner.setOnClickListener(this);
		end_liner.setOnClickListener(this);
		sat_time_txt.setOnClickListener(this);
		end_time_txt.setOnClickListener(this);
		super.bindView();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.sta_year_data_liner:
			dateUtil.getCurYearDateList(sta_year_txt);
			break;
		case R.id.end_year_data_liner:
			dateUtil.getCurYearDateList(end_year_txt);
			break;
		case R.id.sta_time_txt:
			dateUtil.getCurDateList(sat_time_txt);
			break;
		case R.id.end_time_txt:
			dateUtil.getCurDateList(end_time_txt);
			break;
		case R.id.loc_sure_txt:
			getTime();
			break;
		}
		super.onClick(view);
	}

	private void getTime() {
		sta_year_time = sta_year_txt.getText().toString();
		end_year_time = end_year_txt.getText().toString();
		sat_time = sat_time_txt.getText().toString();
		end_time = end_time_txt.getText().toString();
		long min = TimeUtil.getDistanceMin(sta_year_time + " " + sat_time,end_year_time + " " + end_time);
		if (min < 0) {
			showToast("结束时间必须大于开始时间!");
			return;
		}
		if (min > 1440) {
			showToast("时间不能大于1天!");
			return;
		}

		if (min < 5) {
			showToast("时间必须大于5分钟!");
			return;
		}

		sta_year_time = sta_year_time.replace("-", "");
		end_year_time = end_year_time.replace("-", "");
		sat_time = sat_time.replace(":", "");
		end_time = end_time.replace(":", "");
		sta_year_time = sta_year_time + sat_time + "00";
		end_year_time = end_year_time + end_time + "00";
		Intent intent = new Intent(TimeStepSelect.this, LocusActivity.class);
		intent.putExtra("startTime", sta_year_time);
		intent.putExtra("endTime", end_year_time);
		setResult(ResultCode.LOCUS_CODE, intent);
		finish();

	}

}

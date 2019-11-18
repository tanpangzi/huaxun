package cn.huaxunchina.cloud.location.app.activity.stt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.adapter.AlarmSetAdpter;

/**
 * 闹钟设置
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-31 下午12:09:15
 */
public class AlarmSet extends BaseActivity implements OnItemClickListener {
	private ListView loc_alarm_list;
	private AlarmSetAdpter mApdter;
	private MyBackView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_alarm_set);
		findView();
		initView();
		bindView();

	}

	@Override
	public void findView() {
		loc_alarm_list = (ListView) findViewById(R.id.loc_alarm_list);
		back = (MyBackView) findViewById(R.id.back);
		super.findView();
	}

	@Override
	public void initView() {
		back.setAddActivty(this);
		back.setBackText("闹钟设置");
		if (mApdter == null) {
			mApdter = new AlarmSetAdpter(this);
			loc_alarm_list.setAdapter(mApdter);
		}
		super.initView();
	}

	@Override
	public void bindView() {
		loc_alarm_list.setOnItemClickListener(this);
		super.bindView();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=new Intent(this, SetAlarmActivity.class);
		startActivity(intent);
	}
}

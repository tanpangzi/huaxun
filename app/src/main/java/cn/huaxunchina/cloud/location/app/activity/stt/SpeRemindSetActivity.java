package cn.huaxunchina.cloud.location.app.activity.stt;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 特殊提醒设置
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-31 下午4:51:37
 */
public class SpeRemindSetActivity extends BaseActivity {
	private MyBackView back;
	private TextView RemindSaveTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_sep_remind_set);
		findView();
		initView();
		bindView();

	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		RemindSaveTxt=(TextView) findViewById(R.id.loc_spe_remind_save_txt);
		super.findView();
	}

	@Override
	public void initView() {
		back.setAddActivty(this);
		back.setBackText("特殊提醒设置");
		super.initView();
	}

	@Override
	public void bindView() {
		RemindSaveTxt.setOnClickListener(this);
		super.bindView();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loc_spe_remind_save_txt:
			
			
			break;

		default:
			break;
		}
		super.onClick(v);
	}

}

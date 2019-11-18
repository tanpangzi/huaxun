package cn.huaxunchina.cloud.location.app.activity.stt;

import android.os.Bundle;
import android.widget.ListView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.adapter.LocHelpAdpter;

/**
 * 定位服务使用帮助
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-30 下午3:57:16
 */
public class LocHelp extends BaseActivity {
	private ListView help_list;
	private LocHelpAdpter mApdter;
	private MyBackView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_help_list);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		help_list = (ListView) findViewById(R.id.loc_help_list);
		back = (MyBackView) findViewById(R.id.back);
		super.findView();
	}

	@Override
	public void initView() {
		back.setAddActivty(this);
		back.setBackText("使用帮助");
		if (mApdter == null) {
			mApdter = new LocHelpAdpter(this);
			help_list.setAdapter(mApdter);
		}
		super.initView();
	}

	@Override
	public void bindView() {
		super.bindView();
	}

}

package cn.huaxunchina.cloud.app.activity.profile;

import android.os.Bundle;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.view.MyBackView;

public class About extends BaseActivity {
	private MyBackView back;
	private TextView Version_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		back = (MyBackView) findViewById(R.id.back);
		Version_txt = (TextView) findViewById(R.id.version_txt);
		back.setBackText("关于我们");
		back.setAddActivty(this);
	    String ver_txt = ApplicationController.versionName;
		Version_txt.setText(ver_txt.subSequence(0, 3)+"."+ver_txt.subSequence(3,ver_txt.length()));
	}
}

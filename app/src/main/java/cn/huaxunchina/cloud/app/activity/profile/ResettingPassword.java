package cn.huaxunchina.cloud.app.activity.profile;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.R;

/**
 * 重置密码
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月14日 下午5:33:28
 * 
 */
public class ResettingPassword extends BaseActivity implements OnClickListener {

	private EditText edPassword = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resetting_pwd_activity);
		initView();
//		initBartoTitle("重置密码", "提交");
	}

	@Override
	public void initView() {
		super.initView();
		edPassword = (EditText) findViewById(R.id.resetting_password_ed);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_txt:// 修改密码
			Toast.makeText(context, "ok", 1000).show();
			break;
		}

	}

}

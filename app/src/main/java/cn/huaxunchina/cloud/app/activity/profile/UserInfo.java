package cn.huaxunchina.cloud.app.activity.profile;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.view.MyBackView;
/**
 * 用户基本信息
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月15日 下午4:42:16 
 *
 */
public class UserInfo extends BaseActivity implements OnClickListener{
	
	private TextView tvRole;
	private TextView tvUserName;
	private TextView tvSchool;
	private MyBackView back;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo_layout);
		initView();
	}
	
	@Override
	public void initView() {
		super.initView();
		findViewById(R.id.userinfo_up_pwd_ll).setOnClickListener(this);
		back=(MyBackView) findViewById(R.id.back);
		tvRole=(TextView)findViewById(R.id.userinfo_role_tv);
		tvUserName=(TextView)findViewById(R.id.userinfo_username_tv);
		tvSchool=(TextView)findViewById(R.id.userinfo_school_tv);
		
		back.setBackText("个人信息");
		back.setAddActivty(this);
		UserManager manager = LoginManager.getInstance().getUserManager();
		tvRole.setText(manager.curRoleName);
		tvUserName.setText(manager.userName);
		tvSchool.setText(manager.schoolName);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.userinfo_up_pwd_ll://修改密码
			goActivity(ModifyPassword.class);
			break;

		}
		
	}

}

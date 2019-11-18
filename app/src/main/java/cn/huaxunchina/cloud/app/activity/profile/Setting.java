package cn.huaxunchina.cloud.app.activity.profile;

import com.igexin.sdk.PushManager;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 消息设置
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月16日 上午11:35:40 
 *
 */
public class Setting extends BaseActivity {
	
	private CheckBox cbMsg;
	private MyBackView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
//		initBar("消息设置");
		initView();
	}
	
	@Override
	public void initView() {
		super.initView();
		cbMsg=(CheckBox)findViewById(R.id.setting_msg_ck);
		back=(MyBackView) findViewById(R.id.back);
		back.setBackText("消息设置");
		back.setAddActivty(this);
		final LoginManager manager = LoginManager.getInstance();
		String msg = manager.getIsMsg();
		if(msg.equals("")||msg.equals("YES")){
		cbMsg.setChecked(true);	
		}else{
		cbMsg.setChecked(false);	
		}
		cbMsg.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				// TODO Auto-generated method stub
				if(checked){
					manager.setMsg("YES");
					PushManager.getInstance().turnOnPush(ApplicationController.getContext());
				}else{
					manager.setMsg("NO");
					PushManager.getInstance().turnOffPush(ApplicationController.getContext());
				}
				
			}});
	}
}

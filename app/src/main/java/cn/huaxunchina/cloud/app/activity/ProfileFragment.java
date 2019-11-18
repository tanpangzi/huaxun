package cn.huaxunchina.cloud.app.activity;

import com.igexin.sdk.PushManager;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.profile.About;
import cn.huaxunchina.cloud.app.activity.profile.ChangeRole;
import cn.huaxunchina.cloud.app.activity.profile.Feedback;
import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.activity.profile.Setting;
import cn.huaxunchina.cloud.app.activity.profile.UserInfo;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.getui.GetuiReceiver;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.upgrade.VersionService;
import cn.huaxunchina.cloud.app.view.CircularImage;
import cn.huaxunchina.cloud.location.app.model.ClientAppIdUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * 个人中心
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月15日 下午12:23:55
 * 
 */
public class ProfileFragment extends Fragment implements OnClickListener {

	private Activity activity = null;
	private LinearLayout llUserInfo = null;
	private TextView llActionCenter = null;
	private LinearLayout llMsg = null;
	private LinearLayout llFeedback = null;
	private LinearLayout llUpgrade = null;
	private LinearLayout llAbout = null;
	private CircularImage imgHead = null;
	private TextView tvUpgrade;
	private UserManager manager;// 个人信息对象
	private ApplicationController application;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// 初始化数据请求 initdata
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
		application=(ApplicationController)activity.getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_profile_layout, container,
				false);
		initView(view);
		return view;
	}

	public void initView(View v) {
		// TODO Auto-generated method stub
		manager = LoginManager.getInstance().getUserManager();
		TextView title = (TextView) v.findViewById(R.id.bar_title);
		title.setText("个人中心");
		llUserInfo = (LinearLayout) v.findViewById(R.id.pro_userinfo);
		llUserInfo.setOnClickListener(this);
		llActionCenter = (TextView) v.findViewById(R.id.pro_action_center);
		String roleNum = manager.roleCount;
		if(TextUtils.isEmpty(roleNum)){
			roleNum = "0";
		}
		if (Integer.valueOf(roleNum) > 1) {
			llActionCenter.setVisibility(View.VISIBLE);
		}
		llActionCenter.setOnClickListener(this);
		llMsg = (LinearLayout) v.findViewById(R.id.pro_msg);
		llMsg.setOnClickListener(this);
		llFeedback = (LinearLayout) v.findViewById(R.id.pro_feedback);
		llFeedback.setOnClickListener(this);
		llUpgrade = (LinearLayout) v.findViewById(R.id.pro_upgrade);
		llUpgrade.setOnClickListener(this);
		llAbout = (LinearLayout) v.findViewById(R.id.pro_about);
		llAbout.setOnClickListener(this);
		v.findViewById(R.id.pro_logoff).setOnClickListener(this);
		imgHead = (CircularImage) v.findViewById(R.id.pro_head);
		imgHead.setImageResource(R.drawable.profile_avatar_default);
		tvUpgrade = (TextView) v.findViewById(R.id.pro_upgrade_arrow);
		TextView user_name = (TextView) v.findViewById(R.id.pro_username);
		UserManager manager = LoginManager.getInstance().getUserManager();
		user_name.setText(manager.userName + " " + manager.curRoleName);
		initVersion();

	}

	public void initVersion() {
		String version_name = UserUtil.getVersionName();
		if (version_name != null && !version_name.equals("")) {
			double _v = Double.valueOf(ApplicationController.versionName);
			double _v_1 = Double.valueOf(version_name);
			if (_v_1 > _v) {
				tvUpgrade.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
				tvUpgrade.setBackgroundResource(R.drawable.skin_icon_new);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.pro_userinfo:// 用户基本信息
			intent.setClass(activity, UserInfo.class);
			startActivity(intent);
			break;
		case R.id.pro_action_center:// 角色切换
			UserManager manager = LoginManager.getInstance().getUserManager();
			intent = new Intent(activity, ChangeRole.class);
			intent.putExtra("roles_type", "2");
			Bundle bundle = new Bundle();
			bundle.putSerializable("loginData", manager.loginData);
			intent.putExtras(bundle);
			startActivityForResult(intent, ResultCode.PROFILE_CODE);
			// activity.finish();
			break;
		case R.id.pro_msg:// 消息设置
			intent.setClass(activity, Setting.class);
			startActivity(intent);
			break;
		case R.id.pro_feedback:// 反馈中心
			intent.setClass(activity, Feedback.class);
			startActivity(intent);
			break;
		case R.id.pro_upgrade:// 版本升级
			intent = new Intent(activity, VersionService.class);
			intent.putExtra("type", "2");
			activity.startService(intent);
			break;
		case R.id.pro_about:// 关于我们
			intent.setClass(activity, About.class);
			startActivity(intent);
			break;
		case R.id.pro_logoff:// 注销
			// 注销推送
			PushManager.getInstance().turnOffPush(ApplicationController.getContext());
			// 清空clientid
			GetuiReceiver.updateClientId("", ApplicationController.getContext());
			ClientAppIdUtil.setLbsClientAppId("");
			//上次lbsClientAppid
			//ClientAppIdUtil.setLbsClientAppId("");
			// 清空标签
			// loginManager.setJsonTags("");
			// 清空角色信息
			PreferencesHelper pre = new PreferencesHelper(ApplicationController.getContext(), UserUtil.getUserId());
			pre.clear();
			ApplicationController.setPwd("");
			
			// 清除home阅读标记
			// UserUtil.newsClear();
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(0);
			intent.setClass(activity, Login.class);
			startActivity(intent);
			activity.finish();
			break;
		}

	}

}

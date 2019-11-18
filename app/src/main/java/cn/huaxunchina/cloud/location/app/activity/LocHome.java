package cn.huaxunchina.cloud.location.app.activity;

import java.util.ArrayList;
import java.util.List;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.getui.GetuiUtil.GetuidoActivity;
import android.content.Intent;
import android.os.Bundle;
/*import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;*/
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class LocHome extends FragmentActivity implements OnClickListener,GetuidoActivity {

	public List<Fragment> fragments = new ArrayList<Fragment>();
	private int currentTab = 0;
	private Intent intent;
	private LinearLayout navigateLayout = null;
	private static final int[] navigateSelectedIcon = {
			R.drawable.loc_home_location_activated, R.drawable.loc_home_path_activated,
			R.drawable.loc_home_message_activated,R.drawable.loc_home_set_activated };
	private static final int[] navigatNormal = { 
		    R.drawable.loc_home_location_normal,R.drawable.loc_home_path_normal, 
			R.drawable.loc_home_message_normal, R.drawable.loc_home_set_normal };

 
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.loc_home);
		initView();

	}
	 
	public void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.loc_home_location).setOnClickListener(this);
		findViewById(R.id.loc_home_locus).setOnClickListener(this);
		findViewById(R.id.loc_home_msg).setOnClickListener(this);
		findViewById(R.id.loc_home_sett).setOnClickListener(this);
		navigateLayout = (LinearLayout) findViewById(R.id.loc_tabs_navigate_container);

		// fragment初始化
		initFragment();
		 
	}
	
	@Override
	public void doActivity(Intent intent) {
		startActivity(intent);
		
	}
	LocationFragment locationFragment;
	public void initFragment() {
		// fragments.clear();
		
		 locationFragment = new LocationFragment();
		fragments.add(locationFragment);
		fragments.add(new LocusFragment());//new LocusFragment()
		fragments.add(new MsgFragment());
		fragments.add(new SttFragment());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.loc_layout_container, fragments.get(0));
		ft.commit();
		// tab背景颜色及icon的初始化
		RelativeLayout tabItem = (RelativeLayout) navigateLayout.getChildAt(0);
		TextView txt = (TextView) tabItem.getChildAt(0);
		// tabItem.setBackgroundResource(R.color.home_navigation_selected_bg);
		txt.setTextColor(getResources().getColor(R.color.home_tab_activated_bg));
		txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(navigateSelectedIcon[0]),null,null, null);
		// UserManager manager = LoginManager.getInstance().getUserManager();
		// System.out.println(manager.curId);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.loc_home_location:
			setTabChecked(0);
			break;
		case R.id.loc_home_locus:
			//setTabChecked(1);
			goActivity(LocusActivity.class);
			break;
		case R.id.loc_home_msg:
			setTabChecked(2);
			break;
		case R.id.loc_home_sett:
			setTabChecked(3);
			break;
		}

	}

	/**
	 * TODO(描述)
	 * 
	 * @param currentId
	 */
	private void setTabChecked(int currentId) {

		// icon背景的切换
		for (int i = 0; i < navigateLayout.getChildCount(); i++) {
			RelativeLayout tabItem = (RelativeLayout) navigateLayout.getChildAt(i);
			TextView txt = (TextView) tabItem.getChildAt(0);
				if (currentId == i) {
					// tabItem.setBackgroundResource(R.color.home_navigation_selected_bg);
					txt.setTextColor(getResources().getColor(R.color.home_tab_activated_bg));
					txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(navigateSelectedIcon[i]),null, null,null);
				} else {
						// tabItem.setBackgroundResource(R.color.home_navigation_bg);
						txt.setTextColor(getResources().getColor(R.color.home_tab_normal_bg));
						txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(navigatNormal[i]),null, null, null);
				}
		}
		 
		
			Fragment fragment = fragments.get(currentId);
			FragmentTransaction ft = obtainFragmentTransaction(currentId);
			getCurrentFragment().onPause(); // 暂停当前tab
			if (fragment.isAdded()) {
				fragment.onResume(); // 启动目标tab的onResume()
			} else {
				ft.add(R.id.loc_layout_container, fragment);
			}
			showTab(currentId); // 显示目标tab
			ft.commit();
		
	}

	/**
	 * 切换tab
	 * 
	 * @param idx
	 */
	private void showTab(int idx) {
		for (int i = 0; i < fragments.size(); i++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);
			if (idx == i) {
				
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commit();
		}
		currentTab = idx; // 更新目标tab为当前tab
		
		//locationFragment.test();
	}

	/**
	 * 获取一个带动画的FragmentTransaction
	 * 
	 * @param index
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		return ft;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return fragments.get(currentTab);
	}

	// activity跳转
	public void goActivity(Class<?> activity) {
		intent = new Intent();
		intent.setClass(LocHome.this, activity);
		startActivity(intent);
		overridePendingTransition(R.anim.fragment_enter_in,R.anim.fragment_enter_out);
	}
	 
}
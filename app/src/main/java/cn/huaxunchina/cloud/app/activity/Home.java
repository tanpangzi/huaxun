package cn.huaxunchina.cloud.app.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.attendance.AttendanceList;
import cn.huaxunchina.cloud.app.activity.homework.HomeWork;
import cn.huaxunchina.cloud.app.activity.interaction.HomeSchoolActivities;
import cn.huaxunchina.cloud.app.activity.leave.LeaveManage;
import cn.huaxunchina.cloud.app.activity.notice.Notice;
import cn.huaxunchina.cloud.app.activity.score.ScoreList;
import cn.huaxunchina.cloud.app.activity.todayread.TodayRead;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.FunKey;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.getui.GetuiUtil;
import cn.huaxunchina.cloud.app.getui.GetuiUtil.GetuidoActivity;
import cn.huaxunchina.cloud.app.model.Function;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.More;
import cn.huaxunchina.cloud.app.model.MoreComparator;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.Authority;
import cn.huaxunchina.cloud.app.tools.Authority.Action;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.view.MoreDialog;
import cn.huaxunchina.cloud.app.view.MoreDialog.MoreDialogCallBack;
import cn.huaxunchina.cloud.location.app.activity.LocHome;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * 主界面
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年6月30日 下午3:56:44
 * 
 */
@SuppressLint("ShowToast")
public class Home extends FragmentActivity implements OnClickListener,
		GetuidoActivity {

	public List<Fragment> fragments = new ArrayList<Fragment>();
	private int currentTab = 0;
	private Intent intent;
	private LinearLayout navigateLayout = null;
	private static final int[] navigateSelectedIcon = {
			R.drawable.home_news_activated, R.drawable.home_location_activated,
			R.drawable.home_more_activated, R.drawable.home_contacts_activated,
			R.drawable.home_profile_activated };
	private static final int[] navigatNormal = { R.drawable.home_news_normal,
			R.drawable.home_location_normal, R.drawable.home_more_normal,
			R.drawable.home_contacts_normal, R.drawable.home_profile_normal };

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.home_activity);
		initView();

	}

	@Override
	protected void onResume() {
		super.onResume();
		ApplicationController.addActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		ApplicationController.removeActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ApplicationController.removeActivity(this);
	}

	public void initView() {
		findViewById(R.id.home_news).setOnClickListener(this);
		findViewById(R.id.home_location).setOnClickListener(this);
		findViewById(R.id.home_more).setOnClickListener(this);
		findViewById(R.id.home_contacts).setOnClickListener(this);
		findViewById(R.id.home_profile).setOnClickListener(this);
		navigateLayout = (LinearLayout) findViewById(R.id.tabs_navigate_container);

		// fragment初始化
		initFragment();
		String categoryId = this.getIntent().getStringExtra("categoryId");
		String id = this.getIntent().getStringExtra("id");
		GetuiUtil.getInstance().setGetuidoActivity(this);
		GetuiUtil.getInstance().categoryType(categoryId, id, Home.this);

	}

	@Override
	public void doActivity(Intent intent) {
		startActivity(intent);
	}

	public void initFragment() {
		// fragments.clear();
		NewsFragment newsFragment = new NewsFragment();
		fragments.add(newsFragment);
		fragments.add(new SyllabusFragment());
		// fragments.add(new MoreFragment());
		fragments.add(new ContactsFragment());
		fragments.add(new ProfileFragment());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.layout_container, fragments.get(0));
		ft.commit();
		// tab背景颜色及icon的初始化
		RelativeLayout tabItem = (RelativeLayout) navigateLayout.getChildAt(0);
		TextView txt = (TextView) tabItem.getChildAt(0);
		// tabItem.setBackgroundResource(R.color.home_navigation_selected_bg);
		txt.setTextColor(getResources().getColor(R.color.home_tab_activated_bg));
		txt.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(navigateSelectedIcon[0]), null, null);
	}

	@SuppressWarnings("unused")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_news:
			setTabChecked(0);
			break;
		case R.id.home_location:
			setTabChecked(1);
			break;
		case R.id.home_more:
			setTabChecked(2);
			MoreDialog moreDiolog = new MoreDialog(this, getMore());
			Window window = moreDiolog.getWindow();
			moreDiolog.setCancelable(true);
			moreDiolog.setCanceledOnTouchOutside(true);
			moreDiolog.setMoreDialogCallBack(new MoreDialogCallBack() {
				@Override
				public void OnItemClickListener(More more) {
					// TODO Auto-generated method stub
					// 点击的item
					String funkey = more.getFunkey();
					String funid = more.getFunid();
					if (FunKey.NOTICE_LIST_APP.equals(funkey)
							|| FunKey.NOTICE_LIST_APP_ID.equals(funid)) {
						Authority.getInstance().verification(new Action() {
							@Override
							public void doAction() {
								goActivity(Notice.class);
								// goActivity(LeaveDetail1.class);
							}
						});
					} else if (FunKey.HOMEWORKLIST_APP.equals(funkey)
							|| FunKey.HOMEWORKLIST_APP_ID.equals(funid)) {
						Authority.getInstance().verification(new Action() {
							@Override
							public void doAction() {
								goActivity(HomeWork.class);
							}
						});
						// Toast.makeText(getBaseContext(), "模块升级中",
						// 1000).show();

					} else if (FunKey.DAILY_APP.equals(funkey)
							|| FunKey.DAILY_APP_ID.equals(funid)) {
						Authority.getInstance().verification(new Action() {
							@Override
							public void doAction() {
								goActivity(AttendanceList.class);
							}
						});
					} else if (FunKey.INTERACT_LIST_APP.equals(funkey)
							|| FunKey.INTERACT_LIST_APP_ID.equals(funid)) {
						Authority.getInstance().verification(new Action() {
							@Override
							public void doAction() {
								goActivity(HomeSchoolActivities.class);
							}
						});
					} else if (FunKey.APP_LEAVE_TEACHER.equals(funkey)
							|| FunKey.APP_LEAVE_TEACHER.equals(funid)) {
						Authority.getInstance().verification(new Action() {
							@Override
							public void doAction() {
								goActivity(LeaveManage.class);
							}
						});
					} else if (FunKey.APP_EXAM_PAGE.equals(funkey)
							|| FunKey.APP_EXAM_PAGE_ID.equals(funid)) {
						Authority.getInstance().verification(new Action() {
							@Override
							public void doAction() {
								goActivity(ScoreList.class);
							}
						});
					} else if (FunKey.READLIST_APP.equals(funkey)
							|| FunKey.READLIST_APP_ID.equals(funid)) {
						goActivity(TodayRead.class);
					} else {
						String imei = LoginManager.getInstance().getImei();

						UserManager manager = LoginManager.getInstance().getUserManager();
						if (RolesCode.PARENTS == Integer.valueOf(manager.curRoleId)) {
							String id = manager.curStudentId;
							List<Students> list = manager.students;
							Students info = null;
							for (Students s : list) {
								if (s.getStudentId().equals(id)) {
									info = s;
								}
							}
							if (info == null)
								return;
							if (info.isBindCard() && info.getCardType() == 2) {
								goActivity(LocHome.class);
							} else 
							{
								Toast.makeText(Home.this, R.string.lbs_eorr,Toast.LENGTH_LONG).show();
							}
						} else if (TextUtils.isEmpty(imei)||RolesCode.PARENTS != Integer.valueOf(manager.curRoleId)) {
							Toast.makeText(Home.this, R.string.lbs_eorr,Toast.LENGTH_LONG).show();

						}

					}

				}
			});
			moreDiolog.show();

			break;
		case R.id.home_contacts:
			setTabChecked(3);
			break;
		case R.id.home_profile:
			setTabChecked(4);
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
			RelativeLayout tabItem = (RelativeLayout) navigateLayout
					.getChildAt(i);
			TextView txt = (TextView) tabItem.getChildAt(0);
			if (currentId != 2) {
				if (currentId == i) {
					// tabItem.setBackgroundResource(R.color.home_navigation_selected_bg);
					txt.setTextColor(getResources().getColor(R.color.home_tab_activated_bg));
					txt.setCompoundDrawablesWithIntrinsicBounds(
							null,
							getResources().getDrawable(navigateSelectedIcon[i]),
							null, null);
				} else {
					if (i != 2) {
						// tabItem.setBackgroundResource(R.color.home_navigation_bg);
						txt.setTextColor(getResources().getColor(R.color.home_tab_normal_bg));
						txt.setCompoundDrawablesWithIntrinsicBounds(null,
								getResources().getDrawable(navigatNormal[i]),
								null, null);
					}
				}
			}
		}

		if (currentId != 2) {
			if (currentId > 2) {
				currentId = currentId - 1;
			}
			Fragment fragment = fragments.get(currentId);
			FragmentTransaction ft = obtainFragmentTransaction(currentId);
			getCurrentFragment().onPause(); // 暂停当前tab
			if (fragment.isAdded()) {
				fragment.onResume(); // 启动目标tab的onResume()
			} else {
				ft.add(R.id.layout_container, fragment);
			}
			showTab(currentId); // 显示目标tab
			ft.commit();
		}
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
		intent.setClass(Home.this, activity);
		startActivity(intent);
		overridePendingTransition(R.anim.fragment_enter_in,
				R.anim.fragment_enter_out);
		// overridePendingTransition(R.anim.push_up_in,
		// R.anim.push_up_out);界面从下至上推出
	}

	public void goActivity(Class<?> activity, String paName, String param) {
		intent = new Intent(Home.this, activity);
		intent.putExtra(paName, param);
		startActivity(intent);
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				// showLongToast(getResources().getString(R.string.again_exit));
				Toast.makeText(getBaseContext(), "再按一次退出程序", 100).show();
				exitTime = System.currentTimeMillis();
			} else {
				// System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());
				ApplicationController.exit();

			}
		}
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ResultCode.PROFILE_CODE) {
			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
			finish();
		}

	}

	public List<More> getMore() {
		List<More> m = new ArrayList<More>();
		// List<Function> funs = applicationController.getFunctions();
		List<Function> funs = UserUtil.getFunctions();
		if (funs == null)
			return new ArrayList<More>();
		int size = funs.size();
		More more = null;
		for (int i = 0; i < size; i++) {
			Function info = funs.get(i);
			String _funkey = info.getFunKey();
			String _funId = info.getFunId();
			//
			if (FunKey.NOTICE_LIST_APP.equals(_funkey)
					|| FunKey.NOTICE_LIST_APP_ID.equals(_funId)) {
				more = new More();
				more.setImg(R.drawable.more_notice);
				more.setTitle("通知资讯");
				more.setFunkey(_funkey);
				more.setFunid(_funId);
				more.setId(1);
				m.add(more);
			} else if (FunKey.HOMEWORKLIST_APP.equals(_funkey)
					|| FunKey.HOMEWORKLIST_APP_ID.equals(_funId)) {
				more = new More();
				more.setImg(R.drawable.more_homework);
				more.setTitle("家庭作业");
				more.setFunkey(_funkey);
				more.setFunid(_funId);
				more.setId(2);
				m.add(more);
			} else if (FunKey.DAILY_APP.equals(_funkey)
					|| FunKey.DAILY_APP_ID.equals(_funId)) {
				more = new More();
				more.setImg(R.drawable.more_attendance);
				more.setTitle("考勤记录");
				more.setFunkey(_funkey);
				more.setFunid(_funId);
				more.setId(3);
				m.add(more);
			} else if (FunKey.INTERACT_LIST_APP.equals(_funkey)
					|| FunKey.INTERACT_LIST_APP_ID.equals(_funId)) {
				more = new More();
				more.setImg(R.drawable.more_jxhd);
				more.setTitle("家校互动");
				more.setFunkey(_funkey);
				more.setFunid(_funId);
				more.setId(4);
				m.add(more);
			} else if (FunKey.APP_LEAVE_TEACHER.equals(_funkey)
					|| FunKey.APP_LEAVE_TEACHER_ID.equals(_funId)) {
				more = new More();
				more.setImg(R.drawable.more_qjgl);
				more.setTitle("请假管理");
				more.setFunkey(_funkey);
				more.setFunid(_funId);
				more.setId(5);
				m.add(more);
			} else if (FunKey.APP_EXAM_PAGE.equals(_funkey)
					|| FunKey.APP_EXAM_PAGE_ID.equals(_funId)) {
				more = new More();
				more.setImg(R.drawable.more_score);
				more.setTitle("成绩管理");
				more.setFunkey(_funkey);
				more.setFunid(_funId);
				more.setId(6);
				m.add(more);
			} else if (FunKey.READLIST_APP.equals(_funkey)
					|| FunKey.READLIST_APP_ID.equals(_funId)) {
				more = new More();
				more.setImg(R.drawable.more_news);
				more.setTitle("每日一读");
				more.setFunkey(_funkey);
				more.setFunid(_funId);
				more.setId(7);
				m.add(more);
			}
		}
		more = new More();
		more.setImg(R.drawable.more_location);
		more.setTitle("定位服务");
		// more.setFunkey(_funkey);
		// more.setFunid(_funId);
		more.setId(8); 
		m.add(more);

		int m_size = m.size();
		if (m_size > 0) {// 排序
			MoreComparator comparator = new MoreComparator();
			Collections.sort(m, comparator);
		}
		return m;
	}

}

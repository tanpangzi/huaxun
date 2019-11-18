package cn.huaxunchina.cloud.app.activity.notice;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.fragment.SchoolNoticeFragment;
import cn.huaxunchina.cloud.app.adapter.TabFragmentAdapter;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.view.MyBackView;

import com.astuetz.PagerSlidingTabStrip;

/**
 * 通知公告列表数据
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-7-18 上午10:48:14
 */
public class Notice extends FragmentActivity implements OnClickListener {
	private PagerSlidingTabStrip tabs; // PagerSlidingTabStrip的实例
	private ViewPager pager;
	private SchoolNoticeFragment school_notice;
	private ImageButton edit_imagebtn;
	private List<Fragment> fragmentlist = new ArrayList<Fragment>();
	private List<String> tablist = new ArrayList<String>();
	private UserManager manager;// 个人信息对象
	private String[] notice_title = { "4", "3", "2", "1" }; // '公告类型1:系统公告2:教育局公告3:校园公告4:班级公告'
	private String id;
	private MyBackView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_tab);
		findView();
		initView();
		bindView();

	}

	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		edit_imagebtn = (ImageButton) findViewById(R.id.edit_imagebtn);

	}

	public void initView() {
		back.setBackText("通知资讯");
		back.setAddActivty(this);
		manager = LoginManager.getInstance().getUserManager();
		id = manager.curId;
		if (manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))
				|| manager.curRoleId.equals(String.valueOf(RolesCode.TEACHER))) {
			edit_imagebtn.setVisibility(View.GONE);
		} else {
			edit_imagebtn.setVisibility(View.VISIBLE);
		}
		getNoticeList();

	}

	public void bindView() {
		edit_imagebtn.setOnClickListener(this);
	}

	/**
	 * 获取班级信息
	 */
	public void getNoticeList() {
		tablist.add("班级通知");
		tablist.add("校园通知");
		tablist.add("教育局通知");
		tablist.add("系统通知");
		int size = tablist.size();
		for (int i = 0; i < size; i++) {
			school_notice = new SchoolNoticeFragment();
			Bundle data = new Bundle();
			data.putString("notice_type", notice_title[i]);
			data.putString("role", manager.curRoleId);
			data.putString("id", id);
			school_notice.setArguments(data);
			fragmentlist.add(school_notice);
		}
		TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentlist, tablist);
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(3);
		tabs.setViewPager(pager);
		tabs.setMinimumWidth(200);
		tabs.setShouldExpand(true);// 屏幕填充

	}

	@Override
	public void onStart() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.refreshNoticeList");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);
		super.onStart();

	};

	@Override
	public void onDestroy() {
		unregisterReceiver(mRefreshBroadcastReceiver);
		super.onDestroy();
	}

	public BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction();
				if (action.equals("action.refreshNoticeList")) {
					SchoolNoticeFragment class_notice = (SchoolNoticeFragment) fragmentlist.get(0);
					class_notice.getStoreData(intent);

				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_imagebtn:
			Intent intent = new Intent(this, SumbitNotice.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fragment_enter_in,R.anim.fragment_enter_out);
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.new_push_left_in,R.anim.new_push_left_out);// 从右向左推出动画效果
		}
		return false;
	}
	
	
	
}

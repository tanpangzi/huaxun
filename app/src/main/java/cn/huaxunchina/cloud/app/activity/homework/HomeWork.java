package cn.huaxunchina.cloud.app.activity.homework;

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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.fragment.ClassHomeWrokFragment;
import cn.huaxunchina.cloud.app.adapter.TabFragmentAdapter;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokProperty;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.app.view.MyViewPager;

import com.astuetz.PagerSlidingTabStrip;

/**
 * 家庭作业列表界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-7-22 上午10:17:37
 */
public class HomeWork extends FragmentActivity implements OnClickListener {
	private MyViewPager pager;
	private PagerSlidingTabStrip tabs; // PagerSlidingTabStrip的实例
	private ImageButton edit_homework_btn;
	private List<Fragment> fragmentlist = new ArrayList<Fragment>();
	private List<String> tablist = new ArrayList<String>();
	private String[] className;
	private String[] classId;
	private String stu_classId;
	private UserManager manager;// 个人信息对象
	private List<ClassInfo> infos; // 班级信息数据列表
	private MyBackView back;
	private TextView AllText;
	int curCount=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_work_tab);
		findView();
		initView();
		bindView();
	}

	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = findViewById(R.id.mypager);
		edit_homework_btn = (ImageButton) findViewById(R.id.edit_imagebtn);
		AllText=(TextView) findViewById(R.id.all_txt);
	}

	public void initView() {
		back.setBackText("家庭作业");
		back.setAddActivty(this);
		manager = LoginManager.getInstance().getUserManager();
		infos = manager.classInfo;
		if (manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {
			tabs.setVisibility(View.GONE);
			edit_homework_btn.setVisibility(View.GONE);
			String studentId = manager.curId;
			List<Students> list = manager.students;
			if (list != null) {
				int size = list.size();
				for (int i = 0; i < size; i++) {
					Students student_info = list.get(i);
					if (student_info.getStudentId().equals(studentId)) {
						stu_classId = student_info.getClassId();
					}
				}
			}
			getPraentHomeWorkList(stu_classId);
		} else {
			edit_homework_btn.setVisibility(View.VISIBLE);
			getClassInfo();
			getTeacherHomeWorkList();
		}

	}

	public void bindView() {
		edit_homework_btn.setOnClickListener(this);
		AllText.setOnClickListener(this);
	}

	/**
	 * 家长角色角色获取家庭作业列表信息
	 */
	public void getPraentHomeWorkList(String stu_classId) {
		tablist.add("");
		ClassHomeWrokFragment class_home_wrok = new ClassHomeWrokFragment();
		Bundle args = new Bundle();
		args.putString("classId", stu_classId);
		class_home_wrok.setArguments(args);
		fragmentlist.add(class_home_wrok);
		TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentlist, tablist);
		pager.setAdapter(adapter);
		
		tabs.setViewPager(pager);
		
		tabs.setMinimumWidth(200);
		tabs.setShouldExpand(true);// 屏幕填充 ,设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
		
	}

	/**
	 * 老师角色角色获取家庭作业列表信息
	 */
	public void getTeacherHomeWorkList() {
		if (infos != null) {
			for (int i = 0; i < infos.size(); i++) {
				ClassHomeWrokFragment class_home_wrok = new ClassHomeWrokFragment();
				Bundle args = new Bundle();
				args.putString("classId", classId[i]);
				class_home_wrok.setArguments(args);
				fragmentlist.add(class_home_wrok);
			}
		}
		TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentlist, tablist);
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(className.length);
		tabs.setViewPager(pager);
		tabs.setMinimumWidth(200);
		tabs.setShouldExpand(true);// 屏幕填充
		if(className.length>1){
			tabs.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 教师角色获取班级信息
	 */

	public void getClassInfo() {
		if (infos != null || infos.size() > 0) {
			infos = manager.classInfo;
			className = new String[infos.size()];
			classId = new String[infos.size()];
			for (int i = 0; i < infos.size(); i++) {
				ClassInfo c_info = infos.get(i);
				className[i] = c_info.getClassName();
				classId[i] = c_info.getClassId();
				tablist.add(className[i]);
			}
		}

	}

	@Override
	protected void onStart() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.refreshHomeWrokList");
		registerReceiver(mRefreshBroadcastReceiver, intentFilter);
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mRefreshBroadcastReceiver);
		super.onDestroy();
	}

	public BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction();
				if (action.equals("action.refreshHomeWrokList")) {
					HomeWrokProperty data = (HomeWrokProperty) intent.getSerializableExtra("storeData");
					String class_id = data.getClassId();
					for (int i = 0; i < classId.length; i++) {
						if (classId[i].equals(class_id)) {
							ClassHomeWrokFragment class_home_wrok = (ClassHomeWrokFragment) fragmentlist.get(i);
							class_home_wrok.refresLocalData(data);
						}
					}

				}
			}
		}
	};
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_imagebtn: // 发布家庭作业
			Intent intent = new Intent(this, SumbitHomeWrok.class);
			startActivity(intent);
			break;
		case R.id.all_txt:
			int i = pager.getCurrentItem();
			ClassHomeWrokFragment fm = (ClassHomeWrokFragment) fragmentlist.get(i);
			fm.setAllCheck();
			break;
		default:
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

package cn.huaxunchina.cloud.app.activity;

import java.util.ArrayList;
import java.util.List;
import com.astuetz.PagerSlidingTabStrip;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.contacts.ContactsView;
import cn.huaxunchina.cloud.app.activity.contacts.HaveMessageView;
import cn.huaxunchina.cloud.app.activity.contacts.SmsContent;
import cn.huaxunchina.cloud.app.adapter.TabFragmentAdapter;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.ContactsData;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.SendSmsData;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.SmsUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * 通讯录
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月21日 下午5:53:40
 * 
 */
public class ContactsFragment extends Fragment implements OnClickListener {
	private Activity activity;
	private PagerSlidingTabStrip tabs = null;
	private ViewPager pagers = null;
	private List<Fragment> fragmentlist = new ArrayList<Fragment>();
	private List<String> tablist = new ArrayList<String>();
	private List<String> classIdList = new ArrayList<String>();
	private UserManager manager;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 初始化数据请求 initdata
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_contacts_layout, container,
				false);
		view.findViewById(R.id.sms_btn).setOnClickListener(this);
		view.findViewById(R.id.hosmes_btn).setOnClickListener(this);
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.contacts_tabs);
		pagers = (ViewPager) view.findViewById(R.id.contacts_pager);
		// =====================

		manager = LoginManager.getInstance().getUserManager();
		if (manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {// 家长角色
			List<Students> s_list = manager.students;
			if (s_list != null && s_list.size() > 0) {

				int size = s_list.size();
				for (int i = 0; i < size; i++) {
					Students s_info = s_list.get(i);
					if (s_info.getStudentId().equals(manager.curId)) {
						classIdList.add(s_info.getClassId());
					}
				}
				int c_size = classIdList.size();
				if (c_size > 0) {
					tablist.add("老师组");
					ContactsView c_view = new ContactsView();
					Bundle bundle = new Bundle();
					bundle.putString("direct", "parent");
					bundle.putString("classId", classIdList.get(0));
					c_view.setArguments(bundle);
					fragmentlist.add(c_view);
					TabFragmentAdapter adapter = new TabFragmentAdapter(
							getFragmentManager(), fragmentlist, tablist);
					pagers.setAdapter(adapter);
					tabs.setViewPager(pagers);
					tabs.setMinimumWidth(200);
					tabs.setShouldExpand(true);// 屏幕填充
				}
			}

		} else {// 老师角色

			// 如果是任课老师或者是班主任的话添加班级信息
			if (manager.curRoleId
					.equals(String.valueOf(RolesCode.HEAD_TEACHER))
					|| manager.curRoleId.equals(String
							.valueOf(RolesCode.TEACHER))) {
				List<ClassInfo> c_list = manager.classInfo;
				if (c_list != null) {
					int size = c_list.size();
					for (int i = 0; i < size; i++) {
						ClassInfo info = c_list.get(i);
						tablist.add(info.getClassName());
						classIdList.add(info.getClassId());
					}
				}

			}
			tablist.add("老师组");
			classIdList.add("#");
			// 创建Fragment
			int t_size = tablist.size();
			for (int j = 0; j < t_size; j++) {
				ContactsView c_view = new ContactsView();
				Bundle bundle = new Bundle();
				String id = classIdList.get(j);
				if (id.equals("#")) {// 老师请求老师
					bundle.putString("direct", "teacher");
					bundle.putString("classId", null);
				} else {// 老师请求家长
					bundle.putString("direct", "teacher");
					bundle.putString("classId", id);
				}
				c_view.setArguments(bundle);
				fragmentlist.add(c_view);
			}

			TabFragmentAdapter adapter = new TabFragmentAdapter(
					getFragmentManager(), fragmentlist, tablist);
			pagers.setAdapter(adapter);
			tabs.setViewPager(pagers);
			tabs.setMinimumWidth(200);
			tabs.setShouldExpand(true);// 屏幕填充
			pagers.setOffscreenPageLimit(t_size);
			// 显示tabs
			if (t_size > 1) {
				tabs.setVisibility(View.VISIBLE);
			}

		}
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sms_btn:
			Intent intent = new Intent(activity, SmsContent.class);
			getSendData(intent);
			break;
		case R.id.hosmes_btn:
			Intent hosIntent = new Intent(activity, HaveMessageView.class);
			getSendData(hosIntent);
			break;
		}
	}

	/**
	 * 获取短信息发送数据内容
	 * 
	 * @return
	 */
	private void getSendData(Intent intent) {
		SendSmsData data = null;
		if (manager.curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {
			int i = pagers.getCurrentItem();
			ContactsView fm = (ContactsView) fragmentlist.get(i);
			ContactsData c_data = fm.getContactsData();
			data = SmsUtil.creat(3, c_data.getData());
		} else {
			data = SmsUtil.creat(1, null);
		}
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", data);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}

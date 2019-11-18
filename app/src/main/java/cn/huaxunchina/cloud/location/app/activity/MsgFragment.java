package cn.huaxunchina.cloud.location.app.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.astuetz.PagerSlidingTabStrip;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.activity.Initialization;
import cn.huaxunchina.cloud.app.adapter.TabFragmentAdapter;
import cn.huaxunchina.cloud.app.view.DataDialog;
import cn.huaxunchina.cloud.app.view.MyBackView1;
import cn.huaxunchina.cloud.location.app.activity.msg.LocMessageFragment;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil;

/**
 * 消息界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-9 上午11:54:47
 */
public class MsgFragment extends BaseFragment implements Initialization,OnClickListener {
	private MyBackView1 back;
	private PagerSlidingTabStrip tab;
	private ViewPager pager;
	private List<String> tablist;
	private List<Fragment> fragmentlist;
	private int tab_list_size = 0;
	private DataDialog dataDialog;
	private Window window;
	private Activity activity;
	private View view;
	private String[] loc_message_title = { "1", "2" }; // '消息列表类型// 1:低电量特殊提醒//2:电子围栏 '
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.loc_message_tab, container, false);
		findView();
		initView();
		bindView();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void findView() {
		back = (MyBackView1) view.findViewById(R.id.back);
		tab = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		pager = (ViewPager) view.findViewById(R.id.pager);
	}

	@Override
	public void initView() {
		back.setAddActivty(getActivity());
		back.setBackText("消息列表");
		back.searchOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getCurDateList();
			}
		});
		tablist = new ArrayList<String>();
		getTabTitle();
		getTabContent();
	}

	@Override
	public void bindView() {
	}
	@Override
	public void onClick(View v) {
	}

	/**
	 * 获取tab标签主题名
	 */
	public void getTabTitle() {
		tablist.add("特殊提醒");
		tablist.add("电子围栏");
		tab_list_size = tablist.size();
	}

	/**
	 * 获取tab页面内容
	 * 
	 */
	public void getTabContent() {
		fragmentlist = new ArrayList<Fragment>();
		for (int i = 0; i < tab_list_size; i++) {
			LocMessageFragment loc_messFragment = new LocMessageFragment();
			Bundle data = new Bundle();
			data.putString("locMessageType", loc_message_title[i]);
			loc_messFragment.setArguments(data);
			fragmentlist.add(loc_messFragment);
		}
		TabFragmentAdapter adapter = new TabFragmentAdapter(getFragmentManager(), fragmentlist, tablist);
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(tab_list_size);// 显示的最多界面
		tab.setViewPager(pager);
		tab.setMinimumWidth(200);
		tab.setShouldExpand(true);// 屏幕填充
	}

	/**
	 * 查询当前年月列表数据
	 */
	public void getCurDateList() {
		dataDialog = new DataDialog.Builder(activity, true, false, false)
				.setPositiveButton("确认", new OnClickListener() {
					@Override
					public void onClick(View v) {
						 Calendar c = dataDialog.getSetCalendar();
						 String selDate=new DataDialogUtil(activity).getFormatYearMothDate(c);
						 dataDialog.dismiss();
						 int id = pager.getCurrentItem();
						 LocMessageFragment fragment = (LocMessageFragment)fragmentlist.get(id);
						 fragment.onSelectdata(selDate,2);
					}
				}).create();
		window = dataDialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		dataDialog.setCancelable(true);
		dataDialog.setCanceledOnTouchOutside(true);
		dataDialog.show();
	}

	@Override
	protected void loadData() {

	}
}

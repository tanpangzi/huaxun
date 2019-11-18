package cn.huaxunchina.cloud.location.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.activity.Initialization;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.view.MyBackView1;
import cn.huaxunchina.cloud.location.app.activity.stt.FamilyActivity;
import cn.huaxunchina.cloud.location.app.activity.stt.HidingSetActivity;
import cn.huaxunchina.cloud.location.app.activity.stt.LocHelp;
import cn.huaxunchina.cloud.location.app.activity.stt.PathReportedActivity;
import cn.huaxunchina.cloud.location.app.activity.stt.WhiteListActivity;
import cn.huaxunchina.cloud.location.app.activity.stt.crawl.FencingActivity;
import cn.huaxunchina.cloud.location.app.adapter.LocSetListAdpter;
import cn.huaxunchina.cloud.location.app.tools.Instruction;
/**
 * 定位服务设置界面
 * @author zoupeng@huaxunchina.cn
 *
 * 2015-1-8 上午11:10:23
 */

public class SttFragment extends BaseFragment implements Initialization,OnItemClickListener {
	private Activity activity;
	private View view;
	private ListView setting_list;
	private LocSetListAdpter mAdpter;
	private String[] loc_setStr = { "" };
	private int ImageResoure[] = { R.drawable.electronic_fence,R.drawable.the_family, R.drawable.hide_set,
			R.drawable.path_report,R.drawable.white_name,R.drawable.about_logo,
			R.drawable.ser_card,R.drawable.hple};
	private Intent intent;
	private MyBackView1 back;
	private ApplicationController application;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		application = (ApplicationController)activity.getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.loc_home_stt_layout, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back=(MyBackView1) view.findViewById(R.id.back);
		setting_list = (ListView) view.findViewById(R.id.loc_setting_list);
	}

	@Override
	public void initView() {
		back.setSearchGone();
		back.setAddActivty(getActivity());
		back.setBackText("设置");
		loc_setStr = getResources().getStringArray(R.array.loc_setting);
		if (mAdpter == null) {
			mAdpter = new LocSetListAdpter(activity, loc_setStr, ImageResoure);
			setting_list.setAdapter(mAdpter);
		}
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.loc_setting_space_list_item, null);
		view.setPadding(0, 10, 0, 0);
		setting_list.addFooterView(view);
	}

	@Override
	public void bindView() {
		setting_list.setOnItemClickListener(this);
	}

	@SuppressWarnings("static-access")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0: //电子围栏
			goActivity(FencingActivity.class);
			break;
		case 1://亲情号
			goActivity(FamilyActivity.class);
			break;
		case 2: //隐身设置
			goActivity(HidingSetActivity.class);
			break;
		case 3: //轨迹上报
			goActivity(PathReportedActivity.class);
			break;
		case 4: //白名单
			goActivity(WhiteListActivity.class);
			break;
		case 6: //查询终端卡余额
			Uri uri = Uri.parse("smsto:"+application.getSim());
			intent = new Intent(Intent.ACTION_SENDTO, uri);
			intent.putExtra("sms_body", Instruction.getYE());
			startActivity(intent);
			break;
		case 7: //使用帮助
			goActivity(LocHelp.class);
			break;
		}
	}
	
	
	// activity跳转
	public void goActivity(Class<?> activity) {
		intent = new Intent();
		intent.setClass(getActivity(), activity);
		startActivity(intent);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
}

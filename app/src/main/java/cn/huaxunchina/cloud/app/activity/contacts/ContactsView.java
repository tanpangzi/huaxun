package cn.huaxunchina.cloud.app.activity.contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseFragment;
import cn.huaxunchina.cloud.app.adapter.ContactsAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.imp.ContactsModel;
import cn.huaxunchina.cloud.app.imp.ContactsResponse;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.model.ContactsComparator;
import cn.huaxunchina.cloud.app.model.ContactsData;
import cn.huaxunchina.cloud.app.model.ContactsInfo;
import cn.huaxunchina.cloud.app.tools.Authority;
import cn.huaxunchina.cloud.app.tools.MyListAdpterUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.tools.Authority.Action;
import cn.huaxunchina.cloud.app.view.LoadingDialog;

import com.example.sortlistview.CharacterParser;
import com.example.sortlistview.SideBar;
import com.example.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

/**
 * 通讯录
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月20日 下午3:52:36
 * 
 */
public class ContactsView extends BaseFragment implements
		OnRefreshListener<ListView> {

	private Activity activity;
	private PullToRefreshListView sortListView;
	private ListView listview;
	private SideBar sideBar;
	private ContactsAdapter adapter;
	// 汉字转换成拼音的类
	private CharacterParser characterParser;
	// 根据拼音来排列ListView里面的数据类
	private LoadingDialog loadingDialog;
	private ApplicationController applicationController;
	private String direct = null;
	private String classId = null;
	private MyListAdpterUtil adpterUtil;
	// 标志位，标志已经初始化完成。
	private boolean isPrepared;

	@Override
	public void onActivityCreated(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onActivityCreated(bundle);
		// 初始化数据请求 initdata
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		applicationController = (ApplicationController) activity
				.getApplication();
		loadingDialog = new LoadingDialog(activity);
	}

	@Override
	protected void loadData() {
		if (!isPrepared || !isVisible) {
			return;
		}
		isPrepared = false;
		direct = getArguments().getString("direct");
		classId = getArguments().getString("classId");
		Authority.getInstance().verification(new Action() {
			@Override
			public void doAction() {
				handContactsViewUtil();
				updataView(direct, classId, activity, false);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.contacts_layout, container, false);
		sideBar = (SideBar) view.findViewById(R.id.contacts_sidrbar);
		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				if (adapter != null) {
					if (adapter.getCount() > 1) {
						int position = adapter.getPositionForSection(s
								.charAt(0));
						if (position != -1) {
							listview.setSelection(position);

						}
					}
				}
			}
		});

		sortListView = (PullToRefreshListView) view
				.findViewById(R.id.contacts_listview);
		listview = sortListView.getRefreshableView();
		sortListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				// Toast.makeText(activity,
				// ((SortModel)adapter.getItem(position)).getName(),
				// Toast.LENGTH_SHORT).show();

			}
		});
		sortListView.setOnRefreshListener(ContactsView.this);
		isPrepared = true;
		loadData();
		return view;
	}

	public void updataView(String direct, String classId, Activity activity,
			boolean isRefresh) {
		// 判断网络
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(adpterUtil.getHandler());
			return;
		}
		if (!isRefresh) {
			loadingDialog.show();
		}
		ContactsResponse response = new ContactsModel();
		response.getContactsList(direct, classId,applicationController.httpUtils, adpterUtil.getHandler());
	}

	private ContactsData contactsData;

	public ContactsData getContactsData() {
		return contactsData;
	}

	private void setContactsData(List<ContactsInfo> new_data) {
		ContactsData data = new ContactsData();
		data.setData(new_data);
		this.contactsData = data;
	}

	/**
	 * 处理封装handler接收数据实例化类方法
	 */

	public void handContactsViewUtil() {
		adpterUtil = new MyListAdpterUtil(activity, sortListView,
				loadingDialog, new ListDataCallBack() {
					@Override
					public void onCallBack(Message msg) {
						ContactsData data = (ContactsData) msg.obj;
						List<ContactsInfo> c_data = data.getData();
						// 字母转化
						List<ContactsInfo> new_data = filledData(c_data);
						// 排序
						ContactsComparator comparator = new ContactsComparator();
						Collections.sort(new_data, comparator);
						//
						setContactsData(new_data);
						adapter = new ContactsAdapter(activity, new_data);
						sortListView.setAdapter(adapter);
					}
				});

	}

	/**
	 * TODO(描述)
	 * 
	 * @param data
	 *            联系人信息
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	private List<ContactsInfo> filledData(List<ContactsInfo> data) {
		// 整理数据
		List<ContactsInfo> new_data = new ArrayList<ContactsInfo>();
		characterParser = CharacterParser.getInstance();
		int size = data.size();
		for (int i = 0; i < size; i++) {
			ContactsInfo info = data.get(i);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(info.getUserName());
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				info.setSortLetters(sortString.toUpperCase());
			} else {
				info.setSortLetters("#");
			}
			info.setCheck(true);
			new_data.add(info);
		}
		return new_data;
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(activity,
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// 设置最后更新时间
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
			// 下拉刷新数据
			Authority.getInstance().verification(new Action() {
				@Override
				public void doAction() {
					updataView(direct, classId, activity, false);
				}
			});
		}

	}
}

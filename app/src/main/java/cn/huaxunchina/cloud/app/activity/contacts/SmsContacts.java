package cn.huaxunchina.cloud.app.activity.contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.sortlistview.CharacterParser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.SmsContactsAdapter;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.imp.ContactsModel;
import cn.huaxunchina.cloud.app.imp.ContactsResponse;
import cn.huaxunchina.cloud.app.model.ContactsComparator;
import cn.huaxunchina.cloud.app.model.ContactsData;
import cn.huaxunchina.cloud.app.model.ContactsInfo;
import cn.huaxunchina.cloud.app.model.SendSmsItem;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;

/**
 * 选择短信发送人
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 下午5:45:46
 * 
 */
public class SmsContacts extends BaseActivity implements OnClickListener {

	private ListView llContacts;
	private SmsContactsAdapter adapter;
	private SendSmsItem sendSmsItem;
	private TextView select;
	private LinearLayout back;
	private String classId;
	private ApplicationController applicationController;
	private TextView selectNum;
	// 汉字转换成拼音的类
	private CharacterParser characterParser;
	private LoadingDialog loadingDialog;
	private TextView group_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smscontacts_activity);
		applicationController = (ApplicationController) getApplication();
		loadingDialog = new LoadingDialog(this);
		initView();
	}

	@Override
	public void initView() {
		super.initView();
		back = (LinearLayout) findViewById(R.id.sms_back);
		group_txt=(TextView) findViewById(R.id.back_txt);
		group_txt.setText("群组");
		selectNum = (TextView) findViewById(R.id.contacts_num_txt);
		llContacts = (ListView) findViewById(R.id.smscontacts_listview);
		select = (TextView) findViewById(R.id.submit_txt);
		select.setOnClickListener(this);
		back.setOnClickListener(this);
		initData();
	}

	/**
	 * 初始化列表数据
	 */
	public void initData() {
		sendSmsItem = (SendSmsItem) this.getIntent().getSerializableExtra("data");
		if(sendSmsItem!=null){
			int size = sendSmsItem.getGrupData().size();
			if (size > 0) {
				upData(sendSmsItem);
			} else {
				classId = sendSmsItem.getClassId();
				updataView("teacher", classId.equals("schoolTeacher") ? null: classId);
			}
		}
		
	}

	/**
	 * 获取联系人信息
	 * 
	 * @param direct
	 * @param classId
	 */
	public void updataView(String direct, String classId) {
		// 判断网络
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog.show();
		ContactsResponse response = new ContactsModel();
		response.getContactsList(direct, classId,
				applicationController.httpUtils, handler);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				ContactsData data = (ContactsData) msg.obj;
				sendSmsItem.setGrupData(data.getData());
				upData(sendSmsItem);
				break;
			}
		};
	};

	public void upData(SendSmsItem sendSmsItem) {
		List<ContactsInfo> c_data = sendSmsItem.getGrupData();
		// 字母转化
		List<ContactsInfo> new_data = filledData(c_data);
		// 排序
		ContactsComparator comparator = new ContactsComparator();
		Collections.sort(new_data, comparator);
		sendSmsItem.setGrupData(new_data);
		adapter = new SmsContactsAdapter(sendSmsItem, select, selectNum);
		llContacts.setAdapter(adapter);
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
		boolean isCk = sendSmsItem.isCk();
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
			
			if(isCk){
			 info.setCheck(isCk); 
			}
			
			//info.setCheck(isCheck);
			
			new_data.add(info);
		}
		return new_data;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sms_back:
			getBackData();
			break;
		}
	}

	/**
	 * 获取选择数据
	 */
	public void getBackData() {
		if(adapter!=null){
			sendSmsItem = adapter.getAdapterData();
			Intent intent = new Intent(SmsContacts.this, GroupSelectActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("data", sendSmsItem);
			intent.putExtras(bundle);
			setResult(ResultCode.SMSGROUP_CODE, intent);
			finish();
		}else{
			finish();
		}
		overridePendingTransition(R.anim.new_push_left_in,R.anim.new_push_left_out);// 从右向左推出动画效果
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			getBackData();
		}
		return false;
	}

}

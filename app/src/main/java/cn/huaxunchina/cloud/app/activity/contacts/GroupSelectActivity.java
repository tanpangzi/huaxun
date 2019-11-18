package cn.huaxunchina.cloud.app.activity.contacts;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.GroupSelectListAdpter;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.model.SendSmsData;
import cn.huaxunchina.cloud.app.model.SendSmsItem;

/**
 * 短信发送群组功能
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-2-10 下午2:39:11
 */
public class GroupSelectActivity extends BaseActivity {
	private TextView conAllText;
	private ListView group_list;
	private GroupSelectListAdpter adapter;
	private LinearLayout back;
	private SendSmsData  data;
	private TextView group_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_select);
		initView();
		initData();
	}
	
	public void initView(){
		back = (LinearLayout) findViewById(R.id.back);
		conAllText = (TextView) findViewById(R.id.constacts_all_txt);
		group_txt=(TextView) findViewById(R.id.back_txt);
		group_txt.setText("群组");
		group_list = (ListView) findViewById(R.id.group_list);
		data = (SendSmsData)this.getIntent().getSerializableExtra("data");
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		if (adapter == null) {
			adapter = new GroupSelectListAdpter(this,data,conAllText);
			group_list.setAdapter(adapter);
		}
		back.setOnClickListener(this);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.GROUP_CONTACTS_TXT:
				 
				break;
			}
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			result();
			break;
		}
	}
	
 

	 
	public void result(){
		if(adapter!=null){
			data = adapter.getAdpterData();
			Intent intent = new Intent(GroupSelectActivity.this,SmsContent.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("data", data);;
			intent.putExtras(bundle);
			setResult(ResultCode.SMSSEND_CODE, intent);
			finish();
		}else{
			finish();
		}
		overridePendingTransition(R.anim.new_push_left_in,R.anim.new_push_left_out);// 从右向左推出动画效果
		
	}

 

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == ResultCode.SMSGROUP_CODE && intent != null) {
			SendSmsItem sendSmsItem = (SendSmsItem) intent.getSerializableExtra("data");
			if(sendSmsItem!=null){
				data.add(sendSmsItem.getId(),sendSmsItem);
				adapter.setData(data);
			}
			//Toast.makeText(this, sendSmsItem.getId()+"", Toast.LENGTH_LONG).show();
			
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			result();
		}
		return false;
	}

}

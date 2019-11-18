package cn.huaxunchina.cloud.location.app.activity.msg;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.adapter.LocMessDetailAdpter;
import cn.huaxunchina.cloud.location.app.model.res.ResMessageModel;

/**
 * 消息列表详情界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-29 下午3:36:04
 */
public class LocMessageDetail extends BaseActivity implements OnClickListener {
	private ListView list;
	private LocMessDetailAdpter mAdpter;
	private ImageButton sendButton;
	private MyBackView back;
	private String[] detail_key;
	private String[] detail_value = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_mess_detail);
		findView();
		initView();
		bindView();
	}

	public void findView() {
		list = (ListView) findViewById(R.id.loc_mess_detail_list);
		sendButton = (ImageButton) findViewById(R.id.loc_send);
		back = (MyBackView) findViewById(R.id.back);
	}

	public void initView() {
		back.setAddActivty(this);
		detail_key = getResources().getStringArray(R.array.detail_key);
		intent = getIntent();
		if (intent != null) {
			ResMessageModel items = (ResMessageModel) intent.getSerializableExtra("items");
			String type = items.getCategoryId();
			if (type.equals("2")) {
				type = "低电量提醒";
				back.setBackText(type);
			} else if (type.equals("3")) {
				type = "进入围栏提醒";
				back.setBackText(type);
			} else {
				type = "已出围栏提醒";
				back.setBackText(type);
			}
			String[] stringValue = { items.getName(), items.getCardId(),
					items.getImei(), items.getTerminalType(), items.getSim(), type,
					items.getCreateTime(), items.getAddress(), items.getUrl() };
			detail_value = new String[stringValue.length];
			for (int i = 0; i < detail_value.length; i++) {
				detail_value[i] = stringValue[i];
			}
		}
		if (mAdpter == null) {
			mAdpter = new LocMessDetailAdpter(this, detail_key, detail_value);
			list.setAdapter(mAdpter);
		}
	}

	public void bindView() {
		sendButton.setOnClickListener(this);
	}

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loc_send:
			StringBuffer content=new StringBuffer();
			for (int i = 0; i < detail_key.length; i++) {
				content.append(detail_key[i] + ": "+ detail_value[i]+"\n");
			}
			Uri uri = Uri.parse("smsto:");
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
			intent.putExtra("sms_body", content.toString());
			startActivity(intent);
			break;
		default:
			break;
		}

	}

}

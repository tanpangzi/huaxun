package cn.huaxunchina.cloud.app.activity.contacts;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.imp.ContactsModel;
import cn.huaxunchina.cloud.app.imp.ContactsResponse;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.SendSmsData;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.app.tools.SmsUtil;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.app.view.MyTextWatcher;

/**
 * 编写短信内容
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 下午5:46:42
 * 
 */
public class SmsContent extends BaseActivity implements OnClickListener {
	private MyBackView back;
	private TextView edLinkman;
	private TextView tvAddlnkman;
	private LinearLayout addLayout;
	private EditText edContent;
	private TextView tvCount;
	private TextView rTitle;
	private ContactsResponse response;
	private LoadingDialog loadingDialog;
	private ApplicationController applicationController;
	private UserManager manager;
	private SendSmsData data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smscontent_activity);
		data = (SendSmsData) this.getIntent().getSerializableExtra("data");
		response = new ContactsModel();
		applicationController = (ApplicationController) getApplication();
		initBartoTitle("发送");
		initView();
		

	}

	@Override
	public void initView() {
		super.initView();
		back=(MyBackView) findViewById(R.id.back);
		back.setBackText("编辑短信");
		back.setAddActivty(this);
		manager = LoginManager.getInstance().getUserManager();
		edLinkman = (TextView) findViewById(R.id.sms_linkman_ed);
		tvAddlnkman = (TextView) findViewById(R.id.sms_addlnkman_ed);
		addLayout = (LinearLayout)findViewById(R.id.sms_addlnkman_layout);
		tvAddlnkman.setOnClickListener(this);
		edContent = (EditText) findViewById(R.id.sms_content_ed);
		tvCount = (TextView) findViewById(R.id.sms_count);
		rTitle = (TextView) findViewById(R.id.submit_txt);
		MyTextWatcher myTextWatcher = new MyTextWatcher(edContent, tvCount, 210);
		edContent.addTextChangedListener(myTextWatcher);
		edContent.setSelection(edContent.length()); // 将光标移动最后一个字符后面
		myTextWatcher.setLeftCount();
		edContent.setText(data.getContent());
		int type = data.getType();
		if (type==0) {
		addLayout.setVisibility(View.GONE);
		edLinkman.setText(data.getData()[0].getGrupName());
		}else
		{
		edLinkman.setText(data.getData()[0].getGrupName());
		} 
		//edLinkman.setText(tablist.toString().replace("[", "").replace("]", ""));
	}
	
	
	 

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.sms_addlnkman_ed:
			Intent intent = new Intent(this, GroupSelectActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
			startActivityForResult(intent,ResultCode.SMSSEND_CODE);
			break;
		case R.id.submit_txt:
			sendSms();
			break;
		}

	}

	private void sendSms() {
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		
		String nams = edLinkman.getText().toString().trim();
		if (nams == null || nams.equals("")) {
			showToast("请添加联系人!");
			return;
		}
		String content = edContent.getText().toString().trim();
		if (content == null || content.equals("")) {
			showToast("请输入短信内容!");
			return;
		}

		/*
		 * if(true){ showToast("校验完毕"); return; }
		 */
		
		//========================================添加签名
		String roleId =manager.curRoleId;
		//Toast.makeText(getBaseContext(), roleId+"", 1000).show();
		String userName = manager.userName;
		if(roleId.equals("9")||roleId.equals("10")){
			
			data.setContent(content+"--"+userName+"老师");
			
		}if(roleId.equals("6")||roleId.equals("7")||roleId.equals("8"))	
		{
			data.setContent(content);
			
		}else if(roleId.equals("11")){
		
			data.setContent(content+"--"+userName+"家长");
		}
	
        String json = SmsUtil.getSendJson(data,Integer.valueOf(manager.curRoleId),nams);
       //System.out.println("======"+json);
        //showToast(json);
        
	    loadingDialog = new LoadingDialog(SmsContent.this);
		loadingDialog.show();
		rTitle.setFocusable(false);
		response.sendSms(json, applicationController.httpUtils, handler);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
		if(resultCode == ResultCode.SMSSEND_CODE&& intent != null){
			data = (SendSmsData) intent.getSerializableExtra("data");
			String grupname = SmsUtil.getGrupNames(data);
			edLinkman.setText(grupname);
			
		}

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:// 成功
				rTitle.setFocusable(true);
				loadingDialog.dismiss();
				showToast("发送成功");
				finish();
				break;
			case HandlerCode.HANDLER_NET:// 无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_ERROR:// 出错
				rTitle.setFocusable(true);
				showToast("发送失败");
				loadingDialog.dismiss();
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				rTitle.setFocusable(true);
				loadingDialog.dismiss();
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				showLoginDialog(SmsContent.this);
			    break;
			}
		};
	};
}

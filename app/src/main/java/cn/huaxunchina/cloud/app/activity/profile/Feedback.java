package cn.huaxunchina.cloud.app.activity.profile;

import org.json.JSONException;

import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.imp.MyResponseHandler;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.app.view.MyTextWatcher;
/**
 * 反馈
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月16日 上午11:07:54 
 *
 */
public class Feedback extends BaseActivity implements OnClickListener {
	private TextView feedback_ed_text;
	private EditText edContent;
	private LoadingDialog loadingDialog;
	private ApplicationController applicationController;
	private MyBackView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_activity);
		applicationController=(ApplicationController)this.getApplication();
		initView();
		
	}
	
	@Override
	public void initView() {
		super.initView();
		back=(MyBackView) findViewById(R.id.back);
		back.setBackText("反馈");
		back.setAddActivty(this);
		initBartoTitle("提交");
		edContent=(EditText)findViewById(R.id.feedback_content_ed);
		feedback_ed_text=(TextView) findViewById(R.id.feedback_ed_text);
		MyTextWatcher myTextWatcher = new MyTextWatcher(edContent,feedback_ed_text, 100);
		edContent.addTextChangedListener(myTextWatcher);
		edContent.setSelection(edContent.length()); // 将光标移动最后一个字符后面
		myTextWatcher.setLeftCount();
		findViewById(R.id.submit_txt).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_txt:
			doFeedback();
			break;
		}
		
	}
	
	private void doFeedback(){
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		String content = edContent.getText().toString();
		if(TextUtils.isEmpty(content))
		{
			showToast("请填写意见!");
			return;
		}
		loadingDialog = new LoadingDialog(Feedback.this);
		loadingDialog.show();
		String version = "android_"+ApplicationController.versionCode;
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("content", content);
		params.put("version", version);
		String url = UserUtil.getRequestUrl()+RequestCode.FEEDBACK;
		applicationController.httpUtils.post(url, params,new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				try {
					String code = GsonUtils.getCode(json);
					if(code.equals(HttpResultStatus.SUCCESS)){//判断是否成功返回
						message.what=HandlerCode.HANDLER_SUCCESS;//成功
					    handler.sendMessage(message);	
				    }else
				    {
				    	message.what=HandlerCode.HANDLER_FAIL;//失败
					    handler.sendMessage(message);
				    }
				} catch (JSONException e) {
					e.printStackTrace();
					message.what=HandlerCode.HANDLER_ERROR;//错误
				    handler.sendMessage(message);
				}
			}}));
		
	}
	
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS://成功
				loadingDialog.dismiss();
				showToast("提交成功!");
				finish();
				break;
			case HandlerCode.HANDLER_FAIL://反馈失败
				loadingDialog.dismiss();
				showToast("提交失败!");
				break;
			case HandlerCode.HANDLER_ERROR://错误
				loadingDialog.dismiss();
				break;
			case HandlerCode.HANDLER_NET://无网络
				Utils.netWorkToast();
				break;
			case HandlerCode.HANDLER_TIME_OUT://超时
				loadingDialog.dismiss();
				break;
			}
		};
	};

}

package cn.huaxunchina.cloud.app.activity.homework;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ArrowKeyMovementMethod;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.homwork.HomeWrokImpl;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWorkDetailProperty;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokProperty;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 家庭作业详情
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-5 下午2:45:22
 */
public class HomeWrokDetail extends BaseActivity {
	private TextView content_txt, tip_txt, time_txt, course_txt, tip;
	private String HomeworkId;
	private HomeWorkDetailProperty data;
	private MyBackView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_wrok_detail);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		content_txt = (TextView) findViewById(R.id.content_txt);
		content_txt.setFocusableInTouchMode(true);
		content_txt.setFocusable(true);
		content_txt.setClickable(true);
		content_txt.setLongClickable(true);
		content_txt.setMovementMethod( ArrowKeyMovementMethod.getInstance());
		
		tip_txt = (TextView) findViewById(R.id.tip_txt);
		tip_txt.setFocusableInTouchMode(true);
		tip_txt.setFocusable(true);
		tip_txt.setClickable(true);
		tip_txt.setLongClickable(true);
		tip_txt.setMovementMethod( ArrowKeyMovementMethod.getInstance());
		
		course_txt = (TextView) findViewById(R.id.home_course);
		time_txt = (TextView) findViewById(R.id.home_detail_time_txt);
		tip = (TextView) findViewById(R.id.tip);
		super.findView();
	};

	@Override
	public void initView() {
		back.setBackText("作业详情");
		back.setAddActivty(this);
		homeImpl = new HomeWrokImpl();
		intent = getIntent();
		if (intent != null) {
			HomeworkId = intent.getStringExtra("id");
			System.out.println("取到了id:"+HomeworkId);
			if (HomeworkId.equals("")||HomeworkId==null) {
				HomeWrokProperty items = (HomeWrokProperty) intent.getSerializableExtra("items");
				content_txt.setText(items.getContent().toString());
				tip_txt.setText(replaceNull(items.getTips().toString()));
				time_txt.setText(DateUtil.getDateDetail(items.getReleaseTime().toString()));
				time_txt.setTextSize(Utils.px2dip(context, 20));
				course_txt.setText(items.getCourseName().trim() + " :");
				tip.setText("作业答案或提示 :");
			
			} else {
				getHomeWorkDetail();
			}
		}

		super.initView();
	}

	/**
	 * 获取家庭作业详情
	 */
	private void getHomeWorkDetail() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		loadingDialog = new LoadingDialog(this);
		loadingDialog.show();
		homeImpl.getSumbitHomeWrokDetail(HomeWrokDetail.this,httpUtils, handler, HomeworkId);

	}


	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				data = (HomeWorkDetailProperty) msg.obj;
				if (data != null) {
					content_txt.setText(data.getContent().toString());
					tip_txt.setText(replaceNull(data.getTips().toString()));
					time_txt.setText(DateUtil.getDateDetail(data
							.getReleaseTime().toString()));
					course_txt.setText(data.getCourseName().trim() + " :");
					tip.setText("作业答案或提示 :");
				}
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				loadingDialog.dismiss();
				showToast("请求异常");
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				loadingDialog.dismiss();
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				if(loadingDialog!=null){
					loadingDialog.dismiss();}
				showLoginDialog(HomeWrokDetail.this);
			    break;
			}

		};

	};

	@Override
	public void bindView() {
		super.bindView();
	}


}

package cn.huaxunchina.cloud.location.app.activity.stt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.model.post.ChanWhitePostModel;
import cn.huaxunchina.cloud.location.app.model.post.WhiteName;
import cn.huaxunchina.cloud.location.app.model.res.ResNickPhone;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.tools.Verification;

/**
 * 白名单
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-31 下午4:51:37
 */
public class WhiteListActivity extends BaseActivity {
	private MyBackView back;
	private TextView save_txt;
	private ApplicationController applicationController;
	private WhiteName queryWhite = null;
	private Map<String, String> ResWhiteMap;
	private LinearLayout locLiner;
	private List<EditText> nickList = new ArrayList<EditText>();
	private List<EditText> phoneList = new ArrayList<EditText>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_whitelist);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		locLiner = (LinearLayout) findViewById(R.id.loc_white_liner);
		save_txt = (TextView) findViewById(R.id.loc_save_txt);
		super.findView();
	}

	@Override
	public void initView() {
		back.setAddActivty(this);
		back.setBackText("白名单");
		initEdTextView();
		applicationController = (ApplicationController) getApplication();
		loadingDialog = new LoadingDialog(this);
		queryWhite = getWhiteData();
		initWhiteData(queryWhite);
		super.initView();
	}

	@Override
	public void bindView() {
		save_txt.setOnClickListener(this);
		super.bindView();
	}

	/**
	 * 初始化文本框视图
	 */
	private void initEdTextView() {
		for (int i = 0; i < 5; i++) {
			LinearLayout edLinearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.loc_liner_edtext, null);
			EditText nickEd = (EditText) edLinearLayout.findViewById(R.id.loc_edname);
			EditText phoneEd = (EditText) edLinearLayout.findViewById(R.id.loc_edphone);
			locLiner.addView(edLinearLayout, i);
			nickList.add(nickEd);
			phoneList.add(phoneEd);
		}

	}
	/**
	 * 添加或查询获取josn返回数据
	 */
	private void initWhiteData(final Object object) {
		if (loadingDialog != null) {
			loadingDialog.show();
		}
		new HttpHandler(loadingDialog, applicationController.httpUtils_lbs, object,
				new HttpHandlerCallBack() {
					@Override
					public void onCallBack(String json) {
						if (object.equals(queryWhite)) { // 查询
							try {
								String data = GsonUtils.getData(json);
								ResNickPhone resdata = GsonUtils.getSingleBean(data, ResNickPhone.class);
								getMapData(resdata);
							} catch (Exception e) {
								e.printStackTrace();
								showToast("白名单获取失败");
							}
						} else {
							showToast("白名单保存成功");
						}
					}

					@Override
					public void onErro() {
						if (object.equals(queryWhite)) {
							showToast("白名单获取失败");
						}else{
							showToast("白名单保存失败");
						}
					}
				});
	}

	/**
	 * 查询白名单号码josn请求数据
	 */
	private WhiteName getWhiteData() {
		WhiteName queryWhite = new WhiteName();
		queryWhite.setType("1");
		return queryWhite;
	}

	/**
	 * 添加或者修改亲情号josn数据请求接口对象实例
	 */
	public ChanWhitePostModel getReqFamliyModelData() {
		
		String m1 = phoneList.get(0).getText().toString();
		String m2 = phoneList.get(1).getText().toString();
		String m3 = phoneList.get(2).getText().toString();
		String m4 = phoneList.get(3).getText().toString();
		String m5 = phoneList.get(4).getText().toString();
		
		if(!verification(m1,1)){
			return null;
		}
		if(!verification(m2,2)){
			return null;
		}
		if(!verification(m3,3)){
			return null;
		}
		if(!verification(m4,4)){
			return null;
		}
		if(!verification(m5,5)){
			return null;
		}
		
		ChanWhitePostModel changePost= new ChanWhitePostModel();
		changePost.setType("2");
		changePost.setMobile1(m1);
		changePost.setMobile2(m2);
		changePost.setMobile3(m3);
		changePost.setMobile4(m4);
		changePost.setMobile5(m5);
		changePost.setNick1(nickList.get(0).getText().toString());
		changePost.setNick2(nickList.get(1).getText().toString());
		changePost.setNick3(nickList.get(2).getText().toString());
		changePost.setNick4(nickList.get(3).getText().toString());
		changePost.setNick5(nickList.get(4).getText().toString());
		return changePost;
	}
	
	
	private  boolean verification(String m,int num){
		boolean b = true;
		if(!TextUtils.isEmpty(m)){
			if(!Verification.isMobile(m)){
				b = false;
				showToast("号码"+num+"不是正确的手机号码或电话号码");
				return b;
			}
		}
		return b;
	}
	
	
	/**
	 * 获取列表数据list
	 * 
	 */
	public void getMapData(ResNickPhone resdata) {
		ResWhiteMap = new HashMap<String, String>();
		ResWhiteMap.put("mobile1", resdata.getMobile1());
		ResWhiteMap.put("mobile2", resdata.getMobile2());
		ResWhiteMap.put("mobile3", resdata.getMobile3());
		ResWhiteMap.put("mobile4", resdata.getMobile4());
		ResWhiteMap.put("mobile5", resdata.getMobile5());
		ResWhiteMap.put("nick1", resdata.getNick1());
		ResWhiteMap.put("nick2", resdata.getNick2());
		ResWhiteMap.put("nick3", resdata.getNick3());
		ResWhiteMap.put("nick4", resdata.getNick4());
		ResWhiteMap.put("nick5", resdata.getNick5());
		for (int i =0; i < nickList.size(); i++) {
			nickList.get(i).setText(ResWhiteMap.get("nick" + (i+1)));
		}
		for (int i =0; i < phoneList.size(); i++) {
			phoneList.get(i).setText(ResWhiteMap.get("mobile" + (i+1)));
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loc_save_txt:
			ChanWhitePostModel changeData = getReqFamliyModelData();
			if(changeData!=null){
			initWhiteData(changeData);
			}
			break;
		}
		super.onClick(v);
	}

}

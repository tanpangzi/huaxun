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
import cn.huaxunchina.cloud.location.app.model.post.QueryFamily;
import cn.huaxunchina.cloud.location.app.model.post.TheFamily;
import cn.huaxunchina.cloud.location.app.model.res.ResNickPhone;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.tools.Verification;

/**
 * 亲情号界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-31 下午4:51:37
 */
public class FamilyActivity extends BaseActivity {
	private MyBackView back;
	private TextView save_txt;
	private ApplicationController applicationController;
	private QueryFamily queryfamily = null; // 查询josn请求实例
	private TheFamily familyData = null; // 修改json请求实例
	private Map<String, String> ResFlyMap;
	private ResNickPhone resFamily = null;
	private LinearLayout locLiner;
	private EditText nickEd;
	private EditText phoneEd;
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
		save_txt = (TextView) findViewById(R.id.loc_save_txt);
		locLiner = (LinearLayout) findViewById(R.id.loc_white_liner);
		super.findView();
	}

	@Override
	public void initView() {
		loadingDialog = new LoadingDialog(this);
		applicationController = (ApplicationController) getApplication();
		back.setAddActivty(this);
		back.setBackText("亲情号");
		initEdTextView();
		queryfamily = getFamliyQueryData();
		initFamilyData(queryfamily);
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
			nickEd = (EditText) edLinearLayout.findViewById(R.id.loc_edname);
			phoneEd = (EditText) edLinearLayout.findViewById(R.id.loc_edphone);
			locLiner.addView(edLinearLayout, i);
			nickList.add(nickEd);
			phoneList.add(phoneEd);
		}

	}

	/**
	 * 添加或查询获取josn返回数据
	 */
	private void initFamilyData(final Object object) {
		new HttpHandler(loadingDialog, applicationController.httpUtils_lbs, object,
				new HttpHandlerCallBack() {
					@Override
					public void onCallBack(String json) {
						if (object.equals(queryfamily)) {
							try {
								String data = GsonUtils.getData(json);
								resFamily = GsonUtils.getSingleBean(data,ResNickPhone.class);
								getQueryResFamilyData();
							} catch (Exception e) {
								e.printStackTrace();
								showToast("亲情号获取失败");
							}
						} else {
							showToast("亲情号保存成功");
						}
					}

					@Override
					public void onErro() {
						if (object.equals(queryfamily)) {
							showToast("亲情号获取失败");
						}else{
							showToast("亲情号保存失败");
						}
						
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loc_save_txt:
			familyData = getReqFamliyModelData();
			if(familyData!=null){
			initFamilyData(familyData);
			}
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	/**
	 * 查询亲情号josn数据请求接口对象实例
	 */
	public QueryFamily getFamliyQueryData() {
		queryfamily = new QueryFamily();
		queryfamily.setType("1");
		return queryfamily;
	}

	/**
	 * 添加或者修改亲情号josn数据请求接口对象实例
	 */
	public TheFamily getReqFamliyModelData() {
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
		familyData = new TheFamily();
		familyData.setType("2");
		familyData.setMobile1(m1);
		familyData.setMobile2(m2);
		familyData.setMobile3(m3);
		familyData.setMobile4(m4);
		familyData.setMobile5(m5);
		familyData.setNick1(nickList.get(0).getText().toString());
		familyData.setNick2(nickList.get(1).getText().toString());
		familyData.setNick3(nickList.get(2).getText().toString());
		familyData.setNick4(nickList.get(3).getText().toString());
		familyData.setNick5(nickList.get(4).getText().toString());
		return familyData;
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
	 * 获取查询的亲情号数据
	 */
	public void getQueryResFamilyData() {
		
		ResFlyMap = new HashMap<String, String>();
		ResFlyMap.put("mobile1", resFamily.getMobile1());
		ResFlyMap.put("mobile2", resFamily.getMobile2());
		ResFlyMap.put("mobile3", resFamily.getMobile3());
		ResFlyMap.put("mobile4", resFamily.getMobile4());
		ResFlyMap.put("mobile5", resFamily.getMobile5());
		ResFlyMap.put("nick1", resFamily.getNick1());
		ResFlyMap.put("nick2", resFamily.getNick2());
		ResFlyMap.put("nick3", resFamily.getNick3());
		ResFlyMap.put("nick4", resFamily.getNick4());
		ResFlyMap.put("nick5", resFamily.getNick5());
		for (int i =0; i < nickList.size(); i++) {
			nickList.get(i).setText(ResFlyMap.get("nick" + (i+1)));
		}
		for (int i =0; i < phoneList.size(); i++) {
			phoneList.get(i).setText(ResFlyMap.get("mobile" + (i+1)));
		}
	}

}

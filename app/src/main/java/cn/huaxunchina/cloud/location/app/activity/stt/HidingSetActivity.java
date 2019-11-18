package cn.huaxunchina.cloud.location.app.activity.stt;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.model.post.HideSetModel;
import cn.huaxunchina.cloud.location.app.model.res.ResHideSetModel;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.tools.HideSetTool;
import cn.huaxunchina.cloud.location.app.view.TimeLayout;

/**
 * 隐身设置
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-4 上午10:04:05
 */
public class HidingSetActivity extends BaseActivity {
	private LinearLayout checkLiner;
	private MyBackView back;
	private List<CheckBox> checkboxs;
	private String[] checkBoxString;
	private TextView weekText;
	private TextView hideSetSaveText;
	private ApplicationController applicationController;
	private TimeLayout timeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hide_set);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		hideSetSaveText = (TextView) findViewById(R.id.loc_hide_set_save);
		super.findView();
	}

	@Override
	public void initView() {
		back.setAddActivty(this);
		back.setBackText("隐身设置");
		loadingDialog = new LoadingDialog(this);
		applicationController = (ApplicationController) getApplication();
		LinearLayout tlayout = (LinearLayout) findViewById(R.id.timeLayout);
		timeLayout = new TimeLayout(tlayout, this);
		checkLiner = (LinearLayout) findViewById(R.id.check_liner);
		weekText = (TextView) findViewById(R.id.set_day);
		// ======画view
		initData();
		getCheckBoxData();
		super.initView();
	}

	@Override
	public void bindView() {
		hideSetSaveText.setOnClickListener(this);
		super.bindView();
	}

	/**
	 * 添加或查询获取josn返回数据
	 */
	public void initData() {
		if (loadingDialog != null) {
			loadingDialog.show();
		}
		HideSetModel hideSetQuery = new HideSetModel();
		hideSetQuery.setType(1);
		new HttpHandler(loadingDialog, applicationController.httpUtils_lbs,
				hideSetQuery, new HttpHandlerCallBack() {
					@Override
					public void onCallBack(String json) {
						try {
							String data = GsonUtils.getData(json);
							ResHideSetModel model = GsonUtils.getSingleBean(
									data, ResHideSetModel.class);
							timeLayout.setData(model);
							setCheck(model);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onErro() {
					}
				});
	}

	/**
	 * 添加或查询获取josn返回数据
	 */
	public void setData(HideSetModel model) {
		if (loadingDialog != null) {
			loadingDialog.show();
		}
		model.setType(2);
		new HttpHandler(loadingDialog, applicationController.httpUtils_lbs,
				model, new HttpHandlerCallBack() {
					@Override
					public void onCallBack(String json) {
						showToast("设置成功");
					}

					@Override
					public void onErro() {
						showToast("设置失败");
					}
				});
	}

	/**
	 * 动态添加checkBox
	 */
	public void getCheckBoxData() {
		checkboxs = new ArrayList<CheckBox>();
		checkBoxString = getResources().getStringArray(
				R.array.loc_hide_set_checkbox);
		for (int i = 0; i < checkBoxString.length; i++) {
			LinearLayout checkboxLinearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.hide_set_grid_item, null);
			CheckBox checkBox = (CheckBox) checkboxLinearLayout.findViewById(R.id.cb);
			checkboxs.add(checkBox);
			final CheckBox ck = checkboxs.get(i);
			// 设置CheckBox的文本
			ck.setText(checkBoxString[i]);
			setCheckChange(ck);
			checkLiner.addView(checkboxLinearLayout, i);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						ck.setChecked(true);
					} else {
						ck.setChecked(false);
					}
					getSelectCheckText(ck);
				}
			});
		}

	}

	/**
	 * 获取选择的星期
	 */
	public void getSelectCheckText(CheckBox ck) {
		StringBuffer sb = new StringBuffer();
		for (CheckBox checkbox : checkboxs) {
			if (checkbox.isChecked()) {
				sb.append("周" + checkbox.getText().toString() + ",");
			}
		}
		setCheckChange(ck);
		if (sb.toString().equals("")) {
			weekText.setText("无");
		} else {
			String txt = sb.substring(0, sb.length() - 1);
			weekText.setText(txt);
		}
	}

	/**
	 * 改变check选中状态变化
	 * 
	 * @param ck
	 */
	public void setCheckChange(CheckBox ck) {
		if (ck.isChecked()) {
			ck.setBackgroundResource(R.drawable.hide_set_select);
			ck.setTextColor(getResources().getColor(R.color.white));
		} else {
			ck.setBackgroundResource(R.color.white);
			ck.setTextColor(getResources().getColor(R.color.black));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loc_hide_set_save:
			HideSetModel model = timeLayout.getHideAddModelData(checkboxs);
			if (model != null) {
				setData(model);
			}
			break;
		}
		super.onClick(v);
	}

	/**
	 * 获取接口返回的checkBox选中状态
	 */

	public void setCheck(ResHideSetModel model) {
		String onoff = model.getOnOff();
		if (!onoff.equals("")) {
			char[] mchar = onoff.toCharArray();
			HideSetTool chartool = new HideSetTool(mchar);
			for (int i = 0; i < checkboxs.size(); i++) {
				final CheckBox ck = checkboxs.get(i);
				boolean ischeck = chartool.getSelectCheck(i);
				if (ischeck) {
					ck.setChecked(true);
				}
			}
		}
	}

}

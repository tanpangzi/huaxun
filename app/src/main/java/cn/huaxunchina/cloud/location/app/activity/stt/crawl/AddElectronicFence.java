package cn.huaxunchina.cloud.location.app.activity.stt.crawl;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.model.post.Circle;
import cn.huaxunchina.cloud.location.app.model.post.FencesettingModel;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil;
import cn.huaxunchina.cloud.location.app.tools.TimeUtil;
import cn.huaxunchina.cloud.location.app.view.AddFenceNoticeTypeDialog;
import cn.huaxunchina.cloud.location.app.view.AddFenceNoticeTypeDialog.AddFenceTypeCallBack;

/**
 * 添加电子围栏信息
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-26 下午3:16:40
 */
public class AddElectronicFence extends BaseActivity implements OnClickListener, AddFenceTypeCallBack {
	private MyBackView back;
	private LinearLayout checkLiner;
	private TextView setDayTxt;
	private EditText addFenceName;
	private TextView addFenAdderTxt;
	private TextView addTimeTxt1;
	private TextView addTimeTxt2;
	private TextView addTimeTxt3;
	private TextView addTimeTxt4;
	private TextView addNoticeType;
	private EditText radiusTxt;
	private CheckBox addFenCk;
	private List<CheckBox> checkboxs;
	private String[] checkBoxString;
	private boolean[] repeatDay = new boolean[7];
	private DataDialogUtil dateUtil;
	private String[] NoticeType;
	
	private Circle circle;
	private int state = 0;//0表示添加/其他表示修改
	private LoadingDialog loadingDialog;
	private ApplicationController app;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_electronic_fence);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		loadingDialog = new LoadingDialog(this);
		app=(ApplicationController)this.getApplication();
		NoticeType = getResources().getStringArray(R.array.loc_add_fence_type);
		back = (MyBackView) findViewById(R.id.back);
		addFenceName = (EditText) findViewById(R.id.add_fence_name);
		addFenAdderTxt = (TextView) findViewById(R.id.add_fence_address);
		addTimeTxt1 = (TextView) findViewById(R.id.add_fensel_time);
		addTimeTxt2 = (TextView) findViewById(R.id.add_fensel_tim2);
		addTimeTxt3 = (TextView) findViewById(R.id.add_fenctol_time1);
		addTimeTxt4 = (TextView) findViewById(R.id.add_fenctol_time2);
		addNoticeType = (TextView) findViewById(R.id.add_notice_type);
		radiusTxt = (EditText) findViewById(R.id.radius_txt);
		addFenCk = (CheckBox) findViewById(R.id.add_fencen_ck);
		checkLiner=(LinearLayout) findViewById(R.id.fence_check_liner);
		setDayTxt=(TextView) findViewById(R.id.fence_set_day);
		findViewById(R.id.loc_hide_set_save).setOnClickListener(this);
		//===
		circle = (Circle) this.getIntent().getSerializableExtra("data");
		state=circle.getCircle_id();
		if(state==0){
		back.setBackText("添加围栏");
		}else{
		back.setBackText("围栏详情");
		}
		addFenceName.setText(circle.getPositionName());
		addFenAdderTxt.setText(circle.getPointAddress());
		addTimeTxt1.setText(TimeUtil.formatYmd(circle.getStartTime()));
		addTimeTxt2.setText(TimeUtil.formatYmd(circle.getEndTime()));
		addTimeTxt3.setText(TimeUtil.formatHm(circle.getStartTime()));
		addTimeTxt4.setText(TimeUtil.formatHm(circle.getEndTime()));
		repeatDay=getRDay(circle.getRepeatDay());
		addNoticeType.setText(NoticeType[circle.getNoticeType()]);
		radiusTxt.setText(circle.getPositionRadius()+"");
		addFenCk.setChecked(circle.isPositionAlarm());
		
		
		 System.out.println("====时间5:"+circle.getStartTime());
		 System.out.println("=====时间6:"+circle.getEndTime());
		 
		super.findView();
	}

	@Override
	public void initView() {
		back.setAddActivty(this);
		dateUtil = new DataDialogUtil(this);
		getCheckBoxData(checkLiner, setDayTxt);
		super.initView();
	}

	@Override
	public void bindView() {
		addTimeTxt1.setOnClickListener(this);
		addTimeTxt2.setOnClickListener(this);
		addTimeTxt3.setOnClickListener(this);
		addTimeTxt4.setOnClickListener(this);
		addNoticeType.setOnClickListener(this);
		super.bindView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_fensel_time:
			dateUtil.getCurYearDateList(addTimeTxt1);
			break;
		case R.id.add_fensel_tim2:
			dateUtil.getCurYearDateList(addTimeTxt2);
			break;
		case R.id.add_fenctol_time1:
			dateUtil.getCurDateList(addTimeTxt3);
			break;
		case R.id.add_fenctol_time2:
			dateUtil.getCurDateList(addTimeTxt4);
			break;
		case R.id.add_notice_type:
			//getNoticeTypeDialog(NoticeType);
			break;
		case R.id.loc_hide_set_save://保存
			save();
			break;
		}
	}

	/**
	 * 获取通知类型
	 */
	public void getNoticeTypeDialog(String[] data) {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		AddFenceNoticeTypeDialog diolog = new AddFenceNoticeTypeDialog(this,width, height, data);
		Window window = diolog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		diolog.AddFenceTypeCallBack(this);
		diolog.setCancelable(true);
		diolog.setCanceledOnTouchOutside(true);
		diolog.show();
	}

	@Override
	public void onSelectdata(int index) {
			circle.setNoticeType(index);
			addNoticeType.setText(NoticeType[index]);
	}

	/**
	 * 动态添加checkBox
	 */
	public void getCheckBoxData(LinearLayout liner, final TextView weekText) {
		checkboxs = new ArrayList<CheckBox>();
		checkBoxString = getResources().getStringArray(R.array.loc_hide_set_checkbox);
		StringBuffer day = new StringBuffer();
		for (int i = 0; i < checkBoxString.length; i++) {
			LinearLayout checkboxLinearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.hide_set_grid_item, null);
			CheckBox checkBox = (CheckBox) checkboxLinearLayout.findViewById(R.id.cb);
			checkBox.setChecked(repeatDay[i]);
			checkBox.setTag(i);
			checkboxs.add(checkBox);
			final CheckBox ck = checkboxs.get(i);
			// 设置CheckBox的文本
			ck.setText(checkBoxString[i]);
			setCheckChange(ck);
			liner.addView(checkboxLinearLayout, i);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					int id = (Integer)ck.getTag();
					if (isChecked) {
						ck.setChecked(true);
						repeatDay[id]=true;
					} else {
						ck.setChecked(false);
						repeatDay[id]=false;
					}
					getSelectCheckText(ck,weekText);
				}
			});
			
			switch (i) {
			case 0:
				if(repeatDay[i]){
					day.append("周一,");
				}
				break;
			case 1:
				if(repeatDay[i]){
					day.append("周二,");
				}
				break;
			case 2:
				if(repeatDay[i]){
					day.append("周三,");
				}
				break;
			case 3:
				if(repeatDay[i]){
					day.append("周四,");
				}
				break;
			case 4:
				if(repeatDay[i]){
					day.append("周五,");
				}
				break;
			case 5:
				if(repeatDay[i]){
					day.append("周六,");
				}
				break;
			case 6:
				if(repeatDay[i]){
					day.append("周日,");
				}
				break;
			}
		}
		
		String daystr = day.toString();
		if(daystr.length()>0){
			daystr = daystr.substring(0, daystr.length()-1);
			weekText.setText(daystr);
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

	/**
	 * 获取选择的星期
	 */
	public void getSelectCheckText(CheckBox ck,TextView weekText) {
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
	
	
	private boolean[] getRDay(int tempValue){
		   boolean[] onArrs = new boolean[7];
	        for (int i=0;i<7;i++)
	        {
	            int tempTag=1<<(6-i);
	            onArrs[i]=(tempValue & tempTag)==tempTag;
	        }
		return onArrs;
	}
	
	private int getTempValue(){
		 int tempValue=0;
	        for (int i=0;i<7;i++)
	        {
	            if (repeatDay[i])
	            {
	                int tempTag=1<<(6-i);
	                tempValue=tempValue | tempTag;
	            }
	        }
		return tempValue;
	}
	
	
	private void save(){
		
		String name = addFenceName.getText().toString();
		String adder = addFenAdderTxt.getText().toString();
				
		String sta_y_time  = addTimeTxt1.getText().toString();	
		String end_y_time = addTimeTxt2.getText().toString();
		sta_y_time=sta_y_time.replace("-", "");
		end_y_time=end_y_time.replace("-", "");
		 int stadata = Integer.parseInt(sta_y_time);
		 int enddata = Integer.parseInt(end_y_time);
		 int datacount = enddata-stadata;
		 
		 if(name==null||name.equals("")){
			 showToast("围栏名称不能为空!");
			 return; 
		 }
		 if(datacount<0){
			 showToast("结束日期不能小于开始日期!");
			 return;
		 }
		 
		 String sat_time = addTimeTxt3.getText().toString();
		 String end_time = addTimeTxt4.getText().toString();
		 sat_time=sat_time.replace(":", "");
		 end_time=end_time.replace(":", "");
		 int stime = Integer.parseInt(sat_time);
		 int etime = Integer.parseInt(end_time);
		 if(etime<stime){
			 showToast("结束时间不能小于开始时间!");
			 return;
		 }
		 int timecount = etime-stime;
		 if(timecount<=4){
			 showToast("时间必须大于5分钟!");
			 return;
		 }
		 sta_y_time=sta_y_time+sat_time;
		 end_y_time=end_y_time+end_time;
		 int tempValue = getTempValue();
		 String radius = radiusTxt.getText().toString();
		 if(TextUtils.isEmpty(radius)){
			 showToast("请填写半径!");
			 return; 
		 }
		 int num_radius = Integer.parseInt(radius);
		 if(num_radius<20){
			 showToast("半径至少20米!");
			 return; 
		 }
		 if(num_radius>10000){
			 showToast("最大10000米!");
			 return; 
		 }
		 
		 boolean isOnoff = addFenCk.isChecked();
		 
		 //==================
		 circle.setPositionName(name);
		 circle.setPointAddress(adder);
		 circle.setStartTime(sta_y_time);
		 circle.setEndTime(end_y_time);
		 circle.setRepeatDay(tempValue);
		 circle.setPositionRadius(num_radius);
		 //circle.setOnoff(isOnoff);
		 circle.setPositionAlarm(isOnoff);
		FencesettingModel fen = new FencesettingModel();
		if(state==0){//2-添加 3-修改
		fen.setType(2);	
		}else{
		fen.setType(3);	
		}
		fen.setCircle(circle);
		
		String json = new Gson().toJson(fen);
		System.out.println(json);
		
		new HttpHandler(loadingDialog,app.httpUtils_lbs,fen,new HttpHandlerCallBack(){
			@Override
			public void onCallBack(String json) {
			try {
		    int id = Integer.valueOf(GsonUtils.getData(json));
			circle.setCircle_id(id);
			Intent intent = new Intent(AddElectronicFence.this,FencingActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("data", circle);
			intent.putExtras(bundle);
			setResult(ResultCode.FEN_DEL_CODE, intent);	
			finish();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			}
			@Override
			public void onErro() {
				
			}});
		 
		 
		
	}
	
	


}

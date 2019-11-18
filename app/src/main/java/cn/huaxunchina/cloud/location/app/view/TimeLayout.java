package cn.huaxunchina.cloud.location.app.view;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.model.post.HideSetModel;
import cn.huaxunchina.cloud.location.app.model.res.ResHideSetModel;
import cn.huaxunchina.cloud.location.app.tools.DataDialogUtil;

public class TimeLayout {

	private  String[] titles;
	private Context context;
	public TextView[] staList = new  TextView[3];
	public TextView[] ednList = new TextView[3];
	private LinearLayout timeLayout;
	private DataDialogUtil dateUtil;
	
	public TimeLayout(LinearLayout timeLayout,Context context) {
		this.context=context;
		this.timeLayout=timeLayout;
		dateUtil = new DataDialogUtil(context);
		init();
	}
	
	private void init(){
		titles = context.getResources().getStringArray(R.array.loc_hide_set_time);
		 int count = titles.length;
		for(int i=0;i<count;i++){
			View view  = View.inflate(context,R.layout.hide_set_list_item, null);
			TextView timeName = (TextView)view.findViewById(R.id.text_time);
			final TextView stTime = (TextView)view.findViewById(R.id.loc_hid_sta_time);
			final TextView edTime = (TextView)view.findViewById(R.id.loc_hid_end_time);
			view.findViewById(R.id.sta_layout).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dateUtil.getCurDateList(stTime);
				}
			});
			view.findViewById(R.id.end_layout).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dateUtil.getCurDateList(edTime);
				}
			});
			timeName.setText(titles[i]);
			staList[i]=stTime;
			ednList[i]=edTime;
			timeLayout.addView(view);
		}
		
	}
	
	public void setData(ResHideSetModel resHideSet){
		staList[0].setText(resHideSet.getTimeFrom1());
		staList[1].setText(resHideSet.getTimeFrom2());
		staList[2].setText(resHideSet.getTimeFrom3());
		ednList[0].setText(resHideSet.getTimeTo1());
		ednList[1].setText(resHideSet.getTimeTo2());
		ednList[2].setText(resHideSet.getTimeTo3());
	}
	
	/**
	 * 添加或者修改亲情号数据对象实例
	 */
	public HideSetModel getHideAddModelData(List<CheckBox> checkboxs) {
	 
		String st1 = staList[0].getText().toString();
		String st2 = staList[1].getText().toString();
		String st3 = staList[2].getText().toString();
		
		String ed1 = ednList[0].getText().toString();
		String ed2 = ednList[1].getText().toString();
		String ed3 = ednList[2].getText().toString();
		
		
		
		if(verification(st1,ed1,"时段1")){
			return null;
		}
		if(verification(st2,ed2,"时段2")){
			return null;
		}
		if(verification(st3,ed3,"时段3")){
			return null;
		}
		
		if(verificationStTime(st1,st2,st3)){
			return null;
		}
		
		StringBuffer appBuffer = new StringBuffer();
		for (int i = 0; i < checkboxs.size(); i++) {
			if (checkboxs.get(i).isChecked()) {
				appBuffer.append("1");
			} else {
				appBuffer.append("0");
			}
		}
		
		HideSetModel hideSetModel = new HideSetModel();
		hideSetModel.setType(2);
		hideSetModel.setOnOff(appBuffer.toString());
		hideSetModel.setTimeFrom1(st1);
		hideSetModel.setTimeFrom2(st2);
		hideSetModel.setTimeFrom3(st3);
		hideSetModel.setTimeTo1(ed1);
		hideSetModel.setTimeTo2(ed2);
		hideSetModel.setTimeTo3(ed3);
		
		return hideSetModel;
	}

	private boolean verificationStTime(String st1,String st2,String st3){
		boolean b = false;
		int ist1 = Integer.valueOf(st1.replace(":", ""));
		int ist2 = Integer.valueOf(st2.replace(":", ""));
		int ist3 = Integer.valueOf(st3.replace(":", ""));
		if(ist1==ist2&&ist1!=0){
			Toast.makeText(context, "时间1与时间2的开始时间不能相同", Toast.LENGTH_LONG).show();
			b = true;
			return b;
		}
		if(ist2==ist3&&ist2!=0){
			Toast.makeText(context, "时间2与时间3的开始时间不能相同", Toast.LENGTH_LONG).show();
			b = true;
			return b;
		}
		if(ist1==ist3&&ist1!=0){
			Toast.makeText(context, "时间1与时间3的开始时间不能相同", Toast.LENGTH_LONG).show();
			b = true;
			return b;
		}
		return b;
	}
	

	private  boolean verification(String st,String ed,String msg){
		int ist1 = Integer.valueOf(st.replace(":", ""));
		int iet1 = Integer.valueOf(ed.replace(":", ""));
		
		System.out.println(msg+":"+"["+st+"----"+ed+"]");
		
		boolean b = false;
		if(ist1==0&&iet1==0)
			return b;
		if(ist1>iet1){
			Toast.makeText(context, msg+"结束时间必须大于开始时间", Toast.LENGTH_LONG).show();
			b = true;
			return b;
		}
		if(iet1-ist1<5){
			b = true;
			Toast.makeText(context, msg+"间隔必须大于5分钟", Toast.LENGTH_LONG).show();;
			return b;
		}
		return b;
	}
	
}

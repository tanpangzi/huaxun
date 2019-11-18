package cn.huaxunchina.cloud.app.view;

import java.util.List;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.R.color;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 课程表视图
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月8日 下午3:08:24 
 *
 */
public class SyllabusItem extends LinearLayout {
	
	private LinearLayout llTime;
	private LinearLayout llItem;
	private Context context;
	private String time;
	//private List<Syllabus> listdata;
	
	public SyllabusItem(Context context) {//,String time,List<Syllabus> listdata
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		//this.time=time;
		//this.listdata=listdata;
		initView(context);
	}

	public SyllabusItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	private void initView(Context context){
		View view = View.inflate(context, R.layout.syllabus_item, null);
		llTime=(LinearLayout)view.findViewById(R.id.time_layout);
		llItem=(LinearLayout)view.findViewById(R.id.item_layout);
		//setTime(time);
		//setItem(listdata);
		this.addView(view);
		//postInvalidate();//更新ui
		//invalidate();//更新ui course
	}
	
	public void setTime(String time){
		if(llTime!=null&&time!=null){
			int length = time.length();
			for(int i=0;i<length;i++){
				String str = time.substring(i,i+1);
				TextView text = new TextView(context);
				text.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				text.setTextColor(color.white);
				text.setText(str);
				llTime.addView(text);
			}
			invalidate();//更新ui
		}
	}
	
	 

}

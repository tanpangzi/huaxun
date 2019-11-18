package cn.huaxunchina.cloud.app.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.model.ClassInfo;

import com.wheel.LocaWheelAdapter;
import com.wheel.WheelView;

public class ClassDialog extends Dialog {
	
	private static  int theme = R.style.dialog;// 主题
	private ClassCallBack classCallBack;
	private LocaWheelAdapter class_adapter = null;
	private Map<String, String> classdata_map = new HashMap<String, String>();
	private List<String> classIds = new ArrayList<String>();
	private List<String> classNames = new ArrayList<String>();
	
	private boolean scrolling = false;
	private WheelView classview = null;
	private int width, height;// 对话框宽高
	private Context context;
	private List<ClassInfo> classInfo;
	
	
	
	public ClassDialog(Context context,List<ClassInfo> classInfo,int width,int height) {
		super(context,theme);
		this.context=context;
		this.width=width;
		this.height=height;
		
		if(classInfo!=null){
		int size = classInfo.size();
		String[] classdata = new String[size];
		for(int i=0;i<size;i++){
			ClassInfo info = classInfo.get(i);
			classdata[i]= info.getClassName();//StringUtil.substring(info.getClassName(),5);
			classdata_map.put(info.getClassName(), info.getClassId());
			classIds.add(info.getClassId());
			classNames.add(info.getClassName());
		}
		class_adapter=new LocaWheelAdapter<String>(classdata,size);
		}
	}
	
	public ClassDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.att_teacher_diolog);
		LinearLayout layout = (LinearLayout) findViewById(R.id.attendance_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);
		classview=(WheelView)findViewById(R.id.attendance_class_wh);
		//班级
		if(this.classdata_map!=null){
		classview.setCurrentItem(classdata_map.size());
		classview.setCyclic(false);
		classview.setVisibleItems(5);
		classview.setAdapter(class_adapter);
		}
		
		findViewById(R.id.att_inquiry_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String classId = classIds.get(class_adapter.getIndexs());
				String className = classNames.get(class_adapter.getIndexs());
				classCallBack.onSelectdata(classId,className);
				dismiss();
				
			}
		});
	}
	
	
	
	public void setClassCallBack(ClassCallBack callBack){
		this.classCallBack=callBack;
	}
	
	 

}


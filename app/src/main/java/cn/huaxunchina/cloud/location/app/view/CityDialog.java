package cn.huaxunchina.cloud.location.app.view;


import com.wheel.LocaWheelAdapter;
import com.wheel.OnWheelChangedListener;
import com.wheel.WheelView;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.location.app.xml.CityManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * 电子围栏城市选择
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月20日 下午2:28:29 
 *
 */
public class CityDialog extends Dialog {

	private static int theme = R.style.dialog;// 主题
	private Activity activity;
	private LocaWheelAdapter province_adapter = null;
	private LocaWheelAdapter city_adapter = null;
	private WheelView province_view = null;
	private WheelView city_view = null;
	private int width, height;// 对话框宽高
	private  String[] province;
	private  String[]city;
	private CityDisplay cityDisplay;
	private static PreferencesHelper  pre;
	private String province_key = "province";
	private String city_key = "city";
	private static String city_name = "city_name";
	private int province_id = 0;
	private int city_id = 0;
	
	
	
	public CityDialog(Activity activity,int width, int height) {
		super(activity, theme);
		this.activity=activity;
		pre = new PreferencesHelper(activity,"City");
		this.width = width;
		this.height = height;
		province=CityManager.getInstance().getProvince();
		city=CityManager.getInstance().getCitys(province[0]);
		province_adapter = new LocaWheelAdapter<String>(province, province.length);
		
		//读取保存数据 
		province_id = pre.getInt(province_key);
		String province_name = province[province_id];
		city=CityManager.getInstance().getCitys(province_name);
		city_id = pre.getInt(city_key);
		city_adapter = new LocaWheelAdapter<String>(city, city.length);
	}
	
	public CityDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	public void setCityDisplay(CityDisplay cityDisplay){
		this.cityDisplay=cityDisplay;
	}
	
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_dialog);
		findViewById(R.id.al_confirm_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int curId = city_view.getCurrentItem();
				pre.setInt(city_key, curId);
				String str_city = city_adapter.getItem(curId);
				cityDisplay.onCallBackCity(str_city);
				pre.setValue(city_name, str_city);
				dismiss();
				
			}
		});
		province_view = (WheelView) findViewById(R.id.al_province);
		city_view = (WheelView) findViewById(R.id.al_city);
		province_view.setCyclic(false);
		province_view.setVisibleItems(5);
		province_view.setAdapter(province_adapter);
		province_view.setCurrentItem(province_id);
		province_view.addChangingListener(wheelListener_province);
		
		city_view.setCyclic(false);
		city_view.setVisibleItems(5);
		city_view.setAdapter(city_adapter);
		city_view.setCurrentItem(city_id);
		 
		
	}
	
	
	
	private final OnWheelChangedListener wheelListener_province = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			pre.setInt(province_key, newValue);
			String province_name = province[newValue];
			city=CityManager.getInstance().getCitys(province_name);
			city_adapter = new LocaWheelAdapter<String>(city, city.length);
			city_view.setVisibleItems(5);
			city_view.setAdapter(city_adapter);
			city_view.setCurrentItem(0);
			 
		}
	};
	
	
	public static String getCityName(Context context){
		pre = new PreferencesHelper(context,"City");
		String cname = pre.getValue(city_name);
		if(cname.equals("")||cname==null){
			cname = "通州";
		}
		return  cname;
	}
	
	public interface CityDisplay{
		public void onCallBackCity(String city);
	}
	 
	

}

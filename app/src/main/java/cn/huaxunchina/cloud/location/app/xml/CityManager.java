package cn.huaxunchina.cloud.location.app.xml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.huaxunchina.cloud.app.application.ApplicationController;

public class CityManager {
	
	private static  CityManager manager;
	private static List<CityModel> city_list;
	private static String[] province;
	private static Map<String,String[]> citymap;
	
	
	private CityManager(){
	}
	
	public static CityManager getInstance(){
		if(manager==null){
			InputStream input;
			try {
				input = ApplicationController.getContext().getAssets().open("Provinces.xml");
				PullCityParser pull = new PullCityParser();
				city_list = pull.parse(input);
				int size = city_list.size();
				province = new String[size];
				citymap = new HashMap<String, String[]>();
				for(int i=0;i<size;i++){
					CityModel model = city_list.get(i);
					String group_name = model.getGroupName();
					province[i]=group_name;
					List<CityItems> item = model.getItem();
					int city_size = item.size();
					String[] citys = new String[city_size];
					for(int j=0;j<city_size;j++){
						citys[j]=item.get(j).getCityName();
					}
					citymap.put(group_name, citys);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			manager=new CityManager();
		}
		return manager;
	}
	
	public String[] getCitys(String province){
		return citymap.get(province);
	}
	
	public String[] getProvince(){
		return province;
	}
	/**
	 * TODO(描述) 初始化默认城市
	  * @return
	 */
	public String defaultCitys(){
		return citymap.get(province[0])[0];
	}

}

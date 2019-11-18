package cn.huaxunchina.cloud.location.app.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class PullCityParser implements CityParser {
	
    
	

	@Override
	public List<CityModel> parse(InputStream is) throws Exception {
		// TODO Auto-generated method stub\
		List<CityModel> citys = null;
		CityModel model = null;
		List<CityItems> items = null;
		XmlPullParser parser = Xml.newPullParser();	//由android.util.Xml创建一个XmlPullParser实例
    	parser.setInput(is, "UTF-8");	
    	int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				citys = new ArrayList<CityModel>();
				break;
			case XmlPullParser.START_TAG:
				if (parser.getName().equals("group")) {
					model = new CityModel();
					items = new ArrayList<CityItems>();
					String g_name = parser.getAttributeValue(1);
					model.setGroupName(g_name);
				} else if (parser.getName().equals("item")) {
					CityItems itme = new CityItems();
					String g_name = parser.getAttributeValue(1);
					itme.setCityName(g_name);
					items.add(itme);
					eventType = parser.next();
				} 
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("group")) {
					model.setItem(items);
					citys.add(model);
					model = null;	
				}
				break;
			}
			eventType = parser.next();  
		}
		return citys;
	}

	@Override
	public String serialize(List<CityModel> citys) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

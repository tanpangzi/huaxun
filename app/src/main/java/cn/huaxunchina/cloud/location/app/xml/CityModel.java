package cn.huaxunchina.cloud.location.app.xml;

import java.util.List;

public class CityModel {
	
	private String groupName;
	private List<CityItems> item;
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<CityItems> getItem() {
		return item;
	}
	public void setItem(List<CityItems> item) {
		this.item = item;
	}


}

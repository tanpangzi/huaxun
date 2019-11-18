package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ContactsData implements Serializable {
	
	private List<ContactsInfo> data;
	

	public List<ContactsInfo> getData() {
		return data;
	}

	public void setData(List<ContactsInfo> data) {
		this.data = data;
	}
	
	

}

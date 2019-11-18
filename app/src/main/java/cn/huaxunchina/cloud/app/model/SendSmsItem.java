package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  群发短信item集合
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年2月11日 下午4:58:36 
 *
 */
@SuppressWarnings("serial")
public class SendSmsItem implements Serializable {
	private int id;
	private int count;
	private String classId;
	private String grupName;
	private boolean isCk = false;
	private List<ContactsInfo> grupData;
	
	public SendSmsItem(){
		grupData = new ArrayList<ContactsInfo>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public boolean isCk() {
		return isCk;
	}
	public void setCk(boolean isCk) {
		this.isCk = isCk;
	}
	public int getCount() {
		return count;
	}
	public String getCountStr() {
		String str = "";
		if(isCk){
			str="全选";
		}else{
		
		if(count==0||isCk==true){
			str = "";
		}
		else{
			str = count+"";
		}
		
		}
		return str;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	public String getGrupName() {
		return grupName;
	}
	public void setGrupName(String grupName) {
		this.grupName = grupName;
	}
	public List<ContactsInfo> getGrupData() {
		return grupData;
	}
	public void setGrupData(List<ContactsInfo> grupData) {
		this.grupData = grupData;
	}
	
	

}

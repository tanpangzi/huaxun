package cn.huaxunchina.cloud.app.tools;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.ContactsInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.SendSmsData;
import cn.huaxunchina.cloud.app.model.SendSmsItem;
import cn.huaxunchina.cloud.app.model.SmsContents;
import cn.huaxunchina.cloud.app.model.SmsUser;
import cn.huaxunchina.cloud.app.model.UserManager;

public class SmsUtil {
	
	
	public static String getSendJson(SendSmsData data,int roleId,String names){
		
		String json = "";
		StringBuffer classIds = new StringBuffer();
		List<ContactsInfo> users = new ArrayList<ContactsInfo>();
		SmsContents smsContents = new SmsContents();
		smsContents.setNames(names);
		int user_count = 0;
		
		SendSmsItem[] items =  data.getData();
		int length = items.length;
		for (int i = 0; i < length; i++) {
		SendSmsItem info = items[i];
		
		//classid的封装
		String classid = info.getClassId();
		if(classid.equals("schoolTeacher")){
		//判断老师组是否全选
		if(isAllCk(info)){
			
		if(RolesCode.PARENTS==roleId){//当前用户是家长
		users.addAll(info.getGrupData());
		}else{
		smsContents.setDirect("schoolTeacher");
		}
			
		}else
		{   //短信用户数据统计 
			users.addAll(info.getGrupData());
		}
		}else
		{
			
		//判断家长组是否全选
		if(isAllCk(info)){
			classIds.append(classid);
			classIds.append(",");
		}else
		{
			//短信用户数据统计 
			users.addAll(info.getGrupData());
		}
		}
		
		//发送人数的统计
		int ck_count= info.getCount();
		user_count = user_count+ck_count;
		}
		//classid的封装
		int str_length = classIds.length();
		if(str_length>1){
		String new_classIds =classIds.toString();
		new_classIds=new_classIds.substring(0, str_length-1);
		smsContents.setClassId(new_classIds);
		}
		//短信个数类型的封装 //0=单条,1=对多用户群发,
		smsContents.setGroupSms(getGroupSms(items));
		int smsType = 0;//0.短信 1.推送
		smsContents.setSmsType(smsType);
		int category = 9;
		smsContents.setCategory(category);//
		smsContents.setContent(data.getContent());//短信内容
		//短信联系人的封装
		List<SmsUser> smsUser = getUsers(users);
		smsContents.setUsers(smsUser);
		json = new Gson().toJson(smsContents);
		
		System.out.println(">>>>>>>>>>>sms:"+json);
		
		return json;
	}
	
	
	private static int getGroupSms(SendSmsItem[] items){
		int  groupSms = 0;
		int ck_count = 0;
		int length = items.length;
		for(int i = 0;i<length;i++){
			SendSmsItem info = items[i];
			boolean ck = info.isCk();
			int ct = info.getCount();
			if(ct>0){
				ck = true;
			}
			if(ck){
				ck_count++;
			}
		}
		
		if(ck_count>1){
			groupSms=2;
		}else if(ck_count==1){
			groupSms=1;
		}
		
		return groupSms;
		
	}
	
	
	
	public static boolean isAllCk(SendSmsItem info){
		
		boolean b = false;
		int ck_count = info.getCount();	//判断是否全选
		int count = info.getGrupData().size();
		boolean isCk = info.isCk();
		if(ck_count == count&&count!=0||isCk){//选择于总数是否相等
		b = true;
		}
		
		return b;
	}
	
      public static boolean isAllCk(SendSmsData data){
		
		boolean b = true;
		SendSmsItem[] item = data.getData();
		int count = item.length;
		for (int i = 0; i < count; i++) {
			if(!item[i].isCk()){
				b = false;
			}
			
		}
		return b;
	}
	
    public static void setAllCk(SendSmsData  data,boolean isCk){
		
		SendSmsItem[] item = data.getData();
		int count = item.length;
		for (int i = 0; i < count; i++) {
			item[i].setCk(isCk);
			if(item[i].getGrupData()!=null){
			setAllCk(item[i].getGrupData(),isCk);
			if(isCk==false){
			item[i].setCount(0);	
			}
			}
		}
	}
    
	public static void setAllCk(List<ContactsInfo> list,boolean isCk){
		int count = list.size();
		for (int i = 0; i < count; i++) {
			list.get(i).setCheck(isCk);
		}
	}
	
	
	public static List<SmsUser> getUsers(List<ContactsInfo> users){
		
		List<SmsUser> data = new ArrayList<SmsUser>();
		int size = users.size();
		for(int i=0;i<size;i++){
		ContactsInfo info = users.get(i);
		if(info.isCheck()){
		SmsUser user = new SmsUser();
		user.setActive(info.isActive());
		user.setId(info.getUserId());
		user.setName(info.getLinkPhone());
		data.add(user);
		}
		}
		
		return data;
	}
	
	public static String getGrupNames(SendSmsData data){
		
		StringBuffer names = new StringBuffer();
		SendSmsItem[] items =  data.getData();
		int length = items.length;
		
		
		if(data.getType()==3){//家长群发
		SendSmsItem p_data = items[0];
		int count = p_data.getGrupData().size();
		int ck_count = p_data.getCount();
		if(count==ck_count){
		names.append(p_data.getGrupName());
		names.append(";");	
		}else{
		List<ContactsInfo> c_data = p_data.getGrupData();
		int size = c_data.size();
		for(int i=0;i<size;i++){
		ContactsInfo info = c_data.get(i);
		if(info.isCheck()){
		names.append(info.getUserName());
		names.append(";");	
		}
		}	
		}
		
		
		}else{
			
		for (int i = 0; i < length; i++) {
		SendSmsItem t_data = items[i];
		/*int ck_count = info.getCount();
		boolean isCk = info.isCk();
		if(isCk||ck_count>0){//是否全选
		names.append(info.getGrupName());
		names.append(";");	
		} 
		}*/
		
		
		boolean isCk = t_data.isCk();
		if(isCk)
		{
			names.append(t_data.getGrupName());
			names.append(";");	
		
		}else{
			List<ContactsInfo> c_data = t_data.getGrupData();
			int size = c_data.size();
			for(int j=0;j<size;j++){
			ContactsInfo info = c_data.get(j);
				if(info.isCheck()){
				names.append(info.getUserName());
				names.append(";");	
				}
			}	
		
		}
		}}
		String json = names.toString();
		int cont = json.length();
		if(cont>1799){
		json=json.substring(0, 1795)+"...";
		}
		return json;
		
	}
	
	public static SendSmsItem setChecked(SendSmsItem item,boolean ck){
		
		List<ContactsInfo>  infos = item.getGrupData();
		for (ContactsInfo info:infos) {
			info.setCheck(ck);
		}
		
		return item;
	}
	 
	/**
	 * TODO(描述) 创建初始化数据
	  * @param type 1.老师群发  2.单发  3.家长群发
	  * @param grupData
	  * @return
	 */
	public static SendSmsData creat(int type,List<ContactsInfo> grupData){
		SendSmsData data = null;
		if(type==1){//老师群发
			
		UserManager manager = LoginManager.getInstance().getUserManager();
		List<ClassInfo> c_list = manager.classInfo;
		if (c_list != null) {
		int size = c_list.size();
		data = new SendSmsData((size+1));
		data.setType(type);
		for (int i = 0; i < size; i++) {
			ClassInfo info = c_list.get(i);
			SendSmsItem item = new SendSmsItem();
			item.setClassId(info.getClassId());
			item.setGrupName(info.getClassName());
			item.setId(i);
			if(i==0){
			item.setCk(true);	
			}
			data.add(i,item);
			
		}
		
		SendSmsItem item = new SendSmsItem();
		item.setId(size);
		item.setClassId("schoolTeacher");
		item.setGrupName("老师组");
		data.add(size,item);
		}
		 
		}else if(type==3)//家长群发
		{
			
		data = new SendSmsData(1);
		data.setType(3);
		SendSmsItem item = new SendSmsItem();
		item.setId(0);
		item.setClassId("schoolTeacher");
		item.setGrupName("老师组");
		item.setGrupData(grupData);
		item.setCount(grupData.size());
		item.setCk(true);
		data.add(0,item);
			
		} else{//单发短信创建
			
		data = new SendSmsData(1);
		data.setType(type);
		
		ContactsInfo new_info = new ContactsInfo();
		new_info.setActive(false);
		new_info.setCheck(false);
		grupData.add(new_info);
		String username = grupData.get(0).getUserName();
		SendSmsItem item = new SendSmsItem();
		item.setId(0);
		item.setClassId("#");
		item.setGrupName(username+";");
		item.setGrupData(grupData);
		data.add(0,item);
		}
		System.out.println(data);
		return data;
	}

}

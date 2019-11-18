package cn.huaxunchina.cloud.app.tools;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.model.Function;
import cn.huaxunchina.cloud.app.model.NewsMarking;
/**
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月18日 下午4:36:33 
 *
 */
public class UserUtil {
	
	private static final String PRENAME = "uuid";
	private static final String KEY = "userId";
	private static final String SERVER_ID = "server_id";
	private static final String SERVER_URL = "server_url";
	private static final String SERVER_NAME = "server_name";
	private static final String DIALOG_POSTION = "area_dialog_postion";
	private static final String VERSION_NAME = "version_name";
	private static final String MSG_SERVERURL = "msgServerUrl";
	private static final String ROLE_CHANGE_SELECT = "select_role_position";
	private static final String FUNCTIONS = "functions";//权限
	private static final String NEWSJSON = "newsJson";//
	
	//private static final String REQUESTURL = "http://test.hxfzsoft.com:10041/ischoolyard/";//"http://172.18.13.43:8080/ischoolyard/";//"http://test.hxfzsoft.com:10041/ischoolyard/";
	//"http://test.hxfzsoft.com:10047/ischoolyard/";
	//private static final String MSGURL = "http://test.hxfzsoft.com:10047/msgSender/";
	 
	public static void setVersionName(String version_name){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		pre.setValue(VERSION_NAME, version_name);
	}
	
	public static String getVersionName(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		return pre.getValue(VERSION_NAME);
	}
	
	public static void serveUserId(String userId){
		String _userid = getUserId();
        if(!userId.equals(_userid)){//如果登陆用户不是上次登陆用户  清除阅读标记
        newsClear();
		}
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		pre.setValue(KEY, userId);
	}
	
	public static String getUserId(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		String userId = pre.getValue(KEY);
		return userId;
	}
	
	public static void setRequestUrl(String requestUrl){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		pre.setValue(SERVER_URL, requestUrl);
	}
	
	public static String getRequestUrl(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		return pre.getValue(SERVER_URL);
		
		//return "http://test.hxfzsoft.com:10003/ischoolyard";
	}
	
    public static void setMsgUrl(String requestUrl){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		if(requestUrl!=null){
			int leng = requestUrl.length();
			String str = requestUrl.substring(leng-1);
			System.out.println(str);
			if(str.equals("/")){
				requestUrl=requestUrl+"pushReportServlet";
			}else{
				requestUrl=requestUrl+"/pushReportServlet";
			}
			
		}
		pre.setValue(MSG_SERVERURL, requestUrl);
	}
	
	public static String getMsgUrl(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		return pre.getValue(MSG_SERVERURL);// MSGURL;//pre.getValue(MSG_SERVERURL);
	}
	
	public static void setRequestId(String requestId){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		pre.setValue(SERVER_ID, requestId);
	}
	
	public static String getRequestId(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		return pre.getValue(SERVER_ID);
	}
	
	public static void setRequestName(String requestName){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		pre.setValue(SERVER_NAME, requestName);
	}
	
	public static String getRequestName(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		return pre.getValue(SERVER_NAME);
	}
	
	
	public static void setDiologPostion(String postion){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		pre.setValue(DIALOG_POSTION, postion);
	}
	
	public static String getDiologPostion(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		return pre.getValue(DIALOG_POSTION);
	}
 
    
	public static void setChangRolePostion(int postion){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		pre.setValue(ROLE_CHANGE_SELECT, String.valueOf(postion));
	}
	
	public static int getChangRolePostion(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		if(pre.getValue(ROLE_CHANGE_SELECT).equals("")){
			return 0;
		}
		return Integer.valueOf(pre.getValue(ROLE_CHANGE_SELECT));
	}
	
	public static void setFunctions(List<Function> list){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		pre.setValue(FUNCTIONS, json);
	}
	
	public static List<Function> getFunctions(){
		List<Function>  list = new ArrayList<Function>();
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		String json = pre.getValue(FUNCTIONS);
		Gson gson = new Gson();
		list=gson.fromJson(json, new TypeToken<List<Function>>() {}.getType());
		return list;
	}
	
	 
	
	public static boolean isNews(String key,String id){
		List<NewsMarking>  list = new ArrayList<NewsMarking>();
		boolean b = false;
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		String json = pre.getValue(NEWSJSON);
		Gson gson = new Gson();
		list=gson.fromJson(json, new TypeToken<List<NewsMarking>>() {}.getType());
		if(list!=null){
			int size = list.size();
			for(int i=0;i<size;i++){
				NewsMarking info = list.get(i);
				if(key.equals(info.getKey())){
					if(info.getId().equals(id)){
						b=true;
					} 
				}
			}
		}
		return b;
	}
	
	public static void setNews(String key,String id){
		List<NewsMarking>  list = new ArrayList<NewsMarking>();
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		String json = pre.getValue(NEWSJSON);
		Gson gson = new Gson();
		list=gson.fromJson(json, new TypeToken<List<NewsMarking>>() {}.getType());
		if(list==null){
			list = new ArrayList<NewsMarking>();
		}
			boolean b = false;
			int size = list.size();
			for(int i=0;i<size;i++){
				NewsMarking info = list.get(i);
				if(key==info.getKey()){
					b=true;
					info.setId(id); 
					list.remove(i);
					list.add(i, info);
				}
			}
			//如果key存在及覆盖 不存在添加
			if(!b){
				NewsMarking info = new NewsMarking();
				info.setKey(key);
				info.setId(id);
				list.add(info);
			}
	 
		json=gson.toJson(list);
		pre.setValue(NEWSJSON, json);
	}
	
	public static void newsClear(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),PRENAME);
		pre.setValue(NEWSJSON, "");
	}
	
	
	 
	
	 

}

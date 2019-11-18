package cn.huaxunchina.cloud.app.model;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.model.response.LoginData;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.app.tools.UserUtil;

public class LoginManager {
	
	private static LoginManager manager = null;
	private static String CUR_ROLEID = "curRoleId";//当前角色
	private static String CUR_ROLENAME = "curRoleName";//当前角色名称
	private static String CUR_CLASSID = "curClassId";//当前班级id
	private static String CUR_ID = "curId";//
	private static String CUR_STUDENTID = "studentId";
	private static String CUR_TEACHERID = "teacherId";
	private static String USER_NAME = "userName";//用户名称
	private static String SCHOOL_NAME = "schoolName";//学校名称
	private static String LOGIN_PHONE = "loginPhone";//用户名
	private static String PASSWORD = "password";//密码
	private static String JSON_DATA = "jsonData";//
	private static String ROLE_COUNT="roleCount";
	private static String STUDENT_NAME="studentName";//当前家长角色下的学生姓名
	private static String CLIENT_APPID="clientAppId";
	private static String JSON_STUDENTS ="jsonStudents";
	private static String JSON_CLASSINFO ="jsonClassInfo";
	private static String MSG="isMsg";//是否接受推送消息
	private static String TAG="Tag";//是否设置了标签
	private static String TAG_STATUS = "tag_status";//0变化 1不变化
	private static String JSON_TAGS = "jsonTags";//标签
	private static String IMEI = "imei";
	
	
    private LoginManager(){
	}
	
	public static LoginManager getInstance(){
		if(manager==null){
			manager=new LoginManager();
		}
		return manager;
	}
	
	public UserManager getUserManager(){
		UserManager uManager = new UserManager();
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		uManager.curId=pre.getValue(CUR_ID);
		uManager.curRoleId =pre.getValue(CUR_ROLEID);
		uManager.curRoleName = pre.getValue(CUR_ROLENAME);
		uManager.userName = pre.getValue(USER_NAME);
		uManager.loginPhone = pre.getValue(LOGIN_PHONE);
		uManager.password = pre.getValue(PASSWORD);
		uManager.roleCount = pre.getValue(ROLE_COUNT);
		uManager.schoolName=pre.getValue(SCHOOL_NAME);
		uManager.clientAppId=pre.getValue(CLIENT_APPID);
		uManager.userId=UserUtil.getUserId();
		uManager.curClassId=pre.getValue(CUR_CLASSID);
		uManager.curStudentId=pre.getValue(CUR_STUDENTID);
		uManager.curTeacherId=pre.getValue(CUR_TEACHERID);
		String jsonData =  pre.getValue(JSON_DATA);
		if(jsonData!=null&&!jsonData.equals("")){
		try {
			LoginData loginData= GsonUtils.getSingleBean(jsonData, LoginData.class);
			uManager.loginData=loginData;
			uManager.students=loginData.getStudents();
			uManager.classInfo=loginData.getClassList();
			uManager.weekTotal=loginData.getUserinfo().getSchool().getWeekTotal();
			uManager.currentWeek=loginData.getUserinfo().getSchool().getCurrentWeek();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		//学生
		String jsonStudents =  pre.getValue(JSON_STUDENTS);
		if(jsonStudents!=null&&!jsonStudents.equals("")){
		try {
			uManager.students=new Gson().fromJson(jsonStudents, new TypeToken<List<Students>>() {}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//班级
		String jsonClassinfo =  pre.getValue(JSON_CLASSINFO);
		if(jsonClassinfo!=null&&!jsonClassinfo.equals("")){
		try {
			uManager.classInfo=new Gson().fromJson(jsonClassinfo, new TypeToken<List<ClassInfo>>() {}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//标签
		uManager.tags=getJsonTags();
		return uManager;
	}
	
	public List<String> getJsonTags(){
		 List<String> tags = new ArrayList<String>();
		 PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		 String jsonTags = pre.getValue(JSON_TAGS);
			if(jsonTags!=null&&!jsonTags.equals("")){
				try {
					tags=GsonUtils.getStringList(jsonTags);
					} catch (Exception e) 
					{
						e.printStackTrace();
						tags = new ArrayList<String>();
					}
			}
			if(tags==null){
				tags = new ArrayList<String>();
			}
		 return tags;
	}
	
	
	public void setJsonTags(String jsonTags){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		String tag = pre.getValue(JSON_TAGS);
		if(!tag.equals(jsonTags)){
			pre.setValue(JSON_TAGS, jsonTags);
			pre.setValue(TAG_STATUS, "0");
		}
	}
	
	public void setImei(String imei){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		pre.setValue(IMEI, imei);
	}
	
	public String getImei(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		return pre.getValue(IMEI);
	}
	
	public String getTagStatus(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		String status=pre.getValue(TAG_STATUS);
		if(status==null||status.equals("")){
			status="0";
		}
		return status;////0变化 1不变化
	}
	
	public void setTagStatus(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		pre.setValue(TAG_STATUS, "1");
	}
	
	public String getIsMsg(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		return pre.getValue(MSG);
	}
	
	
	
	public void setMsg(String isMsg){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		pre.setValue(MSG, isMsg);
	}
	
	public void setCurClassId(PreferencesHelper  pre,String classId){
		pre.setValue(CUR_CLASSID, classId);
	}
	
	public String getIsTag(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		return pre.getValue(TAG);
	}
	
	public void setTag(){
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		pre.setValue(TAG, "YES");
	}
	
	 
	
	public void setJsonClassInfo(PreferencesHelper  pre,String jsonClassInfo){
		pre.setValue(JSON_CLASSINFO, jsonClassInfo);
	}
	
	public void setJsonStudents(PreferencesHelper  pre,String jsonStudents){
		pre.setValue(JSON_STUDENTS, jsonStudents);
	}
	 
	
	public void setClientAppId(PreferencesHelper  pre,String clientAppId){
		pre.setValue(CLIENT_APPID, clientAppId);
	}
	
	public void setRoleCount(PreferencesHelper  pre,String role_count){
		pre.setValue(ROLE_COUNT, role_count);
		
	}
	
	public void setJsonData(PreferencesHelper  pre ,String jsonData){
		pre.setValue(JSON_DATA, jsonData);
		
	}
	public void setLoginPhone(PreferencesHelper  pre,String loginPhone){
		pre.setValue(LOGIN_PHONE, loginPhone);
		
	}
	public void setPassword(PreferencesHelper  pre,String password){
		pre.setValue(PASSWORD, password);
		
	}
	public void setUserName(PreferencesHelper  pre,String userName){
		pre.setValue(USER_NAME, userName);
	}
	
	
	public void setCurId(PreferencesHelper  pre,String rurId){
		pre.setValue(CUR_ID, rurId);
	}
	
	public void setCurRoleName(PreferencesHelper  pre,String curRoleName){
		pre.setValue(CUR_ROLENAME, curRoleName);
	}
	
	public void setCurRoleId(PreferencesHelper  pre,String curRoleId){
		pre.setValue(CUR_ROLEID, curRoleId);
	}
	
	public void setSchoolName(PreferencesHelper  pre,String schoolName){
		pre.setValue(SCHOOL_NAME, schoolName);
	}

	public void setCurStudentId(PreferencesHelper  pre,String studentId){
		pre.setValue(CUR_STUDENTID, studentId);
	}
	
	public void setCurTeacherId(PreferencesHelper  pre,String teacherid){
		pre.setValue(CUR_TEACHERID, teacherid);
	}
}

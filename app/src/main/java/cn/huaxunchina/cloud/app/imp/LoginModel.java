package cn.huaxunchina.cloud.app.imp;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.huaxunchina.cloud.app.adapter.ChangeRoleAdapter.ChangeInfo;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Role;
import cn.huaxunchina.cloud.app.model.ServerData;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.response.LoginData;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.Base64Util;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.location.app.model.ClientAppIdUtil;

import com.igexin.sdk.PushManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Handler;
import android.os.Message;

/**
 * 登陆
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月6日 下午6:50:58
 * 
 */
public class LoginModel implements LoginResponse {
	
	
	public void doServerList(AsyncHttpClient httpUtils, final Handler handler){
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		String url = RequestCode.SERVER_LIST;
		 httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					
					String jsonData = response.toString();
					try {
						String code = GsonUtils.getCode(jsonData);
						if (code.equals(HttpResultStatus.SUCCESS)) {// 判断是否成功返回
							ServerData data = GsonUtils.getSingleBean(jsonData, ServerData.class);
							message.what = HandlerCode.SERVER_SUCCESS;
							message.obj = data;
							handler.sendMessage(message);
						}else {
							String msg = GsonUtils.getMessage(jsonData);
							message.what = HandlerCode.HANDLER_FAIL;
							message.obj = msg;
							handler.sendMessage(message);
						}
					} catch (Exception e) {
						e.printStackTrace();
						message.what = HandlerCode.HANDLER_ERROR;// 错误
						handler.sendMessage(message);
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				message.what = HandlerCode.HANDLER_TIME_OUT;// 错误
				handler.sendMessage(message);
				}
			});
	}
	
	

	@Override
	public void doLogin(final String loginPhone, final String password,final AsyncHttpClient httpUtils, final Handler handler) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("loginPhone", loginPhone);
		params.put("password", password);// Base64Util.encode(password.getBytes())
		String url = UserUtil.getRequestUrl()+RequestCode.AJAXDOLOGIN;
		System.out.println("doLogin:"+url+"loginPhone="+loginPhone+"&password="+password);
		httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					String jsonData = response.toString();
					System.out.println("login:" + jsonData);
					//Toast.makeText(ApplicationController.getContext(), "登陆了"+jsonData, Toast.LENGTH_LONG).show();
					try {
						String code = GsonUtils.getCode(jsonData);
						if (code.equals(HttpResultStatus.SUCCESS)) {// 判断是否成功返回
							String data = GsonUtils.getData(jsonData);
							LoginData loginData = GsonUtils.getSingleBean(data, LoginData.class);
							// 保存用户id
							String userId = loginData.getUserinfo().getUserId();
							UserUtil.serveUserId(userId);
							// 保存登陆信息 密码 账号 json数据
							PreferencesHelper pre = new PreferencesHelper(ApplicationController.getContext(),userId);
							LoginManager manager = LoginManager.getInstance();
							manager.setLoginPhone(pre, loginPhone);
							manager.setPassword(pre, password);
							manager.setJsonData(pre, data);
							
							cn.huaxunchina.cloud.app.model.UserInfo info = loginData.getUserinfo();
							manager.setUserName(pre, info.getUserName());
							manager.setSchoolName(pre, info.getSchool().getSchoolName());
							manager.setClientAppId(pre, info.getClientAppId());
							@SuppressWarnings("unused")
							List<Students> studentInfo = loginData.getStudents();
							
							//=====个推初始化
							String msg = manager.getIsMsg();
							if(msg.equals("")||msg.equals("YES")){
							PushManager.getInstance().initialize(ApplicationController.getContext());
							PushManager.getInstance().turnOnPush(ApplicationController.getContext());
							manager.setMsg("YES");
							//标签设置
							//List<String> tags = loginData.getAlltags();
							//保存当前标签
							//ApplicationController.setTags(tags);
							//==保存标签数据
							String jsonTags = GsonUtils.getJson(data, "alltags");
							System.out.println("jsonTags:"+jsonTags);
							manager.setJsonTags(jsonTags);
							//权限
							if(loginData.getUserinfo().getRole()!=null){
								UserUtil.setFunctions(loginData.getUserinfo().getRole().getFunctions());
								
							}
							
							}
							message.what = HandlerCode.HANDLER_SUCCESS;
							message.obj = loginData;
							handler.sendMessage(message);
							
						} else {
							String msg = GsonUtils.getMessage(jsonData);
							message.what = HandlerCode.HANDLER_FAIL;// 登陆失败
							message.obj = msg;
							handler.sendMessage(message);
						}
					} catch (Exception e) {

						e.printStackTrace();
						message.what = HandlerCode.HANDLER_ERROR;// 错误
						handler.sendMessage(message);
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				message.what = HandlerCode.HANDLER_TIME_OUT;// 错误
				handler.sendMessage(message);
				}
			});
	}

	@Override
	public void doLogin(String roleId, String loginPhone, String password,AsyncHttpClient httpUtils, final Handler handler) {

		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("roleId", roleId);
		params.put("loginPhone", loginPhone);
		params.put("password", password);
		String url = UserUtil.getRequestUrl()+RequestCode.WITHROLE;
		httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					String jsonData = response.toString();
					System.out.println("doLogin[roleId]" + jsonData);
					try {
						String code = GsonUtils.getCode(jsonData);
						if (code.equals(HttpResultStatus.SUCCESS)) {// 判断是否成功返回
							// 数据的保存==============
							String data = GsonUtils.getData(jsonData);
							LoginData loginData = GsonUtils.getSingleBean(data, LoginData.class);
							// 保存基本信息
							PreferencesHelper pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
							LoginManager manager = LoginManager.getInstance();
							cn.huaxunchina.cloud.app.model.UserInfo info = loginData.getUserinfo();
							manager.setUserName(pre, info.getUserName());
							manager.setSchoolName(pre, info.getSchool().getSchoolName());
							manager.setClientAppId(pre, info.getClientAppId());
							//保存登陆状态 
							ApplicationController.setIsLogin();

							int curRoleId = info.getRole().getRoleId();
							// =====================================角色测试
							manager.setCurRoleId(pre, curRoleId + "");
							manager.setCurRoleName(pre, info.getRole().getRoleName());
//							manager.setRoleCount(pre, "2");
							if (RolesCode.PARENTS != curRoleId) {// 当前角色id
								manager.setCurId(pre, loginData.getTeacher().getTeacherID());
							}
							
							//=====班级和学生
							String jsonStudents = GsonUtils.getJson(data, "students");
							manager.setJsonStudents(pre, jsonStudents);
							String jsonClassInfo = GsonUtils.getJson(data, "classList");
							manager.setJsonClassInfo(pre, jsonClassInfo);
							
							//=====个推初始化
							String msg = manager.getIsMsg();
							if(msg.equals("")||msg.equals("YES")){
							PushManager.getInstance().initialize(ApplicationController.getContext());
							PushManager.getInstance().turnOnPush(ApplicationController.getContext());
							manager.setMsg("YES");
							}
							
							//权限
							if(loginData.getUserinfo().getRole()!=null){
								UserUtil.setFunctions(loginData.getUserinfo().getRole().getFunctions());
							}
							
							message.what = HandlerCode.HANDLER_SUCCESS;
							handler.sendMessage(message);

						}else{
							String msg = GsonUtils.getMessage(jsonData);
							message.obj = msg;
							message.what =HandlerCode.HANDLER_ERROR;
							handler.sendMessage(message);	
						}
					} catch (Exception e) {

						e.printStackTrace();
						message.obj = "网络超时!";
						message.what = HandlerCode.HANDLER_ERROR;// 错误
						handler.sendMessage(message);
					}
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				message.what = HandlerCode.HANDLER_TIME_OUT;// 错误
				handler.sendMessage(message);
				}
			});
	}

	//88角色切换
	@Override
	public void changeRole(final ChangeInfo role, AsyncHttpClient httpUtils,final Handler handler) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("roleId", role.getRoleId() + "");
		String url = UserUtil.getRequestUrl()+RequestCode.AJAXCHANGEROLE;
		System.out.println("changeRole:"+url+"roleId="+role.getRoleId());
		 httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					String jsonData = response.toString();
					System.out.println("doLogin[roleId]" + jsonData);
					try {
						String code = GsonUtils.getCode(jsonData);
						if (code.equals(HttpResultStatus.SUCCESS)) {// 判断是否成功返回
							// 数据的保存==============
							String data = GsonUtils.getData(jsonData);
							LoginData loginData = GsonUtils.getSingleBean(data, LoginData.class);
							// 保存基本信息
							PreferencesHelper pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
							LoginManager manager = LoginManager.getInstance();
							cn.huaxunchina.cloud.app.model.UserInfo info = loginData.getUserinfo();
							manager.setUserName(pre, info.getUserName());
							manager.setSchoolName(pre, info.getSchool().getSchoolName());
							manager.setClientAppId(pre, info.getClientAppId());
							//保存登陆状态 
							ApplicationController.setIsLogin();

							int curRoleId = info.getRole().getRoleId();
							// =====================================角色测试
							manager.setCurRoleId(pre, curRoleId + "");
							manager.setCurRoleName(pre, info.getRole().getRoleName());
							manager.setRoleCount(pre, "2");
							if (RolesCode.PARENTS != curRoleId) {// 当前角色id 老师角色
								manager.setCurId(pre, loginData.getTeacher().getTeacherID());
								manager.setCurTeacherId(pre, loginData.getTeacher().getTeacherID());
								
								//====定位平台ClientAppId
							}else{//家长角色
								manager.setCurId(pre, role.getStudentId());
								
								//====定位平台ClientAppId
							}
							
							//=====班级和学生
							String jsonStudents = GsonUtils.getJson(data, "students");
							manager.setJsonStudents(pre, jsonStudents);
							String jsonClassInfo = GsonUtils.getJson(data, "classList");
							manager.setJsonClassInfo(pre, jsonClassInfo);
							
							//=====个推初始化
							String msg = manager.getIsMsg();
							if(msg.equals("")||msg.equals("YES")){
							PushManager.getInstance().initialize(ApplicationController.getContext());
							PushManager.getInstance().turnOnPush(ApplicationController.getContext());
							manager.setMsg("YES");
							}
							
							//权限
							if(loginData.getUserinfo().getRole()!=null){
								UserUtil.setFunctions(loginData.getUserinfo().getRole().getFunctions());
							}
							
							
							
							message.what = HandlerCode.HANDLER_SUCCESS;
							handler.sendMessage(message);

						}else{
							String msg = GsonUtils.getMessage(jsonData);
							message.obj = msg;
							message.what =HandlerCode.HANDLER_ERROR;
							handler.sendMessage(message);	
						}
					} catch (Exception e) {

						e.printStackTrace();
						message.obj = "网络超时!";
						message.what = HandlerCode.HANDLER_ERROR;// 错误
						handler.sendMessage(message);
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				message.what = HandlerCode.HANDLER_TIME_OUT;// 错误
				handler.sendMessage(message);
				}
			});
	}

	@Override
	public void changPwd(String loginPhone, String oldPassword,final String password, AsyncHttpClient httpUtils, final Handler handler) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("loginPhone", loginPhone);
		params.put("oldPassword",Base64Util.encode(oldPassword.getBytes()));
		params.put("password",Base64Util.encode(password.getBytes()));
		String url = UserUtil.getRequestUrl()+RequestCode.AJAXCHANGEPWD;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {
						// 修改成功保存密码
						PreferencesHelper pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
						LoginManager manager = LoginManager.getInstance();
						manager.setPassword(pre, password);
						message.what = HandlerCode.HANDLER_SUCCESS;
						handler.sendMessage(message);
					} else {
						// 修改密码失败
						message.what = HandlerCode.HANDLER_FAIL;
						message.obj = GsonUtils.getMessage(json);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
					message.what = HandlerCode.HANDLER_ERROR;
					handler.sendMessage(message);
				}
			}}));
		
	}

	@Override
	public void getSmsCode(String loginPhone, AsyncHttpClient httpUtils,final Handler handler) {

		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("loginPhone", loginPhone);
		String url = UserUtil.getRequestUrl()+RequestCode.USER_AJAXFORGET;
		httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					String jsonData = response.toString();
					try {
						String code = GsonUtils.getCode(jsonData);
						if (code.equals(HttpResultStatus.SUCCESS)) {
							// 获取验证码成功
							message.what = HandlerCode.FORGET_VERIFYCODE;
							handler.sendMessage(message);
						}else{
							message.what = HandlerCode.HANDLER_ERROR;
							handler.sendMessage(message);
						}
					} catch (Exception e) {
						e.printStackTrace();
						message.what = HandlerCode.HANDLER_ERROR;
						handler.sendMessage(message);
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				message.what = HandlerCode.HANDLER_TIME_OUT;// 错误
				handler.sendMessage(message);
				}
			});
	}

	@Override
	public void getNewPasswd(String loginPhone, String validCode,String password, AsyncHttpClient httpUtils, final Handler handler) {

		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("loginPhone", loginPhone);
		params.put("validCode", validCode);
		params.put("password",Base64Util.encode(password.getBytes()));
		String url = UserUtil.getRequestUrl()+RequestCode.USER_AJAXGETNEWPASSWD;
		httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					String jsonData = response.toString();
					try {
						String code = GsonUtils.getCode(jsonData);
						if (code.equals(HttpResultStatus.SUCCESS)) {
							message.what = HandlerCode.FORGET_SUCCESS;
							handler.sendMessage(message);
						} else {
							String msg = GsonUtils.getMessage(jsonData);
							message.obj=msg;
							message.what = HandlerCode.HANDLER_ERROR;
							handler.sendMessage(message);
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				message.what = HandlerCode.HANDLER_TIME_OUT;// 错误
				handler.sendMessage(message);
				}
			});
	}
	
	 

}

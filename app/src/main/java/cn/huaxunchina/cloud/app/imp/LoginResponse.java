package cn.huaxunchina.cloud.app.imp;

import cn.huaxunchina.cloud.app.adapter.ChangeRoleAdapter.ChangeInfo;

import com.loopj.android.http.AsyncHttpClient;

import android.os.Handler;

public interface LoginResponse {
	/**
	 * TODO(描述) 
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void doServerList(AsyncHttpClient httpUtils, final Handler handler);
	
	/**
	 * 用户登陆
	 * @param loginPhone 登陆账号
	 * @param password 登陆密码
	 * @param httpUtils 
	 * @param handler
	 */
	public abstract void doLogin(String loginPhone, String password, AsyncHttpClient httpUtils, Handler handler);
	
	/**
	 * TODO(描述) 
	  * @param roleId 角色
	  * @param loginPhone 账号
	  * @param password 密码
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void doLogin(String roleId, String loginPhone, String password, AsyncHttpClient httpUtils, Handler handler);
	
	/**
	 * 角色切换
	 * @param roleId 角色id
	 * @param httpUtils
	 * @param handler
	 */
	public abstract void changeRole(ChangeInfo role, AsyncHttpClient httpUtils, Handler handler);
	/**
	 * 修改密码
	  * @param loginPhone 登陆账号
	  * @param oldPassword 旧密码
	  * @param password 新密码
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void changPwd(String loginPhone, String oldPassword, String password, AsyncHttpClient httpUtils, Handler handler);
	
	/**
	 *  手机验证码 
	  * @param loginPhone 账号
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void getSmsCode(String loginPhone, AsyncHttpClient httpUtils, Handler handler);
	/**
	 * 重置密码
	  * @param loginPhone 账号
	  * @param validCode 验证码
	  * @param password 新密码
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void getNewPasswd(String loginPhone, String validCode, String password, AsyncHttpClient httpUtils, Handler handler);

}

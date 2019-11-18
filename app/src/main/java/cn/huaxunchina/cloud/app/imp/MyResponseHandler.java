package cn.huaxunchina.cloud.app.imp;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 重写JsonHttpResponseHandler
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年11月25日 下午3:25:42
 * 
 */
public class MyResponseHandler extends JsonHttpResponseHandler {

	private RequestParams params;
	private AsyncHttpClient httpUtils;
	private Handler handler;
	private JsonCallBack callBack;
	private Message message;
	private String url;
	private boolean isLogin = true;

	public MyResponseHandler(String url, RequestParams params,
			AsyncHttpClient httpUtils, Handler handler, JsonCallBack callBack) {
		this.url = url;
		this.params = params;
		this.httpUtils = httpUtils;
		this.handler = handler;
		this.callBack = callBack;
		message = handler.obtainMessage();
	}

	/**
	 * 数据处理的重写封装
	 */
	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		String json = response.toString();
		ApplicationController.setDate(headers);
		System.out.println("iSchoolyard---->data:"+json);
		try {
		    String code = GsonUtils.getCode(json);
		    if(code.equals(HttpResultStatus.SUCCESS)){
		     callBack.onCallBack(json);
		     }else{
		     message.what=HandlerCode.HANDLER_ERROR;
			 handler.sendMessage(message);
		     }
		} catch (Exception e) {
			e.printStackTrace();
			message.what = HandlerCode.HANDLER_ERROR;
			handler.sendMessage(message);
		}

	}

	/**
	 * COOKIE失效的处理 ，如果cookie失效，则登陆
	 * 流程:请求数据---->cookie失效--->登陆成功--->回调请求数据方法--->登陆失败--->提示用户
	 */
	@Override
	public void onFailure(int statusCode, Header[] headers,
			String responseString, Throwable throwable) {
		System.out.println(statusCode);
		if (isLogin) {
			// 登陆
			testTast("cookie失效重新登陆中.....");
			UserManager manager = LoginManager.getInstance().getUserManager();
			String password = manager.password;
			String loginPhone = manager.loginPhone;
			String roleId = manager.curRoleId;
			RequestParams requestParams = new RequestParams();
			requestParams.put("roleId", roleId);
			requestParams.put("loginPhone", loginPhone);
			requestParams.put("password", password);
			String logurl = UserUtil.getRequestUrl() + RequestCode.WITHROLE;
			httpUtils.post(logurl, requestParams,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							String json = response.toString();
							try {
								String code = GsonUtils.getCode(json);
								if (code.equals(HttpResultStatus.SUCCESS)) {// 登陆成功重新调用原接口
									testTast("登陆成功.....");
									httpUtils.post(url, params,
											new JsonHttpResponseHandler() {
												@Override
												public void onSuccess(
														int statusCode,
														Header[] headers,
														JSONObject response) {
													String json = response
															.toString();
													try {
														String code = GsonUtils
																.getCode(json);
														if (code.equals(HttpResultStatus.SUCCESS)) {
															testTast("获取数据成功.....");
															callBack.onCallBack(json);
														}
													} catch (Exception e) {
														// TODO Auto-generated
														// catch block
														e.printStackTrace();
														message.what = HandlerCode.HANDLER_ERROR;
														handler.sendMessage(message);
													}
												}

												@Override
												public void onFailure(
														int statusCode,
														Header[] headers,
														Throwable throwable,
														JSONObject errorResponse) {
													message.what = HandlerCode.HANDLER_TIME_OUT;// 错误
													handler.sendMessage(message);
												}
											});
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					});
			isLogin = false;
		} else if (statusCode == HttpStatus.SC_UNAUTHORIZED && isLogin == false) {
			message.what = HandlerCode.COOKIESTORE_SUCCESS; // COOK失效
			handler.sendMessage(message);
		}
	}

	/**
	 * 超时处理的封装
	 */
	@Override
	public void onFailure(int statusCode, Header[] headers,
			Throwable throwable, JSONObject errorResponse) {
		System.out.println(statusCode);
		message.what = HandlerCode.HANDLER_TIME_OUT;// 错误
		handler.sendMessage(message);
	}

	private void testTast(String text) {
		// Toast.makeText(ApplicationController.getContext(), text,
		// Toast.LENGTH_LONG).show();
	}

}

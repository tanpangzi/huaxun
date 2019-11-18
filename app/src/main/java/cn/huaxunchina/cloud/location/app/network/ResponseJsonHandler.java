package cn.huaxunchina.cloud.location.app.network;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ResponseJsonHandler extends JsonHttpResponseHandler {
	
	private AsyncHttpClient httpUtils;
	private Handler handler;
	private JsonCallBack callBack;
	private Message message;
	
	public ResponseJsonHandler(AsyncHttpClient httpUtils, Handler handler, JsonCallBack callBack){
		this.httpUtils=httpUtils;
		this.handler=handler;
		this.callBack=callBack;
		message = handler.obtainMessage();
	}

	/**
	 * 数据处理的重写封装
	 */
	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		String response_json = response.toString();
		System.out.println("onSuccess:"+response_json);
		try {
			String code = GsonUtils.getCode(response_json);
			if(code.equals(HttpResultStatus.SUCCESS)){
			callBack.onCallBack(response_json);
			}else
			{
				/*if(code.equals(String.valueOf(HandlerCode.LBS_901))){//禁用 
					Toast.makeText(ApplicationController.getContext(), "该终端卡已禁用,请联系客服或者系统管理员", 0).show();
				}else if(code.equals(String.valueOf(HandlerCode.LBS_902))){//无效
					Toast.makeText(ApplicationController.getContext(), "无效终端卡号,请联系当地客服人员", 0).show();
				}*/
				
				if(code.equals(String.valueOf(HandlerCode.LBS_901))
				||code.equals(String.valueOf(HandlerCode.LBS_902))){//禁用 
				callBack.onCallBack(response_json);	
				}else{
					message.what =HandlerCode.HANDLER_ERROR;
					handler.handleMessage(message);
				}
				
				 
			}
		} catch (JSONException e) {
			e.printStackTrace();
			message.what =HandlerCode.HANDLER_ERROR;
			handler.handleMessage(message);
		}
		 
		
		
	}

	
	/**
	 * COOKIE失效的处理 ，如果cookie失效，则登陆
	 * 流程:请求数据---->cookie失效--->登陆成功--->回调请求数据方法--->登陆失败--->提示用户
	 */
	@Override
	public void onFailure(int statusCode, Header[] headers,String responseString, Throwable throwable) {
		System.out.println("报错了:"+statusCode+"   "+responseString);
		message.what =HandlerCode.HANDLER_ERROR;
		handler.handleMessage(message);
	}
	
	
	/**
	 * 超时处理的封装
	 */
	@Override
	public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
		System.out.println("报错了:"+statusCode+"   "+errorResponse);
		message.what =HandlerCode.HANDLER_ERROR;
		handler.handleMessage(message);
		
	}
	
	 

}

package cn.huaxunchina.cloud.app.network;

import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.tools.GsonUtils;

public class MyJsonHttpResponseHandler extends JsonHttpResponseHandler {
	
	
	Activity activity;
	
	public MyJsonHttpResponseHandler(Activity activity){
		this.activity = activity;
	}
	
	
	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		super.onSuccess(statusCode, headers, response);
		String jsonData = response.toString();
		try {
			String code = GsonUtils.getCode(jsonData);
			if(code.equals("9999")&&activity!=null){
				activity.startActivity(new Intent(activity,Login.class));
			}
		}catch(Exception e){}
		
		
	}
	
	@Override
	public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse){
		
	}

}

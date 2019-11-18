package cn.huaxunchina.cloud.location.app.activity;


import org.apache.http.HttpEntity;

import android.os.Handler;
import android.os.Message;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.location.app.model.post.Alarmsetting;
import cn.huaxunchina.cloud.location.app.model.post.FencesettingModel;
import cn.huaxunchina.cloud.location.app.model.post.LocationModel;
import cn.huaxunchina.cloud.location.app.model.post.LocusModel;
import cn.huaxunchina.cloud.location.app.model.post.LoginModel;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.network.ResponseJsonHandler;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

public class Test {
//	static String url = "http://172.18.10.11:8080/location/cmd";
	public static String url = "http://test.hxfzsoft.com:10041/location/cmd";
	private static Handler handler = new Handler(){};

	
	/*public static void http_test(AsyncHttpClient httpUtils) throws UnsupportedEncodingException{
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		//params.setContentEncoding("{'c':'gDecode','id':'1','psw': '0000','lat':'99.21','lng': '25.01'}");
		
		GDecode gDecode = new GDecode();
		gDecode.setId("1");
		gDecode.setPsw("0000");
		gDecode.setLat(99.21);
		gDecode.setLng(95.01);
		//String json = new Gson().toJson(gDecode);//"{'c':'gDecode','id':'1','psw': '0000','lat':99.21,'lng': 95.01}";
		
		String url = "http://172.18.10.11:8080/location/cmd";
		httpUtils.addHeader("Content-Type", "application/json");
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			 System.out.println(json);
		}}));
	 
		//HttpEntity entity = new  StringEntity(pm);
		httpUtils.post(ApplicationController.getContext(), url, entity, "application/json", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] bytes) {
				// TODO Auto-generated method stub
				String str  =new String(bytes);
				System.out.println(str);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				System.out.println(statusCode);
			}
			 
		});
		
		httpUtils.post(ApplicationController.getContext(), url, GsonUtils.toJson(gDecode), Constant.CONTENTTYPE, 
		new ResponseJsonHandler(url, httpUtils, handler, new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				// TODO Auto-generated method stub
				System.out.println(json);
			}}));
		
		
		HttpEntity entity = new  StringEntity(json);
		httpUtils.post(ApplicationController.getContext(), url, entity, "application/json",new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				System.out.println(response.toString());
			}
		});
		
		
		new AsyncHttpUtil(httpUtils,handler).post(url, json, new JsonCallBack(){

			@Override
			public void onCallBack(String json) {
				// TODO Auto-generated method stub
				System.out.println(json);
			}});
		
		
	}*/
	
	
	public static void http_test(final AsyncHttpClient httpUtils,LoadingDialog loadingDialog){
		//
//		gDecode(httpUtils);
		//circle(httpUtils,loadingDialog);
		///====json封装
		//gDecode()
		//retue: json
		//mode
		
		new HttpHandler(loadingDialog,httpUtils,new LoginModel(),new HttpHandlerCallBack(){
			@Override
			public void onCallBack(String json) {
				//System.out.println(json);
				//uGetGPS(httpUtils);
				//getLocus(httpUtils);
				alarmsett(httpUtils);
			}
			@Override
			public void onErro() {
				
			}});
		
		 
		
	}
	
	public static void alarmsett(final AsyncHttpClient httpUtils){
		FencesettingModel fen = new FencesettingModel();
		fen.setType(1);
		new HttpHandler(null,httpUtils,fen,new HttpHandlerCallBack(){
			@Override
			public void onCallBack(String json) {
			 
			}
			@Override
			public void onErro() {
				
			}});
	}
	
	//定位
	public static void uGetGPS(AsyncHttpClient httpUtils){
		
		
		new HttpHandler(null,httpUtils,new LocationModel(),new HttpHandlerCallBack(){
			@Override
			public void onCallBack(String json) {
				System.out.println("LocationModel"+json);
			}
			@Override
			public void onErro() {
				
			}});
	}
	
	//轨迹
	public static void getLocus(AsyncHttpClient httpUtils){
		LocusModel loc = new LocusModel();
		loc.setStartTime("20150116080000");
		  loc.setEndTime("20150116230000");
		new HttpHandler(null,httpUtils,loc,new HttpHandlerCallBack(){
			@Override
			public void onCallBack(String json) {
				System.out.println("LocusModel"+json);
			}
			@Override
			public void onErro() {
				
			}});
	}
	
	
	public static void gDecode(AsyncHttpClient httpUtils,Object object,final Handler handler){
		final Message message=handler.obtainMessage();
//		GDecode gDecode = new GDecode();
//		gDecode.setId("1");
//		gDecode.setPsw("0000");
//		gDecode.setLat(99.21);
//		gDecode.setLng(95.01);
	    //"{'c':'gDecode','id':'1','psw': '0000','lat':99.21,'lng': 95.01}";
		HttpEntity entity =  GsonUtils.toJson(object); 
		httpUtils.post(ApplicationController.getContext(), url, entity, Constant.CONTENTTYPE, 
		new ResponseJsonHandler(httpUtils, handler, new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				System.out.println(json);
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {
//						String data = GsonUtils.getData(json);
//						LeaveArray leaveListData = GsonUtils.getSingleBean(data, LeaveArray.class);
						message.what = HandlerCode.HANDLER_SUCCESS;
//						message.obj = leaveListData;
						handler.sendMessage(message);
					}else{
						handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
					e.printStackTrace();
				}
				
				
				
				
			}}));
	}
	
	private static void circle(AsyncHttpClient httpUtils,LoadingDialog loadingDialog){
//		//"positionLat":22.56795,"Circle_id":0,"positionAlarm":false,"positionRadius":3685,"positionName":"App测试1","positionLng":114.05817
//		Fencesetting fencesetting = new Fencesetting();
//		fencesetting.setType(1);
//		fencesetting.setImei("111");
//		fencesetting.setCircle_id(1);
//		String json = new Gson().toJson(fencesetting);
//		System.out.println(json);
		
		/*HttpEntity entity =  GsonUtils.toJson(new TestLoginModel()); 
		httpUtils.post(ApplicationController.getContext(), url, entity, Constant.CONTENTTYPE,new ResponseJsonHandler(httpUtils, handler, new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				System.out.println(json);
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {
						
						
					}else{
						handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
					e.printStackTrace();
				}
				
				
				
				
			}}));*/
//		System.out.println("");
		new HttpHandler(loadingDialog,httpUtils,new LoginModel(),new HttpHandlerCallBack(){
		@Override
		public void onCallBack(String json) {
			System.out.println(json);
		}
		@Override
		public void onErro() {
			
		}});
		
	}
}

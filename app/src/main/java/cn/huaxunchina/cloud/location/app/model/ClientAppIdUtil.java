package cn.huaxunchina.cloud.location.app.model;

import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;

import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.NotifiInfo.NotifiExtras;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.location.app.model.post.ClientAppId;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;
import cn.huaxunchina.cloud.location.app.network.HttpHandlers;
import cn.huaxunchina.cloud.location.app.network.HttpHandlers.HttpHandlerCallBacks;

public class ClientAppIdUtil {
	
	public static void setLbsClientAppId(String appid){
		//UserManager manager = LoginManager.getInstance().getUserManager();
		AsyncHttpClient httpUtils = new AsyncHttpClient();
		System.out.println("<<<<<<<<<<<<<<<<定位平台clientAppId2:=======>["+appid+"]");
		//if(manager.curRoleId.equals(String.valueOf(Constant.RolesCode.PARENTS))){
		
		String imei = LoginManager.getInstance().getImei();
		
		if(!TextUtils.isEmpty(imei)){
		ClientAppId client = new ClientAppId();
		client.setClientAppId(appid);
		new HttpHandler(null,httpUtils,client,new HttpHandlerCallBack(){
		@Override
		public void onCallBack(String json) {
			System.out.println("<<<<<<<<<<<<定位平台clientAppId2:>>>>>>>>>>>>>>>>成功>>>>>>>>>"+json);
		}
		@Override
		public void onErro() {
			System.out.println("<<<<<<<<<<<<定位平台clientAppId2报错了>>>>>>>>>>>>>>>>>>>>>>>>");
		}});
		}
		//}
	}
	
	
	public static void feedback(NotifiExtras msgExtras){
		if(msgExtras==null)return;
		if(msgExtras.getC()==100){
		ClientAppId client = new ClientAppId();
		client.setC("clientAppId");
		AsyncHttpClient httpUtils = new AsyncHttpClient();
		new HttpHandlers(httpUtils, client, new HttpHandlerCallBacks(){
		@Override
		public void onCallBack(String json) {}
		@Override
		public void onErro() {
		}});
	 }
	}

}

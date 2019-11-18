package cn.huaxunchina.cloud.app.getui;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.activity.Loading;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.getui.view.UiService;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.NotifiInfo;
import cn.huaxunchina.cloud.app.model.NotifiInfo.NotifiExtras;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.model.ClientAppIdUtil;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 
/**
 * 个推服务的广播
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月11日 上午10:42:44 
 *
 */
public class GetuiReceiver extends BroadcastReceiver {
	
	
	private NotificationManager nManager;
	private static final int timeOut = 1000 * 10;
	
	private final static int TYPE_ONE = 1;//1单个用户 
	private final static int TYPE_NUM = 2;//2多个用户  
	private final static int TYPE_TAG = 3;//3 群体
	 
	

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle bundle = intent.getExtras();
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");
			String taskid = bundle.getString("taskid");
			String messageid = bundle.getString("messageid");
			// smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
			boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
			System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));
			if (payload != null&&!payload.equals("")) {
				//通知服务端收到消息了
				 String data = new String(payload);
				 System.out.println("推送消息:============>"+data);
				//Toast.makeText(context, data+"", Toast.LENGTH_LONG).show();
				 try {
					 UserManager manager= LoginManager.getInstance().getUserManager();
					 String userId = manager.userId;
					 //没有登录用户不不做数据处理
					 if(userId==null||userId.equals("")){
						 return;
					 }
					 //Toast.makeText(context, data+"", Toast.LENGTH_LONG).show();
					 
					 //创建Notification
					 String ns = Context.NOTIFICATION_SERVICE; 
					 nManager = (NotificationManager)context.getSystemService(ns);
					 Bundle bundles = null;
					 PendingIntent contentIntent = null;
					 int icon = R.drawable.notification_icon; 
				     Notification notification =  new Notification(); 
				     notification.icon=icon;
				     notification.flags |= Notification.FLAG_AUTO_CANCEL;
				     //震动
				     notification.defaults |= Notification.DEFAULT_VIBRATE; 
				     long[] vibrate = {0,100,200,300}; 
				     notification.vibrate = vibrate ;
				     //声音
				     //notification.sound=Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.mm); 
				     notification.defaults=Notification.DEFAULT_SOUND;
				     
					 //解析数据
					 NotifiInfo info = GsonUtils.getSingleBean(data, NotifiInfo.class);
					 NotifiExtras msgExtras = info.getExtras();
					 int groupType = msgExtras.getT();//消息的类别(是否 tag用户和个人用户)
					 int categoryId = msgExtras.getC();//1=通知公告    /2=考勤 /3=成绩
					 String id = msgExtras.getR()+"";//详情id
					 String msgContent = info.getMsg();
					 System.out.println("GetuiReceiver:"+categoryId);
					 
					 List<Activity> acititylist = ApplicationController.getActivityList();
					 int isLogn = acititylist.size();
					 
					 if(categoryId==100){//=============================================定位平台
						
					//通知服务端收到推送
			         if(isLogn>0){//是否在线
			        	 ClientAppIdUtil.feedback(msgExtras);
				    	 Intent serviceIntent = new Intent(context, UiService.class); 
				    	 bundles = new Bundle();  
						 bundles.putString("categoryId",categoryId+""); 
						 bundles.putString("msgContent", msgContent);
						 bundles.putString("id", id);
						 serviceIntent.putExtras(bundles);
				         context.startService(serviceIntent);	 
			         }else{
			        	 int notif_id = (int)(Math.random()*10000000);
				    	 intent = new Intent(context,Loading.class); 
						 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK
						 bundles = new Bundle();  
						 bundles.putString("categoryId",categoryId+""); 
						 bundles.putString("id", id);
						 intent.putExtras(bundles);
						 System.out.println("推送id:===="+id);
						 contentIntent = PendingIntent.getActivity(context, notif_id, intent, PendingIntent.FLAG_UPDATE_CURRENT); 
					     //notification.setLatestEventInfo(context , "iSchoolyard",msgContent,contentIntent);
					     nManager.notify(notif_id, notification); 
			         }
			         
			         return;
					         
					 }
					     
					     
					 switch (groupType) {
					case TYPE_ONE://个人
					case TYPE_NUM://多人
						List<String> list = info.getExtras().getIds();
						if(list!=null&&list.size()>0){
							updateMsgStatus(taskid,context);
							
							if(isExist(userId,list)){//判断是否为当前用户
								 //通知服务端收到推送
							     System.out.println("acititylist:====="+acititylist.size()+"");
							     if(isLogn>0){//表示已登陆 使用全局变量
							    	 Intent serviceIntent = new Intent(context, UiService.class); 
							    	 bundles = new Bundle();  
									 bundles.putString("categoryId",categoryId+""); 
									 bundles.putString("msgContent", msgContent);
									 bundles.putString("id", id);
									 serviceIntent.putExtras(bundles);
							         context.startService(serviceIntent);
							         
							     }else{
							    	 
							    	 int notif_id = (int)(Math.random()*10000000);
							    	 intent = new Intent(context,Loading.class); 
									 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK
									 bundles = new Bundle();  
									 bundles.putString("categoryId",categoryId+""); 
									 bundles.putString("id", id);
									 intent.putExtras(bundles);
									 System.out.println("推送id:===="+id);
									 contentIntent = PendingIntent.getActivity(context, notif_id, intent, PendingIntent.FLAG_UPDATE_CURRENT); 
								     //notification.setLatestEventInfo(context , "iSchoolyard",msgContent,contentIntent);
								     nManager.notify(notif_id, notification); 
							     }
							}
						}
						break;
					case TYPE_TAG://tag
						//通知服务端收到推送
						List<String> _list = info.getExtras().getIds();
						List<String> _tags =manager.tags;
						if(_list!=null&&_list.size()>0){
							updateMsgStatus(taskid,context);
						     if(isExist(_tags,_list)){//判断是否为当前用户
							     //通知服务端收到推送
								 
							     System.out.println("acititylist:====="+acititylist.size()+"");
							     if(isLogn>0){//表示已登陆 使用全局变量
							    	 Intent serviceIntent = new Intent(context, UiService.class); 
							    	 bundles = new Bundle();  
									 bundles.putString("categoryId",categoryId+""); 
									 bundles.putString("msgContent", msgContent);
									 bundles.putString("id", id);
									 serviceIntent.putExtras(bundles);
							         context.startService(serviceIntent);
							         
							     }else{
							    	 
							    	 int notif_id = (int)(Math.random()*10000000);
							    	 intent = new Intent(context,Loading.class); 
									 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK
									 bundles = new Bundle();  
									 bundles.putString("categoryId",categoryId+""); 
									 bundles.putString("id", id);
									 System.out.println("推送id:===="+id);
									 intent.putExtras(bundles);
									 contentIntent = PendingIntent.getActivity(context, notif_id, intent, PendingIntent.FLAG_UPDATE_CURRENT); 
								     //notification.setLatestEventInfo(context , "iSchoolyard",msgContent,contentIntent);
								     nManager.notify(notif_id, notification); 
							     }
							     
						     }
						}
						break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			}
			break;
		case PushConsts.GET_CLIENTID:
			String cid = bundle.getString("clientid");
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			//Toast.makeText(context, cid, Toast.LENGTH_LONG).show();
			LoginManager manager = LoginManager.getInstance();
			if(manager.getTagStatus().equals("0")){//标签发生变化
				List<String> tags = manager.getJsonTags();
				int size = tags.size();
				Tag[] tagParam = new Tag[size];
				for (int i=0;i<size;i++) {
					Tag t = new Tag();
					t.setName(tags.get(i));
					tagParam[i] = t;
				}
				//System.out.println(tagParam.length);
				//设置标签
				PushManager.getInstance().setTag(context, tagParam);
				manager.setTagStatus();
			}
			//更改ClientId
			if(!TextUtils.isEmpty(cid)){
			updateClientId(cid,context);	
			}
			break;
		case PushConsts.THIRDPART_FEEDBACK:
			break;
		}
	}
	
	
	
	 
	
	 
	
	//更新clientId
	public static void updateClientId(String clientId,final Context context){
		UserManager uManager = LoginManager.getInstance().getUserManager();
		String clientAppId = uManager.clientAppId;
		String userId = uManager.userId;
		//如果clentId 为空,或者与服务端clentId
		if(clientAppId.equals("")&&!userId.equals("")||!clientId.equals(clientAppId)&&!userId.equals("")){
	    //Toast.makeText(context, "clientAppId:"+clientId, Toast.LENGTH_LONG).show();
	   //System.out.println("clientId:"+clientId);
		PreferencesHelper  pre = new PreferencesHelper(ApplicationController.getContext(),UserUtil.getUserId());
		LoginManager.getInstance().setClientAppId(pre, clientId);
		
		AsyncHttpClient httpUtils=  new AsyncHttpClient();
		httpUtils.addHeader("x-requested-with", "XMLHttpRequest");
		httpUtils.setTimeout(timeOut);
		RequestParams params = new RequestParams();
		params.put("userId",userId);
		params.put("clientAppId",clientId);//
		String url = UserUtil.getRequestUrl()+RequestCode.UPDATECLIENTAPP;
		 httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					String jsonData = response.toString();
					System.out.println("clientAppId1:=======>"+jsonData);
					 
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
					System.out.println("clientAppId1:===statusCode====>"+statusCode);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					System.out.println("clientAppId1:===statusCode====>"+statusCode);
				}
			});
		 if(!TextUtils.isEmpty(clientId)){
		 ClientAppIdUtil.setLbsClientAppId(clientId); 
		 }
		 
		}
		
		 
		//上次lbs ClientAppid
		 
	}
	
	String url = UserUtil.getMsgUrl();
	
	//更新获取消息的状态
	private void updateMsgStatus(String taskid,final Context context){
		UserManager uManager = LoginManager.getInstance().getUserManager();
		String userId = uManager.userId;
		AsyncHttpClient httpUtils = new AsyncHttpClient();
		httpUtils.addHeader("x-requested-with", "XMLHttpRequest");
		httpUtils.setTimeout(timeOut);
		RequestParams params = new RequestParams();
		params.put("userId",userId);
		params.put("taskId",taskid);
		 httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					//String jsonData = response.toString();
					
					//System.out.println(jsonData);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}
			});
	}
	
	
	/**
	 *   是否存在 
	  * @param id
	  * @param ids
	  * @return
	 */
	private boolean isExist(String id,List<String> ids){
		boolean is_exist = false;
		int size = ids.size();
		for(int i=0;i<size;i++){
			if(id.equals(ids.get(i))){
				is_exist=true;
			}
		}
		return is_exist;
		
	}
	//标签
	private boolean isExist(List<String> tags,List<String> ids){
		if(tags==null||ids==null) return false;
		boolean is_exist = false;
		int id_size = ids.size();
		int tag_size = tags.size();
		
		for(int i=0;i<id_size;i++){
			String id = ids.get(i);
			for(int j=0;j<tag_size;j++){
				String tag = tags.get(j);
				if(id.equals(tag)){
					is_exist=true;
				}
			}
		}
		return is_exist;
		
	}
	
}

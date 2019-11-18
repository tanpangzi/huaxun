package cn.huaxunchina.cloud.app.getui;


import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.network.TANetWorkUtil;

import com.igexin.sdk.PushManager;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
/**
 * 启动app的服务
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月10日 下午5:32:04 
 *
 */
public class PlusService extends Service {
	

		@Override
		public IBinder onBind(Intent arg0) {
			Log.d("onBind", ">>>>>>>>>");
			return null;
		}

		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			Log.d("onStartCommand", ">>>>>>>>>" + startId + ">>>" + flags);
			return START_STICKY;
		}
		 

		@Override
		public void onCreate() {
			Log.d("onCreate", ">>>>>>>>>A.");
			super.onCreate();
			//启动服务
			//Toast.makeText(getBaseContext(), "有网啦2", Toast.LENGTH_LONG).show();
			if(TANetWorkUtil.isNetworkAvailable(getBaseContext())){
				//启动个推服务 是否需要启动服务
				String msg = LoginManager.getInstance().getIsMsg();
				if(msg.equals("")||msg.equals("YES")){
				PushManager.getInstance().initialize(ApplicationController.getContext());
				PushManager.getInstance().turnOnPush(ApplicationController.getContext());
				}
				
				
			}
			 
		}

		
		@Override
		public Intent registerReceiver(BroadcastReceiver receiver,
			IntentFilter filter) {
		   // TODO Auto-generated method stub
			//Intent.ACTION_TIME_TICK，这个广播每分钟发送一次，我们可以每分钟检查一次Service的运行状态，如果已经被结束了，就重新启动Service
			IntentFilter filters = new IntentFilter(Intent.ACTION_TIME_TICK); 
		    MyBroadcastReceiver receivers = new MyBroadcastReceiver(); 
		    registerReceiver(receivers, filters); 
		    
		return super.registerReceiver(receiver, filter);
		}
		
		@Override
		public void onDestroy() {
			Log.d("onDestroy", ">>>>>>>>>B.");
			super.onDestroy();
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onStart(Intent intent, int startId) {
			Log.d("onStart", ">>>>>>>>>C.");
			super.onStart(intent, startId);
		}
		
		/**
		 * 
		 * 对进程服务的一个监听
		 * @author jiangyizhao@huaxunchina.cn
		 * @date 2014年9月10日 下午5:54:59 
		 *
		 */
		private class  MyBroadcastReceiver  extends BroadcastReceiver {

			boolean isServiceRunning = false; 

			public void onReceive(Context context, Intent intent) 
			{
				    if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) { 
				    //检查Service状态 
				    ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE); 
				    for (RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)) { 
					    if("cn.huaxunchina.cloud.app.getui.PlusService".equals(service.service.getClassName())) 
					     { 
					     isServiceRunning = true; 
					    } 
				     } 
				    if (!isServiceRunning) { 
				    Intent i_ent = new Intent(context, PlusService.class); 
				    context.startService(i_ent); 
				    } 

				}
				
			}
			
		}

}

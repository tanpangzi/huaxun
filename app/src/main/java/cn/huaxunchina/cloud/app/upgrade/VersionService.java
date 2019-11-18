package cn.huaxunchina.cloud.app.upgrade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.model.VersionInfo;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * 版本更新
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年10月11日 上午8:53:42 
 *
 */
@SuppressLint("DefaultLocale")
public class VersionService extends Service {

	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private Context contenxt;
	private boolean rundown = true;
	private int notification_id = 19172439;
	private int count = 0;
	private LoadingDialog loadingDialog;
	private String type = "1";
	private String APPNAME = "iSchoolyard.apk";
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Context contenxt = this.getBaseContext();
		this.contenxt=contenxt;
		if(intent!=null){
		type = intent.getStringExtra("type");
		}
		loadingDialog = new LoadingDialog(contenxt);
		if(type.equals("2")){
			loadingDialog.getDialog().getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			loadingDialog.show();
			 
		}
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		doVersion();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler version_handler = new Handler(){
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.VERSION_SUCCESS:
				loadingDialog.dismiss();
				VersionInfo info = (VersionInfo)msg.obj;
				String versionName = ApplicationController.versionName;
				if(versionName!=null){
					if(Double.valueOf(versionName)<info.getVersion()){
						rundown=true;
						_count=0;
						count=0;
						updataeDialog(info);
					}else{
						if(type.equals("2")){
							Toast.makeText(contenxt, "已是最新版本", 0).show();
						}
					}
				}
				break;
			case HandlerCode.VERSION_ERROR:
				loadingDialog.dismiss();
				break;
			case 3://开始下载
				setUpNotification();
				new Thread(
						new Runnable() {
							@Override
							public void run() {
								if(rundown){
									loadFile(RequestCode.DOWNLOAD_CLIENT);
								}
							}
						}).start();
				break;
			case 4://下载进度
				int count = (Integer)msg.obj;
				if(_count<count){
					_count=count;
					mNotification.contentView.setProgressBar(R.id.n_progress, 100, _count, false);
				    mNotification.contentView.setTextViewText(R.id.n_title, count+"%");
				    mNotificationManager.notify(notification_id, mNotification);
				}
				break;
			case 5://下载成功
				rundown = false;
				openFile(Constant.UPDATE_PATH+"/"+APPNAME);
				mNotificationManager.cancel(notification_id);
				break;
			case 6://下载失败
				rundown = false;
				mNotificationManager.cancel(notification_id);
				break;
			}
		}};
		int _count=0;
	 
		
		
		
		@SuppressWarnings("deprecation")
		private void setUpNotification() {
			int icon = R.drawable.notification_icon;
			CharSequence tickerText = "开始下载";
			long when = System.currentTimeMillis();
			mNotification = new Notification(icon, tickerText, when);
			// 放置在"正在运行"栏目中
			mNotification.flags = Notification.FLAG_ONGOING_EVENT;
			mNotification.contentView = new RemoteViews(contenxt.getPackageName(), R.layout.notification_version);
			mNotification.contentView.setTextViewText(R.id.n_title, "0%");
			mNotification.contentView.setProgressBar(R.id.n_progress, 100, 0, false);
			// 指定个性化视图
			Intent intent = new Intent(this, VersionService.class);
			PendingIntent contentIntent = PendingIntent.getActivity(contenxt, notification_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			// 指定内容意图
			mNotification.contentIntent = contentIntent;
			mNotificationManager.notify(notification_id, mNotification);
			 
		}
		
		public void doVersion(){
			if (!Utils.isNetworkConn()) {
				return;
			}
			final Message message = version_handler.obtainMessage();
			AsyncHttpClient httpUtils = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			String url = RequestCode.CLIENT_INFO;
			httpUtils.post(url, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					try {
					    String jsonData = response.toString();
						String code = GsonUtils.getCode(jsonData);
						if (code.equals(HttpResultStatus.SUCCESS)){
						String data = GsonUtils.getData(jsonData);
						VersionInfo info = GsonUtils.getSingleBean(data,VersionInfo.class);
						UserUtil.setVersionName(info.getVersion()+"");
						message.what = HandlerCode.VERSION_SUCCESS;
						message.obj = info;
						version_handler.sendMessage(message);
						}else{
							message.what = HandlerCode.VERSION_ERROR;
							version_handler.sendMessage(message);	
						}
					} catch (Exception e) {
						e.printStackTrace();
						message.what = HandlerCode.VERSION_ERROR;
						version_handler.sendMessage(message);
					}
					 
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
					super.onFailure(statusCode, headers, throwable, errorResponse);
					message.what = HandlerCode.VERSION_ERROR;
					version_handler.sendMessage(message);
					 
				}
				
			});
		}
		
		
		
		public void updataeDialog(VersionInfo info){
			final Message message = version_handler.obtainMessage();
			List<String> des = info.getDesription();
			String newVContent = "";
			for (String str:des){
				newVContent=str+"\n"+newVContent;
			}
			Dialog dialog = new AlertDialog.Builder(
					contenxt)
					.setTitle("新版本更新内容")
					.setMessage(newVContent)
					.setPositiveButton("马上更新",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									message.what = 3;
									version_handler.sendMessage(message);
									dialog.dismiss();
								}
							})
					.setNegativeButton("以后再说",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									
									dialog.dismiss();
								}
							}).create();
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.show();
		}
		
		
		
		
		public void loadFile(String url) {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			HttpResponse response;
			try {
				response = client.execute(get);
				HttpEntity entity = response.getEntity();
				float length = entity.getContentLength();
				InputStream is = entity.getContent();
				FileOutputStream fileOutputStream = null;
				if (is != null) {
					File file1 = new File(Constant.UPDATE_PATH);
					if(!file1.exists()){
						file1.mkdirs();
					}
					File file = new File(Constant.UPDATE_PATH+"/"+APPNAME);
					if (file.exists()) {
						file.delete();
					} else {
					file.createNewFile();
					}
					fileOutputStream = new FileOutputStream(file);
					byte[] buf = new byte[1024];//1024
					int ch = -1;
					long _count = 0;
					while ((ch = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, ch);
						_count += ch;
						
						
						count=(int) (_count*100/length);
						Message msg = version_handler.obtainMessage();
						msg.what = 4;
						msg.obj = count;
						version_handler.sendMessage(msg);
						
					}
				}
				
				//===
				Message msg = version_handler.obtainMessage();
				msg.what = 5;
				version_handler.sendMessage(msg);
				
				fileOutputStream.flush();
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (Exception e) {
				
				Message msg = version_handler.obtainMessage();
				msg.what = 6;
				version_handler.sendMessage(msg);
			}
		}
		
		
		private void openFile(String file_path) {
			/* 关闭notification通知 */
			// nm.cancel(notification_id);
			File f = new File(file_path);
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			String type = getMIMEType(f);
			intent.setDataAndType(Uri.fromFile(f), type);
			contenxt.startActivity(intent);

		}
		
		/**
		 * 文件对应类型
		 * 
		 * @param f
		 * @return
		 */
		@SuppressLint("DefaultLocale")
		private String getMIMEType(File f) {
			String type = "";
			String fName = f.getName();
			String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
			if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
					|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
				type = "audio";
			} else if (end.equals("3gp") || end.equals("mp4")) {
				type = "video";
			} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
					|| end.equals("jpeg") || end.equals("bmp")) {
				type = "image";
			} else if (end.equals("apk")) {
				type = "application/vnd.android.package-archive";
			} else {
				type = "*";
			}
			if (end.equals("apk")) {
			} else {
				type += "/*";
			}
			return type;
		}

 
}

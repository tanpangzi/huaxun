package cn.huaxunchina.cloud.app.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.Header;

import com.baidu.mapapi.SDKInitializer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.huaxunchina.cloud.app.activity.CustomException;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.model.ServerData.ServerInfo;
import cn.huaxunchina.cloud.app.tools.CrashHandler;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.location.app.model.LbsManager;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 自定义application 保存一些全局变量
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年6月26日 下午5:58:52
 * 
 */
public class ApplicationController extends Application {

	private static Context context;
	public static String versionName = null;
	public static int versionCode = 0;
	private static List<Activity> activityList = new LinkedList<Activity>();
	public static AsyncHttpClient httpUtils;
	public  AsyncHttpClient httpUtils_lbs;
	public static final int timeOut = 1000 * 10;
	private static String isLogin=null;
	private  List<ServerInfo> serverList;
	private static LbsManager lbsManager;
	
	private static String sim;
	private static String pwd;
	private static String cur_date;
	
	
	public static void setDate(Header[] headers){
		if(headers!=null){
		int length = headers.length;
		for(int i=0;i<length;i++){
			String name =  headers[i].getName();
			if(name.equals("Date")){
				 //TimeZone.setDefault(TimeZone.getTimeZone("GMT+0"));
				 String date_string =  headers[i].getValue();
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			     Date date= new Date(new Date(date_string).getTime());
			     cur_date = sdf.format(date);
			     break;
			}
		}
		}
	}
	
	public String getCurDate(){
		return cur_date;
	}
	
	

	public static String getSim() {
		return sim;
	}

	public static void setSim(String sim) {
		ApplicationController.sim = sim;
	}

	public static String getPwd() {
		return pwd;
	}

	public static void setPwd(String pwd) {
		ApplicationController.pwd = pwd;
	}

	public List<ServerInfo> getServerList() {
		return serverList;
	}

	public void setServerList(List<ServerInfo> serverList) {
		this.serverList = serverList;
	}

	public static void setIsLogin(){
		isLogin="YES";
	}
	
	public  String getIsLogin(){
		return isLogin;
	}

	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	public static void exit() {
		//android.os.Process.killProcess(android.os.Process.myPid());
		for (Activity activity : activityList) {
			if (null != activity)
				activity.finish();
		}
		System.exit(0);
	}
	
	public static List<Activity> getActivityList(){
		return activityList;
	}
	
	public static AsyncHttpClient getHttpUtils(){
		return httpUtils;
	}
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//baidu 地图sdk初始化
		SDKInitializer.initialize(this);  
		context = getApplicationContext();
		PackageInfo packageInfo;
		try {// 获取包信息
			packageInfo = context.getPackageManager().getPackageInfo(this.getPackageName(), 0);
			versionName = packageInfo.versionName;
			versionCode =packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Utils.isSdExist() == false) {
			Constant.PAXY_PATH = context.getFilesDir().getAbsolutePath()+ "/paxy/";
			Constant.IMGCACHE_SAVE_PATH = Constant.PAXY_PATH + "imgcache/";
			Constant.DATA_CACHE_SAVE_PATH = Constant.PAXY_PATH + "datacache/";
			Constant.UPDATE_PATH = Constant.PAXY_PATH + "update/";
			Constant.LOG = Constant.PAXY_PATH + "log/";
		}

		// 全局变量
		httpUtils = new AsyncHttpClient();
		httpUtils.addHeader("x-requested-with", "XMLHttpRequest");
		PersistentCookieStore cookieStore = new PersistentCookieStore(this);  
		httpUtils.setCookieStore(cookieStore);
		httpUtils.setTimeout(timeOut);
		
		
		httpUtils_lbs= new AsyncHttpClient();
		httpUtils_lbs.addHeader("x-requested-with", "XMLHttpRequest");
		PersistentCookieStore lbs_cookieStore = new PersistentCookieStore(this);  
		httpUtils_lbs.setCookieStore(lbs_cookieStore);
		
		httpUtils.setTimeout(timeOut);
		// 捕获程序异常
		CustomException customException = CustomException.getInstance();
		customException.init(getApplicationContext());
		// PackageManager packageManager = getPackageManager();
		
		//Log日志捕获
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
		
		//==
		initImageLoader(context);
 

	}
	
	
	@SuppressWarnings("deprecation")
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		 


	}
	
	 

	public static  Context getContext() {
		return context;
	}
	
	 

}

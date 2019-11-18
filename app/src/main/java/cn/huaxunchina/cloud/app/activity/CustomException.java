package cn.huaxunchina.cloud.app.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

//捕获程序运行异常类
public class CustomException implements UncaughtExceptionHandler {

	// 获取application 对象；
	private Context mContext;

	private UncaughtExceptionHandler defaultExceptionHandler;
	// 单例声明CustomException;
	private static CustomException customException;

	private CustomException() {
	}

	public static CustomException getInstance() {
		if (customException == null) {
			customException = new CustomException();
		}
		return customException;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable exception) {
		// 如果发生异常,阻止系统崩溃并且重新启动程序
		if (defaultExceptionHandler != null) {
			 /*Intent intent = new Intent();
			 intent.setClass(mContext, Login.class);
			 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 mContext.startActivity(intent);
			 Log.e("tag", "exception >>>>>>>" +
			 exception.getLocalizedMessage());
			 android.os.Process.killProcess(android.os.Process.myPid());
			 System.exit(0);*/

			// 将异常抛出，则应用会弹出异常对话框.这里先注释掉
			defaultExceptionHandler.uncaughtException(thread, exception);

		}

	}

	public void init(Context context) {
		mContext = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);

	}

}
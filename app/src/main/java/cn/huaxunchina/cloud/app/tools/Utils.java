package cn.huaxunchina.cloud.app.tools;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * TODO 常用的工具
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年6月26日 下午6:06:17
 * 
 */
public class Utils {

	/**
	 * TODO(描述)
	 * 
	 * @return
	 */
	public static boolean isSdExist() {
		return Environment.getExternalStorageState().equals("mounted");
	}

	/**
	 * TODO(描述)
	 * 
	 * @return
	 */
	public static boolean isNetworkConn() {
		ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationController
				.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		NetworkInfo mobNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean activeNetState = false;
		if (activeNetInfo != null) {
			if (activeNetInfo.getState() == State.CONNECTED) {
				activeNetState = true;
			}
		}
		boolean mobNetState = false;
		if (mobNetInfo != null) {
			if (mobNetInfo.getState() == State.CONNECTED) {
				mobNetState = true;
			}
		}
		return activeNetState || mobNetState;
	}

	public static void netWorkMessage(Handler handler) {
		final Message message = handler.obtainMessage();
		message.what = HandlerCode.HANDLER_NET;
		handler.sendMessage(message);
	}

	@SuppressLint("ShowToast")
	public static void netWorkToast() {// Activity activity
		Toast.makeText(ApplicationController.getContext(), "网络异常",0).show();
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}

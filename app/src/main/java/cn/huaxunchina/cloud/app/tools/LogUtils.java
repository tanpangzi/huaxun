package cn.huaxunchina.cloud.app.tools;


import java.io.File;
import java.io.FileWriter;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import android.os.Environment;
import android.util.Log;

/**
 * TODO 日志文件的工具类
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年6月26日 下午6:04:00 
 *
 */
public class LogUtils {

	private static boolean isLog = true;
	
	/**
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg){
		if(isLog){
			Log.d(tag, msg);
		}
	}
	
	/**
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg){
		if(isLog){
			Log.e(tag, msg);
		}
	}
	
	/**
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg){
		if(isLog){
			Log.i(tag, msg);
		}
	}
	
	/**
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg){
		if(isLog){
			Log.w(tag, msg);
		}
	}
	
	/**
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg){
		if(isLog){
			Log.v(tag, msg);
		}
	}
	
	/**
	 * TODO 写入本地文件
	 * @param msg
	 */
	public static void LogFile(String msg){
		//sd是否存在 保存路径的切换
		if(Utils.isSdExist() == false){
			Constant.PAXY_PATH = ApplicationController.getContext().getFilesDir().getAbsolutePath() +"/paxy/";
		}else{
			Constant.PAXY_PATH =Environment.getExternalStorageDirectory().getPath()+ "/paxy/";
		}
		File file=new File(Constant.PAXY_PATH +"logs/");
		if(!file.exists()){
			file.mkdirs();
		}
		file=new File(Constant.PAXY_PATH +"logs/log.txt");
		try {//
			file.createNewFile();
			FileWriter writer=new FileWriter(file);
			writer.write(msg);
			writer.write("\r\n");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

package cn.huaxunchina.cloud.app.tools;

import cn.huaxunchina.cloud.app.application.ApplicationController;
/**
 * 长度单位的转化类
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年6月26日 下午6:00:58 
 *
 */
public class DisplayUtil {

	 private static final float scale = ApplicationController.getContext().getResources().getDisplayMetrics().density;
	 private static final float fontScale = ApplicationController.getContext().getResources().getDisplayMetrics().scaledDensity;  

	 /**
	  * px转化为dip
	  * @param pxValue
	  * @return
	  */
	 public static int px2dip(float pxValue) {
		
	  return (int) (pxValue / scale + 0.5f);
	 }

	 /**
	  * dip转化为xp
	  * @param dipValue
	  * @return
	  */
	 public static int dip2px(float dipValue) {
	  return (int) (dipValue * scale + 0.5f);
	 }

	 /**
	  * 
	  * @param pxValue
	  * @return
	  */
	 public static int px2sp(float pxValue) {
	  return (int) (pxValue / fontScale + 0.5f);
	 }

	 /**
	  * 
	  * @param pxValue
	  * @return
	  */
	 public static int sp2px(float spValue) {
	  return (int) (spValue * fontScale + 0.5f);
	 }
	 
	 /**
	  * 获取屏幕的宽度
	  * @return widthPixels
	  */
	 public static int getWindowsWidth(){
		 return ApplicationController.getContext().getResources().getDisplayMetrics().widthPixels;
	 }
	 
	 /**
	  * 获取屏幕的高度
	  * @return heightPixels
	  */
	 public static int getWindowsHeight(){
		 return ApplicationController.getContext().getResources().getDisplayMetrics().heightPixels;
	 }
	 
}

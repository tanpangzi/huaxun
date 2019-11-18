package cn.huaxunchina.cloud.app.imp;

import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;

/**
 * 考勤
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月22日 上午10:31:45 
 *
 */
public interface AttendanceResponse {

	/**
	  * 考勤详情
	  * @param studentId 学生id
	  * @param classId 班级id
	  * @param signDate 时间
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void getAttInfoList(String studentId, String classId, String signDate, AsyncHttpClient httpUtils, Handler handler);
}

package cn.huaxunchina.cloud.app.imp;

import com.loopj.android.http.AsyncHttpClient;

import android.os.Handler;


public interface SyllabusResponse {
	 
	
	/**
	 * TODO(描述)  家长角色 只需要传 classId
	 *             老师 需要传 classId和teacherId
	  * @param classId 班级Id
	  * @param teacherId 老师Id
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void syllabusList(String classId, String teacherId, AsyncHttpClient httpUtils, final Handler handler);

}

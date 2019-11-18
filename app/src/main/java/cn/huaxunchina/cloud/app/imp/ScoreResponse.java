package cn.huaxunchina.cloud.app.imp;

import com.loopj.android.http.AsyncHttpClient;

import android.os.Handler;

/**
 * 考试管理接口
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月28日 下午12:30:33 
 *
 */
public interface ScoreResponse {
	
	/**
	 * TODO(描述) 
	  * @param studentId 学生id
	  * @param teacherId 老师id
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void scoreListData(String studentId, String start, String limit, AsyncHttpClient httpUtils, final Handler handler);
	/**
	 * TODO(描述) 
	  * @param examId 考试id
	  * @param studentId 学生id
	  * @param teacherId 老师id
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void scoreDetail(String examId, String studentId, String teacherId, AsyncHttpClient httpUtils, final Handler handler);
    /**
      * TODO(描述) 
      * @param studentId 学生id
      * @param examId 考试id
      * @param httpUtils
      * @param handler
     */
	public abstract void fiveScores(String studentId, String examId, AsyncHttpClient httpUtils, final Handler handler);

}

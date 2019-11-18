package cn.huaxunchina.cloud.app.imp.interaction;

import com.loopj.android.http.AsyncHttpClient;

import android.app.Activity;
import android.os.Handler;

/**
 * 家校互动模块接口
 * @author zoupeng@huaxunchina.cn
 *
 * 2014-8-27 下午5:56:54
 */
public interface HomeSchoolInterface {

	void getHomeSchoolList(Activity activity, AsyncHttpClient httpUtils, Handler handler, String start, String limit, String homeSchoolId, String roleFlag);//获取家校互动列表
	void sumbitHomeSchool(Activity activity, AsyncHttpClient httpUtils, Handler handler, String receiver, String studentId, String title, String content, String isPrivate);//发布家校互动信息
	void getHomeSchoolDetail(Activity activity, AsyncHttpClient httpUtils, Handler handler, String themeId);//获取家校互动详情
	void getTeacherAndSubject(Activity activity, AsyncHttpClient httpUtils, Handler handler, String studentId);//获取家校互动提问老师与科目
	void sumbitCommentList(Activity activity, AsyncHttpClient httpUtils, Handler handler, String themeId, String content);//發佈评论数据
	
}

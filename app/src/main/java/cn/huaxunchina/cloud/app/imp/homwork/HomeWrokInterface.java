package cn.huaxunchina.cloud.app.imp.homwork;

import com.loopj.android.http.AsyncHttpClient;

import android.app.Activity;
import android.os.Handler;

/**
 * 家庭作业模块接口
 * @author zoupeng@huaxunchina.cn
 *
 * 2014-8-18 下午8:08:56
 */
public interface HomeWrokInterface {
	void getHomeWrokList(Activity activity, AsyncHttpClient httpUtils, Handler handler, String sta, String limit, String classId);// 获取家庭作业列表数据
	void getSumbitHomeWrokCourse(Activity activity, AsyncHttpClient httpUtils, Handler handler);//获取老师所交科目
	void getSumbitHomeWrok(Activity activity, AsyncHttpClient httpUtils, final Handler handler, String content, String tips, String gradeId, String courseId, String courseName, String classId);// 发布家庭作业数据
	void getSumbitHomeWrokDetail(Activity activity, AsyncHttpClient httpUtils, Handler handler, String HomeWrokId);//获取家庭作业详情
	void delHomeWrokList(Activity activity, AsyncHttpClient httpUtils, Handler handler, String homeWorkId);// 删除家庭作业列表数据
	
}

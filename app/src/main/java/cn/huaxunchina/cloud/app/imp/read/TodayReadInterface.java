package cn.huaxunchina.cloud.app.imp.read;


import com.loopj.android.http.AsyncHttpClient;

import android.app.Activity;
import android.os.Handler;
/**
 * 通知通告模块接口定义类
 * @author zoupeng@huaxunchina.cn
 *
 * 2014-8-14 下午4:15:37
 */
public interface TodayReadInterface {
	void getDailyReadList(Activity activity, AsyncHttpClient httpUtils, Handler handler, String start, String limit);//获取每日一读列表
	void getToadySaveList(Activity activity, AsyncHttpClient httpUtils, Handler handler, String start, String limit);//获取每日一读收藏夹数据列表
	void getToadyDetail(Activity activity, AsyncHttpClient httpUtils, Handler handler, String readId);//获取每日一读详情
	void collectTodayRead(Activity activity, AsyncHttpClient httpUtils, Handler handler, int readId); //收藏每日一读文章
	void delCollectTodayRead(Activity activity, AsyncHttpClient httpUtils, Handler handler, int readId); //取消收藏每日一读文章

}

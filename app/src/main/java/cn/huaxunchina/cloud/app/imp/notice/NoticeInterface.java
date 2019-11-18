package cn.huaxunchina.cloud.app.imp.notice;



import com.loopj.android.http.AsyncHttpClient;

import android.app.Activity;
import android.os.Handler;

public interface NoticeInterface {
	void getNoticeList(Activity activity, AsyncHttpClient httpUtils, Handler handler, String roleId, String sta, String limit, String notcieType, String role);//获取通知公告列表信息
	void getNoticeDetail(Activity activity, AsyncHttpClient httpUtils, Handler handler, String noticeId);//获取通知公告详情数据
	void SumbitNoticeMessage(Activity activity, AsyncHttpClient httpUtils, Handler handler, String content, String title, String noticeType, String classId, String fileName);//发布公告信息

}

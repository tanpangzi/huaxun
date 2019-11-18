package cn.huaxunchina.cloud.app.imp.notice;

import java.io.File;
import java.io.FileNotFoundException;

import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.imp.MyResponseHandler;
import cn.huaxunchina.cloud.app.model.notice.NoticeArray;
import cn.huaxunchina.cloud.app.model.notice.NoticeDetailContent;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * 通知通告模块实现接口类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-14 下午4:15:11
 */
public class NoticeImpl implements NoticeInterface {
	/**
	 * 获取通知通告列表数据
	 */
	@Override
	public void getNoticeList(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,String roleId, String sta, String limit, String notcieType,String role) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("start", sta);
		params.put("limit", limit);
		if (role.equals(String.valueOf(RolesCode.PARENTS))) {
			params.put("studentId", roleId);
		} else {
			params.put("teacherId", roleId);
		}
		params.put("noticeType", notcieType);
		String url = UserUtil.getRequestUrl()+RequestCode.NOTICE_AJAXNOTICELIST;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {
						String data = GsonUtils.getData(json);
						NoticeArray loginData = GsonUtils.getSingleBean(data, NoticeArray.class);
						message.what = HandlerCode.HANDLER_SUCCESS;
						message.obj = loginData;
						handler.sendMessage(message);
					}else if(code.equals("999")&&activity!=null){
						activity.startActivity(new Intent(activity,Login.class));
					}else{
						handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
					e.printStackTrace();
				}
			}}));
	}

	/**
	 * 获取通知通告详情数据
	 */
	@Override
	public void getNoticeDetail(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,String noticeId) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("noticeId", noticeId);
		String url = UserUtil.getRequestUrl()+RequestCode.NOTICE_AJAXGETNOTICE;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String data = GsonUtils.getData(json);
					NoticeDetailContent noticeDetailData = GsonUtils.getSingleBean(data,NoticeDetailContent.class);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = noticeDetailData;
					handler.sendMessage(message);
				}else if(code.equals("999")&&activity!=null){
					activity.startActivity(new Intent(activity,Login.class));
				}else{
					handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
				e.printStackTrace();
			}
		}}));

	}

	/**
	 * 发布公告
	 */

	@Override
	public void SumbitNoticeMessage(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,String content, String title, String noticeType, String classId,String fileName) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("content", content);
		params.put("title", title);
		params.put("noticeType", noticeType);
		params.put("classId", classId);
		params.put("sendMessage", "false");
		params.put("sendPush", "true");
		if(fileName!=null){
		try {
			params.put("imgList", new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		String url = UserUtil.getRequestUrl()+RequestCode.NOTICE_AJAXADDNOTICE;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String data = GsonUtils.getData(json);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = data;
					handler.sendMessage(message);
				}else if(code.equals("999")&&activity!=null){
					activity.startActivity(new Intent(activity,Login.class));
				}else{
					handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
				e.printStackTrace();
			}
		}}));

	}

}

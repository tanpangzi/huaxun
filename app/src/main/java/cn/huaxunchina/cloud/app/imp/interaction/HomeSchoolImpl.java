package cn.huaxunchina.cloud.app.imp.interaction;


import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.imp.MyResponseHandler;
import cn.huaxunchina.cloud.app.model.interaction.HomeSchoolDetailData;
import cn.huaxunchina.cloud.app.model.interaction.HomeSchoolListItem;
import cn.huaxunchina.cloud.app.model.interaction.TeacherAndSubject;
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
 * 家校互动实现接口类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-27 下午5:59:16
 */
public class HomeSchoolImpl implements HomeSchoolInterface {
	 

	/**
	 * 获取家校互动列表数据
	 */
	@Override
	public void getHomeSchoolList(final Activity activity,AsyncHttpClient httpUtils,final Handler handler,String studentId, String teacherId, String start,String limit) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("start", start);
		params.put("limit", limit);
		params.put("studentId", studentId);
		params.put("teacherId", teacherId);
		String url = UserUtil.getRequestUrl()+RequestCode.READING_AJAXLASTTHEMES;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			// TODO Auto-generated method stub
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String data = GsonUtils.getData(json);
					HomeSchoolListItem itemData = GsonUtils.getSingleBean(data,HomeSchoolListItem.class);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = itemData;
					handler.sendMessage(message);
				}else if(code.equals("999")&&activity!=null){
					activity.startActivity(new Intent(activity,Login.class));
				}else{
					message.what = HandlerCode.HANDLER_ERROR;
					handler.sendMessage(message);
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
				e.printStackTrace();
			}
		}}));

	}

	/**
	 * 发布家校互动
	 */

	@Override
	public void sumbitHomeSchool(final Activity activity,AsyncHttpClient httpUtils,final Handler handler, String receiver,String studentId,
			String title, String content, String isPrivate) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("receiver", receiver);
		params.put("studentId", studentId);
		params.put("title", title);
		params.put("content", content);
		params.put("isPrivate", isPrivate);
		String url = UserUtil.getRequestUrl()+RequestCode.READING_AJAXADDTHEME;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String data = GsonUtils.getData(json);
					message.what = HandlerCode.SUMBIT_HOME_SCHOOL_QUESTIONS_SUCCESS;
					message.obj = data;
					handler.sendMessage(message);
				}else if(code.equals("999")&&activity!=null){
					activity.startActivity(new Intent(activity,Login.class));
				}else{
					handler.sendEmptyMessage( HandlerCode.HANDLER_ERROR);
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
				e.printStackTrace();
			}
		}}));

	}

	/**
	 * 获取家校互动详情
	 */

	@Override
	public void getHomeSchoolDetail(final Activity activity,AsyncHttpClient httpUtils,final Handler handler, String themeId) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("themeId", themeId);
		String url = UserUtil.getRequestUrl()+RequestCode.READING_AJAXGETTHEME;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			// TODO Auto-generated method stub
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String data = GsonUtils.getData(json);
					HomeSchoolDetailData HomeSchoolDetailData = GsonUtils.getSingleBean(data,HomeSchoolDetailData.class);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = HomeSchoolDetailData;
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
	 * 评论列表数据
	 */
	@Override
	public void sumbitCommentList(final Activity activity,AsyncHttpClient httpUtils,final Handler handler, String themeId,String content) {
		RequestParams params = new RequestParams();
		params.put("themeId", themeId);
		params.put("content", content);
		String url = UserUtil.getRequestUrl()+RequestCode.READING_COMMENTLIST;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			// TODO Auto-generated method stub
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					handler.sendEmptyMessage(HandlerCode.SUMBIT_HOME_SCHOOL_COMMENT_SUCCESS);
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
	 * 获取老师与科目
	 */

	@Override
	public void getTeacherAndSubject(final Activity activity, AsyncHttpClient httpUtils,final Handler handler, String studentId) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("studentId", studentId);
		String url = UserUtil.getRequestUrl()+RequestCode.READING_TEACHERANDSUBJECT;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					TeacherAndSubject teacherAndSubjectData = GsonUtils.getSingleBean(json,TeacherAndSubject.class);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = teacherAndSubjectData;
					handler.sendMessage(message);
				}else if(code.equals("999")&&activity!=null){
					activity.startActivity(new Intent(activity,Login.class));
				}
				else{
					handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
				}

			} catch (Exception e) {
				handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
				e.printStackTrace();
			}
		}}));
	}

}

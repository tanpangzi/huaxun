package cn.huaxunchina.cloud.app.imp.homwork;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.imp.MyResponseHandler;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWorkDetailProperty;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokArray;
import cn.huaxunchina.cloud.app.model.homewrok.HomeWrokCourseData;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * 家庭作业接口实现类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-18 下午8:10:18
 */
public class HomeWrokImpl implements HomeWrokInterface {

	/**
	 * 获取家庭作业列表数据
	 */
	@Override
	public void getHomeWrokList(final Activity activity,AsyncHttpClient httpUtils,
			final Handler handler, String sta, String limit, String classId) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("limit", limit);
		params.put("start", sta);
		params.put("Direct", "APP");
		params.put("eduClasses", classId);
		String url = UserUtil.getRequestUrl()
				+ RequestCode.HOMEWORK_AJAXHOMEWORKLIST;
		httpUtils.post(url, params, new MyResponseHandler(url, params,
				httpUtils, handler, new JsonCallBack() {
					@Override
					public void onCallBack(String json) {
						// TODO Auto-generated method stub
						try {
							String code = GsonUtils.getCode(json);
							if (code.equals(HttpResultStatus.SUCCESS)) {
								String data = GsonUtils.getData(json);
								HomeWrokArray homeWrokData = GsonUtils
										.getSingleBean(data,
												HomeWrokArray.class);
								message.what = HandlerCode.HANDLER_SUCCESS;
								message.obj = homeWrokData;
								handler.sendMessage(message);
							}else if(code.equals("999")&&activity!=null){
								activity.startActivity(new Intent(activity,Login.class));
							} else {
								handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
							}

						} catch (Exception e) {
							handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
							e.printStackTrace();
						}
					}
				}));

	}

	/**
	 * 发布家庭作业
	 */
	@Override
	public void getSumbitHomeWrok(final Activity activity,AsyncHttpClient httpUtils,
			final Handler handler, String content, String tips, String gradeId,
			String courseId,String courseName, String classId) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("content", content);
		params.put("tips", tips);
		params.put("eduGrade", gradeId);
		params.put("course", courseId);
		params.put("courseName", courseName);
		params.put("eduClasses", classId);
		params.put("sendMessage", "false");
		params.put("sendPush", "true");
		String url = UserUtil.getRequestUrl() + RequestCode.SUMBIT_HOMEWORK;
		httpUtils.post(url, params, new MyResponseHandler(url, params,
				httpUtils, handler, new JsonCallBack() {
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
							} else {
								handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
							}
						} catch (Exception e) {
							handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
							e.printStackTrace();
						}
					}
				}));

	}

	/**
	 * 获取老师所交科目
	 */
	@Override
	public void getSumbitHomeWrokCourse(final Activity activity,AsyncHttpClient httpUtils,
			final Handler handler) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		String url = UserUtil.getRequestUrl() + RequestCode.HOMEWORK_COURSENAME;
		httpUtils.post(url, params, new MyResponseHandler(url, params,
				httpUtils, handler, new JsonCallBack() {
					@Override
					public void onCallBack(String json) {
						// TODO Auto-generated method stub
						try {
							String code = GsonUtils.getCode(json);
							if (code.equals(HttpResultStatus.SUCCESS)) {
								HomeWrokCourseData data = GsonUtils.getSingleBean(json,HomeWrokCourseData.class);
								message.what = HandlerCode.GET_HOMEWROK_CLASS_COURSE_SUCCESS;
								message.obj = data;
								handler.sendMessage(message);
							}else if(code.equals("999")&&activity!=null){
								activity.startActivity(new Intent(activity,Login.class));
							}

						} catch (Exception e) {
							handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
							e.printStackTrace();
						}
					}
				}));

	}

	/**
	 * 家庭作业详情接口
	 */
	@Override
	public void getSumbitHomeWrokDetail(final Activity activity,AsyncHttpClient httpUtils,
			final Handler handler, String HomeWrokId) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("Direct", HomeWrokId);
		String url = UserUtil.getRequestUrl() + RequestCode.HOMEWORK_DETAIL;
		httpUtils.post(url, params, new MyResponseHandler(url, params,
				httpUtils, handler, new JsonCallBack() {
					@Override
					public void onCallBack(String json) {
						try {
							String code = GsonUtils.getCode(json);
							if (code.equals(HttpResultStatus.SUCCESS)) {
								String data = GsonUtils.getData(json);
								HomeWorkDetailProperty Detaildata = GsonUtils
										.getSingleBean(data,
												HomeWorkDetailProperty.class);
								message.what = HandlerCode.HANDLER_SUCCESS;
								message.obj = Detaildata;
								handler.sendMessage(message);
							}else if(code.equals("999")&&activity!=null){
								activity.startActivity(new Intent(activity,Login.class));
							} else {
								handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
							}
						} catch (Exception e) {
							handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
							e.printStackTrace();
						}
					}
				}));
	}

	/**
	 * 删除家庭作业列表数据
	 */
	@Override
	public void delHomeWrokList(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,String homeWorkId) {
		RequestParams params = new RequestParams();
		params.put("content", "App");
		params.put("tips", homeWorkId);
		String url = UserUtil.getRequestUrl()+RequestCode.DEL_HOMEWORK;
		httpUtils.post(url, params, new MyResponseHandler(url, params,
				httpUtils, handler, new JsonCallBack() {
					@Override
					public void onCallBack(String json) {
						try {
							String code = GsonUtils.getCode(json);
							if (code.equals(HttpResultStatus.SUCCESS)) {
								handler.sendEmptyMessage(HandlerCode.DEL_SUCCESS);
							}else if(code.equals("999")&&activity!=null){
								activity.startActivity(new Intent(activity,Login.class));
							} else {
								handler.sendEmptyMessage(HandlerCode.DEL_FAIL);
							}
						} catch (Exception e) {
							handler.sendEmptyMessage(HandlerCode.DEL_FAIL);
							e.printStackTrace();
						}
					}
				}));
		
	}

}

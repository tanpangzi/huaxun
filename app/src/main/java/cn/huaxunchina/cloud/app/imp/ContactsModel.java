package cn.huaxunchina.cloud.app.imp;

import android.os.Handler;
import android.os.Message;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.model.ContactsData;
import cn.huaxunchina.cloud.app.model.SMSHistory;
import cn.huaxunchina.cloud.app.model.SMSHistoryData;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class ContactsModel implements ContactsResponse {

	@Override
	public void getContactsList(String direct, String classId,AsyncHttpClient httpUtils, final Handler handler) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("direct", direct);
		params.put("classId", classId);
		String url = UserUtil.getRequestUrl() + RequestCode.CONTACTS;
		System.out.print("url--"+url+"direct="+direct+"&classId="+classId);
		httpUtils.post(url, params, new MyResponseHandler(url, params,
				httpUtils, handler, new JsonCallBack() {
					@Override
					public void onCallBack(String json) {
						try {
							String code = GsonUtils.getCode(json);
							if (code.equals(HttpResultStatus.SUCCESS)) {
								// 成功
								ContactsData data = GsonUtils.getSingleBean(json, ContactsData.class);
								message.obj = data;
								message.what = HandlerCode.HANDLER_SUCCESS;
								handler.sendMessage(message);
							} else {
								message.what = HandlerCode.HANDLER_ERROR;
								handler.sendMessage(message);
							}
						} catch (Exception e) {
							message.what = HandlerCode.HANDLER_ERROR;
							handler.sendMessage(message);
							e.printStackTrace();
						}
					}
				}));

	}

	@Override
	public void sendSms(String data, AsyncHttpClient httpUtils,
			final Handler handler) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("data", data);
		String url = UserUtil.getRequestUrl() + RequestCode.SENDSMS;
		httpUtils.post(url, params, new MyResponseHandler(url, params,
				httpUtils, handler, new JsonCallBack() {
					@Override
					public void onCallBack(String json) {
						try {
							String code = GsonUtils.getCode(json);
							if (code.equals(HttpResultStatus.SUCCESS)) {
								message.what = HandlerCode.HANDLER_SUCCESS;
								handler.sendMessage(message);
							} else {
								message.what = HandlerCode.HANDLER_ERROR;
								handler.sendMessage(message);
							}
						} catch (Exception e) {
							e.printStackTrace();
							message.what = HandlerCode.HANDLER_ERROR;
							handler.sendMessage(message);
						}
					}
				}));
	}

	@Override
	public void getHosMessageList(String sta, String limit,AsyncHttpClient httpUtils, final Handler handler) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("start", sta);
		params.put("limit", limit);
		String url = UserUtil.getRequestUrl() + RequestCode.HOSSMS;
		httpUtils.post(url, params, new MyResponseHandler(url, params,httpUtils, handler, new JsonCallBack() {
					@Override
					public void onCallBack(String json) {
						try {
							String code = GsonUtils.getCode(json);
							if (code.equals(HttpResultStatus.SUCCESS)) {
								String data = GsonUtils.getData(json);
								SMSHistoryData SMSHistoryListData = GsonUtils.getSingleBean(data,SMSHistoryData.class);
								message.what = HandlerCode.HANDLER_SUCCESS;
								message.obj = SMSHistoryListData;
								handler.sendMessage(message);
							} else {
								message.what = HandlerCode.HANDLER_ERROR;
								handler.sendMessage(message);
							}
						} catch (Exception e) {
							e.printStackTrace();
							message.what = HandlerCode.HANDLER_ERROR;
							handler.sendMessage(message);
						}
					}
				}));
	}

	@Override
	public void getHosMessageDetail(String data,String sendSerial,AsyncHttpClient httpUtils, final Handler handler) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("sendSerial", sendSerial);
		String url = UserUtil.getRequestUrl() + RequestCode.HOSSMSDETAIL;
		httpUtils.post(url, params, new MyResponseHandler(url, params,httpUtils, handler, new JsonCallBack() {
					@Override
					public void onCallBack(String json) {
						try {
							String code = GsonUtils.getCode(json);
							if (code.equals(HttpResultStatus.SUCCESS)) {
								String data = GsonUtils.getData(json);
								SMSHistory SMSHistoryData = GsonUtils.getSingleBean(data, SMSHistory.class);
								message.what = HandlerCode.HANDLER_SUCCESS;
								message.obj = SMSHistoryData;
								handler.sendMessage(message);
							} else {
								message.what = HandlerCode.HANDLER_ERROR;
								handler.sendMessage(message);
							}
						} catch (Exception e) {
							e.printStackTrace();
							message.what = HandlerCode.HANDLER_ERROR;
							handler.sendMessage(message);
						}
					}
				}));
		
	}

}

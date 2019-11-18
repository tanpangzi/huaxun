package cn.huaxunchina.cloud.app.imp.read;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.imp.MyResponseHandler;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadData;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadDataArray;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;

public class TodayReadImpl implements TodayReadInterface {

	/**
	 * 每日一读列表数据获取
	 * @param userinfo 当前登录人id
	 * @param start  查询开始记录数
	 * @param limit 每次查询多少条
	 */
	@Override
	public void getDailyReadList(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,
			String start, String limit) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("start", start);
		params.put("limit", limit);
		params.put("city", "App");
		String url = UserUtil.getRequestUrl()+RequestCode.READING_AJAXREADLIST;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			// TODO Auto-generated method stub
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String toadydata = GsonUtils.getData(json);
					TodayReadDataArray todayReadData = GsonUtils.getSingleBean(toadydata,TodayReadDataArray.class);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = todayReadData;
					handler.handleMessage(message);
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
	 * 获取每日一读收藏夹列表数据
	 */

	@Override
	public void getToadySaveList(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,
			String start, String limit) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("start", start);
		params.put("limit", limit);
		params.put("city", "App");
		String url = UserUtil.getRequestUrl()+RequestCode.READING_AJAXADDFAV;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			// TODO Auto-generated method stub
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String toadydata = GsonUtils.getData(json);
					TodayReadDataArray todayReadData = GsonUtils.getSingleBean(toadydata,TodayReadDataArray.class);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = todayReadData;
					handler.handleMessage(message);
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
	 * 获取每日一读详情
	 */

	@Override
	public void getToadyDetail(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,
			String readId) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("readId", readId);
		String url = UserUtil.getRequestUrl()+RequestCode.READING_AJAXREADDETAIL;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				// TODO Auto-generated method stub
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {
						String data = GsonUtils.getData(json);
						TodayReadData todaydetail = GsonUtils.getSingleBean(data,TodayReadData.class);
						message.what = HandlerCode.HANDLER_SUCCESS;
						message.obj = todaydetail;
						handler.handleMessage(message);
					}else if(code.equals("999")&&activity!=null){
						activity.startActivity(new Intent(activity,Login.class));
					} else {
						message.what = HandlerCode.HANDLER_ERROR;
						handler.handleMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
					message.what = HandlerCode.HANDLER_ERROR;
					handler.handleMessage(message);
				}
			}}));

	}

	/**
	 * 收藏每日一读文章
	 */

	@Override
	public void collectTodayRead(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,
			int readId) {
		RequestParams params = new RequestParams();
		params.put("readId", String.valueOf(readId));
		String url = UserUtil.getRequestUrl()+RequestCode.COLLECT_TODAY_READ;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				// TODO Auto-generated method stub
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {
						handler.sendEmptyMessage(HandlerCode.COLLECT_SUCCESS);
					}else if(code.equals("999")&&activity!=null){
						activity.startActivity(new Intent(activity,Login.class));
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
					e.printStackTrace();
				}
			}}));

	}

	/**
	 * 取消收藏
	 */

	@Override
	public void delCollectTodayRead(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,
			int readId) {
		// final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("readId", String.valueOf(readId));
		String url = UserUtil.getRequestUrl()+RequestCode.DEL_TODAY_READ;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				// TODO Auto-generated method stub
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {
						handler.sendEmptyMessage(HandlerCode.DEL_COLLECT_SUCCESS);
					}else if(code.equals("999")&&activity!=null){
						activity.startActivity(new Intent(activity,Login.class));
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(HandlerCode.HANDLER_ERROR);
					e.printStackTrace();
				}
			}}));

	}

}

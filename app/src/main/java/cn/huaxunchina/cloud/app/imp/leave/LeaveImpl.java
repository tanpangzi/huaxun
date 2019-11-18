package cn.huaxunchina.cloud.app.imp.leave;



import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.imp.MyResponseHandler;
import cn.huaxunchina.cloud.app.model.leave.LeaveArray;
import cn.huaxunchina.cloud.app.model.leave.LeaveProperty;
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
 * 请假管理列表接口类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-15 下午4:23:41
 */
public class LeaveImpl implements LeaveInterface {

	// 请假管理列表
	@Override
	public void getLeaveList(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,
			String studentId, String teacherId, String roleFlag, String sta,
			String limit) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("start", sta);
		params.put("limit", limit);
		if (roleFlag.equals(String.valueOf(RolesCode.PARENTS))) {
			params.put("studentId", studentId);
		} else {
			params.put("teacherId", teacherId);
		}
		String url = UserUtil.getRequestUrl()+RequestCode.ASKLEAVE_AJAXASKLEAVELIST;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String data = GsonUtils.getData(json);
					LeaveArray leaveListData = GsonUtils.getSingleBean(data, LeaveArray.class);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = leaveListData;
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

	// 提交请假申请
	@Override
	public void SumbitLeave(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,String leaveObject, String leaveType, String leaveContent,String staDate, String endDate, String studentId, String hour) {
		RequestParams params = new RequestParams();
		params.put("leaveObject", leaveObject);
		params.put("leaveType", leaveType);
		params.put("studentId", studentId);
		params.put("beginTime", staDate);
		params.put("endTime", endDate);
		params.put("hours", hour);
		params.put("leaveContent", leaveContent);
		String url = UserUtil.getRequestUrl()+RequestCode.AJAXADDASKLEAVE;
		System.out.println("url:"+url);
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			try {
				String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					handler.sendEmptyMessage(HandlerCode.HANDLER_SUCCESS);
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
	 * 提交请假审核状态
	 */
	@Override
	public void getCheckState(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,
			String askLeaveId, String sendStatus) {
		RequestParams params = new RequestParams();
		params.put("askLeaveId", askLeaveId);
		params.put("approveStatus", sendStatus);
		String url = UserUtil.getRequestUrl()+RequestCode.ASKLEAVE_AJAXCHECKASKLEAVE;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				// TODO Auto-generated method stub
				try {
					String code = GsonUtils.getCode(json);
					if (code.equals(HttpResultStatus.SUCCESS)) {
						handler.sendEmptyMessage(HandlerCode.HANDLER_SUCCESS);
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

	@Override
	public void getgetLeaveInfo(final Activity activity,AsyncHttpClient httpUtils, final Handler handler,String id) {
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("leaveId", id);
		String url = UserUtil.getRequestUrl()+RequestCode.LEAVEBYID;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
			try {
				   String code = GsonUtils.getCode(json);
				if (code.equals(HttpResultStatus.SUCCESS)) {
					String data = GsonUtils.getData(json);
					LeaveProperty info = GsonUtils.getSingleBean(data, LeaveProperty.class);
					message.what = HandlerCode.HANDLER_SUCCESS;
					message.obj = info;
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

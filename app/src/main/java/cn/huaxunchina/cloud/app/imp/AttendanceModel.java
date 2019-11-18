package cn.huaxunchina.cloud.app.imp;

import android.os.Handler;
import android.os.Message;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.model.AttendanceInfoData;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
/**
 * 考勤接口
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月16日 下午5:15:04 
 *
 */
public class AttendanceModel implements AttendanceResponse {

	
	@Override
	public void getAttInfoList(String studentId, String classId,String signDate, AsyncHttpClient httpUtils, final Handler handler) {
		// TODO Auto-generated method stub
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("studentId", studentId);
		params.put("classId", classId);
		params.put("signDate", signDate);
		String url = UserUtil.getRequestUrl()+RequestCode.ATTENDANCE_AJAXGETATTENDANCE;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			try {
			    AttendanceInfoData data = GsonUtils.getSingleBean(json, AttendanceInfoData.class);
			    message.obj=data;
			    message.what=HandlerCode.HANDLER_SUCCESS;
			    handler.sendMessage(message);	 
			} catch (Exception e) 
			{
			  e.printStackTrace();
			  message.what=HandlerCode.HANDLER_ERROR;
			  handler.sendMessage(message);	
			}
		}}));

	}

}

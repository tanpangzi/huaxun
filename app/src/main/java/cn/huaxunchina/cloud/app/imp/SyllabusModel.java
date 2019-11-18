package cn.huaxunchina.cloud.app.imp;



import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import android.os.Handler;
import android.os.Message;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.model.SyllabusData1;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;


public class SyllabusModel implements SyllabusResponse {
	
     

	@Override
	public void syllabusList(String classId, String teacherId,AsyncHttpClient httpUtils, final Handler handler) {
		// TODO Auto-generated method stub
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		if(classId!=null){
		params.put("classId", classId);}
		if(teacherId!=null){
		params.put("teacherId", teacherId);	}
		System.out.println("查课表:==========classId["+classId+"]teacherId["+teacherId+"]");
		String url = UserUtil.getRequestUrl()+RequestCode.DOAPPSELECT;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				try {
					System.out.println(json);
					SyllabusData1 syllabusData = GsonUtils.getSingleBean(json, SyllabusData1.class);
					 message.what=HandlerCode.HANDLER_SUCCESS;
					 message.obj=syllabusData;
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

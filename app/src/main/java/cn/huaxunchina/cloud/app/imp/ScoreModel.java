package cn.huaxunchina.cloud.app.imp;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import android.os.Handler;
import android.os.Message;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.RequestCode;
import cn.huaxunchina.cloud.app.model.FiveScoredsDatas;
import cn.huaxunchina.cloud.app.model.ScoreData;
import cn.huaxunchina.cloud.app.model.StudentScoreData;
import cn.huaxunchina.cloud.app.network.HttpResultStatus;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.UserUtil;

/**
 * TODO(描述) 成绩管理接口
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月15日 下午4:26:40 
 *
 */
public class ScoreModel implements ScoreResponse {
	


	@Override
	public void scoreListData(String classId,String start,String limit,AsyncHttpClient httpUtils, final Handler handler) {
		// TODO Auto-generated method stub
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("classId", classId);	
		params.put("start", start);	
		params.put("limit", limit);	
		String url = UserUtil.getRequestUrl()+RequestCode.LISTEXAMINATION;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
		@Override
		public void onCallBack(String json) {
			try {
			    String code = GsonUtils.getCode(json);
			    if(code.equals(HttpResultStatus.SUCCESS)){
			     //数据的保存==============
			    	String data = GsonUtils.getData(json);
			    	ScoreData scoredata = GsonUtils.getSingleBean(data, ScoreData.class);
			    	message.obj=scoredata;
			    	message.what=HandlerCode.HANDLER_SUCCESS;
			    	handler.sendMessage(message);
			    }else{
			    	message.what=HandlerCode.HANDLER_FAIL;
			    	handler.sendMessage(message);
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message.what=HandlerCode.HANDLER_ERROR;
		    	handler.sendMessage(message);
			}
		}}));
		 
		
	}

	@Override
	public void scoreDetail(String examId, String studentId, String teacherId,AsyncHttpClient httpUtils, final Handler handler) {
		// TODO Auto-generated method stub
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("studentId", studentId);	
		params.put("teacherId", teacherId);	
		params.put("examId", examId);
		String url = UserUtil.getRequestUrl()+RequestCode.COURSE_SCOREDETAIL;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				try {
					System.out.println("成绩json========>:"+json);
				    String code = GsonUtils.getCode(json);
				    if(code.equals(HttpResultStatus.SUCCESS)){
				     //数据的保存==============
				    	message.what=HandlerCode.HANDLER_SUCCESS;
				    	StudentScoreData data = GsonUtils.getSingleBean(json, StudentScoreData.class);
				    	message.obj=data;
				    	handler.sendMessage(message);
				    }else{
				    	message.what=HandlerCode.HANDLER_ERROR;
						handler.sendMessage(message);
				    }
				} catch (Exception e) {
					e.printStackTrace();
					message.what=HandlerCode.HANDLER_ERROR;
					handler.sendMessage(message);
				}
			}}));
		
			
	}

	
	@Override
	public void fiveScores(String studentId, String examId,AsyncHttpClient httpUtils, final Handler handler) {
		// TODO Auto-generated method stub
		final Message message = handler.obtainMessage();
		RequestParams params = new RequestParams();
		params.put("studentId", studentId);	
		params.put("examId", examId);
		String url = UserUtil.getRequestUrl()+RequestCode.FIVE_SCORES;
		httpUtils.post(url, params, new MyResponseHandler(url,params,httpUtils,handler,new JsonCallBack(){
			@Override
			public void onCallBack(String json) {
				// TODO Auto-generated method stub
				try {
					String code = GsonUtils.getCode(json);
					if(code.equals(HttpResultStatus.SUCCESS)){
						FiveScoredsDatas data = GsonUtils.getSingleBean(json, FiveScoredsDatas.class);
						message.obj=data;
						message.what=HandlerCode.HANDLER_SUCCESS;
						handler.sendMessage(message);
					 }else{
						message.what=HandlerCode.HANDLER_ERROR;
						handler.sendMessage(message); 
					 }
					} catch (Exception e) {
					e.printStackTrace();
					message.what=HandlerCode.HANDLER_ERROR;
					handler.sendMessage(message);	
					}
			}}));
	}

}

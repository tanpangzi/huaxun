package cn.huaxunchina.cloud.app.imp;

import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;

/**
 * 联系人接口 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月20日 下午2:53:01 
 *
 */
public interface ContactsResponse {

	/**
	  * 获取联系人接口
	  * @param direct
	  * @param classId
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void getContactsList(String direct, String classId, AsyncHttpClient httpUtils, Handler handler);
	/**
	 * TODO(描述) 
	  * @param data
	  * @param httpUtils
	  * @param handler
	 */
	public abstract void sendSms(String data, AsyncHttpClient httpUtils, Handler handler);
	
	
	/**
	 * 历史短信列表接口
	 * @param sta
	 * @param limit
	 * @param httpUtils
	 * @param handler
	 */
	public abstract void getHosMessageList(String sta, String limit, AsyncHttpClient httpUtils, Handler handler);
	
	/**
	 * 历史短信详情
	 * @param sta
	 * @param limit
	 * @param httpUtils
	 * @param handler
	 */
	public abstract void getHosMessageDetail(String data, String sendSerial, AsyncHttpClient httpUtils, Handler handler);
}

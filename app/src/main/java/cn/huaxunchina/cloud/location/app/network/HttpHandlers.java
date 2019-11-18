package cn.huaxunchina.cloud.location.app.network;

import org.apache.http.entity.StringEntity;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.loopj.android.http.AsyncHttpClient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.common.Constant.Url;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.location.app.model.post.ClientAppId;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;

public class HttpHandlers {
	private AsyncHttpClient httpUtils;
	private final int HTTPHANDLERDATA = 400;
	private HttpHandlerCallBacks httpHandlerCallBack;

	public HttpHandlers( AsyncHttpClient httpUtils,Object object, HttpHandlerCallBacks httpHandlerCallBack) {
		this.httpUtils = httpUtils;
		this.httpHandlerCallBack = httpHandlerCallBack;
		post(object);
	}

 
	
	 

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HTTPHANDLERDATA:
				String json = (String) msg.obj;
				httpHandlerCallBack.onCallBack(json);
				break;
			case HandlerCode.HANDLER_ERROR:
				//Toast.makeText(ApplicationController.getContext(), "服务端错误", 0).show();
				httpHandlerCallBack.onErro();
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				httpHandlerCallBack.onErro();
				break;
			}
		}

	};

	public  Handler getHander(){
		return handler;
	}
	public void post(Object object) {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		StringEntity entity = GsonUtils.toJson(object);
		httpUtils.post(ApplicationController.getContext(), Url.MSG_SENDER, entity,Constant.CONTENTTYPE, new ResponseJsonHandler(httpUtils,handler, new JsonCallBack() {
							@Override
							public void onCallBack(String json) {
								System.out.println(json);
								Message msg = handler.obtainMessage();
								msg.what = HTTPHANDLERDATA;
								msg.obj = json;
								handler.handleMessage(msg);
							}
						}));
	}

	public interface HttpHandlerCallBacks {
		public void onCallBack(String json);
		public void onErro();
	}
}

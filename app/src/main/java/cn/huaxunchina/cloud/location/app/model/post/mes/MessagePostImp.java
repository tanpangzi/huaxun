package cn.huaxunchina.cloud.location.app.model.post.mes;

import org.apache.http.HttpEntity;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.imp.JsonCallBack;
import cn.huaxunchina.cloud.app.tools.GsonUtils;
import cn.huaxunchina.cloud.location.app.model.post.FencesettingModel;
import cn.huaxunchina.cloud.location.app.model.post.GDecode;
import cn.huaxunchina.cloud.location.app.network.ResponseJsonHandler;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

/**
 * 消息列表接口实现类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-9 下午2:05:50
 */
public class MessagePostImp {

//	private static void gDecode(AsyncHttpClient httpUtils) {
//		GDecode gDecode = new GDecode();
//		gDecode.setId("1");
//		gDecode.setPsw("0000");
//		gDecode.setLat(99.21);
//		gDecode.setLng(95.01);
//		// "{'c':'gDecode','id':'1','psw': '0000','lat':99.21,'lng': 95.01}";
//		HttpEntity entity = GsonUtils.toJson(gDecode);
//
//		httpUtils.post(ApplicationController.getContext(), url, entity,
//				Constant.CONTENTTYPE, new ResponseJsonHandler(httpUtils,
//						handler, new JsonCallBack() {
//							@Override
//							public void onCallBack(String json) {
//								// TODO Auto-generated method stub
//								System.out.println(json);
//							}
//						}));
//	}


}

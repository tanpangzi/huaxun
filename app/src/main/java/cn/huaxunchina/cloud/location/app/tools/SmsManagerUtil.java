package cn.huaxunchina.cloud.location.app.tools;

import java.util.List;

import android.telephony.SmsManager;


public class SmsManagerUtil {
	
	public static void sentSms(String phoneNumber,String content){
		//直接调用短信接口发短信
		SmsManager smsManager = SmsManager.getDefault();
		List<String> divideContents = smsManager.divideMessage(content);  
		for (String text : divideContents) {  
			smsManager.sendTextMessage(phoneNumber, null, text, null, null);  
		}
	}

}

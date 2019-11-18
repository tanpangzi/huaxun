package cn.huaxunchina.cloud.location.app.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {
	
	
	public static boolean isMobile(String str){
		Pattern p = null,p2 = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");//校验手机号
		m = p.matcher(str);
		b = m.matches();
		if(!b){
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
		m = p2.matcher(str);
		b = m.matches();
		}
		return b;
	}
	
 

}

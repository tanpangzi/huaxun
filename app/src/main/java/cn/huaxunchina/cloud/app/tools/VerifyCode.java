package cn.huaxunchina.cloud.app.tools;

import java.text.DecimalFormat;
/**
 *验证码工具类 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月14日 上午11:54:47 
 *
 */
public class VerifyCode {

	public static final int MAX_SEND_COUNT=3;
	
	
	public static String getVerifyCode(){
		double random=Math.random()*1000000l;
		DecimalFormat  decimalFormat=new DecimalFormat ("000000");
		return String.valueOf(decimalFormat.format(random));
	}
	
}

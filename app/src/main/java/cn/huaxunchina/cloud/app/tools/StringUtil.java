package cn.huaxunchina.cloud.app.tools;

public class StringUtil {
	
	/**
	 * TODO(描述) 
	  * @param str 字符截取
	  * @return
	 */
	public static String substring(String str,int sub_length){
		if(str==null){
			return"";	
		}
		int length = str.length();
		if(length<sub_length){
			return str;
		}
		return str.substring(0, 4)+"..";
	}

}

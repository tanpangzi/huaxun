package cn.huaxunchina.cloud.location.app.tools;

public class StringUtil {

	public static String replaceNull(String text) {
		if ("".equals(text) || null == text || "null".equals(text)) {
			return "暂无显示地址";
		} else {
			return text;
		}
	}
}

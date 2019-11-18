package cn.huaxunchina.cloud.app.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

	/**
	 * 判断是否匹配字符
	 * 
	 * @return
	 */
	private final static String regxpForHtml = "<([^>]*)>"; //

	// 过滤所有以<开头以>结尾的标签

	// private final static String regEx_html = "<[^>]+>";

	public static Boolean Partter(String result, String reg) {

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(result);
		if (matcher.matches()) {
			return true;
		}
		return false;

	}

	/**
	 * 判定输入汉字
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测String是否全是中文
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}

	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}

	public static String stripHtml(String content) {
		// <p>段落替换为换行
		content = content.replaceAll("<p.*?>", "rn");
		// <br><br/>替换为换行
		content = content.replaceAll("<brs*/?>", "rn");
		// 去掉其它的<>之间的东西
		content = content.replaceAll("<.*?>", "");
		// 　//还原HTML 　
		// //content=HTMLDecoder.decode(content);
		return content;
	}

	/*
	 * 基本功能：过滤所有以"<"开头以">"结尾的标签 <p>
	 * 
	 * @param str
	 * 
	 * @return String
	 */
	public static String filterHtml(String str) {
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
		// Pattern p_html = Pattern.compile(regEx_html,
		// Pattern.CASE_INSENSITIVE);
		// Matcher m_html = p_html.matcher(str);
		// return m_html.replaceAll("");
	}

	public static String findSrc(String str) {
		String[] text_str = str.split(" ");
		String src = null;
		for (int i = 0; i < text_str.length; i++) {
			if (text_str[i].contains("src")) {
				src = text_str[i];
			}
		}
		return src;

	}

	/**
	 * // 清除掉所有特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String filterStr(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？&nbsp]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

}

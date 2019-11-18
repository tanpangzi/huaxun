package cn.huaxunchina.cloud.app.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 数据存取
 * 
 * @author zxf
 * 
 */
public class PreferencesHelper {

	SharedPreferences sp;
	SharedPreferences.Editor editor;
	Context context;

	@SuppressLint("CommitPrefEdits")
	public PreferencesHelper(Context c, String name) {
		context = c;
		sp = context.getSharedPreferences(name, 0);
		editor = sp.edit();
	}

	public void setValue(String key, String value) {
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
		 
	}
	
	public void setInt(String key, int value) {
		editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
		 
	}
	
	public int getInt(String key) {
		int value = sp.getInt(key,0);
		return value;
	}

	public String getValue(String key) {
		String str = sp.getString(key, "");
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		return str;
	}

	public void clear() {
		editor.clear();
		editor.commit();
	}

	 

}

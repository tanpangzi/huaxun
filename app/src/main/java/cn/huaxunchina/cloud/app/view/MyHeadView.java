package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.tools.HtmlUtil;
import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyHeadView extends LinearLayout {
	private TextView comment;
	private TextView detail_title;
	private TextView detail_time;
	private WebView detail_web;

	public MyHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.includ_interaction_detail, this);
		comment = (TextView)findViewById(R.id.comment);
		detail_title = (TextView)findViewById(R.id.home_school_detail_title);
		detail_time = (TextView)findViewById(R.id.home_school_detal_time);
		detail_web = (WebView)findViewById(R.id.detail_webview);
	}

	/**
	 * 设置模块标题
	 * 
	 * @param text
	 */
	public void setHeadText(String titleText,String timeText,String commentText) {
		comment.setText(commentText);
		detail_title.setText(titleText);
		detail_time.setText(timeText);
		
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	public void setWebView(String content){
		detail_web.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		detail_web.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
		detail_web.addJavascriptInterface(this, "javatojs");
		detail_web.setHorizontalScrollBarEnabled(false);//水平不显示
		detail_web.setVerticalScrollBarEnabled(false); //垂直不显示
		// 在内容显示内部显示
		WebSettings webSetting = detail_web.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setNeedInitialFocus(false);
		webSetting.setSupportZoom(true);
		detail_web.setBackgroundColor(Color.TRANSPARENT);// 设置其背景为透明
		webSetting.setCacheMode(WebSettings.LOAD_DEFAULT|WebSettings.LOAD_CACHE_ELSE_NETWORK);
		detail_web.loadDataWithBaseURL(null, HtmlUtil.getHtml(content), "text/html", "utf-8", null); 
	}
	
}

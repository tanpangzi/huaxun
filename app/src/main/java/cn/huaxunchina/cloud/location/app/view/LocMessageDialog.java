package cn.huaxunchina.cloud.location.app.view;

import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;

/**
 * 消息列表日期查询列表数据对话框
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-29 上午11:25:34
 */
public class LocMessageDialog extends Dialog {
	private static int theme = R.style.dialog;// 主题
	private Context mcontext;
	private int width, height;// 对话框宽高

	public LocMessageDialog(Context context, int theme) {
		super(context, theme);
	}

	public LocMessageDialog(Context context, int width, int height) {
		super(context, theme);
		this.mcontext = context;
		this.width = width;
		this.height = height;

	}

}

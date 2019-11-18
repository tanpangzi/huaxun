package cn.huaxunchina.cloud.location.app.view;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.adapter.AttPatriarchAdapter;
import cn.huaxunchina.cloud.app.getui.GetuiUtil;
import cn.huaxunchina.cloud.app.model.AttendanceInfo;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
/**
 * 
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月12日 下午12:07:08 
 *
 */
public class LocusAboutDialog extends Dialog {
	
	private Context context;
	private static int theme = R.style.dialog;// 主题
	
	public LocusAboutDialog(Context context) {
		super(context,theme);
		this.context=context;
	}

	public LocusAboutDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_locus_about_dialog);
	}
	
	 

}

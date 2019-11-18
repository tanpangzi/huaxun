package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.adapter.AttPatriarchAdapter;
import cn.huaxunchina.cloud.app.model.AttendanceInfo;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * TODO(描述)
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月14日 下午12:18:20
 * 
 */
public class AttDetailDialog extends Dialog {

	private ListView attListview;
	private Context context;
	private static int theme = R.style.dialog;// 主题
	private AttendanceInfo info;
	private String date="";

	public AttDetailDialog(Context context, AttendanceInfo info, String date) {
		super(context, theme);
		this.context = context;
		this.date = date;
		this.info = info;
	}

	public AttDetailDialog(Context context, int theme) {
		super(context, theme);
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.att_detail_dialog);
		attListview = (ListView) findViewById(R.id.att_detail_listview);
		TextView name = (TextView) findViewById(R.id.att_students_name);
		if (date.equals("")||date==null) {
			name.setText(info.getName() + DateUtil.getCurrentMonth() + "考勤");
		} else {
			name.setText(info.getName() + date.substring(5, 7)+"月"+date.substring(8, 10)+"日"+"考勤");
		}
		attListview.setAdapter(new AttPatriarchAdapter(info));
	}

}

package cn.huaxunchina.cloud.location.app.view;

import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * TODO(描述)
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月12日 下午12:07:08
 * 
 */
public class LocationSmsDialog extends Dialog {

	private Context context;
	private static int theme = R.style.dialog;// 主题
	private SmsOnClickListener smsOnClickListener;

	public LocationSmsDialog(Context context,
			SmsOnClickListener smsOnClickListener) {
		super(context, theme);
		this.context = context;
		this.smsOnClickListener = smsOnClickListener;
	}

	public LocationSmsDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_location_sms_dialog);
		findViewById(R.id.loc_location_btn2).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dismiss();
					}
				});
		findViewById(R.id.loc_location_btn1).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dismiss();
						smsOnClickListener.onCallBack();
					}
				});
	}

	public interface SmsOnClickListener {

		public void onCallBack();
	}

}

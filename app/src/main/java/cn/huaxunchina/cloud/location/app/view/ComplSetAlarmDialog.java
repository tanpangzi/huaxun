package cn.huaxunchina.cloud.location.app.view;

import com.wheel.LocaWheelAdapter;
import com.wheel.WheelView;
import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 完成闹钟类型设置dialog TODO(描述)
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 下午2:40:04
 * 
 */
public class ComplSetAlarmDialog extends Dialog {
	private AlarmTypeCallBack callBack;
	@SuppressWarnings("rawtypes")
	private LocaWheelAdapter type_adapter = null;
	private static int theme = R.style.Theme_AppCompat_Dialog;// 主题
	private WheelView alarmTypeView = null;
	private int width, height;// 对话框宽高
	public ComplSetAlarmDialog(Context context, int width, int height,String [] typedata) {
		super(context, theme);
		this.width = width;
		this.height = height;
		type_adapter = new LocaWheelAdapter<String>(typedata, typedata.length);
	}

	public ComplSetAlarmDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setalarm_type_dialog);
		LinearLayout layout = (LinearLayout) findViewById(R.id.reported_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);
		alarmTypeView = (WheelView) findViewById(R.id.alarm_type_index);
		// 时间
		alarmTypeView.setCyclic(false);
		alarmTypeView.setVisibleItems(5);
		alarmTypeView.setAdapter(type_adapter);
		alarmTypeView.setCurrentItem(1);
		findViewById(R.id.reported_inquiry_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
							callBack.onSelectdata((type_adapter.getIndexs()));
						dismiss();
					}
				});
	}

	public void setAlarmTypeCallBack(AlarmTypeCallBack callBack) {
		this.callBack = callBack;
	}

	public interface AlarmTypeCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(int setAlarmTypeIndex);
	}

}

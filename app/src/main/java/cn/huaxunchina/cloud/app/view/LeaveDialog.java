package cn.huaxunchina.cloud.app.view;

import com.wheel.LocaWheelAdapter;
import com.wheel.WheelView;
import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class LeaveDialog extends Dialog {
	private LeaveCallBack callBack;
	@SuppressWarnings("rawtypes")
	private LocaWheelAdapter leave_adapter = null;
	private static int theme = R.style.dialog;// 主题
	private int width, height;// 对话框宽高
	private WheelView leave_type_view = null;

	public LeaveDialog(Context context, int theme) {
		super(context, theme);
	}

	public LeaveDialog(Context context, int width, int height,
			String[] leave_type) {
		super(context, theme);
		this.width = width;
		this.height = height;
		leave_adapter = new LocaWheelAdapter<String>(leave_type,
				leave_type.length);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave_dialog);
		LinearLayout layout = (LinearLayout) findViewById(R.id.leave_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours); 
		leave_type_view = (WheelView) findViewById(R.id.leave_type_index);
		leave_type_view.setCyclic(false);
		leave_type_view.setVisibleItems(5);
		leave_type_view.setAdapter(leave_adapter);
		leave_type_view.setCurrentItem(0);
		findViewById(R.id.leave_inquiry_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						callBack.onSelectdata(leave_adapter.getIndexs() + "");
						dismiss();
					}
				});
	}

	public void SetLeaveCallBack(LeaveCallBack callBack) {
		this.callBack = callBack;
	}

	public interface LeaveCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(String typeindex);
	}

}

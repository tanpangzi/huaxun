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

/**
 * 课程表查询Dialog TODO(描述)
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 下午2:40:04
 * 
 */
public class SumbitNoiceDialog extends Dialog {

	private SumbitNoiceCallBack callBack;
	private LocaWheelAdapter sumbit_Noice_adapter = null;
	private static int theme = R.style.dialog;// 主题
	private WheelView sumbit_notice_view = null;
	private int width, height;// 对话框宽高
	private String[] className;
	private int select_class;

	public SumbitNoiceDialog(Context context, int width, int height,
			String[] className,int select_class) {
		super(context, theme);
		this.width = width;
		this.height = height;
		this.className = className;
		this.select_class=select_class;
		sumbit_Noice_adapter = new LocaWheelAdapter<String>(className,className.length);
	}

	public SumbitNoiceDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sumbit_notice_dialog);
		LinearLayout layout = (LinearLayout) findViewById(R.id.sumbit_notice_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);
		sumbit_notice_view = (WheelView) findViewById(R.id.sumbit_notice_index);
		sumbit_notice_view.setCyclic(false);
		sumbit_notice_view.setVisibleItems(5);
		sumbit_notice_view.setAdapter(sumbit_Noice_adapter);
		sumbit_notice_view.setCurrentItem(select_class);
		findViewById(R.id.sumnoice_inquiry_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						callBack.onSelectdata(sumbit_Noice_adapter.getIndexs());
						dismiss();

					}
				});
	}

	public void setSumbitNoiceCallBack(SumbitNoiceCallBack callBack) {
		this.callBack = callBack;
	}

	public interface SumbitNoiceCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(int classindex);
	}

}

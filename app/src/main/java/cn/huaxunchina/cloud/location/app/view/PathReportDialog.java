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
 * 轨迹上报Dialog TODO(描述)
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 下午2:40:04
 * 
 */
public class PathReportDialog extends Dialog {

	private PathReportCallBack callBack;
	@SuppressWarnings("rawtypes")
	private LocaWheelAdapter time_adapter = null;
	private static int theme = R.style.dialog;// 主题
	private WheelView reportTimeView = null;
	private int width, height;// 对话框宽高
	public PathReportDialog(Context context, int width, int height,String [] timedata) {
		super(context, theme);
		this.width = width;
		this.height = height;
		time_adapter = new LocaWheelAdapter<String>(timedata, timedata.length);
	}

	public PathReportDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.path_reported_dialog);
		LinearLayout layout = (LinearLayout) findViewById(R.id.reported_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);
		reportTimeView = (WheelView) findViewById(R.id.report_time_index);
		// 时间
		reportTimeView.setCyclic(false);
		reportTimeView.setVisibleItems(3);
		reportTimeView.setAdapter(time_adapter);
		reportTimeView.setCurrentItem(0);
		findViewById(R.id.reported_inquiry_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
							callBack.onSelectdata((time_adapter.getIndexs()));
						dismiss();
					}
				});
	}

	public void setPathReportCallBack(PathReportCallBack callBack) {
		this.callBack = callBack;
	}

	public interface PathReportCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(int reportTimeindex);
	}

}

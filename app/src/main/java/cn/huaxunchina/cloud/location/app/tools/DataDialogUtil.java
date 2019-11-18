package cn.huaxunchina.cloud.location.app.tools;

import java.text.DecimalFormat;
import java.util.Calendar;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.view.DataDialog;

/**
 * 底部日期对话框的处理类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-5 下午5:25:35
 */
public class DataDialogUtil {
	private DataDialog dataDialog;
	private Window window;
	private Context mcontext;
	private DateCallBack dateCallBack;

	public DataDialogUtil(Context context) {
		this.mcontext = context;
	}

	public void setDateCallBack(DateCallBack dateCallBack) {
		this.dateCallBack = dateCallBack;
	}

	/**
	 * 查询当前时分列表数据
	 */
	public void getCurDateList(final TextView text) {
		dataDialog = new DataDialog.Builder(mcontext, false, true, false)
				.setPositiveButton("确认", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Calendar c = dataDialog.getSetCalendar();
						text.setText(getFormatDate(c));
						dataDialog.dismiss();
					}
				}).create();
		setDialogWindow();
		dataDialog.show();
	}

	/**
	 * 查询当前年月日列表数据
	 */
	public void getCurYearDateList(final TextView text) {
		dataDialog = new DataDialog.Builder(mcontext, false, false, true)
				.setPositiveButton("确认", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Calendar c = dataDialog.getSetCalendar();
						String date = getFormatYearMDayDate(c);
						if (text != null) {
							text.setText(date);
						}
						if (dateCallBack != null) {
							dateCallBack.onSelectdata(date);
						}
						dataDialog.dismiss();
					}
				}).create();
		setDialogWindow();
		dataDialog.show();
	}

	/**
	 * 获取时间段
	 * 
	 * @param c
	 * @return
	 */
	protected static String getFormatDate(Calendar c) {
		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		// 设置日期的显示
		Calendar calendar = c;
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		return decimal.format(hour) + ":" + decimal.format(min);
	}

	/**
	 * 获取时间段
	 * 
	 * @param c
	 * @return
	 */
	public String getFormatYearMDayDate(Calendar c) {
		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		// 设置日期的显示
		Calendar calendar = c;
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		return year + "-" + decimal.format(month + 1) + "-"
				+ decimal.format(day);
	}

	/**
	 * 获取时间段
	 * 
	 * @param c
	 * @return
	 */
	public String getFormatYearMothDate(Calendar c) {
		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		// 设置日期的显示
		Calendar calendar = c;
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		return year + decimal.format(month + 1);
	}

	/**
	 * 设置对话框窗口动画
	 */
	public void setDialogWindow() {
		window = dataDialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		dataDialog.setCancelable(true);
		dataDialog.setCanceledOnTouchOutside(true);
	}

	public interface DateCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(String date);
	}
}

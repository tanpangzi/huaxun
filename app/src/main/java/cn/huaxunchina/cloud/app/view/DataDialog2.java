package cn.huaxunchina.cloud.app.view;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import com.wheel.NumericWheelAdapter;
import com.wheel.OnWheelChangedListener;
import com.wheel.WheelView;
import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class DataDialog2 extends Dialog {
	private static int START_YEAR = 1990, END_YEAR = 2100;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	String[] months_little = { "4", "6", "9", "11" };
	final List<String> list_big = Arrays.asList(months_big);
	final List<String> list_little = Arrays.asList(months_little);
	private final Context mContext;
	private Button btn_sure;
	private CharSequence positiveText;
	private Calendar calendar;
	// private int width, height;// 对话框宽高
	private View.OnClickListener positiveClickListener;

	public DataDialog2(Context context) {
		super(context, R.style.Theme_AppCompat_Dialog);
		this.mContext = context;

	}

	public DataDialog2(Context context, boolean flag) {
		super(context, R.style.Theme_AppCompat_Dialog);
		this.mContext = context;
	}

	public DataDialog2(Context context, Calendar instance) {
		this(context);
		calendar = instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.leave_data_dialog);
		findView();
		adjustView();
		setListener();
		setDate(calendar);
	}

	@SuppressWarnings("static-access")
	private void adjustView() {
		// 根据屏幕密度来指定选择器字体的大小
		int textSize = 0;
		textSize = pixelsToDip(mContext.getResources(), 10);
		wv_day.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
	}

	public static int pixelsToDip(Resources res, int pixels) {
		final float scale = res.getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}

	private void setListener() {
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		if (positiveClickListener != null) {
			btn_sure.setOnClickListener(positiveClickListener);
		} else {
			btn_sure.setOnClickListener(dismissListener);
		}

	}

	private final View.OnClickListener dismissListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			dismiss();
		}
	};

	private void findView() {
		// 年
		wv_year = (WheelView) findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setLabel("年");// 添加文字

		// 月
		wv_month = (WheelView) findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setLabel("月");

		// 日
		wv_day = (WheelView) findViewById(R.id.day);
		// 判断大小月及是否闰年,用来确定"日"的数据
		wv_day.setLabel("日");

		// // 时
		wv_hours = (WheelView) findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setLabel("时");

		// // 分
		wv_mins = (WheelView) findViewById(R.id.min);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setLabel("分");
		if (Builder.flag) {
			wv_year.setVisibility(View.VISIBLE);
			wv_month.setVisibility(View.VISIBLE);
			wv_day.setVisibility(View.GONE);
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
		} else if (Builder.alarm) {
			wv_year.setVisibility(View.GONE);
			wv_month.setVisibility(View.GONE);
			wv_day.setVisibility(View.GONE);
			wv_hours.setVisibility(View.VISIBLE);
			wv_mins.setVisibility(View.VISIBLE);
		} else if (Builder.step) {
			wv_year.setVisibility(View.VISIBLE);
			wv_month.setVisibility(View.VISIBLE);
			wv_day.setVisibility(View.VISIBLE);
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
		}
		btn_sure = (Button) findViewById(R.id.leave_data_btn);
		if (positiveText != null) {
			btn_sure.setVisibility(View.VISIBLE);
			btn_sure.setText(positiveText);
		}
	}

	// 添加"年"监听
	private final OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			int year_num = newValue + START_YEAR;
			// 判断大小月及是否闰年,用来确定"日"的数据
			if (list_big
					.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 31));
			} else if (list_little.contains(String.valueOf(wv_month
					.getCurrentItem() + 1))) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 30));
			} else {
				if ((year_num % 4 == 0 && year_num % 100 != 0)
						|| year_num % 400 == 0)
					wv_day.setAdapter(new NumericWheelAdapter(1, 29));
				else
					wv_day.setAdapter(new NumericWheelAdapter(1, 28));
			}
		}
	};
	// 添加"月"监听
	private final OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			int month_num = newValue + 1;
			// 判断大小月及是否闰年,用来确定"日"的数据
			if (list_big.contains(String.valueOf(month_num))) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 31));
			} else if (list_little.contains(String.valueOf(month_num))) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 30));
			} else {
				if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
						.getCurrentItem() + START_YEAR) % 100 != 0)
						|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
					wv_day.setAdapter(new NumericWheelAdapter(1, 29));
				else
					wv_day.setAdapter(new NumericWheelAdapter(1, 28));
			}
		}
	};

	private void setPositiveButton(CharSequence mPositiveButtonText,
			View.OnClickListener onClickListener) {
		positiveText = mPositiveButtonText;
		positiveClickListener = onClickListener;// can't use btn_sure here
												// because it's on defined yet
	}

	private void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public DataDialog2 setDate(Calendar calendar) {
		if (calendar == null)
			return this;
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
		wv_month.setCurrentItem(month);
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setCurrentItem(day - 2);
		wv_hours.setCurrentItem(hour);
		wv_mins.setCurrentItem(minute);
		return this;
	}

	public Calendar getSetCalendar() {
		Calendar c = Calendar.getInstance();
		c.set(wv_year.getCurrentItem() + START_YEAR, wv_month.getCurrentItem(),
				wv_day.getCurrentItem() + 1, wv_hours.getCurrentItem(),
				wv_mins.getCurrentItem());

		return c;
	}

	public static class Builder {
		private final DatePickParams P;
		public static boolean flag;
		public static boolean alarm;
		public static boolean step;

		public Builder(Context context, boolean flag, boolean alarm,
				boolean step) {
			P = new DatePickParams(context);
			this.flag = flag;
			this.alarm = alarm;
			this.step = step;
		}

		public Builder setTitle(CharSequence title) {
			P.mTitle = title;
			return this;
		}

		public Builder setIcon(int iconId) {
			P.mIconId = iconId;
			return this;
		}

		public Builder setPositiveButton(CharSequence text,
				final View.OnClickListener listener) {
			P.mPositiveButtonText = text;
			P.mPositiveButtonListener = listener;
			return this;
		}

		public DataDialog2 create() {
			final DataDialog2 dialog = new DataDialog2(P.mContext);
			P.apply(dialog);
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			lp.dimAmount = 0.5f;
			dialog.getWindow().setAttributes(lp);
			dialog.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			return dialog;
		}
	}

	public static class DatePickParams {
		public int mIconId;
		public View.OnClickListener mPositiveButtonListener;
		public CharSequence mPositiveButtonText;
		public CharSequence mTitle;
		public final Context mContext;
		public Calendar calendar;

		public DatePickParams(Context context) {
			mContext = context;
			calendar = Calendar.getInstance();
		};

		public DatePickParams(Context context, Calendar calendar) {
			mContext = context;
			this.calendar = calendar;
		}

		public void apply(DataDialog2 dialog) {
			if (mTitle != null) {
				dialog.setTitle(mTitle);
			}

			if (mPositiveButtonText != null) {
				dialog.setPositiveButton(mPositiveButtonText,
						mPositiveButtonListener);
			}
			if (calendar != null)
				dialog.setCalendar(calendar);

		}
	}

}

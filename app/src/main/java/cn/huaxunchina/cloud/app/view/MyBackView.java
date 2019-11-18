package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;

;

public class MyBackView extends LinearLayout implements OnTouchListener {
	private ImageView backImage;
	private TextView mTitle;
	private Activity activity;
	private LinearLayout back;

	public MyBackView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.back_navigation, this);
		backImage = (ImageView) findViewById(R.id.back_image);
		back = (LinearLayout) findViewById(R.id.liner_back);
		mTitle = (TextView) findViewById(R.id.back_txt);
		back.setOnTouchListener(this);
	}

	/**
	 * 设置模块标题
	 * 
	 * @param text
	 */
	public void setBackText(CharSequence text) {
		mTitle.setText(text);
	}

	public void setAddActivty(Activity activity) {
		this.activity = activity;
	}

	// // 计算出该TextView中文字的长度(像素)
	// public float getTextViewLength(TextView textView, String text) {
	// TextPaint paint = textView.getPaint();
	// // 得到使用该paint写上text的时候,像素为多少
	// float textLength = paint.measureText(text);
	// return textLength;
	// }

	// /**
	// * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	// */
	// public int px2dip(Context context, float pxValue) {
	// final float scale = context.getResources().getDisplayMetrics().density;
	// return (int) (pxValue / scale + 0.5f);
	// }

	/**
	 * 设置按下状态
	 */
	public void setActionDown() {
		backImage.setBackgroundResource(R.drawable.back_arrow2);
	}

	/**
	 * 设置放开状态
	 */
	public void setActionUp() {
		backImage.setBackgroundResource(R.drawable.back_arrow1);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			setActionDown();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			setActionUp();
			activity.finish();
			activity.overridePendingTransition(R.anim.new_push_left_in,
					R.anim.new_push_left_out);// 从右向左推出动画效果
		}
		return true;
	}

}

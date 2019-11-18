package cn.huaxunchina.cloud.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
	private boolean mDisableSroll = false;

	public MyViewPager(Context context) {
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setDisableScroll(boolean bDisable) {
		mDisableSroll = bDisable;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mDisableSroll) {
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mDisableSroll) {
			return false;
		}
		return super.onTouchEvent(ev);
	}

}
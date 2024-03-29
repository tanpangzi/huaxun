package cn.huaxunchina.cloud.app.adapter;

import java.util.List;

import cn.huaxunchina.cloud.app.activity.profile.Login;
import cn.huaxunchina.cloud.app.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FristViewPagerAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;
	private Activity activity;
	private boolean flag;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";

	public FristViewPagerAdapter(List<View> views, Activity activity,
			boolean flag) {
		this.views = views;
		this.activity = activity;
		this.flag = flag;
	}

	// 销毁arg1位置的界面
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	// 获得当前界面数
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	// 初始化arg1位置的界面
	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1), 0);
		if (arg1 == views.size() - 1) {
			ImageView mStartWeiboImageButton = (ImageView) arg0
					.findViewById(R.id.iv_guide_start);
			mStartWeiboImageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 设置已经引导
					setGuided();
					goHome();

				}

			});
		}
		return views.get(arg1);
	}

	private void goHome() {
		if (flag) {
			activity.finish();
		} else {
			// 跳转
			Intent intent = new Intent(activity, Login.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}

	/**
	 * 
	 * method desc：设置已经引导过了，下次启动不用再次引导
	 */
	private void setGuided() {
		SharedPreferences preferences = activity.getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		// 存入数据
		editor.putBoolean("isFirstIn", false);
		// 提交修改
		editor.commit();
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}

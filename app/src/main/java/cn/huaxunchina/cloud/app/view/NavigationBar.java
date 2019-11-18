package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NavigationBar extends RelativeLayout {

	private TextView mLeftText;
	private TextView mRightText;
	private TextView mTitle;

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		/*inflater.inflate(R.layout.activity_navigation, this);
		mLeftText = (TextView) findViewById(R.id.navigation_left_text);
		mRightText = (TextView) findViewById(R.id.navigation_right_text);
		mTitle = (TextView) findViewById(R.id.navigation_title);*/
	}

	public void setLeftButtonText(CharSequence text) {
		mLeftText.setText(text);
	}

	public void setRightButtonText(CharSequence text) {
		mRightText.setText(text);
	}

	public void setTitle(CharSequence title) {
		mTitle.setText(title);
	}

	public void setLeftButtonVisibility(int visibility) {
		mLeftText.setVisibility(visibility);
	}

	public void setRightButtonVisibility(int visibility) {
		mRightText.setVisibility(visibility);
	}

	public void setLeftButtonClick(OnClickListener onClickListener) {
		mLeftText.setOnClickListener(onClickListener);
	}

	public void setRightButtonClick(OnClickListener onClickListener) {
		mRightText.setOnClickListener(onClickListener);
	}
}

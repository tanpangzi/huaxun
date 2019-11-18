package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyBackView1 extends RelativeLayout {
	private TextView backImage;
	private TextView mTitle;
	private Activity activity;
	private ImageButton image ;

	public MyBackView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.back_navigation1, this);
		backImage = (TextView) findViewById(R.id.back_image);
		backImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(activity!=null){
				activity.finish();
				activity.overridePendingTransition(R.anim.new_push_left_in,R.anim.new_push_left_out);// 从右向左推出动画效果
				}
			}
		});
		mTitle = (TextView) findViewById(R.id.back_txt);
		image = (ImageButton) findViewById(R.id.search_imagebtn);
	}

	/**
	 * 设置模块标题
	 * 
	 * @param text
	 */
	public void setBackText(CharSequence text) {
		mTitle.setText(text);
	}
	
	public void setSearchGone(){
		image.setVisibility(View.GONE);
	}

	public void setAddActivty(Activity activity) {
		this.activity = activity;
	}
	
	public void searchOnClickListener(OnClickListener onClickListener){
		image.setOnClickListener(onClickListener);
	}

 
 

}

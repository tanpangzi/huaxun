package cn.huaxunchina.cloud.location.app.view;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.tools.DisplayUtil;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.app.view.MyBackView1;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月20日 下午2:28:29 
 *
 */
public class LoginDialog extends Dialog {

	private static int theme = R.style.dialog;// 主题
	private Activity activity;
	private Context context;
	private ImageView figureImage;
	private LinearLayout layout1,layout2;
	private TextView tips_msg;
	private AnimationDrawable animationDrawable;
	private LocLogin locLogin;
	int width, height;
	public LoginDialog(Activity activity,int width,int height) {
		super(activity, theme);
		this.activity=activity;
		this.context=activity;
		this.width=width;
		this.height=height;
		// TODO Auto-generated constructor stub
	}
	
	public LoginDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	public void setLocLogin(LocLogin locLogin){
		this.locLogin=locLogin;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 
		 
		setContentView(R.layout.loc_login_dialog);
		MyBackView1 back = (MyBackView1)findViewById(R.id.back);
		back.setBackText("即时定位");
		back.setAddActivty(activity);
		back.setSearchGone();
		int h   = DisplayUtil.dip2px(50f);
		LayoutParams lparams_hours = new LayoutParams(width, h);
		back.setLayoutParams(lparams_hours);
		
		figureImage=(ImageView)findViewById(R.id.figureImage);
		layout1=(LinearLayout)findViewById(R.id.tips1);
		layout2=(LinearLayout)findViewById(R.id.tips2);
		tips_msg = (TextView)findViewById(R.id.tips_msg);
		findViewById(R.id.loc_loginbtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				locLogin.loginCallBack();
			}
		});
		setOnKeyListener(new OnKeyListener() {
	    @Override
	    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	    {
	    if (keyCode == KeyEvent.KEYCODE_SEARCH)
	    {
	     return true;
	    }
	    else
	    {
	     return false; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
	    }}
		});
		
	}
	
	
	public void startAnimation(){
		layout1.setVisibility(View.VISIBLE);
		layout2.setVisibility(View.GONE);
		figureImage.setImageResource(R.anim.loc_load);
		animationDrawable = (AnimationDrawable) figureImage.getDrawable();  
        animationDrawable.start();  
	}
	
	public void stopAnimation(){
		layout1.setVisibility(View.GONE);
		layout2.setVisibility(View.VISIBLE);
		figureImage.setImageResource(R.drawable.figure4);
	}
	
	public void setMsg(String msg){
		tips_msg.setText(msg);
		stopAnimation();
	}
	
	
	public interface LocLogin{
		
		public void loginCallBack();
		
	}
	

}

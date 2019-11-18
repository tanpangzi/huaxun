package cn.huaxunchina.cloud.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;

public class NoticePhotoDialog extends Dialog implements
		View.OnClickListener {
	private Button btn_pic;
	private Button btn_select;
	private Button btn_cancel;
	private Handler mHandler;
	private Context mcontext;
	private static int theme=R.style.transparentFrameWindowStyle;
	
	public NoticePhotoDialog(Context context, Handler handler) {
		super(context, theme);
		this.mHandler = handler;
		this.mcontext=context;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.photo_choose_dialog);
//		LinearLayout layout = (LinearLayout) findViewById(R.id.liner);
//		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
//		layout.setLayoutParams(lparams_hours);
		
//		Window window = this.getWindow();
//		window.setWindowAnimations(R.style.main_menu_animstyle);
//		
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
		Dialog dialog = new Dialog(mcontext);
		dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		btn_pic = (Button) findViewById(R.id.btn_pic);
		btn_select = (Button) findViewById(R.id.btn_select);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_pic.setOnClickListener(this);
		btn_select.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		// 设置显示动画
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = 0;
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		
		
		
		
		
		
		
		
		
		
		
//		WindowManager.LayoutParams wl = window.getAttributes();
//		wl.x = 0;
//		wl.y = -1000;
//		wl.gravity = Gravity.BOTTOM;
//		// 以下这两句是为了保证按钮可以水平满屏
//		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
//		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//		// 设置显示位置
//		this.onWindowAttributesChanged(wl);
		
//		WindowManager.LayoutParams winLP = window.getAttributes();
//		winLP.dimAmount = 0.3f;
//		winLP.width = CtripAppController.getWindowWidth();
//		window.setAttributes(winLP);
//		WindowManager.LayoutParams lp = getWindow().getAttributes();
//		lp.dimAmount = 0.5f;
//		getWindow().setAttributes(lp);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//		getWindow().setWindowAnimations(R.style.main_menu_animstyle);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pic:
			mHandler.sendEmptyMessage(HandlerCode.HANDLER_PIC);
			dismiss();
			break;
		case R.id.btn_select:
			mHandler.sendEmptyMessage(HandlerCode.HANDLER_GET_PHOTO);
			dismiss();
			break;
		case R.id.btn_cancel:
			dismiss();
			break;
		default:
			break;
		}

	}

}

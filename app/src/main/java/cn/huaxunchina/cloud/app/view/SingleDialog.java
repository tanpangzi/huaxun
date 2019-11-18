package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SingleDialog{
	private Dialog dialog;
	private Button button;
	private Context context;
	private TextView title;
	private TextView content;
	private String buttonText;
	public SingleDialog(Context context, int theme) {
		this.context = context;
		
		dialog = new Dialog(context, R.style.dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.session_out_dialog);
		WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
		lp.dimAmount=0.5f;
		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		button = (Button) dialog.findViewById(R.id.btn1);
		title = (TextView) dialog.findViewById(R.id.title);
		content = (TextView) dialog.findViewById(R.id.content);
		
	}
	
	

	
	public void setButtonClickListener(OnClickListener clickListener){
		button.setOnClickListener(clickListener);
	}
	
	public void setTitle(String title){
		this.title.setText(title);
	}
	
	public void setContent(String content){
		this.content.setText(content);
	}
	
	public void setButtonText(String text){
		this.button.setText(text);
	}
	
	public void show(){
		dialog.show();
	}
	
	  public void hide() {               
		  dialog.hide();        
		  }         
	  public void dismiss() {
		  dialog.dismiss();        
		  }

//@Override
//public void onClick(View v) {
//	switch (v.getId()) {
//	case R.id.btn1:
//		 android.os.Process.killProcess(android.os.Process.myPid());
//		 System.exit(0);
//		Intent intent = new Intent(context,PaxyLogin.class);
//		intent.putExtra("messageId", messageId);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		((Activity) context).startActivity(intent);
//		((Activity) context).finish();
//
//		break;
//	default:
//		break;
//	}
//	
//}

}

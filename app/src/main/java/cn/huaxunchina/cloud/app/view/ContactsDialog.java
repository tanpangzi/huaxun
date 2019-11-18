package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactsDialog extends Dialog {
	
	private Activity activity;
	private static int theme = R.style.dialog;// 主题
	private TextView tvConet,tvName,tvCancel,tvOk;
	private OnClickListener onClickListener;
	
	public ContactsDialog(Activity activity) {
		super(activity,theme);
		this.activity=activity;
	}

	public ContactsDialog(Context context, int theme) {
		super(context, theme);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_dialog);
		tvConet=(TextView)findViewById(R.id.conet);
		tvName=(TextView) findViewById(R.id.name);
		tvCancel=(TextView)findViewById(R.id.cancel);
		tvCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		tvOk=(TextView)findViewById(R.id.look_up);
		tvOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("=========OnClickListener1=====");
				dismiss();
				onClickListener.onClick();
				 
			}
		});
		 
	}
	
	
	public void setTitle(String title){
		tvName.setText(title);
	}
	
	public void setMessage(String msg){
		tvConet.setText(msg);
	}
	
	public void setNegativeButton(String str){
		tvCancel.setText(str);
	}
	
	public void setPositiveButton(String str){
		tvOk.setText(str);
	}
	
	public void setOkOnClickListener(OnClickListener onClickListener){
		this.onClickListener=onClickListener;
	}
	
	 
	public interface OnClickListener{
		public void onClick();
	}

}

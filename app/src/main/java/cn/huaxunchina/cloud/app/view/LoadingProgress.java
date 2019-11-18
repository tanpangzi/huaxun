package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class LoadingProgress {
	private Dialog dialog;
	private TextView tvTitle;
	String title = "加载中...";

	public LoadingProgress(Context context) {
		dialog = new Dialog(context, R.style.dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.loading_dialog);
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = 0.5f;
		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialog.setCancelable(true);
//		tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
//		tvTitle.setText(title);

	}

	public Dialog getDialog() {
		return dialog;
	}

//	public void setTitle(String title) {
//		this.tvTitle.setText(title);
//	}

	public void show() {
		dialog.show();
	}

	public void hide() {
		dialog.hide();
	}

	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

}

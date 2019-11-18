package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class PopMenu extends BaseActivity {
	private Context context;
	private PopupWindow popupWindow;
	private int viewId;
	private View view;
	private EditText editText;
	private int number;

	public PopMenu(final Context context, int viewId, int anim,
			final EditText editText, int i) {
		this.context = context;
		this.viewId = viewId;
		this.editText = editText;
		this.number = i;
		view = LayoutInflater.from(context).inflate(viewId, null);

		// Button btnCopy = (Button) view.findViewById(R.id.btnCopy);
		//
		// btnCopy.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// editText.requestFocus();
		// InputMethodManager imm = (InputMethodManager) context
		// .getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		// editText.setHint("回复" + String.valueOf(number) + "楼#");
		// if (popupWindow != null) {
		// popupWindow.dismiss();
		// }
		//
		// }
		// });
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		switch (anim) {
		case 0:
			popupWindow.setAnimationStyle(R.style.PopupAnimation);
			break;
		case 1:
			popupWindow.setAnimationStyle(R.style.PopupAnimationFade);
			break;
		default:
			break;
		}

		// new PopupWindow(view,context.getResources()
		// .getDimensionPixelSize(R.dimen.popmenu_width),
		// context.getResources()
		// .getDimensionPixelSize(R.dimen.popmenu_height));
		// context.getResources()
		// .getDimensionPixelSize(R.dimen.popmenu_width)
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	public PopMenu(final Context context, int viewId, int anim) {
		this.context = context;
		this.viewId = viewId;
		view = LayoutInflater.from(context).inflate(viewId, null);
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		switch (anim) {
		case 0:
			popupWindow.setAnimationStyle(R.style.PopupAnimation);
			break;
		case 1:
			popupWindow.setAnimationStyle(R.style.PopupAnimationFade);
			break;
		default:
			break;
		}

		// new PopupWindow(view,context.getResources()
		// .getDimensionPixelSize(R.dimen.popmenu_width),
		// context.getResources()
		// .getDimensionPixelSize(R.dimen.popmenu_height));
		// context.getResources()
		// .getDimensionPixelSize(R.dimen.popmenu_width)
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

	}

	// 设置菜单项点击监听器
	public void setOnItemClickListener(OnItemClickListener listener) {
		// this.listener = listener;
		// listView.setOnItemClickListener(listener);
	}

	public View getView() {
		return view;
	}

	// 下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent, int x, int y) {
		// popupWindow.showAsDropDown(parent);
		popupWindow.showAsDropDown(parent, x, y);
		// popupWindow.showAsDropDown(parent,
		// 10,
		// // 保证尺寸是根据屏幕像素密度来的
		// context.getResources().getDimensionPixelSize(
		// R.dimen.popmenu_yoff));

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// // 刷新状态
		popupWindow.update();
	}

	public void showAtLocationBottom(View parent) {
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// // 刷新状态
		popupWindow.update();
	}

	public void showAtLocation(View parent, int x, int y) {
		if (popupWindow != null) {
			popupWindow.update();
		}
		// int[] location = new int[2];
		// parent.getLocationOnScreen(location);
		//
		popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// // 刷新状态
		popupWindow.update();
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

}

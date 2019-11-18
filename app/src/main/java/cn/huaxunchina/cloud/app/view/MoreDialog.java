package cn.huaxunchina.cloud.app.view;

import java.util.ArrayList;
import java.util.List;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.model.More;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 主页tab更多
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月12日 下午4:35:43
 */
public class MoreDialog extends Dialog{

	private static int theme = R.style.dialog;// 主题
	private Context context = null;
	private GridView gridView = null;
	private List<More> listdata = null;
	private MoreBaseAdapter moreBaseAdapter = null;
	private MoreDialogCallBack callBack = null;

	public MoreDialog(Context context, List<More> listdata) {
		super(context, theme);
		this.context = context;
		this.listdata = listdata;
	}

	public MoreDialog(Context context, int theme) {
		super(context, theme);

	}

	public void setMoreDialogCallBack(MoreDialogCallBack callBack) {
		this.callBack = callBack;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moreview_layout);
		gridView = (GridView) findViewById(R.id.more_gview);
		moreBaseAdapter = new MoreBaseAdapter(listdata);
		gridView.setAdapter(moreBaseAdapter);
		gridView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP
						&& touch_num == true) {
					moreViewOut();
				}
				return false;
			}
		});
		moreViewIn();
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int id,
//					long arg3) {
//				if (callBack != null) {
//					dismiss();
//					callBack.OnItemClickListener(listdata.get(id));
//
//				}
//
//			}
//		});

		gridView.setLayoutAnimation(getAnimationController());

	}

	protected LayoutAnimationController getAnimationController() {
		LayoutAnimationController controller;
		Animation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);// 从0.5倍放大到1倍
		anim.setDuration(500);
		controller = new LayoutAnimationController(anim, 0.1f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		return controller;
	}

	boolean touch_num = true;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_UP && touch_num == true) {
			moreViewOut();
		}
		return super.onTouchEvent(event);
	}

	private void moreViewIn() {
		AnimationSet set = new AnimationSet(true);
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.more_in);
		animation.setInterpolator(new OvershootInterpolator(1F));
		set.addAnimation(animation);
		set.start();
		gridView.setAnimation(animation);
		gridView.setVisibility(View.VISIBLE);

	}

	private void moreViewOut() {
		touch_num = false;
		AnimationSet set = new AnimationSet(true);
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.more_out);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation ani) {
				dismiss();
				/*
				 * new Handler().postDelayed(new Runnable(){ public void run() {
				 * dismiss(); }}, 500);
				 */
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationStart(Animation arg0) {
			}
		});
		set.start();
		set.startNow();
		set.addAnimation(animation);
		// LayoutAnimationController controller = new
		// LayoutAnimationController(set, 0.5f);
		// gridView.setLayoutAnimation(controller);
		gridView.setAnimation(set);

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && touch_num == true) {
			moreViewOut();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	public interface MoreDialogCallBack {
		public abstract void OnItemClickListener(More more);
	}

	public class MoreBaseAdapter extends BaseAdapter {
		
		private List<More> listdata = new ArrayList<More>();

		public MoreBaseAdapter(List<More> listdata) {
			this.listdata = listdata;
		}

		@Override
		public int getCount() {
			return listdata.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int position, View view, ViewGroup arg2) {
			final ViewHolder holder;
			if (view == null) {
				view = View.inflate(context, R.layout.moreview_item, null);
				holder = new ViewHolder();
				holder.liner_dialog = (LinearLayout) view.findViewById(R.id.liner_dialog);
				holder.titletv = (TextView) view.findViewById(R.id.more_title);
				holder.iconimg = (ImageView) view.findViewById(R.id.more_icon);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			More more = listdata.get(position);
			holder.titletv.setText(more.getTitle());
			holder.iconimg.setBackgroundResource(more.getImg());
			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					float start = 1.0f;
					float end = 0.84f;
					Animation scaleAnimation = new ScaleAnimation(start, end, start, end,
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
					Animation endAnimation = new ScaleAnimation(end, start, end, start,
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
					scaleAnimation.setDuration(100);
					scaleAnimation.setFillAfter(true);
					endAnimation.setDuration(100);
					endAnimation.setFillAfter(true);
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						holder.liner_dialog.startAnimation(scaleAnimation);
						break;
					case MotionEvent.ACTION_UP:
						holder.liner_dialog.startAnimation(endAnimation);
						if (callBack != null) {
							dismiss();
							callBack.OnItemClickListener(listdata.get(position));
						}
						break;
						// 滑动出去不会调用action_up,调用action_cancel
					case MotionEvent.ACTION_CANCEL:
						holder.liner_dialog.startAnimation(endAnimation);
						break;
					default:
						break;
					}
					return true;
				}
			});
			return view;
		}

	}

	public class ViewHolder {
		LinearLayout liner_dialog;
		TextView titletv;
		ImageView iconimg;
	}

}

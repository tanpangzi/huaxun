package cn.huaxunchina.cloud.location.app.view;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.tools.DisplayUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * 测试电池量动态显示状态类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-22 上午9:44:07
 */
public class BatteryView extends ProgressBar {
	private int viewWidth;
	private int viewHeight;
	private Bitmap bitmap;
	private float power_percent;
	private Context context;
	private float left = DisplayUtil.dip2px(6.5f);
	private float top = DisplayUtil.dip2px(4f);
	private float right = DisplayUtil.dip2px(16f);
	private float bottom = DisplayUtil.dip2px(20f);
	

	public BatteryView(Context context) {
		super(context);
		this.context=context;
		initView(context);
	}

	public BatteryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		initView(context);
	}

	public BatteryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		initView(context);
	}

	protected synchronized void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
		this.setMeasuredDimension(viewWidth, viewHeight);

	}

	private void initView(Context context) {
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(h, w, oldw, oldh);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected synchronized  void onDraw(Canvas canvas) {//synchronized
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);// 画实心
		if (power_percent <= 0.2) {
			paint.setColor(context.getResources().getColor(R.color.loc_red));
		} else {
			paint.setColor(context.getResources().getColor(R.color.loc_bule));
		}
		canvas.drawRect(new RectF(left, top, right, bottom), paint);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		super.onDraw(canvas);

	}

	public void setPower(int power) {
		if(power>100){power=100;}//容错处
		if(power<0){power=0;}
		power_percent = power / 100.0f;
		top=(bottom-power_percent*(bottom-top));//计算top位置
		BitmapDrawable bitmapDrawable=null;
		if (power_percent <= 0.2) {
			bitmapDrawable = (BitmapDrawable)context.getResources().getDrawable(R.drawable.battery_low);
		} else {
			bitmapDrawable = (BitmapDrawable)context.getResources().getDrawable(R.drawable.battery_enough);
		}
		bitmap = bitmapDrawable.getBitmap();
		viewWidth = bitmap.getWidth();
		viewHeight = bitmap.getHeight();
		invalidate();
	}
}

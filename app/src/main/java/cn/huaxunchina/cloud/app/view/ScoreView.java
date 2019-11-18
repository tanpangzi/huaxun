package cn.huaxunchina.cloud.app.view;




import java.math.BigDecimal;

import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
/**
 * 
 *自定义曲线图
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月9日 上午8:35:56 
 *
 */
@SuppressLint("DrawAllocation")
public class ScoreView extends View {

	float bottomPadding = 100;
	float rightPadding = 0;
	float height = 0;
	float width = 0;
	private Bitmap rectf; //圆
	//float[][] points = {{17,10},{34,20},{50,100},{67,44},{84,50}};
	float[][] points = new float[5][2];
	int[][] points_list = new int[5][3];
	String[] examNos = new String[5];
	private Context context;
	int __padding = 0;
	
	public ScoreView(Context context,float height,float width,int[] scoreList,String[] examNos,int __padding) {
		super(context);
		this.context=context;
		this.height=height;//(height-100);
		this.width=width;//(width-100);
		this.examNos=examNos;
		this.__padding=__padding;
		Resources res = getResources();
		rectf=BitmapFactory.decodeResource(res, R.drawable.rectf_icon);
		
		if(scoreList!=null){
			int size = scoreList.length;
			for(int i=0;i<size;i++){
				switch (i) {
				case 0:
					points[i][0]=17;
					points[i][1]=scoreList[i];
					break;
				case 1:
					points[i][0]=34;
					points[i][1]=scoreList[i];
					break;
				case 2:
					points[i][0]=50;
					points[i][1]=scoreList[i];
					break;
				case 3:
					points[i][0]=67;
					points[i][1]=scoreList[i];
					break;
				case 4:
					points[i][0]=84;
					points[i][1]=scoreList[i];
					break;
				}
			}
			//System.out.println(points);
		}
		 
		
	}

	public ScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		float h = height;
		float w = width;
		float hh = (h - this.bottomPadding) / 150.0f;
		
		
		BigDecimal b = new BigDecimal(hh); 
		hh = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue(); 
		
		float hw = (w - this.rightPadding) / 100.0f;
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		paint.setTextSize(16);
		paint.setColor(Color.parseColor("#cccccc"));
		Path path = new Path();
		int h_size = 15;
		for (int i = 1; i < h_size; i++)
		{
			path.moveTo(0, h - this.bottomPadding - (h - this.bottomPadding) / h_size * i);
			path.lineTo(w - rightPadding, h - this.bottomPadding - (h - this.bottomPadding) / h_size*i);
			canvas.drawText(i+"0", 0 + hw*2, h - this.bottomPadding - (h - this.bottomPadding) / h_size * i - 2-hh*1, paint);
		}
		int w_size = 6;
		for (int i = 1; i < w_size; i++)//10
		{
			//path.moveTo(0 + (w - this.rightPadding) / w_size* i, 0 );
			//path.lineTo(0 + (w - this.rightPadding) / w_size * i, h - this.bottomPadding );
			canvas.drawText(i+"",(w - this.rightPadding) / w_size * i , h - this.bottomPadding+25, paint);
		}
		paint.setStrokeWidth(2);
		//paint.setColor(Color.RED);
		canvas.drawLine(0, h - this.bottomPadding, w - rightPadding, h - this.bottomPadding, paint);
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5}, 1);
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);
		paint.setPathEffect(null);
		path.reset();

		
		//float[][] points = {{10,10},{20,30},{30,20},{40,30},{50,20},{60,35},{70,30},{80,30}};
		//float[][] points = {{17,10},{34,20},{50,100},{67,44},{84,50}};
		
		for (int i = 0; i < points.length ; i++)	
		{
			float[] start = points[i];
			if(i < points.length - 1)
			{
				float[] end = points[i+1];
				path.moveTo(start[0]*hw,(h - this.bottomPadding) - start[1]*hh);
				path.lineTo(end[0]*hw,(h - this.bottomPadding) - end[1]*hh);
				
			}

		}
		paint.setColor(Color.parseColor("#fa5d5d"));
		canvas.drawPath(path, paint);
		path.reset();
		paint.setStrokeWidth(2);
		float r = 0;
		for (int i = 0; i < points.length ; i++)	
		{
			float[] start = points[i];
            //path.addOval(new RectF(start[0]*hw -r,(h - this.bottomPadding) - start[1]*hh -r, start[0]*hw +r,(h - this.bottomPadding) - start[1]*hh+r) , Direction.CW);
			canvas.drawBitmap(rectf, start[0]*hw-__padding, (h - this.bottomPadding) - start[1]*hh-__padding, paint);
			 
		}
		paint.setColor(Color.parseColor("#fa5d5d"));
		canvas.drawPath(path, paint);
		path.reset();
		//r = r*2;
		r = 0;
		for (int i = 0; i < points.length ; i++)	
		{
			float[] start = points[i];
			//path.addOval(new RectF(start[0]*hw -r,(h - this.bottomPadding) - start[1]*hh -r, start[0]*hw +r,(h - this.bottomPadding) - start[1]*hh+r) , Direction.CW);
			canvas.drawBitmap(rectf, start[0]*hw-__padding, (h - this.bottomPadding) - start[1]*hh-__padding, paint);
			//System.out.println("圆:["+(start[0]*hw-10)+"]["+((h - this.bottomPadding) - start[1]*hh-10)+"]");
			
			 
			
				points_list[i][0]=(int)(start[0]*hw-r);
				points_list[i][1]=(int)((h - this.bottomPadding) - start[1]*hh-r);
				points_list[i][2]=(int)start[1];
			
			 
		}
		paint.setColor(Color.parseColor("#ffebcc"));
		canvas.drawPath(path, paint);
		path.reset();
		path.setFillType(FillType.WINDING);
		for (int i = 0; i < points.length ; i++)	
		{
			float[] start = points[i];
			if(i < points.length - 1)
			{
				float[] end = points[i+1];
				if(i==0)
				{
					path.moveTo(start[0]*hw,(h - this.bottomPadding) - start[1]*hh);
				}
				path.lineTo(end[0]*hw,(h - this.bottomPadding) - end[1]*hh);
			}
			else
			{
				float[] end = points[0];
				path.lineTo(start[0]*hw,h - this.bottomPadding);
				path.lineTo(end[0]*hw,(h - this.bottomPadding));
				path.lineTo(end[0]*hw,(h - this.bottomPadding) - end[1]*hh);
			}

		}
		paint.setAlpha(80);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawPath(path, paint);
		
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		boolean value = super.onTouchEvent(event); 
		int x = (int)event.getX();
		int y = (int)event.getY();
		//System.out.println("[x:"+x+"][y:"+y+"]");
		//Toast.makeText(context, x+"  "+y, Toast.LENGTH_LONG).show();
		int size = points.length;
		for(int i=0;i<size;i++){
			int p_x = points_list[i][0];
			int p_y = points_list[i][1];
			//System.out.println("[p_x:"+p_x+"][p_y:"+p_y+"]");
			/*if(x==p_x&&y==p_y){
				float s = points_list[i][2];
				//Toast.makeText(context, s+"", Toast.LENGTH_LONG).show();	
			}*/
			if(isRange(p_x,x)&&isRange(p_y,y)){
				String examNo = examNos[i];
				String _examNo = examNo==null?"":"("+examNo+")";
				float s = points_list[i][2];
				String _s = String.valueOf((int)s)+"分";
				Toast.makeText(context, _s+_examNo, 100).show();	
			}
			
		}
		
		return value;
	}
	
	
	private boolean isRange(int a,int b){
		int _range = 30;
		boolean is_range = false;
		if((b-_range<a)&&(b+_range>a)){
			is_range = true;
		}
		return is_range;
	}
	
}

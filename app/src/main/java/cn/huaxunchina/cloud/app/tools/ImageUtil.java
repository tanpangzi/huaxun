package cn.huaxunchina.cloud.app.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.huaxunchina.cloud.app.activity.BaseActivity;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;

public class ImageUtil extends BaseActivity {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int TOP = 3;
	public static final int BOTTOM = 4;

	// 将Drawable转化为Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * 将图片变为圆角
	 * 
	 * @param bitmap
	 *            原Bitmap图片
	 * @param pixels
	 *            图片圆角的弧度(单位:像素(px))
	 * @return 带有圆角的图片(Bitmap 类型)
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 
	 * 由资源id获取图片
	 * 
	 * 
	 * 
	 * @param context
	 * 
	 * @param resId
	 * 
	 * @return
	 */

	public static Drawable getDrawableById(Context context, int resId) {

		if (context == null) {

			return null;

		}

		return context.getResources().getDrawable(resId);

	}

	/**
	 * 
	 * 由资源id获取位图
	 * 
	 * 
	 * 
	 * @param context
	 * 
	 * @param resId
	 * 
	 * @return
	 */

	public static Bitmap getBitmapById(Context context, int resId) {

		if (context == null) {

			return null;

		}

		return BitmapFactory.decodeResource(context.getResources(), resId);

	}

	/**
	 * 
	 * 将Bitmap转化为字节数组
	 * 
	 * 
	 * 
	 * @param bitmap
	 * 
	 * @return
	 */

	public static byte[] bitmap2byte(Bitmap bitmap) {

		ByteArrayOutputStream baos = null;

		try {

			baos = new ByteArrayOutputStream();

			bitmap.compress(CompressFormat.PNG, 100, baos);

			byte[] array = baos.toByteArray();

			baos.flush();

			baos.close();

			return array;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 
	 * 将byte数组转化为bitmap
	 * 
	 * 
	 * 
	 * @param data
	 * 
	 * @return
	 */

	public static Bitmap byte2bitmap(byte[] data) {

		if (null == data) {

			return null;

		}

		return BitmapFactory.decodeByteArray(data, 0, data.length);

	}

	/**
	 * 
	 * 将Drawable转化为Bitmap
	 * 
	 * 
	 * 
	 * @param drawable
	 * 
	 * @return
	 */

	public static Bitmap drawable2bitmap(Drawable drawable) {

		if (null == drawable) {

			return null;

		}

		int width = drawable.getIntrinsicWidth();

		int height = drawable.getIntrinsicHeight();

		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable

		.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888

		: Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);

		drawable.setBounds(0, 0, width, height);

		drawable.draw(canvas);// 重点

		return bitmap;

	}

	/**
	 * 
	 * 将bitmap转化为drawable
	 * 
	 * 
	 * 
	 * @param bitmap
	 * 
	 * @return
	 */

	public static Drawable bitmap2Drawable(Bitmap bitmap) {

		if (bitmap == null) {

			return null;

		}

		return new BitmapDrawable(bitmap);

	}

	/**
	 * 
	 * 按指定宽度和高度缩放图片,不保证宽高比例
	 * 
	 * 
	 * 
	 * @param bitmap
	 * 
	 * @param w
	 * 
	 * @param h
	 * 
	 * @return
	 */

	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {

		if (bitmap == null) {

			return null;

		}

		int width = bitmap.getWidth();

		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();

		float scaleWidht = ((float) w / width);

		float scaleHeight = ((float) h / height);

		matrix.postScale(scaleWidht, scaleHeight);

		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,

		matrix, true);

		return newbmp;

	}

	/**
	 * 
	 * 将bitmap位图保存到path路径下，图片格式为Bitmap.CompressFormat.PNG，质量为100
	 * 
	 * 
	 * 
	 * @param bitmap
	 * 
	 * @param path
	 */

	public static boolean saveBitmap(Bitmap bitmap, String path) {

		try {

			File file = new File(path);

			File parent = file.getParentFile();

			if (!parent.exists()) {

				parent.mkdirs();

			}

			FileOutputStream fos = new FileOutputStream(file);

			boolean b = bitmap.compress(CompressFormat.PNG, 100, fos);

			fos.flush();

			fos.close();

			return b;

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return false;

	}

	/**
	 * 
	 * 将bitmap位图保存到path路径下
	 * 
	 * 
	 * 
	 * @param bitmap
	 * 
	 * @param path
	 * 
	 *            保存路径-Bitmap.CompressFormat.PNG或Bitmap.CompressFormat.JPEG.PNG
	 * 
	 * @param format
	 * 
	 *            格式
	 * 
	 * @param quality
	 * 
	 *            Hint to the compressor, 0-100. 0 meaning compress for small
	 * 
	 *            size, 100 meaning compress for max quality. Some formats, like
	 * 
	 *            PNG which is lossless, will ignore the quality setting
	 * 
	 * @return
	 */

	public static boolean saveBitmap(Bitmap bitmap, String path,

	CompressFormat format, int quality) {

		try {

			File file = new File(path);

			File parent = file.getParentFile();

			if (!parent.exists()) {

				parent.mkdirs();

			}

			FileOutputStream fos = new FileOutputStream(file);

			boolean b = bitmap.compress(format, quality, fos);

			fos.flush();

			fos.close();

			return b;

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return false;

	}

	/**
	 * 
	 * 获得圆角图片
	 * 
	 * 
	 * 
	 * @param bitmap
	 * 
	 * @param roundPx
	 * 
	 * @return
	 */

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		if (bitmap == null) {

			return null;

		}

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),

		bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;

	}

	/**
	 * 
	 * 获得带倒影的图片
	 */

	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {

		if (bitmap == null) {

			return null;

		}

		final int reflectionGap = 4;

		int width = bitmap.getWidth();

		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();

		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,

		width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,

		(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);

		canvas.drawBitmap(bitmap, 0, 0, null);

		Paint deafalutPaint = new Paint();

		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();

		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,

		bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,

		0x00ffffff, TileMode.CLAMP);

		paint.setShader(shader);

		// Set the Transfer mode to be porter duff and destination in

		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		// Draw a rectangle using the paint with our linear gradient

		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()

		+ reflectionGap, paint);

		return bitmapWithReflection;

	}

	/** */
	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/** */
	/**
	 * 去色同时加圆角
	 * 
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		return toRoundCorner(toGrayscale(bmpOriginal), pixels);
	}

	// /** */
	// /**
	// * 把图片变成圆角
	// *
	// * @param bitmap
	// * 需要修改的图片
	// * @param pixels
	// * 圆角的弧度
	// * @return 圆角图片
	// */
	// public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
	// Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	// bitmap.getHeight(), Config.ARGB_8888);
	// Canvas canvas = new Canvas(output);
	// final int color = 0xff424242;
	// final Paint paint = new Paint();
	// final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	// final RectF rectF = new RectF(rect);
	// final float roundPx = pixels;
	// paint.setAntiAlias(true);
	// canvas.drawARGB(0, 0, 0, 0);
	// paint.setColor(color);
	// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	// paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	// canvas.drawBitmap(bitmap, rect, rect, paint);
	// return output;
	// }

	/** */
	/**
	 * 使圆角功能支持BitampDrawable
	 * 
	 * @param bitmapDrawable
	 * @param pixels
	 * @return
	 */
	public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
			int pixels) {
		Bitmap bitmap = bitmapDrawable.getBitmap();
		bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
		return bitmapDrawable;
	}

	/**
	 * 读取路径中的图片，然后将其转化为缩放后的bitmap
	 * 
	 * @param path
	 */
	public static void saveBefore(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		if (be <= 0)
			be = 1;
		options.inSampleSize = 2; // 图片长宽各缩小二分之一
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		bitmap = BitmapFactory.decodeFile(path, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		System.out.println(w + " " + h);
		// savePNG_After(bitmap,path);
		saveJPGE_After(bitmap, path);
	}

	/**
	 * 保存图片为PNG
	 * 
	 * @param bitmap
	 * @param name
	 */
	public static void savePNG_After(Bitmap bitmap, String name) {
		File file = new File(name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片为JPEG
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static void saveJPGE_After(Bitmap bitmap, String path) {
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 水印
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createBitmapForWatermark(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
		// save all clip
		cv.save();// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	/**
	 * 图片合成
	 * 
	 * @return
	 */
	public static Bitmap potoMix(int direction, Bitmap... bitmaps) {
		if (bitmaps.length <= 0) {
			return null;
		}
		if (bitmaps.length == 1) {
			return bitmaps[0];
		}
		Bitmap newBitmap = bitmaps[0];
		// newBitmap = createBitmapForFotoMix(bitmaps[0],bitmaps[1],direction);
		for (int i = 1; i < bitmaps.length; i++) {
			newBitmap = createBitmapForFotoMix(newBitmap, bitmaps[i], direction);
		}
		return newBitmap;
	}

	private static Bitmap createBitmapForFotoMix(Bitmap first, Bitmap second,
			int direction) {
		if (first == null) {
			return null;
		}
		if (second == null) {
			return first;
		}
		int fw = first.getWidth();
		int fh = first.getHeight();
		int sw = second.getWidth();
		int sh = second.getHeight();
		Bitmap newBitmap = null;
		if (direction == LEFT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, sw, 0, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == RIGHT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, fw, 0, null);
		} else if (direction == TOP) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, sh, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == BOTTOM) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, 0, fh, null);
		}
		return newBitmap;
	}

	/**
	 * 把图片按照屏幕宽度缩放，使图片宽度缩放到屏幕宽度
	 * 
	 * @param activity
	 *            上下文activity
	 * @param imageView
	 *            设置图片的ImageView
	 * @param id
	 *            需要缩放的drawable的id
	 */
	public static Bitmap setScaleImage(Activity activity, Bitmap bm) {
		// 得到需要缩放的图片，并转换成BitMap
		// Drawable drawable = activity.getResources().getDrawable(
		// id);
		// BitmapDrawable bd = (BitmapDrawable) drawable;
		// Bitmap bm = bd.getBitmap();

		// 得到当前屏幕分辨率（得到屏幕宽，按宽与图片的比例进行缩放，目的是使图片宽占满屏幕）
		Display display = activity.getWindowManager().getDefaultDisplay();
		int srcWidth = display.getWidth();
		// 缩放比例
		float scaleWidth = (float) srcWidth / bm.getWidth();
		// 缩放动作
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		// 重构图片
		// 1.原始bitmap；2.3.第一个像素的x/y坐标；4.5.图片的原始宽高；
		// 6.matrix矩阵对象，包含了缩放比；7.如果真的需要过滤，仅适用于如果矩阵包含的不仅仅是转化。
		Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
				bm.getHeight(), matrix, true);
		return bitmap;
	}

}

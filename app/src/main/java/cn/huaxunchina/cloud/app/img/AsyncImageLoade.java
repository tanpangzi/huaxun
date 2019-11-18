package cn.huaxunchina.cloud.app.img;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncImageLoade {
	private static HashMap<String, SoftReference<Drawable>> imageCache;

	static {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable loadDrawable(final String imageUrl, final String imgName,
			final ImageView imageView, final ImageLoader imageLoader) {
		if (imageCache.containsKey(imageUrl)) {
			// 从缓存中获取
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				// 回调函数
				imageLoader.ImageLoader(imageView, (Bitmap) message.obj);
			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				// 下载图片
				Bitmap bitmap = ImaDownload.getbitmap(imageUrl, imgName);
				// imageCache.put(imageUrl,new SoftReference<Drawable>(new
				// BitmapDrawable(bitmap)));
				// 发送给当前消息
				Message message = handler.obtainMessage(0, bitmap);
				handler.sendMessage(message);
			}
		}).start();

		return null;
	}

	 

}

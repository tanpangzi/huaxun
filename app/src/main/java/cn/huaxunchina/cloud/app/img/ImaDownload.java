package cn.huaxunchina.cloud.app.img;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.huaxunchina.cloud.app.common.Constant;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImaDownload {


	public static Bitmap getbitmap(String url, String img_name) {
		String xxx = url;
		Bitmap bitmap = null;
		String path = Constant.IMGCACHE_SAVE_PATH;
		String f = path + MD5.getMD5(img_name);
		File file = new File(f);
		try {

			if (file.exists()) // 是否以存在
			{
				bitmap = readBitMap(file);
			} else {
				File f1 = new File(path);
				if (!f1.exists()) {
					f1.mkdirs();// 创建文件夹
				}
				file.createNewFile();// 创建文件
				FileOutputStream outputStream = new FileOutputStream(file); // 写入流
				URL u = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) u.openConnection(); // 得到流
				InputStream inputStream = conn.getInputStream();

				byte[] buffer = new byte[1024];
				int len = 0;

				while ((len = inputStream.read(buffer)) != -1) { // 开始写
					outputStream.write(buffer, 0, len);
				}
				outputStream.flush();
				outputStream.close();// 关闭流
				bitmap = readBitMap(file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap readBitMap(File file) throws FileNotFoundException {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = 2;

		// 获取资源图片
		InputStream is = new FileInputStream(file);
		// Bitmap imgBit = BitmapFactory.decodeStream(is);
		Bitmap imgBit = (new WeakReference<Bitmap>(BitmapFactory.decodeStream(
				is, null, opt))).get();
		BitmapFactory.decodeStream(is, null, opt);
		return imgBit;
	}

}

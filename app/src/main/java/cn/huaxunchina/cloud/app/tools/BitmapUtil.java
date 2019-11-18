package cn.huaxunchina.cloud.app.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;

public class BitmapUtil {
	
	// ��byte[]ת����InputStream  
    public static InputStream Byte2InputStream(byte[] b) {  
        ByteArrayInputStream bais = new ByteArrayInputStream(b);  
        return bais;  
    }  
  
    // ��InputStreamת����byte[]  
    public static byte[] InputStream2Bytes(InputStream is) {  
        String str = "";  
        byte[] readByte = new byte[1024];  
        int readCount = -1;  
        try {  
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {  
                str += new String(readByte).trim();  
            }  
            return str.getBytes();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    // ��Bitmapת����InputStream  
    public static InputStream Bitmap2InputStream(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        if(bm != null && !bm.isRecycled()){
        	bm.recycle();
        	System.gc();
        }
        return is;  
    }  
  
    // ��Bitmapת����InputStream  
    public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray()); 
        if(bm != null && !bm.isRecycled()){
        	bm.recycle();
        	System.gc();
        }
        return is;  
    }  
  
    // ��InputStreamת����Bitmap  
    public static Bitmap InputStream2Bitmap(InputStream is) {  
        return BitmapFactory.decodeStream(is);  
    }  
  
    // Drawableת����InputStream  
    public static InputStream Drawable2InputStream(Drawable d) {  
        Bitmap bitmap = drawable2Bitmap(d);  
        return Bitmap2InputStream(bitmap);  
    }  
  
    // InputStreamת����Drawable  
    public static Drawable InputStream2Drawable(InputStream is) {  
        Bitmap bitmap = InputStream2Bitmap(is);  
        return bitmap2Drawable(bitmap);  
    }  
  
  
  
  
    // Drawableת����Bitmap  
    public static Bitmap drawable2Bitmap(Drawable drawable) {  
        Bitmap bitmap = Bitmap  
                .createBitmap(  
                        drawable.getIntrinsicWidth(),  
                        drawable.getIntrinsicHeight(),  
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                                : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);  
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
                drawable.getIntrinsicHeight());  
        drawable.draw(canvas);  
        return bitmap;  
    }  
  
    // Bitmapת����Drawable  
    public static Drawable bitmap2Drawable(Bitmap bitmap) {  
        BitmapDrawable bd = new BitmapDrawable(bitmap);  
        Drawable d = (Drawable) bd;  
        return d;  
    }  
    
    
    public static Bitmap CompressionBigBitmap(Bitmap bitmap) { // ��ͼ���������

		Bitmap destBitmap = null;

		if (bitmap.getHeight() > 640) { // ͼƬ��ȵ���Ϊ640�������������ģ���һ���������ŵ����Ϊ640

			float scaleValue = (float) (640f / bitmap.getHeight());
			Matrix matrix = new Matrix();
			// matrix.setRotate(90); // ���ϵͳ���գ���ת90��
			matrix.postScale(scaleValue, scaleValue);
			destBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
		} else {

			return bitmap;

		}

		return destBitmap;

	}
    
    public static Bitmap compressImage(Bitmap image) {  
    	  
    	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ�������ݴ�ŵ�baos��  
    	        int options = 90;  
    	        while ( baos.toByteArray().length / 1024>100) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
    	            baos.reset();//����baos�����baos  
    	            options -= 10;//ÿ�ζ�����10  
    	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ�������ݴ�ŵ�baos��  
    	       }  
    	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ��������baos��ŵ�ByteArrayInputStream��  
    	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream������ͼƬ  
    	        return bitmap;  
    	    }  

    public static Bitmap getimage(String filePath) {  
    	        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        	    newOpts.inPurgeable = true;    
        	    newOpts.inInputShareable = true; 
    	        newOpts.inPreferredConfig = Config.RGB_565;
    	        //��Ϊtrue������ϵͳ��ͼƬ�����ڴ棬ֻ�ǻ�ȡͼƬ����Ϣ  
    	        newOpts.inTempStorage = new byte[16*1024];
    	        newOpts.inJustDecodeBounds = true;
    	        //��ʱ���ص�bitmapֻ��һ����  
    	        Bitmap bitmap = BitmapFactory.decodeFile(filePath,newOpts);
    	        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
    	        newOpts.inJustDecodeBounds = false;
    	        int w = newOpts.outWidth;  //��ȡԭͼ��
    	        int h = newOpts.outHeight;  //��ȡԭͼ��
    	        //���������ֻ�Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
    	        float hh = 800f;//�������ø߶�Ϊ800f  
    	        float ww = 480f;//�������ÿ��Ϊ480f  
    	        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ����ݽ��м��㼴��  
    	        int be = 1;//be=1��ʾ������  
    	        if (w > h && w > ww) {//����ȴ�Ļ���ݿ�ȹ̶���С����  
    	            be = (int) (newOpts.outWidth / ww);  
    	        } else if (w < h && h > hh) {//���߶ȸߵĻ���ݿ�ȹ̶���С����  
    	            be = (int) (newOpts.outHeight / hh);  
    	        }  
    	       // be = (int)(h / (float)400);   
    	        //be = (int) ((w / ww + h/ hh) / 2);
    	        if (be <= 0)  
    	            be = 1;  
    	        newOpts.inSampleSize = be;//�������ű���  
    	        bitmap = BitmapFactory.decodeFile(filePath,newOpts);
    	        return compressImage(bitmap);//ѹ���ñ����С���ٽ�������ѹ��  
    	    }
    
    public static  Bitmap comp(Bitmap bitmap) {  
    	

    	    ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 20);         
    	    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
    	    if(baos.toByteArray().length / 1024>1024) {//�ж����ͼƬ����1M,����ѹ�����������ͼƬ��BitmapFactory.decodeStream��ʱ���    
    	        baos.reset();//����baos�����baos  
    	        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);//����ѹ��50%����ѹ�������ݴ�ŵ�baos��  
    	    }  
    	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
    	    BitmapFactory.Options newOpts = new BitmapFactory.Options();
    	    newOpts.inPurgeable = true;    
    	    newOpts.inInputShareable = true; 
    	    newOpts.inPreferredConfig = Config.RGB_565;
    	    //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
    	    newOpts.inJustDecodeBounds = true;  
    	    Bitmap bm = BitmapFactory.decodeStream(isBm, null, newOpts);  
    	    newOpts.inJustDecodeBounds = false;  
    	    int w = newOpts.outWidth;  
    	    int h = newOpts.outHeight;  
    	    //���������ֻ�Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
    	    float hh = 800f;//�������ø߶�Ϊ800f  
    	    float ww = 480f;//�������ÿ��Ϊ480f  
    	    //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ����ݽ��м��㼴��  
    	    int be = 1;//be=1��ʾ������  
//    	    if (w > h && w > ww) {//����ȴ�Ļ���ݿ�ȹ̶���С����  (int) ((w / STANDARD_WIDTH + h/ STANDARD_HEIGHT) / 2);
//    	        be = (int) (newOpts.outWidth / ww);  
//    	    } else if (w < h && h > hh) {//���߶ȸߵĻ���ݿ�ȹ̶���С����  
//    	        be = (int) (newOpts.outHeight / hh);  
//    	    }  
    	    be = (int) ((w / ww + h/ hh) / 2);
    	    if (be <= 0)  
    	        be = 1;  
    	    newOpts.inSampleSize = be;//�������ű���  
    	    //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
    	    isBm = new ByteArrayInputStream(baos.toByteArray());  
    	    bm = BitmapFactory.decodeStream(isBm, null, newOpts);  
    	    return compressImage(bm);//ѹ���ñ����С���ٽ�������ѹ��   
    	}  

//  public static  long getBitmapsize(Bitmap bitmap){
//	 
//   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
//	   return bitmap.getByteCount();
//   }
//   
//   return bitmap.getRowBytes() * bitmap.getHeight();
//
//}
    
    /** 
     * ��ȡͼƬ���ԣ���ת�ĽǶ� 
     * @param path ͼƬ���·�� 
     * @return degree��ת�ĽǶ� 
     */  
       public static int readPictureDegree(String path) {  
           int degree  = 0;  
           try {  
                   ExifInterface exifInterface = new ExifInterface(path);  
                   int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
                   switch (orientation) {  
                   case ExifInterface.ORIENTATION_ROTATE_90:  
                           degree = 90;  
                           break;  
                   case ExifInterface.ORIENTATION_ROTATE_180:  
                        degree = 180;  
                           break;  
                   case ExifInterface.ORIENTATION_ROTATE_270:  
                           degree = 270;  
                           break;  
                   }  
           } catch (IOException e) {  
                   e.printStackTrace();  
           }  
           return degree;  
       } 

           /* * ��תͼƬ 
            * @param angle 
            * @param bitmap 
            * @return Bitmap 
            */  
           public static Bitmap rotaingBitmap(int angle , Bitmap bitmap) {  
               //��תͼƬ ����  
               Matrix matrix = new Matrix(); 
               matrix.postRotate(angle);  
               // �����µ�ͼƬ  
               Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
                       bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
               return resizedBitmap;
           }
           /**  
            *  ����ͼƬ   
            * @param bm ��Ҫת����bitmap  
            * @param newWidth�µĿ�  
            * @param newHeight�µĸ�    
            * @return ָ����ߵ�bitmap  
            */  
            public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){    
               // ���ͼƬ�Ŀ��    
               int width = bm.getWidth();    
               int height = bm.getHeight();    
               // �������ű���    
               float scaleWidth = ((float) newWidth) / width;    
               float scaleHeight = ((float) newHeight) / height;    
               // ȡ����Ҫ���ŵ�matrix����    
               Matrix matrix = new Matrix();    
               matrix.postScale(scaleWidth, scaleHeight);    
               // �õ��µ�ͼƬ    
               Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);    
               return newbm;    
           } 

}

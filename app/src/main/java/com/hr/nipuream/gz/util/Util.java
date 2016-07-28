package com.hr.nipuream.gz.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.hr.nipuream.gz.GZApplication;

import junit.framework.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 描述：工具类
 * 作者：Nipuream
 * 时间: 2016-07-21 16:38
 * 邮箱：571829491@qq.com
 */
public class Util {

    public static final String PACKAGE_NAME = "com.hr.nipuream.gz";

    /**
     * 获取版本名
     * @return
     */
    public static String getVerName() {
        String verName = "";
        try {
            verName = GZApplication.getInstance().getPackageManager().getPackageInfo(
                    PACKAGE_NAME, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        return verName;
    }

    /**
     * 获取版本号
     *
     * @return
     * @throws Exception
     */
    public static int getVerCode() {
        int verCode = -1;
        try {
            verCode = GZApplication.getInstance().getPackageManager().getPackageInfo(
                    PACKAGE_NAME, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(calendar.getTime());
        return time;
    }


    /**
     * 服务是否在运行
     * @param className
     * @return
     */
    public  static boolean isServiceRunning(String className) {

        boolean IsRunning = false;
        try {
            ActivityManager activityManager = (ActivityManager) GZApplication.getInstance()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                    .getRunningServices(100);
            if (!(serviceList.size() > 0)) {
                return false;
            }
            for (int i = 0; i < serviceList.size(); i++) {
                if (serviceList.get(i).service.getClassName().equals(className) == true) {
                    IsRunning = true;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return IsRunning;
    }


    /**
     * 特点: 通过设置采样率, 减少图片的像素, 达到对内存中的Bitmap进行压缩
     * @param image
     * @return
     */
    public static Bitmap compressBmpFromBmp(Bitmap image, boolean isPng) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;

        if(isPng)
            image.compress(Bitmap.CompressFormat.PNG, 30, baos);
        else
            image.compress(Bitmap.CompressFormat.JPEG, 30, baos);

        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            if(options <= 0){
                options = 0;
                break;
            }
            if(isPng)
                image.compress(Bitmap.CompressFormat.PNG, options, baos);
            else
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap  = BitmapFactory.decodeStream(isBm, null, null);

        return bitmap;
    }

    /**
     * 改变图片大小，压缩图片
     * @param image
     * @param desHeight 期望图片高
     * @param desWidth  期望图片宽
     * @return
     */
    public static Bitmap compressSizeFromBmp(Bitmap image,float desHeight,float desWidth){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, 30, baos);

        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        //		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //		Bitmap bitmap  = BitmapFactory.decodeStream(isBm, null, null);

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        //		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        Bitmap bm = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size(), newOpts);

        if(!image.isRecycled()){
            image.recycle();
        }

        //下次读内容，压缩大小
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && w > desWidth) {
            be = (int) (newOpts.outWidth / desWidth);
        } else if (w < h && h > desHeight) {
            be = (int) (newOpts.outHeight / desHeight);
        }
        if (be <= 0)
            be = 1;

        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bm = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size(), newOpts);

        return bm;
    }

    //对图片大小进行压缩
    public  static Bitmap compressImageFromFile(String srcPath,float hh,float ww) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        //其实是无效的,大家尽管尝试
        //原来的方法调用了这个方法企图进行二次压缩
        //		return compressBmpFromBmp(bitmap);
        return bitmap;
    }

    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
    public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
        Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }

            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;
            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            options.inJustDecodeBounds = false;

            Bitmap bm = BitmapFactory.decodeFile(path, options);
            if (bm == null) {
                return null;
            }

            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (scale != null) {
                bm.recycle();
                bm = scale;
            }

            if (crop) {
                final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
                if (cropped == null) {
                    return bm;
                }

                bm.recycle();
                bm = cropped;
            }
            return bm;

        } catch (final OutOfMemoryError e) {
            options = null;
        }

        return null;
    }


    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**获得sdcard根目录*/
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


}

package npwidget.nopointer.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * Bitmap 工具类
 */
public class BitmapUtils {

    private static final String tag = "BitmapUtils";

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    public static Bitmap getCropped2Bitmap(Bitmap bmp) {
        int radius = 50;
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;
        if (bmp.getWidth() != diameter || bmp.getHeight() != diameter)
            scaledSrcBmp = Bitmap.createScaledBitmap(bmp, diameter, diameter, false);
        else
            scaledSrcBmp = bmp;
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2, scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);

        return output;
    }


    public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int newWidth = w;
            int newHeight = h;
            float scaleWidth = ((float) newWidth) / width;
//			float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            bitmap.recycle();
            System.gc();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resizedBitmap.compress(CompressFormat.JPEG, 100, baos);
            if (baos.toByteArray().length / 1024 > 20) {
                baos.reset();
                resizedBitmap.compress(CompressFormat.JPEG, 50, baos);
            }
            Log.e(tag, baos.toByteArray().length + "");
            try {
                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                if (isBm == null) {
                    return null;
                }
                Bitmap bitmapn = BitmapFactory.decodeStream(isBm, null, null);
                resizedBitmap.recycle();
                System.gc();
                return bitmapn;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int w) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            int newWidth = w;
            float scale = ((float) newWidth) / width;
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return resizedBitmap;
        } else {
            return null;
        }
    }

    public static Bitmap resizeBitmap(float value, Bitmap bitmap) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Log.e(tag, "width" + width + "height" + height);
            float scale = (float) value / width;
            float scale2 = (float) value / height;
            if (scale2 < scale) {
                scale = scale2;
            }
            Matrix matrix = new Matrix();
            if (scale > 1) {
                return bitmap;
            } else {
                matrix.postScale(scale, scale);
            }
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
//			LogUtils.e("getHeight" + resizedBitmap.getWidth() + "getHeight" + resizedBitmap.getHeight());
            bitmap.recycle();
            System.gc();
            return resizedBitmap;
        } else {
            return null;
        }
    }

    public static Bitmap resizeBitmaps(Bitmap bitmap, int w) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int newWidth = w;
            float scale = ((float) newWidth) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            bitmap.recycle();
            System.gc();
            return resizedBitmap;
        } else {
            return null;
        }
    }


    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
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


    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }

}

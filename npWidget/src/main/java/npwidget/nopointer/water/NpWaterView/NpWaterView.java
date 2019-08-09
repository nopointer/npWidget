package npwidget.nopointer.water.NpWaterView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;

import npwidget.nopointer.base.BaseView;

/**
 * 水波纹图，只适用于圆形或者方形的view,如果是其他view的话，可以参考本view
 */
public class NpWaterView extends BaseView {

    public NpWaterView(Context context) {
        super(context);
    }

    public NpWaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpWaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Handler handler = new Handler();

    /**
     * 设置参数
     */
    private NpWaterBean npWaterBean;

    public NpWaterBean getNpWaterBean() {
        return npWaterBean;
    }

    public void setNpWaterBean(NpWaterBean npWaterBean) {
        this.npWaterBean = npWaterBean;
        handler.post(runnable);
    }


    /**
     * 可以绘制的边RectF
     */
    private RectF viewRectF = new RectF();


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }


    @Override
    public void invalidate() {
        draw();
        super.invalidate();
    }

    private void draw() {
        if (canDraw() && npWaterBean != null) {
            clearBitmap();
            drawPaths();
        }
    }

    private void drawPaths() {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);


        Bitmap pathBitmap = Bitmap.createBitmap(this.bitmap);
        Canvas canvas = new Canvas(pathBitmap);

        paint.setColor(Color.RED);
        canvas.drawPath(path1, paint);

        Canvas canvas1 = new Canvas(pathBitmap);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas1.drawCircle(viewRectF.centerX(), viewRectF.centerY(), viewRectF.width() / 2, paint);

        this.canvas.drawBitmap(pathBitmap, new Matrix(), null);
    }

    private boolean isRuning = true;

    Path path1 = new Path();


    private int xTime = 5;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            path1.reset();
            path1.moveTo(30 + 8, viewRectF.bottom - 46);
            path1.lineTo(30 + 8, viewRectF.bottom - 120);

            for (float i = 30 + 8; i <= viewRectF.width() - 30 - 8; i += 2) {
                float x = i + xTime;
                path1.lineTo(i, getY(x, 0.8f, 25f, viewRectF.bottom - 200));
            }
            path1.lineTo(viewRectF.width() - 30 - 8, viewRectF.bottom - 46);
            xTime += 20;
            invalidate();
            if (isRuning)
                postDelayed(this, 50);
        }
    };

    /**
     * 获取Y轴的数据
     */
    public static float getY(float x, float xUnit, float yUnit, float yAddHeight) {
        return (float) (yUnit * Math.sin(x * xUnit * (Math.PI / 180)) + yAddHeight);
    }


}

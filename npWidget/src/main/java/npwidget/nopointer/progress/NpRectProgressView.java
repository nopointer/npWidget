package npwidget.nopointer.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import npwidget.nopointer.base.BaseView;

/**
 * 常规的条形进度条，支持背景色和进度色的设置
 */
public class NpRectProgressView extends BaseView {

    public NpRectProgressView(Context context) {
        super(context);
    }

    public NpRectProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpRectProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 可以绘制的边RectF
     */
    private RectF viewRectF = new RectF();

    private float mProgress = 0.5f;
    private int bgColor = 0xFFFFFF;
    private int progressColor = 0xFFFFFF;
    /**
     * 是否使用圆角模式
     */
    private boolean useRoundMode = false;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }


    public void setUseRoundMode(boolean useRoundMode) {
        this.useRoundMode = useRoundMode;
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(float progress) {
        this.mProgress = progress;
        if (mProgress <= 0) {
            mProgress = 0;
        }
        if (mProgress >= 1) {
            mProgress = 1;
        }
        invalidate();
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    @Override
    public void invalidate() {
        draw();
        super.invalidate();
    }

    private void draw() {
        if (canDraw()) {
            clearBitmap();
            drawProgress();
        }
    }


    private void drawProgress() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);


//        //配置背景参数
        paint.setColor(bgColor);
        canvas.drawRect(viewRectF, paint);
//
        //计算实际进度
        RectF rectF = new RectF(viewRectF);
        rectF.right = viewRectF.width() * mProgress + rectF.left;
        paint.setColor(progressColor);
        canvas.drawRect(rectF, paint);


        if (useRoundMode) {

            Bitmap bitmapTop = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas topCan = new Canvas(bitmapTop);

            Paint paintTop = new Paint();
            paintTop.setStyle(Paint.Style.FILL);

            topCan.drawRoundRect(viewRectF, viewRectF.height() / 2, viewRectF.height() / 2, paintTop);

            paintTop.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));


            canvas.drawBitmap(bitmapTop, new Matrix(), paintTop);
        }


    }

}

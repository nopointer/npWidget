package npwidget.nopointer.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.log.ViewLog;

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

    private float mProgress = 0;
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

        paint.setColor(bgColor);
        if (useRoundMode) {
            canvas.drawRoundRect(viewRectF, viewRectF.height() / 2, viewRectF.height() / 2, paint);
        } else {
            canvas.drawRect(viewRectF, paint);
        }


        RectF rectF = new RectF(viewRectF);
        rectF.right = viewRectF.width() * mProgress + rectF.left;

        ViewLog.e("rectF.right" + rectF.right);

        ViewLog.e("viewRectF.left" + viewRectF.left);
        ViewLog.e("rectF.left" + rectF.left);

//        canvas.draw
        paint.setColor(progressColor);
        if (useRoundMode) {

            //前面的小圆弧要特殊处理一下
            if (rectF.width() < viewRectF.height() / 2) {
                RectF tmpRect = new RectF(rectF);
                tmpRect.right = tmpRect.left + viewRectF.height();
                double sinC = ((tmpRect.width() / 2) - rectF.width()) / (tmpRect.width() / 2);

                ViewLog.e("sinC:" + sinC);


                double tempValue = Math.asin(sinC) * 57.3;

                ViewLog.e("角度:" + tempValue);

//                ViewLog.e("Math.sin(30):" + Math.sin(30 * (Math.PI / 180)));

//                ViewLog.e("Math.asin(30):" + Math.asin(0.49999999999999994) * 57.3);
//
//
//                ViewLog.e("aCosC:" + Math.asin(sinC));
//                ViewLog.e("tempValue:" + tempValue);


                float startAngle = (float) tempValue;
                float sweepAngle = 180 - (startAngle * 2);


                ViewLog.e("startAngle:" + startAngle);
                ViewLog.e("sweepAngle:" + sweepAngle);

                canvas.drawArc(tmpRect, (float) -(tempValue) - 90, -(float) (180 - tempValue * 2), false, paint);
            } else {
                canvas.drawRoundRect(rectF, viewRectF.height() / 2, viewRectF.height() / 2, paint);
            }
        } else {
            canvas.drawRect(rectF, paint);
        }


    }

}

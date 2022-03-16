package npwidget.nopointer.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;


import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.log.NpViewLog;
import npwidget.nopointer.utils.SizeUtils;

/**
 * 浮动的进度条
 */
public class NpFloatProgressView extends BaseView {

    public NpFloatProgressView(Context context) {
        super(context);
        init(context);
    }

    public NpFloatProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NpFloatProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        width = SizeUtils.dp2px(context, 32);
        raduis = SizeUtils.dp2px(context, 3);
        textSize = SizeUtils.sp2px(context, 9);
    }

    /**
     * 可以绘制的边RectF
     */
    private RectF viewRectF = new RectF();

    private float mProgress = 0;
    private int bgColor = 0xFFFFFF;
    private int progressColor = 0xFFFFFF;

    private float width = 100;
    private float raduis = 10;
    private float textSize = 10;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF = new RectF(getPaddingLeft() + width / 2, getPaddingTop(), getMeasuredWidth() - getPaddingRight() - width / 2, getMeasuredHeight() - getPaddingBottom());
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }


    public void setProgress(float mProgress) {
        if (mProgress <= 0) {
            mProgress = 0;
        }
        if (mProgress >= 1) {
            mProgress = 1;
        }
        this.mProgress = mProgress;
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
            drawBg();
            drawProgress();
        } else {
            NpViewLog.log("不能绘制");
        }
    }


    private void drawBg() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(bgColor);
        RectF rectF = new RectF(viewRectF);
        rectF.top = rectF.bottom - viewRectF.height() / 6;
        canvas.drawRoundRect(rectF, rectF.height() / 2, rectF.height() / 2, paint);
    }

    private void drawProgress() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(progressColor);
        RectF rectF = new RectF(viewRectF);
        rectF.top = rectF.bottom - viewRectF.height() / 6;
        rectF.right = rectF.left + mProgress * viewRectF.width();
        canvas.drawRoundRect(rectF, rectF.height() / 2, rectF.height() / 2, paint);

        float centerX = rectF.right;
        RectF floatRectF = new RectF(centerX - width / 2, viewRectF.top, centerX + width / 2, 0);
        floatRectF.bottom = floatRectF.top + viewRectF.height() * 0.5f;

        canvas.drawRoundRect(floatRectF, raduis, raduis, paint);


        Path path = new Path();

        path.moveTo(centerX, floatRectF.bottom + floatRectF.height() * (1.0f / 2.2f));
        path.lineTo(centerX - width / 5, floatRectF.bottom);
        path.lineTo(centerX + width / 5, floatRectF.bottom);

        canvas.drawPath(path, paint);

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 转载请注明出处：http://blog.csdn.net/hursing
        float baseline = (floatRectF.bottom + floatRectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paint.setTextAlign(Paint.Align.CENTER);
        String progressText = String.format("%d%%", Float.valueOf(mProgress * 100).intValue());
        paint.setColor(Color.BLACK);
        paint.setTextSize(textSize);
        canvas.drawText(progressText, floatRectF.centerX(), baseline, paint);

    }

}

package npwidget.nopointer.section.range;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;

import npwidget.nopointer.base.BaseView;

/**
 * 理想区间范围图
 */
public class NpIdealRangeIntervalView extends BaseView {
    public NpIdealRangeIntervalView(Context context) {
        super(context);
        init(context);
    }

    public NpIdealRangeIntervalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NpIdealRangeIntervalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    void init(Context context) {
        paint = new Paint();
    }

    /**
     * 可以绘制的边RectF
     */
    private RectF viewRectF = new RectF();

    private float progress = 0.5f;//实际进度
    private int progressColor = 0xFF00FF00;
    private int progressRadius = 30;
    private float rangeProgressMin = 0.35f;//最小范围
    private float rangeProgressMax = 0.45f;//最大范围

    private int rangeProgressMinColor = 0xFF000000;
    private int rangeProgressMaxColor = 0xFF000000;

    private int rangeProgressColor = 0x60FFFFFF;
    private int rangeProgressWidth = 5;

    private boolean drawBgLayer = false;//绘制背景层，默认不绘制
    private int bgRadius = 20;//背景圆角
    private int bgColor = 0xFF000000;//背景颜色

    private Paint paint = null;

    private int topBottomMargin = 10;//上下边距

    public void setProgress(float progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > 1) {
            progress = 1;
        }
        this.progress = progress;
    }

    public void setRangeProgressMin(float rangeProgressMin) {
        if (rangeProgressMin < 0) {
            rangeProgressMin = 0;
        }
        this.rangeProgressMin = rangeProgressMin;
    }

    public void setRangeProgressMax(float rangeProgressMax) {
        if (rangeProgressMax > 1) {
            rangeProgressMax = 1;
        }
        this.rangeProgressMax = rangeProgressMax;
    }

    public void setDrawBgLayer(boolean drawBgLayer) {
        this.drawBgLayer = drawBgLayer;
    }

    public void setBgRadius(int bgRadius) {
        this.bgRadius = bgRadius;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setProgressRadius(int progressRadius) {
        this.progressRadius = progressRadius;
    }

    public void setRangeProgressMinColor(int rangeProgressMinColor) {
        this.rangeProgressMinColor = rangeProgressMinColor;
    }

    public void setRangeProgressMaxColor(int rangeProgressMaxColor) {
        this.rangeProgressMaxColor = rangeProgressMaxColor;
    }

    public void setRangeProgressColor(int rangeProgressColor) {
        this.rangeProgressColor = rangeProgressColor;
    }

    public void setRangeProgressWidth(int rangeProgressWidth) {
        this.rangeProgressWidth = rangeProgressWidth;
    }

    public void setTopBottomMargin(int topBottomMargin) {
        this.topBottomMargin = topBottomMargin;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() == 0 || getMeasuredHeight() == 0) return;
        viewRectF = new RectF(getPaddingLeft(), getPaddingTop() + topBottomMargin, getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom() - topBottomMargin);
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
        if (canDraw()) {
            clearBitmap();
            drawBgLayer();
            drawDataRect();
            drawMinMaxRangeRect();
        }
    }

    private void drawBgLayer() {
        if (drawBgLayer) {
            paint.setColor(bgColor);
            RectF rectF = new RectF(viewRectF);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rectF, bgRadius, bgRadius, paint);
        }
    }

    /**
     * 绘制数据区域
     */
    private void drawDataRect() {
        RectF rectF = new RectF(viewRectF);
        rectF.right = viewRectF.width() * progress;
        paint.setColor(progressColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, progressRadius, progressRadius, paint);
    }


    /**
     * 绘制范围区域
     */
    private void drawMinMaxRangeRect() {
        RectF rectF = new RectF(viewRectF);
        rectF.left = rectF.left + viewRectF.width() * rangeProgressMin;
        rectF.top = viewRectF.top - topBottomMargin;
        rectF.right = rectF.left + viewRectF.width() * (rangeProgressMax - rangeProgressMin);
        rectF.bottom = viewRectF.bottom + topBottomMargin;
        paint.setColor(rangeProgressColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(rectF, paint);

        Paint paint1 = new Paint(paint);
        paint1.setStrokeWidth(rangeProgressWidth);

        paint1.setColor(rangeProgressMinColor);
        canvas.drawLine(rectF.left, rectF.top, rectF.left, rectF.bottom, paint1);

        paint1.setColor(rangeProgressMaxColor);
        canvas.drawLine(rectF.right, rectF.top, rectF.right, rectF.bottom, paint1);

    }

}

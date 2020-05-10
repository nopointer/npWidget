package npwidget.nopointer.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.base.ValueFormatCallback;
import npwidget.nopointer.log.ViewLog;

/**
 * 常规的条形进度条，支持背景色和进度色的设置
 * 底部就是文字
 */
public class NpRectWithValueProgressView extends BaseView {

    public NpRectWithValueProgressView(Context context) {
        super(context);
    }

    public NpRectWithValueProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpRectWithValueProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 可以绘制的边RectF
     */
    private RectF viewRectF = new RectF();

    /**
     * 背景颜色
     */
    private int bgColor = 0xFF00FF00;
    /**
     * 进度颜色
     */
    private int progressColor = 0xFFFFFF;
    /**
     * 是否使用圆角模式
     */
    private boolean useRoundMode = true;

    /**
     * 文字大小
     */
    private float textSize = 40.0F;
    /**
     * 文字距离颜色块的距离
     */
    private float valueMarginBar = 10;

    /**
     * 颜色条的高度
     */
    private float barHeight = 40;
    /**
     * 最大值
     */
    private float maxValue = 100.0F;
    /**
     * 最小值
     */
    private float minValue = 0.0F;

    /**
     * 边框的颜色
     */
    private int borderColor = 0xFFFF0000;

    /**
     * 文字颜色
     */
    private int valueColor = 0xFF000000;

    /**
     * 数据段数（平分这段数据的个数）
     */
    private int segmentCount = 2;

    /**
     * 是否显示左右两边开始和结束值
     */
    private boolean isShowStartEndValue = true;

    /**
     * 边框的线条粗度
     */
    private float borderWidth = 4;

    /**
     * 开始值
     */
    private float startValue = 0;
    /**
     * 结束值
     */
    private float endValue = 35;


    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setValueMarginBar(float valueMarginBar) {
        this.valueMarginBar = valueMarginBar;
    }

    public void setBarHeight(float barHeight) {
        this.barHeight = barHeight;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setValueColor(int valueColor) {
        this.valueColor = valueColor;
    }

    public void setSegmentCount(int segmentCount) {
        this.segmentCount = segmentCount;
    }

    public void setShowStartEndValue(boolean showStartEndValue) {
        isShowStartEndValue = showStartEndValue;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setStartValue(float startValue) {
        this.startValue = startValue;
    }

    public void setEndValue(float endValue) {
        this.endValue = endValue;
    }

    private ValueFormatCallback valueFormatCallback = null;


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
            drawBgBorder();
            drawProgress();
            drawValues();
        }
    }


    /**
     * 绘制背景的框
     */
    private void drawBgBorder() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setColor(borderColor);
        RectF rectF = new RectF(viewRectF);
        rectF.top = viewRectF.top + borderWidth / 2;
        rectF.bottom = rectF.top + barHeight - borderWidth / 2;
        rectF.left = viewRectF.left + borderWidth / 2;
        rectF.right = viewRectF.right - borderWidth / 2;
        rectF.bottom = rectF.top + barHeight;
        if (useRoundMode) {
            canvas.drawRoundRect(rectF, rectF.height() / 2, rectF.height() / 2, paint);
        } else {
            canvas.drawRect(rectF, paint);
        }
        rectF = new RectF(viewRectF);
        rectF.top = viewRectF.top + borderWidth;
        rectF.bottom = rectF.top + barHeight - borderWidth;
        rectF.left = viewRectF.left + borderWidth;
        rectF.right = viewRectF.right - borderWidth;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bgColor);
        if (useRoundMode) {
            canvas.drawRoundRect(rectF, rectF.height() / 2, rectF.height() / 2, paint);
        } else {
            canvas.drawRect(rectF, paint);
        }
    }


    /**
     * 绘制界值
     */
    private void drawValues() {
        //颜色块的递增宽度
        float rectWidthAdd = viewRectF.width() / segmentCount;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setColor(valueColor);
        //递增值
        float valueAddSpace = (maxValue - minValue) / segmentCount;
        RectF rectF = new RectF(viewRectF);
        rectF.top = viewRectF.top + barHeight;
        rectF.bottom = viewRectF.bottom - borderWidth / 2;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2.0f - fontMetrics.bottom;
        float valuePositionY = rectF.centerY() + distance + valueMarginBar;
        boolean isDrawThisValue = true;//是否要绘制这个数据（部分需求可能不会绘制左右起始的值）
        for (int i = 0; i <= segmentCount; i++) {
            //数据值
            float valueF = minValue + i * valueAddSpace;
            //文字的横坐标
            float textPositionX = i * rectWidthAdd + rectF.left;
            String valueText;
            if (valueFormatCallback != null) {
                valueText = valueFormatCallback.valueFormat(valueF);
            } else {
                valueText = String.format("%d", Float.valueOf(valueF).intValue());
            }
            if (i == segmentCount) {
                paint.setTextAlign(Paint.Align.RIGHT);
//                textPositionX -= textSize * 0.4F;
                isDrawThisValue = isShowStartEndValue;
            } else if (i == 0) {
                paint.setTextAlign(Paint.Align.LEFT);
//                textPositionX += textSize * 0.4F;
                isDrawThisValue = isShowStartEndValue;
            } else {
                paint.setTextAlign(Paint.Align.CENTER);
                isDrawThisValue = true;
            }
            if (isDrawThisValue) {
                canvas.drawText(valueText, textPositionX, valuePositionY, paint);
            }
        }

    }


    /**
     * 绘制进度
     */
    private void drawProgress() {

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        float startV = startValue;
        float endV = endValue;

        if (startV <= minValue) {
            startV = minValue;
        }
        if (endV >= maxValue) {
            endV = maxValue;
        }
        RectF rectF = new RectF(viewRectF);
        rectF.top = viewRectF.top + borderWidth;
        rectF.left = viewRectF.left + borderWidth;
        rectF.right = viewRectF.right - borderWidth;
        rectF.bottom = rectF.top + barHeight - borderWidth;
        float startProgress = (startV - minValue) / (maxValue - minValue);
        float endProgress = (endV - minValue) / (maxValue - minValue);

        ViewLog.e(startProgress + "///" + endProgress);
        float rectWidth = rectF.width();//宽度需要提前取出来 不然改了左右位置 ，宽度也会一起变
        float startLeft = rectF.left;
        rectF.left = startLeft + startProgress * rectWidth;
        rectF.right = startLeft + endProgress * rectWidth;
        paint.setColor(progressColor);
        if (useRoundMode) {
            canvas.drawRoundRect(rectF, rectF.height() / 2, rectF.height() / 2, paint);
        } else {
            canvas.drawRect(rectF, paint);
        }
    }

    public void setValueFormatCallback(ValueFormatCallback valueFormatCallback) {
        this.valueFormatCallback = valueFormatCallback;
    }
}

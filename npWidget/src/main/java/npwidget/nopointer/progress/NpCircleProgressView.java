package npwidget.nopointer.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;


import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.log.ViewLog;
import npwidget.nopointer.utils.SizeUtils;


/**
 * 圆形进度条（很基础的view）
 */
public class NpCircleProgressView extends BaseView {

    /**
     * 可以绘制的RectF 边界，是一个正方形 会根据小边长适配大小
     */
    private RectF viewRectF = new RectF();
    private float mProgress = 0f;

    /**
     * 开始的角度，默认-90 即12点钟方向
     */
    private float startAngle = -90;

    /**
     * 背景颜色
     */
    private int circleProgressBgColor = 0xFF00FFFF;

    /**
     * 进度的颜色
     */
    private int circleProgressColor = 0xFF00FF00;
    /**
     * 进度线的宽度
     */
    private float circleWidth = 2;


    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public void setCircleProgressBgColor(int circleProgressBgColor) {
        this.circleProgressBgColor = circleProgressBgColor;
    }

    public void setCircleProgressColor(int circleProgressColor) {
        this.circleProgressColor = circleProgressColor;
    }

    public void setCircleWidth(float circleWidth) {
        this.circleWidth = circleWidth;
    }

    public NpCircleProgressView(Context context) {
        super(context);
        init(context);
    }

    public NpCircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NpCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        circleWidth = SizeUtils.dp2px(context, 6);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewRectF = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());

        reSizeRect(viewRectF);

        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }

    /**
     * 矫正绘制范围的区域 成一个正方形
     *
     * @param rectF
     */
    private void reSizeRect(RectF rectF) {
        if (rectF == null) return;
        //宽大于高，取高度
        float wh = 0;
        if (rectF.width() > rectF.height()) {
            wh = rectF.height();
        } else {
            //宽小于搞，取宽度
            wh = rectF.width();
        }
        wh /= 2;
        viewRectF = new RectF(rectF.centerX() - wh, rectF.centerY() - wh, rectF.centerX() + wh, rectF.centerY() + wh);
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
        } else {
            ViewLog.e("不能绘制");
        }
    }


    private void drawProgress() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        float circleRadius = viewRectF.width() / 2;
        circleRadius = circleRadius - circleWidth;

        //绘制背景圆环
        paint.setColor(circleProgressBgColor);
        canvas.drawCircle(viewRectF.centerX(), viewRectF.centerY(), circleRadius, paint);

        RectF rectF = new RectF(viewRectF.centerX() - circleRadius, viewRectF.centerY() - circleRadius, viewRectF.centerX() + circleRadius, viewRectF.centerY() + circleRadius);

        //绘制辅助位置的矩形，调试用
//        canvas.drawRect(rectF, paint);

        //绘制进度圆环
        paint.setColor(circleProgressColor);
        canvas.drawArc(rectF, startAngle, 360 * mProgress, false, paint);

    }

    /**
     * 更新进度
     *
     * @param progress
     */
    public void updateProgress(float progress) {
        if (progress >= 1) {
            progress = 1;
        } else if (progress < 0) {
            progress = 0;
        }
        this.mProgress = progress;
        invalidate();
    }


}

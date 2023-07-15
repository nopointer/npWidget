package npwidget.nopointer.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import npwidget.nopointer.R;
import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.log.NpViewLog;
import npwidget.nopointer.utils.SizeUtils;


/**
 * 圆形进度条（很基础的view）
 */
public class NpCircleProgressView extends BaseView {

    /**
     * 可以绘制的RectF 边界，是一个正方形 会根据小边长适配大小
     */
    private RectF viewRectF = new RectF();
    //当前进度
    private float mProgress = 0.0f;

    //开始的角度，默认-90 即12点钟方向
    private float startAngle = -90;

    //进度圆环的 背景颜色 默认没有颜色
    private int circleProgressBgColor = 0x00000000;

    //进度的颜色 进度颜色 默认没有颜色
    private int circleProgressColor = 0x00000000;

    //进度圆环的粗细
    private float circleWidth = 2;

    private float dotR = 2;
    private int dotColor = 0x00000000;

    //是否显示游标
    private boolean showCursor;

    private float cursorR = 30;

    //进度游标的颜色,默认没有颜色
    private int cursorColor = 0x00000000;

    //阴影的范围
    private float cursorShadowR = 0;

    //阴影的颜色
    private int cursorShadowColor = 0xFFFFFFFF;

    //是否显示游标的阴影 默认不显示
    private boolean showCursorShadow = false;


    public boolean isShowCursor() {
        return showCursor;
    }

    public void setShowCursor(boolean showCursor) {
        this.showCursor = showCursor;
    }

    public float getCursorR() {
        return cursorR;
    }

    public void setCursorR(float cursorR) {
        this.cursorR = cursorR;
    }

    public int getCursorColor() {
        return cursorColor;
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
    }

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


    public float getCursorShadowR() {
        return cursorShadowR;
    }

    public void setCursorShadowR(float cursorShadowR) {
        this.cursorShadowR = cursorShadowR;
    }

    public int getCursorShadowColor() {
        return cursorShadowColor;
    }

    public void setCursorShadowColor(int cursorShadowColor) {
        this.cursorShadowColor = cursorShadowColor;
    }

    public boolean isShowCursorShadow() {
        return showCursorShadow;
    }

    public void setShowCursorShadow(boolean showCursorShadow) {
        this.showCursorShadow = showCursorShadow;
    }

    public NpCircleProgressView(Context context) {
        super(context);
        init(context, null);
    }

    public NpCircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NpCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public float getDotR() {
        return dotR;
    }

    public void setDotR(float dotR) {
        this.dotR = dotR;
    }

    public int getDotColor() {
        return dotColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
    }

    private void init(Context context, AttributeSet attrs) {
        circleWidth = SizeUtils.dp2px(context, 6);
        dotR = SizeUtils.dp2px(context, 0);
        cursorR = SizeUtils.dp2px(context, 6);
        cursorShadowR = SizeUtils.dp2px(context, 0);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NpCircleProgressView);


            startAngle = typedArray.getDimension(R.styleable.NpCircleProgressView_startAngle, -90);
            mProgress = typedArray.getFloat(R.styleable.NpCircleProgressView_progress, mProgress);
            circleProgressBgColor = typedArray.getColor(R.styleable.NpCircleProgressView_circleProgressBgColor, circleProgressBgColor);
            circleProgressColor = typedArray.getColor(R.styleable.NpCircleProgressView_circleProgressColor, circleProgressColor);

            circleWidth = typedArray.getDimension(R.styleable.NpCircleProgressView_circleWidth, circleWidth);
            dotR = typedArray.getDimension(R.styleable.NpCircleProgressView_dotR, dotR);
            dotColor = typedArray.getColor(R.styleable.NpCircleProgressView_dotColor, dotColor);
            showCursor = typedArray.getBoolean(R.styleable.NpCircleProgressView_showCursor, showCursor);
            cursorR = typedArray.getDimension(R.styleable.NpCircleProgressView_cursorR, cursorR);
            cursorColor = typedArray.getColor(R.styleable.NpCircleProgressView_cursorColor, cursorColor);
            cursorShadowR = typedArray.getDimension(R.styleable.NpCircleProgressView_cursorShadowR, cursorShadowR);
            cursorShadowColor = typedArray.getColor(R.styleable.NpCircleProgressView_cursorShadowColor, cursorShadowColor);
            showCursorShadow = typedArray.getBoolean(R.styleable.NpCircleProgressView_showCursorShadow, showCursorShadow);

            typedArray.recycle();
            NpViewLog.log("attrs!=null:" + (attrs != null));
        }

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

            canvas.save();

            canvas.rotate(360.0f * mProgress, viewRectF.centerX(), viewRectF.centerY());

            drawCursor();

            drawDot();

            canvas.restore();
        } else {
            NpViewLog.log("不能绘制");
        }
    }


    private void drawCursor() {
        if (!isShowCursor()) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(cursorColor);
        paint.setStyle(Paint.Style.FILL);

        if (isShowCursorShadow()) {
            paint.setShadowLayer(cursorShadowR, 0, 0, cursorShadowColor);
        }
        canvas.drawCircle(viewRectF.centerX(), circleWidth + viewRectF.top, cursorR, paint);
    }

    private void drawDot() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(dotColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(viewRectF.centerX(), circleWidth + viewRectF.top, dotR, paint);
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

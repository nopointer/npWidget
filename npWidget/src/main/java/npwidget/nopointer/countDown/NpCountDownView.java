package npwidget.nopointer.countDown;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;


import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.log.ViewLog;
import npwidget.nopointer.utils.SizeUtils;


/**
 * 倒计时 view
 */
public class NpCountDownView extends BaseView {

    /**
     * 可以绘制的RectF 边界，是一个正方形 会根据小边长适配大小
     */
    private RectF viewRectF = null;
    private float mProgress = 0f;

    //表盘的指针长度
    private float dialLength = 0;
    //表盘的指针宽度
    private float dialWidth = 0;
    //表盘的间距倍数，基数是dialWidth
    private float dialSpaceScale = 2f;
    //外面的样式的外边距
    private float outSideMargin = 10;
    //内部距离外层的边距
    private float innerMargin = 0;
    //外部的颜色
    private int outSideColor = 0xFF0000FF;

    //倒计时的秒数
    private int second = 5;

    private OutSideStyle outSideStyle = OutSideStyle.DIAL;

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

    private ValueAnimator startAnimator = null;
    private ValueAnimator endAnimator = null;
    private boolean isStarting = false;

    private float progressBarRadius = 0;

    /**
     * 进度小球的外层颜色
     */
    private int progressBarOutSideColor = 0xFFFF0000;
    /**
     * 进度小球的圆环颜色
     */
    private int progressBarCircleColor = 0xFFFFFFFF;
    /**
     * 进度小球的背景颜色（即圆环以内层颜色）
     */
    private int progressBarColor = 0xFFFF0000;


    private float unitDp = 0;
    private NpCountDownListener npCountDownListener;

    public void setNpCountDownListener(NpCountDownListener npCountDownListener) {
        this.npCountDownListener = npCountDownListener;
    }

    public void setDialLength(float dialLength) {
        this.dialLength = dialLength;
    }

    public void setDialWidth(float dialWidth) {
        this.dialWidth = dialWidth;
    }

    public void setDialSpaceScale(float dialSpaceScale) {
        this.dialSpaceScale = dialSpaceScale;
    }

    public void setOutSideMargin(float outSideMargin) {
        this.outSideMargin = outSideMargin;
    }

    public void setInnerMargin(float innerMargin) {
        this.innerMargin = innerMargin;
    }

    public void setOutSideStyle(OutSideStyle outSideStyle) {
        this.outSideStyle = outSideStyle;
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

    public void setOutSideColor(int outSideColor) {
        this.outSideColor = outSideColor;
    }

    public void setCircleWidth(float circleWidth) {
        this.circleWidth = circleWidth;
    }

    public void setProgressBarOutSideColor(int progressBarOutSideColor) {
        this.progressBarOutSideColor = progressBarOutSideColor;
    }

    public void setProgressBarCircleColor(int progressBarCircleColor) {
        this.progressBarCircleColor = progressBarCircleColor;
    }

    public void setProgressBarColor(int progressBarColor) {
        this.progressBarColor = progressBarColor;
    }

    public NpCountDownView(Context context) {
        super(context);
        init(context);
    }

    public NpCountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NpCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        dialLength = SizeUtils.dp2px(context, 8);
        dialWidth = SizeUtils.dp2px(context, 1);

        innerMargin = SizeUtils.dp2px(context, 10);

        progressBarRadius = SizeUtils.dp2px(context, 6);
        circleWidth = SizeUtils.dp2px(context, 2);

        unitDp = SizeUtils.dp2px(context, 1);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getWidth()<0||getHeight()<0)return;


        ViewLog.e("viewRectF,fuck,onMeasure");

        RectF viewRectF = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());

        ViewLog.e("viewRectF====>" + viewRectF.toString());
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
            drawOutSide();
//            drawBg();
            drawProgress();
        } else {
            ViewLog.e("不能绘制");
        }
    }


    private void drawBg() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(0xFF00000);
        RectF rectF = new RectF(viewRectF);
        canvas.drawRoundRect(rectF, rectF.height() / 2, rectF.height() / 2, paint);

        //辅助正方形框线
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(rectF, paint);
    }

    /**
     * 绘制外层效果
     */
    private void drawOutSide() {
        //无外层样式
        if (outSideStyle == OutSideStyle.NONE) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(outSideColor);

        if (outSideStyle == OutSideStyle.DIAL) {
            paint.setStrokeWidth(dialWidth);
            if (dialSpaceScale == 0) {
                dialSpaceScale = 1;
            }
            //表盘样式
            //绘制虚线的圆
            float count = viewRectF.width() / (dialWidth * dialSpaceScale);
            for (int i = 0; i < count; i++) {
                canvas.save();
                canvas.rotate((360.f / count) * i, viewRectF.centerX(), viewRectF.centerY());
                canvas.drawLine(viewRectF.left + outSideMargin, viewRectF.centerY(), viewRectF.left + outSideMargin + dialLength, viewRectF.centerY(), paint);
                canvas.restore();
            }
        } else {
            //圆环样式
        }

    }

    private void drawProgress() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleWidth);

        float circleRadius = viewRectF.width() / 2;
        circleRadius = circleRadius - outSideMargin - dialLength - innerMargin;

        //绘制背景圆环
        paint.setColor(circleProgressBgColor);
        canvas.drawCircle(viewRectF.centerX(), viewRectF.centerY(), circleRadius, paint);


        RectF rectF = new RectF(viewRectF.centerX() - circleRadius, viewRectF.centerY() - circleRadius, viewRectF.centerX() + circleRadius, viewRectF.centerY() + circleRadius);
//        canvas.drawRect(rectF, paint);

        //绘制进度圆环
        paint.setColor(circleProgressColor);
        canvas.drawArc(rectF, startAngle, 360 * mProgress, false, paint);


//        RadialGradient radialGradient = new RadialGradient(viewRectF.centerX(), viewRectF.centerY(), 20, 0xff000000, 0xffffffff, Shader.TileMode.MIRROR);

        paint.setStyle(Paint.Style.FILL);
//        paint.setShader(radialGradient);
//        canvas.drawCircle(viewRectF.centerX(), viewRectF.centerY(), 20, paint);


        //小球的中心位置点
        float progressBarCenterX = (float) (viewRectF.centerX() + getXDistance(circleRadius, 360 * mProgress));
        float progressBarCenterY = (float) (viewRectF.centerY() - getYDistance(circleRadius, 360 * mProgress));

        paint.setColor(progressBarOutSideColor);
        canvas.drawCircle(progressBarCenterX, progressBarCenterY, progressBarRadius + unitDp, paint);
        paint.setColor(progressBarColor);
        canvas.drawCircle(progressBarCenterX, progressBarCenterY, progressBarRadius, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(progressBarCircleColor);
        paint.setStrokeWidth(unitDp);
        canvas.drawCircle(progressBarCenterX, progressBarCenterY, progressBarRadius - unitDp + 2, paint);
    }


    /**
     * 外层样式
     */
    public enum OutSideStyle {
        /**
         * 无样式
         */
        NONE,
        /**
         * 线
         */
        CIRCLE,
        /**
         * 表盘样式
         */
        DIAL
    }

    private double getXDistance(float length, float angle) {
        double cos = Math.cos(Math.toRadians(angle));
        if (angle <= 180) {
            return Math.sqrt(1 - cos * cos) * length;
        } else {
            return -Math.sqrt(1 - cos * cos) * length;
        }
    }

    private double getYDistance(float length, float angle) {

        double cos = Math.cos(Math.toRadians(angle));
        return cos * length;
//        return Math.sqrt(1 - sin * sin) * length;
//        if (sin > 0) {
//            return -Math.sqrt(1 - sin * sin) * length;
//        } else {
//            return Math.sqrt(1 - sin * sin) * length;
//        }
    }

    /**
     * 开始倒计时
     *
     * @param second
     */
    public void startCountDown(int second) {
        if (endAnimator != null) {
            endAnimator.removeAllUpdateListeners();
            endAnimator.cancel();
        }
        this.second = second;
        isStarting = true;
        startAnimator = ObjectAnimator.ofFloat(0, 1.0f);
        startAnimator.setDuration(second * 1000);
        startAnimator.setInterpolator(new DecelerateInterpolator());
        startAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isStarting) return;
                float animatedValue = (float) animation.getAnimatedValue();
                mProgress = animatedValue;
                invalidate();
            }
        });
        startAnimator.start();
        startAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //倒计时结束的时候，需要通过自定义接口通知UI去处理其他业务逻辑
                if (npCountDownListener != null) {
                    npCountDownListener.onCountdownFinished();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public void stopCountDown() {
        if (startAnimator != null) {
            startAnimator.removeAllUpdateListeners();
            startAnimator.cancel();
        }
        isStarting = false;
        float tmpValue = mProgress;
        endAnimator = ObjectAnimator.ofFloat(tmpValue, 0);
        endAnimator.setDuration((long) (second * tmpValue * 0.2f));
        endAnimator.setInterpolator(new DecelerateInterpolator());
        endAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (isStarting) return;
                float animatedValue = (float) animation.getAnimatedValue();
                mProgress = animatedValue;
                invalidate();
            }
        });
        endAnimator.start();
        endAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //倒计时结束的时候，需要通过自定义接口通知UI去处理其他业务逻辑
                if (npCountDownListener != null) {
                    npCountDownListener.onCountdownStop();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }


    public interface NpCountDownListener {
        void onCountdownFinished();

        void onCountdownStop();
    }

}

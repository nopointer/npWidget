package npwidget.nopointer.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import npwidget.nopointer.log.NpViewLog;

public class BaseView extends View {

    public BaseView(Context context) {
        super(context);
        init(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
//        enableScroll();
    }


    public void enableScroll() {
        Context context = getContext();
        mScroller = new Scroller(context);
        // 初始化final常量，必须在构造中赋初值
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        TOUCH_SLOP = viewConfiguration.getScaledTouchSlop();
        MIN_FLING_VELOCITY = viewConfiguration.getScaledMinimumFlingVelocity();
        MAX_FLING_VELOCITY = viewConfiguration.getScaledMaximumFlingVelocity();
        NpViewLog.log("TOUCH_SLOP = " + TOUCH_SLOP + " , MIN_FLING_VELOCITY = " + MIN_FLING_VELOCITY + " , MAX_FLING_VELOCITY = " + MAX_FLING_VELOCITY);
    }

    /**
     * 滑动器
     */
    protected Scroller mScroller;
    /**
     * 速度跟踪器
     */
    protected VelocityTracker mVelocityTracker;

    /**
     * 滑动阈值
     */
    protected int TOUCH_SLOP;
    /**
     * 惯性滑动最小、最大速度
     */
    protected int MIN_FLING_VELOCITY, MAX_FLING_VELOCITY;

    /**
     * X方向上的偏移量
     */
    protected float moveOffsetX = 0;
    /**
     * x方向上的滑动距离
     */
    protected float scrollOffsetX = 0;
    /**
     * 上次滑动的距离
     */
    protected float lastScrollOffsetX = 0;

    /**
     * 是否在移动
     */
    protected boolean isMoved;

    //画布
    protected Canvas canvas;
    protected Bitmap bitmap;
    protected int canvasBg = 0xFFFFFFFF;

    private boolean debugRect = false;

    //是否允许enableOnMeasure
    private boolean enableOnMeasure = true;

    /**
     * 滑动的左边界
     */
    protected float minScrollX = 0;

    /**
     * 滑动的右边界
     */
    protected float maxScrollX = 0;


    /**
     * 是否允许在滑动的时候出界，停止滑动的时候回滚边界
     */
    protected boolean isAllowSlideOutInMove = false;

    public void setAllowSlideOutInMove(boolean allowSlideOutInMove) {
        isAllowSlideOutInMove = allowSlideOutInMove;
    }

    public boolean isDebugRect() {
        return debugRect;
    }

    public void setDebugRect(boolean debugRect) {
        this.debugRect = debugRect;
    }

    public boolean isEnableOnMeasure() {
        return enableOnMeasure;
    }

    public void setEnableOnMeasure(boolean enableOnMeasure) {
        this.enableOnMeasure = enableOnMeasure;
    }

    //是否可以触摸
    private boolean enableTouch = true;

    public boolean isEnableTouch() {
        return enableTouch;
    }

    public void setEnableTouch(boolean enableTouch) {
        this.enableTouch = enableTouch;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null && bitmap != null) {
            NpViewLog.log("canvas W = " + canvas.getWidth() + ",H = " + bitmap.getHeight());
            canvas.drawBitmap(bitmap, (canvas.getWidth() - bitmap.getWidth()) / 2, 0, null);
        }
    }


    /**
     * 清除画布
     */
    protected void clearBitmap() {
        if (canvas == null) return;
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
    }


    /**
     * 清除画布
     *
     * @param
     */
    protected void clearBitmap(int color) {
        if (canvas == null) return;
        this.canvasBg = color;
        canvas.drawColor(color, PorterDuff.Mode.CLEAR);
    }

    /**
     * 是否是可以绘制
     *
     * @return
     */
    protected boolean canDraw() {
        return canvas != null && bitmap != null;
    }


    /**
     * 绘制调试矩形
     *
     * @param rect
     */
    protected void drawDebugRect(Rect rect) {
        //绘制可是区域的范围，调试用
        if (isDebugRect()) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0x30000000);
            canvas.drawRect(rect, paint);
        }
    }

    /**
     * 回收bitmap
     *
     * @param bitmap
     */
    protected void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }


    @Override
    public void computeScroll() {
        if (mScroller == null) return;
        boolean computeScrollOffset = mScroller.computeScrollOffset();
        int currX = mScroller.getCurrX(), finalX = mScroller.getFinalX();
//        NpViewLog.log("computeScrollOffset = " + computeScrollOffset + " , currX = " + currX + " , finalX = " + finalX + " , minScrollX = " + minScrollX + " , maxScrollX = " + maxScrollX);

        if (computeScrollOffset) {
            if (currX != finalX) {
                scrollOffsetX = lastScrollOffsetX + currX;
                if (scrollOffsetX <= minScrollX) {
//                    NpViewLog.log("左边限制");
                    scrollOffsetX = minScrollX;
                }

                if (scrollOffsetX > maxScrollX) {
//                    NpViewLog.log("右边限制");
                    scrollOffsetX = maxScrollX;
                }

//                NpViewLog.log("实时滑动距离 -> scrollOffsetX = " + scrollOffsetX);
//                scrollTo(scrollOffsetX, 0);
                moveOffsetX = scrollOffsetX;
                invalidate();
            } else {
                if (scrollOffsetX <= minScrollX) {
//                    NpViewLog.log("左边限制");
                    scrollOffsetX = minScrollX;
                }

                if (scrollOffsetX > maxScrollX) {
//                    NpViewLog.log("右边限制");
                    scrollOffsetX = maxScrollX;
                }

                lastScrollOffsetX = scrollOffsetX;
                moveOffsetX = scrollOffsetX;
                invalidate();
            }
        }
//        NpViewLog.log("lastScrollOffsetX = " + lastScrollOffsetX + " , moveOffsetX = " + moveOffsetX);
    }


}

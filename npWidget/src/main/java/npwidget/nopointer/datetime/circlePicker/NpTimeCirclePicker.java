package npwidget.nopointer.datetime.circlePicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import npwidget.nopointer.R;
import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.utils.BitmapUtils;


/**
 * @version V1.0
 * @Author ly
 * @Description
 * @Date 2017/12/6
 * <p>
 * 修改后的view
 */
public class NpTimeCirclePicker extends BaseView {

    private float mStartDegree; //开始按钮的进度
    private float mEndDegree; //结束按钮的进度
    private float mStartBtnAngle;  //开始按钮的旋转角度
    private float mEndBtnAngle; //结束按钮的旋转角度

    private float mStartBtnCurX, mStartBtnCurY; //开始按钮中心的位置
    private float mEndBtnCurX, mEndBtnCurY; //结束按钮中心的位置

    private int mDegreeCycle;

    private int mMinViewSize;//控件的最小尺寸

    //view的中心点
    private float mCenterX, mCenterY;

    private float mWheelRadius;
    private int mMoveFlag;//1,代表开始按钮,2,代表结束按钮
    private NpTimeChangeListener mOnTimeChangeListener;
    private float mLastEventX;
    private float mLastEventY;


    private static final String tag = "CirclePicker";


    //开始结束按钮圆形背景的半径
    private float btnBgCircleRadius = 0;


    private NpTimeBean npTimeBean;

    public NpTimeBean getNpTimeBean() {
        return npTimeBean;
    }

    public void setNpTimeBean(NpTimeBean npTimeBean) {
        this.npTimeBean = npTimeBean;
    }

    /**
     * 可以绘制的边RectF
     */
    private RectF viewRectF = new RectF();


    public NpTimeCirclePicker(Context context) {
        this(context, null);
    }

    public NpTimeCirclePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NpTimeCirclePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //定义属性
        initAttrs(attrs, defStyleAttr);
        //初始化
        initValue();
    }

    private void initValue() {
        mMoveFlag = -1;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        mCenterX = (int) viewRectF.centerX();
        mCenterY = viewRectF.centerY();
        draw();
    }


    //初始化属性
    private void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Circle_Picker, defStyle, 0);

//       角度的最大周期
        mDegreeCycle = typedArray.getInt(R.styleable.Circle_Picker_Degree_Cycle, 720);
//      开始时间的圆盘角度
        mStartDegree = typedArray.getFloat(R.styleable.Circle_Picker_Start_Degree, 0);
//        结束时间的圆盘角度
        mEndDegree = typedArray.getFloat(R.styleable.Circle_Picker_End_Degree, 45);

        if (mStartDegree > mDegreeCycle)
            mStartDegree = mStartDegree % mDegreeCycle;

        if (mEndDegree > mDegreeCycle)
            mEndDegree = mEndDegree % mDegreeCycle;

        typedArray.recycle();
    }


    private void refreshBtnPosition() {
        refreshStartBtnPositon();
        refreshEndBtnPosition();
    }


    /**
     * 刷新开始按钮的位置
     */
    public void refreshStartBtnPositon() {
        //转换为360度
        mStartBtnAngle = mStartDegree % 360;

        Log.e(tag, "开始按钮的角度是多少" + mStartBtnAngle);

        double startCos = Math.cos(Math.toRadians(mStartBtnAngle));
        MakeCurPosition(startCos);
    }

    /**
     * 刷新结束按钮的位置
     */
    public void refreshEndBtnPosition() {
        mEndBtnAngle = mEndDegree % 360;
        double endCos = Math.cos(Math.toRadians(mEndBtnAngle));
        MakeCurPosition2(endCos);
    }


    private void MakeCurPosition(double cos) {
        //根据旋转的角度来确定圆的位置
        //确定x点的坐标
        mStartBtnCurX = calcXLocationInWheel(mStartBtnAngle, cos);
        //确定y点的坐标
        mStartBtnCurY = calcYLocationInWheel(cos);

        Log.e("mStartBtnCurX/Y", mStartBtnCurX + "/" + mStartBtnCurY);
    }

    private void MakeCurPosition2(double cos2) {
        //根据旋转的角度来确定圆的位置
        //确定x点的坐标
        mEndBtnCurX = calcXLocationInWheel(mEndBtnAngle, cos2);
        //确定y点的坐标
        mEndBtnCurY = calcYLocationInWheel(cos2);
    }

    //确定x点的坐标
    private float calcXLocationInWheel(double angle, double cos) {
        float tmpWidth = mMinViewSize / 2 - btnBgCircleRadius;
        if (angle <= 180) {
            return (float) (viewRectF.centerX() + Math.sqrt(1 - cos * cos) * tmpWidth);
        } else {
            return (float) (viewRectF.centerX() - Math.sqrt(1 - cos * cos) * tmpWidth);
        }
    }

    //确定y点的坐标
    private float calcYLocationInWheel(double cos) {
        float tmpHeight = mMinViewSize / 2 - btnBgCircleRadius;
        return (float) (viewRectF.centerY() - cos * tmpHeight);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (getParent() != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isEnableTouch()){
            return false;
        }

        float btnSize = npTimeBean.getStartAndEndBtnSize();

        if (getDistance(event.getX(), event.getY(), mCenterX, mCenterY) > mMinViewSize / 2 + btnSize) {
            return super.onTouchEvent(event);
        }

        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isMoveStartBtn(eventX, eventY)) {
                    mMoveFlag = 1;
                } else if (isMoveEndBtn(eventX, eventY)) {
                    mMoveFlag = 2;
                } else if (isMoveSelectedArea(eventX, eventY)) {
                    mMoveFlag = 3;
                }
                mLastEventX = eventX;
                mLastEventY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:

//                根据两坐标点求直线方程
//                A = Y2 - Y1
//                B = X1 - X2
//                C = X2*Y1 - X1*Y2


                if (mMoveFlag == 1) {//旋转开始按钮
//                  坐标系的直线表达式
//                  直线l1的表达式子:过钟表中心点和开始控件中心点
                    float a1 = mCenterY - mStartBtnCurY;
                    float b1 = mStartBtnCurX - mCenterX;
                    float c1 = mStartBtnCurY * mCenterX - mCenterY * mStartBtnCurX;
                    double d1 = (a1 * eventX + b1 * eventY + c1) / (Math.sqrt(a1 * a1 + b1 * b1));
//                    Log.d("TAG", "d1==" + d1);

//                  直线l2的表达式:过钟表中心点且垂直直线l1
                    float a2 = b1;
                    float b2 = -a1;
                    float c2 = -a2 * mCenterX - b2 * mCenterY;
                    double d2 = (a2 * eventX + b2 * eventY + c2) / (Math.sqrt(a2 * a2 + b2 * b2));
//                    Log.d("TAG", "d2==" + d2);
//                    以l1为基准线,顺势针半圆为0-180度,逆时针半圆为0-负180度
                    double moveDegree = Math.toDegrees(Math.atan2(d1, d2));

//                    Log.d("Test", "moveDegree==" + moveDegree);
                    mStartDegree = (float) (mStartDegree + Math.floor(moveDegree));

                    mStartDegree = (mStartDegree < 0) ? mStartDegree + mDegreeCycle : mStartDegree % mDegreeCycle;

                    //矫正最小角度2.5度
                    mStartDegree = getNearDegree(mStartDegree);

                    if (mOnTimeChangeListener != null) {
                        mOnTimeChangeListener.startTimeChanged(mStartDegree, mEndDegree);
                    }
                    refreshStartBtnPositon();
                    Log.d("Test", "mStartDegree==" + mStartDegree);
                    Log.d("Test", "d1==" + d1 + "\n" +
                            "d2==" + d2 + "\n" +
                            "moveDegree==" + moveDegree + "\n" +
                            "mStartBtnAngle==" + mStartBtnAngle + "\n" +
                            "mStartBtnCurX==" + mStartBtnCurX + "\n" +
                            "/mStartBtnCurY==" + mStartBtnCurY);
                    invalidate();
                } else if (mMoveFlag == 2) {

//                  坐标系的直线表达式
//                  直线l1的表达式子:过钟表中心点和结束控件中心点
                    float a1 = mCenterY - mEndBtnCurY;
                    float b1 = mEndBtnCurX - mCenterX;
                    float c1 = mEndBtnCurY * mCenterX - mCenterY * mEndBtnCurX;
                    double d1 = (a1 * eventX + b1 * eventY + c1) / (Math.sqrt(a1 * a1 + b1 * b1));
//                    Log.d("TAG", "d1==" + d1);

//                  直线l2的表达式:过钟表中心点且垂直直线l1
                    float a2 = b1;
                    float b2 = -a1;
                    float c2 = -a2 * mCenterX - b2 * mCenterY;
                    double d2 = (a2 * eventX + b2 * eventY + c2) / (Math.sqrt(a2 * a2 + b2 * b2));
//                    Log.d("TAG", "d2==" + d2);
//                    以l1为基准线,顺势针半圆为0-180度,逆时针半圆为0-负180度
                    double moveDegree = Math.toDegrees(Math.atan2(d1, d2));
//                    Log.d("Test", "moveDegree==" + moveDegree);
                    mEndDegree = (float) (mEndDegree + Math.floor(moveDegree));


                    mEndDegree = (mEndDegree < 0) ? mEndDegree + mDegreeCycle : mEndDegree % mDegreeCycle;

                    //矫正最小角度2.5度
                    mEndDegree = getNearDegree(mEndDegree);

                    if (mOnTimeChangeListener != null) {
                        mOnTimeChangeListener.endTimeChanged(mStartDegree, mEndDegree);
                    }
                    refreshEndBtnPosition();
                    Log.d("Test", "mEndDegree==" + mEndDegree);
                    Log.d("Test", "d1==" + d1 + "\n" +
                            "d2==" + d2 + "\n" +
                            "moveDegree==" + moveDegree + "\n" +
                            "mEndBtnAngle==" + mEndBtnAngle + "\n" +
                            "mEndBtnCurX==" + mEndBtnCurX + "\n" +
                            "/mEndBtnCurY==" + mEndBtnCurY);
                    invalidate();

                } else if (mMoveFlag == 3) {

//                  坐标系的直线表达式
//                  直线l1的表达式子:过钟表中心点和上次的触摸点
                    float a1 = mCenterY - mLastEventY;
                    float b1 = mLastEventX - mCenterX;
                    float c1 = mLastEventY * mCenterX - mCenterY * mLastEventX;
                    double d1 = (a1 * eventX + b1 * eventY + c1) / (Math.sqrt(a1 * a1 + b1 * b1));

                    //                  直线l2的表达式:过钟表中心点且垂直直线l1
                    float a2 = b1;
                    float b2 = -a1;
                    float c2 = -a2 * mCenterX - b2 * mCenterY;
                    double d2 = (a2 * eventX + b2 * eventY + c2) / (Math.sqrt(a2 * a2 + b2 * b2));

//                    以l1为基准线,顺势针半圆为0-180度,逆时针半圆为0-负180度
                    double moveDegree = Math.toDegrees(Math.atan2(d1, d2));

                    mStartDegree = (float) (mStartDegree + moveDegree);
                    mStartDegree = (mStartDegree < 0) ? mStartDegree + mDegreeCycle : mStartDegree % mDegreeCycle;

                    mEndDegree = (float) (mEndDegree + moveDegree);
                    mEndDegree = (mEndDegree < 0) ? mEndDegree + mDegreeCycle : mEndDegree % mDegreeCycle;


                    //矫正最小角度2.5度
                    mStartDegree = getNearDegree(mStartDegree);
                    mEndDegree = getNearDegree(mEndDegree);

                    if (mOnTimeChangeListener != null) {
                        mOnTimeChangeListener.onAllTimeChanaged(mStartDegree, mEndDegree);
                    }
                    Log.d("Test3", "moveDegree==" + moveDegree + "/mEndDegree==" + mEndDegree);
                    refreshBtnPosition();
                    mLastEventX = eventX;
                    mLastEventY = eventY;

                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mMoveFlag = -1;
                if (mOnTimeChangeListener != null) {
                    mOnTimeChangeListener.onStopTouch();
                }
                break;
            default:
                break;
        }

        return true;
    }


    private boolean isMoveEndBtn(float x, float y) {
        float dx = Math.abs(mEndBtnCurX - x);
        float dy = Math.abs(mEndBtnCurY - y);

        float btnSize = npTimeBean.getStartAndEndBtnSize();

        if (dx < btnSize / 2 && dy < btnSize / 2) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMoveStartBtn(float x, float y) {
        float dx = Math.abs(mStartBtnCurX - x);

        float btnSize = npTimeBean.getStartAndEndBtnSize();

        float dy = Math.abs(mStartBtnCurY - y);
        if (dx < btnSize / 2 && dy < btnSize / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否实在选中区域
     *
     * @param eventX
     * @param eventY
     * @return
     */
    private boolean isMoveSelectedArea(float eventX, float eventY) {
        float dx = Math.abs(mCenterX - eventX);
        float dy = Math.abs(mCenterY - eventY);
        if ((dx * dx + dy * dy) > ((mMinViewSize / 2) * (mMinViewSize / 2))) {
            return false;
        }

        double radian = Math.atan2(mCenterX - eventX, eventY - mCenterY);
        double degrees = Math.toDegrees(radian);
        double downDegree = degrees + 180;

        if (mEndBtnAngle > mStartBtnAngle && downDegree > mStartBtnAngle && downDegree < mEndBtnAngle) {
            Log.d("isMoveSelectedArea", "isMoveSelectedArea");
            return true;
        } else if (mEndBtnAngle < mStartBtnAngle && !(downDegree > mEndBtnAngle && downDegree < mStartBtnAngle)) {
            Log.d("isMoveSelectedArea", "isMoveSelectedArea");
            return true;
        }

        return false;
    }


    /**
     * 设置监听事件
     */
    public void setOnTimerChangeListener(NpTimeChangeListener listener) {
        if (mOnTimeChangeListener == null) {
            this.mOnTimeChangeListener = listener;
            mOnTimeChangeListener.onTimeInitail(mStartDegree, mEndDegree);
        }
    }

    /**
     * 设置初始化时间
     *
     * @param initStartDegree
     * @param initEndDegree
     */
    public void setInitialTime(float initStartDegree, float initEndDegree) {
        mStartDegree = (initStartDegree < 0) ? initStartDegree + mDegreeCycle : initStartDegree % mDegreeCycle;
        mEndDegree = (initEndDegree < 0) ? initEndDegree + mDegreeCycle : initEndDegree % mDegreeCycle;
        if (mOnTimeChangeListener != null) {
            mOnTimeChangeListener.onTimeInitail(mStartDegree, mEndDegree);
        }
        refreshBtnPosition();
    }


    /**
     * 获取两坐标点的直线距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }


    /**
     * 计算最近的角度
     *
     * @param realDegree
     * @return
     */
    public float getNearDegree(float realDegree) {
        Log.e("当前实时的角度", realDegree + "");


        if (npTimeBean.getMinMinuteUnit() < 2) {
            return realDegree;
        }

        double tmpValue = 720.0 / npTimeBean.getMinMinuteUnit();

        double targetDegree = 360.0 / tmpValue;//最小的刻度是2.5度(如果最小单位是5分钟的话)

        double tmpEnd = realDegree % targetDegree;//实际数据余上目标数据后剩的数据

        double result = Double.valueOf(realDegree / targetDegree).intValue();
        //取后面一个数据
        if (tmpEnd / targetDegree >= 0.5) {
            result++;
        }
        Log.e("最接近的角度", (result * targetDegree) + "");
        return Double.valueOf(result * targetDegree).floatValue();
    }


    @Override
    public void invalidate() {
        draw();
        super.invalidate();
    }


    private void draw() {
        if (canDraw() && npTimeBean != null) {
            clearBitmap(Color.RED);

            //绘制外层圆盘和内层的数字表盘
            drawCircleBg();
            //绘制选择的范围
            drawSelectProgress();
            //绘制开始和结束按钮
            drawStartEndEndBtn();
        }
    }


    /**
     * 绘制表盘 和外层的圆环
     */
    private void drawCircleBg() {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);


        btnBgCircleRadius = npTimeBean.getStartAndEndBtnSize() / 2;


        //开始按钮和结束按钮 以及外边距的总和
        float margin = npTimeBean.getStartAndEndBtnSize() * 2;

        Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg_circle_picker_clock);

        //取表盘的宽与高中小的一方
        int minSize = (int) (viewRectF.height() >= viewRectF.width() ? viewRectF.width() : viewRectF.height());

        mMinViewSize = minSize;

        //由于这里的数字表盘是个背景透明的，我们要给个底色填充一下咯，画一个大圆
        float bgNumberCircleRadius = (minSize - margin) / 2.0f;

        mWheelRadius = bgNumberCircleRadius;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(npTimeBean.getBgNumberColor());
        canvas.drawCircle(viewRectF.centerX(), viewRectF.centerY(), bgNumberCircleRadius, paint);

        Bitmap bitmap = BitmapUtils.resizeBitmap(tmp, Float.valueOf(bgNumberCircleRadius * 2).intValue());
        //绘制数字表盘
        canvas.drawBitmap(bitmap, viewRectF.centerX() - bitmap.getWidth() / 2, viewRectF.centerY() - bitmap.getHeight() / 2, null);


        //外层圆环的绘制和参数
        float radius = (minSize - margin) / 2;

        paint.setColor(npTimeBean.getDialCircleBgColor());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(margin / 2);

        canvas.drawCircle(viewRectF.centerX(), viewRectF.centerY(), radius + margin / 4, paint);

        recycleBitmap(tmp);//回收tmp
    }


    /**
     * 绘制选中区域
     */
    private void drawSelectProgress() {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(false);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        float btnSize = npTimeBean.getStartAndEndBtnSize();

//      画选中区域
        float begin = 0; //圆弧的起点位置
        float sweep = 0;
        if (mStartBtnAngle > 180 && mStartBtnAngle > mEndBtnAngle) {   //180  -- 360
            begin = -Math.abs(mStartBtnAngle - 360) - 90;
            sweep = Math.abs(Math.abs(mStartBtnAngle - 360) + mEndBtnAngle);
        } else if (mStartBtnAngle > mEndBtnAngle) {
            begin = mStartBtnAngle - 90;
            sweep = 360 - (mStartBtnAngle - mEndBtnAngle);
        } else {
            begin = mStartBtnAngle - 90;
            sweep = Math.abs(mStartBtnAngle - mEndBtnAngle);
        }

        paint.setStrokeWidth(btnSize);
        paint.setShader(new LinearGradient(mStartBtnCurX, mStartBtnCurY, mEndBtnCurX, mEndBtnCurY, npTimeBean.getStartColor(), npTimeBean.getEndColor(), Shader.TileMode.CLAMP));

        RectF rectF = new RectF(mCenterX - mWheelRadius - btnSize / 2, mCenterY - mWheelRadius - btnSize / 2, mCenterX + mWheelRadius + btnSize / 2, mCenterY + mWheelRadius + btnSize / 2);

        canvas.drawArc(rectF, begin, sweep, false, paint);
    }


    /**
     * 绘制开始和结束按钮
     * 开始按钮在结束按钮的上一层图层
     */
    private void drawStartEndEndBtn() {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(false);

        //刷新开始结束按钮的位置
        refreshBtnPosition();

        Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_circle_picker_start_btn);
        //按钮的实际大小，（减去边距后的大小）
        int realBitmapWH = Float.valueOf(npTimeBean.getStartAndEndBtnSize() - npTimeBean.getStartAndEndBtnMargin() * 2).intValue();

        //加载开始按钮
        Bitmap startOrEndBitmap = BitmapUtils.resizeBitmap(tmp, realBitmapWH);

        //结束按钮
        paint.setColor(npTimeBean.getEndColor());
        canvas.drawCircle(mEndBtnCurX, mEndBtnCurY, btnBgCircleRadius, paint);
        canvas.drawBitmap(startOrEndBitmap, mEndBtnCurX - realBitmapWH / 2, mEndBtnCurY - realBitmapWH / 2, null);


        //加载结束按钮
        paint.setColor(npTimeBean.getStartColor());
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_circle_picker_end_btn);
        startOrEndBitmap = BitmapUtils.resizeBitmap(tmp, realBitmapWH);

        //开始按钮
        canvas.drawCircle(mStartBtnCurX, mStartBtnCurY, btnBgCircleRadius, paint);
        canvas.drawBitmap(startOrEndBitmap, mStartBtnCurX - realBitmapWH / 2, mStartBtnCurY - realBitmapWH / 2, null);

    }


}

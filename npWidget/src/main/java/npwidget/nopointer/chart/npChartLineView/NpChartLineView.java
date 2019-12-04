package npwidget.nopointer.chart.npChartLineView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.animation.DecelerateInterpolator;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.chart.NpShowDataType;
import npwidget.nopointer.log.ViewLog;

/**
 * 曲线统计图
 * <p>
 * 2.高度问题
 * 3.一条数据的绘制
 * 4.阴影的距离
 */
public class NpChartLineView extends BaseView {


    private ValueAnimator valueAnimator;
    private VelocityTracker velocityTracker = VelocityTracker.obtain();


    private NpChartLineBean chartBean;


    public void setChartBean(NpChartLineBean chartBean) {
        this.chartBean = chartBean;
    }

    public NpChartLineView(Context context) {
        super(context);
        init(context);
    }

    public NpChartLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        init(context);
    }

    public NpChartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        noDataTextSize = QMUIDisplayHelper.sp2px(context, 14);
        dataMarginLeft = QMUIDisplayHelper.dp2px(context, 20);
        pointRadius = QMUIDisplayHelper.dp2px(context, 4);
        unitDp = QMUIDisplayHelper.dp2px(context, 1);
        clickRangeWidth = QMUIDisplayHelper.dp2px(context, 12);

        valueAnimator = new ValueAnimator();

        noDataPaint = new Paint();
        noDataPaint.setAntiAlias(true);

        dataLinePaint = new Paint();
        dataLinePaint.setAntiAlias(true);
        dataLinePaint.setStyle(Paint.Style.STROKE);

        dataLineGradientPaint = new Paint();
        dataLineGradientPaint.setAntiAlias(true);
        dataLineGradientPaint.setStyle(Paint.Style.FILL);

    }

    //绘制没有数据的时候的文字大小
    private float noDataTextSize = 0;

    //绘制没有数据的时候的文字
    private String noDataText = "no data ~ ";

    //无数据是文本的颜色
    private int noDataTextColor = 0xFF888888;

    public void setNoDataTextSize(float noDataTextSize) {
        this.noDataTextSize = noDataTextSize;
    }

    public void setNoDataText(String noDataText) {
        this.noDataText = noDataText;
    }

    public void setNoDataTextColor(int noDataTextColor) {
        this.noDataTextColor = noDataTextColor;
    }


    private Rect viewRectF = new Rect();

    //每个数据点之间的横向距离
    private float labelWidthSpace = 0;

    //最多横向显示的标签（数据）个数
    private int maxLabel = 0;

    //数据线距离左边距的位置
    private float dataMarginLeft = 20;

    //无数据时的画笔
    private Paint noDataPaint = null;

    //数据曲线的画笔
    private Paint dataLinePaint = null;

    //数据渐变区域的画笔
    private Paint dataLineGradientPaint = null;
    //小圆点的半径
    private float pointRadius = 10;

    private float unitDp = 1;

    /**
     * 是否是自动选中最后一个数据
     */
    private boolean isAutoSelectLastData = false;

    //上次选择的索引
    private int lastSelectIndex = -1;

    //可以点击的宽度范围
    private float clickRangeWidth = 0;

    private List<RectF> allTmpRectList = new ArrayList<>();

    private OnLineSelectListener onLineSelectListener;

    public void setOnLineSelectListener(OnLineSelectListener onLineSelectListener) {
        this.onLineSelectListener = onLineSelectListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF.left = getPaddingLeft();
        viewRectF.top = getPaddingTop();
        viewRectF.right = getMeasuredWidth() - getPaddingRight();
        viewRectF.bottom = getMeasuredHeight() - getPaddingBottom();
        bitmap = Bitmap.createBitmap(viewRectF.width(), viewRectF.height(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }


    private void draw() {
        if (canDraw()) {
            clearBitmap();
            if (chartBean != null) {
                drawLine();
                drawXYAxis();
                canvas.save();
                canvas.translate(lastMoveX, 0);
                drawLabels();
                if (chartBean.getNpChartLineDataBeans() != null && chartBean.getNpChartLineDataBeans().size() > 0) {
                    drawDataLineGradient();
                    drawDataLines();
                } else {
                    drawNoData();
                }
                canvas.restore();
            } else {
                drawNoData();
            }
        }
    }


    /**
     * 绘制XY轴
     */
    private void drawXYAxis() {
        if (chartBean == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (chartBean.isShowXAxis()) {
            //绘制X轴 纵向高度一致，统一一个变量记录高度
            float lineBottom = viewRectF.bottom - 100;
            canvas.drawLine(viewRectF.left, lineBottom, viewRectF.right, lineBottom, paint);
        }

        if (chartBean.isShowYAxis()) {
            //绘制Y轴 横向宽度一致，统一一个变量记录宽度
            float lineLeft = viewRectF.left + 100;
            canvas.drawLine(lineLeft, viewRectF.top, lineLeft, viewRectF.bottom - 100, paint);
        }

    }

    private void drawLine() {
//        Paint paint = new Paint();
//        paint.setStrokeWidth(20);
//        LinearGradient linearGradient = new LinearGradient(0, 0, 0, canvas.getHeight(), new int[]{
//                0xFF000000, 0xFFFFFFFF, 0xFFFF0000
//        }, null, Shader.TileMode.CLAMP);
//        paint.setShader(linearGradient);
//        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
    }


    //绘制没有数据的时候的显示样子
    private void drawNoData() {
        noDataPaint.setTextAlign(Paint.Align.CENTER);
        noDataPaint.setAntiAlias(true);
        noDataPaint.setTextSize(noDataTextSize);
        String text = TextUtils.isEmpty(noDataText) ? "no Data" : noDataText;
        canvas.drawText(text, viewRectF.centerX(), viewRectF.centerY(), noDataPaint);
    }


    //绘制标签
    private void drawLabels() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(chartBean.getLabelTextSize());
        List<String> chartLabels = chartBean.getNpLabelList();
        //最多要显示的label个数
        maxLabel = getMaxLabelCount(chartLabels);
        if (maxLabel < 0) {
            ViewLog.e("没有Label 不绘制");
            return;
        } else {
            //如果是多个标签的话
            labelWidthSpace = chartBean.getLabelSpaceWidth();
            if (chartBean.getShowDataType() == NpShowDataType.Equal && maxLabel > 1) {
                labelWidthSpace = viewRectF.width() / (maxLabel - 1.0f);
            }
            if (chartBean.isShowLabels()) {
                for (int i = 0; i < maxLabel; i++) {
                    float xPosition = labelWidthSpace * i + viewRectF.left + dataMarginLeft;
                    String text = chartLabels.get(i);
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(text, xPosition, viewRectF.bottom - 30, paint);
                }
            }
        }
    }


    /**
     * 绘制数据曲线
     */
    private void drawDataLines() {
        List<NpChartLineDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {
            if (maxLabel <= 0) {
                ViewLog.e("没有数据，不绘制数据曲线");
            } else if (maxLabel == 1) {
                ViewLog.e("只有一个数据点，不绘制数据曲线");
                for (NpChartLineDataBean npChartLineDataBean : lineDataBeanList) {
                    List<NpLineEntry> npLineEntryList = npChartLineDataBean.getNpLineEntryList();
                    if (npLineEntryList.size() != 1) {
                        ViewLog.e("数据有误");
                    } else {
                        ViewLog.e("数据符合规范");
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        float x = dataMarginLeft + viewRectF.left;
                        float y = getDataPointYPosition(npLineEntryList.get(0));
                        paint.setColor(Color.WHITE);
                        paint.setStrokeWidth(unitDp);
                        canvas.drawCircle(x, y, pointRadius, paint);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(npChartLineDataBean.getColor());
                        canvas.drawCircle(x, y, pointRadius, paint);
                    }
                }
            } else {
                ViewLog.e("多个数据点，可以绘制数据曲线");

                allTmpRectList.clear();

                int count = 0;
                int tempIndex = 0;
                for (NpChartLineDataBean npChartLineDataBean : lineDataBeanList) {
                    List<NpLineEntry> npLineEntries = npChartLineDataBean.getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 1) {
                        dataLinePaint.setStrokeWidth(npChartLineDataBean.getLineThickness());
                        if (npChartLineDataBean.isShowShadow()) {
                            float shadowRadius = npChartLineDataBean.getShadowRadius();
                            float shadowX = npChartLineDataBean.getShadowX();
                            float shadowY = npChartLineDataBean.getShadowY();
                            dataLinePaint.setShadowLayer(shadowRadius, shadowX, shadowY, npChartLineDataBean.getShadowColor());
                        }
                        PathData pathData = getPath(npLineEntries, false);
                        dataLinePaint.setColor(npChartLineDataBean.getColor());
                        canvas.drawPath(pathData.getPath(), dataLinePaint);
                    }
                    if (count == 0) {
                        for (NpLineEntry npLineEntry : npLineEntries) {
                            RectF rectF = new RectF();
                            rectF.left = dataMarginLeft + labelWidthSpace * tempIndex - clickRangeWidth;
                            rectF.right = rectF.left + clickRangeWidth * 2;
                            rectF.top = getDataPointYPosition(npLineEntry);
                            rectF.bottom = viewRectF.bottom;
                            allTmpRectList.add(rectF);
                            tempIndex++;
                        }
                    }
                    count++;
                }

                for (int i = 0; i < lineDataBeanList.size(); i++) {
                    List<NpLineEntry> npLineEntries = lineDataBeanList.get(i).getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 0) {
                        if (lastSelectIndex != -1) {
                            Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            float x = dataMarginLeft + viewRectF.left + labelWidthSpace * lastSelectIndex;
                            float y = getDataPointYPosition(npLineEntries.get(lastSelectIndex));
                            paint.setColor(Color.WHITE);
                            paint.setStrokeWidth(unitDp);
                            canvas.drawCircle(x, y, pointRadius, paint);
                            paint.setStyle(Paint.Style.STROKE);
                            paint.setColor(lineDataBeanList.get(i).getColor());
                            canvas.drawCircle(x, y, pointRadius, paint);
                        }
                    }
                }
                if (onLineSelectListener != null && lastSelectIndex != -1) {
                    onLineSelectListener.onSelectLine(lineDataBeanList, lastSelectIndex);
                }
            }


        } else {
            ViewLog.e("chartBean.getNpChartLineDataBeans()=null !!!!");
        }
    }

    /**
     * 绘制数据的渐变曲线
     */
    private void drawDataLineGradient() {
        List<NpChartLineDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {
            if (maxLabel > 1) {
                for (NpChartLineDataBean npChartLineDataBean : lineDataBeanList) {

                    List<NpLineEntry> npLineEntries = npChartLineDataBean.getNpLineEntryList();
                    if (npChartLineDataBean.isShowGradient() && npLineEntries != null && npLineEntries.size() > 1) {
                        PathData pathData = getPath(npLineEntries, true);

//                        float prenent = 1 - pathData.getMaxValue() / chartBean.getMaxY();
//                        ViewLog.e("prenent比例=====>" + prenent);

                        LinearGradient lg = new LinearGradient(0, viewRectF.top, 0, viewRectF.bottom,
                                npChartLineDataBean.getStartColor(), npChartLineDataBean.getEndColor(),
                                Shader.TileMode.CLAMP);
                        //mask画笔设置渐变效果
                        dataLineGradientPaint.setShader(lg);
                        canvas.drawPath(pathData.getPath(), dataLineGradientPaint);
                    }
                }
            } else {
                ViewLog.e("没有标签或者标签数量不够，不绘制渐变曲线");
            }
        } else {
            ViewLog.e("chartBean.getNpChartLineDataBeans()=null !!!!");
        }
    }


    /**
     * 如果是多条曲线的话，要以最多label的曲线为参考(才能完整的展示这个最长的曲线的数据)
     *
     * @param npChartLabels
     * @return
     */
    private int getMaxLabelCount(List<String> npChartLabels) {
        if (npChartLabels != null && npChartLabels.size() > 0) {
            return npChartLabels.size();
        } else {
            return 0;
        }
    }


    /**
     * 计算这个点在竖直方向上的位置
     *
     * @param npLineEntry
     * @return
     */
    private float getDataPointYPosition(NpLineEntry npLineEntry) {
        float thisTotalHeight = viewRectF.height() - 100;
        float min = chartBean.getMinY();
        float max = chartBean.getMaxY();
        if (max == min) {
            max = min * 1.05f;

        }
        float tmpValue1 = npLineEntry.getValue();
        if (tmpValue1 <= min) {
            tmpValue1 = min;
        }

        float precent1 = (tmpValue1 - min) / (max - min);
        return (thisTotalHeight * (1.0f - precent1));

    }

    private PathData getPath(List<NpLineEntry> lineEntryList, boolean isClosed) {
        PathData pathData = new PathData();
        Path path = new Path();
        float thisTotalHeight = viewRectF.height() - 100;
        float leftMargin = viewRectF.left;
        float xDisAdd = labelWidthSpace;
        float min = chartBean.getMinY();
        float max = chartBean.getMaxY();
        int dataLen = lineEntryList.size();

        if (max == min) {
            max = min * 1.05f;
        }

        float precent1 = 0, tmpValue1 = 0;
        float precent2 = 0, tmpValue2 = 0;


        tmpValue1 = lineEntryList.get(0).getValue();
        if (tmpValue1 <= min) {
            tmpValue1 = min;
        }

        precent1 = (tmpValue1 - min) / (max - min);
        path.moveTo(dataMarginLeft + leftMargin, (thisTotalHeight * (1.0f - precent1)));
//        if (isClosed) {
//            precent1 = (tmpValue1 - min) / (max - min);
//            //先把点移动到最开始的位置
////            path.moveTo(0 * xDisAdd + leftMargin, thisTotalHeight);
//            path.moveTo(0 * xDisAdd + leftMargin, (thisTotalHeight * (1.0f - precent1)));
//        } else {
//            precent1 = (tmpValue1 - min) / (max - min);
//            path.moveTo(0 * xDisAdd + leftMargin, (thisTotalHeight * (1.0f - precent1)));
//        }


        List<Float> tmpList = new ArrayList<>();
        for (NpLineEntry npLineEntry : lineEntryList) {
            tmpList.add(npLineEntry.getValue());
        }
        if (tmpList.size() > 0) {
            pathData.setMaxValue(Collections.max(tmpList));
        }
        for (int i = 0; i < dataLen - 1; i++) {

            tmpValue1 = lineEntryList.get(i).getValue();
            if (tmpValue1 <= min) {
                tmpValue1 = min;
            }
            if (tmpValue1 >= max) {
                tmpValue1 = max;
            }

            precent1 = (tmpValue1 - min) / (max - min);

            float x1 = i * xDisAdd + leftMargin + dataMarginLeft;
            float y1 = (thisTotalHeight * (1.0f - precent1)) + getPaddingTop() + viewRectF.top;


            tmpValue2 = lineEntryList.get(i + 1).getValue();
            if (tmpValue2 <= min) {
                tmpValue2 = min;
            }
            if (tmpValue2 >= max) {
                tmpValue2 = max;
            }

            precent2 = (tmpValue2 - min) / (max - min);

            float x2 = (i + 1) * xDisAdd + leftMargin + dataMarginLeft;
            float y2 = (thisTotalHeight * (1.0f - precent2)) + getPaddingTop() + viewRectF.top;

            PointF startp = new PointF(x1, y1);
            PointF endp = new PointF(x2, y2);
            float wt = (startp.x + endp.x) / 2;
            PointF p3 = new PointF(wt, startp.y);
            PointF p4 = new PointF(wt, endp.y);

//            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
        }

        if (isClosed) {
            path.lineTo((dataLen - 1) * xDisAdd + leftMargin + dataMarginLeft, (thisTotalHeight * (1.0f - precent2)));
            path.lineTo((dataLen - 1) * xDisAdd + leftMargin + dataMarginLeft, thisTotalHeight);
            path.lineTo(leftMargin + dataMarginLeft, thisTotalHeight);
        }

        pathData.setPath(path);
        return pathData;
    }


    public class PathData {
        private Path path = null;
        private float maxValue = 0;

        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

        public float getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(float maxValue) {
            this.maxValue = maxValue;
        }

        public PathData() {
        }

        public PathData(Path path, float maxValue) {
            this.path = path;
            this.maxValue = maxValue;
        }
    }

    @Override
    public void invalidate() {
        draw();
        super.invalidate();
    }


    private float downX;
    private float moveX = 0;
    private float currentX;
    private float lastMoveX = 0;
    private boolean isUp = false;

    private int leftScroll;
    private int rightScroll;
    private int xVelocity;


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        currentX = event.getX();
        isUp = false;
        velocityTracker.computeCurrentVelocity(500);
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下时如果属性动画还没执行完,就终止,记录下当前按下点的位置
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    valueAnimator.end();
                    valueAnimator.cancel();
                }
                downX = event.getX();

                for (int i = 0; i < allTmpRectList.size(); i++) {
                    if (allTmpRectList.get(i).contains(event.getX(), event.getY())) {
                        lastSelectIndex = i;
                        postInvalidateDelayed(20);
                        ViewLog.e("lastSelectIndex===>" + lastSelectIndex);
                        break;
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                //滑动时候,通过假设的滑动距离,做超出左边界以及右边界的限制。
                moveX = currentX - downX + lastMoveX;
                if (moveX >= 0) {
                    moveX = 0;
                } else {
                    if (maxLabel * labelWidthSpace <= viewRectF.width() + dataMarginLeft) {
                        moveX = 0;
                    } else if (moveX <= getWhichScaleMovex()) {
                        moveX = getWhichScaleMovex();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起时候制造惯性滑动
                lastMoveX = moveX;
                xVelocity = (int) velocityTracker.getXVelocity();
                autoVelocityScroll(xVelocity);
                velocityTracker.clear();
                break;
        }
        invalidate();
        return true;
    }

    private void autoVelocityScroll(int xVelocity) {
        //惯性滑动的代码,速率和滑动距离,以及滑动时间需要控制的很好,应该网上已经有关于这方面的算法了吧。。这里是经过N次测试调节出来的惯性滑动
        if (Math.abs(xVelocity) < 50) {
            isUp = true;
            return;
        }
        if (valueAnimator.isRunning()) {
            return;
        }
        valueAnimator = ValueAnimator.ofInt(0, xVelocity / 20).setDuration(Math.abs(xVelocity / 10));
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveX += (int) animation.getAnimatedValue();
                if (moveX >= 0) {
                    moveX = 0;
                } else {
                    if (maxLabel * labelWidthSpace <= viewRectF.width() + dataMarginLeft) {
                        moveX = 0;
                    } else if (moveX <= getWhichScaleMovex()) {
                        moveX = getWhichScaleMovex();
                    }
                }
                lastMoveX = moveX;
                invalidate();
            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isUp = true;
                invalidate();
            }
        });

        valueAnimator.start();
    }


    private float getWhichScaleMovex() {
        return viewRectF.width() / 2 - labelWidthSpace * maxLabel + viewRectF.width() / 2;
    }


    public interface OnLineSelectListener {
        void onSelectLine(List<NpChartLineDataBean> lineDataBeans, int index);
    }
}

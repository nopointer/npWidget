package npwidget.nopointer.chart.npChartPointView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import npwidget.nopointer.R;
import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;
import npwidget.nopointer.log.ViewLog;
import npwidget.nopointer.utils.SizeUtils;

/**
 * 曲线统计图
 * <p>
 * 2.高度问题
 * 3.一条数据的绘制
 * 4.阴影的距离
 */
public class NpChartPointView extends BaseView {

    private ValueAnimator valueAnimator;
    private VelocityTracker velocityTracker = VelocityTracker.obtain();

    private NpChartPointBean chartBean;

    //底部文字的高度
    private float bottomLabelRangeHeight = 0;


    private float topSpaceHeight = 0;

    //底部文字的大小
    private float labelTextSize = 0;

    //是否是已经点击过了
    private boolean hasClick = false;

    //是否已经触摸过了
    private boolean hasTouch = false;


    public void setChartBean(NpChartPointBean chartBean) {
        this.chartBean = chartBean;
        lastSelectIndex = -1;
        hasClick = false;
        hasTouch = false;
    }

    public NpChartPointView(Context context) {
        super(context);
        init(context, null);
    }

    public NpChartPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NpChartPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        noDataTextSize = SizeUtils.sp2px(context, 14);
        dataMarginLeft = SizeUtils.dp2px(context, 20);
        dataMarginRight = SizeUtils.dp2px(context, 20);
        pointRadius = SizeUtils.dp2px(context, 4);
        unitDp = SizeUtils.dp2px(context, 1);
        clickRangeWidth = SizeUtils.dp2px(context, 12);

        valueAnimator = new ValueAnimator();

        noDataPaint = new Paint();
        noDataPaint.setAntiAlias(true);

        dataLinePaint = new Paint();
        dataLinePaint.setAntiAlias(true);
        dataLinePaint.setStyle(Paint.Style.STROKE);

        dataLineGradientPaint = new Paint();
        dataLineGradientPaint.setAntiAlias(true);
        dataLineGradientPaint.setStyle(Paint.Style.FILL);


        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NpChartLineView);
            dataMarginLeft = typedArray.getDimension(R.styleable.NpChartLineView_dataMarginLeft, SizeUtils.dp2px(context, 20));
            dataMarginRight = typedArray.getDimension(R.styleable.NpChartLineView_dataMarginRight, SizeUtils.dp2px(context, 20));
            canvasBg = typedArray.getResourceId(R.styleable.NpChartLineView_canvasBg, 0xFFFFFFFF);
            typedArray.recycle();
            ViewLog.e("attrs!=null:" + (attrs != null));
        }

        ViewLog.e("dataMarginLeft:" + dataMarginLeft);
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

    //数据线距离右边距的位置
    private float dataMarginRight = 20;

    private int canvasBg = 0xFFFFFFFF;

    //无数据时的画笔
    private Paint noDataPaint = null;

    //数据曲线的画笔
    private Paint dataLinePaint = null;

    //数据渐变区域的画笔
    private Paint dataLineGradientPaint = null;
    //小圆点的半径
    private float pointRadius = 10;

    private float unitDp = 1;


    //上次选择的索引
    private int lastSelectIndex = -1;

    //可以点击的宽度范围
    private float clickRangeWidth = 0;

    private List<RectF> allTmpRectList = new ArrayList<>();

    private OnPointSelectListener onPointSelectListener;

    public void setOnPointSelectListener(OnPointSelectListener onPointSelectListener) {
        this.onPointSelectListener = onPointSelectListener;
    }

    public void setPointRadius(float pointRadius) {
        this.pointRadius = pointRadius;
    }

    public void setClickRangeWidth(float clickRangeWidth) {
        this.clickRangeWidth = clickRangeWidth;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF.left = getPaddingLeft();
        viewRectF.top = getPaddingTop();
        viewRectF.right = getMeasuredWidth() - getPaddingRight();
        viewRectF.bottom = getMeasuredHeight() - getPaddingBottom();
        ViewLog.e("矩形：" + viewRectF.toString());
        if (viewRectF.width() > 0 && viewRectF.height() > 0) {
            bitmap = Bitmap.createBitmap(viewRectF.width(), viewRectF.height(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            draw();
        }
    }


    private void draw() {
        if (canDraw()) {
            clearBitmap(canvasBg);
            if (chartBean != null) {
                loadCfg();
                drawXYAxis();
                drawReferenceLine();
                //绘制可是区域的范围，调试用
//                Paint paint = new Paint();
//                paint.setAntiAlias(true);
//                paint.setStyle(Paint.Style.STROKE);
//                canvas.drawRect(viewRectF, paint);
                canvas.save();
                ViewLog.e("此时的位移是:" + tranlateX);
                canvas.translate(tranlateX, 0);
                drawLabels();
                if (chartBean.getNpChartLineDataBeans() != null && chartBean.getNpChartLineDataBeans().size() > 0) {
                    int dataSum = 0;

                    for (NpChartPointDataBean chartLineDataBean : chartBean.getNpChartLineDataBeans()) {
                        for (NpPointEntry lineEntry : chartLineDataBean.getNpLineEntryList()) {
                            dataSum += lineEntry.getValue();
                        }
                    }
                    if (dataSum <= 0) {
                        drawNoData();
                    } else {
                        drawDataLineGradient();
                        drawDataLines();
                    }
                } else {
                    drawNoData();
                }
                canvas.restore();
            } else {
                drawNoData();
            }
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(canvasBg);

            //左边mask
            RectF maskRectF = new RectF(0, 0, dataMarginLeft - 1 - pointRadius - unitDp, viewRectF.bottom);
            canvas.drawRect(maskRectF, paint);

            //右边mask
            maskRectF.left = viewRectF.width() - dataMarginRight + pointRadius + unitDp;
            maskRectF.right = viewRectF.width();
            canvas.drawRect(maskRectF, paint);
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
            float lineBottom = viewRectF.bottom - bottomLabelRangeHeight;
            paint.setColor(chartBean.getXAxisLineColor());
            ViewLog.e("xy矩形:" + viewRectF.toString());
            canvas.drawLine(dataMarginLeft - pointRadius, lineBottom, viewRectF.width() - dataMarginRight + pointRadius, lineBottom, paint);
        }

        if (chartBean.isShowYAxis()) {
            //绘制Y轴 横向宽度一致，统一一个变量记录宽度
            float lineLeft = dataMarginLeft;
            paint.setColor(chartBean.getYAxisLineColor());
            canvas.drawLine(lineLeft - pointRadius, viewRectF.top, lineLeft - pointRadius, viewRectF.bottom - bottomLabelRangeHeight, paint);
        }

    }


    /**
     * 绘制参考线
     */
    private void drawReferenceLine() {
        if (!chartBean.isShowRefreshLine()) return;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFAAAAAA);

        paint.setTextSize(30);

        paint.setPathEffect(new DashPathEffect(new float[]{12, 12}, 0));

        int refLineCount = chartBean.getRefreshLineCount();

        float height = (viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight) / (refLineCount);

        for (int i = 1; i <= refLineCount; i++) {
            float yPosition = viewRectF.bottom - bottomLabelRangeHeight - height * i;
            canvas.drawLine(viewRectF.left, yPosition, viewRectF.right, yPosition, paint);
        }


        //绘制参考值
        int refValueCount = chartBean.getRefreshValueCount();

        ViewLog.e("refValueCount = " + refValueCount + " , chartBean.getMaxY() = " + chartBean.getMaxY() + " ,chartBean.getMinY() = " + chartBean.getMinY());

        float valueAdd = (chartBean.getMaxY() - chartBean.getMinY()) / refValueCount;

        height = (viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight) / (refValueCount);

        for (int i = 1; i <= refValueCount; i++) {
            float yPosition = viewRectF.bottom - bottomLabelRangeHeight - height * i;

            String text = String.format(Locale.US, "%d", Float.valueOf((valueAdd * i)).intValue());

            canvas.drawText(text, dataMarginLeft - pointRadius + 10, yPosition + 36, paint);
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
        noDataPaint.setColor(noDataTextColor);
        String text = TextUtils.isEmpty(noDataText) ? "no Data" : noDataText;

        canvas.drawText(text, viewRectF.centerX(), viewRectF.centerY() - bottomLabelRangeHeight / 2, noDataPaint);
    }


    //绘制标签
    private void drawLabels() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(labelTextSize);
        List<String> chartLabels = chartBean.getNpLabelList();

        if (maxLabel < 0) {
            ViewLog.e("没有Label 不绘制");
            return;
        } else {
            //如果是多个标签的话
            ViewLog.e("点图的 maxLabel = " + maxLabel);

            if (chartBean.isShowLabels()) {
                String labelText = "";
                paint.setColor(chartBean.getLabelTextColor());

//                labelWidthSpace = (viewRectF.width()-dataMarginLeft-dataMarginRight)/maxLabel;


                for (int i = 0; i < maxLabel; i++) {
//                    float xPosition = labelWidthSpace * i + dataMarginLeft;

                    //计算柱子的中心点
                    float xPosition = i * labelWidthSpace + viewRectF.left + dataMarginLeft + labelWidthSpace / 2.0f;

                    labelText = chartLabels.get(i);
                    paint.setTextAlign(Paint.Align.CENTER);
                    RectF rectF = new RectF(xPosition, viewRectF.bottom - bottomLabelRangeHeight, xPosition, viewRectF.bottom);
                    Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                    float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                    float baseline = rectF.centerY() + distance;
                    canvas.drawText(labelText, rectF.centerX(), baseline, paint);
                }
            }
        }
    }


    /**
     * 绘制数据曲线
     */
    private void drawDataLines() {
        List<NpChartPointDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {
            if (maxLabel <= 0) {
                ViewLog.e("没有数据，不绘制数据曲线");
            } else if (maxLabel == 1) {
                ViewLog.e("只有一个数据点，不绘制数据曲线");
                for (NpChartPointDataBean npChartLineDataBean : lineDataBeanList) {
                    List<NpPointEntry> npLineEntryList = npChartLineDataBean.getNpLineEntryList();
                    allTmpRectList.clear();

                    if (npLineEntryList.size() != 1) {
                        ViewLog.e("数据有误");
                    } else {
                        ViewLog.e("数据符合规范");
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        float x = dataMarginLeft;
                        float y = getDataPointYPosition(npLineEntryList.get(0));

                        RectF rectF = new RectF();
                        rectF.left = x - clickRangeWidth;
                        rectF.right = x + clickRangeWidth;
                        rectF.top = y - clickRangeWidth;
                        rectF.bottom = y + clickRangeWidth;
                        allTmpRectList.add(rectF);

                        paint.setColor(Color.WHITE);
                        paint.setStrokeWidth(unitDp);
                        canvas.drawCircle(x, y, pointRadius, paint);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(npChartLineDataBean.getColor());
                        canvas.drawCircle(x, y, pointRadius, paint);
                    }
                }
                if (chartBean.getNpSelectMode() != NpSelectMode.NONE) {
                    if (onPointSelectListener != null) {
                        onPointSelectListener.onSelectPoint(lineDataBeanList, 0);
                    }
                }
            } else {
                ViewLog.e("多个数据点，可以绘制数据曲线 hasTouch:" + hasTouch);

                allTmpRectList.clear();

                Paint pointPaint = new Paint();
                pointPaint.setAntiAlias(true);

                int count = 0;
                int tempIndex = 0;

                ViewLog.e("lineDataBeanList.size() = " + lineDataBeanList.size());

                for (NpChartPointDataBean npChartLineDataBean : lineDataBeanList) {
                    List<NpPointEntry> npLineEntries = npChartLineDataBean.getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 1) {
                        dataLinePaint.setStrokeWidth(npChartLineDataBean.getLineThickness());
                        if (npChartLineDataBean.isShowShadow()) {
                            float shadowRadius = npChartLineDataBean.getShadowRadius();
                            float shadowX = npChartLineDataBean.getShadowX();
                            float shadowY = npChartLineDataBean.getShadowY();
                            dataLinePaint.setShadowLayer(shadowRadius, shadowX, shadowY, npChartLineDataBean.getShadowColor());
                        }
//                        PathData pathData = getPath(npLineEntries, false);
//                        dataLinePaint.setColor(npChartLineDataBean.getColor());
//                        canvas.drawPath(pathData.getPath(), dataLinePaint);

                        float xDisAdd = labelWidthSpace;
                        for (int i = 0; i < npLineEntries.size(); i++) {

                            float x = dataMarginLeft + xDisAdd * i + xDisAdd / 2;
                            float y = getDataPointYPosition(npLineEntries.get(i));

                            RectF rectF = new RectF();
                            rectF.left = x - clickRangeWidth;
                            rectF.right = x + clickRangeWidth;
                            rectF.top = y - clickRangeWidth;
                            rectF.bottom = y + clickRangeWidth;
//                            allTmpRectList.add(rectF);

                            pointPaint.setColor(npChartLineDataBean.getColor());
                            pointPaint.setStrokeWidth(unitDp);
                            canvas.drawCircle(x, y, pointRadius, pointPaint);
//                            pointPaint.setStyle(Paint.Style.STROKE);
//                            pointPaint.setColor(npChartLineDataBean.getColor());
//                            canvas.drawCircle(x, y, pointRadius, pointPaint);
                        }

                    }
                    if (count == 0) {
                        for (NpPointEntry npLineEntry : npLineEntries) {
                            RectF rectF = new RectF();
                            rectF.left = dataMarginLeft + labelWidthSpace * tempIndex - clickRangeWidth+labelWidthSpace/2;
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
                    List<NpPointEntry> npLineEntries = lineDataBeanList.get(i).getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 0) {

//                        if (!hasTouch) {
//                            if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST) {
//                                lastSelectIndex = 0;
//                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_LAST) {
//                                lastSelectIndex = npLineEntries.size() - 1;
//                                if (!hasTouch) {
//                                    if ((lastSelectIndex + 1) * labelWidthSpace > viewRectF.width() + dataMarginLeft) {
//                                        tranlateX = -((lastSelectIndex + 1) * labelWidthSpace) + viewRectF.width() + dataMarginLeft;
//                                        lastX = tranlateX;
//                                    }
//                                }
//                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_MIN) {
////                                for (int t = 0; t < allColumnDataSum.size(); t++) {
////                                    if (allColumnDataSum.get(t) == Collections.min(allColumnDataSum)) {
////                                        lastSelectIndex = t;
////                                        break;
////                                    }
////                                }
//                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_MAX) {
////                                for (int t = 0; i < allColumnDataSum.size(); i++) {
////                                    if (allColumnDataSum.get(i) == Collections.max(allColumnDataSum)) {
////                                        lastSelectIndex = i;
////                                        break;
////                                    }
////                                }
//                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST_NOT_NULL) {
//                                for (int t = 0; t < npLineEntries.size(); t++) {
//                                    if (npLineEntries.get(t).getValue() > chartBean.getMinY()) {
//                                        lastSelectIndex = t;
//                                        break;
//                                    }
//                                }
//                                if (!hasTouch) {
//                                    if ((lastSelectIndex + 1) * labelWidthSpace > viewRectF.width() + dataMarginLeft) {
//                                        tranlateX = -((lastSelectIndex) * labelWidthSpace) + viewRectF.width() + dataMarginLeft;
//                                        lastX = tranlateX;
//                                    }
//                                }
//                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_LAST_NOT_NULL) {
//                                for (int t = npLineEntries.size() - 1; t >= 0; t--) {
//                                    if (npLineEntries.get(t).getValue() > chartBean.getMinY()) {
//                                        lastSelectIndex = t;
//                                        break;
//                                    }
//                                }
//                                if (!hasTouch) {
//                                    if ((lastSelectIndex + 1) * labelWidthSpace >= viewRectF.width() - dataMarginLeft - dataMarginRight) {
//                                        tranlateX = -((lastSelectIndex + 1) * labelWidthSpace) + viewRectF.width() - dataMarginLeft - dataMarginRight;
//                                        lastX = tranlateX;
//                                    }
//                                }
//                            }
//                        }
                        ViewLog.e("lastSelectIndex:" + lastSelectIndex);
                        if (lastSelectIndex != -1) {
                            Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            float x = dataMarginLeft + labelWidthSpace * lastSelectIndex + labelWidthSpace / 2;
                            float y = getDataPointYPosition(npLineEntries.get(lastSelectIndex));
//                            paint.setColor(Color.WHITE);
                            paint.setStrokeWidth(unitDp);
                            paint.setColor(lineDataBeanList.get(i).getColor());
                            canvas.drawCircle(x, y, pointRadius, paint);

                            canvas.drawLine(x, viewRectF.top + topSpaceHeight, x, viewRectF.bottom - bottomLabelRangeHeight, paint);

//                            paint.setStyle(Paint.Style.FILL);
//                            canvas.drawCircle(x, y, pointRadius, paint);
                        }
                    }
                }
                if (onPointSelectListener != null && lastSelectIndex != -1) {
                    onPointSelectListener.onSelectPoint(lineDataBeanList, lastSelectIndex);
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
        List<NpChartPointDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {
            if (maxLabel > 1) {
                for (NpChartPointDataBean npChartLineDataBean : lineDataBeanList) {

                    List<NpPointEntry> npLineEntries = npChartLineDataBean.getNpLineEntryList();
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
    private float getDataPointYPosition(NpPointEntry npLineEntry) {
        float thisTotalHeight = viewRectF.height() - bottomLabelRangeHeight;
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

    private PathData getPath(List<NpPointEntry> lineEntryList, boolean isClosed) {
        PathData pathData = new PathData();
        Path path = new Path();
        float thisTotalHeight = viewRectF.height() - bottomLabelRangeHeight;
        float leftMargin = dataMarginLeft;
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
        path.moveTo(leftMargin, (thisTotalHeight * (1.0f - precent1)));
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
        for (NpPointEntry npLineEntry : lineEntryList) {
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

            float x1 = i * xDisAdd + leftMargin;
            float y1 = (thisTotalHeight * (1.0f - precent1)) + getPaddingTop() + viewRectF.top;


            tmpValue2 = lineEntryList.get(i + 1).getValue();
            if (tmpValue2 <= min) {
                tmpValue2 = min;
            }
            if (tmpValue2 >= max) {
                tmpValue2 = max;
            }

            precent2 = (tmpValue2 - min) / (max - min);

            float x2 = (i + 1) * xDisAdd + leftMargin;
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
            path.lineTo((dataLen - 1) * xDisAdd + leftMargin, (thisTotalHeight * (1.0f - precent2)));
            path.lineTo((dataLen - 1) * xDisAdd + leftMargin, thisTotalHeight);
            path.lineTo(leftMargin, thisTotalHeight);
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
    private float currentX;
    private float tranlateX = 0;
    private float lastX = 0;

    private int xVelocity;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnableTouch()) {
            return false;
        }
        hasTouch = true;
        velocityTracker.computeCurrentVelocity(1500);
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下时如果属性动画还没执行完,就终止,记录下当前按下点的位置
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    valueAnimator.end();
                    valueAnimator.cancel();
                }
                downX = event.getX();
                ViewLog.e("fuck" + downX + "///");
                for (int i = 0; i < allTmpRectList.size(); i++) {
                    if (allTmpRectList.get(i).contains(event.getX() - tranlateX, event.getY())) {
                        lastSelectIndex = i;
                        hasClick = true;
                        postInvalidateDelayed(20);
                        ViewLog.e("lastSelectIndex===>" + lastSelectIndex);
                        break;
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                //滑动时候,通过假设的滑动距离,做超出左边界以及右边界的限制。
                if (Math.abs(currentX - downX) > SizeUtils.dp2px(getContext(), 20)) {
                    tranlateX = currentX - downX + lastX;
                    if (tranlateX >= 0) {
                        tranlateX = 0;
                    } else {
                        if ((maxLabel - 1) * labelWidthSpace <= viewRectF.width() - dataMarginLeft - dataMarginRight) {
                            ViewLog.e("不能左滑动？");
                            tranlateX = 0;
                        } else if (tranlateX <= getWhichScaleMovex()) {
                            tranlateX = getWhichScaleMovex();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起时候制造惯性滑动
                lastX = tranlateX;
                xVelocity = (int) velocityTracker.getXVelocity();
                autoVelocityScroll(xVelocity);
                velocityTracker.clear();
                break;
        }
        postInvalidateDelayed(20);
        return true;
    }

    private void autoVelocityScroll(int xVelocity) {
        ViewLog.e("xVelocity:" + xVelocity);
        //惯性滑动的代码,速率和滑动距离,以及滑动时间需要控制的很好,应该网上已经有关于这方面的算法了吧。。这里是经过N次测试调节出来的惯性滑动
        if (Math.abs(xVelocity) < 2000) {
            return;
        }
        if (valueAnimator.isRunning()) {
            return;
        }
        valueAnimator = ValueAnimator.ofFloat(0, xVelocity / 220).setDuration(Math.abs(xVelocity) / 12);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tranlateX += (float) animation.getAnimatedValue();
                if (tranlateX >= 0) {
                    tranlateX = 0;
                } else {
                    if ((maxLabel - 1) * labelWidthSpace <= viewRectF.width() - dataMarginLeft - dataMarginRight) {
                        tranlateX = 0;
                    } else if (tranlateX <= getWhichScaleMovex()) {
                        tranlateX = getWhichScaleMovex();
                    }
                }
                lastX = tranlateX;
                invalidate();
            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                invalidate();
            }
        });

        valueAnimator.start();
    }

    private void loadCfg() {
        labelTextSize = chartBean.getLabelTextSize();
        bottomLabelRangeHeight = chartBean.getBottomHeight();
        topSpaceHeight = chartBean.getTopHeight();
        //最多要显示的label个数
        maxLabel = getMaxLabelCount(chartBean.getNpLabelList());
        labelWidthSpace = chartBean.getLabelSpaceWidth();
        if (chartBean.getShowDataType() == NpShowDataType.Equal && maxLabel > 1) {
            labelWidthSpace = (viewRectF.width() - dataMarginLeft - dataMarginRight) / (maxLabel);
            ViewLog.e("满足平分的场景？" + maxLabel + "///" + labelWidthSpace);
        }
        calculationScroll();
    }


    /**
     * 计算要滚动的距离
     */
    private void calculationScroll() {
        if (hasTouch) return;
        List<NpChartPointDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {
            if (maxLabel >= 2) {
                ViewLog.e("多个数据点");
                for (int i = 0; i < lineDataBeanList.size(); i++) {
                    List<NpPointEntry> npLineEntries = lineDataBeanList.get(i).getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 0) {
                        ViewLog.e("hasClick:" + hasClick);
                        ViewLog.e("getNpSelectMode:" + chartBean.getNpSelectMode());
                        if (!hasClick) {
                            if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST) {
                                lastSelectIndex = 0;
                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_LAST) {
                                lastSelectIndex = npLineEntries.size() - 1;
                                if ((lastSelectIndex + 1) * labelWidthSpace > viewRectF.width() + dataMarginLeft) {
                                    tranlateX = -((lastSelectIndex + 1) * labelWidthSpace) + viewRectF.width() + dataMarginLeft;
                                    lastX = tranlateX;
                                }
                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_MIN) {
//                                for (int t = 0; t < allColumnDataSum.size(); t++) {
//                                    if (allColumnDataSum.get(t) == Collections.min(allColumnDataSum)) {
//                                        lastSelectIndex = t;
//                                        break;
//                                    }
//                                }
                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_MAX) {
//                                for (int t = 0; i < allColumnDataSum.size(); i++) {
//                                    if (allColumnDataSum.get(i) == Collections.max(allColumnDataSum)) {
//                                        lastSelectIndex = i;
//                                        break;
//                                    }
//                                }
                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST_NOT_NULL) {
                                for (int t = 0; t < npLineEntries.size(); t++) {
                                    if (npLineEntries.get(t).getValue() > chartBean.getMinY()) {
                                        lastSelectIndex = t;
                                        break;
                                    }
                                }
                                if (!hasTouch) {
                                    if ((lastSelectIndex) * labelWidthSpace > viewRectF.width() + dataMarginLeft) {
                                        tranlateX = -((lastSelectIndex) * labelWidthSpace) + viewRectF.width() + dataMarginLeft;
                                        lastX = tranlateX;
                                    }
                                }
                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_LAST_NOT_NULL) {
                                for (int t = npLineEntries.size() - 1; t >= 0; t--) {
                                    if (npLineEntries.get(t).getValue() > chartBean.getMinY()) {
                                        lastSelectIndex = t;
                                        break;
                                    }
                                }
                                ViewLog.e("SELECT_LAST_NOT_NULL:是到这里吗？//" + hasTouch);
                                if (!hasTouch) {
                                    if ((lastSelectIndex) * labelWidthSpace >= viewRectF.width() - dataMarginLeft - dataMarginRight) {
                                        tranlateX = -((lastSelectIndex) * labelWidthSpace) + viewRectF.width() - dataMarginLeft - dataMarginRight;
                                        lastX = tranlateX;
                                        ViewLog.e("lastSelectIndex:是到这里吗？//" + lastSelectIndex);
                                    }
                                }
                            }
                        }
                    }
                }
                if (onPointSelectListener != null && lastSelectIndex != -1) {
                    onPointSelectListener.onSelectPoint(lineDataBeanList, lastSelectIndex);
                }
            }
        }
    }


    private float getWhichScaleMovex() {
        float result = viewRectF.width() - labelWidthSpace * (maxLabel - 1) - dataMarginLeft - dataMarginRight;
        ViewLog.e("result:" + result);
        return result;
    }


    public interface OnPointSelectListener {
        void onSelectPoint(List<NpChartPointDataBean> lineDataBeans, int index);
    }
}

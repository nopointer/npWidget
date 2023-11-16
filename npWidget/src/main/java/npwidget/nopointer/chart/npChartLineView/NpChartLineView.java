package npwidget.nopointer.chart.npChartLineView;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import npwidget.nopointer.R;
import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;
import npwidget.nopointer.chart.NpValueFormatter;
import npwidget.nopointer.chart.PathData;
import npwidget.nopointer.chart.YAxle;
import npwidget.nopointer.log.NpViewLog;
import npwidget.nopointer.utils.SizeUtils;

/**
 * 曲线统计图
 * <p>
 * 2.高度问题
 * 3.一条数据的绘制
 * 4.阴影的距离
 */
public class NpChartLineView extends BaseView {

    private NpChartLineBean chartBean;

    //底部文字的高度
    private float bottomLabelRangeHeight = 0;

    private float topSpaceHeight = 0;

    //底部文字的大小
    private float labelTextSize = 0;

    //是否是已经点击过了
    private boolean hasClick = false;

    //是否已经触摸过了
    private boolean hasTouch = false;

    /**
     * 左边/右边 Y轴的 宽度 将会是动态的，要考虑到刻度上面的值所占领的长度
     */
    private float leftYAxleWidth = 0, rightYAxleWidth = 0;


    /**
     * 左边的Y轴
     */
    private YAxle leftAxle = null;


    public NpChartLineView(Context context) {
        super(context);
        init(context, null);
    }

    public NpChartLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NpChartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    //xy轴的画笔
    Paint xyAxlsPaint;
    //底部标签的画笔
    Paint labelPaint;

    private void init(Context context, AttributeSet attrs) {
        if (leftAxle == null) {
            leftAxle = new YAxle();
            leftAxle.insideChart = false;//刻度值在外面
            leftAxle.showRefreshLine = true;//显示参考线
            leftAxle.refreshLineCount = 4;//4条参考线
            leftAxle.refreshValueCount = 4;//4个参考值
            leftAxle.max = 100;
            leftAxle.refreshValuePosition = YAxle.CENTER;
        }
        if (chartBean == null) {
            chartBean = new NpChartLineBean();
            chartBean.setShowXAxis(true);//显示X轴
        }
        noDataTextSize = SizeUtils.sp2px(context, 14);
        chartMargLeft = SizeUtils.dp2px(context, 20);
        chartMargRight = SizeUtils.dp2px(context, 20);
        pointRadius = SizeUtils.dp2px(context, 4);
        unitDp = SizeUtils.dp2px(context, 1);
        clickRangeWidth = SizeUtils.dp2px(context, 20);

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
            chartMargLeft = typedArray.getDimension(R.styleable.NpChartLineView_chartMargLeft, chartMargLeft);
            chartMargRight = typedArray.getDimension(R.styleable.NpChartLineView_chartMargRight, chartMargRight);
            canvasBg = typedArray.getResourceId(R.styleable.NpChartLineView_canvasBg, canvasBg);
            typedArray.recycle();
        }
        enableScroll();
        initPaints();
    }

    private void initPaints() {
        //xy 轴画笔
        xyAxlsPaint = new Paint();
        xyAxlsPaint.setAntiAlias(true);

        //底部标签 画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);

        //线上面的点的画笔
        linePointPaint = new Paint();
        linePointPaint.setStyle(Paint.Style.FILL);
        linePointPaint.setAntiAlias(true);
    }


    public void setChartBean(NpChartLineBean chartBean) {
        this.chartBean = chartBean;
        lastSelectIndex = -1;
        hasClick = false;
        hasTouch = false;
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

    /**
     * 整个图表距离左边/右边的间距 默认为0
     */
    private float chartMargLeft = 0, chartMargRight = 0;


    //无数据时的画笔
    private Paint noDataPaint = null;

    //数据曲线的画笔
    private Paint dataLinePaint = null;

    //线上面的点的画笔
    private Paint linePointPaint = null;

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

    private OnLineSelectListener onLineSelectListener;

    public void setOnLineSelectListener(OnLineSelectListener onLineSelectListener) {
        this.onLineSelectListener = onLineSelectListener;
    }

    public void setPointRadius(float pointRadius) {
        this.pointRadius = pointRadius;
    }

    public void setClickRangeWidth(float clickRangeWidth) {
        this.clickRangeWidth = clickRangeWidth;
    }


    public void setLeftAxle(YAxle leftAxle) {
        this.leftAxle = leftAxle;
        invalidate();
    }

    public YAxle getLeftAxle() {
        return leftAxle;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isEnableOnMeasure()) {
            viewRectF.left = getPaddingLeft();
            viewRectF.top = getPaddingTop();
            viewRectF.right = getMeasuredWidth() - getPaddingRight();
            viewRectF.bottom = getMeasuredHeight() - getPaddingBottom();
            NpViewLog.log("onMeasure 矩形：" + viewRectF.toString());
            if (viewRectF.width() > 0 && viewRectF.height() > 0) {
                bitmap = Bitmap.createBitmap(viewRectF.width(), viewRectF.height(), Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                draw();
            }
        }
    }


    private void draw() {
        if (canDraw()) {
            clearBitmap(canvasBg);

            if (chartBean != null) {
                NpViewLog.log("开始绘制数据了");
                boolean hasData = loadCfg();//判断是否有数据

                drawReferenceLine();//绘制参考线（虚线）重新绘制

                drawDebugRect(viewRectF);
                canvas.save();
                NpViewLog.log("此时的位移是:" + moveOffsetX);
                canvas.translate(-moveOffsetX, 0);
                drawLabels();
                if (hasData) {
                    //线图 才会制渐变曲线
                    if (chartBean.getNpChartLineType() == NpChartLineType.LINE) {
                        drawDataLineGradient();
                        drawDataLines();
                    } else {
                        drawDataPoints();
                    }
                } else {
                    drawNoData();
                }
                canvas.restore();
                drawXYAxleRange();
                drawXYAxis();
            } else {
                drawReferenceLine();//绘制参考线（虚线）
                drawNoData();
            }
        }
    }


    /**
     * 绘制X Y 轴的 范围
     */
    private void drawXYAxleRange() {
        if (leftAxle == null) return;

        leftYAxleWidth = 0;
        NpViewLog.log("坐标轴的刻度值  ： " + (leftAxle.insideChart ? "在图表内" : "在图表外"));
        rightYAxleWidth = 0;
        xyAxlsPaint.setStyle(Paint.Style.FILL);

        int refValueCount = leftAxle.refreshValueCount;//参考值数量
        NpViewLog.log("参考值个数 = " + refValueCount);

        float valueAdd = (leftAxle.max - leftAxle.min) / refValueCount;

        NpViewLog.log("valueAdd = " + valueAdd);

        float height = (viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight) / (refValueCount);

        NpValueFormatter npValueFormatter = leftAxle.valueFormatter;

        float[] xs = new float[refValueCount];
        float[] ys = new float[refValueCount];
        String[] texts = new String[refValueCount];

        int refreshValueTextSize = leftAxle.refreshValueTextSize;//字号
        xyAxlsPaint.setTextSize(refreshValueTextSize);//设置字号

        for (int i = 0; i < refValueCount; i++) {
            float value = leftAxle.max - (valueAdd * i);
            String text = String.format(Locale.US, "%d", Float.valueOf(value).intValue());
            if (npValueFormatter != null) {
                text = npValueFormatter.format(value, i);
            }
            float textWidth = xyAxlsPaint.measureText(text);//字宽

            if (!leftAxle.insideChart && textWidth > leftYAxleWidth) {
                leftYAxleWidth = textWidth;
            }

            float xPosition = chartMargLeft - pointRadius;
            Rect tmpRect = new Rect();
            xyAxlsPaint.getTextBounds(text, 0, text.length(), tmpRect);
            float yPosition = viewRectF.top + topSpaceHeight + height * i;
            NpViewLog.log("leftAxle.refreshValuePosition = " + leftAxle.refreshValuePosition + ", text = " + text + " , tmpRect = " + tmpRect.toString());
            switch (leftAxle.refreshValuePosition) {
                case YAxle.CENTER://跟参考线垂直居中
                    yPosition += refreshValueTextSize / 2.5f;
                    break;
                case YAxle.ABOVE://在参考线上方
                    yPosition -= refreshValueTextSize / 2.5f;
                    break;
                case YAxle.BELOW://在参考线下方
                    yPosition += refreshValueTextSize * 1.15f;
                    break;
            }

            xs[i] = xPosition;
            ys[i] = yPosition;
            texts[i] = text;
        }
        xyAxlsPaint.setColor(canvasBg);
        //左边mask
        RectF maskRectF = new RectF(0, 0, 0, viewRectF.bottom);
        if (leftAxle.insideChart) {
            maskRectF.right = chartMargLeft - pointRadius;
        } else {
            maskRectF.right = chartMargLeft + leftYAxleWidth - pointRadius;
        }
        canvas.drawRect(maskRectF, xyAxlsPaint);
        //右边mask
        maskRectF.left = viewRectF.width() - chartMargRight + pointRadius + unitDp - rightYAxleWidth;
        maskRectF.right = viewRectF.width();
        canvas.drawRect(maskRectF, xyAxlsPaint);

        //绘制参考线
        xyAxlsPaint.setColor(leftAxle.refreshLineColor);//设置颜色


        if (leftAxle.insideChart) {
            xyAxlsPaint.setTextAlign(Paint.Align.LEFT);
        } else {
            xyAxlsPaint.setTextAlign(Paint.Align.RIGHT);
        }
        for (int i = 0; i < refValueCount; i++) {
            canvas.drawText(texts[i], xs[i] + leftYAxleWidth, ys[i], xyAxlsPaint);
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
//            chartBean.setXAxisLineColor(0xFFFF0000);
            paint.setColor(chartBean.getXAxisLineColor());
            float startX = chartMargLeft - pointRadius + leftYAxleWidth;
            float stopX = viewRectF.width() - chartMargRight + pointRadius - rightYAxleWidth;
            float yPos = lineBottom;
            NpViewLog.log("xy矩形:" + viewRectF.toString());
            canvas.drawLine(startX, yPos, stopX, yPos, paint);
        }

        if (leftAxle != null && leftAxle.showYAxis) {
            //绘制Y轴 横向宽度一致，统一一个变量记录宽度
            paint.setColor(leftAxle.YAxisLineColor);
            float xPos = chartMargLeft + leftYAxleWidth - pointRadius;
            float startY = viewRectF.top;
            float stopY = viewRectF.bottom - bottomLabelRangeHeight;
            canvas.drawLine(xPos, startY, xPos, stopY, paint);
        }

    }


    /**
     * 绘制参考线
     */
    private void drawReferenceLine() {
        NpViewLog.log("#。绘制参考线");
        NpViewLog.log("leftAxle = " + leftAxle);
        if (leftAxle == null || !leftAxle.showRefreshLine) return;

        xyAxlsPaint.setColor(leftAxle.refreshLineColor);//设置颜色
        xyAxlsPaint.setPathEffect(new DashPathEffect(new float[]{12, 12}, 0));

        int refLineCount = leftAxle.refreshLineCount;//参考线数量

        NpViewLog.log("参考线个数 = " + refLineCount + " , leftAxle.max = " + leftAxle.max + " ,leftAxle.min = " + leftAxle.min);

        NpViewLog.log("bottomLabelRangeHeight = " + bottomLabelRangeHeight + " , topSpaceHeight = " + topSpaceHeight);
        float height = (viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight) / (refLineCount);


        for (int i = 1; i <= refLineCount; i++) {
            float yPosition = viewRectF.bottom - bottomLabelRangeHeight - height * i;
            canvas.drawLine(viewRectF.left, yPosition, viewRectF.right, yPosition, xyAxlsPaint);
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
        NpViewLog.log("#绘制无数据");
        noDataPaint.setTextAlign(Paint.Align.CENTER);
        noDataPaint.setAntiAlias(true);
        noDataPaint.setTextSize(noDataTextSize);
        noDataPaint.setColor(noDataTextColor);
        String text = TextUtils.isEmpty(noDataText) ? "no Data" : noDataText;
        canvas.drawText(text, viewRectF.centerX(), viewRectF.centerY() - bottomLabelRangeHeight / 2, noDataPaint);
    }


    //绘制标签
    private void drawLabels() {
        labelPaint.setTextSize(labelTextSize);
        List<String> chartLabels = chartBean.getNpLabelList();

        if (maxLabel < 0) {
            NpViewLog.log("没有Label 不绘制");
        } else {
            //如果是多个标签的话

            if (chartBean.isShowLabels()) {
                String labelText = "";
                for (int i = 0; i < maxLabel; i++) {
                    float xPosition = chartMargLeft + leftYAxleWidth + i * labelWidthSpace - pointRadius;

                    labelText = chartLabels.get(i);

//                    if (chartBean.isAdaptationFirstLabel() && i == 0) {
//                        labelPaint.setTextAlign(Paint.Align.RIGHT);
//                    } else if (chartBean.isAdaptationLastLabel() && i == maxLabel - 1) {
//                        labelPaint.setTextAlign(Paint.Align.CENTER);
//                    } else {
//                        labelPaint.setTextAlign(Paint.Align.CENTER);
//                    }

                    float textWidth = labelPaint.measureText(labelText);

                    RectF rectF = new RectF(xPosition, viewRectF.bottom - bottomLabelRangeHeight, xPosition + textWidth, viewRectF.bottom);

                    rectF.right = rectF.left + labelWidthSpace;
//                    labelPaint.setColor(0xFFFF0000);
//                    canvas.drawRect(rectF, labelPaint);

//                    rectF.right = rectF.left + labelPaint.measureText(labelText);
//                    labelPaint.setStyle(Paint.Style.FILL);
//                    labelPaint.setColor(0xFF339033);
//                    canvas.drawRect(rectF, labelPaint);

                    labelPaint.setStyle(Paint.Style.FILL);
                    labelPaint.setColor(chartBean.getLabelTextColor());//设置颜色
                    Paint.FontMetrics fontMetrics = labelPaint.getFontMetrics();
                    float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                    float baseline = rectF.centerY() + distance;
                    canvas.rotate(chartBean.getLabelRotateAngle(), rectF.centerX(), baseline);
                    if (i == 0) {
                        canvas.drawText(labelText, rectF.left, baseline, labelPaint);
                    } else if (i == maxLabel - 1) {
                        canvas.drawText(labelText, rectF.left - textWidth / 1.15f, baseline, labelPaint);
                    } else {
                        canvas.drawText(labelText, rectF.left - textWidth / 4 - pointRadius, baseline, labelPaint);
                    }
                    canvas.rotate(-chartBean.getLabelRotateAngle(), rectF.centerX(), baseline);
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
                NpViewLog.log("没有数据，不绘制数据曲线");
            } else if (maxLabel == 1) {
                NpViewLog.log("只有一个数据点，不绘制数据曲线");
                for (NpChartLineDataBean npChartLineDataBean : lineDataBeanList) {
                    List<NpLineEntry> npLineEntryList = npChartLineDataBean.getNpLineEntryList();
                    allTmpRectList.clear();

                    if (npLineEntryList.size() != 1) {
                        NpViewLog.log("数据有误");
                    } else {
                        NpViewLog.log("数据符合规范");
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        float x = chartMargLeft;
                        float y = getDataPointYPosition(npLineEntryList.get(0));

                        RectF rectF = new RectF();
                        rectF.left = x - clickRangeWidth / 2;
                        rectF.right = rectF.left + clickRangeWidth;
                        rectF.top = y - clickRangeWidth / 2;
                        rectF.bottom = rectF.top + clickRangeWidth;
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
                    if (onLineSelectListener != null) {
                        onLineSelectListener.onSelectLine(lineDataBeanList, 0);
                    }
                }
            } else {
                NpViewLog.log("多个数据点，可以绘制数据曲线 hasTouch:" + hasTouch);

                allTmpRectList.clear();

                int count = 0;

                NpViewLog.log("lineDataBeanList.size() = " + lineDataBeanList.size());

                if (chartBean.getNpChartLineType() == NpChartLineType.LINE) {
                    drawSelectLine(lineDataBeanList);
                }

                for (NpChartLineDataBean npChartLineDataBean : lineDataBeanList) {
                    int tempIndex = 0;
                    List<NpLineEntry> npLineEntries = npChartLineDataBean.getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 1) {
                        dataLinePaint.setStrokeWidth(npChartLineDataBean.getLineThickness());
                        if (npChartLineDataBean.isShowShadow()) {
                            float shadowRadius = npChartLineDataBean.getShadowRadius();
                            float shadowX = npChartLineDataBean.getShadowX();
                            float shadowY = npChartLineDataBean.getShadowY();
                            dataLinePaint.setShadowLayer(shadowRadius, shadowX, shadowY, npChartLineDataBean.getShadowColor());
                        }
                        PathData pathData = null;

                        switch (chartBean.getLineType()) {
                            case Bezier://贝塞尔曲线
                                pathData = getPathBezier(npLineEntries, false);
                                break;
                            case Polyline://折线
                                pathData = getPathPolyline(npLineEntries, false);
                                break;
                        }
                        if (pathData != null) {
                            dataLinePaint.setColor(npChartLineDataBean.getColor());
                            canvas.drawPath(pathData.getPath(), dataLinePaint);
                        } else {
                            NpViewLog.log("unKnown chartBean.getLineType()");
                        }
                    }
                    if (count < lineDataBeanList.size()) {
                        for (NpLineEntry npLineEntry : npLineEntries) {
                            RectF rectF = new RectF();
                            rectF.left = chartMargLeft + leftYAxleWidth + labelWidthSpace * tempIndex - clickRangeWidth / 2;
                            rectF.right = rectF.left + clickRangeWidth;
                            rectF.top = getDataPointYPosition(npLineEntry) - clickRangeWidth / 2;
                            rectF.bottom = rectF.top + clickRangeWidth;
                            allTmpRectList.add(rectF);
                            if (npLineEntry.isShowPoint()) {
                                NpViewLog.log("绘制数据点 = " + npLineEntry.getPointColor());
                                linePointPaint.setColor(npLineEntry.getPointColor());
                                canvas.drawCircle(rectF.centerX(), rectF.centerY(), npLineEntry.getPointRadius(), linePointPaint);
                            }
                            tempIndex++;
                        }
                    }
                    count++;
                }
                drawSelect(lineDataBeanList);
            }
        } else {
            NpViewLog.log("chartBean.getNpChartLineDataBeans()=null !!!!");
        }
    }

    private void drawSelectLine(List<NpChartLineDataBean> lineDataBeanList) {
        for (int i = 0; i < lineDataBeanList.size(); i++) {
            List<NpLineEntry> npLineEntries = lineDataBeanList.get(i).getNpLineEntryList();
            if (npLineEntries != null && npLineEntries.size() > 0) {

                NpViewLog.log("lastSelectIndex:" + lastSelectIndex);
                if (lastSelectIndex != -1) {
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    float x = chartMargLeft + leftYAxleWidth + labelWidthSpace * lastSelectIndex;
                    NpLineEntry npPointEntry = npLineEntries.get(lastSelectIndex);
                    if (npPointEntry.isClick()) {
                        paint.setColor(chartBean.getSelectLineColor());
                        paint.setStrokeWidth(chartBean.getSelectLineWidth());
                        canvas.drawLine(x, viewRectF.top + topSpaceHeight, x, viewRectF.bottom - bottomLabelRangeHeight, paint);
                    }
                }
            }
        }
    }


    private void drawSelect(List<NpChartLineDataBean> lineDataBeanList) {
        for (int i = 0; i < lineDataBeanList.size(); i++) {
            List<NpLineEntry> npLineEntries = lineDataBeanList.get(i).getNpLineEntryList();
            if (npLineEntries != null && npLineEntries.size() > 0) {

//                        if (!hasTouch) {
//                            if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST) {
//                                lastSelectIndex = 0;
//                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_LAST) {
//                                lastSelectIndex = npLineEntries.size() - 1;
//                                if (!hasTouch) {
//                                    if ((lastSelectIndex + 1) * labelWidthSpace > viewRectF.width() + chartMargLeft) {
//                                        moveOffsetX = -((lastSelectIndex + 1) * labelWidthSpace) + viewRectF.width() + chartMargLeft;
//                                        lastX = moveOffsetX;
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
//                                    if ((lastSelectIndex + 1) * labelWidthSpace > viewRectF.width() + chartMargLeft) {
//                                        moveOffsetX = -((lastSelectIndex) * labelWidthSpace) + viewRectF.width() + chartMargLeft;
//                                        lastX = moveOffsetX;
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
//                                    if ((lastSelectIndex + 1) * labelWidthSpace >= viewRectF.width() - chartMargLeft - chartMargRight) {
//                                        moveOffsetX = -((lastSelectIndex + 1) * labelWidthSpace) + viewRectF.width() - chartMargLeft - chartMargRight;
//                                        lastX = moveOffsetX;
//                                    }
//                                }
//                            }
//                        }
                NpViewLog.log("lastSelectIndex:" + lastSelectIndex);
                if (lastSelectIndex != -1) {
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);

                    float x = chartMargLeft + leftYAxleWidth + labelWidthSpace * lastSelectIndex;
                    float y = getDataPointYPosition(npLineEntries.get(lastSelectIndex));

                    switch (chartBean.getNpSelectStyle()) {
                        //空心圆
                        case HOLLOW_CIRCLE: {
                            paint.setStyle(Paint.Style.STROKE);
                            paint.setColor(chartBean.getSelectHollowCircleColor());
                            paint.setStrokeWidth(chartBean.getSelectHollowCircleWidth());
                            canvas.drawCircle(x, y, chartBean.getSelectHollowCircleR(), paint);
                        }
                        break;

                        //实心圆
                        case FILLED_CIRCLE: {
                            paint.setStyle(Paint.Style.FILL);
                            paint.setColor(chartBean.getSelectFilledCircleColor());
                            canvas.drawCircle(x, y, chartBean.getSelectFilledCircleR(), paint);
                        }
                        break;
//                        //竖线
//                        case VERTICAL_LINE: {
//                            NpLineEntry npPointEntry = npLineEntries.get(lastSelectIndex);
//                            if (npPointEntry.isClick()) {
//                                paint.setColor(chartBean.getSelectLineColor());
//                                paint.setStrokeWidth(chartBean.getSelectLineWidth());
//                                canvas.drawLine(x, viewRectF.top + topSpaceHeight, x, viewRectF.bottom - bottomLabelRangeHeight, paint);
//                            }
//                        }
//                        break;

                        //竖线+空心圆
                        case VERTICAL_LINE_AND_HOLLOW_CIRCLE: {
                            NpLineEntry npPointEntry = npLineEntries.get(lastSelectIndex);

                            if (npPointEntry.isClick()) {
                                paint.setColor(chartBean.getSelectLineColor());
                                paint.setStrokeWidth(chartBean.getSelectLineWidth());
                                canvas.drawLine(x, viewRectF.top + topSpaceHeight, x, viewRectF.bottom - bottomLabelRangeHeight, paint);
                            }

                            if (npPointEntry.isDraw()) {
                                paint.setStyle(Paint.Style.STROKE);
                                paint.setColor(chartBean.getSelectHollowCircleColor());
                                paint.setStrokeWidth(chartBean.getSelectHollowCircleWidth());
                                canvas.drawCircle(x, y, chartBean.getSelectHollowCircleR(), paint);
                            }
                        }
                        break;

                        //竖线+实心圆
                        case VERTICAL_LINE_AND_FILLED_CIRCLE: {
                            NpLineEntry npPointEntry = npLineEntries.get(lastSelectIndex);

                            if (npPointEntry.isClick()) {
                                paint.setColor(chartBean.getSelectLineColor());
                                paint.setStrokeWidth(chartBean.getSelectLineWidth());
                                canvas.drawLine(x, viewRectF.top + topSpaceHeight, x, viewRectF.bottom - bottomLabelRangeHeight, paint);
                            }

                            if (npPointEntry.isDraw()) {
                                paint.setStyle(Paint.Style.FILL);
                                paint.setColor(chartBean.getSelectFilledCircleColor());
                                canvas.drawCircle(x, y, chartBean.getSelectFilledCircleR(), paint);
                            }
                        }
                        break;
                    }
                }
            }
        }
        if (onLineSelectListener != null && lastSelectIndex != -1) {
            onLineSelectListener.onSelectLine(lineDataBeanList, lastSelectIndex);
        }
    }


    /**
     * 绘制数据曲线
     */
    private void drawDataPoints() {
        List<NpChartLineDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {
            if (maxLabel <= 0) {
                NpViewLog.log("没有数据，不绘制数据曲线");
            } else {
                NpViewLog.log("多个数据点，可以绘制数据曲线 hasTouch:" + hasTouch);

                allTmpRectList.clear();

                Paint pointPaint = new Paint();
                pointPaint.setAntiAlias(true);

                int count = 0;

                NpViewLog.log("lineDataBeanList.size() = " + lineDataBeanList.size());


                if (chartBean.getNpSelectStyle() == NpSelectStyle.VERTICAL_LINE) {
                    drawSelectLine(lineDataBeanList);
                }

                for (NpChartLineDataBean npChartLineDataBean : lineDataBeanList) {
                    int tempIndex = 0;
                    List<NpLineEntry> npLineEntries = npChartLineDataBean.getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 1) {

                        for (int i = 0; i < npLineEntries.size(); i++) {
                            NpLineEntry npPointEntry = npLineEntries.get(i);

                            if (!npPointEntry.isDraw()) {
                                continue;
                            }

                            float x = chartMargLeft + leftYAxleWidth + labelWidthSpace * i;
                            float y = getDataPointYPosition(npLineEntries.get(i));

                            NpViewLog.log("x = " + x + " , y = " + y);


                            pointPaint.setColor(npChartLineDataBean.getColor());
                            pointPaint.setStrokeWidth(unitDp);
                            canvas.drawCircle(x, y, pointRadius, pointPaint);
//                            pointPaint.setStyle(Paint.Style.STROKE);
//                            pointPaint.setColor(npChartLineDataBean.getColor());
//                            canvas.drawCircle(x, y, pointRadius, pointPaint);
                        }

                    }
                    if (count == 0) {
                        for (NpLineEntry npLineEntry : npLineEntries) {
                            RectF rectF = new RectF();
                            rectF.left = chartMargLeft + leftYAxleWidth + labelWidthSpace * tempIndex - clickRangeWidth / 2;
                            rectF.right = rectF.left + clickRangeWidth;
                            rectF.top = getDataPointYPosition(npLineEntry) - clickRangeWidth / 2;
                            rectF.bottom = rectF.top + clickRangeWidth;
                            allTmpRectList.add(rectF);
                            tempIndex++;
                        }
                    }
                    count++;
                }
                drawSelect(lineDataBeanList);
            }
        } else {
            NpViewLog.log("chartBean.getNpChartLineDataBeans()=null !!!!");
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

                        PathData pathData = null;
                        switch (chartBean.getLineType()) {
                            case Bezier://贝塞尔曲线
                                pathData = getPathBezier(npLineEntries, true);
                                break;
                            case Polyline://折线
                                pathData = getPathPolyline(npLineEntries, true);
                                break;
                        }
                        if (pathData != null) {
                            LinearGradient lg = new LinearGradient(0, viewRectF.top, 0, viewRectF.bottom, npChartLineDataBean.getStartColor(), npChartLineDataBean.getEndColor(), Shader.TileMode.CLAMP);
                            //mask画笔设置渐变效果
                            dataLineGradientPaint.setShader(lg);
                            canvas.drawPath(pathData.getPath(), dataLineGradientPaint);
                        } else {
                            NpViewLog.log("unKnown chartBean.getLineType()");
                        }
//                        float prenent = 1 - pathData.getMaxValue() / chartBean.getMaxY();
//                        NpViewLog.log("prenent比例=====>" + prenent);
                    }
                }
            } else {
                NpViewLog.log("没有标签或者标签数量不够，不绘制渐变曲线");
            }
        } else {
            NpViewLog.log("chartBean.getNpChartLineDataBeans()=null !!!!");
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

        NpViewLog.log("topSpaceHeight = " + topSpaceHeight);

        float thisTotalHeight = viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight;
        float min = chartBean.getMinY();
        float max = chartBean.getMaxY();

        float tmpValue = npLineEntry.getValue();
        NpViewLog.log("tmpValue1 = " + tmpValue + " , min = " + min + " ,max = " + max);

        if (max == min) {
            max = min * 1.05f;
        }
        if (tmpValue <= min) {
            tmpValue = min;
        }
        if (tmpValue > max) {
            tmpValue = max;
        }

        float precent1 = (tmpValue - min) / (max - min);

        return (thisTotalHeight * (1.0f - precent1)) + topSpaceHeight;
    }

    /**
     * 收集曲线路径（贝塞尔曲线）
     *
     * @param lineEntryList
     * @param isClosed
     * @return
     */
    private PathData getPathBezier(List<NpLineEntry> lineEntryList, boolean isClosed) {
        PathData pathData = new PathData();
        Path path = new Path();
        float thisTotalHeight = viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight;
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

        path.moveTo(chartMargLeft + leftYAxleWidth, (thisTotalHeight * (1.0f - precent1)) + topSpaceHeight);

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

            float x1 = i * xDisAdd + chartMargLeft + leftYAxleWidth;
            float y1 = (thisTotalHeight * (1.0f - precent1)) + getPaddingTop() + topSpaceHeight;


            tmpValue2 = lineEntryList.get(i + 1).getValue();
            if (tmpValue2 <= min) {
                tmpValue2 = min;
            }
            if (tmpValue2 >= max) {
                tmpValue2 = max;
            }

            precent2 = (tmpValue2 - min) / (max - min);

            float x2 = (i + 1) * xDisAdd + chartMargLeft + leftYAxleWidth;
            float y2 = (thisTotalHeight * (1.0f - precent2)) + getPaddingTop() + topSpaceHeight;

            PointF startp = new PointF(x1, y1);
            PointF endp = new PointF(x2, y2);
            float wt = (startp.x + endp.x) / 2;
            PointF p3 = new PointF(wt, startp.y);
            PointF p4 = new PointF(wt, endp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
        }

        if (isClosed) {
            path.lineTo((dataLen - 1) * xDisAdd + chartMargLeft + leftYAxleWidth, (thisTotalHeight * (1.0f - precent2)));
            path.lineTo((dataLen - 1) * xDisAdd + chartMargLeft + leftYAxleWidth, thisTotalHeight + topSpaceHeight);
            path.lineTo(viewRectF.left + chartMargLeft + leftYAxleWidth, thisTotalHeight + topSpaceHeight);
        }

        pathData.setPath(path);
        return pathData;
    }


    /**
     * 收集曲线路径（折线-直线）
     *
     * @param lineEntryList
     * @param isClosed
     * @return
     */
    private PathData getPathPolyline(List<NpLineEntry> lineEntryList, boolean isClosed) {

        PathData pathData = new PathData();
        Path path = new Path();
        float thisTotalHeight = viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight;
        float xDisAdd = labelWidthSpace;

        float min = chartBean.getMinY();
        float max = chartBean.getMaxY();
        int dataLen = lineEntryList.size();

        if (max == min) {
            max = min * 1.05f;
        }

        NpViewLog.log("min = " + min + " , max = " + max);

        float precent1 = 0, tmpValue1 = 0;

        tmpValue1 = lineEntryList.get(0).getValue();
        if (tmpValue1 <= min) {
            tmpValue1 = min;
        }

        precent1 = (tmpValue1 - min) / (max - min);

        NpViewLog.log("chartMargLeft = " + chartMargLeft + " , leftYAxleWidth = " + leftYAxleWidth);

        path.moveTo(chartMargLeft + leftYAxleWidth, (thisTotalHeight * (1.0f - precent1)) + topSpaceHeight);

        List<Float> tmpList = new ArrayList<>();
        for (NpLineEntry npLineEntry : lineEntryList) {
            tmpList.add(npLineEntry.getValue());
        }
        if (tmpList.size() > 0) {
            pathData.setMaxValue(Collections.max(tmpList));
        }
        for (int i = 1; i < dataLen; i++) {

            tmpValue1 = lineEntryList.get(i).getValue();
            if (tmpValue1 <= min) {
                tmpValue1 = min;
            }
            if (tmpValue1 >= max) {
                tmpValue1 = max;
            }

            precent1 = (tmpValue1 - min) / (max - min);

            float x1 = i * xDisAdd + chartMargLeft + leftYAxleWidth;
            float y1 = (thisTotalHeight * (1.0f - precent1)) + getPaddingTop() + topSpaceHeight;

            path.lineTo(x1, y1);
        }

        if (isClosed) {
            path.lineTo((dataLen - 1) * xDisAdd + chartMargLeft + leftYAxleWidth, thisTotalHeight + topSpaceHeight);
            path.lineTo(chartMargLeft + leftYAxleWidth, thisTotalHeight + topSpaceHeight);
        }

        pathData.setPath(path);
        return pathData;
    }


    @Override
    public void invalidate() {
        NpViewLog.log("调用#invalidate");
        draw();
        super.invalidate();
    }


    private float downActionX;
    private float moveActionX;
    private int mLastX, mLastY;

    private boolean isDisallowIntercept;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnableTouch()) {
            return false;
        }
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        hasTouch = true;

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null) mScroller.forceFinished(true);//强制停止
                isMoved = false;
                if (!isDisallowIntercept) {
                    isDisallowIntercept = true;
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                downActionX = event.getX();
                NpViewLog.log("fuck" + downActionX + "///");
                for (int i = 0; i < allTmpRectList.size(); i++) {
                    RectF rangeRect = allTmpRectList.get(i);
                    float xPosition = event.getX() - (-moveOffsetX);
                    if (rangeRect.left <= xPosition && rangeRect.right >= xPosition) {
                        lastSelectIndex = i;
                        hasClick = true;
                        postInvalidateDelayed(20);
                        NpViewLog.log("lastSelectIndex===>" + lastSelectIndex);
                        break;
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:

                //如果是等宽显示的话,切不允许滑动
                if ((chartBean != null && chartBean.getShowDataType() == NpShowDataType.Equal) && !chartBean.isEqualAllowSliding()) {
                    float downX = event.getX();
                    NpViewLog.log("fuck" + downX + "///");
                    for (int i = 0; i < allTmpRectList.size(); i++) {
                        RectF rangeRect = allTmpRectList.get(i);
                        float xPosition = event.getX() - moveOffsetX;

                        if (rangeRect.left <= xPosition && rangeRect.right >= xPosition) {
                            lastSelectIndex = i;
                            hasClick = true;
                            postInvalidateDelayed(20);
                            NpViewLog.log("lastSelectIndex===>" + lastSelectIndex);
                            break;
                        }
                    }
                } else {
//                    moveActionX = event.getX();
//                    //滑动时候,通过假设的滑动距离,做超出左边界以及右边界的限制。
//                    if (Math.abs(moveActionX - downActionX) > SizeUtils.dp2px(getContext(), 20)) {
//                        moveOffsetX = moveActionX - downActionX + lastX;
//                        if (moveOffsetX >= 0) {
//                            moveOffsetX = 0;
//                        } else {
//                            if ((maxLabel - 1) * labelWidthSpace <= viewRectF.width() - chartMargLeft - chartMargRight) {
//                                NpViewLog.log("不能左滑动？");
//                                moveOffsetX = 0;
//                            } else if (moveOffsetX <= getWhichScaleMovex()) {
//                                moveOffsetX = getWhichScaleMovex();
//                            }
//                        }
//                    }

                    final int dx = x - mLastX;
                    // 判断是否已经滑动
                    if (!isMoved) {
                        final int dy = y - mLastY;
                        // 滑动的触发条件：水平滑动大于垂直滑动；滑动距离大于阈值
                        if (Math.abs(dx) < Math.abs(dy) || Math.abs(x - downActionX) < TOUCH_SLOP) {
                            break;
                        }
                        isMoved = true;
                    }
                    scrollOffsetX = lastScrollOffsetX - dx;

                    //如果不允许在滑动的时候 出边界 就要提前限制
                    if (!isAllowSlideOutInMove) {
                        if (scrollOffsetX <= minScrollX) {//限制左边界
                            scrollOffsetX = minScrollX;
                        }
                        if (scrollOffsetX > maxScrollX) {//限制右边界
                            scrollOffsetX = maxScrollX;
                        }
                    }
                    moveOffsetX = scrollOffsetX;
                    lastScrollOffsetX = moveOffsetX;
                    invalidate();

                }
                break;
            case MotionEvent.ACTION_UP:
                isDisallowIntercept = false;
                //手指抬起时候制造惯性滑动
                lastScrollOffsetX = moveOffsetX;
                // 计算速度：使用1000ms为单位
                mVelocityTracker.computeCurrentVelocity(1000, MAX_FLING_VELOCITY);
                // 获取速度。速度有方向性，水平方向：左滑为负，右滑为正
                int xVelocity = (int) mVelocityTracker.getXVelocity();

                NpViewLog.log("xVelocity = " + xVelocity);

                if (Math.abs(xVelocity) >= MIN_FLING_VELOCITY) {
                    NpViewLog.log("需要快速滑动 , moveOffsetX = " + moveOffsetX);
                    // 速度具有方向性，需要取反
                    mScroller.fling(0, 0, -xVelocity, 0, -1080 * 8, 1080 * 8, 0, 0);
                    invalidate();
                } else {
                    NpViewLog.log("需要缓慢滑动");
                    mScroller.fling(0, 0, -xVelocity / 2, 0, -1080 * 5, 1080 * 2, 0, 0);
                    invalidate();
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                isDisallowIntercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }


    private boolean loadCfg() {
        NpViewLog.log("#。加载配置/读取数据");
        labelTextSize = chartBean.getLabelTextSize();
        bottomLabelRangeHeight = chartBean.getBottomHeight();
        topSpaceHeight = chartBean.getTopHeight();
        //最多要显示的label个数
        maxLabel = getMaxLabelCount(chartBean.getNpLabelList());
        labelWidthSpace = chartBean.getLabelSpaceWidth();
        if (chartBean.getShowDataType() == NpShowDataType.Equal) {
            maxScrollX = 0;
            if (maxLabel > 1) {
                labelWidthSpace = (viewRectF.width() - chartMargLeft - chartMargRight - leftYAxleWidth - rightYAxleWidth) / (maxLabel - 1);
                NpViewLog.log("满足平分的场景？" + maxLabel + "///" + labelWidthSpace);
            }
        } else if (chartBean.getShowDataType() == NpShowDataType.Slide) {
            maxScrollX = maxLabel * labelWidthSpace - viewRectF.width() + rightYAxleWidth + chartMargRight + pointRadius;
            if (maxScrollX < 0) {
                maxScrollX = 0;
            }
        }
        calculationScroll();
        int dataSum = 0;
        if (chartBean.getNpChartLineDataBeans() != null && chartBean.getNpChartLineDataBeans().size() > 0) {
            for (NpChartLineDataBean chartLineDataBean : chartBean.getNpChartLineDataBeans()) {
                for (NpLineEntry lineEntry : chartLineDataBean.getNpLineEntryList()) {
                    dataSum += lineEntry.getValue();
                }
            }
        }
        NpViewLog.log("dataSum = " + dataSum);
        if (dataSum <= 0) {
            moveOffsetX = 0;
            scrollOffsetX = 0;
            lastScrollOffsetX = 0;
        }
        return dataSum > 0;
    }


    /**
     * 计算要滚动的距离
     */
    private void calculationScroll() {
        if (hasTouch) return;
        List<NpChartLineDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {
            if (maxLabel >= 2) {
                NpViewLog.log("多个数据点");
                for (int i = 0; i < lineDataBeanList.size(); i++) {
                    List<NpLineEntry> npLineEntries = lineDataBeanList.get(i).getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 0) {
                        NpViewLog.log("hasClick:" + hasClick);
                        NpViewLog.log("getNpSelectMode:" + chartBean.getNpSelectMode());
                        if (!hasClick) {
                            if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST) {
                                lastSelectIndex = 0;
                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_LAST) {
                                lastSelectIndex = npLineEntries.size() - 1;
                                if ((lastSelectIndex + 1) * labelWidthSpace > viewRectF.width() + chartMargLeft) {
                                    scrollOffsetX = ((lastSelectIndex + 1) * labelWidthSpace) - (viewRectF.width());
                                    moveOffsetX = scrollOffsetX;
                                    lastScrollOffsetX = moveOffsetX;
                                }

                                NpViewLog.log("moveOffsetX = " + moveOffsetX);
                                NpViewLog.log("lastScrollOffsetX = " + lastScrollOffsetX);

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
                                    if ((lastSelectIndex) * labelWidthSpace > viewRectF.width() + chartMargLeft) {
                                        moveOffsetX = -((lastSelectIndex) * labelWidthSpace) + viewRectF.width() + chartMargLeft;
                                        lastScrollOffsetX = moveOffsetX;
                                    }
                                }
                            } else if (chartBean.getNpSelectMode() == NpSelectMode.SELECT_LAST_NOT_NULL) {
                                for (int t = npLineEntries.size() - 1; t >= 0; t--) {
                                    if (npLineEntries.get(t).getValue() > chartBean.getMinY()) {
                                        lastSelectIndex = t;
                                        break;
                                    }
                                }
                                NpViewLog.log("SELECT_LAST_NOT_NULL:是到这里吗？//" + hasTouch);
                                if (!hasTouch) {
                                    if ((lastSelectIndex) * labelWidthSpace >= viewRectF.width() - chartMargLeft - chartMargRight) {
                                        moveOffsetX = -((lastSelectIndex) * labelWidthSpace) + viewRectF.width() - chartMargLeft - chartMargRight;
                                        lastScrollOffsetX = moveOffsetX;
                                        NpViewLog.log("lastSelectIndex:是到这里吗？//" + lastSelectIndex);
                                    }
                                }
                            }
                        }
                    }
                }

                if (onLineSelectListener != null && lastSelectIndex != -1) {
                    onLineSelectListener.onSelectLine(lineDataBeanList, lastSelectIndex);
                }
            }
        } else {
            moveActionX = 0;
            scrollOffsetX = 0;
            lastScrollOffsetX = 0;
        }
    }


    private float getWhichScaleMovex() {
        float result = viewRectF.width() - labelWidthSpace * (maxLabel - 1) - chartMargLeft - chartMargRight;
        NpViewLog.log("result:" + result);
        return result;
    }


    public interface OnLineSelectListener {
        void onSelectLine(List<NpChartLineDataBean> lineDataBeans, int index);
    }

}

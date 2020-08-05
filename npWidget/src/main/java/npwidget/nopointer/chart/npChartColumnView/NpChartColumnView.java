package npwidget.nopointer.chart.npChartColumnView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;
import npwidget.nopointer.log.ViewLog;
import npwidget.nopointer.utils.SizeUtils;

/**
 * 柱状统计图
 * <p>
 * 2.高度问题
 * 3.一条数据的绘制
 */
public class NpChartColumnView extends BaseView {


    //数据源
    private NpChartColumnBean chartColumnBean;

    //可绘制的画布范围
    private Rect viewRectF = new Rect();

    //每个柱子分配的宽度
    private float columnWidth = 0;

    //底部文字的高度
    private float bottomLabelRangeHeight = 0;

    //底部文字的大小
    private float labelTextSize = 0;

    private OnColumnSelectListener onColumnSelectListener = null;

    //上次选择的柱子索引
    private int lastSelectIndex = -1;

    //最大值
    private float columnMaxValue = 0;
    private List<RectF> allTmpRectList = new ArrayList<>();

    private List<ColumnData> columnDataList = new ArrayList<>();

    //绘制没有数据的时候的文字大小
    private float getNoDataTextSize = 0;

    //绘制没有数据的时候的文字
    private String noDataText = "no data ~ ";

    //无数据是文本的颜色
    private int noDataTextColor = 0xFF888888;

    //可以点击的宽度范围
    private float clickRangeWidth = 0;
    //是否是已经点击过了
    private boolean hasClick = false;

    public void setClickRangeWidth(float clickRangeWidth) {
        this.clickRangeWidth = clickRangeWidth;
    }

    public void setNoDataTextColor(int noDataTextColor) {
        this.noDataTextColor = noDataTextColor;
    }

    public NpChartColumnView(Context context) {
        super(context);
        init(context);
    }

    public NpChartColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NpChartColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        getNoDataTextSize = SizeUtils.sp2px(context, 14);
        clickRangeWidth = SizeUtils.dp2px(context, 20);
    }

    public NpChartColumnBean getChartColumnBean() {
        return chartColumnBean;
    }


    public void setGetNoDataTextSize(float getNoDataTextSize) {
        this.getNoDataTextSize = getNoDataTextSize;
    }

    public void setNoDataText(String noDataText) {
        this.noDataText = noDataText;
    }

    public void setChartColumnBean(NpChartColumnBean chartColumnBean) {
        this.chartColumnBean = chartColumnBean;
        lastSelectIndex = -1;
        hasClick = false;
    }

    public void setOnColumnSelectListener(OnColumnSelectListener onColumnSelectListener) {
        this.onColumnSelectListener = onColumnSelectListener;
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
            if (chartColumnBean != null) {
                loadCfg();
                drawXYAxis();
                drawLabels();
                if (chartColumnBean.getNpChartColumnDataBeans() != null && chartColumnBean.getNpChartColumnDataBeans().size() > 0) {
                    int dataSum = 0;

                    for (NpChartColumnDataBean chartColumnDataBean : chartColumnBean.getNpChartColumnDataBeans()) {
                        for (NpColumnEntry columnEntry : chartColumnDataBean.getNpColumnEntryList()) {
                            dataSum += columnEntry.getValue();
                        }
                    }
                    if (dataSum <= 0) {
                        drawNoData();
                    } else {
                        drawDataColumns();
                        if (!hasClick) {
                            if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST) {
                                lastSelectIndex = 0;
                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_LAST) {
                                lastSelectIndex = chartColumnBean.getNpChartColumnDataBeans().size() - 1;
                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_MIN) {
                                for (int i = 0; i < allColumnDataSum.size(); i++) {
                                    if (allColumnDataSum.get(i) == Collections.min(allColumnDataSum)) {
                                        lastSelectIndex = i;
                                        break;
                                    }
                                }
                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_MAX) {
                                for (int i = 0; i < allColumnDataSum.size(); i++) {
                                    if (allColumnDataSum.get(i) == Collections.max(allColumnDataSum)) {
                                        lastSelectIndex = i;
                                        break;
                                    }
                                }
                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST_NOT_NULL) {
                                for (int i = 0; i < allColumnDataSum.size(); i++) {
                                    if (allColumnDataSum.get(i) > chartColumnBean.getMinY()) {
                                        lastSelectIndex = i;
                                        break;
                                    }
                                }
                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_LAST_NOT_NULL) {
                                for (int i = allColumnDataSum.size() - 1; i >= 0; i--) {
                                    if (allColumnDataSum.get(i) > chartColumnBean.getMinY()) {
                                        lastSelectIndex = i;
                                        break;
                                    }
                                }
                            }

                        }
                        drawSelectColumn();
                    }
                } else {
                    drawNoData();
                }
            } else {
                drawNoData();
            }
        }
    }


    //绘制标签
    private void drawLabels() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(chartColumnBean.getLabelTextSize());
        List<String> chartLabels = chartColumnBean.getNpLabelList();
        //最多要显示的label个数
        int labelCount = chartLabels.size();
        if (labelCount < 0) {
            ViewLog.e("没有Label 不绘制");
            return;
        } else {
            //横向的柱子之间的间距
            float xDisAdd = chartColumnBean.getColumnSpaceWidth();

            if (chartColumnBean.getShowDataType() == NpShowDataType.Equal) {
                ViewLog.e("平分宽度");
                //可以被平分的宽度
                float totalWidth = (viewRectF.width() - chartColumnBean.getMarginLeft() - chartColumnBean.getMarginRight());
                xDisAdd = (totalWidth - labelCount * columnWidth) / (labelCount - 1);
            }

            ViewLog.e("xDisAdd====>" + xDisAdd);

            String label = "";

            paint.setColor(chartColumnBean.getLabelTextColor());
            for (int i = 0; i < labelCount; i++) {
                paint.setTextSize(labelTextSize);
                label = chartColumnBean.getNpLabelList().get(i);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setAlpha(0xFF);
                //计算柱子的中心点
                float xColumnCenterX = i * xDisAdd + viewRectF.left + chartColumnBean.getMarginLeft() + columnWidth * i + columnWidth / 2.0f;

//                canvas.drawText(label, xColumnCenterX, viewRectF.bottom - labelTextSize / 2, paint);

                paint.setTextAlign(Paint.Align.CENTER);
                RectF rectF = new RectF(xColumnCenterX, viewRectF.bottom - bottomLabelRangeHeight, xColumnCenterX, viewRectF.bottom);
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                float baseline = rectF.centerY() + distance;
                canvas.drawText(label, rectF.centerX(), baseline, paint);
            }
        }
    }


    //绘制XY轴
    private void drawXYAxis() {
        if (chartColumnBean == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (chartColumnBean.isShowXAxis()) {
            //绘制X轴 纵向高度一致，统一一个变量记录底边距
            float lineBottom = viewRectF.bottom - bottomLabelRangeHeight;
            paint.setColor(chartColumnBean.getXAxisLineColor());
            canvas.drawLine(viewRectF.left, lineBottom, viewRectF.right, lineBottom, paint);
        }

        if (chartColumnBean.isShowYAxis()) {
            //绘制Y轴 横向宽度一致，统一一个变量记录左边距
            float lineLeft = viewRectF.left;
            paint.setColor(chartColumnBean.getYAxisLineColor());
            canvas.drawLine(lineLeft, viewRectF.top, lineLeft, viewRectF.bottom - bottomLabelRangeHeight, paint);
        }

    }


    //绘制没有数据的时候的显示样子
    private void drawNoData() {
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(getNoDataTextSize);
        String text = TextUtils.isEmpty(noDataText) ? "no Data" : noDataText;
        paint.setColor(noDataTextColor);
        canvas.drawText(text, viewRectF.centerX(), viewRectF.centerY() - bottomLabelRangeHeight / 2, paint);
    }


    private void drawSelectColumn() {

        if (lastSelectIndex == -1) return;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(chartColumnBean.getSelectColumnColor());
        ColumnData pathData = columnDataList.get(lastSelectIndex);
        pathData.clickRange.left = pathData.clickRange.centerX() - chartColumnBean.getColumnWidth() / 2;
        pathData.clickRange.right = pathData.clickRange.left + chartColumnBean.getColumnWidth();
        canvas.drawRect(pathData.clickRange, paint);
        if (onColumnSelectListener != null) {
            onColumnSelectListener.onSelectColumn(chartColumnBean.getNpChartColumnDataBeans().get(lastSelectIndex), lastSelectIndex);
        }

    }

    /**
     * 绘制数据柱子
     */
    private void drawDataColumns() {
        List<NpChartColumnDataBean> chartColumnDataBeans = chartColumnBean.getNpChartColumnDataBeans();
        if (chartColumnDataBeans != null && chartColumnDataBeans.size() > 0) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);

            float leftMargin = viewRectF.left;

            columnWidth = chartColumnBean.getColumnWidth();

            //横向的柱子之间的间距
            float xDisAdd = chartColumnBean.getColumnSpaceWidth();

            if (chartColumnBean.getShowDataType() == NpShowDataType.Equal) {
                ViewLog.e("平分宽度");
                // 如果是平分的话，就需要重新计算间距(公式等于 （总宽度-柱子个数*柱子宽度）/(柱子个数-1))
                //柱子个数
                float columnCount = chartColumnDataBeans.size();
                //可以被平分的宽度
                float totalWidth = (viewRectF.width() - chartColumnBean.getMarginLeft() - chartColumnBean.getMarginRight());
                xDisAdd = (totalWidth - columnCount * columnWidth) / (columnCount - 1);
            }

            int tmpI = 0;
            ViewLog.e("xDisAdd====>" + xDisAdd);

            String label = "";
            allTmpRectList.clear();
            columnDataList.clear();
            for (NpChartColumnDataBean columnDataBean : chartColumnDataBeans) {
                List<NpColumnEntry> npColumnEntries = columnDataBean.getNpColumnEntryList();
                if (npColumnEntries != null && npColumnEntries.size() > 0) {
                    //计算柱子的中心点
                    float xColumnCenterX = tmpI * xDisAdd + leftMargin + chartColumnBean.getMarginLeft() + columnWidth * tmpI + columnWidth / 2.0f;
                    ViewLog.e("xColumnCenterX====>" + xColumnCenterX);

                    ColumnData pathData = createRect(npColumnEntries, xColumnCenterX);
                    allTmpRectList.add(pathData.clickRange);
                    int smallRectCount = pathData.rectFList.size();

                    columnDataList.add(pathData);
                    for (int i = 0; i < smallRectCount; i++) {
                        paint.setColor(columnDataBean.getColorList().get(i));
                        canvas.drawRect(pathData.getRectFList().get(i), paint);
                    }
                }
                tmpI++;
            }


        } else {
            ViewLog.e("chartColumnBean.getNpChartColumnDataBeans()=null !!!!");
        }
    }


    /**
     * 每个柱子（分段）的实际值总和
     */
    private List<Float> allColumnDataSum = new ArrayList<>();

    /**
     * @param columnEntryList
     * @return
     */
    private ColumnData createRect(List<NpColumnEntry> columnEntryList, float xColumnCenterX) {

        ColumnData pathData = new ColumnData();

        float columnBottomPosition = viewRectF.bottom - bottomLabelRangeHeight;


        float thisTotalHeight = viewRectF.height() - bottomLabelRangeHeight;

        int dataLen = columnEntryList.size();

        List<RectF> rectFList = new ArrayList<>();
        //计算柱子的最大值
        float maxValue = 0;
        for (NpColumnEntry npColumnEntry : columnEntryList) {
            maxValue += npColumnEntry.getValue();
        }
        pathData.setCloumnValueSum(maxValue);
        allColumnDataSum.add(maxValue);

        ViewLog.e("columnMaxValue===>" + columnMaxValue);
        float thisPercentWithColumnMax = 1.0f;
        if (columnMaxValue > 0) {
            thisPercentWithColumnMax = maxValue / columnMaxValue;
        }
        if (thisPercentWithColumnMax >= 1) {
            thisPercentWithColumnMax = 1;
        }

        ViewLog.e("thisPercentWithColumnMax===>" + thisPercentWithColumnMax);
        thisTotalHeight *= thisPercentWithColumnMax;

        //累计分段计算小柱子的高度百分比
        float tmpPercent = 0;
        float left = xColumnCenterX - columnWidth / 2.0f;
        float right = xColumnCenterX + columnWidth / 2.0f;
        for (int i = 0; i < dataLen; i++) {
            RectF rectF = new RectF(left, 0, right, 0);
            rectF.bottom = columnBottomPosition - thisTotalHeight * (tmpPercent);
            float percent = columnEntryList.get(i).getValue() / maxValue;
            rectF.top = rectF.bottom - thisTotalHeight * percent;
            tmpPercent += percent;
            rectFList.add(rectF);
        }
        pathData.setRectFList(rectFList);
        RectF clickRangeRect = new RectF(0, columnBottomPosition - thisTotalHeight, 0, columnBottomPosition);
        clickRangeRect.left = xColumnCenterX - clickRangeWidth;
        clickRangeRect.right = clickRangeRect.left + clickRangeWidth * 2;
        pathData.clickRange = clickRangeRect;
        return pathData;
    }


    /**
     * 柱子的rect对象
     */
    public class ColumnData {
        private RectF clickRange = null;
        private List<RectF> rectFList = null;
        private float cloumnValueSum = 0;

        public ColumnData() {
        }

        public List<RectF> getRectFList() {
            return rectFList;
        }

        public void setRectFList(List<RectF> rectFList) {
            this.rectFList = rectFList;
        }

        public float getCloumnValueSum() {
            return cloumnValueSum;
        }

        public void setCloumnValueSum(float cloumnValueSum) {
            this.cloumnValueSum = cloumnValueSum;
        }
    }

    @Override
    public void invalidate() {
        draw();
        super.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ViewLog.e("x=>" + event.getX() + " ///y=>" + event.getY());
            for (int i = 0; i < allTmpRectList.size(); i++) {
                if (allTmpRectList.get(i).contains(event.getX(), event.getY())) {
                    lastSelectIndex = i;
                    hasClick = true;
                    postInvalidateDelayed(20);
                    ViewLog.e("lastSelectIndex===>" + lastSelectIndex);
                    break;
                }
            }

        }
        return super.onTouchEvent(event);

    }


    private void loadCfg() {
        labelTextSize = chartColumnBean.getLabelTextSize();
        bottomLabelRangeHeight = chartColumnBean.getBottomHeight();
        columnMaxValue = chartColumnBean.getMaxY();
        allColumnDataSum.clear();
    }

    public interface OnColumnSelectListener {
        void onSelectColumn(NpChartColumnDataBean npChartColumnDataBean, int index);
    }


}

package npwidget.nopointer.chart.npChartColumnView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.chart.NpShowDataType;
import npwidget.nopointer.log.ViewLog;

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

    //最多横向显示的标签（数据）个数
    private int maxLabel = 0;

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

    //绘制没有数据的时候的文字大小
    private float getNoDataTextSize = 0;

    //绘制没有数据的时候的文字
    private String noDataText = "no data ~ ";

    //无数据是文本的颜色
    private int noDataTextColor =0xFF888888;

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
        getNoDataTextSize = QMUIDisplayHelper.sp2px(context, 14);
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
                if (chartColumnBean.getNpChartColumnDataBeans() != null && chartColumnBean.getNpChartColumnDataBeans().size() > 0) {
                    drawXYAxis();
                    drawDataColumns();
                } else {
                    drawNoData();
                }
            } else {
                drawNoData();
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
        canvas.drawText(text, viewRectF.centerX(), viewRectF.centerY(), paint);
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
            for (NpChartColumnDataBean columnDataBean : chartColumnDataBeans) {
                List<NpColumnEntry> npColumnEntries = columnDataBean.getNpColumnEntryList();
                if (npColumnEntries != null && npColumnEntries.size() > 0) {
                    //计算柱子的中心点
                    float xColumnCenterX = tmpI * xDisAdd + leftMargin + chartColumnBean.getMarginLeft() + columnWidth * tmpI + columnWidth / 2.0f;
                    ViewLog.e("xColumnCenterX====>" + xColumnCenterX);

                    ColumnData pathData = createRect(npColumnEntries, xColumnCenterX);
                    allTmpRectList.add(pathData.clickRange);
                    int smallRectCount = pathData.rectFList.size();

                    for (int i = 0; i < smallRectCount; i++) {
                        paint.setColor(columnDataBean.getColorList().get(i));
                        canvas.drawRect(pathData.getRectFList().get(i), paint);
                    }

                    if (tmpI == lastSelectIndex) {
                        paint.setColor(chartColumnBean.getSelectColumenColor());
                        canvas.drawRect(pathData.clickRange, paint);
                        if (onColumnSelectListener != null) {
                            onColumnSelectListener.onSelectColumn(columnDataBean, tmpI);
                        }
                    }

                    paint.setColor(chartColumnBean.getLabelTextColor());
                    paint.setTextSize(labelTextSize);
                    label = chartColumnBean.getNpLabelList().get(tmpI);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setAlpha(0xFF);
                    canvas.drawText(label, xColumnCenterX, viewRectF.bottom - labelTextSize / 2, paint);
                }
                tmpI++;
            }
        } else {
            ViewLog.e("chartColumnBean.getNpChartColumnDataBeans()=null !!!!");
        }
    }

    /**
     * @param columnEntryList
     * @return
     */
    private ColumnData createRect(List<NpColumnEntry> columnEntryList, float xColumnCenterX) {

        ColumnData pathData = new ColumnData();

        float columnBottomPosition = viewRectF.height() - bottomLabelRangeHeight;


        float thisTotalHeight = viewRectF.height() - bottomLabelRangeHeight;

        int dataLen = columnEntryList.size();

        List<RectF> rectFList = new ArrayList<>();
        //计算柱子的最大值
        float maxValue = 0;
        for (NpColumnEntry npColumnEntry : columnEntryList) {
            maxValue += npColumnEntry.getValue();
        }

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
            tmpPercent += columnEntryList.get(i).getValue() / maxValue;
            rectF.top = rectF.bottom - thisTotalHeight * tmpPercent;
            rectFList.add(rectF);
        }
        pathData.setRectFList(rectFList);
        pathData.clickRange = new RectF(left, viewRectF.top, right, viewRectF.top + thisTotalHeight);
        return pathData;
    }


    /**
     * 柱子的rect对象
     */
    public class ColumnData {
        private RectF clickRange = null;
        private List<RectF> rectFList = null;

        public ColumnData() {
        }

        public List<RectF> getRectFList() {
            return rectFList;
        }

        public void setRectFList(List<RectF> rectFList) {
            this.rectFList = rectFList;
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
            for (int i = 0; i < allTmpRectList.size(); i++) {
                if (allTmpRectList.get(i).contains(event.getX(), event.getY())) {
                    lastSelectIndex = i;
                    ViewLog.e("lastSelectIndex===>" + lastSelectIndex);
                    break;
                }
            }
            postInvalidateDelayed(20);
        }
        return super.onTouchEvent(event);

    }

    private void loadCfg() {
        labelTextSize = chartColumnBean.getLabelTextSize();
        bottomLabelRangeHeight = chartColumnBean.getBottomHeight();
        columnMaxValue = chartColumnBean.getMaxY();
        if (labelTextSize * 1.6f > bottomLabelRangeHeight) {
            bottomLabelRangeHeight = labelTextSize * 2f;
        }
    }

    public interface OnColumnSelectListener {
        void onSelectColumn(NpChartColumnDataBean npChartColumnDataBean, int index);
    }


}

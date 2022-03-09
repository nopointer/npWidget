package npwidget.nopointer.chart.npChartColumnView;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;
import npwidget.nopointer.chart.NpValueFormatter;

public class NpChartColumnBean {

    /**
     * 最大值 y轴上
     */
    private float maxY;
    /**
     * 最小值 y轴上
     */
    private float minY;

    /**
     * 是否显示X轴
     */
    private boolean showXAxis;

    //是否显示参考线
    private boolean showRefreshLine = false;

    //参考线条数
    private int refreshLineCount = 4;


    //参考值个数
    private int refreshValueCount = 2;

    /**
     * x轴的颜色
     */
    private int XAxisLineColor = 0xFFFFFFFF;

    /**
     * 是否显示Y轴
     */
    private boolean showYAxis;

    private NpValueFormatter YAxisFormatter;

    private NpValueFormatter valueFormatter;

    /**
     * y轴的颜色
     */
    private int YAxisLineColor = 0xFFFFFF;

    /**
     * 数据展示方式，有两种 一种是可滑动 一种是平分宽度，默认是平分给定的宽度
     */
    private NpShowDataType showDataType = NpShowDataType.Equal;


    /**
     * 每个柱子的宽度，
     */
    private float columnWidth = 15;

    /**
     * 数据距离左边的开始边距
     */
    private float marginLeft = 10;
    /**
     * 数据距离右边的结束边距
     */
    private float marginRight = 10;

    /**
     * 每个柱子之间的间距，当showDataType=Slide的时候生效
     */
    private float columnSpaceWidth = 10;


    /**
     * 是否显示labels
     */
    private boolean showLabels = true;

    /**
     * 标签文字大小
     */
    private float labelTextSize = 20;

    /**
     * 底部绘制横向标签的高度
     */
    private float bottomHeight = 20;

    /**
     * 底部绘制横向标签的高度
     */
    private float topHeight = 20;

    /**
     * 标签的颜色
     */
    private int labelTextColor = 0xFF000000;


    private NpSelectMode npSelectMode = NpSelectMode.NONE;


    /**
     * 数据曲线集合
     */
    private List<NpChartColumnDataBean> npChartColumnDataBeans = new ArrayList<>();

    /**
     * 选择中的柱子的颜色
     */
    private List<Integer> selectColumnColorList = null;


    /**
     * 横向的lable集合
     */
    private List<String> npLabelList = new ArrayList<>();

    /**
     * 顶部圆角
     */
    private boolean isTopRound;

    /**
     * 底部圆角
     */
    private boolean isBottomRound;

    /**
     * 是否显示选择的value
     */
    private boolean showSelectValue = false;

    private float selectValueMarginColumn = 10;

    private float selectValueTextSize = 30;

    private int selectValueTextColor = 0xFF000000;


    public NpShowDataType getShowDataType() {
        return showDataType;
    }

    public void setShowDataType(NpShowDataType showDataType) {
        this.showDataType = showDataType;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public boolean isShowXAxis() {
        return showXAxis;
    }

    public void setShowXAxis(boolean showXAxis) {
        this.showXAxis = showXAxis;
    }

    public boolean isShowYAxis() {
        return showYAxis;
    }

    public void setShowYAxis(boolean showYAxis) {
        this.showYAxis = showYAxis;
    }

    public float getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(float columnWidth) {
        this.columnWidth = columnWidth;
    }

    public List<String> getNpLabelList() {
        return npLabelList;
    }

    public void setNpLabelList(List<String> npLabelList) {
        this.npLabelList = npLabelList;
    }

    public float getLabelTextSize() {
        return labelTextSize;
    }

    public void setLabelTextSize(float labelTextSize) {
        this.labelTextSize = labelTextSize;
    }

    public boolean isShowLabels() {
        return showLabels;
    }

    public void setShowLabels(boolean showLabels) {
        this.showLabels = showLabels;
    }

    public float getColumnSpaceWidth() {
        return columnSpaceWidth;
    }

    public void setColumnSpaceWidth(float columnSpaceWidth) {
        this.columnSpaceWidth = columnSpaceWidth;
    }

    public List<NpChartColumnDataBean> getNpChartColumnDataBeans() {
        return npChartColumnDataBeans;
    }

    public void setNpChartColumnDataBeans(List<NpChartColumnDataBean> npChartColumnDataBeans) {
        this.npChartColumnDataBeans = npChartColumnDataBeans;
    }

    public float getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public float getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    public float getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public int getLabelTextColor() {
        return labelTextColor;
    }

    public void setLabelTextColor(int labelTextColor) {
        this.labelTextColor = labelTextColor;
    }

    public int getXAxisLineColor() {
        return XAxisLineColor;
    }

    public void setXAxisLineColor(int xAxisLineColor) {
        this.XAxisLineColor = xAxisLineColor;
    }

    public int getYAxisLineColor() {
        return YAxisLineColor;
    }

    public void setYAxisLineColor(int yAxisLineColor) {
        this.YAxisLineColor = yAxisLineColor;
    }


    public List<Integer> getSelectColumnColorList() {
        return selectColumnColorList;
    }

    public void setSelectColumnColorList(List<Integer> selectColumnColorList) {
        this.selectColumnColorList = selectColumnColorList;
    }

    public NpSelectMode getNpSelectMode() {
        return npSelectMode;
    }

    public void setNpSelectMode(NpSelectMode npSelectMode) {
        this.npSelectMode = npSelectMode;
    }


    public boolean isTopRound() {
        return isTopRound;
    }

    public void setTopRound(boolean topRound) {
        isTopRound = topRound;
    }

    public boolean isBottomRound() {
        return isBottomRound;
    }

    public void setBottomRound(boolean bottomRound) {
        isBottomRound = bottomRound;
    }

    public boolean isShowSelectValue() {
        return showSelectValue;
    }

    public void setShowSelectValue(boolean showSelectValue) {
        this.showSelectValue = showSelectValue;
    }

    public float getSelectValueMarginColumn() {
        return selectValueMarginColumn;
    }

    public void setSelectValueMarginColumn(float selectValueMarginColumn) {
        this.selectValueMarginColumn = selectValueMarginColumn;
    }

    public float getSelectValueTextSize() {
        return selectValueTextSize;
    }

    public void setSelectValueTextSize(float selectValueTextSize) {
        this.selectValueTextSize = selectValueTextSize;
    }

    public boolean isShowRefreshLine() {
        return showRefreshLine;
    }

    public void setShowRefreshLine(boolean showRefreshLine) {
        this.showRefreshLine = showRefreshLine;
    }

    public int getRefreshLineCount() {
        return refreshLineCount;
    }

    public void setRefreshLineCount(int refreshLineCount) {
        this.refreshLineCount = refreshLineCount;
    }

    public int getRefreshValueCount() {
        return refreshValueCount;
    }

    public void setRefreshValueCount(int refreshValueCount) {
        this.refreshValueCount = refreshValueCount;
    }


    public float getTopHeight() {
        return topHeight;
    }

    public void setTopHeight(float topHeight) {
        this.topHeight = topHeight;
    }


    public int getSelectValueTextColor() {
        return selectValueTextColor;
    }

    public void setSelectValueTextColor(int selectValueTextColor) {
        this.selectValueTextColor = selectValueTextColor;
    }

    public NpValueFormatter getYAxisFormatter() {
        return YAxisFormatter;
    }

    public void setYAxisFormatter(NpValueFormatter YAxisFormatter) {
        this.YAxisFormatter = YAxisFormatter;
    }

    public NpValueFormatter getValueFormatter() {
        return valueFormatter;
    }

    public void setValueFormatter(NpValueFormatter valueFormatter) {
        this.valueFormatter = valueFormatter;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NpChartColumnBean{");
        sb.append("maxY=").append(maxY);
        sb.append(", minY=").append(minY);
        sb.append(", showXAxis=").append(showXAxis);
        sb.append(", showRefreshLine=").append(showRefreshLine);
        sb.append(", refreshLineCount=").append(refreshLineCount);
        sb.append(", refreshValueCount=").append(refreshValueCount);
        sb.append(", XAxisLineColor=").append(XAxisLineColor);
        sb.append(", showYAxis=").append(showYAxis);
        sb.append(", YAxisLineColor=").append(YAxisLineColor);
        sb.append(", showDataType=").append(showDataType);
        sb.append(", columnWidth=").append(columnWidth);
        sb.append(", marginLeft=").append(marginLeft);
        sb.append(", marginRight=").append(marginRight);
        sb.append(", columnSpaceWidth=").append(columnSpaceWidth);
        sb.append(", showLabels=").append(showLabels);
        sb.append(", labelTextSize=").append(labelTextSize);
        sb.append(", bottomHeight=").append(bottomHeight);
        sb.append(", topHeight=").append(topHeight);
        sb.append(", labelTextColor=").append(labelTextColor);
        sb.append(", npSelectMode=").append(npSelectMode);
        sb.append(", npChartColumnDataBeans=").append(npChartColumnDataBeans);
        sb.append(", selectColumnColorList=").append(selectColumnColorList);
        sb.append(", npLabelList=").append(npLabelList);
        sb.append(", isTopRound=").append(isTopRound);
        sb.append(", isBottomRound=").append(isBottomRound);
        sb.append(", showSelectValue=").append(showSelectValue);
        sb.append(", selectValueMarginColumn=").append(selectValueMarginColumn);
        sb.append(", selectValueTextSize=").append(selectValueTextSize);
        sb.append(", selectValueTextColor=").append(selectValueTextColor);
        sb.append('}');
        return sb.toString();
    }
}

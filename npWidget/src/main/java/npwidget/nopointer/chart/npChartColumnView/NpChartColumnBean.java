package npwidget.nopointer.chart.npChartColumnView;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;

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

    /**
     * x轴的颜色
     */
    private int XAxisLineColor = 0xFFFFFFFF;

    /**
     * 是否显示Y轴
     */
    private boolean showYAxis;

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
    private int selectColumenColor = 0xA0333333;

    /**
     * 横向的lable集合
     */
    private List<String> npLabelList = new ArrayList<>();


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

    public int getSelectColumenColor() {
        return selectColumenColor;
    }

    public void setSelectColumenColor(int selectColumenColor) {
        this.selectColumenColor = selectColumenColor;
    }

    public NpSelectMode getNpSelectMode() {
        return npSelectMode;
    }

    public void setNpSelectMode(NpSelectMode npSelectMode) {
        this.npSelectMode = npSelectMode;
    }

    @Override
    public String toString() {
        return "NpChartColumnBean{" +
                "maxY=" + maxY +
                ", minY=" + minY +
                ", showXAxis=" + showXAxis +
                ", XAxisLineColor=" + XAxisLineColor +
                ", showYAxis=" + showYAxis +
                ", YAxisLineColor=" + YAxisLineColor +
                ", showDataType=" + showDataType +
                ", columnWidth=" + columnWidth +
                ", marginLeft=" + marginLeft +
                ", marginRight=" + marginRight +
                ", columnSpaceWidth=" + columnSpaceWidth +
                ", showLabels=" + showLabels +
                ", labelTextSize=" + labelTextSize +
                ", bottomHeight=" + bottomHeight +
                ", labelTextColor=" + labelTextColor +
                ", npSelectMode=" + npSelectMode +
                ", npChartColumnDataBeans=" + npChartColumnDataBeans +
                ", selectColumenColor=" + selectColumenColor +
                ", npLabelList=" + npLabelList +
                '}';
    }
}

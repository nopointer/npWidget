package npwidget.nopointer.chart.npChartPointView;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;

public class NpChartPointBean {

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

    //是否显示参考线
    private boolean showRefreshLine = false;

    //参考线条数
    private int refreshLineCount = 4;


    //参考值个数
    private int refreshValueCount = 2;


    /**
     * 数据展示方式，有两种 一种是可滑动 一种是平分宽度，默认是平分给定的宽度
     */
    private NpShowDataType showDataType = NpShowDataType.Equal;


    /**
     * 横向label间距
     */
    private float labelSpaceWidth = 100;




    /**
     * 是否显示labels
     */
    private boolean showLabels = true;

    /**
     * 标签文字大小
     */
    private float labelTextSize = 40;


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


    public NpShowDataType getShowDataType() {
        return showDataType;
    }

    public void setShowDataType(NpShowDataType showDataType) {
        this.showDataType = showDataType;
    }


    public List<NpChartPointDataBean> getNpChartLineDataBeans() {
        return npChartLineDataBeans;
    }

    public void setNpChartLineDataBeans(List<NpChartPointDataBean> npChartLineDataBeans) {
        this.npChartLineDataBeans = npChartLineDataBeans;
    }

    /**
     * 数据曲线集合
     */
    private List<NpChartPointDataBean> npChartLineDataBeans = new ArrayList<>();

    /**
     * 横向的lable集合
     */
    private List<String> npLabelList = new ArrayList<>();

    private NpSelectMode npSelectMode = NpSelectMode.NONE;


    public NpSelectMode getNpSelectMode() {
        return npSelectMode;
    }

    public void setNpSelectMode(NpSelectMode npSelectMode) {
        this.npSelectMode = npSelectMode;
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

    public float getLabelSpaceWidth() {
        return labelSpaceWidth;
    }

    public void setLabelSpaceWidth(float labelSpaceWidth) {
        this.labelSpaceWidth = labelSpaceWidth;
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

    public float getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
    }


    public int getXAxisLineColor() {
        return XAxisLineColor;
    }

    public void setXAxisLineColor(int XAxisLineColor) {
        this.XAxisLineColor = XAxisLineColor;
    }

    public int getYAxisLineColor() {
        return YAxisLineColor;
    }

    public void setYAxisLineColor(int YAxisLineColor) {
        this.YAxisLineColor = YAxisLineColor;
    }

    public int getLabelTextColor() {
        return labelTextColor;
    }

    public void setLabelTextColor(int labelTextColor) {
        this.labelTextColor = labelTextColor;
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


    @Override
    public String toString() {
        return "NpChartPointBean{" +
                "maxY=" + maxY +
                ", minY=" + minY +
                ", showXAxis=" + showXAxis +
                ", XAxisLineColor=" + XAxisLineColor +
                ", showYAxis=" + showYAxis +
                ", YAxisLineColor=" + YAxisLineColor +
                ", showRefreshLine=" + showRefreshLine +
                ", refreshLineCount=" + refreshLineCount +
                ", refreshValueCount=" + refreshValueCount +
                ", showDataType=" + showDataType +
                ", labelSpaceWidth=" + labelSpaceWidth +
                ", showLabels=" + showLabels +
                ", labelTextSize=" + labelTextSize +
                ", bottomHeight=" + bottomHeight +
                ", topHeight=" + topHeight +
                ", labelTextColor=" + labelTextColor +
                ", npChartLineDataBeans=" + npChartLineDataBeans +
                ", npLabelList=" + npLabelList +
                ", npSelectMode=" + npSelectMode +
                '}';
    }
}

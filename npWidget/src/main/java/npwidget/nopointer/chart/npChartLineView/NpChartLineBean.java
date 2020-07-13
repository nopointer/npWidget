package npwidget.nopointer.chart.npChartLineView;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;

public class NpChartLineBean {

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
     * 是否显示Y轴
     */
    private boolean showYAxis;

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



    public NpShowDataType getShowDataType() {
        return showDataType;
    }

    public void setShowDataType(NpShowDataType showDataType) {
        this.showDataType = showDataType;
    }


    public List<NpChartLineDataBean> getNpChartLineDataBeans() {
        return npChartLineDataBeans;
    }

    public void setNpChartLineDataBeans(List<NpChartLineDataBean> npChartLineDataBeans) {
        this.npChartLineDataBeans = npChartLineDataBeans;
    }

    /**
     * 数据曲线集合
     */
    private List<NpChartLineDataBean> npChartLineDataBeans = new ArrayList<>();

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
}

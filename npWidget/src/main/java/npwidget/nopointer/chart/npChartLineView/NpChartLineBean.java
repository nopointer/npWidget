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

    /**
     * 选择样式，默认空心圆
     */
    private NpSelectStyle npSelectStyle = NpSelectStyle.HOLLOW_CIRCLE;

    /**
     * 图表类型
     */
    private NpChartLineType npChartLineType = NpChartLineType.LINE;


    /**
     * 实心圆半径
     */
    private float selectFilledCircleR;

    /**
     * 实心圆颜色
     */
    private int selectFilledCircleColor;


    /**
     * 空心圆半径
     */
    private float selectHollowCircleR;
    /**
     * 空心圆宽度
     */
    private float selectHollowCircleWidth;

    /**
     * 空心圆颜色
     */
    private int selectHollowCircleColor;


    /**
     * 选择线宽度
     */
    private int selectLineWidth;
    /**
     * 选择线颜色
     */
    private int selectLineColor;

    /**
     * 是否适配第一个label，（数据较多的时候，可能会显示不全，适配的话 就偏右边一些）
     */
    private boolean adaptationFirstLabel = false;

    /**
     * 是否适配最后一个label，（数据较多的时候，可能会显示不全，适配的话 就偏右边一些）
     */
    private boolean adaptationLastLabel = false;


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

    public NpSelectStyle getNpSelectStyle() {
        return npSelectStyle;
    }

    public void setNpSelectStyle(NpSelectStyle npSelectStyle) {
        this.npSelectStyle = npSelectStyle;
    }


    public NpChartLineType getNpChartLineType() {
        return npChartLineType;
    }

    public void setNpChartLineType(NpChartLineType npChartLineType) {
        this.npChartLineType = npChartLineType;
    }

    public float getSelectFilledCircleR() {
        return selectFilledCircleR;
    }

    public void setSelectFilledCircleR(float selectFilledCircleR) {
        this.selectFilledCircleR = selectFilledCircleR;
    }

    public int getSelectFilledCircleColor() {
        return selectFilledCircleColor;
    }

    public void setSelectFilledCircleColor(int selectFilledCircleColor) {
        this.selectFilledCircleColor = selectFilledCircleColor;
    }

    public float getSelectHollowCircleR() {
        return selectHollowCircleR;
    }

    public void setSelectHollowCircleR(float selectHollowCircleR) {
        this.selectHollowCircleR = selectHollowCircleR;
    }

    public float getSelectHollowCircleWidth() {
        return selectHollowCircleWidth;
    }

    public void setSelectHollowCircleWidth(float selectHollowCircleWidth) {
        this.selectHollowCircleWidth = selectHollowCircleWidth;
    }

    public int getSelectHollowCircleColor() {
        return selectHollowCircleColor;
    }

    public void setSelectHollowCircleColor(int selectHollowCircleColor) {
        this.selectHollowCircleColor = selectHollowCircleColor;
    }

    public int getSelectLineWidth() {
        return selectLineWidth;
    }

    public void setSelectLineWidth(int selectLineWidth) {
        this.selectLineWidth = selectLineWidth;
    }

    public int getSelectLineColor() {
        return selectLineColor;
    }

    public void setSelectLineColor(int selectLineColor) {
        this.selectLineColor = selectLineColor;
    }

    public boolean isAdaptationFirstLabel() {
        return adaptationFirstLabel;
    }

    public void setAdaptationFirstLabel(boolean adaptationFirstLabel) {
        this.adaptationFirstLabel = adaptationFirstLabel;
    }

    public boolean isAdaptationLastLabel() {
        return adaptationLastLabel;
    }

    public void setAdaptationLastLabel(boolean adaptationLastLabel) {
        this.adaptationLastLabel = adaptationLastLabel;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NpChartLineBean{");
        sb.append("maxY=").append(maxY);
        sb.append(", minY=").append(minY);
        sb.append(", showXAxis=").append(showXAxis);
        sb.append(", XAxisLineColor=").append(XAxisLineColor);
        sb.append(", showYAxis=").append(showYAxis);
        sb.append(", YAxisLineColor=").append(YAxisLineColor);
        sb.append(", showRefreshLine=").append(showRefreshLine);
        sb.append(", refreshLineCount=").append(refreshLineCount);
        sb.append(", refreshValueCount=").append(refreshValueCount);
        sb.append(", showDataType=").append(showDataType);
        sb.append(", labelSpaceWidth=").append(labelSpaceWidth);
        sb.append(", showLabels=").append(showLabels);
        sb.append(", labelTextSize=").append(labelTextSize);
        sb.append(", bottomHeight=").append(bottomHeight);
        sb.append(", topHeight=").append(topHeight);
        sb.append(", labelTextColor=").append(labelTextColor);
        sb.append(", npSelectStyle=").append(npSelectStyle);
        sb.append(", npChartLineType=").append(npChartLineType);
        sb.append(", selectFilledCircleR=").append(selectFilledCircleR);
        sb.append(", selectFilledCircleColor=").append(selectFilledCircleColor);
        sb.append(", selectHollowCircleR=").append(selectHollowCircleR);
        sb.append(", selectHollowCircleWidth=").append(selectHollowCircleWidth);
        sb.append(", selectHollowCircleColor=").append(selectHollowCircleColor);
        sb.append(", selectLineWidth=").append(selectLineWidth);
        sb.append(", selectLineColor=").append(selectLineColor);
        sb.append(", npChartLineDataBeans=").append(npChartLineDataBeans);
        sb.append(", npLabelList=").append(npLabelList);
        sb.append(", npSelectMode=").append(npSelectMode);
        sb.append('}');
        return sb.toString();
    }
}

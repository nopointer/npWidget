package npwidget.nopointer.chart.npChartLineView;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.LineType;
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
    private int XAxisLineColor = 0xFF333333;


    /**
     * 数据展示方式，有两种 一种是可滑动 一种是平分宽度，默认是平分给定的宽度
     */
    private NpShowDataType showDataType = NpShowDataType.Equal;

    /**
     * 是否在等宽下 允许滚动
     */
    private boolean isEqualAllowSliding =false;


    /**
     * 线类型，支持贝塞尔曲线，折线
     */
    private LineType lineType = LineType.Bezier;


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

    //标签旋转
    private float labelRotateAngle = 0;


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

    public int getLabelTextColor() {
        return labelTextColor;
    }

    public void setLabelTextColor(int labelTextColor) {
        this.labelTextColor = labelTextColor;
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

    public float getLabelRotateAngle() {
        return labelRotateAngle;
    }

    public void setLabelRotateAngle(float labelRotateAngle) {
        this.labelRotateAngle = labelRotateAngle;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }


    public boolean isEqualAllowSliding() {
        return isEqualAllowSliding;
    }

    public void setEqualAllowSliding(boolean equalAllowSliding) {
        isEqualAllowSliding = equalAllowSliding;
    }




    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NpChartLineBean{");
        sb.append("maxY=").append(maxY);
        sb.append(", minY=").append(minY);
        sb.append(", showXAxis=").append(showXAxis);
        sb.append(", XAxisLineColor=").append(XAxisLineColor);
        sb.append(", showDataType=").append(showDataType);
        sb.append(", isEqualAllowSliding=").append(isEqualAllowSliding);
        sb.append(", lineType=").append(lineType);
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
        sb.append(", adaptationFirstLabel=").append(adaptationFirstLabel);
        sb.append(", adaptationLastLabel=").append(adaptationLastLabel);
        sb.append(", npChartLineDataBeans=").append(npChartLineDataBeans);
        sb.append(", npLabelList=").append(npLabelList);
        sb.append(", npSelectMode=").append(npSelectMode);
        sb.append(", labelRotateAngle=").append(labelRotateAngle);
        sb.append('}');
        return sb.toString();
    }
}

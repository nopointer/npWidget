package npwidget.nopointer.sleepView.sleepStateAreaView;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.sleepView.NpSleepEntry;

/**
 * 睡眠区间图数据
 */
public class NpSleepStateAreaBean {


    /**
     * 睡眠数据的状态类型（常规的有深睡，浅睡，清醒，后期可能会有其他状态）
     */
    private int sleepStateCount = 3;
    /**
     * 背景填充样式
     */
    private StateAreaType stateAreaType = StateAreaType.ALL_HEIGHT;

    /**
     * 选中后的颜色
     */
    private StateAreaSelectType stateAreaSelectType = StateAreaSelectType.UNIFY;

    /**
     * 等分高的时候 填充比例默认填满
     */
    private float splitHeightRatio = 1f;

    /**
     * 数据列表
     */
    private List<NpSleepEntry> dataList = new ArrayList<>();

    /**
     * 选中的睡眠碎片颜色
     */
    private int selectPartRectColor = 0xFFFFFFFF;

    /**
     * 左下角的文字
     */
    private String leftText = "";

    /**
     * 左下角文字颜色
     */
    private int leftTextColor = 0xFFFFFFFF;

    /**
     * 右下角的文字
     */
    private String rightText = "";

    /**
     * 右下角文字颜色
     */
    private int rightTextColor = 0xFFFFFFFF;

    /**
     * 下面的文字大小
     */
    private float leftRightTextSize = 12;


    /**
     * 是否点击可以显示睡眠碎片区域
     */
    private boolean isEnableClickPart = true;

    /**
     * 选择的睡眠碎片 点击后显示的详情的文字颜色
     */
    private int selectPartTextInfoColor = 0xFF000000;

    /**
     * 分割线的颜色
     */
    private int clipLineColor = 0xFFFFFFFF;

    /**
     * 是否显示分割线
     */
    private boolean showClipLine = true;

    /**
     * 分割线的宽度
     */
    private float clipLineWidth = 1;


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
    private int XAxisLineColor = 0xFFFF0000;

    /**
     * x轴的粗细
     */
    private int XAxisLineWidth = 1;

    /**
     * 是否显示Y轴
     */
    private boolean showYAxis;

    /**
     * y轴的颜色
     */
    private int YAxisLineColor = 0xFFFF0000;

    /**
     * x轴的粗细
     */
    private int YAxisLineWidth = 1;

    //是否显示参考线
    private boolean showRefreshLine = false;

    //参考线条数
    private int refreshLineCount = 4;


    //参考值个数
    private int refreshValueCount = 2;


    /**
     * 底部绘制横向标签的高度
     */
    private float bottomHeight = 40;

    /**
     * 选择的睡眠碎片 点击后显示的详情的文字大小
     */
    private int selectPartTextInfoSize = 24;

    /**
     * 显示碎片之间的连线
     */
    private boolean showPartLigature = true;


    //显示选中的线条
    private boolean showSelectLine = false;
    //选中的线条的颜色
    private int selectLineColor = 0x00000000;
    //选中的线条的高度比例
    private float selectLineHeightScale = 1.0f;
    //选中的线条的宽度
    private int selectLineWidth = 1;

    /**
     * 在柱子上面
     */
    public static final int SelectLineShowType_TOP = 1;
    /**
     * 在柱子下面面
     */
    public static final int SelectLineShowType_BOTTOM = 0;
    private int selectLineShowType = SelectLineShowType_BOTTOM;

    public StateAreaType getStateAreaType() {
        return stateAreaType;
    }

    public void setStateAreaType(StateAreaType stateAreaType) {
        this.stateAreaType = stateAreaType;
    }

    public int getSleepStateCount() {
        return sleepStateCount;
    }

    public void setSleepStateCount(int sleepStateCount) {
        this.sleepStateCount = sleepStateCount;
    }

    public List<NpSleepEntry> getDataList() {
        return dataList;
    }

    public void setDataList(List<NpSleepEntry> dataList) {
        this.dataList = dataList;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }


    public int getLeftTextColor() {
        return leftTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
    }

    public int getRightTextColor() {
        return rightTextColor;
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
    }

    public float getLeftRightTextSize() {
        return leftRightTextSize;
    }

    public void setLeftRightTextSize(float leftRightTextSize) {
        this.leftRightTextSize = leftRightTextSize;
    }

    public boolean isEnableClickPart() {
        return isEnableClickPart;
    }

    public void setEnableClickPart(boolean enableClickPart) {
        isEnableClickPart = enableClickPart;
    }

    public int getSelectPartTextInfoColor() {
        return selectPartTextInfoColor;
    }

    public void setSelectPartTextInfoColor(int selectPartTextInfoColor) {
        this.selectPartTextInfoColor = selectPartTextInfoColor;
    }

    public int getSelectPartTextInfoSize() {
        return selectPartTextInfoSize;
    }

    public void setSelectPartTextInfoSize(int selectPartTextInfoSize) {
        this.selectPartTextInfoSize = selectPartTextInfoSize;
    }

    public int getSelectPartRectColor() {
        return selectPartRectColor;
    }

    public void setSelectPartRectColor(int selectPartRectColor) {
        this.selectPartRectColor = selectPartRectColor;
    }

    public int getClipLineColor() {
        return clipLineColor;
    }

    public void setClipLineColor(int clipLineColor) {
        this.clipLineColor = clipLineColor;
    }

    public float getClipLineWidth() {
        return clipLineWidth;
    }

    public void setClipLineWidth(float clipLineWidth) {
        this.clipLineWidth = clipLineWidth;
    }


    public float getSplitHeightRatio() {
        return splitHeightRatio;
    }

    public void setSplitHeightRatio(float splitHeightRatio) {
        this.splitHeightRatio = splitHeightRatio;
        if (this.splitHeightRatio < 0 || this.splitHeightRatio > 1) {
            this.splitHeightRatio = 1;
        }
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

    public float getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
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

    public int getXAxisLineColor() {
        return XAxisLineColor;
    }

    public void setXAxisLineColor(int XAxisLineColor) {
        this.XAxisLineColor = XAxisLineColor;
    }

    public boolean isShowYAxis() {
        return showYAxis;
    }

    public void setShowYAxis(boolean showYAxis) {
        this.showYAxis = showYAxis;
    }

    public int getYAxisLineColor() {
        return YAxisLineColor;
    }

    public void setYAxisLineColor(int YAxisLineColor) {
        this.YAxisLineColor = YAxisLineColor;
    }

    public int getXAxisLineWidth() {
        return XAxisLineWidth;
    }

    public void setXAxisLineWidth(int XAxisLineWidth) {
        this.XAxisLineWidth = XAxisLineWidth;
    }

    public int getYAxisLineWidth() {
        return YAxisLineWidth;
    }

    public void setYAxisLineWidth(int YAxisLineWidth) {
        this.YAxisLineWidth = YAxisLineWidth;
    }

    public StateAreaSelectType getStateAreaSelectType() {
        return stateAreaSelectType;
    }

    public void setStateAreaSelectType(StateAreaSelectType stateAreaSelectType) {
        this.stateAreaSelectType = stateAreaSelectType;
    }


    public boolean isShowPartLigature() {
        return showPartLigature;
    }

    public void setShowPartLigature(boolean showPartLigature) {
        this.showPartLigature = showPartLigature;
    }

    public boolean isShowSelectLine() {
        return showSelectLine;
    }

    public void setShowSelectLine(boolean showSelectLine) {
        this.showSelectLine = showSelectLine;
    }

    public int getSelectLineColor() {
        return selectLineColor;
    }

    public void setSelectLineColor(int selectLineColor) {
        this.selectLineColor = selectLineColor;
    }

    public float getSelectLineHeightScale() {
        return selectLineHeightScale;
    }

    public void setSelectLineHeightScale(float selectLineHeightScale) {
        this.selectLineHeightScale = selectLineHeightScale;
    }

    public int getSelectLineWidth() {
        return selectLineWidth;
    }

    public void setSelectLineWidth(int selectLineWidth) {
        this.selectLineWidth = selectLineWidth;
    }

    public boolean isShowClipLine() {
        return showClipLine;
    }

    public void setShowClipLine(boolean showClipLine) {
        this.showClipLine = showClipLine;
    }

    public int getSelectLineShowType() {
        return selectLineShowType;
    }

    public void setSelectLineShowType(int selectLineShowType) {
        this.selectLineShowType = selectLineShowType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NpSleepStateAreaBean{");
        sb.append("sleepStateCount=").append(sleepStateCount);
        sb.append(", stateAreaType=").append(stateAreaType);
        sb.append(", stateAreaSelectType=").append(stateAreaSelectType);
        sb.append(", splitHeightRatio=").append(splitHeightRatio);
        sb.append(", dataList=").append(dataList);
        sb.append(", selectPartRectColor=").append(selectPartRectColor);
        sb.append(", leftText='").append(leftText).append('\'');
        sb.append(", leftTextColor=").append(leftTextColor);
        sb.append(", rightText='").append(rightText).append('\'');
        sb.append(", rightTextColor=").append(rightTextColor);
        sb.append(", leftRightTextSize=").append(leftRightTextSize);
        sb.append(", isEnableClickPart=").append(isEnableClickPart);
        sb.append(", selectPartTextInfoColor=").append(selectPartTextInfoColor);
        sb.append(", clipLineColor=").append(clipLineColor);
        sb.append(", showClipLine=").append(showClipLine);
        sb.append(", clipLineWidth=").append(clipLineWidth);
        sb.append(", maxY=").append(maxY);
        sb.append(", minY=").append(minY);
        sb.append(", showXAxis=").append(showXAxis);
        sb.append(", XAxisLineColor=").append(XAxisLineColor);
        sb.append(", XAxisLineWidth=").append(XAxisLineWidth);
        sb.append(", showYAxis=").append(showYAxis);
        sb.append(", YAxisLineColor=").append(YAxisLineColor);
        sb.append(", YAxisLineWidth=").append(YAxisLineWidth);
        sb.append(", showRefreshLine=").append(showRefreshLine);
        sb.append(", refreshLineCount=").append(refreshLineCount);
        sb.append(", refreshValueCount=").append(refreshValueCount);
        sb.append(", bottomHeight=").append(bottomHeight);
        sb.append(", selectPartTextInfoSize=").append(selectPartTextInfoSize);
        sb.append(", showPartLigature=").append(showPartLigature);
        sb.append(", showSelectLine=").append(showSelectLine);
        sb.append(", selectLineColor=").append(selectLineColor);
        sb.append(", selectLineHeightScale=").append(selectLineHeightScale);
        sb.append(", selectLineWidth=").append(selectLineWidth);
        sb.append(", selectLineShowType=").append(selectLineShowType);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 区间图样式
     */
    public enum StateAreaType {
        /**
         * 全高,一个状态充满整列
         */
        ALL_HEIGHT,
        /**
         * 等分高，该状态在整列中占一个等比高度位置(从上往下)
         */
        SPLIT_HEIGHT
    }

    /**
     * 选择后的颜色类型
     * 目前支持指定颜色 和 半透明 还有统一颜色
     */
    public enum StateAreaSelectType {
        /**
         * 指定颜色
         */
        SPECIFY,

        /**
         * 半透明 TRANSLUCENT
         */
        TRANSLUCENT,

        /**
         * 统一颜色
         */
        UNIFY

    }


}

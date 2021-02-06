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
     * 分割线的宽度
     */
    private float clipLineWidth = 1;

    /**
     * 选择的睡眠碎片 点击后显示的详情的文字大小
     */
    private int selectPartTextInfoSize = 24;

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


}

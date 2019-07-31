package npwidget.nopointer.sleepStateView;

import java.util.ArrayList;
import java.util.List;

/**
 * 睡眠数据
 */
public class NpSleepStateBean {

    /**
     * 背景图层颜色，平分纵向
     */
    private List<Integer> bgColors = new ArrayList<>();

    /**
     * 背景填充样式
     */
    private BgType bgType = BgType.Tile;

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
     * 线宽
     */
    private float lineWidth = 5;

    /**
     * 是否点击可以显示睡眠碎片区域
     */
    private boolean isEnableClickPart = true;

    /**
     * 选择的睡眠碎片 点击后显示的详情的文字颜色
     */
    private int selectPartTextInfoColor = 0xFF000000;


    /**
     * 选择的睡眠碎片 点击后显示的详情的文字大小
     */
    private int selectPartTextInfoSize = 24;


    public List<Integer> getBgColors() {
        return bgColors;
    }

    public void setBgColors(List<Integer> bgColors) {
        this.bgColors = bgColors;
    }


    public BgType getBgType() {
        return bgType;
    }

    public void setBgType(BgType bgType) {
        this.bgType = bgType;
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

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
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

    @Override
    public String toString() {
        return "NpSleepStateBean{" +
                "bgColors=" + bgColors +
                ", bgType=" + bgType +
                ", dataList=" + dataList +
                ", selectPartRectColor=" + selectPartRectColor +
                ", leftText='" + leftText + '\'' +
                ", leftTextColor=" + leftTextColor +
                ", rightText='" + rightText + '\'' +
                ", rightTextColor=" + rightTextColor +
                ", lineWidth=" + lineWidth +
                '}';
    }

    public int getSelectPartRectColor() {
        return selectPartRectColor;
    }

    public void setSelectPartRectColor(int selectPartRectColor) {
        this.selectPartRectColor = selectPartRectColor;
    }

    /**
     * 背景颜色样式
     */
    public enum BgType {
        /**
         * 平铺
         */
        Tile,
        /**
         * 渐变
         */
        Gradient
    }


}

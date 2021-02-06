package npwidget.nopointer.progress.npColorBars;

import java.util.List;

import npwidget.nopointer.base.NpPosition;
import npwidget.nopointer.base.ValueFormatCallback;
import npwidget.nopointer.progress.npColorBars.cursorTop.NpColorType;

/**
 * 颜色条的数据填充对象
 */
public class NpColorBarBean {


    /**
     * 显示类型
     */
    private NpColorType npColorType;

    /**
     * 当前的数据
     */
    private float currentValue = 20.0F;
    /**
     * 游标的颜色
     */
    private int cursorColor;
    /**
     * 游标的宽度
     */
    private float cursorWidth = 28;

    /**
     * 游标是否是等边3角形
     */
    private boolean cursorEquilateral = true;
    /**
     * 游标距离颜色块的距离
     */
    private float cursorMarginColorBar = 1;
    /**
     * 最大值
     */
    private float maxValue = 100.0F;
    /**
     * 最小值
     */
    private float minValue = 0.0F;

    /**
     * 是否是自动计算数据,默认是根据最大最小值以及数据段数 去自动计算，实际某些场景可能会要求数据不平分显示
     */
    private boolean isAutoCalculationValue = true;

    /**
     * 颜色条
     */
    private List<NpColorBarEntity> npColorBarEntityList = null;
    /**
     * 文字大小
     */
    private float textSize = 20.0F;
    /**
     * 是否使用圆角模式
     */
    private boolean useRoundMode = false;

    /**
     * 是否显示游标，默认显示
     */
    private boolean isShowCursor = true;
    /**
     * 文字颜色
     */
    private int valueColor;

    /**
     * 是否显示左右两边开始和结束值
     */
    private boolean isShowStartEndValue = true;

    /**
     * 是否显示文字或者值
     */
    private boolean isShowValue = false;

    /**
     * value的位置（目前遇到的场景是中间或者底部）
     */
    private NpPosition valuePosition = NpPosition.BOTTOM;

    /**
     * 游标的位置（目前遇到的场景是在上面）
     */
    private NpPosition cursorPosition = NpPosition.TOP;

    /**
     * 是否显示在图层最上面显示当前进度，默认不显示
     */
    private boolean showFloatProgress = false;

    /**
     * 最上层进度的颜色
     */
    private int floatProgressColor = 0xFFFFFF;



    private ValueFormatCallback valueFormatCallback = null;

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public int getCursorColor() {
        return cursorColor;
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public List<NpColorBarEntity> getNpColorBarEntityList() {
        return npColorBarEntityList;
    }

    public void setNpColorBarEntityList(List<NpColorBarEntity> npColorBarEntityList) {
        this.npColorBarEntityList = npColorBarEntityList;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public boolean isUseRoundMode() {
        return useRoundMode;
    }

    public void setUseRoundMode(boolean useRoundMode) {
        this.useRoundMode = useRoundMode;
    }

    public int getValueColor() {
        return valueColor;
    }

    public void setValueColor(int valueColor) {
        this.valueColor = valueColor;
    }

    public ValueFormatCallback getValueFormatCallback() {
        return valueFormatCallback;
    }

    public void setValueFormatCallback(ValueFormatCallback valueFormatCallback) {
        this.valueFormatCallback = valueFormatCallback;
    }

    public boolean isShowCursor() {
        return isShowCursor;
    }

    public void setShowCursor(boolean showCursor) {
        isShowCursor = showCursor;
    }

    public boolean isShowStartEndValue() {
        return isShowStartEndValue;
    }

    public void setShowStartEndValue(boolean showStartEndValue) {
        isShowStartEndValue = showStartEndValue;
    }

    public NpPosition getValuePosition() {
        return valuePosition;
    }

    public void setValuePosition(NpPosition valuePosition) {
        this.valuePosition = valuePosition;
    }

    public NpPosition getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(NpPosition cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    public float getCursorWidth() {
        return cursorWidth;
    }

    public void setCursorWidth(float cursorWidth) {
        this.cursorWidth = cursorWidth;
    }

    public boolean isCursorEquilateral() {
        return cursorEquilateral;
    }

    public void setCursorEquilateral(boolean cursorEquilateral) {
        this.cursorEquilateral = cursorEquilateral;
    }

    public float getCursorMarginColorBar() {
        return cursorMarginColorBar;
    }

    public void setCursorMarginColorBar(float cursorMarginColorBar) {
        this.cursorMarginColorBar = cursorMarginColorBar;
    }

    public boolean isAutoCalculationValue() {
        return isAutoCalculationValue;
    }

    public void setAutoCalculationValue(boolean autoCalculationValue) {
        isAutoCalculationValue = autoCalculationValue;
    }

    public boolean isShowValue() {
        return isShowValue;
    }

    public void setShowValue(boolean showValue) {
        isShowValue = showValue;
    }

    public NpColorType getNpColorType() {
        return npColorType;
    }

    public void setNpColorType(NpColorType npColorType) {
        this.npColorType = npColorType;
    }

    public boolean isShowFloatProgress() {
        return showFloatProgress;
    }

    public void setShowFloatProgress(boolean showFloatProgress) {
        this.showFloatProgress = showFloatProgress;
    }

    public int getFloatProgressColor() {
        return floatProgressColor;
    }

    public void setFloatProgressColor(int floatProgressColor) {
        this.floatProgressColor = floatProgressColor;
    }


    @Override
    public String toString() {
        return "NpColorBarBean{" +
                "npColorType=" + npColorType +
                ", currentValue=" + currentValue +
                ", cursorColor=" + cursorColor +
                ", cursorWidth=" + cursorWidth +
                ", cursorEquilateral=" + cursorEquilateral +
                ", cursorMarginColorBar=" + cursorMarginColorBar +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", isAutoCalculationValue=" + isAutoCalculationValue +
                ", npColorBarEntityList=" + npColorBarEntityList +
                ", textSize=" + textSize +
                ", useRoundMode=" + useRoundMode +
                ", isShowCursor=" + isShowCursor +
                ", valueColor=" + valueColor +
                ", isShowStartEndValue=" + isShowStartEndValue +
                ", isShowValue=" + isShowValue +
                ", valuePosition=" + valuePosition +
                ", cursorPosition=" + cursorPosition +
                ", showFloatProgress=" + showFloatProgress +
                ", floatProgressColor=" + floatProgressColor +
                ", valueFormatCallback=" + valueFormatCallback +
                '}';
    }
}
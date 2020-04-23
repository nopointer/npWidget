package npwidget.nopointer.progress.npColorBars;

import java.util.List;

import npwidget.nopointer.base.ValueFormatCallback;

/**
 * 颜色条的数据填充对象
 */
public class NpColorBarBean {

    /**
     * 当前的数据
     */
    private float currentValue = 20.0F;
    /**
     * 游标的颜色
     */
    private int cursorColor;
    /**
     * 最大值
     */
    private float maxValue = 100.0F;
    /**
     * 最小值
     */
    private float minValue = 0.0F;
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

    @Override
    public String toString() {
        return "NpColorBarBean{" +
                "currentValue=" + currentValue +
                ", cursorColor=" + cursorColor +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", npColorBarEntityList=" + npColorBarEntityList +
                ", textSize=" + textSize +
                ", useRoundMode=" + useRoundMode +
                ", isShowCursor=" + isShowCursor +
                ", valueColor=" + valueColor +
                ", valueFormatCallback=" + valueFormatCallback +
                '}';
    }
}
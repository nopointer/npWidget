package npwidget.nopointer.chart.npChartPointView;

import java.util.ArrayList;
import java.util.List;

/**
 * 实际一条数据线的对象
 */
public class NpChartPointDataBean {

    /**
     * 绘制的数据线的颜色
     */
    private int color;

    /**
     * 渐变色开始颜色
     */
    private int startColor;
    /**
     * 渐变色结束颜色
     */
    private int endColor;

    /**
     * 是否显示渐变色绘制区域
     */
    private boolean showGradient = true;

    /**
     * 线的厚度
     */
    private float lineThickness = 1;

    /**
     * 是否显示阴影
     */
    private boolean showShadow = true;

    /**
     * 阴影的颜色
     */
    private int shadowColor = 0xFF000000;


    /**
     * 阴影的半径
     */
    private float shadowRadius = 0;

    /**
     * 阴影的横线距离
     */
    private float shadowX = 0;
    /**
     * 阴影的纵向距离
     */
    private float shadowY = 0;


    /**
     * 数据线的点
     */
    private List<NpPointEntry> npLineEntryList = new ArrayList<>();

    /**
     * 最多显示多少个数据
     */
    private int showMaxData;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<NpPointEntry> getNpLineEntryList() {
        return npLineEntryList;
    }

    public void setNpLineEntryList(List<NpPointEntry> npLineEntryList) {
        this.npLineEntryList = npLineEntryList;
    }

    public int getShowMaxData() {
        return showMaxData;
    }

    public void setShowMaxData(int showMaxData) {
        this.showMaxData = showMaxData;
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public boolean isShowGradient() {
        return showGradient;
    }

    public void setShowGradient(boolean showGradient) {
        this.showGradient = showGradient;
    }

    public float getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
    }

    public boolean isShowShadow() {
        return showShadow;
    }

    public void setShowShadow(boolean showShadow) {
        this.showShadow = showShadow;
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
    }

    public float getShadowRadius() {
        return shadowRadius;
    }

    public void setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
    }

    public float getShadowX() {
        return shadowX;
    }

    public void setShadowX(float shadowX) {
        this.shadowX = shadowX;
    }

    public float getShadowY() {
        return shadowY;
    }

    public void setShadowY(float shadowY) {
        this.shadowY = shadowY;
    }

    @Override
    public String toString() {
        return "NpChartColumnDataBean{" +
                "color=" + color +
                ", startColor=" + startColor +
                ", endColor=" + endColor +
                ", showGradient=" + showGradient +
                ", lineThickness=" + lineThickness +
                ", showShadow=" + showShadow +
                ", shadowColor=" + shadowColor +
                ", shadowRadius=" + shadowRadius +
                ", shadowX=" + shadowX +
                ", shadowY=" + shadowY +
                ", npLineEntryList=" + npLineEntryList +
                ", showMaxData=" + showMaxData +
                '}';
    }
}

package npwidget.nopointer.chart.npChartLineView;

import npwidget.nopointer.chart.npChartColumnView.NpColumnEntry;

/**
 * 线的数据节点对象，最小单元
 */
public class NpLineEntry {
    /**
     * 数据
     */
    private float value;
    /**
     * 横向的小标签
     */
    private Object tag;
    public Object extraData;

    private int pointColor;
    private boolean isShowPoint;
    private int pointRadius = 10;


    /**
     * 这个点是否是要显示
     */
    private boolean isDraw = true;

    /**
     * 是否能点击
     */
    private boolean isClick = true;


    public NpLineEntry() {
    }

    public NpLineEntry(float value) {
        this.value = value;
    }

    public NpLineEntry(float value, Object tag) {
        this.value = value;
        this.tag = tag;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public NpLineEntry setExtraData(Object extraData) {
        this.extraData = extraData;
        return this;
    }

    public NpLineEntry setPointColor(int pointColor) {
        this.pointColor = pointColor;
        return setShowPoint(true);
    }

    public int getPointColor() {
        return pointColor;
    }

    public boolean isShowPoint() {
        return isShowPoint;
    }

    public NpLineEntry setShowPoint(boolean showPoint) {
        isShowPoint = showPoint;
        return this;
    }

    public int getPointRadius() {
        return pointRadius;
    }

    public NpLineEntry setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NpLineEntry{");
        sb.append("value=").append(value);
        sb.append(", tag=").append(tag);
        sb.append(", extraData=").append(extraData);
        sb.append(", pointColor=").append(pointColor);
        sb.append(", isShowPoint=").append(isShowPoint);
        sb.append(", pointRadius=").append(pointRadius);
        sb.append(", isDraw=").append(isDraw);
        sb.append(", isClick=").append(isClick);
        sb.append('}');
        return sb.toString();
    }
}

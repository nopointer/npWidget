package npwidget.nopointer.chart.npChartPointView;

import npwidget.nopointer.chart.npChartColumnView.NpColumnEntry;

/**
 * 线的数据节点对象，最小单元
 */
public class NpPointEntry {

    /**
     * 这个点是否是要显示
     */
    private boolean isDraw = true;

    /**
     * 是否能点击
     */
    private boolean isClick = true;
    /**
     * 数据
     */
    private float value;
    /**
     * 横向的小标签
     */
    private Object tag;

    public Object extraData;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public NpPointEntry() {
    }

    public NpPointEntry(float value) {
        this.value = value;
    }

    public NpPointEntry(float value, Object tag) {
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

    public NpPointEntry setExtraData(Object extraData) {
        this.extraData = extraData;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NpPointEntry{");
        sb.append("isDraw=").append(isDraw);
        sb.append(", isClick=").append(isClick);
        sb.append(", value=").append(value);
        sb.append(", tag=").append(tag);
        sb.append(", extraData=").append(extraData);
        sb.append('}');
        return sb.toString();
    }
}

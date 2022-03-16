package npwidget.nopointer.chart.npChartPointView;

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

    @Override
    public String toString() {
        return "NpColumnEntry{" +
                "value=" + value +
                ", tag='" + tag + '\'' +
                '}';
    }
}

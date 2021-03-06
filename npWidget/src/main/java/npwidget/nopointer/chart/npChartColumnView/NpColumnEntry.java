package npwidget.nopointer.chart.npChartColumnView;

/**
 * 线的数据节点对象，最小单元
 */
public class NpColumnEntry {

    /**
     * 数据
     */
    private float value;
    /**
     * 标签
     */
    private Object tag;

    public NpColumnEntry() {
    }

    public NpColumnEntry(float value) {
        this.value = value;
    }

    public NpColumnEntry(float value, Object tag) {
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

    @Override
    public String toString() {
        return "NpColumnEntry{" +
                "value=" + value +
                ", tag='" + tag + '\'' +
                '}';
    }
}

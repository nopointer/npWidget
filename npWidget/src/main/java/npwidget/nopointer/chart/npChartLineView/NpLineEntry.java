package npwidget.nopointer.chart.npChartLineView;

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

    @Override
    public String toString() {
        return "NpColumnEntry{" +
                "value=" + value +
                ", tag='" + tag + '\'' +
                '}';
    }
}

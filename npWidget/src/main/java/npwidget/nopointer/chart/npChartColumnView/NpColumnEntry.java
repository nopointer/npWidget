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

    public Object extraData;

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


    public NpColumnEntry setExtraData(Object extraData) {
        this.extraData = extraData;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NpColumnEntry{");
        sb.append("value=").append(value);
        sb.append(", tag=").append(tag);
        sb.append(", extraData=").append(extraData);
        sb.append('}');
        return sb.toString();
    }
}

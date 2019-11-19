package npwidget.nopointer.chart.npChartColumnView;

import java.util.ArrayList;
import java.util.List;

/**
 * 实际一个柱子的对象，里面允许包含多个分段柱子
 */
public class NpChartColumnDataBean {

    /**
     * 绘制的柱子颜色，如果有多种颜色的话 就表示柱子被分成多段
     */
    private List<Integer> colorList;

    /**
     * 总数据，如果有多段的话，用来计算分段的百分比
     */
    private float totalValue = 0;

    /**
     * 分段柱子的数据
     */
    private List<NpColumnEntry> npColumnEntryList = new ArrayList<>();


    public List<Integer> getColorList() {
        return colorList;
    }

    public void setColorList(List<Integer> colorList) {
        this.colorList = colorList;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public List<NpColumnEntry> getNpColumnEntryList() {
        return npColumnEntryList;
    }

    public void setNpColumnEntryList(List<NpColumnEntry> npColumnEntryList) {
        this.npColumnEntryList = npColumnEntryList;
    }
}

package npwidget.nopointer.chart;

public class ChartBean {

    /**
     * 最大值 y轴上
     */
    private float maxY;

    /**
     * 是否显示X轴
     */
    private boolean showXAxis;

    /**
     * 是否显示Y轴
     */
    private boolean showYAxis;



    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public boolean isShowXAxis() {
        return showXAxis;
    }

    public void setShowXAxis(boolean showXAxis) {
        this.showXAxis = showXAxis;
    }

    public boolean isShowYAxis() {
        return showYAxis;
    }

    public void setShowYAxis(boolean showYAxis) {
        this.showYAxis = showYAxis;
    }
}

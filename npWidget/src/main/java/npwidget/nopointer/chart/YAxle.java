package npwidget.nopointer.chart;

/**
 * Y轴
 */
public class YAxle {
    public static final int CENTER = 0;
    public static final int ABOVE = 1;
    public static final int BELOW = 2;
    /**
     * 是否是在图表里面 默认不是
     */
    public boolean insideChart = false;

    /**
     * 最大值 y轴上
     */
    public float max = 100;
    /**
     * 最小值 y轴上
     */
    public float min = 0;

    public NpValueFormatter valueFormatter;

    /**
     * 是否显示Y轴
     */
    public boolean showYAxis;

    /**
     * y轴的颜色
     */
    public int YAxisLineColor = 0xFF333333;

    //是否显示参考线
    public boolean showRefreshLine = false;

    //参考线条数
    public int refreshLineCount = 4;


    //参考值个数
    public int refreshValueCount = 2;

    /**
     * 参考值的位置
     */
    public int refreshValuePosition = ABOVE;


    /**
     * 参考值的字号
     */
    public int refreshValueTextSize = 30;

    /**
     * 参考线颜色
     */
    public int refreshLineColor = 0xFF999999;

    /**
     * 参考线颜色
     */
    public int refreshValueColor = 0xFFff0000;


    /**
     * 参考线为虚线
     */
    public boolean refreshLineDashed = false;

    public float[] dashedLineIntervals = new float[]{12, 12};

    /**
     * 是否显示参考值0
     */
    public boolean showRefreshValueZero = true;


}

package npwidget.nopointer.datetime.circlePicker;

/**
 * 仿照ios的就寝时间选择器
 * 最外层的圆环是滑动选择的区域 开始 结束按钮就在那个圆环里面
 */
public class NpTimeBean {


    /**
     * 表盘外层圆环背景的颜色
     */
    private int dialCircleBgColor = 0xFF143C4E;


    /**
     * 开始按钮和结束按钮的大小（等宽高）
     */
    private float startAndEndBtnSize = 20;


    /**
     * 开始按钮和结束按钮在圆环里面的外边距，如果填满的话 看起来就不太好看了 ，留点间隙更佳
     */
    private float startAndEndBtnMargin = 10;


    /**
     * 为了给背景透明的数字表盘一个机会，我们这里设置一个背景表盘的颜色来填充他
     */
    private int bgNumberColor = 0xFF000000;

    /**
     * 由于选中的区域是个渐变色，所以需要设置开始和结束颜色;
     */
    private int startColor = 0xFF4EE2CE;

    /**
     * 结束颜色
     */
    private int endColor = 0xFF2CC7F2;

    /**
     * 最小分钟单位 ,默认5分钟
     */
    private int minMinuteUnit = 5;


    public float getStartAndEndBtnSize() {
        return startAndEndBtnSize;
    }

    public void setStartAndEndBtnSize(float startAndEndBtnSize) {
        this.startAndEndBtnSize = startAndEndBtnSize;
    }

    public int getDialCircleBgColor() {
        return dialCircleBgColor;
    }

    public void setDialCircleBgColor(int dialCircleBgColor) {
        this.dialCircleBgColor = dialCircleBgColor;
    }

    public float getStartAndEndBtnMargin() {
        return startAndEndBtnMargin;
    }

    public void setStartAndEndBtnMargin(float startAndEndBtnMargin) {
        this.startAndEndBtnMargin = startAndEndBtnMargin;
    }

    public int getBgNumberColor() {
        return bgNumberColor;
    }

    public void setBgNumberColor(int bgNumberColor) {
        this.bgNumberColor = bgNumberColor;
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

    public int getMinMinuteUnit() {
        return minMinuteUnit;
    }

    public void setMinMinuteUnit(int minMinuteUnit) {
        this.minMinuteUnit = minMinuteUnit;
    }
}

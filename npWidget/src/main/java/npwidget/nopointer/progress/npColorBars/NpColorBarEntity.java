package npwidget.nopointer.progress.npColorBars;

/**
 * 颜色条的实体对象，既可以展示单一的颜色，又可以展示渐变色
 */
public class NpColorBarEntity {

    /**
     * 单一颜色的颜色值
     */
    private int color;

    /**
     * 渐变色的开始颜色
     */
    private int startColor;

    /**
     * 渐变色的结束颜色
     */
    private int endColor;


    private Object tag;
    /**
     * 是否是使用渐变模式，根据创建的对象自动判断，如果使用单一颜色，就不使用渐变模式
     */
    private boolean useGradientMode = false;

    public NpColorBarEntity(int color) {
        this.color = color;
        this.useGradientMode = false;
    }

    public NpColorBarEntity(int startColor, int endColor) {
        this.endColor = endColor;
        this.startColor = startColor;
        this.useGradientMode = true;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public boolean isUseGradientMode() {
        return useGradientMode;
    }

    @Override
    public String toString() {
        return "NpColorBarEntity{" +
                "color=" + color +
                ", endColor=" + endColor +
                ", startColor=" + startColor +
                ", tag=" + tag +
                ", useGradientMode=" + useGradientMode +
                '}';
    }
}
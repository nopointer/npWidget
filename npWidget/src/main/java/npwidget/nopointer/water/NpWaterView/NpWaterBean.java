package npwidget.nopointer.water.NpWaterView;

/**
 * 水波纹view的参数数据
 */
public class NpWaterBean {


    /**
     * 背景的图形，一般情况下用于圆形的场景较多
     */
    private BgType bgType = BgType.ROUND;

    public BgType getBgType() {
        return bgType;
    }

    /**
     * 进度
     */
    private float progress = 0;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public void setBgType(BgType bgType) {
        this.bgType = bgType;
    }

    public enum BgType {
        /**
         * 正方形
         */
        SQUARE,
        /**
         * 圆形
         */
        ROUND,

    }


}

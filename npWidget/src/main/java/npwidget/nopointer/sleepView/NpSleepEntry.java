package npwidget.nopointer.sleepView;

/**
 * 睡眠数据
 */
public class NpSleepEntry {

    /**
     * 睡眠状态（类型）
     */
    private int sleepType;

    /**
     * 持续时长 单位分钟
     */
    private int duration;

    /**
     * 这个状态开始的时间，单位秒
     */
    private long startTime;

    /**
     * 这种数据的颜色
     */
    private int color;

    private int selectColor;

    /**
     * 该状态的数据在等分高度中的位置（该参数只对睡眠区间的等分模式有效）,从0开始
     */
    private int position;

    public int getSleepType() {
        return sleepType;
    }

    public void setSleepType(int sleepType) {
        this.sleepType = sleepType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public NpSleepEntry() {
    }

    public NpSleepEntry(int sleepType, int duration) {
        this.sleepType = sleepType;
        this.duration = duration;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position <= 0) {
            position = 0;
        }
        this.position = position;
    }

    @Override
    public String toString() {
        return "NpSleepEntry{" +
                "sleepType=" + sleepType +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}

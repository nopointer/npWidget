package npwidget.nopointer.sleepStateView;

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

    @Override
    public String toString() {
        return "NpSleepEntry{" +
                "sleepType=" + sleepType +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}

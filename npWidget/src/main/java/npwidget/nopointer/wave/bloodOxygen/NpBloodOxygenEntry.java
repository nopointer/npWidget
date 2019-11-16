package npwidget.nopointer.wave.bloodOxygen;

public class NpBloodOxygenEntry {
    public static final int TYPE_LOW = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_HEIGHT = 2;

    private int dataType = TYPE_NORMAL;

    private float value;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}

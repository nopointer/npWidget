package npwidget.nopointer.chart;

import android.graphics.Path;

public class PathData {
    private Path path = null;
    private float maxValue = 0;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public PathData() {
    }

    public PathData(Path path, float maxValue) {
        this.path = path;
        this.maxValue = maxValue;
    }
}
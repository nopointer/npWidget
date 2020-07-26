package npwidget.extra.win.swt6.selectcarlib;

/**
 * Title: CellBean <br>
 * Description: <br>
 * Copyright (c) Wentao.Shi 2017 <br>
 * Created DateTime: 2017/9/13 14:48
 * Created by Wentao.Shi.
 */
public class CellBean {
    String text;
    float locationX,locationY;

    public CellBean() {
    }

    public CellBean(String text, float locationX, float locationY) {
        this.text = text;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }
}

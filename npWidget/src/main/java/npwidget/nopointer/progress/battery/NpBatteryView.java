package npwidget.nopointer.progress.battery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.log.ViewLog;

/**
 * 电池View 电池的小圆点默认在顶部
 */
public class NpBatteryView extends BaseView {

    /**
     * 连续显示
     */
    public static final int TYPE_CONTINUOUS = 0;
    /**
     * 分段显示
     */
    public static final int TYPE_PART = 1;

    public NpBatteryView(Context context) {
        super(context);
    }

    public NpBatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpBatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 可以绘制的RectF 边界，是一个正方形 会根据小边长适配大小
     */
    private RectF viewRectF = null;

    /**
     * 电池边框颜色
     */
    private int borderColor = 0xFF999999;
    /**
     * 外边框的圆角弧度，默认0
     */
    private float batteryBorderRadius = 8;

    /**
     * 顶部的小圆点的宽
     */
    private float topRectWidth = 22;
    /**
     * 顶部的小圆点的高
     */
    private float topRectHeight = 10;

    /**
     * 顶部小圆点的圆角弧度
     */
    private float topRectRadius = 8;

    /**
     * 边框线的厚度
     */
    private float borderWidth = 5;

    /**
     * 显示类型
     */
    private int showType = TYPE_PART;


    /**
     * 低电量颜色
     */
    private int lowBatteryColor = 0xFFFF0000;

    /**
     * 常规时的电量颜色
     */
    private int batteryColor = 0xFF00FF00;

    /**
     * 把电池分成几段显示，该参数只能针对TYPE_PART 时有效
     */
    private int partCount = 5;

    /**
     * 分段之间的间隔
     */
    private float partMargin = 2;


    /**
     * 电池内部的边距（边框线距离显示的边距）
     */
    private float innerPadding = 5;

    /**
     * 电池的电量
     */
    private int batteryValue = 30;

    /**
     * 低电量界值
     */
    private int lowBattery = 20;

    /**
     * 是否使用四舍五入,针对分段显示有用
     */
    private boolean useRounding = false;

    /**
     * 是否显示低于低电量时候的值，只显示最后一个 ,针对分段显示有用
     */
    private boolean showAtLastOne = false;


    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public float getBatteryBorderRadius() {
        return batteryBorderRadius;
    }

    public void setBatteryBorderRadius(float batteryBorderRadius) {
        this.batteryBorderRadius = batteryBorderRadius;
    }

    public float getTopRectWidth() {
        return topRectWidth;
    }

    public void setTopRectWidth(float topRectWidth) {
        this.topRectWidth = topRectWidth;
    }

    public float getTopRectHeight() {
        return topRectHeight;
    }

    public void setTopRectHeight(float topRectHeight) {
        this.topRectHeight = topRectHeight;
    }

    public float getTopRectRadius() {
        return topRectRadius;
    }

    public void setTopRectRadius(float topRectRadius) {
        this.topRectRadius = topRectRadius;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getLowBatteryColor() {
        return lowBatteryColor;
    }

    public void setLowBatteryColor(int lowBatteryColor) {
        this.lowBatteryColor = lowBatteryColor;
    }

    public int getBatteryColor() {
        return batteryColor;
    }

    public void setBatteryColor(int batteryColor) {
        this.batteryColor = batteryColor;
    }

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    public float getPartMargin() {
        return partMargin;
    }

    public void setPartMargin(float partMargin) {
        this.partMargin = partMargin;
    }

    public float getInnerPadding() {
        return innerPadding;
    }

    public void setInnerPadding(float innerPadding) {
        this.innerPadding = innerPadding;
    }

    public int getBatteryValue() {
        return batteryValue;
    }

    public void setBatteryValue(int batteryValue) {
        this.batteryValue = batteryValue;
    }

    public int getLowBattery() {
        return lowBattery;
    }

    public void setLowBattery(int lowBattery) {
        this.lowBattery = lowBattery;
    }

    public boolean isUseRounding() {
        return useRounding;
    }

    public void setUseRounding(boolean useRounding) {
        this.useRounding = useRounding;
    }

    public boolean isShowAtLastOne() {
        return showAtLastOne;
    }

    public void setShowAtLastOne(boolean showAtLastOne) {
        this.showAtLastOne = showAtLastOne;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewRectF = new RectF(getPaddingLeft()+borderWidth/2, getPaddingTop() + topRectHeight+borderWidth/2, getMeasuredWidth() - getPaddingRight()-borderWidth/2, getMeasuredHeight() - getPaddingBottom()-borderWidth/2);

        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }


    @Override
    public void invalidate() {
        draw();
        super.invalidate();
    }

    private void draw() {
        if (canDraw()) {
            clearBitmap();
            drawBg();
            if (showType == TYPE_CONTINUOUS) {
                drawBattery();
            } else {
                drawBatteryByPart();
            }
        } else {
            ViewLog.e("不能绘制");
        }
    }


    /**
     * 绘制实际电量 分段开来
     */
    private void drawBatteryByPart() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int tmpPartCount = partCount;
        if (tmpPartCount <= 0) {
            partCount = 5;
        }
        if (tmpPartCount >= 10) {
            partCount = 10;
        }

        float partRectHeight = ((viewRectF.height() - innerPadding * 2.0f - (partCount - 1) * partMargin)) / partCount;

        int tmpValue = batteryValue;
        if (tmpValue < 0) {
            tmpValue = 0;
        }
        if (tmpValue > 100) {
            tmpValue = 100;
        }
        //单个单元格代表的电量值
        float singleRectBattery = 100 / partCount;
        //真实显示的个数
        int realShowCount = 0;
        if (useRounding) {
            realShowCount = Math.round(tmpValue / singleRectBattery);
        } else {
            realShowCount = (int) (tmpValue / singleRectBattery);
        }


        if (tmpValue <= lowBattery) {
            paint.setColor(lowBatteryColor);
            if (showAtLastOne) {
                realShowCount = 1;
            }
        } else {
            paint.setColor(batteryColor);
        }
        for (int i = 0; i < realShowCount; i++) {
            RectF rectF = new RectF(viewRectF.left + innerPadding, 0, viewRectF.right - innerPadding, 0);
            rectF.bottom = viewRectF.bottom - innerPadding - i * (partMargin + partRectHeight);
            rectF.top = rectF.bottom - partRectHeight;
            canvas.drawRect(rectF, paint);
        }


    }

    /**
     * 绘制实际电量 连续的
     */
    private void drawBattery() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rectF = new RectF(viewRectF);
        rectF.left = rectF.left + innerPadding;
        rectF.top = rectF.top + innerPadding;
        rectF.right = rectF.right - innerPadding;
        rectF.bottom = rectF.bottom - innerPadding;

        int tmpValue = batteryValue;
        if (tmpValue < 0) {
            tmpValue = 0;
        }
        if (tmpValue > 100) {
            tmpValue = 100;
        }
        rectF.top = rectF.bottom - rectF.height() * (tmpValue / 100.0f);
        if (tmpValue < lowBattery) {
            paint.setColor(lowBatteryColor);
        } else {
            paint.setColor(batteryColor);
        }
        canvas.drawRect(rectF, paint);
    }


    /**
     * 绘制背景框和顶部的小圆点
     */
    private void drawBg() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(borderColor);
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(viewRectF, batteryBorderRadius, batteryBorderRadius, paint);


        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(viewRectF.centerX() - topRectWidth / 2, viewRectF.top - topRectHeight, viewRectF.centerX() + topRectWidth / 2, viewRectF.top);
        canvas.drawRoundRect(rectF, topRectRadius, topRectRadius, paint);
        rectF.top = rectF.bottom - topRectRadius;
        canvas.drawRect(rectF, paint);

    }


}

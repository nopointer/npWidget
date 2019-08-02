package npwidget.nopointer.datetime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import npwidget.nopointer.base.BaseView;


/**
 * 圆形旋转的时间图形
 */
public class NpTimeCirclePickerView extends BaseView {

    public NpTimeCirclePickerView(Context context) {
        super(context);
    }

    public NpTimeCirclePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpTimeCirclePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 可以绘制的边RectF
     */
    private RectF viewRectF = new RectF();
    /**
     * 配置数据类
     */
    private NpTimeBean npTimeBean;

    public NpTimeBean getNpTimeBean() {
        return npTimeBean;
    }

    public void setNpTimeBean(NpTimeBean npTimeBean) {
        this.npTimeBean = npTimeBean;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
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
        if (canDraw() && npTimeBean != null) {
            clearBitmap();

            //绘制背景表盘
            drawDialBg();
        }
    }


    /**
     * 绘制表盘背景
     */
    private void drawDialBg() {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(npTimeBean.getDialBgWidth());

        //选择小的的边长作为半径的参考
        float minSize = viewRectF.width() >= viewRectF.height() ? viewRectF.height() : viewRectF.width();

        //表盘背景圆环的半径
//        float raduis = (minSize - npTimeBean.getDialBgWidth()) / 2;

//        canvas.drawCircle(viewRectF.centerX(), viewRectF.centerY(), raduis, paint);


    }


}

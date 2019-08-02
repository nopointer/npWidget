package npwidget.nopointer.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

public class BaseView extends View {

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //画布
    protected Canvas canvas;
    protected Bitmap bitmap;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null && bitmap != null) {
            canvas.drawBitmap(bitmap, (canvas.getWidth() - bitmap.getWidth()) / 2, (canvas.getHeight() - bitmap.getHeight()) / 2, null);
        }
    }


    /**
     * 清除画布
     */
    protected void clearBitmap() {
        if (canvas == null)
            return;
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

    }


    /**
     * 清除画布
     *
     * @param
     */
    protected void clearBitmap(int color) {
        if (canvas == null)
            return;
        canvas.drawColor(color, PorterDuff.Mode.CLEAR);
    }

    /**
     * 是否是可以绘制
     *
     * @return
     */
    protected boolean canDraw() {
        return canvas != null && bitmap != null;
    }


    /**
     * 回收bitmap
     *
     * @param bitmap
     */
    protected void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }

    }

}

package npwidget.nopointer.wave.bloodOxygen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.util.Random;

import npwidget.nopointer.base.BaseView;

public class NpBloodOxygenWaveView extends BaseView {

    private Rect viewRectF = null;

    private NpBloodOxygenBean npBloodOxygenBean = new NpBloodOxygenBean();

    public NpBloodOxygenWaveView(Context context) {
        super(context);
    }

    public NpBloodOxygenWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpBloodOxygenWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF = new Rect(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
        bitmap = Bitmap.createBitmap(viewRectF.width(), viewRectF.height(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }


    private void draw() {
        if (canDraw() && npBloodOxygenBean != null) {
            clearBitmap();
            drawData();
        }
    }

    private void drawData() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(0, 0);
//        path.lineTo(200, 100);
//        path.lineTo(210, 500);
//        path.lineTo(220, 100);

        CornerPathEffect cornerPathEffect = new CornerPathEffect(200);
        paint.setPathEffect(cornerPathEffect);

        canvas.drawPath(path, paint);
        paint.setColor(Color.WHITE);
//        canvas.drawPoint(100, 100, paint);
//        canvas.drawPoint(200, 100, paint);
//        canvas.drawPoint(210, 500, paint);
//        canvas.drawPoint(220, 100, paint);

        int addXPosition = 0;
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            addXPosition += 20;
            path.lineTo(addXPosition, r.nextInt(viewRectF.height()));
        }
        canvas.drawPath(path, paint);

    }


}

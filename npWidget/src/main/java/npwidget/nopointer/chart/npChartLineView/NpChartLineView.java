package npwidget.nopointer.chart.npChartLineView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.chart.ChartBean;

/**
 * 曲线统计图
 */
public class NpChartLineView extends BaseView {


    private ChartBean chartBean;

    public ChartBean getChartBean() {
        return chartBean;
    }

    public void setChartBean(ChartBean chartBean) {
        this.chartBean = chartBean;
    }

    public NpChartLineView(Context context) {
        super(context);
    }

    public NpChartLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpChartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Rect viewRectF = new Rect();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRectF.left = getPaddingLeft();
        viewRectF.top = getPaddingTop();
        viewRectF.right = getMeasuredWidth() - getPaddingRight();
        viewRectF.bottom = getMeasuredHeight() - getPaddingBottom();
        bitmap = Bitmap.createBitmap(viewRectF.width(), viewRectF.height(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }


    private void draw() {
        if (canDraw() && chartBean != null) {
            drawLine();
            drawXYAxis();
        }
    }


    /**
     * 绘制xy 轴
     */
    private void drawXYAxis() {
        if (chartBean == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (chartBean.isShowXAxis()) {
            //绘制X轴 纵向高度一致，统一一个变量记录高度
            float lineBottom = viewRectF.bottom - 100;
            canvas.drawLine(viewRectF.left, lineBottom, viewRectF.right, lineBottom, paint);
        }

        if (chartBean.isShowYAxis()) {
            //绘制Y轴 横向宽度一致，统一一个变量记录宽度
            float lineLeft = viewRectF.left + 100;
            canvas.drawLine(lineLeft, viewRectF.top, lineLeft, viewRectF.bottom - 100, paint);
        }

    }


    private void doWork() {
        draw();
        invalidate();
    }


    private void drawLine() {
        Paint paint = new Paint();
        paint.setStrokeWidth(20);
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, canvas.getHeight(), new int[]{
                0xFF000000, 0xFFFFFFFF, 0xFFFF0000
        }, null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
    }

    @Override
    public void invalidate() {
        draw();
        super.invalidate();
    }
}

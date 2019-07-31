package npwidget.nopointer.sleepStateView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.base.BaseView;

/**
 * 睡眠状态图
 */
public class NpSleepStateLineView extends BaseView {
    public NpSleepStateLineView(Context context) {
        super(context);
    }

    public NpSleepStateLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpSleepStateLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 背景的图层
     */
    Bitmap backgroundBitmap = null;

    /**
     * 绘制碎片的图层
     */
    Bitmap partsBitmap = null;

    /**
     * 绘制数据曲线的图层
     */
    Bitmap lineBitmap = null;
    /**
     * 睡眠碎片图
     */
    private List<RectF> sleepPartRectList = new ArrayList<>();

    private PartSelectCallback partSelectCallback = null;

    public void setPartSelectCallback(PartSelectCallback partSelectCallback) {
        this.partSelectCallback = partSelectCallback;
    }

    /**
     * 可以绘制的边RectF
     */
    private RectF viewRectF = new RectF();

    private NpSleepStateBean npStateBean = null;

    public void setNpStateBean(NpSleepStateBean npStateBean) {
        this.npStateBean = npStateBean;
        sleepPartCount = 0;
        selectPartIndex = -1;
        if (npStateBean != null && npStateBean.getDataList() != null && npStateBean.getDataList().size() > 0) {
            sleepPartCount = npStateBean.getDataList().size();
        }
    }

    /**
     * 睡眠碎片的个数
     */
    private int sleepPartCount = 0;

    /**
     * 选中的碎片索引
     */
    private int selectPartIndex = -1;


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


    /**
     * 绘制背景图层
     */
    private void drawBg() {

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        backgroundBitmap = Bitmap.createBitmap(bitmap);
        Canvas canvas = new Canvas(backgroundBitmap);

        if (npStateBean != null && npStateBean.getBgColors() != null && npStateBean.getBgColors().size() > 0 && npStateBean.getBgType() != null) {
            //平铺的方式
            if (npStateBean.getBgType() == NpSleepStateBean.BgType.Tile) {
                int colorSize = npStateBean.getBgColors().size();
                float rectFHeight = viewRectF.height() / colorSize;
                for (int i = 0; i < colorSize; i++) {
                    RectF rectF = new RectF(viewRectF.left, 0, viewRectF.right, 0);
                    rectF.top = i * rectFHeight + viewRectF.top;
                    rectF.bottom = rectF.top + rectFHeight;
                    paint.setColor(npStateBean.getBgColors().get(i));
                    canvas.drawRect(rectF, paint);
                }
            } else {
                int colors[] = new int[npStateBean.getBgColors().size()];
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = npStateBean.getBgColors().get(i);
                }
                Shader shader = new LinearGradient(0, viewRectF.top, 0, viewRectF.bottom,
                        colors, null, Shader.TileMode.CLAMP);
                paint.setShader(shader);
                canvas.drawRect(new RectF(viewRectF), paint);
            }
        } else {
            paint.setColor(0xFFFF00FF);
            RectF rectF = new RectF(viewRectF);
            canvas.drawRect(rectF, paint);
        }
    }

    private void draw() {
        if (canDraw() && npStateBean != null) {
            clearBitmap();

            //在碎片的图层上绘制碎片数据
            drawSleepParts();

            //在碎片的图层上绘制分割线
            drawPartClipLine();

            //在碎片的图层上绘制碎片选中的效果
            drawSelectPart();

            //绘制背景效果
            drawBg();

            //绘制数据曲线
            drawDataLine();


            //先合背景和数据曲线图层

            Bitmap bgAndLineBitmap = Bitmap.createBitmap(bitmap);
            Canvas canvas = new Canvas(bgAndLineBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawBitmap(backgroundBitmap, new Matrix(), paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
            canvas.drawBitmap(lineBitmap, new Matrix(), paint);

            this.canvas.drawColor(Color.WHITE);
            //显示碎片图层
            this.canvas.drawBitmap(partsBitmap, new Matrix(), null);
            //显示背景图层和曲线图层的合并层
            this.canvas.drawBitmap(bgAndLineBitmap, new Matrix(), null);


            //绘制左右下面的文字
            float leftRightTextSize = npStateBean.getLeftRightTextSize();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            paint.setColor(npStateBean.getLeftTextColor());
            paint.setTextSize(leftRightTextSize);
            paint.setTextAlign(Paint.Align.LEFT);
            this.canvas.drawText(npStateBean.getLeftText(), viewRectF.left, viewRectF.bottom + leftRightTextSize / 1.2f, paint);
//
            paint.setTextAlign(Paint.Align.RIGHT);
            this.canvas.drawText(npStateBean.getRightText(), viewRectF.right, viewRectF.bottom + leftRightTextSize / 1.2f, paint);

            if (partsBitmap != null && !partsBitmap.isRecycled()) {
                partsBitmap.recycle();
            }

            if (bgAndLineBitmap != null && !backgroundBitmap.isRecycled()) {
                bgAndLineBitmap.recycle();
            }

            if (lineBitmap != null && !lineBitmap.isRecycled()) {
                lineBitmap.recycle();
            }

            if (backgroundBitmap != null && !backgroundBitmap.isRecycled()) {
                backgroundBitmap.recycle();
            }

//            if (bitmap != null && !bitmap.isRecycled()) {
//                bitmap.recycle();
//            }
        }
    }

    /**
     * 绘制选择的碎片效果
     */
    private void drawSelectPart() {
        if (sleepPartRectList != null && sleepPartRectList.size() > 0 && selectPartIndex != -1) {
            Canvas canvas = new Canvas(partsBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(npStateBean.getSelectPartRectColor());
            canvas.drawRect(sleepPartRectList.get(selectPartIndex), paint);

            if (partSelectCallback != null) {
                paint.setColor(npStateBean.getSelectPartTextInfoColor());
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(npStateBean.getSelectPartTextInfoSize());
                String text = partSelectCallback.drawText(npStateBean.getDataList().get(selectPartIndex));
                Log.e("显示text", text);
                canvas.drawText(text, viewRectF.centerX(), viewRectF.top + npStateBean.getSelectPartTextInfoSize() * 2, paint);
            }

        }

    }


    /**
     * 绘制背景分割线
     */
    private void drawPartClipLine() {
        Canvas canvas = new Canvas(partsBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFD3D3D3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        if (npStateBean != null && npStateBean.getBgColors() != null && npStateBean.getBgColors().size() > 0) {
            int colorSize = npStateBean.getBgColors().size();
            float lineSpaceHeight = viewRectF.height() / (colorSize * 1.0f);
            for (int i = 0; i <= colorSize; i++) {
                float yPosition = lineSpaceHeight * i + viewRectF.top;
                canvas.drawLine(viewRectF.left, yPosition, viewRectF.right, yPosition, paint);
            }
        }
    }


    /**
     * 绘制睡眠碎片图
     */
    public void drawSleepParts() {
        sleepPartRectList.clear();
        if (npStateBean != null && npStateBean.getDataList() != null && npStateBean.getDataList().size() > 0) {

            partsBitmap = Bitmap.createBitmap(bitmap);
            Canvas canvas = new Canvas(partsBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);

            float totalTimeLength = 0.0f;//总时长
            int partCount = npStateBean.getDataList().size();
            for (NpSleepEntry sleepEntry : npStateBean.getDataList()) {
                totalTimeLength += sleepEntry.getDuration();
            }
            float tmpLeft = viewRectF.left;
            for (int i = 0; i < partCount; i++) {
                //算出这段数据占总数据的百分比
                float precent = npStateBean.getDataList().get(i).getDuration() / (totalTimeLength);
                float rectWidth = precent * viewRectF.width();
                RectF rectF = new RectF(0, viewRectF.top, 0, viewRectF.bottom);
                rectF.left = tmpLeft;
                rectF.right = rectF.left + rectWidth;
                sleepPartRectList.add(rectF);
                paint.setColor(npStateBean.getDataList().get(i).getColor());
                canvas.drawRect(rectF, paint);
                tmpLeft += rectF.width();
            }
        }
    }

    /**
     * 绘制数据曲线
     */
    private void drawDataLine() {
        if (npStateBean.getDataList() != null && npStateBean.getDataList().size() > 0) {

            lineBitmap = Bitmap.createBitmap(bitmap);
            Canvas canvas = new Canvas(lineBitmap);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(npStateBean.getLineWidth());

            int dataListSize = npStateBean.getDataList().size();
            if (dataListSize == 1) {//如果只有一个数据的话,就绘制一条直线咯

            } else {
                Path dataLine = new Path();
                DataPoint startDataPoint;//开始点
                DataPoint endDataPoint;//结束点
                DataPoint p3;
                DataPoint p4;
                float x1;
                float y1;


                float totalTimeLength = 0.0f;//总时长
                for (NpSleepEntry sleepEntry : npStateBean.getDataList()) {
                    totalTimeLength += sleepEntry.getDuration();
                }

                float tmpLeft = viewRectF.left;
                for (int i = 0; i < dataListSize - 1; i++) {

                    float precent = npStateBean.getDataList().get(i).getDuration() / (totalTimeLength);
                    float rectWidth = precent * viewRectF.width();
                    RectF rectF = new RectF(0, viewRectF.top, 0, viewRectF.bottom);
                    rectF.left = tmpLeft;
                    rectF.right = rectF.left + rectWidth;

                    x1 = rectF.left;
                    y1 = getYPointHeight(npStateBean.getDataList().get(i).getSleepType(), dataListSize);

                    float x2 = rectF.right;
                    float y2 = getYPointHeight(npStateBean.getDataList().get(i + 1).getSleepType(), dataListSize);

                    startDataPoint = new DataPoint(x1, y1);
                    endDataPoint = new DataPoint(x2, y2);
                    float wt = (startDataPoint.x + endDataPoint.x) / 2;
                    p3 = new DataPoint(wt, startDataPoint.y);
                    p4 = new DataPoint(wt, endDataPoint.y);

                    dataLine.moveTo(startDataPoint.x, startDataPoint.y);
                    dataLine.cubicTo(p3.x, p3.y, p4.x, p4.y, endDataPoint.x, endDataPoint.y);

                    tmpLeft += rectF.width();
                }
                canvas.drawPath(dataLine, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (sleepPartCount != 0 && npStateBean.isEnableClickPart()) {
                for (int i = 0; i < sleepPartCount; i++) {
                    if (sleepPartRectList.get(i).contains(event.getX(), event.getY())) {
                        selectPartIndex = i;
                        break;
                    }
                }
                if (selectPartIndex != -1 && partSelectCallback != null) {
                    partSelectCallback.onSelect(selectPartIndex, npStateBean.getDataList().get(selectPartIndex));
                }
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }

    private float getYPointHeight(int xData, int dataSize) {
        float result = viewRectF.top;
        switch (xData) {
            case 0:
                result += npStateBean.getLineWidth();
                break;
            case 1:
                result += viewRectF.height() / 2;
                break;
            case 2:
                result += viewRectF.height() - npStateBean.getLineWidth();
                break;
        }
        return result;
    }


    class DataPoint implements Serializable {
        public float x;
        public float y;

        public DataPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public interface PartSelectCallback {
        /**
         * 选中的碎片回调
         *
         * @param index
         * @param npSleepEntry
         */
        void onSelect(int index, NpSleepEntry npSleepEntry);

        /**
         * 自定义绘制选中的碎片的文本内容
         *
         * @param npSleepEntry
         * @return
         */
        String drawText(NpSleepEntry npSleepEntry);
    }


}

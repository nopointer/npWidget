package npwidget.nopointer.sleepView.sleepStateAreaView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.log.ViewLog;
import npwidget.nopointer.sleepView.NpSleepEntry;

/**
 * 睡眠状态区间图
 */
public class NpSleepStateAreaView extends BaseView {
    public NpSleepStateAreaView(Context context) {
        super(context);
    }

    public NpSleepStateAreaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NpSleepStateAreaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

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

    private NpSleepStateAreaBean npStateBean = null;

    public void setNpStateBean(NpSleepStateAreaBean npStateBean) {
        this.npStateBean = npStateBean;
        sleepPartCount = 0;
        selectPartIndex = -1;
        if (npStateBean != null && npStateBean.getDataList() != null && npStateBean.getDataList().size() > 0) {
            sleepPartCount = npStateBean.getDataList().size();
        }
        ViewLog.e("碎片个数==>" + sleepPartCount);
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


    private void draw() {
        if (canDraw() && npStateBean != null) {
            clearBitmap();
//
            //先绘制分割线
            drawPartClipLine();

            //在碎片的图层上绘制碎片数据
            drawSleepParts();

            //在碎片的图层上绘制碎片选中的效果
            drawSelectPart();

            Paint paint = new Paint();
            paint.setAntiAlias(true);
//
            //绘制左右下面的文字
            float leftRightTextSize = npStateBean.getLeftRightTextSize();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            paint.setColor(npStateBean.getLeftTextColor());
            paint.setTextSize(leftRightTextSize);
            paint.setTextAlign(Paint.Align.LEFT);
            this.canvas.drawText(npStateBean.getLeftText(), viewRectF.left, viewRectF.bottom + leftRightTextSize / 1.2f, paint);
            paint.setTextAlign(Paint.Align.RIGHT);
            this.canvas.drawText(npStateBean.getRightText(), viewRectF.right, viewRectF.bottom + leftRightTextSize / 1.2f, paint);

        }
    }

    /**
     * 绘制选择的碎片效果
     */
    private void drawSelectPart() {
        if (sleepPartRectList != null && sleepPartRectList.size() > 0 && selectPartIndex != -1) {
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
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(npStateBean.getClipLineColor());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(npStateBean.getClipLineWidth());
        if (npStateBean != null) {
            int sleepStateCount = npStateBean.getSleepStateCount();
            if (sleepStateCount <= 0) {
                sleepStateCount = 3;
            }
            float lineSpaceHeight = viewRectF.height() / (sleepStateCount * 1.0f);
            for (int i = 0; i <= sleepStateCount; i++) {
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
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);

            float totalTimeLength = 0.0f;//总时长
            for (NpSleepEntry sleepEntry : npStateBean.getDataList()) {
                totalTimeLength += sleepEntry.getDuration();
            }
            float tmpLeft = viewRectF.left;
            for (int i = 0; i < sleepPartCount; i++) {
                //算出这段数据占总数据的百分比
                NpSleepEntry npSleepEntry = npStateBean.getDataList().get(i);
                float precent = npSleepEntry.getDuration() / (totalTimeLength);
                float rectWidth = precent * viewRectF.width();
                RectF rectF = new RectF(0, viewRectF.top, 0, viewRectF.bottom);

                ViewLog.e("left====>" + tmpLeft + "///" + rectWidth);
                rectF.left = tmpLeft;
                rectF.right = rectF.left + rectWidth;
                sleepPartRectList.add(rectF);
                paint.setColor(npSleepEntry.getColor());

                //如果是全高的话，就直接绘制了;等分高度的话，需要计算所在的位置
                if (npStateBean.getStateAreaType() == NpSleepStateAreaBean.StateAreaType.SPLIT_HEIGHT) {
                    float rectHeight = viewRectF.height() / npStateBean.getSleepStateCount();
                    rectF.top = viewRectF.top + viewRectF.height() * ((npSleepEntry.getPosition() * 1.0f) / npStateBean.getSleepStateCount());
                    rectF.bottom = rectF.top + rectHeight;
                    canvas.drawRect(rectF, paint);
                }
                canvas.drawRect(rectF, paint);
                tmpLeft += rectF.width();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnableTouch()){
            return false;
        }
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

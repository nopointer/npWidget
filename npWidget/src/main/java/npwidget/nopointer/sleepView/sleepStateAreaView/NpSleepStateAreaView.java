package npwidget.nopointer.sleepView.sleepStateAreaView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnBean;
import npwidget.nopointer.log.NpViewLog;
import npwidget.nopointer.sleepView.NpSleepEntry;

/**
 * 睡眠状态区间图
 */
public class NpSleepStateAreaView extends BaseView {
    public NpSleepStateAreaView(Context context) {
        super(context);
        init(context);
    }

    public NpSleepStateAreaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NpSleepStateAreaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
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

    //底部文字的高度
    private float bottomLabelRangeHeight = 0;

    private float topSpaceHeight = 0;

    //无数据时的画笔
    private Paint noDataPaint = null;

    //绘制没有数据的时候的文字大小
    private float noDataTextSize = 0;

    //绘制没有数据的时候的文字
    private String noDataText = "no data ~ ";

    //无数据是文本的颜色
    private int noDataTextColor = 0xFF888888;

    public void setNoDataTextSize(float noDataTextSize) {
        this.noDataTextSize = noDataTextSize;
    }

    public void setNoDataText(String noDataText) {
        this.noDataText = noDataText;
    }

    public void setNoDataTextColor(int noDataTextColor) {
        this.noDataTextColor = noDataTextColor;
    }

    public void setNpStateBean(NpSleepStateAreaBean npStateBean) {
        this.npStateBean = npStateBean;
        sleepPartCount = 0;
        selectPartIndex = -1;
        if (npStateBean != null && npStateBean.getDataList() != null && npStateBean.getDataList().size() > 0) {
            sleepPartCount = npStateBean.getDataList().size();
        }
        NpViewLog.log("碎片个数==>" + sleepPartCount);
    }

    /**
     * 睡眠碎片的个数
     */
    private int sleepPartCount = 0;

    /**
     * 选中的碎片索引
     */
    private int selectPartIndex = -1;


    void init(Context context) {
        noDataPaint = new Paint();
        noDataPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() == 0 || getMeasuredHeight() == 0) return;
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
            loadCfg();
            clearBitmap();
            //绘制X Y 轴
            drawXYAxis();
            //绘制参考线
            drawReferenceLine();

            if (npStateBean.getDataList() == null) {
                drawNoData();
            } else {
                //先绘制分割线
                drawPartClipLine();
                //在碎片的图层上绘制碎片数据
                drawSleepParts();
                //在碎片的图层上绘制碎片选中的效果
                drawSelectPart();
                //在底部绘制文本
                drawBottomText();
            }
        }
    }

    private void loadCfg() {
        bottomLabelRangeHeight = npStateBean.getBottomHeight();
    }

    private void drawBottomText() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //绘制左右下面的文字
        float leftRightTextSize = npStateBean.getLeftRightTextSize();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint.setColor(npStateBean.getLeftTextColor());
        paint.setTextSize(leftRightTextSize);
        paint.setTextAlign(Paint.Align.LEFT);
        this.canvas.drawText(npStateBean.getLeftText(), viewRectF.left, viewRectF.bottom - bottomLabelRangeHeight / 2 + leftRightTextSize / 2.5f, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        this.canvas.drawText(npStateBean.getRightText(), viewRectF.right, viewRectF.bottom - bottomLabelRangeHeight / 2 + leftRightTextSize / 2.5f, paint);
    }

    /**
     * 绘制选择的碎片效果
     */
    private void drawSelectPart() {
        if (sleepPartRectList != null && sleepPartRectList.size() > 0 && selectPartIndex != -1) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);


            RectF rectF = sleepPartRectList.get(selectPartIndex);


            //显示在下面
            if (npStateBean.isShowSelectLine() && npStateBean.getSelectLineShowType() == NpChartColumnBean.SelectLineShowType_BOTTOM) {
                paint.setColor(npStateBean.getSelectLineColor());
                paint.setStrokeWidth(npStateBean.getSelectLineWidth());

                float bottom = viewRectF.bottom - bottomLabelRangeHeight;
                float totalHeight = (viewRectF.height() - bottomLabelRangeHeight) * npStateBean.getSelectLineHeightScale();
                canvas.drawLine(rectF.centerX(), bottom - totalHeight, rectF.centerX(), bottom, paint);
            }

            {
                switch (npStateBean.getStateAreaSelectType()) {
                    case UNIFY:
                    default:
                        paint.setColor(npStateBean.getSelectPartRectColor());
                        break;
                    case SPECIFY:
                        paint.setColor(npStateBean.getDataList().get(selectPartIndex).getSelectColor());
                        break;
                    case TRANSLUCENT:
                        paint.setColor(npStateBean.getDataList().get(selectPartIndex).getColor());
                        paint.setAlpha(0x80);
                        break;
                }
                canvas.drawRect(rectF, paint);
            }

            if (partSelectCallback != null) {
                paint.setColor(npStateBean.getSelectPartTextInfoColor());
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(npStateBean.getSelectPartTextInfoSize());
                String text = partSelectCallback.drawText(npStateBean.getDataList().get(selectPartIndex));
                Log.e("显示text", text);
                canvas.drawText(text, viewRectF.centerX(), viewRectF.top + npStateBean.getSelectPartTextInfoSize() * 2, paint);
            }


            //显示在上面
            if (npStateBean.isShowSelectLine() && npStateBean.getSelectLineShowType() == NpChartColumnBean.SelectLineShowType_TOP) {
                paint.setColor(npStateBean.getSelectLineColor());
                paint.setStrokeWidth(npStateBean.getSelectLineWidth());
                float totalHeight = (viewRectF.height() - (viewRectF.bottom - rectF.bottom)) * npStateBean.getSelectLineHeightScale();
                canvas.drawLine(rectF.centerX(), rectF.bottom - totalHeight, rectF.centerX(), rectF.bottom, paint);
            }
        }

    }


    /**
     * 绘制背景分割线
     */
    private void drawPartClipLine() {
        if (npStateBean == null || !npStateBean.isShowClipLine()) {
            NpViewLog.log("不绘制分割线");
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(npStateBean.getClipLineColor());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(npStateBean.getClipLineWidth());
        int sleepStateCount = npStateBean.getSleepStateCount();
        if (sleepStateCount <= 0) {
            sleepStateCount = 3;
        }
        float lineSpaceHeight = (viewRectF.height() - bottomLabelRangeHeight) / (sleepStateCount * 1.0f);
        for (int i = 0; i <= sleepStateCount; i++) {
            float yPosition = lineSpaceHeight * i + viewRectF.top;
            canvas.drawLine(viewRectF.left, yPosition, viewRectF.right, yPosition, paint);
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
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);

            float totalTimeLength = 0.0f;//总时长
            for (NpSleepEntry sleepEntry : npStateBean.getDataList()) {
                totalTimeLength += sleepEntry.getDuration();
            }
            float tmpLeft = viewRectF.left;
            int tmpIndex = 0;
            for (int i = 0; i < sleepPartCount; i++) {
                //算出这段数据占总数据的百分比
                NpSleepEntry npSleepEntry = npStateBean.getDataList().get(i);
                float precent = npSleepEntry.getDuration() / totalTimeLength;
                float rectWidth = precent * viewRectF.width();
                RectF rectF = new RectF(0f, viewRectF.top, 0f, viewRectF.bottom);

                NpViewLog.log("left====>" + tmpLeft + "///" + rectWidth);
                rectF.left = tmpLeft;
                rectF.right = rectF.left + rectWidth;

                paint.setColor(npSleepEntry.getColor());

                //如果是全高的话，就直接绘制了;等分高度的话，需要计算所在的位置
                if (npStateBean.getStateAreaType() == NpSleepStateAreaBean.StateAreaType.SPLIT_HEIGHT) {
                    float rectHeight = (viewRectF.height() - bottomLabelRangeHeight) / npStateBean.getSleepStateCount();
                    rectF.top = viewRectF.top + (viewRectF.height() - bottomLabelRangeHeight) * ((npSleepEntry.getPosition() * 1.0f) / npStateBean.getSleepStateCount());
                    rectF.bottom = rectF.top + rectHeight;

                    float rectHeightRatio = npStateBean.getSplitHeightRatio();

                    float realHeight = rectF.height() * rectHeightRatio;
                    float heightCenterY = rectF.centerY();
                    rectF.top = heightCenterY - realHeight / 2.0f;
                    rectF.bottom = heightCenterY + realHeight / 2.0f;
                }
                if (tmpIndex != selectPartIndex) {
                    canvas.drawRect(rectF, paint);
                }

                sleepPartRectList.add(rectF);

                //绘制碎片之间的连线
                if (npStateBean.isShowPartLigature()) {
                    if (tmpIndex > 0) {
                        int color = npStateBean.getDataList().get(tmpIndex - 1).getColor();
                        paint.setColor(color);
                        //上一个RectF
                        RectF previousRectF = sleepPartRectList.get(tmpIndex - 1);
                        //上一个矩形在当前矩形的上方
                        if (previousRectF.bottom <= rectF.top) {
                            canvas.drawLine(previousRectF.right, previousRectF.bottom, rectF.left, rectF.top, paint);
                        }
                        if (previousRectF.top >= rectF.bottom) {   //上一个矩形在当前矩形的下方
                            canvas.drawLine(previousRectF.right, previousRectF.top, rectF.left, rectF.bottom, paint);
                        }
                    }
                }
                tmpLeft += rectF.width();
                tmpIndex++;
            }
        }
    }

    private boolean isDisallowIntercept;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnableTouch()) {
            return false;
        }
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isDisallowIntercept) {
                    isDisallowIntercept = true;
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: {
                if (sleepPartCount != 0 && npStateBean.isEnableClickPart()) {
                    for (int i = 0; i < sleepPartCount; i++) {
                        if (sleepPartRectList.get(i).left <= event.getX() && sleepPartRectList.get(i).right >= event.getX()) {
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
            break;
            case MotionEvent.ACTION_UP:
                isDisallowIntercept = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                isDisallowIntercept = false;
                break;
        }


//        return super.onTouchEvent(event);
        return true;
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


    /**
     * 绘制XY轴
     */
    private void drawXYAxis() {
        if (npStateBean == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (npStateBean.isShowXAxis()) {
            float lineHeight = npStateBean.getXAxisLineWidth();
            //绘制X轴 纵向高度一致，统一一个变量记录高度
            float lineBottom = viewRectF.bottom - bottomLabelRangeHeight - lineHeight / 2.0f;
//            npStateBean.setXAxisLineColor(0xFFFF0000);
            paint.setColor(npStateBean.getXAxisLineColor());

            NpViewLog.log("xy矩形:" + viewRectF.toString());
            paint.setStrokeWidth(lineHeight);
            canvas.drawLine(viewRectF.left, lineBottom, viewRectF.right, lineBottom, paint);
        }

        if (npStateBean.isShowYAxis()) {
            float lineHeight = npStateBean.getYAxisLineWidth();
            //绘制Y轴 横向宽度一致，统一一个变量记录宽度
            paint.setColor(npStateBean.getYAxisLineColor());
            float xPosition = viewRectF.left + lineHeight / 2.0f;
            canvas.drawLine(xPosition, viewRectF.top, xPosition, viewRectF.bottom - bottomLabelRangeHeight, paint);
        }

    }


    /**
     * 绘制参考线
     */
    private void drawReferenceLine() {
        if (!npStateBean.isShowRefreshLine()) return;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFAAAAAA);

        paint.setTextSize(30);

        paint.setPathEffect(new DashPathEffect(new float[]{12, 12}, 0));

        int refLineCount = npStateBean.getRefreshLineCount();

        float height = (viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight) / (refLineCount);

        for (int i = 1; i <= refLineCount; i++) {
            float yPosition = viewRectF.bottom - bottomLabelRangeHeight - height * i;
            canvas.drawLine(viewRectF.left, yPosition, viewRectF.right, yPosition, paint);
        }


        //绘制参考值
        int refValueCount = npStateBean.getRefreshValueCount();
        NpViewLog.log("refValueCount = " + refValueCount + " , npStateBean.getMaxY() = " + npStateBean.getMaxY() + " ,npStateBean.getMinY() = " + npStateBean.getMinY());

        if (refValueCount != 0) {

            float valueAdd = (npStateBean.getMaxY() - npStateBean.getMinY()) / refValueCount;

            height = (viewRectF.height() - bottomLabelRangeHeight - topSpaceHeight) / (refValueCount);

            for (int i = 1; i <= refValueCount; i++) {
                float yPosition = viewRectF.bottom - bottomLabelRangeHeight - height * i;
                String text = String.format(Locale.US, "%d", Float.valueOf((valueAdd * i)).intValue());
                canvas.drawText(text, viewRectF.left + 10, yPosition + 36, paint);
            }
        }
    }


    //绘制没有数据的时候的显示样子
    private void drawNoData() {
        noDataPaint.setTextAlign(Paint.Align.CENTER);
        noDataPaint.setAntiAlias(true);
        noDataPaint.setTextSize(noDataTextSize);
        noDataPaint.setColor(noDataTextColor);
        String text = TextUtils.isEmpty(noDataText) ? "no Data" : noDataText;
        NpViewLog.log("无数据 = 绘制 " + noDataText);

        canvas.drawText(text, viewRectF.centerX(), viewRectF.centerY() - bottomLabelRangeHeight / 2 + noDataTextSize / 1.95f, noDataPaint);
    }
}

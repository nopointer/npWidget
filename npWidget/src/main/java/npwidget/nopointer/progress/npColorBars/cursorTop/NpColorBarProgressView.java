package npwidget.nopointer.progress.npColorBars.cursorTop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

import java.util.List;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.base.NpPosition;
import npwidget.nopointer.base.ValueFormatCallback;
import npwidget.nopointer.log.NpViewLog;
import npwidget.nopointer.progress.npColorBars.NpColorBarBean;
import npwidget.nopointer.progress.npColorBars.NpColorBarEntity;
import npwidget.nopointer.utils.SizeUtils;

/**
 * 带有颜色条块的进度View
 */
public class NpColorBarProgressView extends BaseView {
    private int colorBarCount = 0;
    private float mMaxValue;
    private float mMinValue;
    private float mValue;
    NpColorBarBean npColorBarBean;
    private RectF viewRectF = new RectF();

    public NpColorBarProgressView(Context paramContext) {
        super(paramContext);
    }

    public NpColorBarProgressView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public NpColorBarProgressView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        viewRectF = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        draw();
    }


    private void draw() {
        if (canDraw()) {
            clearBitmap();
            if (npColorBarBean == null) {
                npColorBarBean = new NpColorBarBean();
            }
            NpColorType colorType = npColorBarBean.getNpColorType();
            mValue = npColorBarBean.getCurrentValue();
            if (colorType == NpColorType.TYPE_RANGE) {
                mMaxValue = npColorBarBean.getMaxValue();
                mMinValue = npColorBarBean.getMinValue();
                if (mMaxValue < mMinValue) {
                    float tempValue = mMaxValue;
                    mMaxValue = mMinValue;
                    mMinValue = tempValue;
                }
                if (mMinValue == mMaxValue) {
                    mMinValue += 10.0F;
                }
                if (mValue <= mMinValue) {
                    mValue = mMinValue;
                }
                if (mValue >= mMaxValue) {
                    mValue = mMaxValue;
                }
            } else {
                mMinValue = 0;
                mMaxValue = 100;
                List<NpColorBarEntity> npColorBarEntities = npColorBarBean.getNpColorBarEntityList();

                float tmpSumValue = 0;
                if (npColorBarEntities != null && npColorBarEntities.size() > 0) {
                    for (NpColorBarEntity colorBarEntity : npColorBarEntities) {
                        tmpSumValue += colorBarEntity.getDataValue();
                    }
                }
                if (tmpSumValue > 0) {
                    mMaxValue = tmpSumValue;
                }
            }
            if ((npColorBarBean.getNpColorBarEntityList() != null) && (npColorBarBean.getNpColorBarEntityList().size() > 0)) {
                drawColorBars();
                if (npColorBarBean.isUseRoundMode()) {
                    drawRoundMode();
                }
                if (npColorBarBean.isShowFloatProgress()) {
                    drawFloatProgress();
                }
                drawValues();
                if (npColorBarBean.isShowCursor()) {
                    drawCursor();
                }
            }
        }
    }

    /**
     * 绘制悬浮在最上面的进度
     */
    private void drawFloatProgress() {

        Bitmap localBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float progress = (mValue - mMinValue) / (mMaxValue - mMinValue * 1.0f);
        NpViewLog.log("progress:" + progress);
        RectF rectF = new RectF(viewRectF);
        float f = viewRectF.height() / 3.0F;
        rectF.top += f;
        rectF.bottom -= f;
        rectF.right = viewRectF.width() * progress + rectF.left;
        paint.setColor(npColorBarBean.getFloatProgressColor());

        localCanvas.drawRect(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(localBitmap, new Matrix(), paint);

    }


    /**
     * 绘制颜色条块
     */
    void drawColorBars() {
        List<NpColorBarEntity> npColorBarEntityList = npColorBarBean.getNpColorBarEntityList();
        colorBarCount = npColorBarEntityList.size();
        if (colorBarCount <= 0) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        RectF tempRectF = new RectF(viewRectF);
        float tempHeight = tempRectF.height() / 3.0F;//总view的高度的三分之一
        tempRectF.top += tempHeight;
        tempRectF.bottom -= tempHeight;
        float rectWidth = tempRectF.width() / (colorBarCount * 1.0f);


        NpViewLog.log("几段数据:" + colorBarCount);
        float sumWidth = 0;
        for (int i = 0; i < colorBarCount; i++) {
            NpColorBarEntity npColorBarEntity = npColorBarEntityList.get(i);
            RectF rectF = new RectF(tempRectF);

            if (npColorBarBean.getNpColorType() == NpColorType.TYPE_DATA) {
                rectWidth = tempRectF.width() * (npColorBarEntity.getDataValue() * 1.0f - mMinValue) / (mMaxValue - mMinValue);
            }
            rectF.left = rectF.left + sumWidth;
            rectF.right = (rectF.left + rectWidth);
            if (npColorBarEntity.isUseGradientMode()) {
                float leftX = rectF.left;
                float rightX = rectF.right;
                int startColor = npColorBarEntity.getStartColor();
                int endColor = npColorBarEntity.getEndColor();
                LinearGradient linearGradient = new LinearGradient(leftX, 0.0F, rightX, 0.0F,
                        new int[]{startColor, endColor}, null, Shader.TileMode.CLAMP);
                paint.setShader(linearGradient);
            } else {
                paint.setColor(npColorBarEntity.getColor());
            }
            canvas.drawRect(rectF, paint);
            sumWidth += rectWidth;
        }

    }

    /**
     * 绘制游标
     */
    void drawCursor() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(npColorBarBean.getCursorColor());
        //进度
        float progress = (mValue - mMinValue) / (mMaxValue - mMinValue * 1.0f);

        if (progress <= 0) {
            progress = 0;
        }
        if (progress >= 1) {
            progress = 1;
        }

        //游标尖角指向的横坐标位置
        float xPosition = viewRectF.left + viewRectF.width() * progress;
        float tempHeight = viewRectF.height() / 3.0F;//总view的高度的三分之一

        Path path = null;
        float cursorWidth = npColorBarBean.getCursorWidth();
        float cursorMarginColorBar = npColorBarBean.getCursorMarginColorBar();
        //如果在底部的话
        if (npColorBarBean.getCursorPosition() == NpPosition.BOTTOM) {
            path = new Path();
            if (npColorBarBean.isCursorEquilateral()) {
                path.moveTo(xPosition - cursorWidth / SizeUtils.getSqrt(3), viewRectF.top + tempHeight * 2 + cursorWidth + cursorMarginColorBar);
                path.lineTo(xPosition + cursorWidth / SizeUtils.getSqrt(3), viewRectF.top + tempHeight * 2 + cursorWidth + cursorMarginColorBar);
                path.lineTo(xPosition, viewRectF.top + tempHeight * 2 + cursorMarginColorBar);
            } else {
                path.moveTo(xPosition - cursorWidth, viewRectF.top + tempHeight * 2 + cursorWidth + cursorMarginColorBar);
                path.lineTo(xPosition + cursorWidth, viewRectF.top + tempHeight * 2 + cursorWidth + cursorMarginColorBar);
                path.lineTo(xPosition, viewRectF.top + tempHeight * 2 + cursorMarginColorBar);
            }

        } else if (npColorBarBean.getCursorPosition() == NpPosition.TOP) {
            //顶部
            path = new Path();
            if (npColorBarBean.isCursorEquilateral()) {
                path.moveTo(xPosition - cursorWidth / SizeUtils.getSqrt(3), viewRectF.top + tempHeight - cursorWidth - cursorMarginColorBar);
                path.lineTo(xPosition + cursorWidth / SizeUtils.getSqrt(3), viewRectF.top + tempHeight - cursorWidth - cursorMarginColorBar);
                path.lineTo(xPosition, viewRectF.top + tempHeight - cursorMarginColorBar);
            } else {
                path.moveTo(xPosition - cursorWidth, viewRectF.top + tempHeight - cursorWidth - cursorMarginColorBar);
                path.lineTo(xPosition + cursorWidth, viewRectF.top + tempHeight - cursorWidth - cursorMarginColorBar);
                path.lineTo(xPosition, viewRectF.top + tempHeight - cursorMarginColorBar);
            }
        }
        if (path != null) {
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 裁剪四个角，使之成为圆角
     */
    void drawRoundMode() {
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
        RectF localRectF = new RectF(viewRectF);
        float f = viewRectF.height() / 3.0F;
        localRectF.top += f;
        localRectF.bottom -= f;
        paint.setColor(-65536);
        Bitmap localBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        f = viewRectF.height() / 2.0F;
        localCanvas.drawRoundRect(localRectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(localBitmap, new Matrix(), paint);
    }

    /**
     * 绘制界值
     */
    void drawValues() {
        if (colorBarCount <= 0) {
            return;
        }
        if (npColorBarBean.getValuePosition() == NpPosition.HIDE) {
            NpViewLog.log("不显示范围界限值");
            return;
        }
        float tempHeight = viewRectF.height() / 3.0F;//总高度的3分之1
        //颜色块的递增宽度
        float rectWidthAdd = viewRectF.width() / colorBarCount;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(npColorBarBean.getValueColor());
        ValueFormatCallback localValueFormatCallback = npColorBarBean.getValueFormatCallback();
        paint.setTextSize(npColorBarBean.getTextSize());
        paint.setStyle(Style.FILL);
        //递增值
        float valueAddSpace = (mMaxValue - mMinValue) / colorBarCount;

        //值的纵坐标位置
        float valuePositionY = 0;
        //底部
        if (npColorBarBean.getValuePosition() == NpPosition.BOTTOM) {
            valuePositionY = viewRectF.bottom - tempHeight / 3.0F;
        } else if (npColorBarBean.getValuePosition() == NpPosition.TOP) {
            //顶部
            valuePositionY = viewRectF.bottom - tempHeight / 3.0F;
        } else if (npColorBarBean.getValuePosition() == NpPosition.CENTER) {
            //中间
            RectF rectF = new RectF(viewRectF);
            rectF.top = viewRectF.top + tempHeight;
            rectF.bottom = viewRectF.bottom - tempHeight;
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float distance = (fontMetrics.bottom - fontMetrics.top) / 2.0f - fontMetrics.bottom;
            valuePositionY = rectF.centerY() + distance;
        }
        boolean isDrawThisValue = true;//是否要绘制这个数据（部分需求可能不会绘制左右起始的值）
        for (int i = 0; i <= colorBarCount; i++) {
            //数据值
            float valueF = mMinValue + i * valueAddSpace;
            //文字的横坐标
            float textPositionX = i * rectWidthAdd + viewRectF.left;
            String valueText;
            if (localValueFormatCallback != null) {
                valueText = localValueFormatCallback.valueFormat(valueF);
            } else {
                valueText = String.format("%d", Float.valueOf(valueF).intValue());
            }
            if (i == colorBarCount) {
                paint.setTextAlign(Align.RIGHT);
                textPositionX -= npColorBarBean.getTextSize() * 0.5F;
                isDrawThisValue = npColorBarBean.isShowStartEndValue();
            } else if (i == 0) {
                paint.setTextAlign(Align.LEFT);
                textPositionX += npColorBarBean.getTextSize() * 0.5F;
                isDrawThisValue = npColorBarBean.isShowStartEndValue();
            } else {
                paint.setTextAlign(Align.CENTER);
                isDrawThisValue = true;
            }
            if (isDrawThisValue) {
                canvas.drawText(valueText, textPositionX, valuePositionY, paint);
            }
        }
    }

    public void invalidate() {
        draw();
        super.invalidate();
    }


    public void setNpColorBarBean(NpColorBarBean paramNpColorBarBean) {
        npColorBarBean = paramNpColorBarBean;
    }


}

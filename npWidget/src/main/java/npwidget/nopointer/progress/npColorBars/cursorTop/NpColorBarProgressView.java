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
import npwidget.nopointer.base.ValueFormatCallback;
import npwidget.nopointer.progress.npColorBars.NpColorBarBean;
import npwidget.nopointer.progress.npColorBars.NpColorBarEntity;

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
            mMaxValue = npColorBarBean.getMaxValue();
            mMinValue = npColorBarBean.getMinValue();
            mValue = npColorBarBean.getCurrentValue();
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
            if ((npColorBarBean.getNpColorBarEntityList() != null) && (npColorBarBean.getNpColorBarEntityList().size() > 0)) {
                drawColorBars();
                if (npColorBarBean.isUseRoundMode()) {
                    drawRoundMode();
                }
                drawValues();
                drawCursor();
            }
        }
    }


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
        float rectWidth = tempRectF.width() / colorBarCount;

        for (int i=0;i<colorBarCount;i++){
            NpColorBarEntity npColorBarEntity = npColorBarEntityList.get(i);
            RectF rectF = new RectF(tempRectF);
            rectF.left += i * rectWidth;
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
        }

    }

    /**
     * 绘制游标
     */
    void drawCursor() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(npColorBarBean.getCursorColor());
        float f1 = (mValue - mMinValue) / (mMaxValue - mMinValue * 1.0F);
        f1 = viewRectF.left + viewRectF.width() * f1;
        float f2 = viewRectF.height() / 3.0F;
        RectF localRectF = new RectF(viewRectF);
        localRectF.bottom = (localRectF.top + f2);
        localRectF.top += 5.0F;
        localRectF.bottom -= 5.0F;
        Path localPath = new Path();
        f2 = localRectF.height();
        localPath.moveTo(f1, localRectF.bottom);
        localPath.lineTo(f1 - f2 / 1.5F, localRectF.top);
        localPath.lineTo(f2 / 1.5F + f1, localRectF.top);
        canvas.drawPath(localPath, paint);
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
        float f2 = viewRectF.height() / 3.0F;
        float f3 = viewRectF.width() / colorBarCount;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(npColorBarBean.getValueColor());
        ValueFormatCallback localValueFormatCallback = npColorBarBean.getValueFormatCallback();
        paint.setTextSize(npColorBarBean.getTextSize());
        paint.setStyle(Style.FILL);
        float f4 = (mMaxValue - mMinValue) / colorBarCount;
        int i = 0;
        while (i <= colorBarCount) {
            float f1 = mMinValue;
            float f5 = i;
            String str;
            if (localValueFormatCallback != null) {
                str = localValueFormatCallback.valueFormat(Float.valueOf(f1 + f5 * f4));
            } else {
                str = String.format("%d", new Object[]{Integer.valueOf(Float.valueOf(mMinValue + i * f4).intValue())});
            }
            f1 = i * f3 + viewRectF.left;
            if (i == colorBarCount) {
                paint.setTextAlign(Align.RIGHT);
                f1 -= npColorBarBean.getTextSize() * 0.5F;
            } else if (i == 0) {
                paint.setTextAlign(Align.LEFT);
                f1 += npColorBarBean.getTextSize() * 0.5F;
            } else {
                paint.setTextAlign(Align.CENTER);
            }
            canvas.drawText(str, f1, viewRectF.bottom - f2 / 3.0F, paint);
            i += 1;
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

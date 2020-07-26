package npwidget.extra.win.swt6.selectcarlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.R;

/**
 * Title: SelectCarHeaderView <br>
 * Description: 车票类型选择器 <br>
 * Copyright (c) Wentao.Shi 2017 <br>
 * Created DateTime: 2017/9/13 10:52
 * Created by Wentao.Shi.
 */
public class SelectCharHeaderView extends View {
    private Context mContext;
    private int mColumnNum, mColorCellBackground, mColorCellText, mColorShrinkBackground;
    private float mCellRadius, mCellPaddingVertical, mCellPaddingHorizontal, mCellTextSize, mCellWidth, mCellHeight;
    private String mSrcText;
    private String[] mSrcArray;//分割后的字符
    private List<CellBean> cellList;
    private Paint mPaint;
    private RectF mRectf;
    private OnItemClickListener mItemClickListener;
    private OnShrinkButtonClickListener mShrinkButtonClickListener;

    public SelectCharHeaderView(Context context) {
        super(context);
        init(context, null);
    }

    public SelectCharHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SelectCharHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectCharHeaderView);
        mColumnNum = typedArray.getInt(R.styleable.SelectCharHeaderView_column_num, 9);
        mCellRadius = typedArray.getDimensionPixelSize(R.styleable.SelectCharHeaderView_cell_radius, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        mCellPaddingVertical = typedArray.getDimensionPixelSize(R.styleable.SelectCharHeaderView_cell_padding_vertical, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        mCellPaddingHorizontal = typedArray.getDimensionPixelSize(R.styleable.SelectCharHeaderView_cell_padding_horizontal, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mCellTextSize = typedArray.getDimensionPixelSize(R.styleable.SelectCharHeaderView_cell_text_size, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        mColorCellBackground = typedArray.getColor(R.styleable.SelectCharHeaderView_cell_background, Color.WHITE);
        mColorCellText = typedArray.getColor(R.styleable.SelectCharHeaderView_cell_text_color, Color.BLACK);
        mColorShrinkBackground = typedArray.getColor(R.styleable.SelectCharHeaderView_shrink_background, Color.GRAY);
        mSrcText = typedArray.getString(R.styleable.SelectCharHeaderView_src_text);
        if(TextUtils.isEmpty(mSrcText)){
//            mSrcText="京,津,渝,沪,冀,晋,辽,吉,黑,苏,浙,皖,闽,赣,鲁,豫,鄂,湘,粤,琼,川,贵,云,陕,甘,青,蒙,桂,宁,新,藏,使,领,警,学,港,澳";
            mSrcText="京,津,渝,沪,冀,晋,辽,吉,黑,苏,浙,皖,闽,赣,鲁,豫,鄂,湘,粤,琼,川,贵,云,陕,甘,青,蒙,桂,宁,新,藏,港,澳";
        }
        mCellHeight = typedArray.getDimensionPixelSize(R.styleable.SelectCharHeaderView_cell_height, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
        typedArray.recycle();
        mCellWidth = ScreenUtil.getScreenWidth(mContext) / mColumnNum; //每个单元格宽度
        initData();
    }

    private void initData() {
        cellList = new ArrayList<>();
        mRectf = new RectF();
        mSrcArray = mSrcText.split(",");
        for (int i = 0; i < mSrcArray.length; i++) {
            int columnIndex = i % mColumnNum;
            int rowIndex = i / mColumnNum;
            cellList.add(new CellBean(mSrcArray[i], mCellWidth * columnIndex, rowIndex * mCellHeight));
        }
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = ScreenUtil.getScreenWidth(mContext);
        int mHeight = (int) (mCellHeight * ((cellList.size() + mColumnNum) / mColumnNum));

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (CellBean bean : cellList) {
            drawCell(bean, canvas);
        }
        if (cellList.size() % mColumnNum != 0) {
            mPaint.reset();
            mPaint.setColor(mColorCellBackground);
            mRectf.set(mCellWidth * (cellList.size() % mColumnNum) + mCellPaddingHorizontal,
                    (cellList.size() / mColumnNum) * mCellHeight + mCellPaddingVertical,
                    mCellWidth * mColumnNum - mCellPaddingHorizontal,
                    (cellList.size() / mColumnNum + 1) * mCellHeight - mCellPaddingVertical);
            canvas.drawRoundRect(mRectf, mCellRadius, mCellRadius, mPaint);
            mPaint.reset();
            mPaint.setColor(mColorCellText);
            mPaint.setStrokeWidth(5);
            canvas.drawLine(mCellWidth * (cellList.size() % mColumnNum) + mCellPaddingHorizontal +
                            (mCellWidth * (mColumnNum + 1 - cellList.size() % mColumnNum) - 2 * mCellPaddingHorizontal) * 0.15f,//收缩按钮划线两侧空隙
                    (cellList.size() / mColumnNum + 0.5f - 0.15f) * mCellHeight,
                    mCellWidth * (cellList.size() % mColumnNum + mColumnNum) / 2,
                    (cellList.size() / mColumnNum + 0.5f + 0.15f) * mCellHeight,
                    mPaint);// 画线
            canvas.drawLine(mCellWidth * (cellList.size() % mColumnNum + mColumnNum) / 2,
                    (cellList.size() / mColumnNum + 0.5f + 0.15f) * mCellHeight,
                    mCellWidth * mColumnNum - mCellPaddingHorizontal -
                            (mCellWidth * (mColumnNum + 1 - cellList.size() % mColumnNum) - 2 * mCellPaddingHorizontal) * 0.15f,//收缩按钮划线两侧空隙
                    (cellList.size() / mColumnNum + 0.5f - 0.15f) * mCellHeight,
                    mPaint);// 画线
        }
    }

    private void drawCell(CellBean cellBean, Canvas canvas) {
        mPaint.reset();
        mPaint.setColor(mColorCellBackground);
        mRectf.set(cellBean.getLocationX() + mCellPaddingHorizontal,
                cellBean.getLocationY() + mCellPaddingVertical,
                cellBean.getLocationX() + mCellWidth - mCellPaddingHorizontal,
                cellBean.getLocationY() + mCellHeight - mCellPaddingVertical);
        canvas.drawRoundRect(mRectf, mCellRadius, mCellRadius, mPaint);
        mPaint.reset();
        mPaint.setColor(mColorCellText);
        mPaint.setTextSize(mCellTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int baseLineY = (int) (mRectf.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(cellBean.getText(), mRectf.centerX(), baseLineY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                checkLocation(event.getX(), event.getY());
                break;
        }
        return true;

    }

    private void checkLocation(float x, float y) {
        for (CellBean cellBean : cellList) {
            if (cellBean.getLocationX() < x && x <= cellBean.getLocationX() + mCellWidth){
                if (cellBean.getLocationY() < y && y <= cellBean.getLocationY() + mCellHeight){
                    if (mItemClickListener!=null){
                        mItemClickListener.selectString(cellBean.getText());
                    }
                    Log.d("TAG",cellBean.text);
                }else {
                    continue;
                }

            }else {
                continue;
            }
                return;
        }
        if (mShrinkButtonClickListener != null) {
            mShrinkButtonClickListener.click();
        }
        Log.d("TAG","点击了收缩按钮");
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setShrinkButtonClickListener(OnShrinkButtonClickListener shrinkButtonClickListener) {
        mShrinkButtonClickListener = shrinkButtonClickListener;
    }
}

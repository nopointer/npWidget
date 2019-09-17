package npwidget.nopointer.chart.npChartLineView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import npwidget.nopointer.base.BaseView;
import npwidget.nopointer.chart.ChartBean;
import npwidget.nopointer.chart.ShowDataType;
import npwidget.nopointer.log.ViewLog;

/**
 * 曲线统计图
 * <p>
 * 2.高度问题
 * 3.一条数据的绘制
 * 4.阴影的距离
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
        init(context);
    }

    public NpChartLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        init(context);
    }

    public NpChartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {

    }

    private Rect viewRectF = new Rect();

    //每个数据点之间的横向距离
    private float labelWidthSpace = 0;


    //最多横向显示的标签（数据）个数
    private int maxLabel = 0;

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
            if (chartBean.getNpChartLineDataBeans() != null && chartBean.getNpChartLineDataBeans().size() > 0) {
                clearBitmap();
                drawLine();
                drawXYAxis();
                drawLabels();
                drawDataLineGradient();
                drawDataLines();
            } else {
                drawNoData();
            }
        }
    }


    /**
     * 绘制XY轴
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

    private void drawLine() {
//        Paint paint = new Paint();
//        paint.setStrokeWidth(20);
//        LinearGradient linearGradient = new LinearGradient(0, 0, 0, canvas.getHeight(), new int[]{
//                0xFF000000, 0xFFFFFFFF, 0xFFFF0000
//        }, null, Shader.TileMode.CLAMP);
//        paint.setShader(linearGradient);
//        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
    }


    //绘制没有数据的时候的显示样子
    private void drawNoData() {
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(chartBean.getNoDataTextSize());
        String text = TextUtils.isEmpty(chartBean.getNoDataText()) ? "no Data" : chartBean.getNoDataText();
        canvas.drawText(text, viewRectF.centerX(), viewRectF.centerY(), paint);
    }


    //绘制标签
    private void drawLabels() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(chartBean.getLabelTextSize());
        List<String> chartLabels = chartBean.getNpLabelList();
        //最多要显示的label个数
        maxLabel = getMaxLabelCount(chartLabels);
        if (maxLabel == 0) {
            //没有数据
        } else if (maxLabel == 1) {
            //只有一个数据
        } else {
            //如果是多个标签的话
            labelWidthSpace = chartBean.getLabelSpaceWidth();
            if (chartBean.getShowDataType() == ShowDataType.Equal) {
                labelWidthSpace = viewRectF.width() / (maxLabel - 1.0f);
            }
            if (chartBean.isShowLabels()) {
                for (int i = 0; i < maxLabel; i++) {
                    float xPosition = labelWidthSpace * i + viewRectF.left;
                    String text = chartLabels.get(i);
                    if (i == 0) {
                        xPosition += 10;
                        paint.setTextAlign(Paint.Align.LEFT);
                    } else if (i == maxLabel - 1) {
                        xPosition -= 10;
                        paint.setTextAlign(Paint.Align.RIGHT);
                    } else {
                        paint.setTextAlign(Paint.Align.CENTER);
                    }
                    canvas.drawText(text, xPosition, viewRectF.bottom - 30, paint);
                }
            }
        }
    }


    /**
     * 绘制数据曲线
     */
    private void drawDataLines() {
        List<NpChartLineDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);

            if (maxLabel <= 0) {
                ViewLog.e("没有标签，不绘制渐变曲线");
            } else if (maxLabel == 1) {
                ViewLog.e("只有一个标签，不绘制渐变曲线");
            } else {
                ViewLog.e("多个标签，制渐变曲线");

                for (NpChartLineDataBean npChartLineDataBean : lineDataBeanList) {
                    List<NpLineEntry> npLineEntries = npChartLineDataBean.getNpLineEntryList();
                    if (npLineEntries != null && npLineEntries.size() > 1) {
                        paint.setStrokeWidth(npChartLineDataBean.getLineThickness());
                        if (npChartLineDataBean.isShowShadow()) {
                            float shadowRadius = npChartLineDataBean.getShadowRadius();
                            float shadowX = npChartLineDataBean.getShadowX();
                            float shadowY = npChartLineDataBean.getShadowY();
                            paint.setShadowLayer(shadowRadius, shadowX, shadowY, npChartLineDataBean.getShadowColor());
                        }
                        PathData pathData = getPath(npLineEntries, false);
                        paint.setColor(npChartLineDataBean.getColor());
                        canvas.drawPath(pathData.getPath(), paint);
                    }
                }
            }
        } else {
            ViewLog.e("chartBean.getNpChartLineDataBeans()=null !!!!");
        }
    }

    /**
     * 绘制数据的渐变曲线
     */
    private void drawDataLineGradient() {
        List<NpChartLineDataBean> lineDataBeanList = chartBean.getNpChartLineDataBeans();
        if (lineDataBeanList != null && lineDataBeanList.size() > 0) {
            if (maxLabel <= 0) {
                ViewLog.e("没有标签，不绘制渐变曲线");
            } else if (maxLabel == 1) {
                ViewLog.e("只有一个标签，不绘制渐变曲线");
            } else {
                ViewLog.e("多个标签，制渐变曲线");

                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);

                for (NpChartLineDataBean npChartLineDataBean : lineDataBeanList) {

                    List<NpLineEntry> npLineEntries = npChartLineDataBean.getNpLineEntryList();
                    if (npChartLineDataBean.isShowGradient() && npLineEntries != null && npLineEntries.size() > 1) {
                        PathData pathData = getPath(npLineEntries, true);

//                        float prenent = 1 - pathData.getMaxValue() / chartBean.getMaxY();
//                        ViewLog.e("prenent比例=====>" + prenent);

                        LinearGradient lg = new LinearGradient(0, viewRectF.top, 0, viewRectF.bottom,
                                npChartLineDataBean.getStartColor(), npChartLineDataBean.getEndColor(),
                                Shader.TileMode.CLAMP);
                        //mask画笔设置渐变效果
                        paint.setShader(lg);
                        canvas.drawPath(pathData.getPath(), paint);
                    }
                }
            }
        } else {
            ViewLog.e("chartBean.getNpChartLineDataBeans()=null !!!!");
        }
    }


    /**
     * 如果是多条曲线的话，要以最多label的曲线为参考(才能完整的展示这个最长的曲线的数据)
     *
     * @param npChartLabels
     * @return
     */
    private int getMaxLabelCount(List<String> npChartLabels) {
        if (npChartLabels != null && npChartLabels.size() > 0) {
            return npChartLabels.size();
        } else {
            return 0;
        }
    }

    private PathData getPath(List<NpLineEntry> lineEntryList, boolean isClosed) {
        PathData pathData = new PathData();
        Path path = new Path();
        float thisTotalHeight = viewRectF.height() - 100;
        float leftMargin = viewRectF.left;
        float xDisAdd = labelWidthSpace;
        float min = chartBean.getMinY();
        float max = chartBean.getMaxY();
        int dataLen = lineEntryList.size();

        if (max == min) {
            max = min + 10;
        }


        float precent1 = 0, tmpValue1 = 0;
        float precent2 = 0, tmpValue2 = 0;


        tmpValue1 = lineEntryList.get(0).getValue();
        if (tmpValue1 <= min) {
            tmpValue1 = min;
        }

        if (isClosed) {
            precent1 = (tmpValue1 - min) / (max - min);
            //先把点移动到最开始的位置
            path.moveTo(0 * xDisAdd + leftMargin, thisTotalHeight);
            path.lineTo(0 * xDisAdd + leftMargin, (thisTotalHeight * (1.0f - precent1)));
        } else {
            path.moveTo(0 * xDisAdd + leftMargin, (thisTotalHeight * (1.0f - precent1)));
        }
        List<Float> tmpList = new ArrayList<>();
        for (NpLineEntry npLineEntry : lineEntryList) {
            tmpList.add(npLineEntry.getValue());
        }
        if (tmpList.size() > 0) {
            pathData.setMaxValue(Collections.max(tmpList));
        }
        for (int i = 0; i < dataLen - 1; i++) {

            tmpValue1 = lineEntryList.get(i).getValue();
            if (tmpValue1 <= min) {
                tmpValue1 = min;
            }
            if (tmpValue1 >= max) {
                tmpValue1 = max;
            }

            precent1 = (tmpValue1 - min) / (max - min);

            float x1 = i * xDisAdd + leftMargin;
            float y1 = (thisTotalHeight * (1.0f - precent1)) + getPaddingTop() + viewRectF.top;


            tmpValue2 = lineEntryList.get(i + 1).getValue();
            if (tmpValue2 <= min) {
                tmpValue2 = min;
            }
            if (tmpValue2 >= max) {
                tmpValue2 = max;
            }

            precent2 = (tmpValue2 - min) / (max - min);

            float x2 = (i + 1) * xDisAdd + leftMargin;
            float y2 = (thisTotalHeight * (1.0f - precent2)) + getPaddingTop() + viewRectF.top;

            PointF startp = new PointF(x1, y1);
            PointF endp = new PointF(x2, y2);
            float wt = (startp.x + endp.x) / 2;
            PointF p3 = new PointF(wt, startp.y);
            PointF p4 = new PointF(wt, endp.y);

//            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
        }

        if (isClosed) {
            path.lineTo((dataLen - 1) * xDisAdd + leftMargin, (thisTotalHeight * (1.0f - precent2)));
            path.lineTo((dataLen - 1) * xDisAdd + leftMargin, thisTotalHeight);
            path.lineTo(0 * xDisAdd + leftMargin, thisTotalHeight);
        }

        pathData.setPath(path);
        return pathData;
    }


    public class PathData {
        private Path path = null;
        private float maxValue = 0;

        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

        public float getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(float maxValue) {
            this.maxValue = maxValue;
        }

        public PathData() {
        }

        public PathData(Path path, float maxValue) {
            this.path = path;
            this.maxValue = maxValue;
        }
    }

    @Override
    public void invalidate() {
        draw();
        super.invalidate();
    }
}

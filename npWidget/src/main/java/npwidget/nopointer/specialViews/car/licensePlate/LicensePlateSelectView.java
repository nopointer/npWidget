//package npwidget.nopointer.specialViews.car.licensePlate;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//
//import java.util.Collections;
//
//import npwidget.nopointer.base.BaseView;
//import npwidget.nopointer.chart.NpSelectMode;
//import npwidget.nopointer.chart.npChartColumnView.NpChartColumnDataBean;
//import npwidget.nopointer.chart.npChartColumnView.NpColumnEntry;
//import npwidget.nopointer.log.ViewLog;
//
///**
// * 车牌选择View
// */
//public class LicensePlateSelectView extends BaseView {
//
//    public LicensePlateSelectView(Context context) {
//        super(context);
//        init(context);
//    }
//
//    public LicensePlateSelectView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    public LicensePlateSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//
//    private void init(Context context) {
////        getNoDataTextSize = SizeUtils.sp2px(context, 14);
////        clickRangeWidth = SizeUtils.dp2px(context, 20);
//    }
//
//
//    private Rect viewRectF = new Rect();
//
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        viewRectF.left = getPaddingLeft();
//        viewRectF.top = getPaddingTop();
//        viewRectF.right = getMeasuredWidth() - getPaddingRight();
//        viewRectF.bottom = getMeasuredHeight() - getPaddingBottom();
//        ViewLog.e("矩形：" + viewRectF.toString());
//        if (viewRectF.width() > 0 && viewRectF.height() > 0) {
//            bitmap = Bitmap.createBitmap(viewRectF.width(), viewRectF.height(), Bitmap.Config.ARGB_8888);
//            canvas = new Canvas(bitmap);
//            draw();
//        }
//    }
//
//
//    private void draw() {
//        if (canDraw()) {
//            clearBitmap();
//            if (chartColumnBean != null) {
//                loadCfg();
//                drawXYAxis();
//                drawLabels();
//                if (chartColumnBean.getNpChartColumnDataBeans() != null && chartColumnBean.getNpChartColumnDataBeans().size() > 0) {
//                    int dataSum = 0;
//
//                    for (NpChartColumnDataBean chartColumnDataBean : chartColumnBean.getNpChartColumnDataBeans()) {
//                        for (NpColumnEntry columnEntry : chartColumnDataBean.getNpColumnEntryList()) {
//                            dataSum += columnEntry.getValue();
//                        }
//                    }
//                    if (dataSum <= 0) {
//                        drawNoData();
//                    } else {
//                        drawDataColumns();
//                        if (!hasClick) {
//                            if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST) {
//                                lastSelectIndex = 0;
//                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_LAST) {
//                                lastSelectIndex = chartColumnBean.getNpChartColumnDataBeans().size() - 1;
//                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_MIN) {
//                                for (int i = 0; i < allColumnDataSum.size(); i++) {
//                                    if (allColumnDataSum.get(i) == Collections.min(allColumnDataSum)) {
//                                        lastSelectIndex = i;
//                                        break;
//                                    }
//                                }
//                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_MAX) {
//                                for (int i = 0; i < allColumnDataSum.size(); i++) {
//                                    if (allColumnDataSum.get(i) == Collections.max(allColumnDataSum)) {
//                                        lastSelectIndex = i;
//                                        break;
//                                    }
//                                }
//                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_FIRST_NOT_NULL) {
//                                for (int i = 0; i < allColumnDataSum.size(); i++) {
//                                    if (allColumnDataSum.get(i) > chartColumnBean.getMinY()) {
//                                        lastSelectIndex = i;
//                                        break;
//                                    }
//                                }
//                            } else if (chartColumnBean.getNpSelectMode() == NpSelectMode.SELECT_LAST_NOT_NULL) {
//                                for (int i = allColumnDataSum.size() - 1; i >= 0; i--) {
//                                    if (allColumnDataSum.get(i) > chartColumnBean.getMinY()) {
//                                        lastSelectIndex = i;
//                                        break;
//                                    }
//                                }
//                            }
//
//                        }
//                        drawSelectColumn();
//                    }
//                } else {
//                    drawNoData();
//                }
//            } else {
//                drawNoData();
//            }
//        }
//    }
//
//
//}

package demo.npwidget.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import demo.npwidget.R;
import npwidget.nopointer.chart.LineType;
import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;
import npwidget.nopointer.chart.NpValueFormatter;
import npwidget.nopointer.chart.YAxle;
import npwidget.nopointer.chart.npChartLineView.NpChartLineBean;
import npwidget.nopointer.chart.npChartLineView.NpChartLineDataBean;
import npwidget.nopointer.chart.npChartLineView.NpChartLineType;
import npwidget.nopointer.chart.npChartLineView.NpChartLineView;
import npwidget.nopointer.chart.npChartLineView.NpLineEntry;
import npwidget.nopointer.chart.npChartLineView.NpSelectStyle;

public class NpLineViewActivity extends Activity {

    NpChartLineView npChartLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_view);
        npChartLineView = findViewById(R.id.npChartLineView);
        debug(false);

        findViewById(R.id.debugBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug(false);
            }
        });
    }

    private void debug(boolean isEmpty) {
        NpChartLineBean chartBean = new NpChartLineBean();
        chartBean.setShowXAxis(true);
        chartBean.setAdaptationFirstLabel(true);
        chartBean.setAdaptationLastLabel(true);
        chartBean.setBottomHeight(100);
        chartBean.setLabelRotateAngle(15);
        chartBean.setTopHeight(80);
//        chartBean.setLabelTextSize(100);

        YAxle leftYAxle = new YAxle();
        leftYAxle.showYAxis = true;
        leftYAxle.showRefreshLine = true;
        leftYAxle.refreshLineCount = 4;
        leftYAxle.max = 300;
        leftYAxle.refreshValueCount = 4;
        leftYAxle.insideChart = false;
        leftYAxle.valueFormatter = new NpValueFormatter() {
            @Override
            public String format(float value, int index) {
                return Float.valueOf(value).intValue() + "";
            }
        };
        npChartLineView.setLeftAxle(leftYAxle);

        List<NpChartLineDataBean> npChartLineDataBeans = new ArrayList<>();


        NpChartLineDataBean npChartLineDataBean1 = new NpChartLineDataBean();
        NpChartLineDataBean npChartLineDataBean2 = new NpChartLineDataBean();

        List<NpLineEntry> npLineEntries1 = new ArrayList<>();
        List<NpLineEntry> npLineEntries2 = new ArrayList<>();

        String[] week = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

        //底部标签
        List<String> labelList = new ArrayList<>();

        chartBean.setNpLabelList(labelList);

//        npLineEntries1.add(new NpLineEntry(225));
//        labelList.add("周一");
//
//        npLineEntries1.add(new NpLineEntry(150));
//        labelList.add("周二");
//
//        npLineEntries1.add(new NpLineEntry(75));
//        labelList.add("周三");
//
//        npLineEntries1.add(new NpLineEntry(300));
//        labelList.add("周四");
//
//        npLineEntries1.add(new NpLineEntry(0));
//        labelList.add("周五");
//
        for (int i = 0; i < 200; i++) {
            npLineEntries1.add(new NpLineEntry(new Random().nextInt(180) + 50).setPointColor(i % 2 == 0 ? 0xFF005500 : 0xFFFF0000).setPointRadius(i % 2 == 0 ? 10 : 15));
            npLineEntries2.add(new NpLineEntry(new Random().nextInt(150) + 50));
            labelList.add("周-" + i);
        }

//            npLineEntries1.add(new NpLineEntry(100));
//            npLineEntries1.add(new NpLineEntry(20));
//            npLineEntries1.add(new NpLineEntry(65));
//            npLineEntries1.add(new NpLineEntry(48));
//        npLineEntries1.add(new NpLineEntry(50));
//        npLineEntries1.add(new NpLineEntry(90));
//        for (int i = 1; i <= 10; i++) {
//            npLineEntries1.add(new NpPointEntry(0));
//            npLineEntries2.add(new NpPointEntry(0));
//            stringList.add(i + "D");
//        }
        npChartLineDataBean1.setLineThickness(3);
        npChartLineDataBean1.setShowGradient(false);
        npChartLineDataBean1.setShowShadow(false);

        npChartLineDataBean1.setColor(0xFFFF00FF);
        npChartLineDataBean1.setStartColor(0xFFFF0000);
        npChartLineDataBean1.setEndColor(0xFFFFFFFF);
        npChartLineDataBean1.setNpLineEntryList(npLineEntries1);
        npChartLineDataBeans.add(npChartLineDataBean1);


        npChartLineDataBean2.setLineThickness(3);
        npChartLineDataBean2.setShowGradient(false);
        npChartLineDataBean2.setShowShadow(false);

        npChartLineDataBean2.setColor(0xFFFF00FF);
        npChartLineDataBean2.setStartColor(0xFF000000);
        npChartLineDataBean2.setEndColor(0xFFFFFFFF);
        npChartLineDataBean2.setNpLineEntryList(npLineEntries2);
        npChartLineDataBeans.add(npChartLineDataBean2);

        chartBean.setNpChartLineDataBeans(npChartLineDataBeans);
        chartBean.setShowDataType(NpShowDataType.Slide);
//        chartBean.setBottomHeight(50);
        chartBean.setLabelSpaceWidth(QMUIDisplayHelper.dp2px(this, 50));
        chartBean.setMinY(0);
        chartBean.setMaxY(300);
        chartBean.setShowLabels(true);
        chartBean.setNpSelectMode(NpSelectMode.NONE);

        chartBean.setNpSelectStyle(NpSelectStyle.VERTICAL_LINE);

        chartBean.setSelectHollowCircleR(20);
        chartBean.setSelectHollowCircleWidth(6);
        chartBean.setSelectHollowCircleColor(0xFFFF0000);

        chartBean.setSelectFilledCircleColor(0xFF00FF00);
        chartBean.setSelectFilledCircleR(15);


        chartBean.setSelectLineColor(0xFF000000);
        chartBean.setSelectLineWidth(2);

        chartBean.setNpChartLineType(NpChartLineType.LINE);// 线/点

        chartBean.setLineType(LineType.Polyline);

        chartBean.setLabelTextSize(30);
        if (isEmpty) {
            npChartLineView.setChartBean(null);
        } else {
            npChartLineView.setChartBean(chartBean);
        }
        npChartLineView.invalidate();

        npChartLineView.setOnLineSelectListener(new NpChartLineView.OnLineSelectListener() {
            @Override
            public void onSelectLine(List<NpChartLineDataBean> lineDataBeans, int index) {
                Log.e("fuck,onSelectLine", lineDataBeans.size() + "///" + index);
                Log.e("fuck,tag", lineDataBeans.get(0).getNpLineEntryList().get(index).getValue() + "");
            }
        });
    }


}

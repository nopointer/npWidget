package demo.npwidget.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.List;

import demo.npwidget.R;
import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;
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
        debug(true);

        findViewById(R.id.debugBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug(false);
            }
        });
        findViewById(R.id.click_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void debug(boolean isEmpty) {
        NpChartLineBean chartBean = new NpChartLineBean();
        chartBean.setShowYAxis(false);
        chartBean.setShowXAxis(true);
        chartBean.setXAxisLineColor(0xFFAAAAAA);
        chartBean.setShowRefreshLine(true);
        chartBean.setRefreshLineCount(4);
        chartBean.setRefreshValueCount(4);
        chartBean.setAdaptationFirstLabel(true);
        chartBean.setAdaptationLastLabel(true);
        chartBean.setBottomHeight(100);
        chartBean.setLabelRotateAngle(30);
//        chartBean.setLabelTextSize(100);


        List<NpChartLineDataBean> npChartLineDataBeans = new ArrayList<>();


        NpChartLineDataBean npChartLineDataBean1 = new NpChartLineDataBean();
        NpChartLineDataBean npChartLineDataBean2 = new NpChartLineDataBean();

        List<NpLineEntry> npLineEntries1 = new ArrayList<>();
//        List<NpLineEntry> npLineEntries2 = new ArrayList<>();

        String[] week = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};


        //底部标签
        List<String> labelList = new ArrayList<>();

        for (int i = 0; i < 288; i++) {
//            labelList.add(week[i]);
            labelList.add(i + "45678");
        }

        chartBean.setNpLabelList(labelList);


        for (int i = 0; i < 288; i++) {
            npLineEntries1.add(new NpLineEntry(300 / (i+1)));
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
//        npChartLineDataBean2.setNpLineEntryList(npLineEntries2);
        npChartLineDataBeans.add(npChartLineDataBean2);

        chartBean.setNpChartLineDataBeans(npChartLineDataBeans);
        chartBean.setShowDataType(NpShowDataType.Slide);
//        chartBean.setBottomHeight(50);
        chartBean.setLabelSpaceWidth(QMUIDisplayHelper.dp2px(this, 70));
        chartBean.setMinY(0);
        chartBean.setMaxY(300);
        chartBean.setShowLabels(true);
        chartBean.setNpSelectMode(NpSelectMode.SELECT_FIRST);

        chartBean.setNpSelectStyle(NpSelectStyle.VERTICAL_LINE);

        chartBean.setSelectHollowCircleR(20);
        chartBean.setSelectHollowCircleWidth(6);
        chartBean.setSelectHollowCircleColor(0xFFFF0000);


        chartBean.setSelectFilledCircleColor(0xFF00FF00);
        chartBean.setSelectFilledCircleR(15);


        chartBean.setSelectLineColor(0xFF000000);
        chartBean.setSelectLineWidth(2);


        chartBean.setNpChartLineType(NpChartLineType.LINE);
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

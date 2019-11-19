package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.npChartLineView.NpChartLineBean;
import npwidget.nopointer.chart.npChartLineView.NpChartLineDataBean;
import npwidget.nopointer.chart.npChartLineView.NpChartLineView;
import npwidget.nopointer.chart.npChartLineView.NpLineEntry;

public class NpLineViewActivity extends Activity {

    NpChartLineView npChartLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_view);
        npChartLineView = findViewById(R.id.npChartLineView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                debug();
            }
        }, 0);
    }

    private void debug() {
        NpChartLineBean chartBean = new NpChartLineBean();
        chartBean.setShowXAxis(true);
        chartBean.setShowYAxis(false);


        List<NpChartLineDataBean> npChartLineDataBeans = new ArrayList<>();

        NpChartLineDataBean npChartLineDataBean1 = new NpChartLineDataBean();
        List<String> stringList = new ArrayList<>();

        List<NpLineEntry> npLineEntries1 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            npLineEntries1.add(new NpLineEntry((i * 7) % 30));
            stringList.add(i % 10 + "");
        }
        npChartLineDataBean1.setLineThickness(3);
        npChartLineDataBean1.setShowGradient(false);
        npChartLineDataBean1.setShowShadow(false);
        npChartLineDataBean1.setColor(0xFFFF00FF);
        npChartLineDataBean1.setStartColor(0xFF000000);
        npChartLineDataBean1.setEndColor(0xFFFFFFFF);
        npChartLineDataBean1.setNpLineEntryList(npLineEntries1);
        npChartLineDataBeans.add(npChartLineDataBean1);



        chartBean.setNpLabelList(stringList);
        chartBean.setNpChartLineDataBeans(npChartLineDataBeans);
        chartBean.setMinY(0);
        chartBean.setMaxY(60);
        chartBean.setShowLabels(true);
        chartBean.setLabelTextSize(30);
        npChartLineView.setChartBean(chartBean);
        npChartLineView.invalidate();
    }
}

package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import npwidget.nopointer.chart.npChartColumnView.NpChartColumnBean;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnDataBean;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnView;
import npwidget.nopointer.chart.npChartColumnView.NpColumnEntry;

public class NpColumnViewActivity extends Activity {

    NpChartColumnView npChartColumnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_view);
        npChartColumnView = findViewById(R.id.npChartColumnView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                debug();
            }
        }, 0);
    }

    private void debug() {
        NpChartColumnBean chartBean = new NpChartColumnBean();
        chartBean.setShowXAxis(true);
        chartBean.setShowYAxis(false);


        List<NpChartColumnDataBean> npChartColumnDataBeans = new ArrayList<>();


        List<String> stringList = new ArrayList<>();

        //柱子的颜色
        List<Integer> colorList = new ArrayList<>();
        colorList.add(0xFF00FF99);
        colorList.add(0xFFFF0099);
        Random r = new Random(10);
        for (int i = 0; i < 7; i++) {
            NpChartColumnDataBean npChartColumnDataBean = new NpChartColumnDataBean();
            npChartColumnDataBean.setColorList(colorList);

            List<NpColumnEntry> npColumnEntries = new ArrayList<>();
            npColumnEntries.add(new NpColumnEntry(r.nextInt(100)));
            npColumnEntries.add(new NpColumnEntry(r.nextInt(12) * 10));
            if (i % 7 == 0) {
                stringList.add(i + "");
            } else {
                stringList.add(i + "");
            }
            npChartColumnDataBean.setNpColumnEntryList(npColumnEntries);
            npChartColumnDataBeans.add(npChartColumnDataBean);
        }

        chartBean.setXAxisLineColor(0xFF000000);
        chartBean.setYAxisLineColor(0xFF000000);
        chartBean.setNpLabelList(stringList);
        chartBean.setMarginLeft(40);
        chartBean.setMarginRight(40);
        chartBean.setNpChartColumnDataBeans(npChartColumnDataBeans);
        chartBean.setMinY(0);
        chartBean.setMaxY(60);
        chartBean.setShowLabels(true);
        chartBean.setColumnWidth(50);
        chartBean.setLabelTextColor(0xFf00ff00);
        chartBean.setLabelTextSize(40);
        npChartColumnView.setChartColumnBean(chartBean);
        npChartColumnView.invalidate();
    }
}

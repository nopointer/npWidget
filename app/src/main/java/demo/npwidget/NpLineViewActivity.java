package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.NpShowDataType;
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
        debug();
    }

    private void debug() {
        NpChartLineBean chartBean = new NpChartLineBean();
        chartBean.setShowXAxis(true);
        chartBean.setShowYAxis(true);
        chartBean.setBottomHeight(100);
//        chartBean.setLabelTextSize(100);


        List<NpChartLineDataBean> npChartLineDataBeans = new ArrayList<>();


        List<String> stringList = new ArrayList<>();

        NpChartLineDataBean npChartLineDataBean1 = new NpChartLineDataBean();
        NpChartLineDataBean npChartLineDataBean2 = new NpChartLineDataBean();

        List<NpLineEntry> npLineEntries1 = new ArrayList<>();
        List<NpLineEntry> npLineEntries2 = new ArrayList<>();
        for (int i = 1; i <= 1; i++) {
            npLineEntries1.add(new NpLineEntry((i * 6) % 100));
            npLineEntries2.add(new NpLineEntry((i * 5) % 100));
            stringList.add(i + "");
        }
        npChartLineDataBean1.setLineThickness(3);
        npChartLineDataBean1.setShowGradient(false);
        npChartLineDataBean1.setShowShadow(false);

        npChartLineDataBean1.setColor(0xFFFF00FF);
        npChartLineDataBean1.setStartColor(0xFF000000);
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
//        npChartLineDataBeans.add(npChartLineDataBean2);

        chartBean.setNpLabelList(stringList);
        chartBean.setNpChartLineDataBeans(npChartLineDataBeans);
        chartBean.setShowDataType(NpShowDataType.Slide);
        chartBean.setLabelSpaceWidth(200);
        chartBean.setMinY(0);
        chartBean.setMaxY(100);
        chartBean.setShowLabels(true);
        chartBean.setLabelTextSize(40);
        npChartLineView.setChartBean(chartBean);
        npChartLineView.invalidate();

        npChartLineView.setOnLineSelectListener(new NpChartLineView.OnLineSelectListener() {
            @Override
            public void onSelectLine(List<NpChartLineDataBean> lineDataBeans, int index) {
                Log.e("fuck,onSelectLine", lineDataBeans.size() + "///" + index);
               Log.e("fuck,tag",lineDataBeans.get(0).getNpLineEntryList().get(index).getValue()+"");
            }
        });
    }


}

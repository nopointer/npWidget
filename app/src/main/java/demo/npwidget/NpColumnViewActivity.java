package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpValueFormatter;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnBean;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnDataBean;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnView;
import npwidget.nopointer.chart.npChartColumnView.NpColumnEntry;
import npwidget.nopointer.log.NpViewLog;

public class NpColumnViewActivity extends Activity {

    NpChartColumnView npChartColumnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_view);
        npChartColumnView = findViewById(R.id.npChartColumnView);
        debug();

        findViewById(R.id.debugBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug();
            }
        });

    }

    private void debug() {

        String[]  week =new String[]{"周一","周一","周一","周一","周一","周一","周一"};

        for (int i = 0; i <7; i++) {
            NpViewLog.log("index i:" + i);
        }

        NpChartColumnBean chartBean = new NpChartColumnBean();
        chartBean.setShowRefreshLine(true);
        chartBean.setRefreshLineCount(4);
        chartBean.setRefreshValueCount(2);
        chartBean.setTopRound(true);
        chartBean.setBottomRound(true);
        chartBean.setShowSelectValue(true);
        chartBean.setSelectValueTextSize(20);
        chartBean.setShowXAxis(true);
        chartBean.setShowYAxis(false);
        chartBean.setMinY(0);
        chartBean.setMaxY(100);
        chartBean.setNpSelectMode(NpSelectMode.SELECT_FIRST_NOT_NULL);

        chartBean.setMinY(0);

        chartBean.setYAxisFormatter(new NpValueFormatter() {
            @Override
            public String format(float value) {
                return String.format("%.2f", value);
            }
        });

        chartBean.setValueFormatter(new NpValueFormatter() {
            @Override
            public String format(float value) {
                return String.format("%.5f", value);
            }
        });

        List<NpChartColumnDataBean> npChartColumnDataBeans = new ArrayList<>();


        List<String> stringList = new ArrayList<>();

        //柱子的颜色
        List<Integer> colorList = new ArrayList<>();
        colorList.add(0xFF00FF99);
//        colorList.add(0xFFFF0099);
        for (int i = 0; i < 7; i++) {
            NpChartColumnDataBean npChartColumnDataBean = new NpChartColumnDataBean();
            npChartColumnDataBean.setColorList(colorList);

            List<NpColumnEntry> npColumnEntries = new ArrayList<>();
            if (i==0){
                npColumnEntries.add(new NpColumnEntry(0.10f));
            }else{
                npColumnEntries.add(new NpColumnEntry(0));
            }
//            npColumnEntries.add(new NpColumnEntry((i * 18)%25));
//            if (i % 7 == 0) {
//                stringList.add(i + "");
//            } else {
//                stringList.add(i + "");
//            }

            stringList.add(week[i]);

            npChartColumnDataBean.setNpColumnEntryList(npColumnEntries);
            npChartColumnDataBeans.add(npChartColumnDataBean);
        }

        chartBean.setXAxisLineColor(0xFF000000);
        chartBean.setYAxisLineColor(0xFF000000);

        List<Integer> selectColumnColorList = new ArrayList<>();
        selectColumnColorList.add(0xFFFF0000);
//        selectColumnColorList.add(0xFF00FFFF);
//        chartBean.setSelectColumnColorList(selectColumnColorList);
        chartBean.setNpLabelList(stringList);
        chartBean.setMarginLeft(40);
        chartBean.setMarginRight(40);
        chartBean.setNpChartColumnDataBeans(npChartColumnDataBeans);
        chartBean.setShowLabels(true);
        chartBean.setMaxY(60);
        chartBean.setColumnWidth(40);
        chartBean.setLabelTextColor(0xFF660000);
        chartBean.setLabelTextSize(40);
        chartBean.setBottomHeight(80);
        chartBean.setLabelTextSize(40);
        npChartColumnView.setChartColumnBean(chartBean);

        npChartColumnView.invalidate();
    }
}

package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnBean;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnDataBean;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnView;
import npwidget.nopointer.chart.npChartColumnView.NpColumnEntry;
import npwidget.nopointer.log.ViewLog;

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

        for (int i=4;i>=0;i--){
            ViewLog.e("index i:"+i);
        }

        NpChartColumnBean chartBean = new NpChartColumnBean();
        chartBean.setBottomRound(false);
        chartBean.setShowSelectValue(true);
        chartBean.setTopRound(true);
        chartBean.setShowXAxis(true);
        chartBean.setShowYAxis(false);
        chartBean.setNpSelectMode(NpSelectMode.SELECT_FIRST_NOT_NULL);

        chartBean.setMinY(0);

        List<NpChartColumnDataBean> npChartColumnDataBeans = new ArrayList<>();


        List<String> stringList = new ArrayList<>();

        //柱子的颜色
        List<Integer> colorList = new ArrayList<>();
        colorList.add(0xFF00FF99);
        colorList.add(0xFFFF0099);
        for (int i = 0; i <= 6; i++) {
            NpChartColumnDataBean npChartColumnDataBean = new NpChartColumnDataBean();
            npChartColumnDataBean.setColorList(colorList);

            List<NpColumnEntry> npColumnEntries = new ArrayList<>();
            npColumnEntries.add(new NpColumnEntry((i * 15)%30));
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
        chartBean.setSelectColumnColor(0xFF3AB8FD);
        chartBean.setNpLabelList(stringList);
        chartBean.setMarginLeft(40);
        chartBean.setMarginRight(40);
        chartBean.setNpChartColumnDataBeans(npChartColumnDataBeans);
        chartBean.setShowLabels(true);
        chartBean.setShowSelectValue(true);
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

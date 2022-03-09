package demo.npwidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.chart.NpSelectMode;
import npwidget.nopointer.chart.NpShowDataType;
import npwidget.nopointer.chart.npChartPointView.NpChartPointBean;
import npwidget.nopointer.chart.npChartPointView.NpChartPointDataBean;
import npwidget.nopointer.chart.npChartPointView.NpChartPointView;
import npwidget.nopointer.chart.npChartPointView.NpPointEntry;

public class NpPointViewActivity extends Activity {

    NpChartPointView npChartPointView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_view);
        npChartPointView = findViewById(R.id.npChartPointView);
//        debug(true);

        findViewById(R.id.debugBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug(false);
            }
        });
        findViewById(R.id.click_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NpPointViewActivity.this, MainActivity.class));
            }
        });
    }

    private void debug(boolean isEmpty) {
        NpChartPointBean chartBean = new NpChartPointBean();
        chartBean.setShowXAxis(true);
        chartBean.setXAxisLineColor(0xFF000000);
        chartBean.setYAxisLineColor(0xFF000000);
        chartBean.setShowYAxis(false);
        chartBean.setShowRefreshLine(true);
        chartBean.setRefreshLineCount(4);
        chartBean.setRefreshValueCount(2);
        chartBean.setBottomHeight(100);
//        chartBean.setLabelTextSize(100);


        List<NpChartPointDataBean> npChartLineDataBeans = new ArrayList<>();


        List<String> stringList = new ArrayList<>();

        NpChartPointDataBean npChartLineDataBean1 = new NpChartPointDataBean();
        NpChartPointDataBean npChartLineDataBean2 = new NpChartPointDataBean();

        List<NpPointEntry> npLineEntries1 = new ArrayList<>();
        List<NpPointEntry> npLineEntries2 = new ArrayList<>();

        String[]  week =new String[]{"周一","周一","周一","周一","周一","周一","周一"};


        for (int i = 1; i <= 7; i++) {
            npLineEntries1.add(new NpPointEntry((i * 6) % 11));
            npLineEntries2.add(new NpPointEntry((i * 12) % 23));
            stringList.add(week[i-1]);
        }
//        for (int i = 1; i <= 10; i++) {
//            npLineEntries1.add(new NpPointEntry(0));
//            npLineEntries2.add(new NpPointEntry(0));
//            stringList.add(i + "D");
//        }
        npChartLineDataBean1.setLineThickness(3);
        npChartLineDataBean1.setShowGradient(false);
        npChartLineDataBean1.setShowShadow(false);

        npChartLineDataBean1.setColor(0xFFFF00FF);
        npChartLineDataBean1.setStartColor(0xFF000000);
        npChartLineDataBean1.setEndColor(0xFFFFFFFF);
        npChartLineDataBean1.setNpLineEntryList(npLineEntries1);
        npChartLineDataBeans.add(npChartLineDataBean1);


//        npChartLineDataBean2.setLineThickness(3);
//        npChartLineDataBean2.setShowGradient(false);
//        npChartLineDataBean2.setShowShadow(false);
//
//        npChartLineDataBean2.setColor(0xFFFF00FF);
//        npChartLineDataBean2.setStartColor(0xFF000000);
//        npChartLineDataBean2.setEndColor(0xFFFFFFFF);
//        npChartLineDataBean2.setNpLineEntryList(npLineEntries2);
//        npChartLineDataBeans.add(npChartLineDataBean2);

        chartBean.setNpLabelList(stringList);
        chartBean.setNpChartLineDataBeans(npChartLineDataBeans);
        chartBean.setShowDataType(NpShowDataType.Equal);
        chartBean.setLabelSpaceWidth(QMUIDisplayHelper.dp2px(this, 70));
        chartBean.setMinY(0);
        chartBean.setMaxY(20);
        chartBean.setShowLabels(true);
        chartBean.setNpSelectMode(NpSelectMode.SELECT_LAST);
//        chartBean.setLabelTextSize(40);
        if (isEmpty) {
            npChartPointView.setChartBean(null);
        } else {
            npChartPointView.setChartBean(chartBean);
        }
        npChartPointView.invalidate();

        npChartPointView.setOnPointSelectListener(new NpChartPointView.OnPointSelectListener() {
            @Override
            public void onSelectPoint(List<NpChartPointDataBean> lineDataBeans, int index) {
                Log.e("fuck,onSelectLine", lineDataBeans.size() + "///" + index);
                Log.e("fuck,tag", lineDataBeans.get(0).getNpLineEntryList().get(index).getValue() + "");

            }
        });
    }


}

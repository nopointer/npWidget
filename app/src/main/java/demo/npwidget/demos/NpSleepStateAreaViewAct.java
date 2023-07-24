package demo.npwidget.demos;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import demo.npwidget.R;
import npwidget.nopointer.chart.npChartColumnView.NpChartColumnBean;
import npwidget.nopointer.sleepView.NpSleepEntry;
import npwidget.nopointer.sleepView.sleepStateAreaView.NpSleepStateAreaBean;
import npwidget.nopointer.sleepView.sleepStateAreaView.NpSleepStateAreaView;
import npwidget.nopointer.sleepView.sleepStateLineView.NpSleepStateLineView;

/**
 * 睡眠状态图
 */
public class NpSleepStateAreaViewAct extends AppCompatActivity {

    //睡眠状态区域图
    private NpSleepStateAreaView npSleepStateAreaView;

    //睡眠状态线图
    private NpSleepStateLineView npSleepStateLineView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_sleep);
        npSleepStateAreaView = findViewById(R.id.npSleepStateAreaView);
        npSleepStateLineView = findViewById(R.id.npSleepStateLineView);

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDebug(false);
            }
        });
        loadDebug(true);
    }


    private void loadDebug(boolean notNull) {
        NpSleepStateAreaBean npStateBean = new NpSleepStateAreaBean();
        npStateBean.setStateAreaType(NpSleepStateAreaBean.StateAreaType.SPLIT_HEIGHT);
        npStateBean.setSplitHeightRatio(0.5f);

        npStateBean.setStateAreaSelectType(NpSleepStateAreaBean.StateAreaSelectType.UNIFY);

        //X Y 轴的配置
        npStateBean.setShowXAxis(true);
        npStateBean.setXAxisLineColor(0xFF0000FF);
        npStateBean.setShowYAxis(false);

        //参考线
        npStateBean.setShowRefreshLine(true);
        npStateBean.setRefreshLineCount(3);
        npStateBean.setMaxY(100);
        npStateBean.setMinY(10);
        npStateBean.setRefreshValueCount(0);

        //分割线的配置
        npStateBean.setShowClipLine(false);
        npStateBean.setClipLineWidth(1);
        npStateBean.setClipLineColor(Color.BLACK);

        npStateBean.setShowSelectLine(true);
        npStateBean.setSelectLineColor(0xFFFF0000);
        npStateBean.setSelectLineWidth(3);
        npStateBean.setSelectLineHeightScale(0.80f);
        npStateBean.setSelectLineShowType(NpChartColumnBean.SelectLineShowType_BOTTOM);

        npStateBean.setSelectPartRectColor(0xFF808080);
        npStateBean.setSleepStateCount(3);
        List<NpSleepEntry> dataList = new ArrayList<>();

        String string = "5DDC10C40F02 5DDC14480500 5DDC15740F02 5DDC18F80502";
        string += "5DDC1A242302 5DDC22580F01 5DDC25DC0502 5DDC27080503 5DDC28343C02 5DDC36442D01 5DDC40D00A025DDC432805035DDC445419025DDC4A3005015DDC4B5C23025DDC53900A015DDC55E83C025DDC63F805015DDC65245002";
        string = string.replace(" ", "");
        int length = string.length() / 12;
        for (int i = 0; i < length; i++) {
            NpSleepEntry npSleepEntry = new NpSleepEntry();
            int type = Integer.valueOf(string.substring(12 * i + 10, 12 * i + 12));
            npSleepEntry.setSleepType(type);
            if (npSleepEntry.getSleepType() == 0) {
                npSleepEntry.setColor(0xFF129EF7);
                npSleepEntry.setSelectColor(0xFFFF0000);
                npSleepEntry.setPosition(0);
            } else if (npSleepEntry.getSleepType() == 1) {
                npSleepEntry.setColor(0xFF2FE7E7);
                npSleepEntry.setSelectColor(0xFF00FF00);
                npSleepEntry.setPosition(1);
            } else if (npSleepEntry.getSleepType() == 2) {
                npSleepEntry.setPosition(2);
                npSleepEntry.setColor(0xFFFF59B3);
                npSleepEntry.setSelectColor(0xFFFFFF00);
            }
            npSleepEntry.setDuration(Integer.parseInt(string.substring(12 * i + 8, 12 * i + 10), 16));
            dataList.add(npSleepEntry);
        }
        if (notNull) {
            npStateBean.setDataList(dataList);
        } else {
            npStateBean.setDataList(null);
        }
        npStateBean.setLeftText("00:00");
        npStateBean.setLeftTextColor(0xFF000000);
        npStateBean.setRightText("23:59");
        npStateBean.setRightTextColor(0xFF000000);
        npStateBean.setEnableClickPart(true);

        npSleepStateAreaView.setPartSelectCallback(new NpSleepStateAreaView.PartSelectCallback() {
            @Override
            public void onSelect(int index, NpSleepEntry npSleepEntry) {

            }

            @Override
            public String drawText(NpSleepEntry npSleepEntry) {
                String text = "";
                long startTime = npSleepEntry.getStartTime();
                long endTime = npSleepEntry.getDuration() * 60 + startTime;

                String timeString = new SimpleDateFormat("HH:mm").format(startTime * 1000L) + " ~ " + new SimpleDateFormat("HH:mm").format(endTime * 1000L);


                Log.e("npSleepEntry", npSleepEntry.toString());

                switch (npSleepEntry.getSleepType()) {
                    case 0:
                        text = "清醒:" + timeString;
                        break;
                    case 1:
                        text = "浅睡:" + timeString;
                        break;
                    case 2:
                        text = "深睡:" + timeString;
                        break;
                }
                return text;
            }
        });
        npStateBean.setLeftRightTextSize(30);
        npSleepStateAreaView.setNoDataTextSize(40);
        npSleepStateAreaView.setNoDataTextColor(0xFFFF0000);
        npSleepStateAreaView.setNoDataText("无数据");
        npSleepStateAreaView.setNpStateBean(npStateBean);
        npSleepStateAreaView.invalidate();
    }


}

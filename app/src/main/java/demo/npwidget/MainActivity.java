package demo.npwidget;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.progress.NpCircleProgressView;
import npwidget.nopointer.sleepView.NpSleepEntry;
import npwidget.nopointer.sleepView.sleepStateAreaView.NpSleepStateAreaBean;
import npwidget.nopointer.sleepView.sleepStateAreaView.NpSleepStateAreaView;


public class MainActivity extends Activity {


    private NpSleepStateAreaView npStateLineView;

    NpCircleProgressView npCircleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        npStateLineView = findViewById(R.id.NpChartLineView);
        npCircleProgressView = findViewById(R.id.npCircleProgressView);

        npCircleProgressView.setCircleWidth(10);
        npCircleProgressView.setStartAngle(-90);
        npCircleProgressView.setCircleProgressBgColor(0xFF999999);
        npCircleProgressView.setCircleProgressColor(0xFFFF0000);
        npCircleProgressView.updateProgress(0.5f);
//        loadDebug();

//        startService(new Intent(this, KeepService.class));
//        startService(new Intent(this, GuardService.class));

//        SettingsPageUtils.goToSettingPage(this, PageType.AUTO_START);

//        startActivity(new Intent(this, NpTimeCirclePickerViewActivity.class));
//        startActivity(new Intent(this, NpLineViewActivity.class));
        //进度
//        startActivity(new Intent(this, NpRectViewActivity.class));
//        startActivity(new Intent(this, NpOxWaveViewActivity.class));

//        startActivity(new Intent(this, NpDateTypeActivity.class));
//        startActivity(new Intent(this, NpColumnViewActivity.class));
//        startActivity(new Intent(this, NpCountDwonViewActivity.class));
//        startActivity(new Intent(this, NpPolylineViewActivity.class));

    loadDebug();

    }


    private void loadDebug() {
        NpSleepStateAreaBean npStateBean = new NpSleepStateAreaBean();
        npStateBean.setStateAreaType(NpSleepStateAreaBean.StateAreaType.SPLIT_HEIGHT);
        npStateBean.setClipLineWidth(2);
        npStateBean.setClipLineColor(Color.BLACK);
        npStateBean.setSelectPartRectColor(0xFFE5E5E5);
        List<NpSleepEntry> dataList = new ArrayList<>();

        String string = "5DDC10C40F02 5DDC14480501 5DDC15740F02 5DDC18F80501";
        string += "5DDC1A242302 5DDC22580F01 5DDC25DC0502 5DDC27080503 5DDC28343C02 5DDC36442D01 5DDC40D00A025DDC432805035DDC445419025DDC4A3005015DDC4B5C23025DDC53900A015DDC55E83C025DDC63F805015DDC65245002";
        string = string.replace(" ", "");
        int length = string.length() / 12;
        for (int i = 0; i < length; i++) {
            NpSleepEntry npSleepEntry = new NpSleepEntry();
            npSleepEntry.setSleepType(3 - Integer.valueOf(string.substring(12 * i + 10, 12 * i + 12)));
            if (npSleepEntry.getSleepType() == 0) {
                npSleepEntry.setColor(0xFF129EF7);
                npSleepEntry.setPosition(0);
            } else if (npSleepEntry.getSleepType() == 1) {
                npSleepEntry.setColor(0xFF2FE7E7);
                npSleepEntry.setPosition(1);
            } else {
                npSleepEntry.setPosition(2);
                npSleepEntry.setColor(0xFFFF59B3);
            }
            npSleepEntry.setDuration(Integer.parseInt(string.substring(12 * i + 8, 12 * i + 10), 16));
            dataList.add(npSleepEntry);
        }
        npStateBean.setDataList(dataList);

        npStateBean.setLeftText("00:00");
        npStateBean.setLeftTextColor(0xFF000000);
        npStateBean.setRightText("23:59");
        npStateBean.setRightTextColor(0xFF000000);
        npStateBean.setEnableClickPart(true);

        npStateLineView.setPartSelectCallback(new NpSleepStateAreaView.PartSelectCallback() {
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
        npStateLineView.setNpStateBean(npStateBean);
        npStateLineView.invalidate();

    }
}

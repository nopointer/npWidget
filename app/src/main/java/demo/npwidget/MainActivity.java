package demo.npwidget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.progress.npColorBars.NpColorBarBean;
import npwidget.nopointer.progress.npColorBars.NpColorBarEntity;
import npwidget.nopointer.progress.npColorBars.cursorTop.NpColorBarProgressView;
import npwidget.nopointer.sleepView.NpSleepEntry;
import npwidget.nopointer.sleepView.sleepStateAreaView.NpSleepStateAreaBean;
import npwidget.nopointer.sleepView.sleepStateAreaView.NpSleepStateAreaView;
import npwidget.nopointer.utils.SizeUtils;


public class MainActivity extends Activity {


    private NpSleepStateAreaView npStateLineView;

    NpColorBarProgressView npCircleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        npStateLineView = findViewById(R.id.NpChartLineView);
        npCircleProgressView = findViewById(R.id.npCircleProgressView);

//        loadDebug();

//        startService(new Intent(this, KeepService.class));
//        startService(new Intent(this, GuardService.class));

//        SettingsPageUtils.goToSettingPage(this, PageType.AUTO_START);

//        startActivity(new Intent(this, NpTimeCirclePickerViewActivity.class));
//        startActivity(new Intent(this, NpCountDwonViewActivity.class));
//        startActivity(new Intent(this, NpLineViewActivity.class));
        //进度
        startActivity(new Intent(this, NpRectViewActivity.class));
//        startActivity(new Intent(this, NpOxWaveViewActivity.class));

//        startActivity(new Intent(this, NpDateTypeActivity.class));
//        startActivity(new Intent(this, NpColumnViewActivity.class));
//        startActivity(new Intent(this, NpCountDwonViewActivity.class));
//        startActivity(new Intent(this, NpPolylineViewActivity.class));

        loadDebug();

        loadColorBar();

    }


    private void loadColorBar() {
//        NpColorBarBean localNpColorBarBean = new NpColorBarBean();
//        localNpColorBarBean.setMinValue(35.0F);
//        localNpColorBarBean.setCursorColor(-65536);
//        localNpColorBarBean.setValueColor(-16777216);
//        localNpColorBarBean.setCurrentValue(-38.5F);
//        localNpColorBarBean.setMaxValue(42.0F);
//        localNpColorBarBean.setUseRoundMode(true);
//        ArrayList localArrayList = new ArrayList();
//        localArrayList.add(new NpColorBarEntity(0xFFFF00FF, 0xFFFF0000));
//        localArrayList.add(new NpColorBarEntity(0xFFFF0000, 0xFF0000FF));
//        localArrayList.add(new NpColorBarEntity(-1771130, -6779));
//        localArrayList.add(new NpColorBarEntity(-6779, -11643));
//        localNpColorBarBean.setNpColorBarEntityList(localArrayList);
//        this.npCircleProgressView.setNpColorBarBean(localNpColorBarBean);
//        this.npCircleProgressView.invalidate();


        NpColorBarBean localNpColorBarBean = new NpColorBarBean();
        localNpColorBarBean.setShowStartEndValue(true);
        localNpColorBarBean.setMinValue(35.0F);
        localNpColorBarBean.setCursorColor(0xFFFF3500);
        localNpColorBarBean.setValueColor(0xFFFF0000);
        localNpColorBarBean.setTextSize(SizeUtils.sp2px(this,14));
        localNpColorBarBean.setValuePosition(NpColorBarBean.positionCenter);
        localNpColorBarBean.setCursorPosition(NpColorBarBean.PositionTop);
        localNpColorBarBean.setCursorEquilateral(true);
        localNpColorBarBean.setCursorWidth(34);
        localNpColorBarBean.setCursorMarginColorBar(5);
        localNpColorBarBean.setCurrentValue(38.5F);
        localNpColorBarBean.setMaxValue(42.0F);
        localNpColorBarBean.setUseRoundMode(true);
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new NpColorBarEntity(0xFF05B4F2, 0xFF39F2FF));//35 36
        localArrayList.add(new NpColorBarEntity(0xFF39F2FF, 0xFFF4F785));//36 37
//        localArrayList.add(new NpColorBarEntity(0xFFFFE785, 0xFFFFE585));//37 38
//        localArrayList.add(new NpColorBarEntity(0xFFFFD285, 0xFFFFD285));//38 39
//        localArrayList.add(new NpColorBarEntity(0xFFFFBE86, 0xFFFFB886));//39 40
//        localArrayList.add(new NpColorBarEntity(0xFFFFA886, 0xFFFFA886));//40 41
//        localArrayList.add(new NpColorBarEntity(0xFFFF9386, 0xFFFF9386));//41 42
        localNpColorBarBean.setNpColorBarEntityList(localArrayList);
        npCircleProgressView.setNpColorBarBean(localNpColorBarBean);
        npCircleProgressView.invalidate();

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

package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.keepAlive.utils.PageType;
import com.keepAlive.utils.SettingsPageUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import npwidget.nopointer.sleepStateView.NpSleepEntry;
import npwidget.nopointer.sleepStateView.NpSleepStateBean;
import npwidget.nopointer.sleepStateView.NpSleepStateLineView;


public class MainActivity extends Activity {


    private TextView time_tv;
    private NpSleepStateLineView npStateLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        npStateLineView = findViewById(R.id.NpChartLineView);
        time_tv = findViewById(R.id.time_tv);
        loadDebug();

//        startService(new Intent(this, KeepService.class));
//        startService(new Intent(this, GuardService.class));

        SettingsPageUtils.goToSettingPage(this, PageType.AUTO_START);

    }


    private void loadDebug() {
        NpSleepStateBean npStateBean = new NpSleepStateBean();
        List<Integer> bgColorList = new ArrayList<>();
        bgColorList.add(0xFF76A2E4);
        bgColorList.add(0xFFA3A4FF);
        bgColorList.add(0xFF6039FF);
        npStateBean.setBgColors(bgColorList);

        npStateBean.setBgType(NpSleepStateBean.BgType.Gradient);
        npStateBean.setSelectPartRectColor(0xFFE5E5E5);

        List<NpSleepEntry> dataList = new ArrayList<>();

        String string = "5c6d7e300f025c6d81b405015c6d82e00f 025c6d86 6414015c 6d8b1405 025c6d8c 4005035c 6d8d6c05 025c6d8e 9805015c 6d8fc428 025c6d99 2405035c 6d9a5005 025c6d9b 7c05015c 6d9ca805 025c6d9d d40a035c 6da02c1e 025c6da7 341e015c 6dae3c0f 025c6db1 c01e015c 6db8c80f 025c6dbc 4c0f015c 6dbfd014 025c6dc4 800f015c 6dc8040f 025c6dcb 880a015c 6dcde00f 025c6dd1 640a015c 6dd3bc0f 025c6dd7 4019015c 6ddd1c05 025c6dde480f035c 6de0a000 00";
        string = string.replace(" ", "");
        int length = string.length() / 12;
        for (int i = 0; i < length; i++) {
            NpSleepEntry npSleepEntry = new NpSleepEntry();
            npSleepEntry.setSleepType(3 - Integer.valueOf(string.substring(12 * i + 10, 12 * i + 12)));
            if (npSleepEntry.getSleepType() == 0) {
                npSleepEntry.setColor(0x0000FFFF);
            } else if (npSleepEntry.getSleepType() == 1) {
                npSleepEntry.setColor(0x00FF0000);
            } else {
                npSleepEntry.setColor(0x00FFFF00);
            }
            npSleepEntry.setDuration(Integer.parseInt(string.substring(12 * i + 8, 12 * i + 10), 16));
            dataList.add(npSleepEntry);
        }
        npStateBean.setDataList(dataList);

        npStateBean.setLeftText("00:00");
        npStateBean.setLeftTextColor(0xFF000000);
        npStateBean.setRightText("23:59");
        npStateBean.setRightTextColor(0xFF000000);
        npStateBean.setLineWidth(8);
        npStateBean.setEnableClickPart(true);

        npStateLineView.setPartSelectCallback(new NpSleepStateLineView.PartSelectCallback() {
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

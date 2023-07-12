package demo.npwidget.demos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import demo.npwidget.R;
import npwidget.nopointer.progress.battery.NpBatteryView;

public class NpBatteryViewActivity extends AppCompatActivity {
    private NpBatteryView npBatteryView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_battery);
        npBatteryView = findViewById(R.id.npBatteryView);

        loadBattery();
    }


    private void loadBattery() {
        npBatteryView.setBatteryColor(0xFF009900);
        npBatteryView.setBorderColor(0xFF000000);
        npBatteryView.setBatteryBorderRadius(5);
        npBatteryView.setBorderWidth(4);
//        npBatteryView.setInnerPadding(10);
        npBatteryView.setTopRectHeight(8);
        npBatteryView.setTopRectWidth(16);
        npBatteryView.setBatteryValue(100);
        npBatteryView.setLowBatteryColor(0xFFFF0000);
        npBatteryView.setShowAtLastOne(true);
        npBatteryView.setShowType(NpBatteryView.TYPE_CONTINUOUS);
//        float[] customBatteryArray = new float[]{0, 5, 20, 40, 60, 80, 100};
//        npBatteryView.setCustomBatteryArray(customBatteryArray);
        npBatteryView.invalidate();
    }

}

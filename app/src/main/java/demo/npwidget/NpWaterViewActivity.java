package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;

import npwidget.nopointer.water.NpWaterView.NpWaterBean;
import npwidget.nopointer.water.NpWaterView.NpWaterView;

public class NpWaterViewActivity extends Activity {

    NpWaterView waterView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        waterView = findViewById(R.id.waterView);

        loadDebug();
    }

    private void loadDebug() {
        NpWaterBean npWaterBean = new NpWaterBean();
        waterView.setNpWaterBean(npWaterBean);
        waterView.invalidate();

    }
}

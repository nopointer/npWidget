package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;

import npwidget.nopointer.progress.NpRectProgressView;
import npwidget.nopointer.progress.NpRectWithValueProgressView;

public class NpRectViewActivity extends Activity {

    NpRectProgressView npRectProgressView;

    NpRectWithValueProgressView npRectWithValueProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect_view);
        npRectProgressView = findViewById(R.id.npRectProgressView);
        npRectWithValueProgressView = findViewById(R.id.npRectWithValueProgressView);
        debug();
    }

    private void debug() {
        npRectProgressView.setUseRoundMode(true);
        npRectProgressView.setProgressColor(0xFFFF0000);
        npRectProgressView.setBgColor(0xFFFFFFFF);
        npRectProgressView.setProgress(0.001f);
        npRectProgressView.invalidate();

//        npRectWithValueProgressView.setUseRoundMode(false);
//        npRectWithValueProgressView.setSegmentCount(4);
//        npRectWithValueProgressView.setBarHeight(SizeUtils.dp2px(this,20));
//        npRectWithValueProgressView.setProgressColor(0xFF0000FF);
//        npRectWithValueProgressView.setBorderColor(0xFF00FFFF);
//        npRectWithValueProgressView.setBgColor(0xFFFFFFFF);
//        npRectWithValueProgressView.setMinValue(0);
//        npRectWithValueProgressView.setMaxValue(200);
//        npRectWithValueProgressView.setStartValue(50);
//        npRectWithValueProgressView.setEndValue(100);
//        npRectWithValueProgressView.invalidate();
    }
}

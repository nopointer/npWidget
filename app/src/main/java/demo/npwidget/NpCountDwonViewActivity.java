package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import npwidget.nopointer.countDown.NpCountDownView;
import npwidget.nopointer.countDown.extra.VBWomanCountDownView;

public class NpCountDwonViewActivity extends Activity {

    NpCountDownView npCountDownView;

    VBWomanCountDownView vBWomanCountDownView;

    private Handler handler =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_view);
        npCountDownView = findViewById(R.id.npCountDownView);

        vBWomanCountDownView = findViewById(R.id.vBWomanCountDownView);

        npCountDownView.startCountDown(10);
        npCountDownView.finish();


        vBWomanCountDownView.setCircleProgressBgColor(0xFFE7E7E7);
        vBWomanCountDownView.setCircleProgressColor(0xFFF86575);
        vBWomanCountDownView.setOutSideColor(0xFFE7E7E7);
        vBWomanCountDownView.setProgressBarOutSideColor(0xFFF86575);
        vBWomanCountDownView.setProgressBarColor(0xFFFFFFFF);
        vBWomanCountDownView.startCountDown(3,0.5f);

    }


}

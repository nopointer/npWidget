package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import npwidget.nopointer.countDown.NpCountDownView;

public class NpCountDwonViewActivity extends Activity {

    NpCountDownView npCountDownView;

    private Handler handler =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_view);
        npCountDownView = findViewById(R.id.npCountDownView);

        npCountDownView.startCountDown(5);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                npCountDownView.stopCountDown();
            }
        },3000);
    }


}

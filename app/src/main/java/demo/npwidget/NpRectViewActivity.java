package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import npwidget.nopointer.progress.NpRectProgressView;

public class NpRectViewActivity extends Activity {

    NpRectProgressView npRectProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect_view);
        npRectProgressView = findViewById(R.id.npRectProgressView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                debug();
            }
        }, 0);
    }

    private void debug() {
        npRectProgressView.setUseRoundMode(true);
        npRectProgressView.setProgressColor(0xFFFF0000);
        npRectProgressView.setBgColor(0xFFFFFFFF);
        npRectProgressView.setProgress(0.9f);
    }
}

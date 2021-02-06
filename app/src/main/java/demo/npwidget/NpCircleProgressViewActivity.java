package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;

import npwidget.nopointer.progress.NpCircleProgressView;

public class NpCircleProgressViewActivity extends Activity {

    private NpCircleProgressView npCircleProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);
        npCircleProgressView = findViewById(R.id.npCircleProgressView);

        npCircleProgressView.setCircleWidth(50);
        npCircleProgressView.setCircleProgressBgColor(0xFF000000);
        npCircleProgressView.updateProgress(0.2f);
    }
}

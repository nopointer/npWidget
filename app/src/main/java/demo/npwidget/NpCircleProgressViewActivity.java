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

        npCircleProgressView.setCircleWidth(40);
        npCircleProgressView.setCircleProgressBgColor(0xFF000000);
        npCircleProgressView.setDotColor(0xFF0000FF);
        npCircleProgressView.setDotR(8);
        npCircleProgressView.updateProgress(0.1f);
    }
}

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
        npCircleProgressView.setCircleProgressBgColor(0xFF230000);
        npCircleProgressView.setDotColor(0xFF0000FF);
        npCircleProgressView.setDotR(0);

        npCircleProgressView.setShowCursor(true);
        npCircleProgressView.setCursorColor(0xFFFF0000);
        npCircleProgressView.setCursorR(30);

        npCircleProgressView.setShowCursorShadow(true);
        npCircleProgressView.setCursorShadowR(3);
        npCircleProgressView.setCursorShadowColor(0x90000000);

        npCircleProgressView.updateProgress(0.1f);


    }
}

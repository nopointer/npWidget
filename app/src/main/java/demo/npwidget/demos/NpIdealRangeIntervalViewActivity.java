package demo.npwidget.demos;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import demo.npwidget.R;
import npwidget.nopointer.section.range.NpIdealRangeIntervalView;

public class NpIdealRangeIntervalViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_range);

        NpIdealRangeIntervalView npIdealRangeIntervalView = findViewById(R.id.npIdealRangeIntervalView);
        npIdealRangeIntervalView.setProgress(0.8f);
        npIdealRangeIntervalView.setProgressRadius(0);
        npIdealRangeIntervalView.setRangeProgressMin(0.65f);
        npIdealRangeIntervalView.setRangeProgressMax(0.9f);
    }

}

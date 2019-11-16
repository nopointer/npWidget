package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import npwidget.nopointer.wave.bloodOxygen.NpBloodOxygenWaveView;

public class NpOxWaveViewActivity extends Activity {


    NpBloodOxygenWaveView bloodOxygenWaveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ox_waveview);

        bloodOxygenWaveView =findViewById(R.id.bloodOxygenWaveView);
    }
}

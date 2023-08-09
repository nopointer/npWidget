package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import npwidget.nopointer.combinationControl.date.NpDateType;
import npwidget.nopointer.combinationControl.date.dateChoose.NpDateBean;
import npwidget.nopointer.combinationControl.date.dateChoose.NpDateChooseView;
import npwidget.nopointer.combinationControl.date.dateType.NpDateTypeSelectCallback;
import npwidget.nopointer.combinationControl.date.dateType.NpDateTypeSelectView;

public class NpDateTypeActivity extends Activity {


    private NpDateTypeSelectView dateTypeSelectView;
    private NpDateChooseView npDateChooseView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_type);
        dateTypeSelectView = findViewById(R.id.dateTypeSelectView);
        npDateChooseView = findViewById(R.id.npDateChooseView);

        dateTypeSelectView.setDateTypeSelectCallback(new NpDateTypeSelectCallback() {
            @Override
            public void onSelect(final NpDateType dateType) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), dateType.toString(), 0).show();
                        npDateChooseView.setDateType(dateType);
                    }
                });
            }
        });
        npDateChooseView.setDateType(NpDateType.DAY);
        dateTypeSelectView.setTextSize(14);
        npDateChooseView.setTextSize(10);

        npDateChooseView.setDayIndex(0);
    }


}

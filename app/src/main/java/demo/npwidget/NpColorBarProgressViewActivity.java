package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import npwidget.nopointer.base.NpPosition;
import npwidget.nopointer.progress.npColorBars.NpColorBarBean;
import npwidget.nopointer.progress.npColorBars.NpColorBarEntity;
import npwidget.nopointer.progress.npColorBars.cursorTop.NpColorBarProgressView;
import npwidget.nopointer.progress.npColorBars.cursorTop.NpColorType;

public class NpColorBarProgressViewActivity extends Activity {


    TextView women_date_1_tv;
    TextView women_date_2_tv;
    TextView women_date_3_tv;
    TextView women_date_4_tv;


    private NpColorBarProgressView npColorBarProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_color_progress_view);
        npColorBarProgressView = findViewById(R.id.npColorBarProgressView);

        women_date_1_tv = findViewById(R.id.women_date_1_tv);
        women_date_2_tv = findViewById(R.id.women_date_2_tv);
        women_date_3_tv = findViewById(R.id.women_date_3_tv);
        women_date_4_tv = findViewById(R.id.women_date_4_tv);


        NpColorBarBean localNpColorBarBean = new NpColorBarBean();
        localNpColorBarBean.setCursorColor(0xFFF86575);
        localNpColorBarBean.setUseRoundMode(true);
        localNpColorBarBean.setShowCursor(true);
        localNpColorBarBean.setCurrentValue(7);
        localNpColorBarBean.setShowFloatProgress(false);
        localNpColorBarBean.setFloatProgressColor(0xFFF86575);
        localNpColorBarBean.setNpColorType(NpColorType.TYPE_DATA);
        localNpColorBarBean.setValuePosition(NpPosition.HIDE);
        ArrayList localArrayList = new ArrayList();
//        localArrayList.add(new NpColorBarEntity(0xFFE7E7E7, 280f));
        localArrayList.add(new NpColorBarEntity(0xFFF86575, 7f));
        localArrayList.add(new NpColorBarEntity(0xFF65F87A, 5f));
        localArrayList.add(new NpColorBarEntity(0xFFFBDFA0, 8f));
        localArrayList.add(new NpColorBarEntity(0xFF65F87A, 8f));
        localNpColorBarBean.setNpColorBarEntityList(localArrayList);
        npColorBarProgressView.setNpColorBarBean(localNpColorBarBean);
        npColorBarProgressView.invalidate();

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams1.weight = 7;
        women_date_1_tv.setLayoutParams(layoutParams1);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams2.weight = 5;
        women_date_2_tv.setLayoutParams(layoutParams2);

        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams3.weight = 8;
        women_date_3_tv.setLayoutParams(layoutParams3);

        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams4.weight = 8;
        women_date_4_tv.setLayoutParams(layoutParams4);

    }
}

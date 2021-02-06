package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import npwidget.nopointer.base.NpPosition;
import npwidget.nopointer.progress.npColorBars.NpColorBarBean;
import npwidget.nopointer.progress.npColorBars.NpColorBarEntity;
import npwidget.nopointer.progress.npColorBars.cursorTop.NpColorBarProgressView;
import npwidget.nopointer.progress.npColorBars.cursorTop.NpColorType;

public class NpColorBarProgressViewActivity extends Activity {


    private NpColorBarProgressView npColorBarProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_color_progress_view);
        npColorBarProgressView = findViewById(R.id.npColorBarProgressView);


        NpColorBarBean localNpColorBarBean = new NpColorBarBean();
        localNpColorBarBean.setCursorColor(0xFFF86575);
        localNpColorBarBean.setUseRoundMode(true);
        localNpColorBarBean.setShowCursor(true);
        localNpColorBarBean.setCurrentValue(5);
        localNpColorBarBean.setShowFloatProgress(true);
        localNpColorBarBean.setFloatProgressColor(0xFFF86575);
        localNpColorBarBean.setNpColorType(NpColorType.TYPE_DATA);
        localNpColorBarBean.setValuePosition(NpPosition.HIDE);
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new NpColorBarEntity(0xFFE7E7E7, 280f));
//        localArrayList.add(new NpColorBarEntity(0xFF65F87A, 4f));
//        localArrayList.add(new NpColorBarEntity(0xFFFBDFA0, 4f));
//        localArrayList.add(new NpColorBarEntity(0xFF65F87A, 4f));
        localNpColorBarBean.setNpColorBarEntityList(localArrayList);
        npColorBarProgressView.setNpColorBarBean(localNpColorBarBean);
        npColorBarProgressView.invalidate();

    }
}

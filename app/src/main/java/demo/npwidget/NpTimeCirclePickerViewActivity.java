package demo.npwidget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import npwidget.nopointer.datetime.circlePicker.NpTimeBean;
import npwidget.nopointer.datetime.circlePicker.NpTimeCirclePicker;
import npwidget.nopointer.datetime.circlePicker.NpTimeChangeListener;

public class NpTimeCirclePickerViewActivity extends Activity {

    NpTimeCirclePicker mTimer;

    private TextView mTvEndTime;
    private TextView mTvStartTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        mTimer = (NpTimeCirclePicker) findViewById(R.id.timer);
        mTimer.setInitialTime(0, 270);
        mTvStartTime = (TextView) findViewById(R.id.start_time);
        mTvEndTime = (TextView) findViewById(R.id.end_time);
        mTimer.setOnTimerChangeListener(new NpTimeChangeListener() {
            @Override
            public void startTimeChanged(float startDegree, float endDegree) {
                float startCount = (startDegree * 2.0f);
                Log.e("startCount", startCount + "");
                int startHour = (int) (startCount / 60);
                int startMinute = (int) (startCount % 60);
                mTvStartTime.setText(((startHour < 10) ? ("0" + startHour) : (startHour + "")) + ":" + ((startMinute < 10) ? ("0" + startMinute) : (startMinute + "")));
            }

            @Override
            public void endTimeChanged(float startDegree, float endDegree) {
                float endCount = (endDegree * 2.0f);
                int endHour = Float.valueOf(endCount / 60).intValue();
                int endMinute = Float.valueOf(endCount % 60).intValue();
                mTvEndTime.setText(((endHour < 10) ? ("0" + endHour) : (endHour + "")) + ":" + ((endMinute < 10) ? ("0" + endMinute) : (endMinute + "")));
            }

            @Override
            public void onTimeInitail(float startDegree, float endDegree) {
                float startCount = (startDegree * 2.0f);
                int startHour = Float.valueOf(startCount / 60).intValue();
                int startMinute = Float.valueOf(startCount % 60).intValue();
                mTvStartTime.setText(((startHour < 10) ? ("0" + startHour) : (startHour + "")) + ":" + ((startMinute < 10) ? ("0" + startMinute) : (startMinute + "")));


                float endCount = (endDegree * 2.0f);
                int endHour = Float.valueOf(endCount / 60).intValue();
                int endMinute = Float.valueOf(endCount % 60).intValue();
                mTvEndTime.setText(((endHour < 10) ? ("0" + endHour) : (endHour + "")) + ":" + ((endMinute < 10) ? ("0" + endMinute) : (endMinute + "")));
            }

            @Override
            public void onAllTimeChanaged(float startDegree, float endDegree) {
                float startCount = (startDegree * 2.0f);
                int startHour = Float.valueOf(startCount / 60).intValue();
                int startMinute = Float.valueOf(startCount % 60).intValue();
                mTvStartTime.setText(((startHour < 10) ? ("0" + startHour) : (startHour + "")) + ":" + ((startMinute < 10) ? ("0" + startMinute) : (startMinute + "")));


                float endCount = (endDegree * 2.0f);
                int endHour = Float.valueOf(endCount / 60).intValue();
                int endMinute = Float.valueOf(endCount % 60).intValue();
                mTvEndTime.setText(((endHour < 10) ? ("0" + endHour) : (endHour + "")) + ":" + ((endMinute < 10) ? ("0" + endMinute) : (endMinute + "")));
            }

            @Override
            public void onStopTouch() {

            }
        });

        loadDebug();
    }

    private void loadDebug() {
        NpTimeBean npTimeBean = new NpTimeBean();
        npTimeBean.setDialCircleBgColor(0xFFFFFFFF);
        npTimeBean.setMinMinuteUnit(5);
        npTimeBean.setBgNumberColor(npTimeBean.getDialCircleBgColor());
        npTimeBean.setStartAndEndBtnSize(90);//设置开始和按钮的大小（等宽高）
        npTimeBean.setStartAndEndBtnMargin(2);

        mTimer.setNpTimeBean(npTimeBean);
        mTimer.invalidate();

    }
}

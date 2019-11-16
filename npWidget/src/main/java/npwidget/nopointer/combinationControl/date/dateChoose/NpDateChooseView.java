package npwidget.nopointer.combinationControl.date.dateChoose;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import npwidget.nopointer.R;
import npwidget.nopointer.combinationControl.date.NpDateType;

/**
 * 日期选择范围的控件
 */
public class NpDateChooseView extends RelativeLayout {

    //天 索引
    private int dayIndex = 0;
    //周 索引
    private int weekIndex = 0;
    //月 索引
    private int monthIndex = 0;
    //年 索引
    private int yearIndex = 0;


    //选择当前的日期类型
    private NpDateType dateType = NpDateType.MONTH;


    private TextView date_choose_content_tv;

    private NpDateBean npDateBean;

    private NpDataChooseCallback npDataChooseCallback;

    public void setNpDataChooseCallback(NpDataChooseCallback npDataChooseCallback) {
        this.npDataChooseCallback = npDataChooseCallback;
    }

    public void setDateType(NpDateType dateType) {
        this.dateType = dateType;
        loadDateToView();
    }

    public NpDateChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public NpDateChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NpDateChooseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.date_choose_layout, this, true);

        findViewById(R.id.date_choose_left_iv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dateType) {
                    case DAY:
                        dayIndex--;
                        break;
                    case WEEK:
                        weekIndex--;
                        break;
                    case MONTH:
                        monthIndex--;
                        break;
                    case YEAR:
                        yearIndex--;
                        break;
                }
                loadDateToView();
            }
        });

        findViewById(R.id.date_choose_right_iv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dateType) {
                    case DAY:
                        if (dayIndex < 0) {
                            dayIndex++;
                            loadDateToView();
                        }
                        break;
                    case WEEK:
                        if (weekIndex < 0) {
                            weekIndex++;
                            loadDateToView();
                        }
                        break;
                    case MONTH:
                        if (monthIndex < 0) {
                            monthIndex++;
                            loadDateToView();
                        }
                        break;
                    case YEAR:
                        if (yearIndex < 0) {
                            yearIndex++;
                            loadDateToView();
                        }
                        break;
                }
            }
        });


        init();
    }

    private void init() {
        date_choose_content_tv = findViewById(R.id.date_choose_content_tv);
        loadDateToView();
    }


    /**
     * 加载数据
     */
    private void loadDateToView() {
        switch (dateType) {
            case DAY:
                npDateBean = NpDateBean.getDayDateBean(new Date(System.currentTimeMillis()), dayIndex);
                break;
            case WEEK:
                npDateBean = NpDateBean.getWeekDateBean(new Date(System.currentTimeMillis()), weekIndex);
                break;
            case MONTH:
                npDateBean = NpDateBean.getMonthDateBean(new Date(System.currentTimeMillis()), monthIndex);
                break;
            case YEAR:
                npDateBean = NpDateBean.getYearDateBean(new Date(System.currentTimeMillis()), yearIndex);
                break;
        }
        updateDateShow();
        if (npDataChooseCallback != null) {
            npDataChooseCallback.onSelectDate(npDateBean);
        }
    }

    private void updateDateShow() {
        if (npDateBean != null) {
            if (npDataChooseCallback != null) {
                date_choose_content_tv.setText(npDataChooseCallback.formatDateShow(npDateBean));
            } else {
                date_choose_content_tv.setText(npDateBean.getSimpleTitle());
            }
        }
    }

}

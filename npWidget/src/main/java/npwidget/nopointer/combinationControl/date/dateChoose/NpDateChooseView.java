package npwidget.nopointer.combinationControl.date.dateChoose;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import npwidget.nopointer.R;
import npwidget.nopointer.combinationControl.date.NpDateType;
import npwidget.nopointer.log.NpViewLog;
import npwidget.nopointer.utils.SizeUtils;

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


    //日周月年的偏移量（防止 跨天的时候数据不显示）
    private int dayAlignOffset = 0, weekAlignOffset = 0, monthAlignOffset = 0, yearAlignOffset = 0;

    private String enterInitDate = "";//进入的初始日期

    private ImageView leftIconIv;
    private ImageView rightIconIv;
    private TextView titleTv;

    //选择当前的日期类型
    private NpDateType dateType = NpDateType.MONTH;

    //文字大小
    private float textSize = 14;

    private NpDateBean npDateBean;

    public NpDateBean getNpDateBean() {
        return npDateBean;
    }

    public void setDayIndex(int dayIndex) {
        if (dayIndex > dayAlignOffset) {
            dayIndex = dayAlignOffset;
        }
        this.dayIndex = dayIndex;
        loadDateToView();
    }

    private NpDataChooseCallback npDataChooseCallback;


    public void setLeftAndRightIcon(int leftIcon, int rightIcon) {
        setLeftIcon(leftIcon);
        setRightIcon(rightIcon);
    }

    public void setLeftIcon(int leftIcon) {
        rightIconIv.setImageResource(leftIcon);
    }

    public void setRightIcon(int rightIcon) {
        rightIconIv.setImageResource(rightIcon);
    }

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

        leftIconIv = findViewById(R.id.date_choose_left_iv);
        rightIconIv = findViewById(R.id.date_choose_right_iv);
        titleTv = findViewById(R.id.date_choose_content_tv);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NpDateChooseView);
            int leftIcon = typedArray.getResourceId(R.styleable.NpDateChooseView_leftIcon, R.drawable.icon_date_left);
            leftIconIv.setImageResource(leftIcon);

            int rightIcon = typedArray.getResourceId(R.styleable.NpDateChooseView_rightIcon, R.drawable.icon_date_right);
            rightIconIv.setImageResource(rightIcon);

            int textColor = typedArray.getColor(R.styleable.NpDateChooseView_textColor, context.getResources().getColor(R.color.gray));
            titleTv.setTextColor(textColor);

            textSize = typedArray.getDimension(R.styleable.NpDateChooseView_textSize, SizeUtils.sp2px(context, 14));

            textSize = SizeUtils.px2sp(context, (int) textSize);

            typedArray.recycle();
        }


        leftIconIv.setOnClickListener(new OnClickListener() {
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

        rightIconIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshAlignOffset();
                switch (dateType) {
                    case DAY:
                        if (dayIndex < dayAlignOffset) {
                            dayIndex++;
                            loadDateToView();
                        }
                        break;
                    case WEEK:
                        if (weekIndex < weekAlignOffset) {
                            weekIndex++;
                            loadDateToView();
                        }
                        break;
                    case MONTH:
                        if (monthIndex < monthAlignOffset) {
                            monthIndex++;
                            loadDateToView();
                        }
                        break;
                    case YEAR:
                        if (yearIndex < yearAlignOffset) {
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
        enterInitDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(System.currentTimeMillis());
        loadDateToView();
        refreshTextSize();
        refreshAlignOffset();
    }


    /**
     * 加载数据
     */
    private void loadDateToView() {
        switch (dateType) {
            case DAY:
                npDateBean = NpDateBean.getDayDateBean(new Date(System.currentTimeMillis()), dayIndex - dayAlignOffset);
                break;
            case WEEK:
                npDateBean = NpDateBean.getWeekDateBean(new Date(System.currentTimeMillis()), weekIndex - weekAlignOffset);
                break;
            case MONTH:
                npDateBean = NpDateBean.getMonthDateBean(new Date(System.currentTimeMillis()), monthIndex - monthAlignOffset);
                break;
            case YEAR:
                npDateBean = NpDateBean.getYearDateBean(new Date(System.currentTimeMillis()), yearIndex - yearAlignOffset);
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
                titleTv.setText(npDataChooseCallback.formatDateShow(npDateBean));
            } else {
                titleTv.setText(npDateBean.getSimpleTitle());
            }
        }
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        refreshTextSize();
    }


    //刷新view的大小
    void refreshTextSize() {
        if (textSize <= 0) {
            textSize = 14;
        }
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }


    /**
     * 刷新偏移量
     */
    void refreshAlignOffset() {
        NpViewLog.log("刷新对齐的 日周月年 偏移量");
        long time = System.currentTimeMillis();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(time);
        NpViewLog.log("今天时间 = " + enterInitDate + " , 当前时间 = " + currentDate);

        if (enterInitDate.equals(currentDate)) {
            dayAlignOffset = 0;
            weekAlignOffset = 0;
            monthAlignOffset = 0;
            yearAlignOffset = 0;
        } else {
            SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {

                long referInitTime = smp.parse(enterInitDate).getTime();//参考日期（在时间没发生改变之前的日期，一般就是刚进入界面的日期）
                long currentTime = smp.parse(currentDate).getTime();//实时日期（如果是跨天的话 还是当前的最新日期）

                NpViewLog.log("计算日对齐偏移量: referInitTime = " + referInitTime + " , currentTime = " + currentTime);
                long unitDay = 3600 * 24 * 1000L;
                dayAlignOffset = (int) ((currentTime - referInitTime) / unitDay);
                if ((currentTime - referInitTime) % unitDay != 0) {
                    dayAlignOffset += 1;
                }
                NpViewLog.log("dayAlignOffset = " + dayAlignOffset);

                //开始计算周的对齐偏移量
                NpDateBean weekRange = NpDateBean.getWeekDateBean(new Date(referInitTime), 0);
                //周结束的天 用来判断改变的日期是否是在结束的天后面，如果在前面就不处理
                referInitTime = smp.parse(smp.format(weekRange.getEndDate())).getTime();
                NpViewLog.log("计算周对齐偏移量:  referInitTime = " + referInitTime + " , currentTime = " + currentTime);

                //超过周的最后一天了，进入下n周
                if (currentTime > referInitTime) {
                    weekAlignOffset = (int) ((currentTime - referInitTime) / (unitDay * 7));
                    if ((currentTime - referInitTime) % (unitDay * 7) != 0) {
                        weekAlignOffset += 1;
                    }
                }

                //开始计算周的对齐偏移量
                NpDateBean monthRange = NpDateBean.getMonthDateBean(new Date(referInitTime), 0);
                //周结束的天 用来判断改变的日期是否是在结束的天后面，如果在前面就不处理
                referInitTime = smp.parse(smp.format(monthRange.getEndDate())).getTime();
                NpViewLog.log("计算月对齐偏移量:  referInitTime = " + referInitTime + " , currentTime = " + currentTime);

                //超过月的最后一天了，进入下n月
                if (currentTime > referInitTime) {
                    int curYear = Integer.valueOf(currentDate.substring(0, 4));
                    int curMonth = Integer.valueOf(currentDate.substring(5, 7));

                    int refYear = Integer.valueOf(enterInitDate.substring(0, 4));
                    int refMonth = Integer.valueOf(enterInitDate.substring(5, 7));

                    NpViewLog.log("curYear = " + curYear + ",curMonth = " + curMonth);
                    NpViewLog.log("refYear = " + refYear + ",refMonth = " + refMonth);

                    int yearOffset = curYear - refYear;
                    int monthOffset = curMonth - refMonth;

                    if (monthOffset < 0) {
                        monthOffset += 12;
                        yearOffset -= 1;
                    }
                    monthAlignOffset = monthOffset + yearOffset * 12;
                }


                //开始计算年的对齐偏移量
                NpDateBean yearRange = NpDateBean.getYearDateBean(new Date(referInitTime), 0);
                referInitTime = smp.parse(smp.format(yearRange.getEndDate())).getTime();
                NpViewLog.log("计算年对齐偏移量:  referInitTime = " + referInitTime + " , currentTime = " + currentTime);

                //超过年的最后一天了，进入下n年
                if (currentTime > referInitTime) {
                    int curYear = Integer.valueOf(currentDate.substring(0, 4));
                    int refYear = Integer.valueOf(enterInitDate.substring(0, 4));

                    NpViewLog.log("curYear = " + curYear);
                    NpViewLog.log("refYear = " + refYear);
                    yearAlignOffset = curYear - refYear;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

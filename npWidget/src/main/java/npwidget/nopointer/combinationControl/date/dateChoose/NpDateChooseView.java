package npwidget.nopointer.combinationControl.date.dateChoose;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import npwidget.nopointer.R;
import npwidget.nopointer.combinationControl.date.NpDateType;
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


    private ImageView leftIconIv;
    private ImageView rightIconIv;
    private TextView titleTv;

    //选择当前的日期类型
    private NpDateType dateType = NpDateType.MONTH;

    //文字大小
    private float textSize = 14;


    private NpDateBean npDateBean;

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

        loadDateToView();
        refreshTextSize();
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
}

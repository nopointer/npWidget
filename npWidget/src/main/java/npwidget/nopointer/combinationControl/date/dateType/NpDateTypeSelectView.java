package npwidget.nopointer.combinationControl.date.dateType;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import npwidget.nopointer.R;
import npwidget.nopointer.combinationControl.date.NpDateType;

/**
 * 日周月年的选择样式组合控件
 * 提供两种样式 分离式 或者连续式
 */
public class NpDateTypeSelectView extends RelativeLayout {


    private RadioGroup date_type_group;

    //切换类型时候的回调接口
    private NpDateTypeSelectCallback dateTypeSelectCallback = null;

    //显示的item
    private int showItem;

    //文字颜色选择样式
    private int textSelector = 0;


    private int daySelector = 0;
    private int weekSelector = 0;
    private int monthSelector = 0;
    private int yearSelector = 0;


    //日周月年的item
    private RadioButton[] dateTypeViews = new RadioButton[4];
    //中间的分割线
    private View[] dateTypeViewLines = new View[3];


    public NpDateTypeSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public NpDateTypeSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NpDateTypeSelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {


        //选择样式
        int layoutResId = R.layout.date_type_select_by_continuous_layout;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NpDateTypeSelectView);
            if (typedArray != null) {
                int showStyle = typedArray.getInt(R.styleable.NpDateTypeSelectView_show_type, 1);
                if (showStyle == 1) {
                    layoutResId = R.layout.date_type_select_by_continuous_layout;

                    daySelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_bg_selector_by_day, R.drawable.date_type_default_selector_left);
                    weekSelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_bg_selector_by_day, R.drawable.date_type_default_selector_center);
                    monthSelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_bg_selector_by_day, R.drawable.date_type_default_selector_center);
                    yearSelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_bg_selector_by_day, R.drawable.date_type_default_selector_right);
                } else {
                    layoutResId = R.layout.date_type_select_by_depart_layout;

                    daySelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_bg_selector_by_day, R.drawable.date_type_default_selector_all);
                    weekSelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_bg_selector_by_day, R.drawable.date_type_default_selector_all);
                    monthSelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_bg_selector_by_day, R.drawable.date_type_default_selector_all);
                    yearSelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_bg_selector_by_day, R.drawable.date_type_default_selector_all);
                }
                showItem = typedArray.getInt(R.styleable.NpDateTypeSelectView_show_item, 0);

                textSelector = typedArray.getResourceId(R.styleable.NpDateTypeSelectView_text_selector, R.drawable.date_type_text_selector);

                typedArray.recycle();
            }
        }
        LayoutInflater.from(context).inflate(layoutResId, this, true);
        init();
    }

    private void init() {
        date_type_group = findViewById(R.id.date_type_group);

        dateTypeViews[0] = findViewById(R.id.date_type_day);
        dateTypeViews[0].setBackgroundResource(daySelector);
        dateTypeViews[0].setTextColor(ContextCompat.getColorStateList(getContext(), textSelector));


        dateTypeViews[1] = findViewById(R.id.date_type_week);
        dateTypeViews[1].setBackgroundResource(weekSelector);
        dateTypeViews[1].setTextColor(ContextCompat.getColorStateList(getContext(), textSelector));

        dateTypeViews[2] = findViewById(R.id.date_type_month);
        dateTypeViews[2].setBackgroundResource(monthSelector);
        dateTypeViews[2].setTextColor(ContextCompat.getColorStateList(getContext(), textSelector));

        dateTypeViews[3] = findViewById(R.id.date_type_year);
        dateTypeViews[3].setBackgroundResource(yearSelector);
        dateTypeViews[3].setTextColor(ContextCompat.getColorStateList(getContext(), textSelector));


        dateTypeViewLines[0] = findViewById(R.id.date_type_day_line);
        dateTypeViewLines[1] = findViewById(R.id.date_type_week_line);
        dateTypeViewLines[2] = findViewById(R.id.date_type_month_line);


        date_type_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.date_type_day) {
                    //日
                    if (dateTypeSelectCallback != null) {
                        dateTypeSelectCallback.onSelect(NpDateType.DAY);
                    }
                } else if (checkedId == R.id.date_type_week) {
                    //周
                    if (dateTypeSelectCallback != null) {
                        dateTypeSelectCallback.onSelect(NpDateType.WEEK);
                    }
                } else if (checkedId == R.id.date_type_month) {
                    //月
                    if (dateTypeSelectCallback != null) {
                        dateTypeSelectCallback.onSelect(NpDateType.MONTH);
                    }
                } else if (checkedId == R.id.date_type_year) {
                    //年
                    if (dateTypeSelectCallback != null) {
                        dateTypeSelectCallback.onSelect(NpDateType.YEAR);
                    }
                }
            }
        });

        if (showItem == 0) {
            //全显示
            for (View view : dateTypeViews) {
                view.setVisibility(VISIBLE);
            }
            for (View view : dateTypeViewLines) {
                view.setVisibility(VISIBLE);
            }

        } else if (showItem == 1) {
            //日周月
            dateTypeViews[3].setVisibility(GONE);
            dateTypeViewLines[2].setVisibility(GONE);
        } else if (showItem == 2) {
            //周月年
            dateTypeViews[0].setVisibility(GONE);
            dateTypeViewLines[0].setVisibility(GONE);
        }
    }


    public void setDateTypeSelectCallback(NpDateTypeSelectCallback dateTypeSelectCallback) {
        this.dateTypeSelectCallback = dateTypeSelectCallback;
    }
}

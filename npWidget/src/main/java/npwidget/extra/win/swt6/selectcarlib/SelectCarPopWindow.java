package npwidget.extra.win.swt6.selectcarlib;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import npwidget.nopointer.R;

/**
 * Title: SelectCarPopWindow <br>
 * Description: 显示牌照选择的单例类<br>
 * Copyright (c) Wentao.Shi 2017 <br>
 * Created DateTime: 2017/12/28 15:44
 * Created by Wentao.Shi.
 */
public class SelectCarPopWindow {
    private PopupWindow mPopWindow;
    private SelectCarPopWindow() {

    }

    private static class SelectCarPopWindowHolder {
        private static final SelectCarPopWindow INSTANCE = new SelectCarPopWindow();
    }

    public static SelectCarPopWindow getInstance() {
        return SelectCarPopWindowHolder.INSTANCE;
    }

    /**
     * Description: 显示省份选择popwindow
     * Copyright (c) WenTao.Shi
     * Created DateTime: 2017/12/28 15:55
     * Created by WenTao.Shi .
     * @param context   上下文
     * @param clickListener 省份选择接口
     * @param shrinkButtonClickListener     收缩按钮接口
     * @param parent        父视图view，建议使用activity
     */
    public void showSelectCarPopWindow(Context context, OnItemClickListener clickListener, OnShrinkButtonClickListener shrinkButtonClickListener, View parent){
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_select_car, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setWidth(ScreenUtil.getScreenWidth(context));
        mPopWindow.setContentView(contentView);
        mPopWindow.setBackgroundDrawable(
                new ColorDrawable(context.getResources().getColor(R.color.selectcarlib_transparent)));
        // 使其聚焦
        mPopWindow.setFocusable(true);
        // 设置允许在外点击消失
        mPopWindow.setOutsideTouchable(true);

        //设置各个控件的点击响应
        SelectCharHeaderView selectCharHeaderView = contentView.findViewById(R.id.schv);
        selectCharHeaderView.setItemClickListener(clickListener);
        selectCharHeaderView.setShrinkButtonClickListener(shrinkButtonClickListener);
        //显示PopupWindow
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 关闭popwindow
     */
    public void  dismissSelectCarPopWindow(){
        if (mPopWindow!=null){
            mPopWindow.dismiss();
        }
    }
}

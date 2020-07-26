package npwidget.extra.win.swt6.selectcarlib;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Title: ScreenUtil <br>
 * Description: <br>
 * Copyright (c) Wentao.Shi 2017 <br>
 * Created DateTime: 2017/9/12 14:05
 * Created by Wentao.Shi.
 */
public class ScreenUtil {

    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int height = dm.heightPixels;
        return height;
    }
}

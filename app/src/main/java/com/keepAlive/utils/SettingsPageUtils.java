package com.keepAlive.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * 系统设置页面
 */
public class SettingsPageUtils {

    private SettingsPageUtils() {
    }


    /**
     * 跳转设置页面
     *
     * @param context
     */
    public static void toToSettingsPage(Context context) {
        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }


    public static void goToSavePowerPage(Context context) {
        context.startActivity(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
    }


    public static void goToSettingPage(Context context, PageType pageType) {
        PhoneRomSysPage.goToSettingPage(context, pageType);
    }


}

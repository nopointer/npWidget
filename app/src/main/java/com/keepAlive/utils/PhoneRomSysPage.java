package com.keepAlive.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class PhoneRomSysPage {

    private static String TAG = "PhoneRomSysPage";

    private PhoneRomSysPage() {
    }

    public static void goToSettingPage(Context context, PageType pageType) {
        String romName = Build.MANUFACTURER;//系统名称

        if (romName.equalsIgnoreCase("HUAWEI")) {
            goToHuaWeiSettingsPage(context, pageType);
        } else if (romName.equalsIgnoreCase("Xiaomi")) {
            goToXiaoMiSettingsPage(context, pageType);
        } else if (romName.equalsIgnoreCase("OPPO")) {
            goToOppoSettingsPage(context, pageType);
        } else if (romName.equalsIgnoreCase("VIVO")) {
            goToVIVOSettingsPage(context, pageType);
        }

        Logger.d(TAG, "romName==>" + romName);
    }


    /**
     * 跳转小米手机的设置页面
     *
     * @param pageType 页面类型
     */
    public static void goToXiaoMiSettingsPage(Context context, PageType pageType) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = null;
        switch (pageType) {
            case AUTO_START://自启动
                componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                break;
        }
        try {
            if (componentName != null) {
                intent.setComponent(componentName);
                context.startActivity(intent);
            } else {
                Logger.d(TAG, "小米手机：componentName=null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转华为的设置页面
     *
     * @param context
     * @param pageType
     */
    public static void goToHuaWeiSettingsPage(Context context, PageType pageType) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = null;
        switch (pageType) {
            case AUTO_START://自启动
                if (Build.VERSION.SDK_INT >= 28) {
                    componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                } else if (Build.VERSION.SDK_INT >= 26) {
                    componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
                } else if (Build.VERSION.SDK_INT >= 23) {
                    componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                } else {//6.0以下跳转到权限设置页面了
                    componentName = ComponentName.unflattenFromString("com.huawei.systemmanager/com.huawei.permissionmanager.ui.MainActivity");
                }
                break;
        }
        try {
            if (componentName != null) {
                intent.setComponent(componentName);
                context.startActivity(intent);
            } else {
                Logger.d(TAG, "华为手机：componentName=null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转OPPo的设置页面
     *
     * @param context
     * @param pageType
     */
    public static void goToOppoSettingsPage(Context context, PageType pageType) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = null;
        switch (pageType) {
            case AUTO_START://自启动
                if (Build.VERSION.SDK_INT >= 21) {//5.0以上
                    componentName = ComponentName.unflattenFromString("com.coloros.safecenter/.startupapp.StartupAppListActivity");
                } else {//5.0以下跳转到权限设置页面了
                    componentName = ComponentName.unflattenFromString("com.color.safecenter/.permission.startup.StartupAppListActivity");
                }
                break;
        }
        try {
            if (componentName != null) {
                intent.setComponent(componentName);
                context.startActivity(intent);
            } else {
                Logger.d(TAG, "oppo手机：componentName=null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转ViVO的设置页面
     *
     * @param context
     * @param pageType
     */
    public static void goToVIVOSettingsPage(Context context, PageType pageType) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("packagename",context.getPackageName());
        ComponentName componentName = null;
        switch (pageType) {
            case AUTO_START://自启动
                if (Build.VERSION.SDK_INT >= 23) {//6.0以上
                    intent.setClassName("com.vivo.permissionmanager","com.vivo.permissionmanager.activity.BgStartUpManagerActivity");
//                    componentName =ComponentName.unflattenFromString("com.vivo.upslide/.recents.RecentsActivity");
//                    componentName = new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
//                    componentName = new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity");
                } else {//6.0以下跳转到权限设置页面了
                    componentName = new ComponentName("com.iqoo.secure", "com.vivo.permissionmanager/.activity.BgStartUpManagerActivity");
                }
                break;
        }
        try {
            if (componentName != null) {
                intent.setComponent(componentName);
                context.startActivity(intent);
            } else {
                Logger.d(TAG, "vivo手机：componentName=null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

package npwidget.nopointer.log;

import android.util.Log;

public class ViewLog {
    public static final String tag = "npWidget";

    static String logMac = "";

    public static void initLogDirName(String appBleLogDirName) {
    }


 


    private ViewLog() {
    }

    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;


    public static void e(String content) {
        if (allowE) {
            Log.e(tag, content);
        }
    }

    public static void w(String content) {
        if (!allowW) return;
        Log.w(tag, content);
    }

    public static void i(String content) {
        if (!allowW) return;
        Log.i(tag, content);
    }

    public static void d(String content) {
        if (!allowD) return;
        Log.d(tag, content);
    }

}

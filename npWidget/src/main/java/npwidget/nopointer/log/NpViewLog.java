package npwidget.nopointer.log;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class NpViewLog {

    private static final String tag = "_NpWidget_log_";

    private static Context mContext = null;


    //是否显示调用路径和行号
    public static boolean allowShowCallPathAndLineNumber = true;


    //是否允许打印日志，默认允许
    public static boolean allowLog = false;




    private NpViewLog() {
    }

    /**
     * 打印日志
     *
     * @param content
     */
    public static void log(String content) {
        if (allowLog) {
            if (allowShowCallPathAndLineNumber) {
                StackTraceElement caller = getCallerStackTraceElement();
                content = "[" + getCallPathAndLineNumber(caller) + "]：" + content;
            }
            Log.e(tag, content);
        }
    }


    /**
     * 获取调用路径和行号
     *
     * @return
     */
    private static String getCallPathAndLineNumber(StackTraceElement caller) {
        String result = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        result = String.format(result, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return result;
    }


    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static File getLogParentDir() {
        return mContext.getExternalFilesDir(null).getParentFile();
    }

}

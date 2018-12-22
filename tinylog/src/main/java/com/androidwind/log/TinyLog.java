package com.androidwind.log;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyLog {

    private static final int LOG_V = 0;
    private static final int LOG_D = 1;
    private static final int LOG_I = 2;
    private static final int LOG_W = 3;
    private static final int LOG_E = 4;

    private static LogConfig mLogConfig = new LogConfig();

    public static LogConfig config() {
        return mLogConfig;
    }

    public static void v(String content) {
        if (!mLogConfig.isEnable) return;
        if (mLogConfig.isWritable) {
            logFile(LOG_V, null, content, null);
        }
        logConsole(LOG_V, null, content, null);
    }

    public static void v(String tag, String content, Object... objects) {
        if (!mLogConfig.isEnable) return;
        if (mLogConfig.isWritable) {
            logFile(LOG_V, tag, content, null, objects);
        }
        logConsole(LOG_V, tag, content, null, objects);
    }

    private static String generateTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            return tag;
        } else {
            StackTraceElement caller = new Throwable().getStackTrace()[3];
            String callerClazzName = caller.getClassName();
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
            String result = "%s.%s.%d";
            result = String.format(result, callerClazzName, caller.getMethodName(), caller.getLineNumber());
            return result;
        }
    }

    private static String generateContent(String content, Object... objects) {
        StackTraceElement caller = new Throwable().getStackTrace()[3];
        String result = "(%s:%d) %s";
        result = String.format(result, caller.getFileName(), caller.getLineNumber(), content + wrapContent(objects));
        return result;
    }

    private static Object wrapContent(Object... objects) {
        if (objects.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append("arg").append("[").append(i).append("]").append(" = ").append("null").append("\n");
                } else {
                    stringBuilder.append("arg").append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        }
        return "";
    }

    private static void logConsole(int logSupport, String tag, String content, Throwable tr, Object... args) {
        switch (logSupport) {
            case LOG_V:
                Log.v(generateTag(tag), generateContent(content, args), tr);
                break;
            case LOG_D:
                Log.d(generateTag(tag), generateContent(content, args), tr);
                break;
            case LOG_I:
                Log.i(generateTag(tag), generateContent(content, args), tr);
                break;
            case LOG_W:
                Log.w(generateTag(tag), generateContent(content, args), tr);
                break;
            case LOG_E:
                Log.e(generateTag(tag), generateContent(content, args), tr);
                break;
            default:
                Log.wtf(generateTag(tag), generateContent(content, args), tr);
                break;
        }
    }

    private static void logFile(int logSupport, String tag, String content, Throwable tr, Object... args) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = sf.format(new Date());
        String level;
        switch (logSupport) {
            case LOG_V:
                level = "V";
                break;
            case LOG_D:
                level = "D";
                break;
            case LOG_I:
                level = "I";
                break;
            case LOG_W:
                level = "W";
                break;
            case LOG_E:
                level = "E";
                break;
            default:
                level = "WTF";
                break;
        }
        StackTraceElement caller = new Throwable().getStackTrace()[3];
        //打印进程ID 线程ID 当前类 当前方法
        String message = time + " " + level + " "
                + "" + android.os.Process.myPid() + "|" + android.os.Process.myTid()
                + " [" + caller.getClassName() + "->" + caller.getMethodName() + "]"
                + " [" + generateTag(tag) + "]" + generateContent(content, args);
        mLogConfig.saveToFile(message);
    }
}
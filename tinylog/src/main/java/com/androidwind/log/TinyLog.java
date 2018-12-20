package com.androidwind.log;

import android.text.TextUtils;
import android.util.Log;

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

    private static LogConfig mLogConfig;

    public static LogConfig config() {
        if (mLogConfig == null) {
            mLogConfig = new LogConfig();
        }
        return mLogConfig;
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
        result = String.format(result, caller.getFileName(), caller.getLineNumber(), content + wrapperContent(objects));
        return result;
    }

    private static Object wrapperContent(Object... objects) {
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

    public static void v(String content) {
        if (!mLogConfig.isEnable) return;
        log(LOG_V, null, content, null);
    }

    public static void v(String tag, String content, Object... objects) {
        if (!mLogConfig.isEnable) return;
        log(LOG_V, tag, content, null, objects);
    }

    public static void log(int logSupport, String tag, String content, Throwable tr, Object... args) {
        switch (logSupport) {
            case LOG_V:
                Log.v(generateTag(tag), generateContent(content, args), tr);
                break;
            case LOG_D:
                break;
            case LOG_I:
                break;
            case LOG_W:
                break;
            case LOG_E:
                break;
            default:
                Log.wtf(generateTag(tag), generateContent(content, args), tr);
                break;
        }
    }
}
package com.androidwind.log;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * the core class of TintLog
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyLog {

    public static final int LOG_V = 0;
    public static final int LOG_D = 1;
    public static final int LOG_I = 2;
    public static final int LOG_W = 3;
    public static final int LOG_E = 4;

    private static TinyLogConfig sMTinyLogConfig;

    public static synchronized TinyLogConfig config() {
        if (sMTinyLogConfig == null) {
            sMTinyLogConfig = new TinyLogConfig();
        }
        return sMTinyLogConfig;
    }

    private static void preCheck() {
        if (sMTinyLogConfig == null) {
            throw new RuntimeException("[TinyLog Exception]: You should initialize TinyLogConfig first, such as \"Tiny.config()\".");
        }
        if (!sMTinyLogConfig.isEnable) return;
        if (sMTinyLogConfig.isWritable) {
            if (!sMTinyLogConfig.writeCheck) {
                sMTinyLogConfig.apply();
                sMTinyLogConfig.writeCheck = true;
            }
        }
    }

    public static void v(String content) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_V, null, content, null);
        }
        logConsole(LOG_V, null, content, null);
    }

    public static void v(String tag, String content, Object... objects) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_V, tag, content, null, objects);
        }
        logConsole(LOG_V, tag, content, null, objects);
    }

    public static void d(String content) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_D, null, content, null);
        }
        logConsole(LOG_D, null, content, null);
    }

    public static void d(String tag, String content, Object... objects) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_D, tag, content, null, objects);
        }
        logConsole(LOG_D, tag, content, null, objects);
    }

    public static void i(String content) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_I, null, content, null);
        }
        logConsole(LOG_I, null, content, null);
    }

    public static void i(String tag, String content, Object... objects) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_I, tag, content, null, objects);
        }
        logConsole(LOG_I, tag, content, null, objects);
    }

    public static void w(String content) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_W, null, content, null);
        }
        logConsole(LOG_W, null, content, null);
    }

    public static void w(String tag, String content, Object... objects) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_W, tag, content, null, objects);
        }
        logConsole(LOG_W, tag, content, null, objects);
    }

    public static void e(String content) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_E, null, content, null);
        }
        logConsole(LOG_E, null, content, null);
    }

    public static void e(String content, Throwable t, Object... objects) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_E, null, content, t, objects);
        }
        logConsole(LOG_E, null, content, t, objects);
    }

    public static void e(String tag, String content, Object... objects) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_E, tag, content, null, objects);
        }
        logConsole(LOG_E, tag, content, null, objects);
    }


    public static void e(String tag, String content, Throwable t, Object... objects) {
        preCheck();
        if (sMTinyLogConfig.isWritable) {
            logFile(LOG_E, tag, content, t, objects);
        }
        logConsole(LOG_E, tag, content, t, objects);
    }

    private static String generateTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            return tag;
        } else {
            StackTraceElement caller = new Throwable().getStackTrace()[3];
            String callerClazzName = caller.getClassName();
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
            String result = "%s.%s";
            result = String.format(result, callerClazzName, caller.getMethodName());
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
        if (logSupport < sMTinyLogConfig.logLevel) {
            return;
        }
        String logTag, logContent;
        logTag = generateTag(tag);
        logContent = generateContent(content, args);
        if (sMTinyLogConfig.mKey != null && !"".equals(sMTinyLogConfig.mKey)) {
            try {
                logContent = TinyLogUtil.encrypt(sMTinyLogConfig.mKey, logContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (sMTinyLogConfig.mLogCallBack != null) {
            sMTinyLogConfig.mLogCallBack.getLogString(logTag, logContent);
        }
        switch (logSupport) {
            case LOG_V:
                Log.v(logTag, logContent, tr);
                break;
            case LOG_D:
                Log.d(logTag, logContent, tr);
                break;
            case LOG_I:
                Log.i(logTag, logContent, tr);
                break;
            case LOG_W:
                Log.w(logTag, logContent, tr);
                break;
            case LOG_E:
                Log.e(logTag, logContent, tr);
                break;
            default:
                Log.wtf(logTag, logContent, tr);
                break;
        }
    }

    private static void logFile(int logSupport, String tag, String content, Throwable tr, Object... args) {
        if (logSupport < sMTinyLogConfig.logLevel) {
            return;
        }
        if (sMTinyLogConfig.mKey != null && !"".equals(sMTinyLogConfig.mKey)) {
            try {
                content = TinyLogUtil.encrypt(sMTinyLogConfig.mKey, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String error = tr != null ? "\n" + Log.getStackTraceString(tr) : "";
        //打印进程ID 线程ID 当前类 当前方法
        String message = time + " " + level + " "
                + "" + android.os.Process.myPid() + "|" + Thread.currentThread().getId()
                + " [" + callerClazzName + "->" + caller.getMethodName() + "]"
                + " [" + generateTag(tag) + "]" + generateContent(content, args) + error;
        sMTinyLogConfig.saveToFile(message);
    }
}
package com.androidwind.log.sample;

import android.app.Application;
import android.os.Environment;

import com.androidwind.log.BuildConfig;
import com.androidwind.log.TinyLog;

import java.io.File;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MyApplication extends Application {

    public static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1);
        //encrypt
        // TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1).setEncrypt("1234567812345678");
        //logLevel
        TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1).setLogLevel(TinyLog.LOG_I);
        // TinyLog.v("this is tinylog");
    }

    public String getLogDir() {
        String logDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logDir = Environment.getExternalStorageDirectory().getPath();
        } else {
            logDir = getFilesDir().getPath();
        }
        return logDir + File.separator + "Log";
    }

}

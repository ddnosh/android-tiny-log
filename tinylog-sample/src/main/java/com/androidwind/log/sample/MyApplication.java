package com.androidwind.log.sample;

import android.app.Application;

import com.androidwind.log.TinyLog;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TinyLog.config().setEnable(BuildConfig.DEBUG);
        TinyLog.v("this is tinylog");
    }

}

package com.androidwind.log.sample;

import android.app.Application;

import com.androidwind.log.TinyLog;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        TinyLog.init(BuildConfig.DEBUG);
    }

}

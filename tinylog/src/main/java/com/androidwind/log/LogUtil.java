package com.androidwind.log;

import android.os.Environment;

import java.io.File;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LogUtil {

    public static String getLogDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getPath() + File.separator + "Log";
        } else {
            throw new RuntimeException("there is no storage!");
        }

    }
}

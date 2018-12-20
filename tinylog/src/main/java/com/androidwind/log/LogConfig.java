package com.androidwind.log;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LogConfig implements LogConfigContract {

    //whether log
    public boolean isEnable;
    //whether log to file
    public boolean isWritable;
    //log path
    public String logPath;
    //printer
    private PrintWriter mPrintWriter;

    @Override
    public LogConfig setEnable(boolean enable) {
        this.isEnable = enable;
        return this;
    }

    @Override
    public LogConfig setWritable(boolean writable) {
        this.isWritable = writable;
        return this;
    }

    @Override
    public LogConfig setLogPath(String logPath) {
        this.logPath = logPath;
        return this;
    }

    @Override
    public void apply() {
        if (logPath == null) {
            logPath = LogUtil.getLogDir();
        }
        File dir = new File(logPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir , "log.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mPrintWriter = new PrintWriter(new FileWriter(file, true), true);
        } catch (IOException e) {
            Log.e("LogConfig", "PrintWriter IOException");
            e.printStackTrace();
        }
    }

    public void saveToFile(String message) {
        synchronized (mPrintWriter) {
            mPrintWriter.println(message);
        }
    }
}

package com.androidwind.log;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * configuration for TinyLog
 * we should use TinyLog.config() when app initialized
 *
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

    private static ExecutorService executor;

    @Override
    public LogConfigContract setEnable(boolean enable) {
        this.isEnable = enable;
        return this;
    }

    @Override
    public LogConfigContract setWritable(boolean writable) {
        this.isWritable = writable;
        return this;
    }

    @Override
    public LogConfigContract setLogPath(String logPath) {
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

        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void saveToFile(final String message) {
        executor.execute(new Runnable () {
            public void run() {
                synchronized (mPrintWriter) {
                    Log.v("LogConfig", Thread.currentThread().getName() + " : " + Thread.currentThread().getId());
                    mPrintWriter.println(message);
                }
            }
        });
    }
}

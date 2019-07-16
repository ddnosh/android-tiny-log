package com.androidwind.log;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ILogConfig's implementation class
 * we should use TinyLog.config() when app initialized
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LogConfig implements ILogConfig {

    //enable log or not
    boolean isEnable;
    //whether log to file
    boolean isWritable;
    //log path
    private String mLogPath;
    //single file size, unit:M
    private int mFileSize = 1;
    //output callback
    LogCallBack mLogCallBack;
    //printer
    private PrintWriter mPrintWriter;
    //currentFile
    private File mCurrentFile;
    //encrypt && decrypt
    String mKey;
    //output log level when >= logLevel
    int logLevel = TinyLog.LOG_V;

    private static ExecutorService executor;

    @Override
    public ILogConfig setEnable(boolean enable) {
        this.isEnable = enable;
        return this;
    }

    @Override
    public ILogConfig setWritable(boolean writable) {
        this.isWritable = writable;
        return this;
    }

    @Override
    public ILogConfig setLogPath(String logPath) {
        this.mLogPath = logPath;
        return this;
    }

    @Override
    public ILogConfig setFileSize(int fileSize) {
        if (fileSize < 1) {
            this.mFileSize = 1;
        }
        this.mFileSize = fileSize;
        return this;
    }

    @Override
    public ILogConfig setLogCallBack(LogCallBack callBack) {
        this.mLogCallBack = callBack;
        return this;
    }

    @Override
    public ILogConfig setEncrypt(String key) {
        this.mKey = key;
        return this;
    }

    @Override
    public ILogConfig setLogLevel(int level) {
        this.logLevel = level;
        return this;
    }

    @Override
    public void apply() {
        if (!isWritable)
            return;
        if (mLogPath == null) {
            mLogPath = LogUtil.getLogDir();
        }
        File dir = new File(mLogPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        createFile();

        executor = Executors.newSingleThreadExecutor(); //file operation should use single thread pool
    }

    public void saveToFile(final String message) {
        executor.execute(new Runnable () {
            public void run() {
                synchronized (mPrintWriter) {
                    checkFile();
                    Log.v("LogConfig", Thread.currentThread().getName() + " : " + Thread.currentThread().getId());
                    mPrintWriter.println(message);
                }
            }
        });
    }

    private void createFile() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        String time = sf.format(new Date());
        mCurrentFile = new File(mLogPath , time + ".txt");
        try {
            mPrintWriter = new PrintWriter(new FileWriter(mCurrentFile, true), true);
        } catch (IOException e) {
            Log.e("LogConfig", "PrintWriter IOException");
            e.printStackTrace();
            throw new RuntimeException("[TinyLog Exception]: " + e.getMessage());
        }
    }

    private void checkFile() {
        long fileSizeM = (mCurrentFile.length() / 1024 / 1024);// convert to M bytes
        Log.v("LogConfig", mCurrentFile.getName() + ", " + "file size: " + mCurrentFile.length() + " byte");
        if (fileSizeM >= mFileSize) {
            createFile();
        }
    }

    public interface LogCallBack {
        void getLogString(String tag, String content);
    }
}

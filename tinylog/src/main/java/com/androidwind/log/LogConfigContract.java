package com.androidwind.log;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface LogConfigContract {
    LogConfigContract setEnable(boolean isEnable);

    LogConfigContract setWritable(boolean writable);

    LogConfigContract setLogPath(String logPath);

    LogConfigContract setFileSize(int fileSize);

    void apply();
}

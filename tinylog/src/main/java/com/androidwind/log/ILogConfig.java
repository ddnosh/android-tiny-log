package com.androidwind.log;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface ILogConfig {
    ILogConfig setEnable(boolean isEnable);

    ILogConfig setWritable(boolean writable);

    ILogConfig setLogPath(String logPath);

    ILogConfig setFileSize(int fileSize);

    ILogConfig setLogCallBack(LogConfig.LogCallBack callBack);

    void apply();
}

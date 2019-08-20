package com.androidwind.log;

/**
 * interface to define the implementation class's function
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface ITinyLogConfig {
    ITinyLogConfig setEnable(boolean isEnable);

    ITinyLogConfig setWritable(boolean writable);

    ITinyLogConfig setLogPath(String logPath);

    ITinyLogConfig setFileSize(int fileSize);

    ITinyLogConfig setLogCallBack(TinyLogConfig.LogCallBack callBack);

    ITinyLogConfig setEncrypt(String key);

    ITinyLogConfig setLogLevel(int level);

    void apply();
}

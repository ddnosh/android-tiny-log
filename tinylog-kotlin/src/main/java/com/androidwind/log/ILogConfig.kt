package com.androidwind.log

/**
 * interface to define the implementation class's function
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
interface ILogConfig {
    fun setEnable(isEnable: Boolean): ILogConfig

    fun setWritable(writable: Boolean): ILogConfig

    fun setLogPath(logPath: String): ILogConfig

    fun setFileSize(fileSize: Int): ILogConfig

    fun setLogCallBack(callBack: LogConfig.LogCallBack): ILogConfig

    fun setEncrypt(key: String): ILogConfig

    fun setLogLevel(level: Int): ILogConfig

    fun apply()
}

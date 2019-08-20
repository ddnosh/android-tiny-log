package com.androidwind.log

/**
 * interface to define the implementation class's function
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
interface ITinyLogConfig {
    fun setEnable(isEnable: Boolean): ITinyLogConfig

    fun setWritable(writable: Boolean): ITinyLogConfig

    fun setLogPath(logPath: String): ITinyLogConfig

    fun setFileSize(fileSize: Int): ITinyLogConfig

    fun setLogCallBack(callBack: TinyLogConfig.LogCallBack): ITinyLogConfig

    fun setEncrypt(key: String): ITinyLogConfig

    fun setLogLevel(level: Int): ITinyLogConfig

    fun apply()
}

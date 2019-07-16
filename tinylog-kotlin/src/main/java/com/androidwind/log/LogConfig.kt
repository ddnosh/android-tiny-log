package com.androidwind.log

import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * ILogConfig's implementation class
 * we should use TinyLog.config() when app initialized
 *
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class LogConfig : ILogConfig {
    var isEnable: Boolean = false
    var isWritable: Boolean = false;
    var mLogPath: String? = null
    var mFileSize: Int = 1
    var mLogCallBack: LogCallBack? = null
    var mPrintWriter: PrintWriter? = null
    var mCurrentFile: File? = null
    var mKey: String? = null
    //output log level when >= logLevel
    var logLevel: Int = TinyLog.LOG_V

    companion object {
        var executor: ExecutorService? = null
    }

    override fun setEnable(isEnable: Boolean): ILogConfig {
        this.isEnable = isEnable
        return this
    }

    override fun setWritable(writable: Boolean): ILogConfig {
        this.isWritable = writable
        return this
    }

    override fun setLogPath(logPath: String): ILogConfig {
        this.mLogPath = logPath
        return this
    }

    override fun setFileSize(fileSize: Int): ILogConfig {
        if (fileSize < 1) {
            this.mFileSize = 1
        }
        this.mFileSize = fileSize
        return this
    }

    override fun setLogCallBack(callBack: LogConfig.LogCallBack): ILogConfig {
        this.mLogCallBack = callBack
        return this
    }

    override fun setEncrypt(key: String): ILogConfig {
        this.mKey = key
        return this
    }

    override fun setLogLevel(level: Int): ILogConfig {
        this.logLevel = level
        return this
    }

    override fun apply() {
        if (!isWritable)
            return
        if (mLogPath == null)
            mLogPath = LogUtil.logDir
        val dir: File = File(mLogPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        createFile()
        executor = Executors.newSingleThreadExecutor(); //file operation should use single thread pool
    }

    fun saveToFile(message: String) {
        executor?.execute(Runnable {
            synchronized(mPrintWriter!!) {
                checkFile()
                Log.v("LogConfig", Thread.currentThread().name + " : " + Thread.currentThread().id)
                mPrintWriter!!.println(message)
            }
        })
    }

    fun createFile() {
        val sf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        val time: String = sf.format(Date())
        mCurrentFile = File(mLogPath, "$time.txt")
        try {
            mPrintWriter = PrintWriter(FileWriter(mCurrentFile, true), true)
        } catch (e: IOException) {
            Log.e("LogConfig", "PrintWriter IOException")
            e.printStackTrace()
            throw RuntimeException("[TinyLog Exception]: " + e.message)
        }
    }

    private fun checkFile() {
        val fileSizeM = mCurrentFile!!.length() / 1024 / 1024// convert to M bytes
        Log.v("LogConfig", mCurrentFile!!.name + ", " + "file size: " + mCurrentFile!!.length() + " byte")
        if (fileSizeM >= mFileSize) {
            createFile()
        }
    }

    interface LogCallBack {
        fun getLogString(tag: String, content: String)
    }
}
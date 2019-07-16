package com.androidwind.log.sample

import android.app.Application
import android.os.Environment
import com.androidwind.log.BuildConfig
import com.androidwind.log.TinyLog
import java.io.File

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class MyApplication : Application() {
    companion object {
        lateinit var application: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        // TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1).apply();
        //encrypt
        // TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1).setEncrypt("1234567812345678").apply();
        //logLevel
        TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1).setLogLevel(TinyLog.LOG_I).apply()
        TinyLog.v("this is tinylog")
    }

    private fun getLogDir(): String {
        val logDir: String
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            logDir = Environment.getExternalStorageDirectory().path
        } else {
            logDir = filesDir.path
        }
        return logDir + File.separator + "Log"
    }
}
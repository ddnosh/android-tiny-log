package com.androidwind.log

import android.text.TextUtils
import android.util.Log

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
object TinyLog {

    private val LOG_V = 0
    private val LOG_D = 1
    private val LOG_I = 2
    private val LOG_W = 3
    private val LOG_E = 4

    private val mLogConfig: LogConfig by lazy { LogConfig() }

    fun config(): LogConfig {
        return mLogConfig
    }

    private fun preCheck() {
        if (mLogConfig == null)
            throw RuntimeException("[TinyLog Exception]: You should initialize LogConfig first, such as \"Tiny.config()\".")
    }

    fun v(content: String) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_V, null, content, null)
        }
        logConsole(LOG_V, null, content, null)
    }

    fun v(tag: String, content: String, vararg objects: Any) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_V, tag, content, null, *objects)
        }
        logConsole(LOG_V, tag, content, null, *objects)
    }

    fun d(content: String) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_D, null, content, null)
        }
        logConsole(LOG_D, null, content, null)
    }

    fun d(tag: String, content: String, vararg objects: Any) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_D, tag, content, null, *objects)
        }
        logConsole(LOG_D, tag, content, null, *objects)
    }

    fun i(content: String) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_I, null, content, null)
        }
        logConsole(LOG_I, null, content, null)
    }

    fun i(tag: String, content: String, vararg objects: Any) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_I, tag, content, null, *objects)
        }
        logConsole(LOG_I, tag, content, null, *objects)
    }

    fun w(content: String) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_W, null, content, null)
        }
        logConsole(LOG_W, null, content, null)
    }

    fun w(tag: String, content: String, vararg objects: Any) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_W, tag, content, null, *objects)
        }
        logConsole(LOG_W, tag, content, null, *objects)
    }

    fun e(content: String) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_E, null, content, null)
        }
        logConsole(LOG_E, null, content, null)
    }

    fun e(content: String, t: Throwable, vararg objects: Any) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_E, null, content, t, *objects)
        }
        logConsole(LOG_E, null, content, t, *objects)
    }

    fun e(tag: String, content: String, vararg objects: Any) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_E, tag, content, null, *objects)
        }
        logConsole(LOG_E, tag, content, null, *objects)
    }


    fun e(tag: String, content: String, t: Throwable, vararg objects: Any) {
        preCheck()
        if (!mLogConfig.isEnable) return
        if (mLogConfig.isWritable) {
            logFile(LOG_E, tag, content, t, *objects)
        }
        logConsole(LOG_E, tag, content, t, *objects)
    }

    private fun generateTag(tag: String?): String {
        if (!TextUtils.isEmpty(tag)) {
            return tag!!
        } else {
            val caller = Throwable().stackTrace[3]
            var callerClazzName = caller.className
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
            var result = "%s.%s"
            result = String.format(result, callerClazzName, caller.methodName)
            return result
        }
    }

    private fun generateContent(content: String?, vararg objects: Any): String {
        val caller = Throwable().stackTrace[3]
        var result = "(%s:%d) %s"
        result = String.format(result, caller.fileName, caller.lineNumber, content!! + wrapContent(*objects))
        return result
    }

    private fun wrapContent(vararg objects: Any): Any {
        if (objects.size > 0) {
            val stringBuilder = StringBuilder()
            stringBuilder.append("\n")
            for (i in objects.indices) {
                val `object` = objects[i]
                if (`object` == null) {
                    stringBuilder.append("arg").append("[").append(i).append("]").append(" = ").append("null").append("\n")
                } else {
                    stringBuilder.append("arg").append("[").append(i).append("]").append(" = ").append(`object`.toString()).append("\n")
                }
            }
            return stringBuilder.toString()
        }
        return ""
    }

    private fun logConsole(logSupport: Int, tag: String?, content: String, tr: Throwable?, vararg args: Any) {
        val logTag: String
        var logContent: String?
        logTag = generateTag(tag)
        logContent = generateContent(content, *args)
        if (mLogConfig.key != null && "" != mLogConfig.key) {
            try {
                logContent = LogUtil.encrypt(mLogConfig.key!!, logContent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        mLogConfig.mLogCallBack?.getLogString(logTag, logContent!!)
        when (logSupport) {
            LOG_V -> Log.v(logTag, logContent, tr)
            LOG_D -> Log.d(logTag, logContent, tr)
            LOG_I -> Log.i(logTag, logContent, tr)
            LOG_W -> Log.w(logTag, logContent, tr)
            LOG_E -> Log.e(logTag, logContent, tr)
            else -> Log.wtf(logTag, logContent, tr)
        }
    }

    private fun logFile(logSupport: Int, tag: String?, content: String?, tr: Throwable?, vararg args: Any) {
        var content = content
        if (mLogConfig.key != null && "" != mLogConfig.key) {
            try {
                content = LogUtil.encrypt(mLogConfig.key!!, content!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val time = sf.format(Date())
        val level: String
        when (logSupport) {
            LOG_V -> level = "V"
            LOG_D -> level = "D"
            LOG_I -> level = "I"
            LOG_W -> level = "W"
            LOG_E -> level = "E"
            else -> level = "WTF"
        }
        val caller = Throwable().stackTrace[2]
        var callerClazzName = caller.className
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
        val error = if (tr != null) "\n" + Log.getStackTraceString(tr) else ""
        //打印进程ID 线程ID 当前类 当前方法
        val message = (time + " " + level + " "
            + "" + android.os.Process.myPid() + "|" + Thread.currentThread().id
            + " [" + callerClazzName + "->" + caller.methodName + "]"
            + " [" + generateTag(tag) + "]" + generateContent(content, *args) + error)
        mLogConfig.saveToFile(message)
    }
}
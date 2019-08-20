package com.androidwind.log.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.View

import com.androidwind.log.TinyLogConfig
import com.androidwind.log.TinyLog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mResult: String? = null
    private val mStringBuilder: StringBuilder by lazy { StringBuilder() }
    private var times: Int = 0

    private val mLogCallBack = object : TinyLogConfig.LogCallBack {
        override fun getLogString(tag: String, content: String) {
            mStringBuilder.append("$tag -----------> $content").append("\n")
            this@MainActivity.runOnUiThread { tv_output?.text = mStringBuilder.toString() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_click_once.setOnClickListener(this)
        btn_click_more.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
        tv_output?.movementMethod = ScrollingMovementMethod.getInstance()

        TinyLog.config().setLogCallBack(mLogCallBack)
    }

    private fun printLog() {
        TinyLog.v("this is tinylog")
        TinyLog.d("TAG", "this is tinylog")
        TinyLog.i("TAG", "this is tinylog", "arg1")
        TinyLog.w("TAG", "this is tinylog", "arg1", "arg2")
        try {
            val s: String? = null
            s!!.toString()
        } catch (e: NullPointerException) {
            TinyLog.e("TAG", e, "printStackTrace")
        }

        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_click_once) {
            mStringBuilder.delete(0, mStringBuilder.length)
            tv_output?.text = mStringBuilder.toString()
            printLog()
        } else if (v.id == R.id.btn_click_more) {
            mStringBuilder.delete(0, mStringBuilder.length)
            tv_output?.text = mStringBuilder.toString()
            if (TextUtils.isEmpty(et_times.getText().toString())) {
                return
            }
            times = Integer.valueOf(et_times.getText().toString())
            Thread(Runnable {
                //test
                for (i in 0 until times) {
                    printLog()
                }
            }).start()
        } else if (v.id == R.id.btn_clear) {
            mStringBuilder.delete(0, mStringBuilder.length)
            tv_output?.text = mStringBuilder.toString()
        }
    }
}

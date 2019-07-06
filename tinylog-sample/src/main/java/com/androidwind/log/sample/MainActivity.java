package com.androidwind.log.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidwind.log.LogConfig;
import com.androidwind.log.TinyLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String mResult;
    private StringBuilder mStringBuilder;
    private int times;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn_click_once);
        btn1.setOnClickListener(this);
        Button btn2 = findViewById(R.id.btn_click_more);
        btn2.setOnClickListener(this);
        Button btn3 = findViewById(R.id.btn_clear);
        btn3.setOnClickListener(this);
        result = findViewById(R.id.tv_output);
        result.setMovementMethod(ScrollingMovementMethod.getInstance());

        TinyLog.config().setLogCallBack(mLogCallBack);
        mStringBuilder = new StringBuilder();
    }

    private void printLog() {
        TinyLog.v("this is tinylog");
        TinyLog.d("TAG", "this is tinylog");
        TinyLog.i("TAG", "this is tinylog", "arg1");
        TinyLog.w("TAG", "this is tinylog", "arg1", "arg2");
        try {
            String s = null;
            s.toString();
        } catch (NullPointerException e) {
            TinyLog.e("TAG", e, "printStackTrace");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click_once) {
            mStringBuilder.delete(0, mStringBuilder.length());
            result.setText(mStringBuilder.toString());
            printLog();
        } else if (v.getId() == R.id.btn_click_more) {
            mStringBuilder.delete(0, mStringBuilder.length());
            result.setText(mStringBuilder.toString());

            EditText etTimes = findViewById(R.id.et_times);
            if (TextUtils.isEmpty(etTimes.getText().toString())) {
                return;
            }
            times = Integer.valueOf(etTimes.getText().toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //test
                    for (int i = 0; i < times; i++){
                        printLog();
                    }
                }
            }).start();
        } else if (v.getId() == R.id.btn_clear) {
            mStringBuilder.delete(0, mStringBuilder.length());
            result.setText(mStringBuilder.toString());
        }
    }

    private LogConfig.LogCallBack mLogCallBack = new LogConfig.LogCallBack() {
        @Override
        public void getLogString(String tag, String content) {
            mStringBuilder.append(tag + " -----------> " + content).append("\n");
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    result.setText(mStringBuilder.toString());
                }
            });
        }
    };
}

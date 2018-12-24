package com.androidwind.log.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidwind.log.TinyLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //test
                for (int i = 0; i < 1; i++){
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
                }
            }
        }).start();

    }

}

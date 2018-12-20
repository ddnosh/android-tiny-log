package com.androidwind.log.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidwind.log.TinyLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TinyLog.v("this is tinylog");
        TinyLog.v("TAG", "this is tinylog");
        TinyLog.v("TAG", "this is tinylog", "arg1");
        TinyLog.v("TAG", "this is tinylog", "arg1", "arg2");
    }

}

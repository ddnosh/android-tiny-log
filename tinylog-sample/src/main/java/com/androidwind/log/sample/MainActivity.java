package com.androidwind.log.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidwind.log.TinyLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TinyLog.d("112233");
    }
}

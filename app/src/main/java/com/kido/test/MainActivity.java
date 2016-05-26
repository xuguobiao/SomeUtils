package com.kido.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kido.someutils.AsynThreadPool;
import com.kido.someutils.Logger;
import com.kido.someutils.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private final static String TAG = "kido";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.log_btn).setOnClickListener(this);
        findViewById(R.id.thread_btn).setOnClickListener(this);
        Logger.init(Logger.LogCondition.ACCORDING_SD_FILE, Logger.LogLevel.VERBOSE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_btn:
                Logger.v(TAG, "verbose");
                Logger.d(TAG, "debug");
                Logger.i(TAG, "info");
                Logger.w(TAG, "warn");
                Logger.e(TAG, "error");
                Logger.print(new NullPointerException("kido test"));
                break;
            case R.id.thread_btn:
                AsynThreadPool.getInstance().runThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "i am runnable...", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                break;
        }
    }
}

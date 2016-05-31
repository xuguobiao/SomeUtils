package com.kido.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kido.someutils.AsynThreadPool;
import com.kido.someutils.ClassFactory;
import com.kido.someutils.Logger;
import com.kido.someutils.R;

import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {

    private final static String TAG = "kido";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.log_btn).setOnClickListener(this);
        findViewById(R.id.thread_btn).setOnClickListener(this);
        findViewById(R.id.test_btn).setOnClickListener(this);

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
            case R.id.test_btn:

                ITest1 test1 = ClassFactory.create(Test1Impl.class);
                ITest1 test1_2 = ClassFactory.create(Test1Impl.class);

                ITest1 test1_new = ClassFactory.newInstance(Test1Impl.class);

                ITest2 test2 = ClassFactory.create(Test2Impl.class);

                ITest2 test2_1 = ClassFactory.create(Test2Impl.class);


                ITest1 test1_remove = ClassFactory.remove(Test1Impl.class);
                ITest2 test2_remove = ClassFactory.remove(Test2Impl.class);

                Map<Class<?>, Object> sInstanceMap = ClassFactory.sInstanceMap;

                Log.d("","");

                break;
        }
    }
}

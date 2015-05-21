package com.example.uur.stock;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import android.os.Handler;
import android.widget.TextView;

/**
 * Created by Uğur on 12.5.2015.
 */
public class SplashActivity extends Activity{
    private static long SLEEP_TIME = 5;
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private TextView textView;
    private Handler mHandler = new Handler();


    private static String TAG = SplashActivity.class.getName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        mProgress = (ProgressBar)findViewById(R.id.pbSplashScreen);
        textView = (TextView)findViewById(R.id.txtTitleOfProgress);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();

    }
    private class IntentLauncher extends Thread{
        @Override
        public void run()
        {
            while (mProgressStatus < 100)
            {
                mProgressStatus += 1;

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setProgress(mProgressStatus);
                        textView.setText(mProgressStatus + " / " + mProgress.getMax());
                    }
                });
                try {
                    Thread.sleep(25 * SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

}
//
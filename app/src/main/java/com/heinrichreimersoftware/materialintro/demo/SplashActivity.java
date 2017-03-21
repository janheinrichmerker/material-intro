package com.heinrichreimersoftware.materialintro.demo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.heinrichreimersoftware.materialintro.demo.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private final Handler waitHandler = new Handler();
    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, FinishActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        //Fake wait 2s to simulate some initialization on cold start (never do this in production!)
        waitHandler.postDelayed(waitCallback, 2000);
    }

    @Override
    protected void onDestroy() {
        waitHandler.removeCallbacks(waitCallback);
        super.onDestroy();
    }
}

package com.vendor.vendorpannel.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vendor.vendorpannel.R;
import com.vendor.vendorpannel.SharedPreferences.SharedPreference_Config;


public class SplashActivity extends AppCompatActivity {
    private TextView textView;
    private static int SPLASH_TIME_OUT = 1000;
    private SharedPreference_Config sharedPreference_config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_activity);
        sharedPreference_config = new SharedPreference_Config(getApplicationContext());
        checkState();
        textView = findViewById(R.id.txtVendorPanel);

//


    }

    public void checkState() {
        if (sharedPreference_config.readLoginStatus()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, SPLASH_TIME_OUT);

        }

    }


}
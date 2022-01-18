package com.example.wallpaperapplication.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.login.LoginActivity;
import com.example.wallpaperapplication.models.User;
import com.example.wallpaperapplication.session.SharedPreference;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 1500;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pbLoading = findViewById(R.id.pb_loading_splash);

        SharedPreference sharedPreference = new SharedPreference(SplashActivity.this);
        User user = sharedPreference.load();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user.getName().isEmpty())
                {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
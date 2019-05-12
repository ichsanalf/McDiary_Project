package com.tubes.mcdiary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SplashScreen
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(Splashscreen.this, LoginActivity.class);

                startActivity(i);

                finish();
            }
        },2000);

    }

}
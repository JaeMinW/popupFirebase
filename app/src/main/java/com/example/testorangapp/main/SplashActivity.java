package com.example.testorangapp.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.testorangapp.R;
import com.example.testorangapp.auth.IntroActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null || mAuth.getCurrentUser().getUid() == null){
            Log.d("SplashActivity", "null");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                }
            },3000);
        }else{
            Log.d("SplashActivity", "not null");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            },3000);
        }

    }
}
package com.example.picnedit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends AppCompatActivity {

    TextView tv_splash_pic,tv_splash_n,tv_splash_edit,tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv_splash_pic = findViewById(R.id.tv_splash_pic);
        tv_splash_n = findViewById(R.id.tv_splash_n);
        tv_splash_edit = findViewById(R.id.tv_splash_edit);
        tv_version = findViewById(R.id.tv_version);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);

        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(tv_splash_pic);
        YoYo.with(Techniques.FadeIn).duration(3000).repeat(0).playOn(tv_splash_n);
        YoYo.with(Techniques.FadeIn).duration(5000).repeat(0).playOn(tv_splash_edit);

        YoYo.with(Techniques.FadeOut).duration(3000).repeat(0).playOn(tv_version);



    }
}

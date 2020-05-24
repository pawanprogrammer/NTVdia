package com.example.railwayhvac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sp;
    Boolean firsttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sp=getSharedPreferences("file2",0);
        firsttime=sp.getBoolean("firsttime",true);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!firsttime){
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));

                    finish();

                }

                else {
                    startActivity(new Intent(SplashScreen.this, Slides.class));

                    finish();
                }



            }
        },2000);
    }
}

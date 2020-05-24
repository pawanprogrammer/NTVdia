package com.example.railwayhvac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.github.paolorotolo.appintro.AppIntro;
import com.viewpagerindicator.PageIndicator;

public class Slides extends AppIntro {


    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sp=getSharedPreferences("file2",0);
        editor=sp.edit();

        addSlide(new Slide1());
        addSlide(new Slide2());
        addSlide(new Slide3());
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        editor.putBoolean("firsttime",false);
        editor.apply();
        startActivity(new Intent(Slides.this,LoginActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        editor.putBoolean("firsttime",false);
        editor.apply();

        startActivity(new Intent(Slides.this,LoginActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(Fragment oldFragment,  Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}

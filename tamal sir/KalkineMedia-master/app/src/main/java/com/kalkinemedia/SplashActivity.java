package com.kalkinemedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.kalkinemedia.Authentication.PhoneLoginActivity;
import com.kalkinemedia.Authentication.ProfileActivity;
import com.kalkinemedia.Dashboard.DashboardActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.Shape;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class SplashActivity extends AppCompatActivity implements MaterialIntroListener {
    VideoView videoView;
    TextView tv_createaccount;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        videoView = findViewById(R.id.videoview);
        tv_createaccount=findViewById(R.id.tv_createaccount);
        linearLayout = findViewById(R.id.linear1);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        showIntro(linearLayout, "first click", "Just Click to Register", FocusGravity.RIGHT);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        });
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mine);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();

            }
        });
        videoView.start();
    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void onUserClicked(String s) {
        new MaterialIntroView.Builder(SplashActivity.this)
                .enableDotAnimation(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText("Click here to create new account")
                .setTarget(linearLayout.getRootView())
                .setListener(this)
                .setUsageId(s) //THIS SHOULD BE UNIQUE ID
                .show();
    }
    public void showIntro(View view, String id, String text, FocusGravity focusGravity){
        new MaterialIntroView.Builder(SplashActivity.this)

                .enableDotAnimation(true)
                .setFocusGravity(FocusGravity.RIGHT)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText(text)
                .setTarget(view)
                .setListener(this)
                .setUsageId(id)//THIS SHOULD BE UNIQUE ID
                .show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("kalkine", 0);
        if (sp.getBoolean("b", false) == true)
        {
            Intent intent = new Intent(SplashActivity.this,
                    DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
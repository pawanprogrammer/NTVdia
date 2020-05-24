package com.kalkinemedia.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kalkinemedia.MainActivity;
import com.kalkinemedia.Notification.MyNotificationOpenedHandler;
import com.kalkinemedia.R;
import com.kalkinemedia.Service.KalkinemediaService;
import com.onesignal.OneSignal;



public class  PhoneLoginActivity extends AppCompatActivity {
    private EditText editTextMobile;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this is my first Activity
        OneSignal.startInit(this).setNotificationOpenedHandler(new MyNotificationOpenedHandler(this))
                .init();
        setContentView(R.layout.activity_phone_login);
        //task done
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //foreground service.
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, KalkinemediaService.class));
        } else {
            startService(new Intent(this, KalkinemediaService.class));
        }*/
/*
        String token = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(this, token, Toast.LENGTH_LONG).show();
        Log.d("mytoken",""+ token);
*/

        editTextMobile = findViewById(R.id.editTextMobile);
        tv1 = findViewById(R.id.tv1);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/myfont.ttf");

        tv1.setTypeface(custom_font);
        getSupportActionBar().hide();
        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();
                String smobile = mobile.substring(1);

                if (smobile.isEmpty() ) {
                    editTextMobile.setError("Enter a mobile Number");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneLoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", smobile);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("kalkine", 0);

        if (sp.getBoolean("b", false) == true) {
            Intent intent = new Intent(PhoneLoginActivity.this,
                    MainActivity.class);
            intent.putExtra("termid", "https://kalkinemedia.com/");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(PhoneLoginActivity.this, NotificationService.class);
        startService(intent);
    }*/

}

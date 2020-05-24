package com.apkglobal.lifecycle;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
ConstraintLayout view;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
        //Snackbar.make(view, "OnCreate", 3000).show();
        Snackbar sn = Snackbar.make(view, Html.fromHtml("<font color=\"#000000\">Tap to open</font>"), 3000);
        View myview =sn.getView();
        myview.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                R.color.white));
        sn.show();
        /*Toast.makeText(this,
                "onCreate", Toast.LENGTH_LONG).show();*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("apkglobal", "onStart");
        /*Toast.makeText(this,
                "onStart", Toast.LENGTH_LONG).show();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Toast.makeText(this,
                "onResume", Toast.LENGTH_LONG).show();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*Toast.makeText(this,
                "onPause", Toast.LENGTH_LONG).show();*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*Toast.makeText(this,
                "onStop", Toast.LENGTH_LONG).show();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*Toast.makeText(this,
                "onDestroy", Toast.LENGTH_LONG).show();*/
    }
}

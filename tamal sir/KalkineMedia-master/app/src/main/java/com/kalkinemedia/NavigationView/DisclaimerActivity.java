package com.kalkinemedia.NavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kalkinemedia.R;

public class DisclaimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        getSupportActionBar().setTitle("Disclaimer");
    }
}

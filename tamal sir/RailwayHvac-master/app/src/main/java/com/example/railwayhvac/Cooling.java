package com.example.railwayhvac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Cooling extends AppCompatActivity {

    Spinner s1,s2,s3,s4,s5,s6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cooling);

        String[] inputs1={"HI - Summer"};
        String[] inputs2={"User-Define"};

        s1=findViewById(R.id.spinner1);
        s2=findViewById(R.id.spinner2);
        s3=findViewById(R.id.spinner3);
        s4=findViewById(R.id.spinner4);
        s5=findViewById(R.id.spinner5);
        s6=findViewById(R.id.spinner6);

        s1.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,inputs1));
        s2.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,inputs2));
        s3.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,inputs2));
        s4.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,inputs2));
        s5.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,inputs2));
        s6.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,inputs2));


    }
}

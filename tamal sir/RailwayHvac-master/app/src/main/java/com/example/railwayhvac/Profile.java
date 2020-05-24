package com.example.railwayhvac;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {


    TextView pusername,pemail,pcompanyname,pnumber;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp=getSharedPreferences("file1",0);

        pusername=findViewById(R.id.p_username);
        pemail=findViewById(R.id.p_email);
        pcompanyname=findViewById(R.id.p_companyname);
        pnumber=findViewById(R.id.p_number);


        pusername.setText(sp.getString("nameid",""));
        pnumber.setText(sp.getString("mobileid",""));
        pcompanyname.setText(sp.getString("companynameid",""));
        pemail.setText(sp.getString("emailid",""));

    }
}

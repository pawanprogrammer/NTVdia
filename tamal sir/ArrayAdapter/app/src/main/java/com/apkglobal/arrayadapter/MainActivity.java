package com.apkglobal.arrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
ListView listView;
String [] name = {"Select Name","pawan", "raman", "chaman", "naman", "hasan", "tasan"};
   Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // listView = findViewById(R.id.listview);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, name);
        spinner.setAdapter(aa);

        /*to click on the spinner*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,
                        name[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
/*        listView.setAdapter(aa);

        *//*to click on the listview*//*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,
                        name[i], Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}

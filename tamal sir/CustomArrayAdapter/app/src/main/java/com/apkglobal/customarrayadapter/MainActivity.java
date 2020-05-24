package com.apkglobal.customarrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ListView listView;
List<Pojo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listview);
        list = new ArrayList<>();
        /*to add the data into list*/
        list.add(new Pojo("vijay", "professional", R.mipmap.ic_launcher));
        list.add(new Pojo("pawan", "professional", R.mipmap.ic_launcher));
        list.add(new Pojo("raman", "professional", R.mipmap.ic_launcher));
        list.add(new Pojo("chaman", "professional", R.mipmap.ic_launcher));
        list.add(new Pojo("hasan", "professional", R.mipmap.ic_launcher));
        list.add(new Pojo("tasan", "professional", R.mipmap.ic_launcher));
        list.add(new Pojo("aman", "professional", R.mipmap.ic_launcher));

        /*to call the adapter*/
        MyAdapter myAdapter = new MyAdapter(this, R.layout.item ,list);
        listView.setAdapter(myAdapter);



    }
}

package com.example.railwayhvac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


   ImageView openDrawer;
   DrawerLayout drawerLayout;
   RecyclerView recyclerView;
   ArrayList<Menu> menuList;
   TextView drawerUsername;
   Button coolingButton;

   SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coolingButton=findViewById(R.id.cooling_button);

        sp=getSharedPreferences("file1",0);

        drawerUsername=findViewById(R.id.drawer_username);
        drawerUsername.setText(sp.getString("nameid",""));

        openDrawer=findViewById(R.id.open_drawer);
        drawerLayout=findViewById(R.id.drawer_layout);
        recyclerView=findViewById(R.id.recycler_view);

        menuList=new ArrayList<>();
        menuList.add(new Menu(R.drawable.ic_home_black_24dp,"Home"));
        menuList.add(new Menu(R.drawable.ic_person_outline_black_24dp,"Profile"));
        menuList.add(new Menu(R.drawable.ic_phone_black_24dp,"Contact Us"));
        menuList.add(new Menu(R.drawable.ic_share_black_24dp,"Share App"));

        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.START);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter(this,menuList));


        coolingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Cooling.class));
            }
        });

    }




}


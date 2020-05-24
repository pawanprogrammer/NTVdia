package com.trishasofttech.database.Sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trishasofttech.database.R;

public class BlogActivity extends AppCompatActivity {
private EditText etname,etemail,etmsg;
private Button btnsave, btnview;
private SQLiteDatabase sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        etemail = findViewById(R.id.etemail);
        etmsg = findViewById(R.id.etmsg);
        etname = findViewById(R.id.etname);

        btnsave = findViewById(R.id.btnsave);
        btnview = findViewById(R.id.btnview);
        /* to create the database*/
        sd = openOrCreateDatabase("dbname", 0, null);
        /*to create the table*/
        sd.execSQL("create table if not exists pawan (name varchar (150), email varchar(250), msg varchar(500))");
        /*to save the data in sqlite*/
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*to store the data inside the table*/
                sd.execSQL("insert into pawan values ('"+etname.getText().toString()+"', '"+etemail.getText().toString()+"', '"+etmsg.getText().toString()+"')");

                /*to clear the form*/
                /*etmsg.setText("");
                etemail.setText("");
                etname.setText("");*/
            }
        });
        /*to fetch the data from sqlite*/
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = sd.rawQuery("select * from pawan", null);
                c.moveToLast();
                Toast.makeText(BlogActivity.this, c.getString(0)+"\n"+c.getString(1)+
                        "\n"+c.getString(2), Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.example.railwayhvac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    Button loginbtn;
    RequestQueue requestQueue;
    EditText emailid,passwordid;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp=getSharedPreferences("file3",0);
        editor=sp.edit();

        if (sp.contains("emailid") && sp.contains("passwordid")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        emailid=findViewById(R.id.user_name);
        passwordid=findViewById(R.id.pass_word);

        requestQueue= Volley.newRequestQueue(this);

        signUp=findViewById(R.id.sign_up);
        loginbtn=findViewById(R.id.login);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });

    }


    void login(){
        String url="http://searchkero.com/tamal/register.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                toast("completed");

                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                toast("error");
            }
        }


        ){

            @Override
            protected Map<String, String> getParams()  {
                HashMap<String,String> params=new HashMap<>();


                params.put("emailid",emailid.getText().toString());
                params.put("passwordid",passwordid.getText().toString());

                editor.putString("emailid",emailid.getText().toString());
                editor.putString("passwordid",passwordid.getText().toString());
                editor.apply();

                return params;
            }



        };

        requestQueue.add(request);

    }


    void toast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

package com.apgklobal.webpromo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText name, mobile, username, password;
    Button login, register;
    String url = "http://searchkero.com/aa/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        //to hide the statusbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        /*getSupportActionBar().setTitle("LOGIN IN WEBPROMO");*/
        register.setText("Create New Account");
        login.setText("LOGIN");
        name.setVisibility(View.GONE);
        mobile.setVisibility(View.GONE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this,
                            RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty()
                        || password.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this,
                            "Fill the Username and Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    StringRequest sr = new StringRequest(1, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(LoginActivity.this,
                                            response, Toast.LENGTH_SHORT).show();
                                    username.setText("");
                                    password.setText("");
                                    if (response.equals("success"))
                                    {
                                        Intent intent = new Intent(LoginActivity.this,
                                                HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("usernameid", username.getText().toString());
                            map.put("passwordid", password.getText().toString());
                            return map;
                        }
                    };
                    RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);
                    rq.add(sr);

                }

            }
        });
    }
}

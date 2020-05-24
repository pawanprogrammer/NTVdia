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

public class RegisterActivity extends AppCompatActivity {
    EditText name, mobile, username, password;
    Button login, register;
    String url = "http://searchkero.com/aa/register.php";

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
        register.setText("Register");
        getSupportActionBar().hide();
        //getSupportActionBar().setTitle("Create New Account");
//to hide the statusbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        login.setVisibility(View.GONE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty() ||
                        name.getText().toString().isEmpty() ||
                        mobile.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this,
                            "Please fill the form", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest sr = new StringRequest(1, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(RegisterActivity.this,
                                            response, Toast.LENGTH_SHORT).show();
                                    name.setText("");
                                    username.setText("");
                                    mobile.setText("");
                                    password.setText("");
                                    Intent intent = new Intent(RegisterActivity.this,
                                            HomeActivity.class);
                                    startActivity(intent);
                                    MyPreferences myPreferences=
                                            new MyPreferences(RegisterActivity.this);
                                    myPreferences.second();
                                    finish();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("nameid", name.getText().toString());
                            map.put("mobileid", mobile.getText().toString());
                            map.put("usernameid", username.getText().toString());
                            map.put("passwordid", password.getText().toString());
                            return map;
                        }
                    };
                    RequestQueue rq = Volley.newRequestQueue(RegisterActivity.this);
                    rq.add(sr);


                }
            }
        });
    }
}

package com.kalkinemedia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
private EditText et_email, et_name,et_mobile, et_pin;
private Button btn_register;
private ProgressBar progressBar;
String register_url = "https://kalkinemedia.com/mobileapp/km/newregister.php";
private TextView terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //to hide the actionbar
        getSupportActionBar().hide();
        //typecast convert ui components to java objects
        btn_register = findViewById(R.id.btn_register);
        et_email=findViewById(R.id.et_email);
        et_mobile=findViewById(R.id.et_mobile);
        et_name=findViewById(R.id.et_name);
        et_pin=findViewById(R.id.et_pin);
        /*progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);*/

        terms = findViewById(R.id.login_terms);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent terms = new Intent(RegisterActivity.this, MainActivity.class);
                terms.putExtra("termid", "https://kalkinemedia.com/terms-and-conditions/");
                startActivity(terms);
                finish();
            }
        });

        //to click on the register button
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                //to check the validation for internet connection
                if (isNetworkAvailable(true))
                {
                    //to check the validation the form can't be empty
                    if (et_mobile.getText().toString().trim().isEmpty()
                            ||
                            et_email.getText().toString().trim().isEmpty()
                            ||
                            et_name.getText().toString().trim().isEmpty()
                            ||
                            et_pin.getText().toString().isEmpty())
                    {
                        Toast.makeText(RegisterActivity.this,
                                "Please fill the complete form", Toast.LENGTH_SHORT).show();
                  //      progressBar.setVisibility(View.GONE);
                    }
                    else {
                            registeruser();
                    }
                }
                else {
                    AlertDialog.Builder ab=new AlertDialog.Builder(RegisterActivity.this);
                    ab.setTitle("No Internet Connect");
                    ab.setMessage("Please connect with Internet");
                    ab.setCancelable(false);
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ab.create();
                    ab.show();
                }
            }
        });
    }

    private void registeruser() {
        StringRequest sr = new StringRequest(1, register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    progressBar.setVisibility(View.GONE);
                        et_mobile.setText("");
                        et_email.setText("");
                        et_name.setText("");
                        et_pin.setText("");

                        Toast.makeText(RegisterActivity.this,
                                response,
                                Toast.LENGTH_SHORT).show();

                        Intent share = new Intent(RegisterActivity.this, MainActivity.class);
                        share.putExtra("termid", "https://kalkinemedia.com/");
                        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(share);
                        MyPreferences myPreferences = new MyPreferences(RegisterActivity.this);
                        myPreferences.second();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nameid", et_name.getText().toString().trim());
                map.put("emailid", et_email.getText().toString().trim());
                map.put("mobileid", et_mobile.getText().toString().trim());
                map.put("postcodeid", et_pin.getText().toString().trim());
                return map;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(RegisterActivity.this);
        rq.add(sr);
    }

    private boolean isNetworkAvailable(boolean b) {
        /*ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    Log.w("INTERNET:",String.valueOf(i));
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.w("INTERNET:", "connected!");
                        return true;
                    }
                }
            }
        }*/
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("kalkine", 0);
        if (sp.getBoolean("b", false) == true) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra("termid", "https://kalkinemedia.com/");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}

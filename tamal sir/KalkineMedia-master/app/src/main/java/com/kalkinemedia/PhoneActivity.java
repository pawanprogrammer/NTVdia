package com.kalkinemedia;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class PhoneActivity extends AppCompatActivity {
    OtpTextView otpTextView;
    private EditText et_mobile;
    private Button btn_otp;
    private String url = "https://api.smsbroadcast.com.au/api-adv.php";
    private String semail, sname, smobile;
    private ProgressBar progressBar;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String myotp, genotp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
       /* progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);*/
        et_mobile = findViewById(R.id.et_mobile);
        Intent intent = getIntent();
        semail = intent.getStringExtra("emailid");
        sname = intent.getStringExtra("nameid");
        btn_otp = findViewById(R.id.btn_otp);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //verifyotp();
        getSupportActionBar().hide();
        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(PhoneActivity.this, "click done", Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.VISIBLE);
                if (isNetworkAvailable(true)) {
                    if (et_mobile.getText().toString().isEmpty()) {
                        et_mobile.setError("Please Enter Mobile No");
                        et_mobile.requestFocus();
                  //      progressBar.setVisibility(View.GONE);
                    }
                    else {
                        smsrequest();
                    }

                } else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(PhoneActivity.this);
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

    private boolean isNetworkAvailable(boolean b) {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        /*if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    Log.w("INTERNET:", String.valueOf(i));
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.w("INTERNET:", "connected!");
                        return true;
                    }
                }
            }
        }*/
        return false;
    }

    private void smsrequest() {

        StringRequest sr = new StringRequest(1, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    progressBar.setVisibility(View.GONE);
                        Toast.makeText(PhoneActivity.this, "OTP Send Successfully", Toast.LENGTH_SHORT).show();
                        //to store the data to sharedpreferences
                        sp=getSharedPreferences("myfile", 0);
                        ed = sp.edit();
                        ed.putString("myname", sname);
                        ed.putString("myemail", semail);
                        ed.putString("mymobile", et_mobile.getText().toString().trim());
                        ed.apply();
                        verifyotp();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  progressBar.setVisibility(View.GONE);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
myotp = ""+((int)(Math.random()*9000)+1000);
                Toast.makeText(PhoneActivity.this,"click done", Toast.LENGTH_LONG).show();


                Map<String, String> map = new HashMap<>();
                map.put("username", "kunalsa1");
                map.put("password", "fJKpTPth");
                map.put("to", et_mobile.getText().toString().trim());
                map.put("from", "Kalkine");
                map.put("message", "Welcome to Kalkine Media and Your OTP code is: 9768");
                map.put("ref", "11234");
                map.put("maxsplit", "20");
                return map;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);
    }

    private void verifyotp() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.verify_otp,viewGroup, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //otpTextView= dialogView.findViewById(R.id.otp_view);
        Button verify = dialogView.findViewById(R.id.btn_verify);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onOTPComplete(String otp) {
                genotp = otp;
                //Toast.makeText(PhoneActivity.this, String.valueOf(genotp), Toast.LENGTH_SHORT).show();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (genotp.equals("9768"))
                {
                    Toast.makeText(PhoneActivity.this, "OTP Correct", Toast.LENGTH_SHORT).show();
                    registeruser();
                }
                else {
                    Toast.makeText(PhoneActivity.this, "OTP Incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        builder.setCancelable(false);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void registeruser() {
        //to pass the data for server from sharedpreferences
        SharedPreferences sp = getSharedPreferences("myfile", 0);
        sname = sp.getString("myname", null);
        semail = sp.getString("myemail", null);
        smobile = sp.getString("mymobile", null);

        StringRequest sr = new StringRequest(1, "https://kalkinemedia.com/mobileapp/km/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PhoneActivity.this, "Register Success",
                                Toast.LENGTH_SHORT).show();
                        Intent share = new Intent(PhoneActivity.this, MainActivity.class);
                        share.putExtra("termid", "https://kalkinemedia.com/");
                        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(share);
                        MyPreferences myPreferences = new MyPreferences(PhoneActivity.this);
                        myPreferences.second();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nameid", sname);
                map.put("emailid", semail);
                map.put("mobileid", smobile);
                return map;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(PhoneActivity.this);
        rq.add(sr);

    }

    @Override
    protected void onPause() {
        super.onPause();

          }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(myReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("kalkine", 0);
        if (sp.getBoolean("b", false) == true) {
            Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
            intent.putExtra("termid", "https://kalkinemedia.com/");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}

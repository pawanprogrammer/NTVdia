package com.kalkinemedia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class MainActivity extends AppCompatActivity implements UpdateHelper.Onupdatechecklistener{
    //declare java objects for xml widgets
    private WebView webView;
    private ProgressBar progressBar;
    private FloatingActionButton fab_share;
    private String testurl = "https://kalkinemedia.com/mobileapp/km/register.php";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sp = getSharedPreferences("myfile", 0);
        /*Toast.makeText(MainActivity.this,
                sp.getString("myname", null)+
                sp.getString("myemail", null)+
                sp.getString("mymobile", null),
                Toast.LENGTH_SHORT).sh();*/

        //to get the auto update from server side
        UpdateHelper.with(this).onupdatecheck(this)
                .check();
        //to hide the Actionbar
        getSupportActionBar().hide();
        sp=getSharedPreferences("myfile", 0);
        fab_share=findViewById(R.id.fab_share);
        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, "Download kalkine Media App to stay updated with latest ASX news and updates"+"\n"+
                        "https://play.google.com/store/apps/details?id=com.kalkinemedia");
                share.setType("text/plain");
                startActivity(Intent.createChooser(share, "ShareApp Via:"));

            }
        });
        //typecast or initialization convert xml widgets into java objects
        webView = findViewById(R.id.webview);
        //progressBar = findViewById(R.id.progress);
        if (isNetworkAvailable(true)) {
            //to load website
            loadwebsite();
        } else {

            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
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
    private boolean isNetworkAvailable(boolean b) {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        /*if (connectivity != null) {
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

    private void loadwebsite() {
        Intent intent = getIntent();
        String myurl = intent.getStringExtra("termid");
        if (myurl.equals("https://kalkinemedia.com/terms-and-conditions/")) {
            //to open all links inside application
            webView.setWebViewClient(new Myclient());
            //to enable the javascripts function inside app
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
            //to load the website url to webview
            webView.loadUrl("https://kalkinemedia.com/terms-and-conditions/");
        } else {
            //to open all links inside application
            webView.setWebViewClient(new Myclient());
            //to enable the javascripts function inside app
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
            //to load the website url to webview
            webView.loadUrl("https://kalkinemedia.com/");
        }
    }

    //to press on the back button
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            //to go back all internal links inside the website
            webView.goBack();
        } else {
            //to show the alertdialog for close the Application
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setTitle("Exit Application");
            ab.setMessage("Do you want to close the App?");
            ab.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           // MainActivity.super.onBackPressed();
                        finish();
                        }
                    });
            ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            ab.setCancelable(false);
            ab.create();
            ab.show();
        }
    }

    @Override
    public void onupdatechecklistener(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Version Available");
        builder.setMessage("Please update your Application to new version");
        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent= new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.kalkinemedia"));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();

    }

    //to open all website links inside the application
    private class Myclient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            //to load the website the start progressbar visible
            //progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            //after loading the website just close the application
            //progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        StringRequest sr = new StringRequest(1, testurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(PhoneActivity.this, "Data Save Successfully", Toast.LENGTH_SHORT).show();
//                        MyPreferences myPreferences = new MyPreferences(MainActivity.this);
//                        myPreferences.second();
//                        /*Intent intent = new Intent(MainActivity.this,
//                                MainActivity.class);
//                        intent.putExtra("termid", "https://kalkinemedia.com/");
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);*/
//                        finish();
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
                sp=getSharedPreferences("myfile", 0);
                    map.put("nameid", sp.getString("myname", null));
                    map.put("emailid", sp.getString("myemail", null));
                    map.put("mobileid", sp.getString("mymboile", null));

                    return map;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);

    }


}

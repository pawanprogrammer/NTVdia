package com.kalkinemedia.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kalkinemedia.Dashboard.DashboardActivity;
import com.kalkinemedia.LoginActivity;
import com.kalkinemedia.MainActivity;
import com.kalkinemedia.MyPreferences;
import com.kalkinemedia.PhoneActivity;
import com.kalkinemedia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.kalkinemedia.SplashActivity;

import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class   ProfileActivity extends AppCompatActivity implements MaterialIntroListener {
    private EditText name, email, mobile;
    private Button register;
    String semail, sname, smobile, source;
    String reg_url ="https://kalkinemedia.com/mobileapp/km/newregister.php";
    private SignInButton glogin;
    private GoogleSignInOptions gso;
    private GoogleSignInClient signInClient;
    private int code = 1;
    private FirebaseAuth mAuth;
    private LinearLayout l1,l2;
    //for facebook login
    private LoginButton facebook_button;
    private CallbackManager mCallbackManager;
    private int facebook_code = 2;
    private static final String EMAIL = "email";
    //for firebase authentication
    FirebaseUser user;
    Animation animation;
    ProgressDialog progressDialog;
    View v1, v2;
    TextView tv_or;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        l1= findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        tv_or=findViewById(R.id.tv_or);
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
        sp = getSharedPreferences("userdata", 0);
        ed = sp.edit();
        progressDialog = new ProgressDialog(this);
        //to hide the actionbar and statusbar
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.et_enter);
        //to initialize the firebaseauth context
        mAuth = FirebaseAuth.getInstance();
        //intialize the views
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_phone);
        register = findViewById(R.id.register);
        glogin = findViewById(R.id.glogin);
        facebook_button = findViewById(R.id.facebook_button);

        showIntro(name, "first click", "Enter Your Name, Email, Mobile No. For Free Stocks Report", FocusGravity.RIGHT);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);
        /*name.startAnimation(animation);
        email.startAnimation(animation);
        mobile.startAnimation(animation);
        register.startAnimation(animation);*/
        //to get the mobile no from verify phone activity
        /*Intent intent = getIntent();
        smobile = intent.getStringExtra("mobile");*/

        //if user want to put manually form data
        source = "form";
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (source.equals("form"))
                {
                    register.setClickable(false);
                    sname = name.getText().toString().trim();
                    semail = email.getText().toString().trim();
                    smobile = mobile.getText().toString().trim();
                    if (sname.isEmpty() ||
                            semail.isEmpty())
                    {
                        Toast.makeText(ProfileActivity.this,
                                "Please fill the form", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (sname.matches("^[a-zA-Z ]*$") && semail.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+
                                "[a-zA-Z0-9_+&*-]+)*@" +
                                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                                "A-Z]{2,7}$"))
                        {
                            showprogress();
                            register();
                        }
                        else {
                            Toast.makeText(ProfileActivity.this, "Name contains alphabets only and email id should be correct", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                else if (source.equals("google"))
                {
                    showprogress();
                    smobile = mobile.getText().toString().trim();
                    register();
                }
                else if (source.equals("facebook"))
                {
                    showprogress();
                    smobile = mobile.getText().toString().trim();
                    register();
                }
                            }
        });


        /*For Google plus login*/
        glogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to check the internet connection is working or not
                if (isNetworkAvailable(true))
                {
                    // Configure Google Sign In
                    gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(
                                    /*"77526378799-rudcgolc7eqjq3kbafoa05smmg8v2csi.apps.googleusercontent.com"*/

                                    getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    //to pass gso to googlesign in client
                    signInClient = GoogleSignIn.getClient(ProfileActivity.this, gso);
                    //to click on the glogin for login using google account
                    //progressBar.setVisibility(View.VISIBLE);
                    Intent signInIntent = signInClient.getSignInIntent();
                    startActivityForResult(signInIntent, code);
                }
                else {
                    AlertDialog.Builder ab=new AlertDialog.Builder(ProfileActivity.this);
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

        //to login usign facebook
        facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookSdk.sdkInitialize(getApplicationContext());
                mCallbackManager = CallbackManager.Factory.create();
                facebook_button.setReadPermissions(Arrays.asList(EMAIL, "public_profile"));
                //checkLoginStatus();
                /*LoginManager.getInstance()
                        .logInWithReadPermissions(ProfileActivity.this,
                        Collections.singletonList("email"));*/
                facebook_button.registerCallback(mCallbackManager,
                        new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken =
                                loginResult.getAccessToken();
                        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        //to get the response
                                        try {
                                            sname = object.getString("name");
                                            semail = object.getString("email");
                                            source = "facebook";
                                            //to register the user
                                            Toast.makeText(ProfileActivity.this,
                                                    "Please submit your Mobile No. for free reports", Toast.LENGTH_SHORT).show();
                                            //to register the user after the mobile no enter
                                            name.setVisibility(View.INVISIBLE);
                                            email.setVisibility(View.INVISIBLE);
                                            facebook_button.setVisibility(View.GONE);
                                            glogin.setVisibility(View.GONE);
                                            l1.setVisibility(View.GONE);
                                            l2.setVisibility(View.GONE);
                                            v1.setVisibility(View.GONE);
                                            v2.setVisibility(View.GONE);
                                            tv_or.setVisibility(View.GONE);
                                            register.setText("SUBMIT");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name,email");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                    }
                    @Override
                    public void onCancel() {

                    }
                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });
    }

    @Override
    public void onUserClicked(String s) {
        new MaterialIntroView.Builder(ProfileActivity.this)
                .enableDotAnimation(true)
                .setFocusGravity(FocusGravity.RIGHT)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText("Click here to create new account")
                .setTarget(email.getRootView())
                .setListener(this)
                .setUsageId(s) //THIS SHOULD BE UNIQUE ID
                .show();
    }
    public void showIntro(View view, String id, String text, FocusGravity focusGravity){
        new MaterialIntroView.Builder(ProfileActivity.this)
                .enableDotAnimation(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText(text)
                .setTarget(view)
                .setListener(this)
                .setUsageId(id)//THIS SHOULD BE UNIQUE ID
                .show();

    }


    public void showprogress()
    {
        progressDialog.setTitle("Please wait.....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    private boolean isNetworkAvailable(boolean b) {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        }
        return false;
    }

    private void register() {
        //to pass the data for server from sharedpreferences
        /*SharedPreferences sp = getSharedPreferences("myfile", 0);
        sname = sp.getString("myname", null);
        semail = sp.getString("myemail", null);
        smobile = sp.getString("mymobile", null);*/
        StringRequest sr = new StringRequest(1,
                reg_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Successfully Registered",
                                Toast.LENGTH_SHORT).show();
                        Intent share = new Intent(ProfileActivity.this, DashboardActivity.class);
                        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(share);
                        finish();
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        MyPreferences myPreferences = new MyPreferences(ProfileActivity.this);
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
                ed.putString("myname", sname);
                ed.putString("myemail", semail);
                ed.putString("mymobile", smobile);
                ed.apply();
                Map<String, String> map = new HashMap<>();
                map.put("nameid", sname);
                map.put("emailid", semail);
                map.put("mobileid", smobile);
                map.put("sourceid", source);
                return map;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(ProfileActivity.this);
        rq.add(sr);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == code) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //progressBar.setVisibility(View.VISIBLE);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Toast.makeText(this, "Google SignIn Falied", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        showprogress();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //progressBar.setVisibility(View.VISIBLE);
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            sname = user.getDisplayName();
                            semail = user.getEmail();
                            source = "google";
                            Toast.makeText(ProfileActivity.this,
                                    "Please submit your Mobile No. for free reports", Toast.LENGTH_SHORT).show();
                            //to register the user after the mobile no enter
                            name.setVisibility(View.INVISIBLE);
                            email.setVisibility(View.INVISIBLE);
                            facebook_button.setVisibility(View.GONE);
                            glogin.setVisibility(View.GONE);
                            l1.setVisibility(View.GONE);
                            l2.setVisibility(View.GONE);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.GONE);
                            tv_or.setVisibility(View.GONE);
                            register.setText("SUBMIT");
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ProfileActivity.this, "Authentication Failed try again", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        progressDialog.dismiss();
                    }
                });
    }

}

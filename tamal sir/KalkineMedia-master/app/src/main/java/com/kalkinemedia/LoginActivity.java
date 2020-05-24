package com.kalkinemedia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private SignInButton glogin;
    private GoogleSignInOptions gso;
    private GoogleSignInClient signInClient;
    private int code = 1;
    private FirebaseAuth mAuth;
    private Animation animation;
    private TextView terms;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        //to hide the Actionbar
        getSupportActionBar().hide();
        //to initialize the views
        initviews();
    }

    private void initviews() {

        terms = findViewById(R.id.login_terms);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent terms = new Intent(LoginActivity.this, MainActivity.class);
                terms.putExtra("termid", "https://kalkinemedia.com/terms-and-conditions/");
                startActivity(terms);
            }
        });

        /*progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);*/

        glogin = findViewById(R.id.glogin);
        //to initialize the firebaseauth context
        mAuth = FirebaseAuth.getInstance();

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
                    signInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
                    //to click on the glogin for login using google account
                    //progressBar.setVisibility(View.VISIBLE);
                    Intent signInIntent = signInClient.getSignInIntent();
                    startActivityForResult(signInIntent, code);
                }
                else {
                    AlertDialog.Builder ab=new AlertDialog.Builder(LoginActivity.this);
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
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //progressBar.setVisibility(View.VISIBLE);
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication Failed try again", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    //to redirect to MainActivity if user already has login inside the application
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            //to jump the MainActivity after login
            //progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(LoginActivity.this, PhoneActivity.class);
Toast.makeText(this,
                    currentUser.getEmail() + "\n" +
                            currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();

            intent.putExtra("nameid", currentUser.getDisplayName());
            intent.putExtra("emailid", currentUser.getEmail());
            startActivity(intent);
            finish();
        }
    }

}

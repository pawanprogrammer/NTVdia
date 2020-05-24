package com.example.railwayhvac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button signUp;
    RequestQueue requestQueue;
    TextView logIn;
    EditText userid,numberid,emailid,passwordid,companynameid;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actvity);

        sp=getSharedPreferences("file1",0);
        editor=sp.edit();

        userid=findViewById(R.id.username);
        numberid=findViewById(R.id.number);
        emailid=findViewById(R.id.email);
        passwordid=findViewById(R.id.password);
        companynameid=findViewById(R.id.companyname);
        logIn=findViewById(R.id.log_in);

        requestQueue= Volley.newRequestQueue(this);

        signUp=findViewById(R.id.signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailid.getText().toString().equals("")
                || userid.getText().toString().equals("") ||passwordid.getText().toString().equals("")
                ||numberid.getText().toString().equals("") || companynameid.getText().toString().equals("")){


                    userid.setError("empty filed");
                    numberid.setError("empty filed");
                    emailid.setError("empty filed");
                    passwordid.setError("empty filed");
                    companynameid.setError("empty filed");

                }

                else {
                    register();
                }


            }

        });


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }



    void register(){

        String url="http://searchkero.com/tamal/register.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                toast("completed");
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));

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

                params.put("nameid",userid.getText().toString());
                params.put("mobileid",numberid.getText().toString());
                params.put("emailid",emailid.getText().toString());
                params.put("passwordid",passwordid.getText().toString());
                params.put("companynameid",companynameid.getText().toString());

                editor.putString("nameid",userid.getText().toString());
                editor.putString("mobileid",numberid.getText().toString());
                editor.putString("emailid",emailid.getText().toString());
                editor.putString("passwordid",passwordid.getText().toString());
                editor.putString("companynameid",companynameid.getText().toString());
                editor.apply();




                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };


        requestQueue.add(request);



    }
//

    void toast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

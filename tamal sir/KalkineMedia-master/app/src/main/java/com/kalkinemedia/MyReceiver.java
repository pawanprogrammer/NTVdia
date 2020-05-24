package com.kalkinemedia;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo = "";
    Context context;
    String sname,semail, smobile;

    @Override
    public void onReceive(final Context context, Intent intent) {
        //retrieves the general action to be performed and display on log
        Log.i(TAG, "Intent Received: " + intent.getAction());
        this.context = context;
        if (intent.getAction() == SMS_RECEIVED) {
            //retrieves a map of extended data from the intent
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null) {
                //creating PDU(Protocol Data Unit) object which is a protocol for transferring message
                Object[] mypdu = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for (int i = 0; i < mypdu.length; i++) {
                    //for build versions >= API Level 23
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = dataBundle.getString("format");
                        //From PDU we get all object and SmsMessage Object using following line of code
                        //message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);
                    } else {
                        //<API level 23
                        //message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }

                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                }
                if (msg.contains("136756")) {
                    SharedPreferences sp = context.getSharedPreferences("myfile", 0);
                    sname = sp.getString("myname", null);
                    semail = sp.getString("myemail", null);
                    smobile = sp.getString("mymobile", null);

                    StringRequest sr = new StringRequest(1, "https://kalkinemedia.com/mobileapp/km/register.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(context, "Register Success",
                                            Toast.LENGTH_SHORT).show();
                                    Intent share = new Intent(context, MainActivity.class);
                                    share.putExtra("termid", "https://kalkinemedia.com/");
                                    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(share);
                                    MyPreferences myPreferences = new MyPreferences(context);
                                    myPreferences.second();
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
                            SharedPreferences sp = context.getSharedPreferences("myfile", 0);
                            map.put("nameid", sname);
                            map.put("emailid", semail);
                            map.put("mobileid", smobile);

                            return map;
                        }
                    };

                    RequestQueue rq = Volley.newRequestQueue(context);
                    rq.add(sr);

                    //Toast.makeText(context, "Message: " +msg +"\nNumber: " +phoneNo, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}


package com.kalkinemedia;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyClient {
    private static final String OTP_URL = "https://api.smsbroadcast.com.au/";
    private static final String REGISTER_URL = "https://kalkinemedia.com/mobileapp/km/";
    private static MyClient myClient;
    private Retrofit retrofit;

    private MyClient(int a) {
        if (a ==1)
        {
            retrofit = new Retrofit.Builder().baseUrl(OTP_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        else {
            retrofit = new Retrofit.Builder().baseUrl(REGISTER_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
      }

    public static synchronized MyClient getInstance() {
        if (myClient == null) {
            myClient = new MyClient(1);
        }
        return myClient;
    }

    public MyApi getMyApi(int a) {
        return retrofit.create(MyApi.class);
    }


}

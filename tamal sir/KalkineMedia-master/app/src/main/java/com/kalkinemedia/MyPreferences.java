package com.kalkinemedia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MyPreferences {
    Context c;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    public MyPreferences(Context c) {
        this.c = c;
        sp=c.getSharedPreferences("kalkine", 0);
        ed=sp.edit();
    }


    private boolean islogin() {
        return sp.getBoolean("b", false);
    }

    public void second()
    {
        ed.putBoolean("b", true);
        ed.apply();
    }
}

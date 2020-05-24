package com.apgklobal.webpromo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;

public class MyPreferences {

    Context c;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    public MyPreferences(Context c) {
        this.c = c;
        sp = c.getSharedPreferences("spfile", 0);
        ed = sp.edit();
    }

    public void first()
    {
        if (!this.login())
        {
            Intent intent = new Intent(c, LoginActivity.class);
            //to open fresh activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //to delete intent backtrack
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            c.startActivity(intent);
        }
    }

    private boolean login() {
        return sp.getBoolean("b", false);
    }

    public void second()
    {
        ed.putBoolean("b", true);
        ed.apply();
    }


}

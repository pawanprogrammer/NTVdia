package com.kalkinemedia;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.kalkinemedia.Notification.MyNotificationOpenedHandler;
import com.kalkinemedia.Notification.MyNotificationReceivedHandler;
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //MyNotificationOpenedHandler : This will be called when a notification is tapped on.
        //MyNotificationReceivedHandler : This will be called when a notification is received while your app is running.
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler(this))
                .setNotificationReceivedHandler( new MyNotificationReceivedHandler() )
                .init();
    }

        /*final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        Map<String, Object> myvalues = new HashMap<>();
        myvalues.put(UpdateHelper.key_update, false);
        myvalues.put(UpdateHelper.key_version, "iv_digital.0");
        myvalues.put(UpdateHelper.key_url, "https://play.google.com/store/apps/details?id=com.kalkinemedia");

        remoteConfig.setDefaults(myvalues);
        remoteConfig.fetch(5)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            remoteConfig.activateFetched();
                        }
                    }
                });


    }*/
}

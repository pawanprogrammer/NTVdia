package com.kalkinemedia.Service;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kalkinemedia.App;

import java.util.Map;

public class FCMService extends FirebaseMessagingService {

    private final String TAG = getClass().getName();
    //this is for service
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Toast.makeText(App.getContext(), "onMessageReceived", Toast.LENGTH_LONG).show();
        Log.e(TAG,"onMessageReceived ");
        if (remoteMessage != null && remoteMessage.getNotification() != null) {
            final RemoteMessage.Notification notification = remoteMessage.getNotification();
            Map<String, String> data = remoteMessage.getData();
            final String imageUrl = data.get("image");
            String desc = data.get("desc");
            String link = data.get("link");
            String type = data.get("type");
            Log.e(TAG, "DESC: " + desc);
            Log.e(TAG, "URL: " + link);


            /*Log.e(TAG, "IMAGE: " + imageUrl);
            if (type != null) {
                if (type.equalsIgnoreCase("notification")) {
                    if (desc != null) {
                        if (imageUrl != null) {

                            new DownloadBitmap().execute(notification.getTitle(), notification.getBody(), desc, imageUrl, this);

                        } else {

                            NotificationUtil.createHeadsUpNotification(this, notification.getTitle(), notification.getBody(), desc, null);

                        }
                    } else {
                        if (imageUrl != null) {

                            new DownloadBitmap().execute(notification.getTitle(), notification.getBody(), null, imageUrl, this);


                        } else {
                            NotificationUtil.createHeadsUpNotification(this, notification.getTitle(), notification.getBody(), null, null);

                        }

                    }
                    Log.e(TAG, "ON MESSAGE RECEIVED: " + remoteMessage.getFrom());

                } else if (type.equalsIgnoreCase("coupon")) {
                    NotificationUtil.createHeadsUpCouponNotification(this, notification.getTitle(), notification.getBody(), link);

                }
            }*/
        }

        super.onMessageReceived(remoteMessage);
    }
    @Override
    public void onMessageSent(String s)
    {
        Log.e(TAG,"ON MESSAGE SENT CALLED: "+s);
        super.onMessageSent(s);
    }
}
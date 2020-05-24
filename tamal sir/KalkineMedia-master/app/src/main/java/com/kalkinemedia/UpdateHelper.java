package com.kalkinemedia;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {
    public static String key_update = "isUpdate";
    public static String key_version = "version";
    public static String key_url = "update_url";

    public interface Onupdatechecklistener{
        void onupdatechecklistener(String url);
    }

    public static Builder with(Context context)
    {
        return new Builder(context);
    }
    private Onupdatechecklistener onupdatechecklistener;
    private Context context;

    public UpdateHelper(Context context, Onupdatechecklistener onupdatechecklistener)
    {
        this.onupdatechecklistener=onupdatechecklistener;
        this.context=context;
    }

    public void check()
    {
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(key_update))
        {
            String currentversion = remoteConfig.getString(key_update);
            String appversion = getAppversion(context);
            String updateurl = remoteConfig.getString(key_url);

            if (!TextUtils.equals(currentversion, appversion) &&
            onupdatechecklistener !=null)
            {
                onupdatechecklistener.onupdatechecklistener(updateurl);
            }
        }
    }

    private String getAppversion(Context context) {
        String result = "";
        try{
            result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static class Builder{

        private Context context;
        private Onupdatechecklistener onupdatechecklistener;

        public Builder(Context context)
        {
            this.context=context;
        }
        public Builder onupdatecheck(Onupdatechecklistener onupdatechecklistener)
        {
            this.onupdatechecklistener =onupdatechecklistener;
            return this;
        }

        public UpdateHelper build()
        {
            return new UpdateHelper(context, onupdatechecklistener);
        }

        public UpdateHelper check()
        {
            UpdateHelper updateHelper = build();
            updateHelper.check();
            return updateHelper;
        }
    }


}

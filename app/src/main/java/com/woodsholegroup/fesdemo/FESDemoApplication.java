package com.woodsholegroup.fesdemo;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.woodsholegroup.fesdemo.api.Intents;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * {@link Application} for the FES Demo application
 */
public class FESDemoApplication extends Application {

    private static final String TAG = FESDemoApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        registerFES();
    }

    private void registerFES() {
        Log.d(TAG, "Registering to FES...");

        Intent registerIntent = new Intent();
        registerIntent.setAction(Intents.ACTION_REGISTER);
        registerIntent.putExtra(Intents.EXTRA_PACKAGE_NAME, getPackageName());
        sendBroadcast(registerIntent);
    }
}

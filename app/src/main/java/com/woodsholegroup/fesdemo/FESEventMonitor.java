package com.woodsholegroup.fesdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.woodsholegroup.fesdemo.api.Broadcasts;
import com.woodsholegroup.fesdemo.api.model.IridiumStatus;

import java.util.ArrayList;

/**
 * To avoid memory leaks, clear the callback reference with {@link #clear()} any time
 * a lifecycle destroy event occurs (e.g. {@link Activity#onDestroy()}
 */
public class FESEventMonitor {
    private static final String TAG = "FESEventMonitor";
    private static final String PACKAGE_THORIUM = "com.clsa";
    private static final String PACKAGE_MARLIN_PRO = "com.clsa_marlin";

    public interface FESMonitoringListener {
        void onErrorReceived(int errorCode, String errorMessage);

        void onFileStatusChanged(@NonNull String filename, @NonNull String status, @NonNull String uuid);

        void onIridiumStatusReceived(@NonNull IridiumStatus intent);

        void onFileReceived(@NonNull String filename, @NonNull String uuid);

        void onFileScanFinished(@NonNull ArrayList<String> processedFiles);

        void onFileScanStarted();
    }

    private FESMonitoringListener callback;

    /**
     * <b>To be called in onResume</b>
     * <p>
     * Registers the FES BroadcastReceiver to receive the updates sent by the service and adds the
     * <var>callback</var> to handle these events.
     *
     * @param context  {@link Context}
     * @param callback The FESMonitoringListener to handle the FES events
     */
    public void register(@NonNull Context context, FESMonitoringListener callback) {
        this.callback = callback;
        registerReceiver(context);
    }

    /**
     * <b>To be called in onPause</b>
     * <p>
     * Unregisters the previously registered FES BroadcastReceiver
     *
     * @param context {@link Context}
     */
    public void unregister(@NonNull Context context) {
        context.unregisterReceiver(fesReceiver);
    }

    /**
     * <b>To be called in onDestroy</b>
     * <p>
     * To be called any time a lifecycle destroy event occurs (e.g. {@link Activity#onDestroy()}
     * to avoid memory leaks
     */
    public void clear() {
        callback = null;
    }

    /**
     * Registers the FES BroadcastReceiver to be run in the main activity thread
     *
     * @param context {@link Context}
     */
    private void registerReceiver(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Broadcasts.ACTION_SCAN_STARTED);
        intentFilter.addAction(Broadcasts.ACTION_SCAN_FINISHED);
        intentFilter.addAction(Broadcasts.ACTION_STATUS_CHANGED);
        intentFilter.addAction(Broadcasts.ACTION_FILE_RECEIVED);
        intentFilter.addAction(Broadcasts.ACTION_IRIDIUM_STATUS);
        intentFilter.addAction(Broadcasts.ACTION_ERROR);

        context.registerReceiver(fesReceiver, intentFilter);
    }

    private BroadcastReceiver fesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            Log.d(TAG, "onReceive: " + action);
            //Toast.makeText(MainActivity.this, action, Toast.LENGTH_LONG).show();

            switch (action) {
                case Broadcasts.ACTION_SCAN_STARTED:
                    onFileScanStarted(intent);
                    break;
                case Broadcasts.ACTION_SCAN_FINISHED:
                    onFileScanFinished(intent);
                    break;
                case Broadcasts.ACTION_FILE_RECEIVED:
                    onFileReceived(intent);
                    break;
                case Broadcasts.ACTION_IRIDIUM_STATUS:
                    onIridiumStatusReceived(intent);
                    break;
                case Broadcasts.ACTION_STATUS_CHANGED:
                    onFileStatusChanged(intent);
                    break;
                case Broadcasts.ACTION_ERROR:
                    onErrorReceived(intent);
                    break;
            }
        }
    };

    private void onErrorReceived(Intent intent) {
        int errorCode = intent.getIntExtra(Broadcasts.EXTRA_ERROR_CODE, -1);
        String errorMessage = intent.getStringExtra(Broadcasts.EXTRA_MESSAGE);

        if (callback != null) {
            callback.onErrorReceived(errorCode, errorMessage);
        }
    }

    private void onFileStatusChanged(Intent intent) {
        String filename = intent.getStringExtra(Broadcasts.EXTRA_FILENAME);
        String uuid = intent.getStringExtra(Broadcasts.EXTRA_UUID);
        String changedStatus = intent.getStringExtra(Broadcasts.EXTRA_STATUS);

        if (callback != null) {
            callback.onFileStatusChanged(filename, changedStatus, uuid);
        }
    }

    private void onIridiumStatusReceived(Intent intent) {
        IridiumStatus iridiumStatus = IridiumStatus.fromIntent(intent);
        if (callback != null) {
            callback.onIridiumStatusReceived(iridiumStatus);
        }
    }

    private void onFileReceived(Intent intent) {
        String uuid = intent.getStringExtra(Broadcasts.EXTRA_UUID);
        String filename = intent.getStringExtra(Broadcasts.EXTRA_FILENAME);

        if (callback != null) {
            callback.onFileReceived(filename, uuid);
        }
    }

    private void onFileScanFinished(Intent intent) {
        ArrayList<String> filenames = new ArrayList<>();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(Broadcasts.EXTRA_FILENAME)) {
            filenames = extras.getStringArrayList(Broadcasts.EXTRA_FILENAME);
        }
        if (callback != null) {
            callback.onFileScanFinished(filenames);
        }
    }

    private void onFileScanStarted(Intent intent) {
        if (callback != null) {
            callback.onFileScanStarted();
        }
    }

    /**
     * @param context {@link Context}
     * @return {@link #PACKAGE_MARLIN_PRO} if Marlin Pro is installed on the device,
     * {@link #PACKAGE_THORIUM} is Thorium is installed. Null otherwise
     */
    @Nullable
    public static String getFESInstalledApp(@NonNull Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (isPackageInstalled(packageManager, PACKAGE_THORIUM)) {
            // Thorium is installed
            return PACKAGE_THORIUM;
        } else if (isPackageInstalled(packageManager, PACKAGE_MARLIN_PRO)) {
            // Marlin Pro is installed
            return PACKAGE_MARLIN_PRO;
        }
        return null;
    }

    private static boolean isPackageInstalled(PackageManager packageManager, @NonNull String packageName) {
        try {
            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }
}

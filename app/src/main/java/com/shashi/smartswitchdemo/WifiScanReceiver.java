package com.shashi.smartswitchdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class WifiScanReceiver extends BroadcastReceiver {
    private final ScanResultCompletionListener scanResultCompletionListener;

    public WifiScanReceiver(ScanResultCompletionListener scanResultCompletionListener) {
        this.scanResultCompletionListener = scanResultCompletionListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
        if (success)
            scanResultCompletionListener.onScanSuccess();
        else
            scanResultCompletionListener.onScanFailed();
    }
}

package com.shashi.smartswitchdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiConnectionReceiver extends BroadcastReceiver {
    private static final String TAG = "WifiConnectionReceiver";
    private final WifiManager wifiManager;
    private final ScanResult scanResult;
    private final String password;
    private final WifiConnectionListener wifiConnectionListener;

    public WifiConnectionReceiver(WifiManager wifiManager, ScanResult scanResult, String password, WifiConnectionListener wifiConnectionListener) {
        this.wifiManager = wifiManager;
        this.scanResult = scanResult;
        this.password = password;
        this.wifiConnectionListener = wifiConnectionListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            Utils utils = new Utils();
            if (utils.isConnectedTo(wifiManager, scanResult.SSID)){
                Log.d(TAG, "onReceive: Connection success with : " + scanResult.SSID);
                wifiConnectionListener.onWifiConnected(scanResult, password);
            }
        }
    }
}

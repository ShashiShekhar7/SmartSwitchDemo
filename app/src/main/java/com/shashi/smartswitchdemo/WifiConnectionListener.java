package com.shashi.smartswitchdemo;

import android.net.wifi.ScanResult;

public interface WifiConnectionListener {
    void onWifiConnected(ScanResult scanResult, String password);
}

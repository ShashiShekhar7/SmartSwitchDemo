package com.shashi.smartswitchdemo;

import android.net.wifi.ScanResult;

public interface WifiClickListener {
    void onWifiClick(ScanResult scanResult);
}

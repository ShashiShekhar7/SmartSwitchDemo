package com.shashi.smartswitchdemo;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConnectWiFiFragment extends Fragment implements WifiClickListener, WifiConnectionListener {
    private static final String TAG = "ConnectWifiFragment";
    private WifiManager wifiManager;
    private WifiScanReceiver wifiScanReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connect_wi_fi, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_scan_wifi);

        AdapterScanWifi adapterScanWifi = new AdapterScanWifi(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterScanWifi);
        
        wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        
        wifiScanReceiver = new WifiScanReceiver(new ScanResultCompletionListener() {
            @Override
            public void onScanSuccess() {
                Log.d(TAG, "onScanSuccess: " + wifiManager.getScanResults());
                adapterScanWifi.setResults(wifiManager.getScanResults());
            }

            @Override
            public void onScanFailed() {
                adapterScanWifi.setResults(wifiManager.getScanResults());
                Log.d(TAG, "onScanFailed: " + wifiManager.getScanResults());
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getContext().registerReceiver(wifiScanReceiver, intentFilter);

        Handler handler = new Handler(Looper.getMainLooper());

        new Runnable() {
            @Override
            public void run() {
                wifiManager.startScan();
                handler.postDelayed(this, 30000);
            }
        }.run();

        return view;
    }

    @Override
    public void onWifiClick(ScanResult scanResult) {
        DialogPasswordFragment dialogPasswordFragment = new DialogPasswordFragment(scanResult, wifiManager, this);
        dialogPasswordFragment.show(getChildFragmentManager(), "DialogPasswordFragment");
    }

    @Override
    public void onWifiConnected(ScanResult scanResult, String password) {

    }
}
package com.shashi.smartswitchdemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;


public class Utils {
    private static final String TAG="Utils";


    public boolean isConnectedTo(WifiManager wifiManager, String SSID) {
        WifiInfo info = wifiManager.getConnectionInfo();
        String ssid = info.getSSID();

        Log.d(TAG, "isConnectedTo: SSID: " + SSID);
        Log.d(TAG, "isConnectedTo: ssid: " + ssid);

        return ssid.equals("\"" + SSID + "\"");
    }

    public void bindToNetwork(Context context) {

        Log.d(TAG, "bindToNetwork: binding process to network");

        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new NetworkRequest.Builder();
            //set the transport type do WIFI
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
            connectivityManager.requestNetwork(builder.build(), new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                        connectivityManager.bindProcessToNetwork(null);
                        connectivityManager.bindProcessToNetwork(network);
                    } else {
                        //This method was deprecated in API level 23
                        ConnectivityManager.setProcessDefaultNetwork(null);
                        ConnectivityManager.setProcessDefaultNetwork(network);
                    }

                    connectivityManager.unregisterNetworkCallback(this);
                }
            });
        }
    }
}

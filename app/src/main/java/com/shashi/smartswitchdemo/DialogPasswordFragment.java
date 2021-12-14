package com.shashi.smartswitchdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class DialogPasswordFragment extends DialogFragment implements InterfaceOnConnectingQ{

    private static final String TAG = "DialogPasswordFragment";

    private final ScanResult scanResult;
    private final WifiManager wifiManager;
    private WifiConnectionReceiver wifiConnectionReceiver;
    private final WifiConnectionListener wifiConnectionListener;

    public DialogPasswordFragment(ScanResult scanResult, WifiManager wifiManager, WifiConnectionListener wifiConnectionListener) {
        this.scanResult = scanResult;
        this.wifiManager = wifiManager;
        this.wifiConnectionListener = wifiConnectionListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: ");

        View view  = getLayoutInflater().inflate(R.layout.fragment_dialog_password, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Enter Password")
                .setNegativeButton("cancel", (dialogInterface, i) -> {})
                .setPositiveButton("connect", null);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnShowListener(dialogInterface -> {
            Button btnConnect = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            btnConnect.setOnClickListener(view1 -> {
                TextInputEditText etPassword = view.findViewById(R.id.et_pass);
                String strPassword = etPassword.getText().toString().trim();
                if (strPassword.isEmpty())
                    etPassword.setError("Cannot be empty");
                else if (strPassword.length() < 8)
                    etPassword.setError("Cannot be less than 8 character");
                else {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    WifiConnection wifiConnection = new WifiConnection();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        wifiConnection.connectWifiQ(connectivityManager, scanResult, strPassword ,this);
                    else
                        wifiConnection.connectToWiFi(wifiManager, scanResult, strPassword);
                }
            });
        });

        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        wifiConnectionReceiver = new WifiConnectionReceiver(wifiManager,scanResult, password, wifiConnectionListener);
        IntentFilter intentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        getContext().registerReceiver(wifiConnectionReceiver, intentFilter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        getContext().unregisterReceiver(wifiConnectionReceiver);
    }


    @Override
    public void onUnavailable() {
        Log.d(TAG, "onUnavailable:");
    }

    @Override
    public void onAvailable() {
        Log.d(TAG, "onAvailable:");
    }
}

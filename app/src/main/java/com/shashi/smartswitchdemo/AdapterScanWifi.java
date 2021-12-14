package com.shashi.smartswitchdemo;

import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterScanWifi extends RecyclerView.Adapter<AdapterScanWifi.MyViewHolder> {

    private List<ScanResult> results = new ArrayList<>();

    private final WifiClickListener wifiClickListener;

    public AdapterScanWifi(WifiClickListener wifiClickListener) {
        this.wifiClickListener = wifiClickListener;
    }

    public void setResults(List<ScanResult> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_scan_wifi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvSsid.setText(results.get(position).SSID);
        holder.itemView.setOnClickListener(view -> {
            wifiClickListener.onWifiClick(results.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSsid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSsid = itemView.findViewById(R.id.tv_ssid);
        }
    }
}

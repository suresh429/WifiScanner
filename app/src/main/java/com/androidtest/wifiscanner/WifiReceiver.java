package com.androidtest.wifiscanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WifiReceiver extends BroadcastReceiver {
    String SSID;
    WifiManager wifiManager;
    StringBuilder sb;
    ListView wifiDeviceList;
    Spinner spinner;
    FloatingActionButton floatingActionButton;
    public WifiReceiver(WifiManager wifiManager, ListView wifiDeviceList, Spinner spinner, FloatingActionButton floatingActionButton) {
        this.wifiManager = wifiManager;
        this.wifiDeviceList = wifiDeviceList;
        this.spinner = spinner;
        this.floatingActionButton = floatingActionButton;
    }
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            sb = new StringBuilder();
            List<ScanResult> wifiList = wifiManager.getScanResults();
            ArrayList<String> deviceList = new ArrayList<>();
            for (ScanResult scanResult : wifiList) {
                sb.append("\n").append(scanResult.SSID).append(" - ").append(scanResult.capabilities);
               // deviceList.add(scanResult.SSID + " - " + scanResult.capabilities   );

                if (!scanResult.SSID.equalsIgnoreCase("")){
                    deviceList.add(scanResult.SSID );
                }

            }
           // Toast.makeText(context, sb, Toast.LENGTH_SHORT).show();
            ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, deviceList.toArray());
            wifiDeviceList.setAdapter(arrayAdapter);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Log.d(TAG, "onItemSelected: "+deviceList.get(position));
                    SSID = deviceList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: "+SSID);
                    Toast.makeText(context, SSID, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

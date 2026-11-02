package com.example.testproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WifiStateActivity extends AppCompatActivity {

    private TextView statusText;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiReceiver;
    private boolean receiverRegistered = false;

    private void showWifiStatus() {
        int state = wifiManager.getWifiState();

        switch (state) {
            case WifiManager.WIFI_STATE_ENABLED:
                statusText.setText("Wi-Fi: ON");
                break;

            case WifiManager.WIFI_STATE_DISABLED:
                statusText.setText("Wi-Fi: OFF");
                break;

            case WifiManager.WIFI_STATE_ENABLING:
                statusText.setText("Wi-Fi: TURNING ON");
                break;

            case WifiManager.WIFI_STATE_DISABLING:
                statusText.setText("Wi-Fi: TURNING OFF");
                break;

            default:
                statusText.setText("Wi-Fi: UNKNOWN");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_state);

        statusText = findViewById(R.id.statusText);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        showWifiStatus();

        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                    showWifiStatus();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(wifiReceiver, filter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(wifiReceiver, filter);
        }

        receiverRegistered = true;
        showWifiStatus();
    }

    @Override
    protected void onStop() {
        if (receiverRegistered) {
            unregisterReceiver(wifiReceiver);
            receiverRegistered = false;
        }
        super.onStop();
    }
}

package com.example.testproject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BatteryActivity extends AppCompatActivity {
    private Button btnStatus;
    private BatteryReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_status);

        btnStatus = findViewById(R.id.btnStatus);

        btnStatus.setOnClickListener(v -> {
            if (receiver != null) {
                try {
                    unregisterReceiver(receiver);
                } catch (Exception ignored) {}
            }
            receiver = new BatteryReceiver();
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
            } else {
                registerReceiver(receiver, filter);
            }
        });
    }

    @Override
    protected void onStop() {
        if (receiver != null) {
            try {
                unregisterReceiver(receiver);
                receiver = null;
            } catch (Exception ignored) {}
        }
        super.onStop();
    }
}

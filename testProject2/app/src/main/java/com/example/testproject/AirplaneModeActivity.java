package com.example.testproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AirplaneModeActivity extends AppCompatActivity {

    private TextView statusText;
    private BroadcastReceiver airplaneReceiver;
    private boolean receiverRegistered = false;

    private void showCurrentStatus() {
        int value = Settings.Global.getInt(
                getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0);

        statusText.setText(value == 1
                ? "Airplane Mode: ON"
                : "Airplane Mode: OFF");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane_mode);

        statusText = findViewById(R.id.statusText);
        showCurrentStatus();

        airplaneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showCurrentStatus();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(
                Intent.ACTION_AIRPLANE_MODE_CHANGED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(airplaneReceiver, filter,
                    Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(airplaneReceiver, filter);
        }

        receiverRegistered = true;
        showCurrentStatus();
    }

    @Override
    protected void onStop() {
        if (receiverRegistered) {
            unregisterReceiver(airplaneReceiver);
            receiverRegistered = false;
        }
        super.onStop();
    }
}

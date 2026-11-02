package com.example.testproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (level != -1 && scale != -1) {
            int batteryPercent = (level * 100) / scale;
            Toast.makeText(context, "Current Battery: " + batteryPercent + "%", Toast.LENGTH_LONG).show();
        }
    }
}

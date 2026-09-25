package com.example.testproject;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvAppName = findViewById(R.id.tvAppName);
        TextView tvVersion = findViewById(R.id.tvVersion);
        TextView tvDesc = findViewById(R.id.tvDesc);

        // Get app name (from app settings)
        String appName = getApplicationInfo().loadLabel(getPackageManager()).toString();
        tvAppName.setText(appName);

        // Get version name safely
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            String versionName = info.versionName;
            tvVersion.setText("Version " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tvVersion.setText("Version N/A");
        }

        tvDesc.setText("This app helps you manage your daily tasks.");
    }
}

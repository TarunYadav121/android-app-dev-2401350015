package com.example.testproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_open_calculator).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_second_activity).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Second_activity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_checkbox).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CheckboxActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_listview).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_login_page).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login_page.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_select_color).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Select_color.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_hello_page).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HelloActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_google).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
        });

        findViewById(R.id.btn_open_caller).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CallerActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_location).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_open_file_manager).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FileManagerActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_open_grid_layout).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GridLayout.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_open_frame_layout).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FrameLayout.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_open_constraint_layout).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Constraint.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_open_relative_layout).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Relative.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_open_test_your_skill).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestYourSkillActivity.class);
            startActivity(intent);
        });

    }
}

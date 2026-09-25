package com.example.testproject;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
        findViewById(R.id.btn_open_splash).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_open_fragments).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FragmentContainerActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_open_subject).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubjectsActivity.class);
            startActivity(intent);
        });

        Button btnOpenPdf = findViewById(R.id.btnopenpdf);
        btnOpenPdf.setOnClickListener(v -> {
            openPdfFromAssets("CppNotes.pdf");
        });

        Button btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Add Note", Toast.LENGTH_SHORT).show();
        });

    }

    private void openPdfFromAssets(String fileName) {
        try {
            // 1. Copy PDF from assets to internal storage (cache)
            InputStream inputStream = getAssets().open(fileName);
            File outFile = new File(getCacheDir(), fileName);
            OutputStream outputStream = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();

            // 2. Open PDF using FileProvider and Intent
            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", outFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No PDF viewer app installed on device", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to open PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.testproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Relative extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);

        findViewById(R.id.btn_back_to_main).setOnClickListener(v -> finish());
    }
}

package com.example.testproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Select_color extends AppCompatActivity {
    private TextView tvColorName;
    private Button btnRed;
    private Button btnGreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.select_color);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvColorName = findViewById(R.id.tvColorName);
        btnRed = findViewById(R.id.btnRed);
        btnGreen = findViewById(R.id.btnGreen);

        // RED button
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getWindow().getDecorView()
                        .setBackgroundColor(Color.RED);

                tvColorName.setText("Red");
                tvColorName.setTextColor(Color.WHITE);
            }
        });

        // GREEN button
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getWindow().getDecorView()
                        .setBackgroundColor(Color.GREEN);

                tvColorName.setText("Green");
                tvColorName.setTextColor(Color.WHITE);
            }
        });
    }
}

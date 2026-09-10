package com.example.testproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TestYourSkillActivity extends AppCompatActivity {

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_your_skill);

        TextView tvCounter = findViewById(R.id.tv_counter);
        Button btnPlus = findViewById(R.id.btn_plus);
        Button btnMinus = findViewById(R.id.btn_minus);

        btnPlus.setOnClickListener(v -> {
            counter++;
            tvCounter.setText(String.valueOf(counter));
        });

        btnMinus.setOnClickListener(v -> {
            if (counter > 0) {
                counter--;
                tvCounter.setText(String.valueOf(counter));
            }
        });
    }
}

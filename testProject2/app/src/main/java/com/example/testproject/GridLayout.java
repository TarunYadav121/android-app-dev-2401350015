package com.example.testproject;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GridLayout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);

        // Find buttons and set click listeners
        for (int i = 1; i <= 9; i++) {
            int resId = getResources().getIdentifier("btn" + i, "id", getPackageName());
            Button button = findViewById(resId);
            if (button != null) {
                final int number = i;
                button.setOnClickListener(v -> Toast.makeText(GridLayout.this, "Button " + number + " clicked",
                        Toast.LENGTH_SHORT).show());
            }
        }
    }
}
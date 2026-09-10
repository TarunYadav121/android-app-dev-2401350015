package com.example.testproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etLocation = findViewById(R.id.et_location);

        findViewById(R.id.btn_view_location).setOnClickListener(v -> {
            String location = etLocation.getText().toString().trim();
            if (!location.isEmpty()) {
                // Uri to open Google Maps with a search query
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                // Check if there's an app to handle the intent
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // If Google Maps is not installed, try without the package constraint
                    Intent genericMapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    startActivity(genericMapIntent);
                }
            } else {
                Toast.makeText(LocationActivity.this, "Please enter a location", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

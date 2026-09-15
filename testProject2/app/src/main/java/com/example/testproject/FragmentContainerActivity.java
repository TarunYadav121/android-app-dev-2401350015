package com.example.testproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentContainerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        // Load FragmentOne by default
        if (savedInstanceState == null) {
            loadFragment(new FragmentOne());
        }

        findViewById(R.id.btn_fragment_one).setOnClickListener(v -> loadFragment(new FragmentOne()));
        findViewById(R.id.btn_fragment_two).setOnClickListener(v -> loadFragment(new FragmentTwo()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}

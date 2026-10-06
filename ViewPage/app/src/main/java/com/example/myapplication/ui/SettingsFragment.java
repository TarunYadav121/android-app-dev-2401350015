package com.example.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.utils.PreferenceManager;

public class SettingsFragment extends Fragment {

    private EditText etStudentName, etRollNumber;
    private Button btnSaveProfile, btnCampusGuidelines, btnShareApp;
    private RadioGroup rgLanguage;
    private RadioButton rbEnglish, rbHindi;

    private PreferenceManager prefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        etStudentName = view.findViewById(R.id.etStudentName);
        etRollNumber = view.findViewById(R.id.etRollNumber);
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
        btnCampusGuidelines = view.findViewById(R.id.btnCampusGuidelines);
        btnShareApp = view.findViewById(R.id.btnShareApp);
        rgLanguage = view.findViewById(R.id.rgLanguage);
        rbEnglish = view.findViewById(R.id.rbEnglish);
        rbHindi = view.findViewById(R.id.rbHindi);

        prefManager = new PreferenceManager(requireContext());

        loadProfile();

        btnSaveProfile.setOnClickListener(v -> saveProfile());
        btnCampusGuidelines.setOnClickListener(v -> openCampusGuidelines());
        btnShareApp.setOnClickListener(v -> shareApp());

        setupLanguageGroup();

        return view;
    }

    private void loadProfile() {
        etStudentName.setText(prefManager.getStudentName());
        etRollNumber.setText(prefManager.getRollNumber());

        String lang = prefManager.getLanguage();
        if ("hi".equalsIgnoreCase(lang)) {
            rbHindi.setChecked(true);
        } else {
            rbEnglish.setChecked(true);
        }
    }

    private void saveProfile() {
        String name = etStudentName.getText().toString().trim();
        String roll = etRollNumber.getText().toString().trim();

        if (name.isEmpty()) {
            etStudentName.setError(getString(R.string.error_required));
            return;
        }

        prefManager.saveProfile(name, roll);
        Toast.makeText(requireContext(), R.string.profile_saved, Toast.LENGTH_SHORT).show();
    }

    private void setupLanguageGroup() {
        rgLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedLang = checkedId == R.id.rbHindi ? "hi" : "en";
            if (!selectedLang.equals(prefManager.getLanguage())) {
                prefManager.saveLanguage(selectedLang);
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(selectedLang));
            }
        });
    }

    private void openCampusGuidelines() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(intent);
    }

    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Use Campus Lost & Found App to report and recover lost items in our campus!");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_app)));
    }
}
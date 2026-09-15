package com.example.testproject;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SubjectsActivity extends AppCompatActivity {

    ArrayList<String> subjects = new ArrayList<>();
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        CheckBox cbSoftware = findViewById(R.id.cb_software_dev);
        CheckBox cbMobile = findViewById(R.id.cb_mobile_dev);
        CheckBox cbToc = findViewById(R.id.cb_toc);
        CheckBox cbOs = findViewById(R.id.cb_os);

        tvResult = findViewById(R.id.tv_selection_result);

        cbSoftware.setOnCheckedChangeListener((button, checked) ->
                changeSubject((CheckBox) button, checked));

        cbMobile.setOnCheckedChangeListener((button, checked) ->
                changeSubject((CheckBox) button, checked));

        cbToc.setOnCheckedChangeListener((button, checked) ->
                changeSubject((CheckBox) button, checked));

        cbOs.setOnCheckedChangeListener((button, checked) ->
                changeSubject((CheckBox) button, checked));
    }

    void changeSubject(CheckBox box, boolean checked) {

        String subject = box.getText().toString();

        if (checked)
            subjects.add(subject);
        else
            subjects.remove(subject);

        if (subjects.isEmpty())
            tvResult.setText("Selected Subjects: None");
        else
            tvResult.setText("Selected Subjects: " + String.join(", ", subjects));
    }
}
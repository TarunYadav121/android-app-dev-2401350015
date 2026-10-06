package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "campus_lost_found_prefs";
    private static final String KEY_STUDENT_NAME = "student_name";
    private static final String KEY_ROLL_NUMBER = "roll_number";
    private static final String KEY_LANGUAGE = "language";

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveProfile(String name, String rollNo) {
        prefs.edit()
                .putString(KEY_STUDENT_NAME, name)
                .putString(KEY_ROLL_NUMBER, rollNo)
                .apply();
    }

    public String getStudentName() {
        return prefs.getString(KEY_STUDENT_NAME, "Student");
    }

    public String getRollNumber() {
        return prefs.getString(KEY_ROLL_NUMBER, "CS2026-001");
    }

    public void saveLanguage(String lang) {
        prefs.edit().putString(KEY_LANGUAGE, lang).apply();
    }

    public String getLanguage() {
        return prefs.getString(KEY_LANGUAGE, "en");
    }
}
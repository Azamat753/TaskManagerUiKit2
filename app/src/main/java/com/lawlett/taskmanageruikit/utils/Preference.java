package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    public static volatile Preference instance;
    private SharedPreferences preferences;

    public Preference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }
    public static Preference getInstance(Context context) {
        if (instance == null) new Preference(context);
        return instance;
    }

    public boolean isShown() {
        return preferences.getBoolean("isShown", false);
    }

    public void saveShown() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }
}

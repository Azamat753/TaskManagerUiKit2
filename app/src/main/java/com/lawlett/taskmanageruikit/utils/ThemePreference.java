package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePreference {

    public static volatile ThemePreference instance;
    private SharedPreferences preferences;

    public ThemePreference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("light", 0);
    }

    public static ThemePreference getInstance(Context context) {
        if (instance == null) new ThemePreference(context);
        return instance;
    }

    public boolean isTheme() {
        return preferences.getBoolean("night_mode", false);
    }

    public void saveThemeTrue() {
        preferences.edit().putBoolean("night_mode", true).apply();
    }

    public void saveThemeFalse() {
        preferences.edit().putBoolean("night_mode", false).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }
}

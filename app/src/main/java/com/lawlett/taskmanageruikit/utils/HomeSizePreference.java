package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class HomeSizePreference {

    public static volatile HomeSizePreference instance;
    private SharedPreferences preferences;

    public HomeSizePreference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("homeSize", Context.MODE_PRIVATE);
    }

    public static HomeSizePreference getInstance(Context context) {
        if (instance == null) new HomeSizePreference(context);
        return instance;
    }

    public int getHomeSize() {
        return preferences.getInt("home", 0);
    }

    public void saveHomeSize(int size) {
        preferences.edit().putInt("home",size).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }
}

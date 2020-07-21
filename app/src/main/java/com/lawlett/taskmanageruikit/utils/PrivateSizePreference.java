package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrivateSizePreference {

    public static volatile PrivateSizePreference instance;
    private SharedPreferences preferences;

    public PrivateSizePreference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("privateSize", Context.MODE_PRIVATE);
    }

    public static PrivateSizePreference getInstance(Context context) {
        if (instance == null) new PrivateSizePreference(context);
        return instance;
    }

    public int getPrivateSize() {
        return preferences.getInt("private", 0);
    }

    public void savePrivateSize(int size) {
        preferences.edit().putInt("private",size).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }
}

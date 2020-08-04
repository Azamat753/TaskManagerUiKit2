package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TimingSizePreference {

    public static volatile TimingSizePreference instance;
    private SharedPreferences preferences;

    public TimingSizePreference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("personalSize", Context.MODE_PRIVATE);
    }

    public static TimingSizePreference getInstance(Context context) {
        if (instance == null) new TimingSizePreference(context);
        return instance;
    }

    public int getPersonalSize() {
        return preferences.getInt("personal", 0);
    }

    public void savePersonalSize(int size) {
        preferences.edit().putInt("personal",size).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }
}

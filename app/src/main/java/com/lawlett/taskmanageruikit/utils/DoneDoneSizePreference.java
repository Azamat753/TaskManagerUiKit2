package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DoneDoneSizePreference {

    public static volatile DoneDoneSizePreference instance;
    private SharedPreferences preferences;

    public DoneDoneSizePreference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("doneSize", Context.MODE_PRIVATE);
    }

    public static DoneDoneSizePreference getInstance(Context context) {
        if (instance == null) new DoneDoneSizePreference(context);
        return instance;
    }

    public int getDataSize() {
        return preferences.getInt("done", 0);
    }

    public void saveDataSize(int size) {
        preferences.edit().putInt("done", size).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }
}

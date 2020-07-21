package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class WorkSizePreference {

    public static volatile WorkSizePreference instance;
    private SharedPreferences preferences;

    public WorkSizePreference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("workSize", Context.MODE_PRIVATE);
    }

    public static WorkSizePreference getInstance(Context context) {
        if (instance == null) new WorkSizePreference(context);
        return instance;
    }

    public int getWorkSize() {
        return preferences.getInt("work", 0);
    }

    public void saveWorkSize(int size) {
        preferences.edit().putInt("work",size).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }
}

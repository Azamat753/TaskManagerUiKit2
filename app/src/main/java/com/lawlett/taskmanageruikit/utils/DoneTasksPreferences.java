package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DoneTasksPreferences {

    public static volatile DoneTasksPreferences instance;
    private SharedPreferences preferences;

    public DoneTasksPreferences(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("size", Context.MODE_PRIVATE);
    }

    public static DoneTasksPreferences getInstance(Context context) {
        if (instance == null) new DoneTasksPreferences(context);
        return instance;
    }

    public int getDataSize() {
        return preferences.getInt("all", 0);
    }

    public void saveDataSize(int size) {
        preferences.edit().putInt("all", size).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }

}

package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MeetSizePreference {

    public static volatile MeetSizePreference instance;
    private SharedPreferences preferences;

    public MeetSizePreference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("meetSize", Context.MODE_PRIVATE);
    }

    public static MeetSizePreference getInstance(Context context) {
        if (instance == null) new MeetSizePreference(context);
        return instance;
    }

    public int getMeetSize() {
        return preferences.getInt("meet", 0);
    }

    public void saveMeetSize(int size) {
        preferences.edit().putInt("meet",size).apply();
    }

    public void clearSettings() {
        preferences.edit().clear().apply();
    }
}

package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PersonalSizePreference {

    public static volatile PersonalSizePreference instance;
    private SharedPreferences preferences;

    public PersonalSizePreference(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("personalSize", Context.MODE_PRIVATE);
    }

    public static PersonalSizePreference getInstance(Context context) {
        if (instance == null) new PersonalSizePreference(context);
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

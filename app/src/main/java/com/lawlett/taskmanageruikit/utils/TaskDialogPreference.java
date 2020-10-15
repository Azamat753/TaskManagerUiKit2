package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TaskDialogPreference {
    private static SharedPreferences sharedPreferences;
    private final static String PREF_NAME = "pref name";
    private final static String TITLE = "title";
    private final static String IMAGE = "image";

    public static  void init(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveTitle(String title){
        sharedPreferences.edit().putString(TITLE, title).apply();
    }

    public static String getTitle(){
        return sharedPreferences.getString(TITLE, "");
    }
    public static void saveImage(int image){
        sharedPreferences.edit().putInt(IMAGE, image).apply();
    }
    public static int getImage(){
        return sharedPreferences.getInt(IMAGE, 0);
    }
    public static void remove(){
        sharedPreferences.edit().remove(TITLE).apply();
    }
}

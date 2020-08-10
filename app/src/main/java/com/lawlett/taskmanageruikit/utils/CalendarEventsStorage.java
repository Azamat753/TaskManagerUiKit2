package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class CalendarEventsStorage {

    public static void save(String tasks, Context context) {
        Gson gson = new Gson();
        String jsonTasks = gson.toJson(tasks);
        SharedPreferences sp = context.getSharedPreferences("my_calendar_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tasks", jsonTasks);
        editor.commit();
    }

    public static String read(Context context) {
        SharedPreferences sp = context.getSharedPreferences("my_calendar_sp", Context.MODE_PRIVATE);
        String jsonTasks = sp.getString("tasks", "");

        if (jsonTasks == "") {
            return "";
        }
        Gson gson = new Gson();
        String tasks = gson.fromJson(jsonTasks, new TypeToken<String>() {
        }.getType());
        return tasks;
    }

    public static void deleteAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences("my_calendar_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}

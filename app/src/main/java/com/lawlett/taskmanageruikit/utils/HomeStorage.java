package com.lawlett.taskmanageruikit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;

import java.util.ArrayList;

public class HomeStorage {

    public static void save(ArrayList<TaskModel> tasks, Context context) {
        Gson gson = new Gson();
        String jsonTasks = gson.toJson(tasks);
        SharedPreferences sp = context.getSharedPreferences("my_home_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tasks", jsonTasks);
        editor.commit();
    }

    public static ArrayList<TaskModel> read(Context context) {
        SharedPreferences sp = context.getSharedPreferences("my_home_sp", Context.MODE_PRIVATE);
        String jsonTasks = sp.getString("tasks", "");

        if (jsonTasks == "") {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        ArrayList<TaskModel> tasks = gson.fromJson(jsonTasks, new TypeToken<ArrayList<TaskModel>>() {
        }.getType());
        return tasks;
    }

    public static void deleteAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences("my_home_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.clear();
        editor.apply();
    }
}

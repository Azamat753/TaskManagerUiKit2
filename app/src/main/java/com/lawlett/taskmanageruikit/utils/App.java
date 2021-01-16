package com.lawlett.taskmanageruikit.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.lawlett.taskmanageruikit.room.AppDataBase;

public class App extends Application {
    public static App instance;
    private static AppDataBase dataBase;
    public static final String CHANNEL_ID = "exampleChannel";
    private static SharedPreferences sharedPreferences;
    private static  SharedPreferences.Editor sharedPreferencesEditor;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
             .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        createNotificationChannel();

    }



    public static AppDataBase getDataBase() {
        return dataBase;
    }


    public static App getInstance() {

        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static SharedPreferences.Editor getApplicationPreferenceEditor(){
        if (sharedPreferencesEditor == null){
            sharedPreferencesEditor = sharedPreferences.edit();
        }
        return sharedPreferencesEditor;
    }

    public static SharedPreferences getApplicationPreference(){
        if (sharedPreferences == null)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(instance);
        return sharedPreferences;
    }


    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Example Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}

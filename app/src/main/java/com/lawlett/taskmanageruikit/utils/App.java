package com.lawlett.taskmanageruikit.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.lawlett.taskmanageruikit.room.AppDataBase;

public class App extends Application {
    public static App instance;
    private static AppDataBase dataBase;
    public static final String CHANNEL_ID = "exampleChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
             .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        createNotificationChannel();


    }
    public static AppDataBase getDataBase() {
        return dataBase;
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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

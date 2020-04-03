package com.lawlett.taskmanageruikit.quick.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;

@Database(entities = {QuickModel.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();


}

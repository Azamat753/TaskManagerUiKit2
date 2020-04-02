package com.lawlett.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lawlett.data.model.QuickModel;

@Database(entities = {QuickModel.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();


}

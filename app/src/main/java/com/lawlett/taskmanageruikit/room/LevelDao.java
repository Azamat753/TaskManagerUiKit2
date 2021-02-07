package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lawlett.taskmanageruikit.achievement.models.LevelModel;

import java.util.List;

@Dao
public interface LevelDao {

    @Query("SELECT * FROM level")
    LiveData<List<LevelModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LevelModel levelModel);

    @Query("DELETE FROM level")
    void deleteAll();

}

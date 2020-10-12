package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;

import java.util.List;

@Dao
public interface DoneDao {
    @Query("SELECT*FROM doneModel")
    List<DoneModel> getAll();

    @Query("SELECT*FROM doneModel")
    LiveData<List<DoneModel>> getAllLive();

    @Insert
    void insert(DoneModel doneModel);

    @Delete
    void delete(DoneModel doneModel);

    @Delete
    void deleteAll(List<DoneModel> doneModel);

    @Update
    void update(DoneModel doneModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWord(List<DoneModel> doneModel);
}

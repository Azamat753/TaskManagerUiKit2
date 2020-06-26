package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;

import java.util.List;

@Dao
public interface DoneTaskDao {

    @Query("SELECT*FROM donemodel")
    List<DoneModel> getAll();

    @Query("SELECT*FROM donemodel")
    LiveData<List<DoneModel>> getAllLive();

    @Insert
    void insert(DoneModel donemodel);

    @Delete
    void delete(DoneModel donemodel);

    @Delete
    void deleteAll(List<DoneModel> donemodel);

    @Update
    void update(DoneModel donemodel);

}

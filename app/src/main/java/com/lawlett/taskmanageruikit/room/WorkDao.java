package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.model.WorkModel;

import java.util.List;

@Dao
public interface WorkDao {

    @Query("SELECT*FROM workModel")
    List<WorkModel> getAll();

    @Query("SELECT*FROM workModel")
    LiveData<List<WorkModel>> getAllLive();

    @Insert
    void insert(WorkModel workModel);

    @Delete
    void delete(WorkModel workModel);

    @Delete
    void deleteAll(List<WorkModel> workModel);

    @Update
    void update(WorkModel workModel);

}

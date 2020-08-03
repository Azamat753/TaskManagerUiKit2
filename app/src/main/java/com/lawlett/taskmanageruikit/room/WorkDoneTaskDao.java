package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.done_model.WorkDoneModel;

import java.util.List;

@Dao
public interface WorkDoneTaskDao {

    @Query("SELECT*FROM workDoneModel")
    List<WorkDoneModel> getAll();

    @Query("SELECT*FROM workDoneModel")
    LiveData<List<WorkDoneModel>> getAllLive();

    @Insert
    void insert(WorkDoneModel workDoneModel);

    @Delete
    void delete(WorkDoneModel workDoneModel);

    @Delete
    void deleteAll(List<WorkDoneModel> workDoneModel);

    @Update
    void update(WorkDoneModel workDoneModel);

}

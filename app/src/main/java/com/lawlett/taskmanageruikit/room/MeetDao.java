package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;

import java.util.List;

@Dao
public interface MeetDao {

    @Query("SELECT*FROM meetModel")
    List<MeetModel> getAll();

    @Query("SELECT*FROM meetModel")
    LiveData<List<MeetModel>> getAllLive();

    @Insert
    void insert(MeetModel meetModel);

    @Delete
    void delete(MeetModel meetModel);

    @Delete
    void deleteAll(List<MeetModel> meetModel);

    @Update
    void update(MeetModel meetModel);

}

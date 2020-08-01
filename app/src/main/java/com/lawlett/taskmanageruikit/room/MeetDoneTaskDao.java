package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.model.MeetDoneModel;

import java.util.List;

@Dao
public interface MeetDoneTaskDao {

    @Query("SELECT*FROM meetDoneModel")
    List<MeetDoneModel> getAll();

    @Query("SELECT*FROM meetDoneModel")
    LiveData<List<MeetDoneModel>> getAllLive();

    @Insert
    void insert(MeetDoneModel meetDoneModel);

    @Delete
    void delete(MeetDoneModel meetDoneModel);

    @Delete
    void deleteAll(List<MeetDoneModel> meetDoneModel);

    @Update
    void update(MeetDoneModel meetDoneModel);

}

package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.done_model.PrivateDoneModel;

import java.util.List;

@Dao
public interface PrivateDoneTaskDao {

    @Query("SELECT*FROM homeDoneModel")
    List<PrivateDoneModel> getAll();

    @Query("SELECT*FROM homeDoneModel")
    LiveData<List<PrivateDoneModel>> getAllLive();

    @Insert
    void insert(PrivateDoneModel homeDoneModel);

    @Delete
    void delete(PrivateDoneModel homeDoneModel);

    @Delete
    void deleteAll(List<PrivateDoneModel> homeDoneModel);

    @Update
    void update(PrivateDoneModel homeDoneModel);

}

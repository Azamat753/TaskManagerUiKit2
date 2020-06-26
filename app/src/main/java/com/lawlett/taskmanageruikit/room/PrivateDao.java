package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.model.PrivateModel;

import java.util.List;

@Dao
public interface PrivateDao {

    @Query("SELECT*FROM privateModel")
    List<PrivateModel> getAll();

    @Query("SELECT*FROM privateModel")
    LiveData<List<PrivateModel>> getAllLive();

    @Insert
    void insert(PrivateModel privateModel);

    @Delete
    void delete(PrivateModel privateModel);

    @Delete
    void deleteAll(List<PrivateModel> privateModel);

    @Update
    void update(PrivateModel privateModel);

}

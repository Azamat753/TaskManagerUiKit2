package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.HomeDoneModel;

import java.util.List;

@Dao
public interface HomeDoneTaskDao {

    @Query("SELECT*FROM homeDoneModel")
    List<HomeDoneModel> getAll();

    @Query("SELECT*FROM homeDoneModel")
    LiveData<List<HomeDoneModel>> getAllLive();

    @Insert
    void insert(HomeDoneModel homeDoneModel);

    @Delete
    void delete(HomeDoneModel homeDoneModel);

    @Delete
    void deleteAll(List<HomeDoneModel> homeDoneModel);

    @Update
    void update(HomeDoneModel homeDoneModel);

}

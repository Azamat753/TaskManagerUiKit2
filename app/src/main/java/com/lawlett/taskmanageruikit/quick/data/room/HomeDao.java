package com.lawlett.taskmanageruikit.quick.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.model.HomeModel;

import java.util.List;

@Dao
public interface HomeDao {

    @Query("SELECT*FROM homeModel")
    List<HomeModel> getAll();

    @Query("SELECT*FROM homeModel")
    LiveData<List<HomeModel>> getAllLive();

    @Insert
    void insert(HomeModel homeModel);

    @Delete
    void delete(HomeModel homeModel);

    @Delete
    void deleteAll(List<HomeModel> homeModel);

    @Update
    void update(HomeModel homeModel);

}

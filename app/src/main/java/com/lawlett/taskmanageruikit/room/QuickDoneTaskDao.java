package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.quick.data.model.QuickDoneModel;

import java.util.List;

@Dao
public interface QuickDoneTaskDao {

    @Query("SELECT*FROM quickdonemodel")
    List<QuickDoneModel> getAll();

    @Query("SELECT*FROM quickdonemodel")
    LiveData<List<QuickDoneModel>> getAllLive();

    @Insert
    void insert(QuickDoneModel quickdonemodel);

    @Delete
    void delete(QuickDoneModel quickdonemodel);

    @Delete
    void deleteAll(List<QuickDoneModel> quickdonemodel);

    @Update
    void update(QuickDoneModel quickdonemodel);

}

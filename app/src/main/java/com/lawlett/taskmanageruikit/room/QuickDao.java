package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;

import java.util.List;

@Dao
public interface QuickDao {

    @Query("SELECT*FROM quickModel")
    List<QuickModel> getAll();

    @Query("SELECT*FROM quickModel")
    LiveData<List<QuickModel>> getAllLive();

    @Insert
    void insert(QuickModel quickModel);

    @Delete
    void delete(QuickModel quickModel);

    @Delete
    void deleteAll(List<QuickModel> quickModel);

    @Update
    void update(QuickModel quickModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWord(List<QuickModel> quickModel);

}

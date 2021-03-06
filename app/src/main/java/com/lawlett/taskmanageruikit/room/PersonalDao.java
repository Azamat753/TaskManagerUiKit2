package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;

import java.util.List;

@Dao
public interface PersonalDao {

    @Query("SELECT*FROM personalModel")
    List<PersonalModel> getAll();

    @Query("SELECT*FROM personalmodel")
    LiveData<List<PersonalModel>> getAllLive();

    @Insert
    void insert(PersonalModel personalModel);

    @Delete
    void delete(PersonalModel personalModel);

    @Delete
    void deleteAll(List<PersonalModel> personalModel);

    @Update
    void update(PersonalModel personalModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWord(List<PersonalModel> personalModel);
}

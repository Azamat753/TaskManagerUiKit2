package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.tasksPage.data.done_model.PersonalDoneModel;

import java.util.List;

@Dao
public interface PersonalDoneTaskDao {

    @Query("SELECT*FROM personalDoneModel")
    List<PersonalDoneModel> getAll();

    @Query("SELECT*FROM personalDoneModel")
    LiveData<List<PersonalDoneModel>> getAllLive();

    @Insert
    void insert(PersonalDoneModel personalDoneModel);

    @Delete
    void delete(PersonalDoneModel personalDoneModel);

    @Delete
    void deleteAll(List<PersonalDoneModel> personalDoneModel);

    @Update
    void update(PersonalDoneModel personalDoneModel);

}

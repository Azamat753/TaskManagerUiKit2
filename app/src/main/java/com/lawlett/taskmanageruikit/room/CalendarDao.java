package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;

import java.util.List;

@Dao
public interface CalendarDao {

    @Query("SELECT*FROM calendarTaskModel")
    List<CalendarTaskModel> getAll();

    @Query("SELECT*FROM calendarTaskModel")
    LiveData<List<CalendarTaskModel>> getAllLive();

    @Insert
    void insert(CalendarTaskModel calendarTaskModel);

    @Delete
    void delete(CalendarTaskModel calendarTaskModel);

    @Delete
    void deleteAll(List<CalendarTaskModel> calendarTaskModel);

    @Update
    void update(CalendarTaskModel calendarTaskModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWord(List<CalendarTaskModel> calendarTaskModel);

}

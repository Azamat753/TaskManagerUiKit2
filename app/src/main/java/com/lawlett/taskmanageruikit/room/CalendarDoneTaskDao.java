package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarDoneModel;

import java.util.List;

@Dao
public interface CalendarDoneTaskDao {

    @Query("SELECT*FROM calendardonemodel")
    List<CalendarDoneModel> getAll();

    @Query("SELECT*FROM calendardonemodel")
    LiveData<List<CalendarDoneModel>> getAllLive();

    @Insert
    void insert(CalendarDoneModel calendardonemodel);

    @Delete
    void delete(CalendarDoneModel calendardonemodel);

    @Delete
    void deleteAll(List<CalendarDoneModel> calendardonemodel);

    @Update
    void update(CalendarDoneModel calendardonemodel);

}

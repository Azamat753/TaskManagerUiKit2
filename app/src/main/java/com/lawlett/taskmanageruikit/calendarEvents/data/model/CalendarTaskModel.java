package com.lawlett.taskmanageruikit.calendarEvents.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CalendarTaskModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String dataTime;
    public String title;
    public String startTime;
    public String endTime;
    public int chooseColor;

    public CalendarTaskModel(String dataTime, String title, String startTime, String endTime,int chooseColor) {
        this.dataTime = dataTime;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.chooseColor= chooseColor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

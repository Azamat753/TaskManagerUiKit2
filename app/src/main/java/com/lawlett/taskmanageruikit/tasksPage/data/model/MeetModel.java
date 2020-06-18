package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MeetModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String meetTask;


    public MeetModel(String meetTask) {
        this.meetTask = meetTask;
    }

    public String getMeetTask() {
        return meetTask;
    }

    public void setMeetTask(String meetTask) {
        this.meetTask = meetTask;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
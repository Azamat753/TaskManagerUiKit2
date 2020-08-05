package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MeetModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String meetTask;
    public Boolean isDone;

    public MeetModel(String meetTask, Boolean isDone) {
        this.meetTask = meetTask;
        this.isDone = isDone;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
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
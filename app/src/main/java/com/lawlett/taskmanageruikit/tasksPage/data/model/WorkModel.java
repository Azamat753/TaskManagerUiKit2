package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class WorkModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String workTask;

    public WorkModel(String workTask) {
        this.workTask = workTask;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWorkTask() {
        return workTask;
    }

    public void setWorkTask(String workTask) {
        this.workTask = workTask;
    }
}
package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class HomeModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String homeTask;
    public Boolean isDone;

    public HomeModel(String homeTask, Boolean isDone) {
        this.homeTask = homeTask;
        this.isDone = isDone;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getHomeTask() {
        return homeTask;
    }

    public void setHomeTask(String homeTask) {
        this.homeTask = homeTask;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
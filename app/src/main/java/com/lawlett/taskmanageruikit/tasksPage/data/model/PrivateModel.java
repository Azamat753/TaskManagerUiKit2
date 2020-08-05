package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class PrivateModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String privateTask;
    public Boolean isDone;

    public PrivateModel(String privateTask, Boolean isDone) {
        this.privateTask = privateTask;
        this.isDone = isDone;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getPrivateTask() {
        return privateTask;
    }

    public void setPrivateTask(String privateTask) {
        this.privateTask = privateTask;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class PrivateModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String privateTask;

    public PrivateModel(String privateTask) {
        this.privateTask = privateTask;
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
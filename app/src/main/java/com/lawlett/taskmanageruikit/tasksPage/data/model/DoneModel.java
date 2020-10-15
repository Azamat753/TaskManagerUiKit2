package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DoneModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String doneTask;
    public boolean isDone;

    public DoneModel(String doneTask, boolean isDone) {
        this.doneTask = doneTask;
        this.isDone = isDone;
    }
    public void setDone(Boolean done) {
        isDone = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDoneTask() {
        return doneTask;
    }

    public void setDoneTask(String doneTask) {
        this.doneTask = doneTask;
    }
}

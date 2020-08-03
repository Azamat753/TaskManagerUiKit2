package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class PersonalModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String personalTask;
    public Boolean isDone;

    public PersonalModel(String personalTask,Boolean isDone) {
        this.personalTask = personalTask;
        this.isDone=isDone;
    }

    public Boolean getDone() {
        return isDone;
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

    public String getPersonalTask() {
        return personalTask;
    }

    public void setPersonalTask(String personalTask) {
        this.personalTask = personalTask;
    }
}
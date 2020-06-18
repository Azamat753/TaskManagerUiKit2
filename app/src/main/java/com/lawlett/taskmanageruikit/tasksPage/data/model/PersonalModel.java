package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class PersonalModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String personalTask;


    public PersonalModel(String personalTask) {
        this.personalTask = personalTask;

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
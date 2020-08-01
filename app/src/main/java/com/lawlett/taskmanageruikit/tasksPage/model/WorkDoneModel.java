package com.lawlett.taskmanageruikit.tasksPage.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class WorkDoneModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String title;

    public WorkDoneModel(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

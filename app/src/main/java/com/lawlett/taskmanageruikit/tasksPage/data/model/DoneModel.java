package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class DoneModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String doneTitle;
    public String doneDesc;

    public DoneModel(String doneTitle, String doneDesc) {
        this.doneTitle = doneTitle;
        this.doneDesc = doneDesc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDoneTitle() {
        return doneTitle;
    }

    public void setDoneTitle(String doneTitle) {
        this.doneTitle = doneTitle;
    }

    public String getDoneDesc() {
        return doneDesc;
    }

    public void setDoneDesc(String doneDesc) {
        this.doneDesc = doneDesc;
    }
}

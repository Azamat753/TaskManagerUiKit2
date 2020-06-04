package com.lawlett.taskmanageruikit.tasksPage.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TaskModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String personalTask;
    private String workTask;
    private String meetTask;
    private String homeTask;
    private String privateTask;

    public TaskModel(String personalTask) {
        this.personalTask = personalTask;
    }

    public TaskModel(String personalTask, String workTask, String meetTask, String homeTask, String privateTask) {
        this.personalTask = personalTask;
        this.workTask = workTask;
        this.meetTask = meetTask;
        this.homeTask = homeTask;
        this.privateTask = privateTask;
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

    public String getWorkTask() {
        return workTask;
    }

    public void setWorkTask(String workTask) {
        this.workTask = workTask;
    }

    public String getMeetTask() {
        return meetTask;
    }

    public void setMeetTask(String meetTask) {
        this.meetTask = meetTask;
    }

    public String getHomeTask() {
        return homeTask;
    }

    public void setHomeTask(String homeTask) {
        this.homeTask = homeTask;
    }

    public String getPrivateTask() {
        return privateTask;
    }

    public void setPrivateTask(String privateTask) {
        this.privateTask = privateTask;
    }
}
